package cn.leolezury.eternalstarlight.common.client.helper;

import cn.leolezury.eternalstarlight.common.EternalStarlight;
import cn.leolezury.eternalstarlight.common.client.book.BookDefinition;
import cn.leolezury.eternalstarlight.common.client.book.component.BookComponentRegistry;
import cn.leolezury.eternalstarlight.common.client.book.component.ConfiguredBookComponent;
import cn.leolezury.eternalstarlight.common.client.book.component.IndexBookComponent;
import cn.leolezury.eternalstarlight.common.client.gui.screen.BookScreen;
import cn.leolezury.eternalstarlight.common.client.gui.screen.CrestSelectionScreen;
import cn.leolezury.eternalstarlight.common.client.gui.screen.GatekeeperDialogueScreen;
import cn.leolezury.eternalstarlight.common.client.gui.toast.SimpleTextToast;
import cn.leolezury.eternalstarlight.common.client.handler.ESClientHandler;
import cn.leolezury.eternalstarlight.common.client.particle.advanced.AdvancedParticleOptions;
import cn.leolezury.eternalstarlight.common.client.weather.ClientWeatherState;
import cn.leolezury.eternalstarlight.common.entity.living.boss.gatekeeper.TheGatekeeper;
import cn.leolezury.eternalstarlight.common.entity.projectile.SoulitSpectator;
import cn.leolezury.eternalstarlight.common.item.component.GuideBook;
import cn.leolezury.eternalstarlight.common.network.*;
import cn.leolezury.eternalstarlight.common.registry.ESDataComponents;
import cn.leolezury.eternalstarlight.common.registry.ESItems;
import cn.leolezury.eternalstarlight.common.registry.ESParticles;
import cn.leolezury.eternalstarlight.common.spell.ManaType;
import cn.leolezury.eternalstarlight.common.util.Color;
import cn.leolezury.eternalstarlight.common.util.Easing;
import cn.leolezury.eternalstarlight.common.util.SmoothSegmentedValue;
import net.minecraft.client.Camera;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.phys.Vec3;

import java.util.*;
import java.util.stream.Collectors;

public class ClientSideHelper extends ClientHelper {
	@Override
	public void handleServerToClientSimpleAction(SimpleActionPacket packet) {
		switch (packet.id()) {
			case SimpleActionPacket.S2C_CLEAR_WEATHER -> ClientWeatherState.weather = null;
		}
	}

	@Override
	public void handleParticlePacket(ParticlePacket packet) {
		ClientLevel clientLevel = Minecraft.getInstance().level;
		if (clientLevel != null) {
			clientLevel.addParticle(packet.particle(), packet.longDistance(), packet.x(), packet.y(), packet.z(), packet.dx(), packet.dy(), packet.dz());
		}
	}

	@Override
	public void handleOpenCrestGui(OpenCrestGuiPacket packet) {
		if (packet.ownedCrests().isEmpty()) {
			if (Minecraft.getInstance().player != null) {
				Minecraft.getInstance().player.displayClientMessage(Component.translatable("message." + EternalStarlight.ID + ".no_crest"), true);
			}
		} else {
			Minecraft.getInstance().setScreen(new CrestSelectionScreen(packet.crests(), packet.ownedCrests()));
		}
	}

	@Override
	public void handleUpdateCamera(UpdateCameraPacket packet) {
		if (packet.cameraId() == -1) {
			ESClientHandler.resetCameraIn = 0;
		} else {
			if (Minecraft.getInstance().level != null && !(Minecraft.getInstance().getCameraEntity() instanceof SoulitSpectator)) {
				Entity camera = Minecraft.getInstance().level.getEntity(packet.cameraId());
				if (camera != null) {
					ESClientHandler.resetCameraIn = 260;
					Minecraft.getInstance().options.hideGui = true;
					Minecraft.getInstance().setCameraEntity(camera);
				}
			}
		}
	}

	@Override
	public void handleClientMount(ClientMountPacket packet) {
		if (Minecraft.getInstance().level != null) {
			Entity rider = Minecraft.getInstance().level.getEntity(packet.riderId());
			Entity vehicle = Minecraft.getInstance().level.getEntity(packet.vehicleId());
			if (rider != null && vehicle != null) {
				rider.startRiding(vehicle, true);
			}
		}
	}

	@Override
	public void handleClientDismount(ClientDismountPacket packet) {
		if (Minecraft.getInstance().level != null) {
			Entity rider = Minecraft.getInstance().level.getEntity(packet.riderId());
			if (rider != null) {
				rider.stopRiding();
			}
		}
	}

	@Override
	public void handleOpenGatekeeperGui(OpenGatekeeperGuiPacket packet) {
		ClientLevel clientLevel = Minecraft.getInstance().level;
		if (clientLevel != null && clientLevel.getEntity(packet.id()) instanceof TheGatekeeper gatekeeper) {
			Minecraft.getInstance().setScreen(new GatekeeperDialogueScreen(gatekeeper, packet.challenged()));
		}
	}

	@Override
	public void handleUpdateBook(UpdateBookPacket packet) {
		Set<ResourceLocation> bookIds = new HashSet<>();
		LocalPlayer player = Minecraft.getInstance().player;
		if (player != null) {
			Inventory inventory = player.getInventory();
			for (int i = 0; i < inventory.getContainerSize(); i++) {
				ItemStack stack = inventory.getItem(i);
				GuideBook guideBook = stack.get(ESDataComponents.BOOK.get());
				if (guideBook != null) {
					bookIds.add(guideBook.id());
				}
			}
		}
		Set<BookDefinition> definitions = bookIds.stream().map(ESClientHandler.books::getBook).collect(Collectors.toSet());
		List<IndexBookComponent.Entry> newEntries = new ArrayList<>();
		List<IndexBookComponent.Entry> changedEntries = new ArrayList<>();
		for (BookDefinition definition : definitions) {
			List<IndexBookComponent.Entry> entries = new ArrayList<>();
			definition.components().stream().flatMap(List::stream)
				.filter(c -> c.component() == BookComponentRegistry.INDEX)
				.forEach(c -> {
					if (c.config() instanceof IndexBookComponent.Config config) {
						entries.addAll(config.entries());
					}
				});
			for (IndexBookComponent.Entry entry : entries) {
				Optional<ConfiguredBookComponent<?, ?>> jumpTo = definition.getComponent(entry.getJumpToId());
				boolean oldEnabled = jumpTo.isPresent() && jumpTo.get().isEnabled(packet.oldUnlocked());
				boolean enabled = jumpTo.isPresent() && jumpTo.get().isEnabled(packet.unlocked());
				if (!oldEnabled && enabled) {
					newEntries.add(entry);
				}
				for (ResourceLocation listeningId : entry.getListeningIds()) {
					Optional<ConfiguredBookComponent<?, ?>> listening = definition.getComponent(listeningId);
					boolean oldListeningEnabled = listening.isPresent() && listening.get().isEnabled(packet.oldUnlocked());
					boolean listeningEnabled = listening.isPresent() && listening.get().isEnabled(packet.unlocked());
					if (!oldListeningEnabled && listeningEnabled) {
						changedEntries.add(entry);
						break;
					}
				}
			}
		}
		changedEntries.removeAll(newEntries);
		if (newEntries.size() > 3) {
			Minecraft.getInstance().getToasts().addToast(new SimpleTextToast(
				Component.translatable("book." + EternalStarlight.ID + ".unlock"),
				Component.translatable("book." + EternalStarlight.ID + ".unlock.multiple"),
				ESItems.BOOK.get().getDefaultInstance()
			));
		} else {
			for (IndexBookComponent.Entry entry : newEntries) {
				Minecraft.getInstance().getToasts().addToast(new SimpleTextToast(
					Component.translatable("book." + EternalStarlight.ID + ".unlock"),
					entry.getText(),
					entry.getIcon()
				));
			}
		}
		if (changedEntries.size() > 3) {
			Minecraft.getInstance().getToasts().addToast(new SimpleTextToast(
				Component.translatable("book." + EternalStarlight.ID + ".update"),
				Component.translatable("book." + EternalStarlight.ID + ".unlock.multiple"),
				ESItems.BOOK.get().getDefaultInstance()
			));
		} else {
			for (IndexBookComponent.Entry entry : changedEntries) {
				Minecraft.getInstance().getToasts().addToast(new SimpleTextToast(
					Component.translatable("book." + EternalStarlight.ID + ".update"),
					entry.getText(),
					entry.getIcon()
				));
			}
		}
	}

	@Override
	public void handleOpenBook(OpenBookPacket packet) {
		BookDefinition definition = ESClientHandler.books.getBook(packet.bookId());
		if (definition != null) {
			Minecraft.getInstance().setScreen(new BookScreen(definition, packet.unlocked()));
		}
	}

	@Override
	public void handleUpdateBossBar(UpdateBossBarPacket packet) {
		if (packet.barType() == 0) {
			ESClientHandler.BOSS_BAR_TYPES.remove(packet.barId());
		} else {
			ESClientHandler.BOSS_BAR_TYPES.put(packet.barId(), packet.barType());
		}
	}

	@Override
	public void spawnStellarRackParticles(Vec3 center) {
		if (AdvancedParticleOptions.RANDOM.nextFloat() < 0.3) {
			if (AdvancedParticleOptions.RANDOM.nextBoolean()) {
				float size = (float) (1f + (AdvancedParticleOptions.RANDOM.nextFloat() - 0.5) * 0.4);
				new AdvancedParticleOptions()
					.speed(SmoothSegmentedValue.of(Easing.IN_OUT_SINE, (AdvancedParticleOptions.RANDOM.nextFloat() - 0.5f) * 0.1f, (AdvancedParticleOptions.RANDOM.nextFloat() - 0.5f) * 0.1f, 1),
						SmoothSegmentedValue.of(Easing.IN_OUT_BACK, 0.1f, 0.3f, 1),
						SmoothSegmentedValue.of(Easing.IN_OUT_SINE, (AdvancedParticleOptions.RANDOM.nextFloat() - 0.5f) * 0.1f, (AdvancedParticleOptions.RANDOM.nextFloat() - 0.5f) * 0.1f, 1))
					.spinSpeed(SmoothSegmentedValue.of(Easing.IN_OUT_QUAD, (float) ((12 + (AdvancedParticleOptions.RANDOM.nextFloat() - 0.5) * 10) * Mth.DEG_TO_RAD), (float) ((18 + (AdvancedParticleOptions.RANDOM.nextFloat() - 0.5) * 10) * Mth.DEG_TO_RAD), 1))
					.quadSize(SmoothSegmentedValue.of(Easing.IN_OUT_SINE, 0, size, 0.4f).add(Easing.IN_OUT_BOUNCE, size, 0, 0.6f))
					.lifetime((int) (40 + (AdvancedParticleOptions.RANDOM.nextFloat() - 0.5) * 20))
					.color(SmoothSegmentedValue.of(Easing.IN_OUT_QUART, 100 * 0.3f / 255f, 151 * 0.3f / 255f, 1),
						SmoothSegmentedValue.of(Easing.IN_OUT_SINE, 228 * 0.3f / 255f, 255 * 0.3f / 255f, 1),
						SmoothSegmentedValue.of(Easing.IN_OUT_SINE, 254 * 0.3f / 255f, 250 * 0.3f / 255f, 1),
						SmoothSegmentedValue.of(Easing.OUT_QUINT, 0, 1f, 0.7f).add(Easing.IN_OUT_QUAD, 1f, 0, 0.3f))
					.defaultOperators()
					.spawn(BuiltInRegistries.PARTICLE_TYPE.getKey(ESParticles.SHINE.get()), (float) center.x, (float) center.y, (float) center.z);
			}

			for (int i = 0; i < 4; i++) {
				new AdvancedParticleOptions()
					.speed(SmoothSegmentedValue.of(Easing.IN_OUT_SINE, (AdvancedParticleOptions.RANDOM.nextFloat() - 0.5f) * 0.1f, (AdvancedParticleOptions.RANDOM.nextFloat() - 0.5f) * 0.1f, 1),
						SmoothSegmentedValue.of(Easing.IN_OUT_BACK, 0.1f, 0.3f, 1),
						SmoothSegmentedValue.of(Easing.IN_OUT_SINE, (AdvancedParticleOptions.RANDOM.nextFloat() - 0.5f) * 0.1f, (AdvancedParticleOptions.RANDOM.nextFloat() - 0.5f) * 0.1f, 1))
					.spinSpeed(SmoothSegmentedValue.of(Easing.IN_OUT_QUAD, 18 * Mth.DEG_TO_RAD, 36 * Mth.DEG_TO_RAD, 1))
					.quadSize(SmoothSegmentedValue.of(Easing.IN_OUT_SINE, 0, 0.2f, 0.6f).add(Easing.IN_OUT_BOUNCE, 0.2f, 0, 0.4f))
					.lifetime((int) (40 + (AdvancedParticleOptions.RANDOM.nextFloat() - 0.5) * 20))
					.color(SmoothSegmentedValue.of(Easing.IN_OUT_QUART, 251 * 0.3f / 255f, 198 * 0.3f / 255f, 1),
						SmoothSegmentedValue.of(Easing.IN_OUT_SINE, 125 * 0.3f / 255f, 124 * 0.3f / 255f, 1),
						SmoothSegmentedValue.of(Easing.IN_OUT_SINE, 2 * 0.3f / 255f, 180 * 0.3f / 255f, 1),
						SmoothSegmentedValue.of(Easing.OUT_QUINT, 0, 1f, 0.7f).add(Easing.IN_OUT_QUAD, 1f, 0, 0.3f))
					.defaultOperators()
					.spawn(BuiltInRegistries.PARTICLE_TYPE.getKey(ESParticles.ADVANCED_GLOW.get()), (float) center.x, (float) center.y, (float) center.z);
			}
		}
		for (int i = 0; i < 5; i++) {
			new AdvancedParticleOptions()
				.speed(SmoothSegmentedValue.of(Easing.IN_OUT_SINE, (AdvancedParticleOptions.RANDOM.nextFloat() - 0.5f) * 0.03f, (AdvancedParticleOptions.RANDOM.nextFloat() - 0.5f) * 0.03f, 1),
					SmoothSegmentedValue.of(Easing.IN_OUT_SINE, (AdvancedParticleOptions.RANDOM.nextFloat() - 0.5f) * 0.03f, (AdvancedParticleOptions.RANDOM.nextFloat() - 0.5f) * 0.03f, 1),
					SmoothSegmentedValue.of(Easing.IN_OUT_SINE, (AdvancedParticleOptions.RANDOM.nextFloat() - 0.5f) * 0.03f, (AdvancedParticleOptions.RANDOM.nextFloat() - 0.5f) * 0.03f, 1))
				.spinSpeed(SmoothSegmentedValue.of(Easing.IN_OUT_QUAD, 18 * Mth.DEG_TO_RAD, 36 * Mth.DEG_TO_RAD, 1))
				.quadSize(SmoothSegmentedValue.of(Easing.IN_OUT_SINE, 0, 0.15f, 0.5f).add(Easing.IN_OUT_BOUNCE, 0.15f, 0, 0.5f))
				.lifetime(12)
				.color(SmoothSegmentedValue.of(Easing.IN_OUT_QUART, 251 * 0.3f / 255f, 198 * 0.3f / 255f, 1),
					SmoothSegmentedValue.of(Easing.IN_OUT_SINE, 125 * 0.3f / 255f, 124 * 0.3f / 255f, 1),
					SmoothSegmentedValue.of(Easing.IN_OUT_SINE, 2 * 0.3f / 255f, 180 * 0.3f / 255f, 1),
					SmoothSegmentedValue.of(Easing.OUT_QUINT, 0, 1f, 0.7f).add(Easing.IN_OUT_QUAD, 1f, 0, 0.3f))
				.defaultOperators()
				.spawn(BuiltInRegistries.PARTICLE_TYPE.getKey(ESParticles.ADVANCED_GLOW.get()), (float) center.x, (float) center.y + 0.45f, (float) center.z);
		}
	}

	@Override
	public void spawnStellarRackItemParticles(Vec3 center) {
		for (int i = 0; i < 5; i++) {
			new AdvancedParticleOptions()
				.speed(SmoothSegmentedValue.of(Easing.IN_OUT_SINE, (AdvancedParticleOptions.RANDOM.nextFloat() - 0.5f) * 0.03f, (AdvancedParticleOptions.RANDOM.nextFloat() - 0.5f) * 0.03f, 1),
					SmoothSegmentedValue.of(Easing.IN_OUT_SINE, (AdvancedParticleOptions.RANDOM.nextFloat() - 0.5f) * 0.03f, (AdvancedParticleOptions.RANDOM.nextFloat() - 0.5f) * 0.03f, 1),
					SmoothSegmentedValue.of(Easing.IN_OUT_SINE, (AdvancedParticleOptions.RANDOM.nextFloat() - 0.5f) * 0.03f, (AdvancedParticleOptions.RANDOM.nextFloat() - 0.5f) * 0.03f, 1))
				.spinSpeed(SmoothSegmentedValue.of(Easing.IN_OUT_QUAD, 18 * Mth.DEG_TO_RAD, 36 * Mth.DEG_TO_RAD, 1))
				.quadSize(SmoothSegmentedValue.of(Easing.IN_OUT_SINE, 0, 0.15f, 0.5f).add(Easing.IN_OUT_BOUNCE, 0.15f, 0, 0.5f))
				.lifetime(12)
				.color(SmoothSegmentedValue.of(Easing.IN_OUT_QUART, 251 * 0.3f / 255f, 198 * 0.3f / 255f, 1),
					SmoothSegmentedValue.of(Easing.IN_OUT_SINE, 125 * 0.3f / 255f, 124 * 0.3f / 255f, 1),
					SmoothSegmentedValue.of(Easing.IN_OUT_SINE, 2 * 0.3f / 255f, 180 * 0.3f / 255f, 1),
					SmoothSegmentedValue.of(Easing.OUT_QUINT, 0, 1f, 0.7f).add(Easing.IN_OUT_QUAD, 1f, 0, 0.3f))
				.defaultOperators()
				.spawn(BuiltInRegistries.PARTICLE_TYPE.getKey(ESParticles.ADVANCED_GLOW.get()), (float) center.x, (float) center.y, (float) center.z);
		}
	}

	@Override
	public void spawnManaCrystalItemParticles(ManaType type, Vec3 center) {
		for (int i = 0; i < 5; i++) {
			Vec3 pos = center.offsetRandom(AdvancedParticleOptions.RANDOM, 0.5f);
			Color color = Color.rgb(type.getColor());
			new AdvancedParticleOptions()
				.speed(SmoothSegmentedValue.of(Easing.IN_OUT_SINE, (AdvancedParticleOptions.RANDOM.nextFloat() - 0.5f) * 0.03f, (AdvancedParticleOptions.RANDOM.nextFloat() - 0.5f) * 0.03f, 1),
					SmoothSegmentedValue.of(Easing.IN_OUT_SINE, (AdvancedParticleOptions.RANDOM.nextFloat() - 0.5f) * 0.03f, AdvancedParticleOptions.RANDOM.nextFloat() * 0.015f, 1),
					SmoothSegmentedValue.of(Easing.IN_OUT_SINE, (AdvancedParticleOptions.RANDOM.nextFloat() - 0.5f) * 0.03f, (AdvancedParticleOptions.RANDOM.nextFloat() - 0.5f) * 0.03f, 1))
				.spinSpeed(SmoothSegmentedValue.of(Easing.IN_OUT_QUAD, 18 * Mth.DEG_TO_RAD, 36 * Mth.DEG_TO_RAD, 1))
				.quadSize(SmoothSegmentedValue.of(Easing.IN_OUT_SINE, 0, 0.25f, 0.6f).add(Easing.IN_OUT_BOUNCE, 0.25f, 0, 0.4f))
				.lifetime(12)
				.color(SmoothSegmentedValue.of(Easing.IN_OUT_QUART, color.r() * 0.4f / 255f, 0.3f, 1),
					SmoothSegmentedValue.of(Easing.IN_OUT_SINE, color.g() * 0.4f / 255f, 0.3f, 1),
					SmoothSegmentedValue.of(Easing.IN_OUT_SINE, color.b() * 0.4f / 255f, 0.3f, 1),
					SmoothSegmentedValue.of(Easing.OUT_QUINT, 0, 1f, 0.7f).add(Easing.IN_OUT_QUAD, 1f, 0, 0.3f))
				.defaultOperators()
				.spawn(BuiltInRegistries.PARTICLE_TYPE.getKey(ESParticles.ADVANCED_GLOW.get()), (float) pos.x, (float) pos.y, (float) pos.z);
		}
	}

	@Override
	public void handleMeteorShowerClientTick() {
		ClientLevel level = Minecraft.getInstance().level;
		Camera camera = Minecraft.getInstance().gameRenderer.getMainCamera();
		if (level != null && level.getGameTime() % 20 == 0) {
			Vec3 randomPos = camera.getPosition().offsetRandom(level.getRandom(), 75f);
			int height = level.getHeight(Heightmap.Types.MOTION_BLOCKING, (int) randomPos.x, (int) randomPos.z);
			level.addParticle(ESParticles.METEOR.get(), true, randomPos.x, Math.max(height + 75, camera.getPosition().y + 75), randomPos.z, 0, 0, 0);
		}
	}

	@Override
	public float handleMeteorShowerRainLevel() {
		float partialTick = Minecraft.getInstance().getTimer().getGameTimeDeltaPartialTick(Minecraft.getInstance().level != null && Minecraft.getInstance().level.tickRateManager().runsNormally());
		ClientWeatherState.levelTarget = 1;
		return ClientWeatherState.getRainLevel(partialTick);
	}
}

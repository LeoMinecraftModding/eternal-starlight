package cn.leolezury.eternalstarlight.common.client.helper;

import cn.leolezury.eternalstarlight.common.EternalStarlight;
import cn.leolezury.eternalstarlight.common.client.ClientWeatherInfo;
import cn.leolezury.eternalstarlight.common.client.book.Book;
import cn.leolezury.eternalstarlight.common.client.book.component.BookComponentDefinition;
import cn.leolezury.eternalstarlight.common.client.book.component.DisplayBookComponent;
import cn.leolezury.eternalstarlight.common.client.book.component.IndexBookComponent;
import cn.leolezury.eternalstarlight.common.client.book.component.TextBookComponent;
import cn.leolezury.eternalstarlight.common.client.gui.screen.BookScreen;
import cn.leolezury.eternalstarlight.common.client.gui.screen.CrestSelectionScreen;
import cn.leolezury.eternalstarlight.common.client.gui.screen.GatekeeperDialogueScreen;
import cn.leolezury.eternalstarlight.common.client.handler.ClientHandlers;
import cn.leolezury.eternalstarlight.common.client.particle.advanced.AdvancedParticleOptions;
import cn.leolezury.eternalstarlight.common.entity.living.boss.gatekeeper.TheGatekeeper;
import cn.leolezury.eternalstarlight.common.entity.projectile.SoulitSpectator;
import cn.leolezury.eternalstarlight.common.network.*;
import cn.leolezury.eternalstarlight.common.registry.ESEntities;
import cn.leolezury.eternalstarlight.common.registry.ESItems;
import cn.leolezury.eternalstarlight.common.registry.ESParticles;
import cn.leolezury.eternalstarlight.common.util.Easing;
import cn.leolezury.eternalstarlight.common.util.SmoothSegmentedValue;
import com.google.common.collect.Lists;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.phys.Vec3;
import org.joml.Quaternionf;

import java.util.List;

@Environment(EnvType.CLIENT)
public class ClientSideHelper implements ClientHelper {
	@Override
	public void handleS2cNoParam(NoParametersPacket packet) {
		switch (packet.id()) {
			case "cancel_weather" -> ClientWeatherInfo.WEATHER = null;
		}
	}

	@Override
	public void handleParticlePacket(ParticlePacket packet) {
		ClientLevel clientLevel = Minecraft.getInstance().level;
		if (clientLevel != null) {
			clientLevel.addParticle(packet.particle(), true, packet.x(), packet.y(), packet.z(), packet.dx(), packet.dy(), packet.dz());
		}
	}

	@Override
	public void handleOpenCrestGui(OpenCrestGuiPacket packet) {
		Minecraft.getInstance().setScreen(new CrestSelectionScreen(packet.crests().crests(), packet.ownedCrests().crests()));
	}

	@Override
	public void handleOpenGatekeeperGui(OpenGatekeeperGuiPacket packet) {
		ClientLevel clientLevel = Minecraft.getInstance().level;
		if (clientLevel != null && clientLevel.getEntity(packet.id()) instanceof TheGatekeeper gatekeeper) {
			Minecraft.getInstance().setScreen(new GatekeeperDialogueScreen(gatekeeper, packet.killedDragon(), packet.challenged()));
		}
	}

	@Override
	public void handleUpdateCamera(UpdateCameraPacket packet) {
		if (packet.cameraId() == -1) {
			ClientHandlers.RESET_CAMERA_IN = 0;
		} else {
			if (Minecraft.getInstance().level != null && !(Minecraft.getInstance().getCameraEntity() instanceof SoulitSpectator)) {
				Entity camera = Minecraft.getInstance().level.getEntity(packet.cameraId());
				if (camera != null) {
					ClientHandlers.RESET_CAMERA_IN = 260;
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

	private Component translatedBookText(String id) {
		return Component.translatable("book." + EternalStarlight.ID + "." + id);
	}

	@Override
	public void handleOpenStarlightStory(OpenStarlightStoryPacket packet) {
		DisplayBookComponent title = new DisplayBookComponent(105, 130)
			.imageDisplay(EternalStarlight.id("textures/gui/screen/book/separator.png"), 4, 92, 96, 11)
			.textDisplay(Component.translatable(ESItems.BOOK.get().getDescriptionId()), 52, 115, 1.2f);

		TextBookComponent preface = new TextBookComponent(translatedBookText("preface"), 105, 130);

		IndexBookComponent index = new IndexBookComponent(translatedBookText("index"), List.of(
			new IndexBookComponent.IndexItem(Component.literal("•").append(Component.translatable(ESEntities.STARLIGHT_GOLEM.get().getDescriptionId())), EternalStarlight.id("starlight_golem_display"), packet.unlocked().contains(EternalStarlight.id("enter_starlight"))),
			new IndexBookComponent.IndexItem(Component.literal("•").append(Component.translatable(ESEntities.LUNAR_MONSTROSITY.get().getDescriptionId())), EternalStarlight.id("lunar_monstrosity_display"), packet.unlocked().contains(EternalStarlight.id("enter_starlight"))),
			new IndexBookComponent.IndexItem(Component.literal("•").append(Component.translatable(ESEntities.TWILIGHT_GAZE.get().getDescriptionId())), EternalStarlight.id("twilight_gaze_display"), packet.unlocked().contains(EternalStarlight.id("twilight_gaze"))),
			new IndexBookComponent.IndexItem(Component.literal("•").append(Component.translatable(ESEntities.SHIMMER_LACEWING.get().getDescriptionId())), EternalStarlight.id("shimmer_lacewing_display"), packet.unlocked().contains(EternalStarlight.id("shimmer_lacewing")))
		), 105, 130);

		DisplayBookComponent golemDisplay = new DisplayBookComponent(105, 130)
			.entityDisplay(ESEntities.STARLIGHT_GOLEM.get(), 52, 75, -25, 210, 20, new Quaternionf().rotationXYZ(0.43633232F, 0.0F, 3.1415927F))
			.imageDisplay(EternalStarlight.id("textures/gui/screen/book/frame.png"), 18, 20, 68, 68)
			.imageDisplay(EternalStarlight.id("textures/gui/screen/book/separator.png"), 4, 92, 96, 11)
			.textDisplay(Component.translatable(ESEntities.STARLIGHT_GOLEM.get().getDescriptionId()), 52, 115, 1.2f);

		TextBookComponent golem = new TextBookComponent(translatedBookText("starlight_golem"), 105, 130);

		TextBookComponent golemSeen = new TextBookComponent(translatedBookText("starlight_golem_seen"), 105, 130);

		DisplayBookComponent lunarMonstrosityDisplay = new DisplayBookComponent(105, 130)
			.entityDisplay(ESEntities.LUNAR_MONSTROSITY.get(), 52, 80, -25, 210, 16, new Quaternionf().rotationXYZ(0.43633232F, 0.0F, 3.1415927F))
			.imageDisplay(EternalStarlight.id("textures/gui/screen/book/frame.png"), 18, 20, 68, 68)
			.imageDisplay(EternalStarlight.id("textures/gui/screen/book/separator.png"), 4, 92, 96, 11)
			.textDisplay(Component.translatable(ESEntities.LUNAR_MONSTROSITY.get().getDescriptionId()), 52, 115, 1.1f);

		TextBookComponent lunarMonstrosity = new TextBookComponent(translatedBookText("lunar_monstrosity"), 105, 130);

		TextBookComponent lunarMonstrositySeen = new TextBookComponent(translatedBookText("lunar_monstrosity_seen"), 105, 130);

		DisplayBookComponent twilightGazeDisplay = new DisplayBookComponent(105, 130)
			.entityDisplay(ESEntities.TWILIGHT_GAZE.get(), 52, 60, 0, 210, 25, new Quaternionf().rotationXYZ(0.43633232F, 0.0F, 3.1415927F))
			.imageDisplay(EternalStarlight.id("textures/gui/screen/book/frame.png"), 18, 20, 68, 68)
			.imageDisplay(EternalStarlight.id("textures/gui/screen/book/separator.png"), 4, 92, 96, 11)
			.textDisplay(Component.translatable(ESEntities.TWILIGHT_GAZE.get().getDescriptionId()), 52, 115, 1.2f);

		TextBookComponent twilightGaze = new TextBookComponent(translatedBookText("twilight_gaze"), 105, 130);

		DisplayBookComponent shimmerLacewingDisplay = new DisplayBookComponent(105, 130)
			.entityDisplay(ESEntities.SHIMMER_LACEWING.get(), 52, 65, 0, 210, 25, new Quaternionf().rotationXYZ(0.43633232F, 0.0F, 3.1415927F))
			.imageDisplay(EternalStarlight.id("textures/gui/screen/book/frame.png"), 18, 20, 68, 68)
			.imageDisplay(EternalStarlight.id("textures/gui/screen/book/separator.png"), 4, 92, 96, 11)
			.textDisplay(Component.translatable(ESEntities.SHIMMER_LACEWING.get().getDescriptionId()), 52, 115, 1.2f);

		TextBookComponent shimmerLacewing = new TextBookComponent(translatedBookText("shimmer_lacewing"), 105, 130);

		Book book = new Book(Lists.newArrayList(
			new BookComponentDefinition(title, EternalStarlight.id("title"), 11, 6, 5, 6),
			new BookComponentDefinition(preface, EternalStarlight.id("preface"), 11, 6, 5, 6),
			new BookComponentDefinition(index, EternalStarlight.id("index"), 11, 6, 5, 6),
			new BookComponentDefinition(golemDisplay, EternalStarlight.id("starlight_golem_display"), 11, 6, 5, 6, packet.unlocked().contains(EternalStarlight.id("enter_starlight"))),
			new BookComponentDefinition(golem, EternalStarlight.id("starlight_golem"), 11, 6, 5, 6, packet.unlocked().contains(EternalStarlight.id("enter_starlight"))),
			new BookComponentDefinition(golemSeen, EternalStarlight.id("starlight_golem_seen"), 11, 6, 5, 6, packet.unlocked().contains(EternalStarlight.id("enter_starlight")) && packet.unlocked().contains(EternalStarlight.id("starlight_golem_seen"))),
			new BookComponentDefinition(lunarMonstrosityDisplay, EternalStarlight.id("lunar_monstrosity_display"), 11, 6, 5, 6, packet.unlocked().contains(EternalStarlight.id("enter_starlight"))),
			new BookComponentDefinition(lunarMonstrosity, EternalStarlight.id("lunar_monstrosity"), 11, 6, 5, 6, packet.unlocked().contains(EternalStarlight.id("enter_starlight"))),
			new BookComponentDefinition(lunarMonstrositySeen, EternalStarlight.id("lunar_monstrosity_seen"), 11, 6, 5, 6, packet.unlocked().contains(EternalStarlight.id("enter_starlight")) && packet.unlocked().contains(EternalStarlight.id("lunar_monstrosity_seen"))),
			new BookComponentDefinition(twilightGazeDisplay, EternalStarlight.id("twilight_gaze_display"), 11, 6, 5, 6, packet.unlocked().contains(EternalStarlight.id("twilight_gaze"))),
			new BookComponentDefinition(twilightGaze, EternalStarlight.id("twilight_gaze"), 11, 6, 5, 6, packet.unlocked().contains(EternalStarlight.id("twilight_gaze"))),
			new BookComponentDefinition(shimmerLacewingDisplay, EternalStarlight.id("shimmer_lacewing_display"), 11, 6, 5, 6, packet.unlocked().contains(EternalStarlight.id("shimmer_lacewing"))),
			new BookComponentDefinition(shimmerLacewing, EternalStarlight.id("shimmer_lacewing"), 11, 6, 5, 6, packet.unlocked().contains(EternalStarlight.id("shimmer_lacewing")))
		), 240, 165, 18, 7, 12, 27,
			EternalStarlight.id("textures/gui/screen/book/book.png"),
			EternalStarlight.id("textures/gui/screen/book/book_cover.png"),
			EternalStarlight.id("textures/gui/screen/book/book_back_cover.png"),
			EternalStarlight.id("textures/gui/screen/book/book_flip_left.png"),
			EternalStarlight.id("textures/gui/screen/book/book_flip_right.png"));
		Minecraft.getInstance().setScreen(new BookScreen(book));
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
}

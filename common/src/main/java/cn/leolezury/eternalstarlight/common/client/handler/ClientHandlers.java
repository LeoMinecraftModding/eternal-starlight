package cn.leolezury.eternalstarlight.common.client.handler;

import cn.leolezury.eternalstarlight.common.EternalStarlight;
import cn.leolezury.eternalstarlight.common.block.EtherLiquidBlock;
import cn.leolezury.eternalstarlight.common.client.ClientWeatherInfo;
import cn.leolezury.eternalstarlight.common.client.visual.DelayedMultiBufferSource;
import cn.leolezury.eternalstarlight.common.client.visual.ScreenShake;
import cn.leolezury.eternalstarlight.common.client.visual.WorldVisualEffect;
import cn.leolezury.eternalstarlight.common.crest.Crest;
import cn.leolezury.eternalstarlight.common.data.ESBiomes;
import cn.leolezury.eternalstarlight.common.data.ESDimensions;
import cn.leolezury.eternalstarlight.common.data.ESRegistries;
import cn.leolezury.eternalstarlight.common.entity.interfaces.SpellCaster;
import cn.leolezury.eternalstarlight.common.entity.living.boss.gatekeeper.TheGatekeeper;
import cn.leolezury.eternalstarlight.common.entity.living.boss.golem.StarlightGolem;
import cn.leolezury.eternalstarlight.common.entity.living.boss.monstrosity.LunarMonstrosity;
import cn.leolezury.eternalstarlight.common.entity.projectile.SoulitSpectator;
import cn.leolezury.eternalstarlight.common.item.component.CurrentCrestComponent;
import cn.leolezury.eternalstarlight.common.network.NoParametersPacket;
import cn.leolezury.eternalstarlight.common.platform.ESPlatform;
import cn.leolezury.eternalstarlight.common.registry.*;
import cn.leolezury.eternalstarlight.common.spell.SpellCastData;
import cn.leolezury.eternalstarlight.common.util.ESBlockUtil;
import cn.leolezury.eternalstarlight.common.util.ESEntityUtil;
import cn.leolezury.eternalstarlight.common.util.ESGuiUtil;
import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.shaders.FogShape;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.ByteBufferBuilder;
import com.mojang.blaze3d.vertex.PoseStack;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.AttackIndicatorStatus;
import net.minecraft.client.Camera;
import net.minecraft.client.Minecraft;
import net.minecraft.client.Options;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.LerpingBossEvent;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.client.renderer.FogRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.BossEvent;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.FogType;
import net.minecraft.world.phys.Vec3;
import org.joml.Matrix4f;

import java.util.*;

@Environment(EnvType.CLIENT)
public class ClientHandlers {
	public static final int FULL_BRIGHT = 0xf000f0;
	public static final Set<Mob> BOSSES = Collections.newSetFromMap(new WeakHashMap<>());
	public static final List<WorldVisualEffect> VISUAL_EFFECTS = new ArrayList<>();
	public static final List<ScreenShake> SCREEN_SHAKES = new ArrayList<>();
	private static final ResourceLocation[] BAR_BACKGROUND_SPRITES = new ResourceLocation[]{ResourceLocation.withDefaultNamespace("boss_bar/pink_background"), ResourceLocation.withDefaultNamespace("boss_bar/blue_background"), ResourceLocation.withDefaultNamespace("boss_bar/red_background"), ResourceLocation.withDefaultNamespace("boss_bar/green_background"), ResourceLocation.withDefaultNamespace("boss_bar/yellow_background"), ResourceLocation.withDefaultNamespace("boss_bar/purple_background"), ResourceLocation.withDefaultNamespace("boss_bar/white_background")};
	private static final ResourceLocation[] BAR_PROGRESS_SPRITES = new ResourceLocation[]{ResourceLocation.withDefaultNamespace("boss_bar/pink_progress"), ResourceLocation.withDefaultNamespace("boss_bar/blue_progress"), ResourceLocation.withDefaultNamespace("boss_bar/red_progress"), ResourceLocation.withDefaultNamespace("boss_bar/green_progress"), ResourceLocation.withDefaultNamespace("boss_bar/yellow_progress"), ResourceLocation.withDefaultNamespace("boss_bar/purple_progress"), ResourceLocation.withDefaultNamespace("boss_bar/white_progress")};
	private static final ResourceLocation[] OVERLAY_BACKGROUND_SPRITES = new ResourceLocation[]{ResourceLocation.withDefaultNamespace("boss_bar/notched_6_background"), ResourceLocation.withDefaultNamespace("boss_bar/notched_10_background"), ResourceLocation.withDefaultNamespace("boss_bar/notched_12_background"), ResourceLocation.withDefaultNamespace("boss_bar/notched_20_background")};
	private static final ResourceLocation[] OVERLAY_PROGRESS_SPRITES = new ResourceLocation[]{ResourceLocation.withDefaultNamespace("boss_bar/notched_6_progress"), ResourceLocation.withDefaultNamespace("boss_bar/notched_10_progress"), ResourceLocation.withDefaultNamespace("boss_bar/notched_12_progress"), ResourceLocation.withDefaultNamespace("boss_bar/notched_20_progress")};
	private static final ResourceLocation ETHER_EROSION_OVERLAY = EternalStarlight.id("textures/misc/ether_erosion.png");
	private static final ResourceLocation ETHER_ARMOR_EMPTY = EternalStarlight.id("textures/gui/hud/ether_armor_empty.png");
	private static final ResourceLocation ETHER_ARMOR_HALF = EternalStarlight.id("textures/gui/hud/ether_armor_half.png");
	private static final ResourceLocation ETHER_ARMOR_FULL = EternalStarlight.id("textures/gui/hud/ether_armor_full.png");
	private static final ResourceLocation ORB_OF_PROPHECY_USE = EternalStarlight.id("textures/misc/orb_of_prophecy_use.png");
	private static final ResourceLocation CROSSHAIR_ATTACK_INDICATOR_BACKGROUND_SPRITE = ResourceLocation.withDefaultNamespace("hud/crosshair_attack_indicator_background");
	private static final ResourceLocation CROSSHAIR_ATTACK_INDICATOR_PROGRESS_SPRITE = ResourceLocation.withDefaultNamespace("hud/crosshair_attack_indicator_progress");
	private static final Map<ResourceKey<Crest>, GuiCrest> GUI_CRESTS = new HashMap<>();
	private static final List<DreamCatcherText> DREAM_CATCHER_TEXTS = new ArrayList<>();
	public static int RESET_CAMERA_IN;
	public static float FOG_START_DECREMENT;
	public static float FOG_END_DECREMENT;
	public static MultiBufferSource.BufferSource DELAYED_BUFFER_SOURCE = new DelayedMultiBufferSource(new ByteBufferBuilder(RenderType.TRANSIENT_BUFFER_SIZE));
	private static Matrix4f MODEL_VIEW_MATRIX = new Matrix4f();

	public static void onClientTick() {
		ClientWeatherInfo.tickRainLevel();
		List<WorldVisualEffect> effectsToRemove = new ArrayList<>();
		List<ScreenShake> screenShakesToRemove = new ArrayList<>();
		if (Minecraft.getInstance().level != null && Minecraft.getInstance().level.tickRateManager().runsNormally()) {
			for (WorldVisualEffect effect : VISUAL_EFFECTS) {
				if (effect.shouldRemove()) {
					effectsToRemove.add(effect);
				} else if (!Minecraft.getInstance().isPaused()) {
					effect.worldTick();
				}
			}
			for (WorldVisualEffect effect : effectsToRemove) {
				VISUAL_EFFECTS.remove(effect);
			}

			for (ScreenShake effect : SCREEN_SHAKES) {
				if (effect.shouldRemove()) {
					screenShakesToRemove.add(effect);
				} else if (!Minecraft.getInstance().isPaused()) {
					effect.tick();
				}
			}
			for (ScreenShake effect : screenShakesToRemove) {
				SCREEN_SHAKES.remove(effect);
			}
		}
		if (Minecraft.getInstance().level != null) {
			for (ClientSetupHandlers.WorldVisualEffectSpawnFunction function : ClientSetupHandlers.VISUAL_EFFECT_SPAWN_FUNCTIONS) {
				function.clientTick(Minecraft.getInstance().level, VISUAL_EFFECTS);
			}
		}

		if (Minecraft.getInstance().player != null) {
			Registry<Crest> registry = Minecraft.getInstance().player.registryAccess().registryOrThrow(ESRegistries.CREST);
			List<ResourceKey<Crest>> toRemove = new ArrayList<>();
			for (ResourceKey<Crest> key : GUI_CRESTS.keySet()) {
				if (registry.get(key) == null) {
					toRemove.add(key);
				}
			}
			for (ResourceKey<Crest> key : toRemove) {
				GUI_CRESTS.remove(key);
			}
			registry.forEach(crest -> {
				if (registry.getResourceKey(crest).isPresent() && !GUI_CRESTS.containsKey(registry.getResourceKey(crest).get())) {
					GUI_CRESTS.put(registry.getResourceKey(crest).get(), new GuiCrest());
				}
			});
			for (Map.Entry<ResourceKey<Crest>, GuiCrest> entry : GUI_CRESTS.entrySet()) {
				entry.getValue().tick();
			}
			ItemStack mainHand = Minecraft.getInstance().player.getMainHandItem();
			ItemStack offHand = Minecraft.getInstance().player.getOffhandItem();
			CurrentCrestComponent component = null;
			if (mainHand.has(ESDataComponents.CURRENT_CREST.get())) {
				component = mainHand.get(ESDataComponents.CURRENT_CREST.get());
			} else if (offHand.has(ESDataComponents.CURRENT_CREST.get())) {
				component = offHand.get(ESDataComponents.CURRENT_CREST.get());
			}
			for (Map.Entry<ResourceKey<Crest>, GuiCrest> entry : GUI_CRESTS.entrySet()) {
				entry.getValue().shouldShow = false;
			}
			if (component != null && component.crest().isBound()) {
				Optional<ResourceKey<Crest>> key = registry.getResourceKey(component.crest().value());
				if (key.isPresent() && GUI_CRESTS.containsKey(key.get())) {
					GUI_CRESTS.get(key.get()).shouldShow = true;
				}
			}
			if (ClientSetupHandlers.KEY_MAPPINGS.get(EternalStarlight.id("switch_crest")).consumeClick()) {
				ESPlatform.INSTANCE.sendToServer(new NoParametersPacket("switch_crest"));
			}
		}

		if (Minecraft.getInstance().player != null) {
			if (RESET_CAMERA_IN > 0) {
				RESET_CAMERA_IN--;
				Minecraft.getInstance().options.hideGui = true;
				if (RESET_CAMERA_IN <= 0) {
					Minecraft.getInstance().setCameraEntity(Minecraft.getInstance().player);
					Minecraft.getInstance().options.hideGui = false;
					RESET_CAMERA_IN = 0;
				}
			} else if (Minecraft.getInstance().getCameraEntity() instanceof SoulitSpectator) {
				Minecraft.getInstance().setCameraEntity(Minecraft.getInstance().player);
				Minecraft.getInstance().options.hideGui = false;
			}
		}

		if (Minecraft.getInstance().player != null) {
			LocalPlayer player = Minecraft.getInstance().player;
			Camera camera = Minecraft.getInstance().gameRenderer.getMainCamera();
			FOG_START_DECREMENT -= 0.5f;
			FOG_END_DECREMENT -= 0.5f;
			Holder<Biome> biomeHolder = player.level().getBiome(player.blockPosition());
			if (camera.getFluidInCamera() == FogType.NONE && player.level().getBlockState(camera.getBlockPosition()).getFluidState().isEmpty()) {
				if (biomeHolder.is(ESBiomes.STARLIGHT_PERMAFROST_FOREST)) {
					FOG_START_DECREMENT += (0.5f + 0.75f);
					FOG_END_DECREMENT += (0.5f + 0.5f);
					FOG_END_DECREMENT = Mth.clamp(FOG_END_DECREMENT, 0, 80);
				}
			}
		}

		LocalPlayer player = Minecraft.getInstance().player;
		if (player != null && player.hasEffect(ESMobEffects.DREAM_CATCHER.asHolder())) {
			List<DreamCatcherText> toRemove = new ArrayList<>();
			for (DreamCatcherText text : DREAM_CATCHER_TEXTS) {
				text.updatePosition();
				int width = Minecraft.getInstance().font.width(text.getText());
				int height = Minecraft.getInstance().font.lineHeight;
				if (text.getX() - width / 2 > Minecraft.getInstance().getWindow().getGuiScaledWidth() || text.getY() - height / 2 > Minecraft.getInstance().getWindow().getGuiScaledHeight()) {
					toRemove.add(text);
				}
			}
			for (DreamCatcherText text : toRemove) {
				DREAM_CATCHER_TEXTS.remove(text);
			}
			if (player.getRandom().nextInt(40) == 0 && DREAM_CATCHER_TEXTS.size() < 20) {
				Component component = Component.translatable("message." + EternalStarlight.ID + ".dream_catcher_" + player.getRandom().nextInt(7));
				DREAM_CATCHER_TEXTS.add(new DreamCatcherText(component, (int) (player.getRandom().nextFloat() * 5 + 1), -Minecraft.getInstance().font.width(component) / 2, player.getRandom().nextInt(Minecraft.getInstance().getWindow().getGuiScaledHeight())));
			}
		} else {
			DREAM_CATCHER_TEXTS.clear();
		}
	}

	public static float getScreenShakeYawOffset() {
		float sum = 0;
		for (ScreenShake screenShake : SCREEN_SHAKES) {
			sum += screenShake.getYawOffset();
		}
		return sum;
	}

	public static float getScreenShakePitchOffset() {
		float sum = 0;
		for (ScreenShake screenShake : SCREEN_SHAKES) {
			sum += screenShake.getPitchOffset();
		}
		return sum;
	}

	public static void onAfterRenderEntities(MultiBufferSource source, PoseStack stack, float partialTicks) {
		Camera camera = Minecraft.getInstance().gameRenderer.getMainCamera();
		Vec3 cameraPos = camera.getPosition();
		stack.pushPose();
		stack.translate(-cameraPos.x(), -cameraPos.y(), -cameraPos.z());
		for (WorldVisualEffect effect : VISUAL_EFFECTS) {
			if (!effect.shouldRemove()) {
				effect.render(source, stack, partialTicks);
			}
		}
		stack.popPose();
	}

	public static void onAfterRenderParticles() {
		MODEL_VIEW_MATRIX = RenderSystem.getModelViewMatrix();
	}

	public static void onAfterRenderWeather() {
		RenderSystem.enableCull();
		RenderSystem.enableBlend();
		RenderSystem.enableDepthTest();
		RenderSystem.depthMask(false);
		Matrix4f matrix4f = new Matrix4f(RenderSystem.getModelViewMatrix());
		RenderSystem.getModelViewMatrix().set(MODEL_VIEW_MATRIX);
		if (Minecraft.useShaderTransparency()) {
			Minecraft.getInstance().getMainRenderTarget().bindWrite(false);
		}
		DELAYED_BUFFER_SOURCE.endBatch();
		RenderSystem.getModelViewMatrix().set(matrix4f);
		if (Minecraft.useShaderTransparency() && Minecraft.getInstance().levelRenderer.getCloudsTarget() != null) {
			Minecraft.getInstance().levelRenderer.getCloudsTarget().bindWrite(false);
		}
	}

	public static Vec3 computeCameraAngles(Vec3 angles) {
		Camera camera = Minecraft.getInstance().gameRenderer.getMainCamera();
		if (camera.isDetached() && Minecraft.getInstance().player instanceof SpellCaster caster && caster.getSpellData().hasSpell()) {
			camera.move(-camera.getMaxZoom(2 * Minecraft.getInstance().player.getScale()), 1, 0);
		}
		return angles.add(getScreenShakePitchOffset(), getScreenShakeYawOffset(), 0);
	}

	// tail of setupFog
	public static void onRenderFog(Camera camera, FogRenderer.FogMode fogMode) {
		Minecraft minecraft = Minecraft.getInstance();
		LocalPlayer player = minecraft.player;
		if (player == null) {
			return;
		}
		if (ESPlatform.INSTANCE.getLoader() == ESPlatform.Loader.FABRIC) {
			FluidState fluidState = camera.getEntity().level().getFluidState(camera.getBlockPosition());
			if (fluidState.is(ESFluids.ETHER_STILL.get()) || fluidState.is(ESFluids.ETHER_FLOWING.get())) {
				if (camera.getPosition().y < (double) ((float) camera.getBlockPosition().getY() + fluidState.getHeight(camera.getEntity().level(), camera.getBlockPosition()))) {
					RenderSystem.setShaderFogStart(0.0F);
					RenderSystem.setShaderFogEnd(3.0F);
					RenderSystem.setShaderFogColor(232 / 255F, 255 / 255F, 222 / 255F);
					RenderSystem.setShaderFogShape(FogShape.SPHERE);
				}
			}
		}

		if (player.level().dimension() == ESDimensions.STARLIGHT_KEY && camera.getFluidInCamera() == FogType.NONE && player.level().getBlockState(camera.getBlockPosition()).getFluidState().isEmpty() && fogMode == FogRenderer.FogMode.FOG_TERRAIN) {
			FOG_START_DECREMENT = Mth.clamp(FOG_START_DECREMENT, 0, RenderSystem.getShaderFogStart() + 5);
			FOG_END_DECREMENT = Mth.clamp(FOG_END_DECREMENT, 0, RenderSystem.getShaderFogEnd() - 50);
			RenderSystem.setShaderFogStart(RenderSystem.getShaderFogStart() - FOG_START_DECREMENT);
			RenderSystem.setShaderFogEnd(RenderSystem.getShaderFogEnd() - FOG_END_DECREMENT);

			Holder<Biome> biomeHolder = player.level().getBiome(player.blockPosition());
			if (biomeHolder.is(ESBiomes.STARLIGHT_PERMAFROST_FOREST)) {
				RenderSystem.setShaderFogShape(FogShape.SPHERE);
			}
		}
	}

	public static boolean renderBossBar(GuiGraphics guiGraphics, LerpingBossEvent bossEvent, int x, int y) {
		ResourceLocation barLocation;
		Mob boss = null;
		if (BOSSES.isEmpty()) {
			return false;
		}
		for (Mob mob : BOSSES) {
			if (mob.getUUID().equals(bossEvent.getId())) {
				boss = mob;
				break;
			}
		}
		switch (boss) {
			case TheGatekeeper theGatekeeper -> barLocation = ResourceLocation.fromNamespaceAndPath(EternalStarlight.ID, "textures/gui/bars/the_gatekeeper.png");
			case StarlightGolem starlightGolem -> barLocation = ResourceLocation.fromNamespaceAndPath(EternalStarlight.ID, "textures/gui/bars/starlight_golem.png");
			case LunarMonstrosity lunarMonstrosity -> barLocation = ResourceLocation.fromNamespaceAndPath(EternalStarlight.ID, "textures/gui/bars/lunar_monstrosity" + (lunarMonstrosity.getPhase() > 0 ? "_soul.png" : ".png"));
			case null, default -> {
				return false;
			}
		}
		//event.setIncrement(12 + 2 * Minecraft.getInstance().font.lineHeight);
		drawBar(guiGraphics, x, y, bossEvent, barLocation);
		Component component = bossEvent.getName();
		int textWidth = Minecraft.getInstance().font.width(component);
		int textX = Minecraft.getInstance().getWindow().getGuiScaledWidth() / 2 - textWidth / 2;
		int textY = y - 9;
		guiGraphics.drawString(Minecraft.getInstance().font, component, textX, textY, 16777215);
        /*int descWidth = Minecraft.getInstance().font.width(bossDesc);
        int descX = Minecraft.getInstance().getWindow().getGuiScaledWidth() / 2 - descWidth / 2;
        int descY = event.getY() + 9;
        event.getGuiGraphics().drawString(Minecraft.getInstance().font, bossDesc, descX, descY, 16777215);*/
		return true;
	}

	public static void drawBar(GuiGraphics guiGraphics, int x, int y, BossEvent event, ResourceLocation barLocation) {
		drawBar(guiGraphics, x, y, event, 182, BAR_BACKGROUND_SPRITES, OVERLAY_BACKGROUND_SPRITES);
		int k = Mth.lerpDiscrete(event.getProgress(), 0, 182);
		if (k > 0) {
			drawBar(guiGraphics, x, y, event, k, BAR_PROGRESS_SPRITES, OVERLAY_PROGRESS_SPRITES);
		}
		guiGraphics.blit(barLocation, x - 1, y - 5, 0.0F, 0.0F, 184, 16, 184, 16);
	}

	private static void drawBar(GuiGraphics guiGraphics, int x, int y, BossEvent bossEvent, int progress, ResourceLocation[] bars, ResourceLocation[] overlays) {
		guiGraphics.blitSprite(bars[bossEvent.getColor().ordinal()], 182, 5, 0, 0, x, y, progress, 5);
		if (bossEvent.getOverlay() != BossEvent.BossBarOverlay.PROGRESS) {
			RenderSystem.enableBlend();
			guiGraphics.blitSprite(overlays[bossEvent.getOverlay().ordinal() - 1], 182, 5, 0, 0, x, y, progress, 5);
			RenderSystem.disableBlend();
		}
	}

	// copied from Gui
	public static void renderTextureOverlay(GuiGraphics guiGraphics, ResourceLocation resourceLocation, float f) {
		RenderSystem.disableDepthTest();
		RenderSystem.depthMask(false);
		RenderSystem.enableBlend();
		guiGraphics.setColor(1.0F, 1.0F, 1.0F, f);
		guiGraphics.blit(resourceLocation, 0, 0, -90, 0.0F, 0.0F, guiGraphics.guiWidth(), guiGraphics.guiHeight(), guiGraphics.guiWidth(), guiGraphics.guiHeight());
		RenderSystem.disableBlend();
		RenderSystem.depthMask(true);
		RenderSystem.enableDepthTest();
		guiGraphics.setColor(1.0F, 1.0F, 1.0F, 1.0F);
	}

	public static void renderSpellCrosshair(GuiGraphics guiGraphics, int screenWidth, int screenHeight) {
		Options options = Minecraft.getInstance().options;
		if (options.getCameraType().isFirstPerson()) {
			LocalPlayer player = Minecraft.getInstance().player;
			if (player instanceof SpellCaster caster && caster.getSpellData().hasSpell()) {
				SpellCastData data = caster.getSpellData();
				RenderSystem.blendFuncSeparate(GlStateManager.SourceFactor.ONE_MINUS_DST_COLOR, GlStateManager.DestFactor.ONE_MINUS_SRC_COLOR, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
				if (Minecraft.getInstance().options.attackIndicator().get() == AttackIndicatorStatus.CROSSHAIR) {
					float f = Math.min(1, (float) data.castTicks() / data.spell().spellProperties().preparationTicks());
					if (data.castTicks() > data.spell().spellProperties().preparationTicks()) {
						f = Math.max(0, 1 - (float) (data.castTicks() - data.spell().spellProperties().preparationTicks()) / data.spell().spellProperties().spellTicks());
					}

					int j = screenHeight / 2 - 7 + 16;
					int k = screenWidth / 2 - 8;

					int l = (int) (f * 17.0F);
					guiGraphics.blitSprite(CROSSHAIR_ATTACK_INDICATOR_BACKGROUND_SPRITE, k, j, 16, 4);
					guiGraphics.blitSprite(CROSSHAIR_ATTACK_INDICATOR_PROGRESS_SPRITE, 16, 4, 0, 0, k, j, l, 4);
				}

				RenderSystem.defaultBlendFunc();
			}
		}
	}

	public static void renderEtherErosion(GuiGraphics guiGraphics) {
		float clientEtherTicksRaw = ESEntityUtil.getPersistentData(Minecraft.getInstance().player).getInt(EtherLiquidBlock.TAG_CLIENT_IN_ETHER_TICKS);
		float clientEtherTicks = Math.min(clientEtherTicksRaw + Minecraft.getInstance().getTimer().getGameTimeDeltaPartialTick(Minecraft.getInstance().level != null && Minecraft.getInstance().level.tickRateManager().runsNormally()), 140f);
		float erosionProgress = Math.min(clientEtherTicks, 140f) / 140f;
		if (clientEtherTicksRaw > 0) {
			renderTextureOverlay(guiGraphics, ETHER_EROSION_OVERLAY, erosionProgress);
		}
	}

	public static void renderEtherArmor(GuiGraphics guiGraphics, int screenWidth, int screenHeight) {
		Minecraft minecraft = Minecraft.getInstance();
		if (ESBlockUtil.isEntityInBlock(minecraft.player, ESBlocks.ETHER.get())) {
			minecraft.getProfiler().push("armor");
			int initialX = screenWidth / 2 - 91;
			int initialY = screenHeight - 39;
			float maxHealth = (float) Math.max(minecraft.player.getAttributeValue(Attributes.MAX_HEALTH), Mth.ceil(minecraft.player.getHealth()));
			int absorptionAmount = Mth.ceil(minecraft.player.getAbsorptionAmount());
			int q = Mth.ceil((maxHealth + (float) absorptionAmount) / 2.0F / 10.0F);
			int r = Math.max(10 - (q - 2), 3);
			int armorValue = minecraft.player.getArmorValue();
			int y = initialY - (q - 1) * r - 10;
			for (int i = 0; i < 10; ++i) {
				if (armorValue > 0) {
					int x = initialX + i * 8;
					if (i * 2 + 1 < armorValue) {
						guiGraphics.blit(ETHER_ARMOR_FULL, x, y, 0f, 0f, 9, 9, 9, 9);
					}

					if (i * 2 + 1 == armorValue) {
						guiGraphics.blit(ETHER_ARMOR_HALF, x, y, 0f, 0f, 9, 9, 9, 9);
					}

					if (i * 2 + 1 > armorValue) {
						guiGraphics.blit(ETHER_ARMOR_EMPTY, x, y, 0f, 0f, 9, 9, 9, 9);
					}
				}
			}
		}
	}

	public static void renderOrbOfProphecyUse(GuiGraphics guiGraphics) {
		LocalPlayer player = Minecraft.getInstance().player;
		if (player != null && player.isUsingItem() && player.getUseItem().is(ESItems.ORB_OF_PROPHECY.get()) && !player.getUseItem().has(ESDataComponents.CURRENT_CREST.get())) {
			int usingTicks = player.getTicksUsingItem();
			float ticks = Math.min(usingTicks + Minecraft.getInstance().getTimer().getGameTimeDeltaPartialTick(Minecraft.getInstance().level != null && Minecraft.getInstance().level.tickRateManager().runsNormally()), 150f);
			float progress = Math.min(ticks, 150f) / 150f;
			if (usingTicks < 150) {
				renderTextureOverlay(guiGraphics, ORB_OF_PROPHECY_USE, progress);
			}
		}
	}

	public static void renderCurrentCrest(GuiGraphics guiGraphics) {
		if (Minecraft.getInstance().player != null) {
			Registry<Crest> registry = Minecraft.getInstance().player.registryAccess().registryOrThrow(ESRegistries.CREST);
			float partialTicks = Minecraft.getInstance().getTimer().getGameTimeDeltaPartialTick(Minecraft.getInstance().level != null && Minecraft.getInstance().level.tickRateManager().runsNormally());
			for (Map.Entry<ResourceKey<Crest>, GuiCrest> entry : GUI_CRESTS.entrySet()) {
				GuiCrest guiCrest = entry.getValue();
				Crest crest = registry.get(entry.getKey());
				if (crest != null) {
					ESGuiUtil.blit(guiGraphics, crest.texture(), guiCrest.getX(partialTicks), guiCrest.getY(partialTicks), 72, 72, 72, 72);
				}
			}
		}
	}

	public static void renderDreamCatcher(GuiGraphics guiGraphics) {
		LocalPlayer player = Minecraft.getInstance().player;
		if (player != null && player.hasEffect(ESMobEffects.DREAM_CATCHER.asHolder())) {
			for (DreamCatcherText text : DREAM_CATCHER_TEXTS) {
				guiGraphics.drawCenteredString(Minecraft.getInstance().font, text.getText(), text.getX(), text.getY(), 0x5187c4);
			}
		}
	}

	private static class GuiCrest {
		private boolean shouldShow = false;
		private float prevAngle, angle;

		public void tick() {
			prevAngle = angle;
			angle = Mth.approachDegrees(angle, shouldShow ? 45 : -135, 15);
		}

		public float getX(float partialTicks) {
			return (float) Math.cos(Mth.lerp(partialTicks, prevAngle, angle) * Mth.DEG_TO_RAD) * 36 * Mth.SQRT_OF_TWO - 36;
		}

		public float getY(float partialTicks) {
			return (float) Math.sin(Mth.lerp(partialTicks, prevAngle, angle) * Mth.DEG_TO_RAD) * 36 * Mth.SQRT_OF_TWO - 36;
		}
	}

	private static class DreamCatcherText {
		private final Component text;
		private final int speed;
		private int x;
		private final int y;

		public DreamCatcherText(Component component, int speed, int x, int y) {
			this.text = component;
			this.speed = speed;
			this.x = x;
			this.y = y;
		}

		public Component getText() {
			return text;
		}

		public int getX() {
			return x;
		}

		public int getY() {
			return y;
		}

		public void updatePosition() {
			x += speed;
		}
	}
}

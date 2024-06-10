package cn.leolezury.eternalstarlight.common.client.handler;

import cn.leolezury.eternalstarlight.common.EternalStarlight;
import cn.leolezury.eternalstarlight.common.client.ClientWeatherInfo;
import cn.leolezury.eternalstarlight.common.client.visual.TrailVisualEffect;
import cn.leolezury.eternalstarlight.common.client.visual.WorldVisualEffect;
import cn.leolezury.eternalstarlight.common.data.ESBiomes;
import cn.leolezury.eternalstarlight.common.entity.interfaces.SpellCaster;
import cn.leolezury.eternalstarlight.common.entity.interfaces.TrailOwner;
import cn.leolezury.eternalstarlight.common.entity.living.boss.gatekeeper.TheGatekeeper;
import cn.leolezury.eternalstarlight.common.entity.living.boss.golem.StarlightGolem;
import cn.leolezury.eternalstarlight.common.entity.living.boss.monstrosity.LunarMonstrosity;
import cn.leolezury.eternalstarlight.common.entity.misc.CameraShake;
import cn.leolezury.eternalstarlight.common.entity.projectile.SoulitSpectator;
import cn.leolezury.eternalstarlight.common.platform.ESPlatform;
import cn.leolezury.eternalstarlight.common.registry.ESBlocks;
import cn.leolezury.eternalstarlight.common.registry.ESFluids;
import cn.leolezury.eternalstarlight.common.registry.ESItems;
import cn.leolezury.eternalstarlight.common.registry.ESMobEffects;
import cn.leolezury.eternalstarlight.common.spell.SpellCastData;
import cn.leolezury.eternalstarlight.common.util.ESBlockUtil;
import cn.leolezury.eternalstarlight.common.util.ESEntityUtil;
import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.shaders.FogShape;
import com.mojang.blaze3d.systems.RenderSystem;
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
import net.minecraft.core.Holder;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.BossEvent;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.FogType;
import net.minecraft.world.phys.Vec3;

import java.util.*;

@Environment(EnvType.CLIENT)
public class ClientHandlers {
    public static final Set<Mob> BOSSES = Collections.newSetFromMap(new WeakHashMap<>());
    public static final List<WorldVisualEffect> VISUAL_EFFECTS = new ArrayList<>();
    private static final ResourceLocation[] BAR_BACKGROUND_SPRITES = new ResourceLocation[]{new ResourceLocation("boss_bar/pink_background"), new ResourceLocation("boss_bar/blue_background"), new ResourceLocation("boss_bar/red_background"), new ResourceLocation("boss_bar/green_background"), new ResourceLocation("boss_bar/yellow_background"), new ResourceLocation("boss_bar/purple_background"), new ResourceLocation("boss_bar/white_background")};
    private static final ResourceLocation[] BAR_PROGRESS_SPRITES = new ResourceLocation[]{new ResourceLocation("boss_bar/pink_progress"), new ResourceLocation("boss_bar/blue_progress"), new ResourceLocation("boss_bar/red_progress"), new ResourceLocation("boss_bar/green_progress"), new ResourceLocation("boss_bar/yellow_progress"), new ResourceLocation("boss_bar/purple_progress"), new ResourceLocation("boss_bar/white_progress")};
    private static final ResourceLocation[] OVERLAY_BACKGROUND_SPRITES = new ResourceLocation[]{new ResourceLocation("boss_bar/notched_6_background"), new ResourceLocation("boss_bar/notched_10_background"), new ResourceLocation("boss_bar/notched_12_background"), new ResourceLocation("boss_bar/notched_20_background")};
    private static final ResourceLocation[] OVERLAY_PROGRESS_SPRITES = new ResourceLocation[]{new ResourceLocation("boss_bar/notched_6_progress"), new ResourceLocation("boss_bar/notched_10_progress"), new ResourceLocation("boss_bar/notched_12_progress"), new ResourceLocation("boss_bar/notched_20_progress")};
    private static final ResourceLocation ETHER_EROSION_OVERLAY = EternalStarlight.id("textures/misc/ether_erosion.png");
    private static final ResourceLocation ETHER_ARMOR_EMPTY = EternalStarlight.id("textures/gui/hud/ether_armor_empty.png");
    private static final ResourceLocation ETHER_ARMOR_HALF = EternalStarlight.id("textures/gui/hud/ether_armor_half.png");
    private static final ResourceLocation ETHER_ARMOR_FULL = EternalStarlight.id("textures/gui/hud/ether_armor_full.png");
    private static final ResourceLocation ORB_OF_PROPHECY_USE = EternalStarlight.id("textures/misc/orb_of_prophecy_use.png");
    private static final ResourceLocation CROSSHAIR_ATTACK_INDICATOR_BACKGROUND_SPRITE = new ResourceLocation("hud/crosshair_attack_indicator_background");
    private static final ResourceLocation CROSSHAIR_ATTACK_INDICATOR_PROGRESS_SPRITE = new ResourceLocation("hud/crosshair_attack_indicator_progress");
    private static final List<DreamCatcherText> DREAM_CATCHER_TEXTS = new ArrayList<>();
    public static int resetCameraIn;
    public static float biomeFogStartDecrement;
    public static float biomeFogEndDecrement;

    public static void onClientTick() {
        ClientWeatherInfo.tickRainLevel();
        List<WorldVisualEffect> effectsToRemove = new ArrayList<>();
        for (WorldVisualEffect effect : VISUAL_EFFECTS) {
            if (effect.shouldRemove()) {
                effectsToRemove.add(effect);
            } else {
                effect.tick();
            }
        }
        for (WorldVisualEffect effect : effectsToRemove) {
            VISUAL_EFFECTS.remove(effect);
        }
        if (Minecraft.getInstance().level != null) {
            for (Entity entity : Minecraft.getInstance().level.entitiesForRendering()) {
                if (entity instanceof TrailOwner && VISUAL_EFFECTS.stream().noneMatch(effect -> effect instanceof TrailVisualEffect<?> trail && trail.getEntity().getUUID().equals(entity.getUUID()))) {
                    VISUAL_EFFECTS.add(new TrailVisualEffect<>(entity));
                }
            }
        }
        if (Minecraft.getInstance().player != null) {
            if (resetCameraIn > 0) {
                resetCameraIn--;
                Minecraft.getInstance().options.hideGui = true;
                if (resetCameraIn <= 0) {
                    Minecraft.getInstance().setCameraEntity(Minecraft.getInstance().player);
                    Minecraft.getInstance().options.hideGui = false;
                    resetCameraIn = 0;
                }
            } else if (Minecraft.getInstance().getCameraEntity() instanceof SoulitSpectator) {
                Minecraft.getInstance().setCameraEntity(Minecraft.getInstance().player);
                Minecraft.getInstance().options.hideGui = false;
            }
        }

        if (Minecraft.getInstance().player != null) {
            LocalPlayer player = Minecraft.getInstance().player;
            Camera camera = Minecraft.getInstance().gameRenderer.getMainCamera();
            biomeFogStartDecrement -= 0.5f;
            biomeFogEndDecrement -= 0.5f;
            Holder<Biome> biomeHolder = player.level().getBiome(player.blockPosition());
            if (camera.getFluidInCamera() == FogType.NONE && player.level().getBlockState(camera.getBlockPosition()).getFluidState().isEmpty()) {
                if (biomeHolder.is(ESBiomes.STARLIGHT_PERMAFROST_FOREST)) {
                    biomeFogStartDecrement += (0.5f + 0.75f);
                    biomeFogEndDecrement += (0.5f + 0.5f);
                    biomeFogEndDecrement = Mth.clamp(biomeFogEndDecrement, 0, 80);
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

    public static Vec3 computeCameraAngles(Vec3 angles) {
        LocalPlayer localPlayer = Minecraft.getInstance().player;
        float partialTicks = Minecraft.getInstance().getFrameTime();

        if (localPlayer != null) {
            float ticks = localPlayer.tickCount + partialTicks;
            if (!Minecraft.getInstance().isPaused()) {
                float shakeAmount = 0;
                if (Minecraft.getInstance().level != null) {
                    for (CameraShake cameraShake : Minecraft.getInstance().level.getEntitiesOfClass(CameraShake.class, localPlayer.getBoundingBox().inflate(50))) {
                        if (cameraShake.distanceTo(localPlayer) < cameraShake.getRadius()) {
                            shakeAmount += cameraShake.getShakeAmount(localPlayer, partialTicks);
                        }
                    }
                }
                shakeAmount = Math.min(shakeAmount, 1);

                return new Vec3(angles.x + shakeAmount * Math.cos(ticks * 3) * 20, angles.y + shakeAmount * Math.cos(ticks * 4) * 20, angles.z + shakeAmount * Math.cos(ticks * 5) * 20);
            }
        }
        return angles;
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

        if (fogMode == FogRenderer.FogMode.FOG_TERRAIN) {
            biomeFogStartDecrement = Mth.clamp(biomeFogStartDecrement, 0, RenderSystem.getShaderFogStart() + 5);
            biomeFogEndDecrement = Mth.clamp(biomeFogEndDecrement, 0, RenderSystem.getShaderFogEnd() - 50);
            RenderSystem.setShaderFogStart(RenderSystem.getShaderFogStart() - biomeFogStartDecrement);
            RenderSystem.setShaderFogEnd(RenderSystem.getShaderFogEnd() - biomeFogEndDecrement);

            Holder<Biome> biomeHolder = player.level().getBiome(player.blockPosition());
            if (camera.getFluidInCamera() == FogType.NONE && player.level().getBlockState(camera.getBlockPosition()).getFluidState().isEmpty()) {
                if (biomeHolder.is(ESBiomes.STARLIGHT_PERMAFROST_FOREST)) {
                    RenderSystem.setShaderFogShape(FogShape.SPHERE);
                }
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
            case TheGatekeeper theGatekeeper ->
                    barLocation = new ResourceLocation(EternalStarlight.ID, "textures/gui/bars/the_gatekeeper.png");
            case StarlightGolem starlightGolem ->
                    barLocation = new ResourceLocation(EternalStarlight.ID, "textures/gui/bars/starlight_golem.png");
            case LunarMonstrosity lunarMonstrosity ->
                    barLocation = new ResourceLocation(EternalStarlight.ID, "textures/gui/bars/lunar_monstrosity.png");
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
        guiGraphics.blit(barLocation, x - 36, y - 32, 0.0F, 0.0F, 256, 64, 256, 64);
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

                    int l = (int)(f * 17.0F);
                    guiGraphics.blitSprite(CROSSHAIR_ATTACK_INDICATOR_BACKGROUND_SPRITE, k, j, 16, 4);
                    guiGraphics.blitSprite(CROSSHAIR_ATTACK_INDICATOR_PROGRESS_SPRITE, 16, 4, 0, 0, k, j, l, 4);
                }

                RenderSystem.defaultBlendFunc();
            }
        }
    }

    public static void renderEtherErosion(GuiGraphics guiGraphics) {
        float clientEtherTicksRaw = ESEntityUtil.getPersistentData(Minecraft.getInstance().player).getInt("ClientEtherTicks");
        float clientEtherTicks = Math.min(clientEtherTicksRaw + Minecraft.getInstance().getFrameTime(), 140f);
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
        if (player != null) {
            int usingTicks = player.getTicksUsingItem();
            float ticks = Math.min(usingTicks + Minecraft.getInstance().getFrameTime(), 150f);
            float progress = Math.min(ticks, 150f) / 150f;
            if (player.isUsingItem() && player.getUseItem().is(ESItems.ORB_OF_PROPHECY.get())) {
                if (usingTicks < 150) {
                    renderTextureOverlay(guiGraphics, ORB_OF_PROPHECY_USE, progress);
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

package cn.leolezury.eternalstarlight.event.client;

import cn.leolezury.eternalstarlight.EternalStarlight;
import cn.leolezury.eternalstarlight.entity.misc.CameraShake;
import cn.leolezury.eternalstarlight.init.BiomeInit;
import com.mojang.blaze3d.shaders.FogShape;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.Camera;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.LerpingBossEvent;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.BossEvent;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.material.FogType;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.CustomizeGuiOverlayEvent;
import net.minecraftforge.client.event.ViewportEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@OnlyIn(Dist.CLIENT)
@Mod.EventBusSubscriber(modid = EternalStarlight.MOD_ID, value = Dist.CLIENT)
public class ClientEvents {
    @SubscribeEvent
    public static void onSetupCamera(ViewportEvent.ComputeCameraAngles event) {
        Player player = Minecraft.getInstance().player;
        float delta = Minecraft.getInstance().getFrameTime();
        if (player != null) {
            float ticksExistedDelta = player.tickCount + delta;
            if (!Minecraft.getInstance().isPaused()) {
                float shakeAmplitude = 0;
                for (CameraShake cameraShake : player.level().getEntitiesOfClass(CameraShake.class, player.getBoundingBox().inflate(20, 20, 20))) {
                    if (cameraShake.distanceTo(player) < cameraShake.getRadius()) {
                        shakeAmplitude += cameraShake.getShakeAmount(player, delta);
                    }
                }
                if (shakeAmplitude > 1.0f) shakeAmplitude = 1.0f;
                event.setPitch((float) (event.getPitch() + shakeAmplitude * Math.cos(ticksExistedDelta * 3 + 2) * 25));
                event.setYaw((float) (event.getYaw() + shakeAmplitude * Math.cos(ticksExistedDelta * 5 + 1) * 25));
                event.setRoll((float) (event.getRoll() + shakeAmplitude * Math.cos(ticksExistedDelta * 4) * 25));
            }
        }
    }

    @SubscribeEvent
    public static void onRenderFog(ViewportEvent.RenderFog event) {
        Minecraft minecraft = Minecraft.getInstance();
        LocalPlayer player = minecraft.player;
        Camera camera = event.getCamera();
        if (player != null && player.level().getBiome(new BlockPos(player.getBlockX(), player.getBlockY(), player.getBlockZ())).is(BiomeInit.FROST_FOREST_KEY) && camera.getFluidInCamera() == FogType.NONE && camera.getBlockAtCamera().getFluidState().isEmpty()) {
            RenderSystem.setShaderFogStart(0.0F);
            RenderSystem.setShaderFogEnd(150.0F);
            RenderSystem.setShaderFogShape(FogShape.SPHERE);
        }
    }

    private static final ResourceLocation GUI_BARS_LOCATION = new ResourceLocation("textures/gui/bars.png");

    @SubscribeEvent
    public static void onRenderBossBar(CustomizeGuiOverlayEvent.BossEventProgress event) {
        ResourceLocation barLocation;
        LerpingBossEvent bossEvent = event.getBossEvent();
        Component bossDesc;
        if (bossEvent.getName().contains(Component.translatable("entity." + EternalStarlight.MOD_ID + ".starlight_golem"))) {
            event.setCanceled(true);
            barLocation = new ResourceLocation(EternalStarlight.MOD_ID,"textures/gui/bars/starlight_golem.png");
            bossDesc = Component.translatable("boss." + EternalStarlight.MOD_ID + ".starlight_golem.desc");
        } else if (bossEvent.getName().contains(Component.translatable("entity." + EternalStarlight.MOD_ID + ".lunar_monstrosity"))) {
            event.setCanceled(true);
            barLocation = new ResourceLocation(EternalStarlight.MOD_ID,"textures/gui/bars/lunar_monstrosity.png");
            bossDesc = Component.translatable("boss." + EternalStarlight.MOD_ID + ".lunar_monstrosity.desc");
        } else {
            return;
        }
        event.setIncrement(12 + 2 * Minecraft.getInstance().font.lineHeight);
        RenderSystem.setShaderTexture(0, GUI_BARS_LOCATION);
        drawBar(event.getGuiGraphics(), event.getX(), event.getY(), bossEvent, barLocation);
        Component component = bossEvent.getName();
        int textWidth = Minecraft.getInstance().font.width(component);
        int textX = Minecraft.getInstance().getWindow().getGuiScaledWidth() / 2 - textWidth / 2;
        int textY = event.getY() - 9;
        event.getGuiGraphics().drawString(Minecraft.getInstance().font, component, textX, textY, 16777215);
        int descWidth = Minecraft.getInstance().font.width(bossDesc);
        int descX = Minecraft.getInstance().getWindow().getGuiScaledWidth() / 2 - descWidth / 2;
        int descY = event.getY() + 9;
        event.getGuiGraphics().drawString(Minecraft.getInstance().font, bossDesc, descX, descY, 16777215);
    }

    public static void drawBar(GuiGraphics guiGraphics, int x, int y, BossEvent event, ResourceLocation barLocation) {
        drawBar(guiGraphics, x, y, event, 182, 0);
        int i = (int)(event.getProgress() * 183.0F);
        if (i > 0) {
            drawBar(guiGraphics, x, y, event, i, 5);
        }
        RenderSystem.setShaderTexture(0, barLocation);
        guiGraphics.blit(barLocation, x, y - 1, 0.0F, 0.0F, 184, 7, 184, 7);
    }

    private static void drawBar(GuiGraphics guiGraphics, int x, int y, BossEvent event, int width, int i) {
        guiGraphics.blit(GUI_BARS_LOCATION, x, y, 0, event.getColor().ordinal() * 5 * 2 + i, width, 5);
        if (event.getOverlay() != BossEvent.BossBarOverlay.PROGRESS) {
            RenderSystem.enableBlend();
            guiGraphics.blit(GUI_BARS_LOCATION, x, y, 0, 80 + (event.getOverlay().ordinal() - 1) * 5 * 2 + i, width, 5);
            RenderSystem.disableBlend();
        }
    }
}

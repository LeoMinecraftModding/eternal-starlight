package cn.leolezury.eternalstarlight.event.client;

import cn.leolezury.eternalstarlight.EternalStarlight;
import cn.leolezury.eternalstarlight.entity.misc.CameraShake;
import cn.leolezury.eternalstarlight.init.BiomeInit;
import com.mojang.blaze3d.shaders.FogShape;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Camera;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
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
        if (event.getBossEvent().getName().contains(Component.translatable("entity." + EternalStarlight.MOD_ID + ".starlight_golem"))) {
            event.setCanceled(true);
            barLocation = new ResourceLocation(EternalStarlight.MOD_ID,"textures/gui/bars/starlight_golem.png");
        } else if (event.getBossEvent().getName().contains(Component.translatable("entity." + EternalStarlight.MOD_ID + ".lunar_monstrosity"))) {
            event.setCanceled(true);
            barLocation = new ResourceLocation(EternalStarlight.MOD_ID,"textures/gui/bars/lunar_monstrosity.png");
        } else {
            return;
        }
        RenderSystem.setShaderTexture(0, GUI_BARS_LOCATION);
        drawBar(event.getGuiGraphics(), event.getX(), event.getY(), event.getBossEvent(), barLocation);
        Component component = event.getBossEvent().getName();
        int l = Minecraft.getInstance().font.width(component);
        int i1 = Minecraft.getInstance().getWindow().getGuiScaledWidth() / 2 - l / 2;
        int j1 = event.getY() - 9;
        event.getGuiGraphics().drawString(Minecraft.getInstance().font, component, i1, j1, 16777215);
    }

    public static void drawBar(GuiGraphics guiGraphics, int p_93708_, int p_93709_, BossEvent p_93710_, ResourceLocation barLocation) {
        drawBar(guiGraphics, p_93708_, p_93709_, p_93710_, 182, 0);
        int i = (int)(p_93710_.getProgress() * 183.0F);
        if (i > 0) {
            drawBar(guiGraphics, p_93708_, p_93709_, p_93710_, i, 5);
        }
        RenderSystem.setShaderTexture(0, barLocation);
        guiGraphics.blit(barLocation, p_93708_, p_93709_ - 1, 0.0F, 0.0F, 184, 7, 184, 7);
    }

    private static void drawBar(GuiGraphics p_281657_, int p_283675_, int p_282498_, BossEvent p_281288_, int p_283619_, int p_281636_) {
        p_281657_.blit(GUI_BARS_LOCATION, p_283675_, p_282498_, 0, p_281288_.getColor().ordinal() * 5 * 2 + p_281636_, p_283619_, 5);
        if (p_281288_.getOverlay() != BossEvent.BossBarOverlay.PROGRESS) {
            RenderSystem.enableBlend();
            p_281657_.blit(GUI_BARS_LOCATION, p_283675_, p_282498_, 0, 80 + (p_281288_.getOverlay().ordinal() - 1) * 5 * 2 + p_281636_, p_283619_, 5);
            RenderSystem.disableBlend();
        }
    }
}

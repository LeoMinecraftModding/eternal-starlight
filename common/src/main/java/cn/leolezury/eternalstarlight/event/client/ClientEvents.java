package cn.leolezury.eternalstarlight.event.client;

import cn.leolezury.eternalstarlight.EternalStarlight;
import cn.leolezury.eternalstarlight.client.sounds.CommonBossMusicInstance;
import cn.leolezury.eternalstarlight.entity.boss.AbstractSLBoss;
import cn.leolezury.eternalstarlight.entity.boss.LunarMonstrosity;
import cn.leolezury.eternalstarlight.entity.boss.StarlightGolem;
import cn.leolezury.eternalstarlight.entity.boss.TheGatekeeper;
import cn.leolezury.eternalstarlight.entity.misc.CameraShake;
import cn.leolezury.eternalstarlight.util.SLTags;
import com.mojang.blaze3d.shaders.FogShape;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.Camera;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.LerpingBossEvent;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.BossEvent;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.material.FogType;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.CustomizeGuiOverlayEvent;
import net.minecraftforge.client.event.ViewportEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.*;

@OnlyIn(Dist.CLIENT)
@Mod.EventBusSubscriber(modid = EternalStarlight.MOD_ID, value = Dist.CLIENT)
public class ClientEvents {
    public static final Set<Mob> BOSSES = Collections.newSetFromMap(new WeakHashMap<>());
    public static final Map<Integer, CommonBossMusicInstance> BOSS_SOUND_MAP = new HashMap<>();
    public static final int BOSS_MUSIC_ID = 100;

    @SubscribeEvent
    public static void onComputeCameraAngles(ViewportEvent.ComputeCameraAngles event) {
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

                event.setPitch((float) (event.getPitch() + shakeAmount * Math.cos(ticks * 3) * 20));
                event.setYaw((float) (event.getYaw() + shakeAmount * Math.cos(ticks * 4) * 20));
                event.setRoll((float) (event.getRoll() + shakeAmount * Math.cos(ticks * 5) * 20));
            }
        }
    }

    @SubscribeEvent
    public static void onRenderFog(ViewportEvent.RenderFog event) {
        Minecraft minecraft = Minecraft.getInstance();
        LocalPlayer player = minecraft.player;
        Camera camera = event.getCamera();
        if (player == null) {
            return;
        }
        Holder<Biome> biomeHolder = player.level().getBiome(new BlockPos(player.getBlockX(), player.getBlockY(), player.getBlockZ()));
        if (camera.getFluidInCamera() == FogType.NONE && camera.getBlockAtCamera().getFluidState().isEmpty()) {
            if (biomeHolder.is(SLTags.Biomes.PERMAFROST_FOREST_VARIANT)) {
                RenderSystem.setShaderFogStart(-4.0F);
                RenderSystem.setShaderFogEnd(96.0F);
                RenderSystem.setShaderFogColor(0.87f, 0.87f, 1f);
            } else if (biomeHolder.is(SLTags.Biomes.DARK_SWAMP_VARIANT)) {
                RenderSystem.setShaderFogStart(-1.0F);
                RenderSystem.setShaderFogEnd(96.0F);
                RenderSystem.setShaderFogColor(0.07f, 0, 0.07f);
            }
            RenderSystem.setShaderFogShape(FogShape.SPHERE);
        }
    }

    public static void handleEntityEvent(Entity entity, Byte id) {
        float volume = Minecraft.getInstance().options.getSoundSourceVolume(SoundSource.RECORDS);

        if (entity instanceof AbstractSLBoss boss && entity.isAlive() && id == BOSS_MUSIC_ID) {
            if (volume <= 0.0F) {
                BOSS_SOUND_MAP.clear();
            } else {
                CommonBossMusicInstance sound;
                if (BOSS_SOUND_MAP.get(entity.getId()) == null) {
                    sound = new CommonBossMusicInstance(boss);
                    BOSS_SOUND_MAP.put(entity.getId(), sound);
                } else {
                    sound = BOSS_SOUND_MAP.get(entity.getId());
                }

                if (!Minecraft.getInstance().getSoundManager().isActive(sound) && sound.isNearest()) {
                    Minecraft.getInstance().getSoundManager().stop();
                    Minecraft.getInstance().getSoundManager().play(sound);
                }
            }
        }
    }

    private static final ResourceLocation GUI_BARS_LOCATION = new ResourceLocation("textures/gui/bars.png");

    @SubscribeEvent
    public static void onRenderBossBar(CustomizeGuiOverlayEvent.BossEventProgress event) {
        ResourceLocation barLocation;
        LerpingBossEvent bossEvent = event.getBossEvent();
        Mob boss = null;
        if (BOSSES.isEmpty()) {
            return;
        }
        for (Mob mob : BOSSES) {
            if (mob.getUUID().equals(bossEvent.getId())) {
                boss = mob;
                break;
            }
        }
        if (boss == null) {
            return;
        }
        Component bossDesc;
        if (boss instanceof TheGatekeeper) {
            event.setCanceled(true);
            barLocation = new ResourceLocation(EternalStarlight.MOD_ID,"textures/gui/bars/the_gatekeeper.png");
            bossDesc = Component.translatable("boss." + EternalStarlight.MOD_ID + ".the_gatekeeper.desc");
        } else if (boss instanceof StarlightGolem) {
            event.setCanceled(true);
            barLocation = new ResourceLocation(EternalStarlight.MOD_ID,"textures/gui/bars/starlight_golem.png");
            bossDesc = Component.translatable("boss." + EternalStarlight.MOD_ID + ".starlight_golem.desc");
        } else if (boss instanceof LunarMonstrosity) {
            event.setCanceled(true);
            barLocation = new ResourceLocation(EternalStarlight.MOD_ID,"textures/gui/bars/lunar_monstrosity.png");
            bossDesc = Component.translatable("boss." + EternalStarlight.MOD_ID + ".lunar_monstrosity.desc");
        } else {
            return;
        }
        //event.setIncrement(12 + 2 * Minecraft.getInstance().font.lineHeight);
        RenderSystem.setShaderTexture(0, GUI_BARS_LOCATION);
        drawBar(event.getGuiGraphics(), event.getX(), event.getY(), bossEvent, barLocation);
        Component component = bossEvent.getName();
        int textWidth = Minecraft.getInstance().font.width(component);
        int textX = Minecraft.getInstance().getWindow().getGuiScaledWidth() / 2 - textWidth / 2;
        int textY = event.getY() - 9;
        event.getGuiGraphics().drawString(Minecraft.getInstance().font, component, textX, textY, 16777215);
        /*int descWidth = Minecraft.getInstance().font.width(bossDesc);
        int descX = Minecraft.getInstance().getWindow().getGuiScaledWidth() / 2 - descWidth / 2;
        int descY = event.getY() + 9;
        event.getGuiGraphics().drawString(Minecraft.getInstance().font, bossDesc, descX, descY, 16777215);*/
    }

    public static void drawBar(GuiGraphics guiGraphics, int x, int y, BossEvent event, ResourceLocation barLocation) {
        drawBar(guiGraphics, x, y, event, 182, 0);
        int i = (int)(event.getProgress() * 183.0F);
        if (i > 0) {
            drawBar(guiGraphics, x, y, event, i, 5);
        }
        RenderSystem.setShaderTexture(0, barLocation);
        guiGraphics.blit(barLocation, x - 36, y - 32, 0.0F, 0.0F, 256, 64, 256, 64);
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

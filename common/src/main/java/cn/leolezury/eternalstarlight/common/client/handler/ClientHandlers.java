package cn.leolezury.eternalstarlight.common.client.handler;

import cn.leolezury.eternalstarlight.common.EternalStarlight;
import cn.leolezury.eternalstarlight.common.client.gui.screens.CrestSelectionScreen;
import cn.leolezury.eternalstarlight.common.client.sounds.CommonBossMusicInstance;
import cn.leolezury.eternalstarlight.common.entity.boss.ESBoss;
import cn.leolezury.eternalstarlight.common.entity.boss.LunarMonstrosity;
import cn.leolezury.eternalstarlight.common.entity.boss.StarlightGolem;
import cn.leolezury.eternalstarlight.common.entity.boss.TheGatekeeper;
import cn.leolezury.eternalstarlight.common.entity.misc.CameraShake;
import cn.leolezury.eternalstarlight.common.init.BlockInit;
import cn.leolezury.eternalstarlight.common.init.FluidInit;
import cn.leolezury.eternalstarlight.common.init.ItemInit;
import cn.leolezury.eternalstarlight.common.platform.ESPlatform;
import cn.leolezury.eternalstarlight.common.util.BlockUtil;
import cn.leolezury.eternalstarlight.common.util.ESUtil;
import com.mojang.blaze3d.shaders.FogShape;
import com.mojang.blaze3d.systems.RenderSystem;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.Camera;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.LerpingBossEvent;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.world.BossEvent;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.phys.Vec3;

import java.util.*;

@Environment(EnvType.CLIENT)
public class ClientHandlers {
    public static final Set<Mob> BOSSES = Collections.newSetFromMap(new WeakHashMap<>());
    public static final Map<Integer, CommonBossMusicInstance> BOSS_SOUND_MAP = new HashMap<>();
    public static final int BOSS_MUSIC_ID = 100;
    private static final ResourceLocation[] BAR_BACKGROUND_SPRITES = new ResourceLocation[]{new ResourceLocation("boss_bar/pink_background"), new ResourceLocation("boss_bar/blue_background"), new ResourceLocation("boss_bar/red_background"), new ResourceLocation("boss_bar/green_background"), new ResourceLocation("boss_bar/yellow_background"), new ResourceLocation("boss_bar/purple_background"), new ResourceLocation("boss_bar/white_background")};
    private static final ResourceLocation[] BAR_PROGRESS_SPRITES = new ResourceLocation[]{new ResourceLocation("boss_bar/pink_progress"), new ResourceLocation("boss_bar/blue_progress"), new ResourceLocation("boss_bar/red_progress"), new ResourceLocation("boss_bar/green_progress"), new ResourceLocation("boss_bar/yellow_progress"), new ResourceLocation("boss_bar/purple_progress"), new ResourceLocation("boss_bar/white_progress")};
    private static final ResourceLocation[] OVERLAY_BACKGROUND_SPRITES = new ResourceLocation[]{new ResourceLocation("boss_bar/notched_6_background"), new ResourceLocation("boss_bar/notched_10_background"), new ResourceLocation("boss_bar/notched_12_background"), new ResourceLocation("boss_bar/notched_20_background")};
    private static final ResourceLocation[] OVERLAY_PROGRESS_SPRITES = new ResourceLocation[]{new ResourceLocation("boss_bar/notched_6_progress"), new ResourceLocation("boss_bar/notched_10_progress"), new ResourceLocation("boss_bar/notched_12_progress"), new ResourceLocation("boss_bar/notched_20_progress")};
    private static final ResourceLocation ETHER_EROSION_OVERLAY = new ResourceLocation(EternalStarlight.MOD_ID, "textures/misc/ether_erosion.png");
    private static final ResourceLocation ETHER_ARMOR_EMPTY = new ResourceLocation(EternalStarlight.MOD_ID, "textures/gui/hud/ether_armor_empty.png");
    private static final ResourceLocation ETHER_ARMOR_HALF = new ResourceLocation(EternalStarlight.MOD_ID, "textures/gui/hud/ether_armor_half.png");
    private static final ResourceLocation ETHER_ARMOR_FULL = new ResourceLocation(EternalStarlight.MOD_ID, "textures/gui/hud/ether_armor_full.png");
    private static final ResourceLocation PROPHET_ORB_USE = new ResourceLocation(EternalStarlight.MOD_ID, "textures/misc/prophet_orb_use.png");

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
    public static void onRenderFog(Camera camera) {
        Minecraft minecraft = Minecraft.getInstance();
        LocalPlayer player = minecraft.player;
        if (player == null) {
            return;
        }
        if (ESPlatform.INSTANCE.getLoader() == ESPlatform.Loader.FABRIC) {
            FluidState fluidState = camera.getEntity().level().getFluidState(camera.getBlockPosition());
            if (fluidState.is(FluidInit.ETHER_STILL.get()) || fluidState.is(FluidInit.ETHER_FLOWING.get())) {
                if (camera.getPosition().y < (double) ((float) camera.getBlockPosition().getY() + fluidState.getHeight(camera.getEntity().level(), camera.getBlockPosition()))) {
                    RenderSystem.setShaderFogStart(0.0F);
                    RenderSystem.setShaderFogEnd(3.0F);
                    RenderSystem.setShaderFogColor(232 / 255F, 255 / 255F, 222 / 255F);
                    RenderSystem.setShaderFogShape(FogShape.SPHERE);
                }
            }
        }
        /*Holder<Biome> biomeHolder = player.level().getBiome(new BlockPos(player.getBlockX(), player.getBlockY(), player.getBlockZ()));
        boolean noFluidAtCam = player.level().getBlockState(camera.getBlockPosition()).getFluidState().isEmpty();
        if (camera.getFluidInCamera() == FogType.NONE && noFluidAtCam) {
            if (biomeHolder.is(ESTags.Biomes.PERMAFROST_FOREST_VARIANT)) {
                RenderSystem.setShaderFogStart(-4.0F);
                RenderSystem.setShaderFogEnd(96.0F);
                RenderSystem.setShaderFogColor(0.87f, 0.87f, 1f);
            } else if (biomeHolder.is(ESTags.Biomes.DARK_SWAMP_VARIANT)) {
                RenderSystem.setShaderFogStart(-1.0F);
                RenderSystem.setShaderFogEnd(96.0F);
                RenderSystem.setShaderFogColor(0.07f, 0, 0.07f);
            }
            RenderSystem.setShaderFogShape(FogShape.SPHERE);
        }*/
    }

    public static void handleEntityEvent(Entity entity, Byte id) {
        float volume = Minecraft.getInstance().options.getSoundSourceVolume(SoundSource.RECORDS);

        if (entity instanceof ESBoss boss && entity.isAlive() && id == BOSS_MUSIC_ID) {
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
        if (boss == null) {
            return false;
        }
        //Component bossDesc;
        if (boss instanceof TheGatekeeper) {
            barLocation = new ResourceLocation(EternalStarlight.MOD_ID,"textures/gui/bars/the_gatekeeper.png");
            //bossDesc = Component.translatable("boss." + EternalStarlight.MOD_ID + ".the_gatekeeper.desc");
        } else if (boss instanceof StarlightGolem) {
            barLocation = new ResourceLocation(EternalStarlight.MOD_ID,"textures/gui/bars/starlight_golem.png");
            //bossDesc = Component.translatable("boss." + EternalStarlight.MOD_ID + ".starlight_golem.desc");
        } else if (boss instanceof LunarMonstrosity) {
            barLocation = new ResourceLocation(EternalStarlight.MOD_ID,"textures/gui/bars/lunar_monstrosity.png");
            //bossDesc = Component.translatable("boss." + EternalStarlight.MOD_ID + ".lunar_monstrosity.desc");
        } else {
            return false;
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
        RenderSystem.setShaderTexture(0, barLocation);
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

    public static void renderEtherErosion(Gui gui, GuiGraphics guiGraphics) {
        float clientEtherTicksRaw = ESUtil.getPersistentData(Minecraft.getInstance().player).getInt("ClientEtherTicks");
        float clientEtherTicks = Math.min(clientEtherTicksRaw + Minecraft.getInstance().getFrameTime(), 140f);
        float erosionProgress = Math.min(clientEtherTicks, 140f) / 140f;
        if (clientEtherTicksRaw > 0) {
            gui.renderTextureOverlay(guiGraphics, ETHER_EROSION_OVERLAY, erosionProgress);
        }
    }

    public static void renderEtherArmor(GuiGraphics guiGraphics, int screenWidth, int screenHeight) {
        Minecraft minecraft = Minecraft.getInstance();
        if (minecraft.player.level().isClientSide && BlockUtil.isEntityInBlock(minecraft.player, BlockInit.ETHER.get())) {
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

    public static void renderProphetOrbUse(Gui gui, GuiGraphics guiGraphics) {
        LocalPlayer player = Minecraft.getInstance().player;
        if (player != null) {
            int usingTicks = player.getTicksUsingItem();
            float ticks = Math.min(usingTicks + Minecraft.getInstance().getFrameTime(), 140f);
            float progress = Math.min(ticks, 140f) / 140f;
            if (player.isUsingItem() && player.getUseItem().is(ItemInit.PROPHET_ORB.get())) {
                if (usingTicks < 140) {
                    gui.renderTextureOverlay(guiGraphics, PROPHET_ORB_USE, progress);
                } else {
                    Minecraft.getInstance().setScreen(new CrestSelectionScreen(player));
                }
            }
        }
    }
}

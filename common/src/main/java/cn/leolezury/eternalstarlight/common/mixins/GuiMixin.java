package cn.leolezury.eternalstarlight.common.mixins;

import cn.leolezury.eternalstarlight.common.EternalStarlight;
import cn.leolezury.eternalstarlight.common.init.BlockInit;
import cn.leolezury.eternalstarlight.common.util.BlockUtil;
import cn.leolezury.eternalstarlight.common.util.ESUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.ai.attributes.Attributes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Gui.class)
public abstract class GuiMixin {
    @Shadow protected abstract void renderTextureOverlay(GuiGraphics guiGraphics, ResourceLocation resourceLocation, float f);

    @Shadow
    private int screenWidth;

    @Shadow
    private int screenHeight;

    @Shadow
    private int displayHealth;

    @Unique
    private static final ResourceLocation ETHER_EROSION_OVERLAY = new ResourceLocation(EternalStarlight.MOD_ID, "textures/misc/ether_erosion.png");

    @Unique
    private final static ResourceLocation ETHER_ARMOR_EMPTY = new ResourceLocation(EternalStarlight.MOD_ID, "hud/ether_armor_empty.png");

    @Unique
    private final static ResourceLocation ETHER_ARMOR_HALF = new ResourceLocation(EternalStarlight.MOD_ID, "hud/ether_armor_half.png");

    @Unique
    private final static ResourceLocation ETHER_ARMOR_FULL = new ResourceLocation(EternalStarlight.MOD_ID, "hud/ether_armor_full.png");

    @Inject(method = "render", at = @At("TAIL"))
    private void es_render(GuiGraphics guiGraphics, float f, CallbackInfo ci) {
        float clientEtherTicks = Math.min(ESUtil.getPersistentData(Minecraft.getInstance().player).getInt("ClientEtherTicks") + Minecraft.getInstance().getFrameTime(), 140f);
        float erosionProgress = Math.min(clientEtherTicks, 140f) / 140f;
        if (clientEtherTicks > 0) {
            renderTextureOverlay(guiGraphics, ETHER_EROSION_OVERLAY, erosionProgress);
        }
    }
    
    @Inject(method = "renderPlayerHealth", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/Minecraft;getProfiler()Lnet/minecraft/util/profiling/ProfilerFiller;", ordinal = 1, shift = At.Shift.BEFORE))
    public void es_renderPlayerHealth(GuiGraphics guiGraphics, CallbackInfo ci) {
        Minecraft minecraft = Minecraft.getInstance();
        if (minecraft.player.level().isClientSide && BlockUtil.isEntityInBlock(minecraft.player, BlockInit.ETHER.get())) {
            minecraft.getProfiler().push("armor");
            int x;
            int i = Mth.ceil(minecraft.player.getHealth());
            int j = this.displayHealth;
            int m = this.screenWidth / 2 - 91;
            int o = this.screenHeight - 39;
            float f = Math.max((float) minecraft.player.getAttributeValue(Attributes.MAX_HEALTH), (float) Math.max(j, i));
            int p = Mth.ceil(minecraft.player.getAbsorptionAmount());
            int q = Mth.ceil((f + (float) p) / 2.0F / 10.0F);
            int r = Math.max(10 - (q - 2), 3);
            int u = minecraft.player.getArmorValue();
            int s = o - (q - 1) * r - 10;
            for (int w = 0; w < 10; ++w) {
                if (u > 0) {
                    x = m + w * 8;
                    if (w * 2 + 1 < u) {
                        guiGraphics.blitSprite(ETHER_ARMOR_FULL, x, s, 9, 9);
                    }

                    if (w * 2 + 1 == u) {
                        guiGraphics.blitSprite(ETHER_ARMOR_HALF, x, s, 9, 9);
                    }

                    if (w * 2 + 1 > u) {
                        guiGraphics.blitSprite(ETHER_ARMOR_EMPTY, x, s, 9, 9);
                    }
                }
            }
        }
    }
}


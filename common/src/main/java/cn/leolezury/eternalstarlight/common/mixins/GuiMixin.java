package cn.leolezury.eternalstarlight.common.mixins;

import cn.leolezury.eternalstarlight.common.EternalStarlight;
import cn.leolezury.eternalstarlight.common.util.BlockUtil;
import cn.leolezury.eternalstarlight.common.init.BlockInit;
import cn.leolezury.eternalstarlight.common.util.ESUtil;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.resources.ResourceLocation;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Gui.class)
public abstract class GuiMixin {
    @Shadow
    private final Minecraft minecraft;
    protected GuiMixin(Minecraft minecraft) {
        this.minecraft = minecraft;
    }
    ResourceLocation ETHER_ERODE_OVERLAY = new ResourceLocation(EternalStarlight.MOD_ID,"textures/misc/ether_erode.png");
    @Inject(method = "render", at = @At(value = "TAIL"))
    public void injectRender(GuiGraphics guiGraphics, float f, CallbackInfo ci) {
        int s = 140;
        int clientEtherTicks = ESUtil.getPersistentData(minecraft.player).getInt("ClientEtherTicks");
        float getPercentErode = (float)Math.min(clientEtherTicks, s) / (float)s;
        if (BlockUtil.isEntityInBlock(minecraft.player, BlockInit.ETHER.get())) {
            ((Gui)(Object)this).renderTextureOverlay(guiGraphics, this.ETHER_ERODE_OVERLAY, getPercentErode);
        } else {
            if (clientEtherTicks > 0) {
                ESUtil.getPersistentData(minecraft.player).putInt("ClientEtherTicks", clientEtherTicks - 1);
            }
            ((Gui)(Object)this).renderTextureOverlay(guiGraphics, this.ETHER_ERODE_OVERLAY, getPercentErode);
        }
    }

}

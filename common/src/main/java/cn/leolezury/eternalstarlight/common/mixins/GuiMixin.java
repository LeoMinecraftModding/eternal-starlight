package cn.leolezury.eternalstarlight.common.mixins;

import cn.leolezury.eternalstarlight.common.EternalStarlight;
import cn.leolezury.eternalstarlight.common.init.BlockInit;
import cn.leolezury.eternalstarlight.common.util.BlockUtil;
import cn.leolezury.eternalstarlight.common.util.ESUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.resources.ResourceLocation;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Gui.class)
public abstract class GuiMixin {
    @Shadow protected abstract void renderTextureOverlay(GuiGraphics guiGraphics, ResourceLocation resourceLocation, float f);

    @Unique
    private static final ResourceLocation ETHER_ERODE_OVERLAY = new ResourceLocation(EternalStarlight.MOD_ID, "textures/misc/ether_erode.png");

    @Inject(method = "render", at = @At("TAIL"))
    private void es_render(GuiGraphics guiGraphics, float f, CallbackInfo ci) {
        int clientEtherTicks = ESUtil.getPersistentData(Minecraft.getInstance().player).getInt("ClientEtherTicks");
        float erodePercentage = Math.min(clientEtherTicks, 140f) / 140f;
        if (BlockUtil.isEntityInBlock(Minecraft.getInstance().player, BlockInit.ETHER.get())) {
            renderTextureOverlay(guiGraphics, ETHER_ERODE_OVERLAY, erodePercentage);
        } else {
            if (clientEtherTicks > 0) {
                ESUtil.getPersistentData(Minecraft.getInstance().player).putInt("ClientEtherTicks", clientEtherTicks - 1);
            }
            renderTextureOverlay(guiGraphics, ETHER_ERODE_OVERLAY, erodePercentage);
        }
    }
}

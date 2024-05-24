package cn.leolezury.eternalstarlight.fabric.mixins;

import cn.leolezury.eternalstarlight.common.client.handler.ClientHandlers;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiGraphics;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Gui.class)
public abstract class GuiMixin {
    @Inject(method = "renderCameraOverlays", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/player/LocalPlayer;getTicksFrozen()I", shift = At.Shift.BEFORE))
    private void renderCameraOverlays(GuiGraphics guiGraphics, float f, CallbackInfo ci) {
        ClientHandlers.renderSpellCrosshair(guiGraphics, guiGraphics.guiWidth(), guiGraphics.guiHeight());
        ClientHandlers.renderEtherErosion(guiGraphics);
        ClientHandlers.renderOrbOfProphecyUse(guiGraphics);
        ClientHandlers.renderDreamCatcher(guiGraphics);
    }

    @Inject(method = "renderPlayerHealth", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/Minecraft;getProfiler()Lnet/minecraft/util/profiling/ProfilerFiller;", ordinal = 1, shift = At.Shift.BEFORE))
    public void renderPlayerHealth(GuiGraphics guiGraphics, CallbackInfo ci) {
        ClientHandlers.renderEtherArmor(guiGraphics, guiGraphics.guiWidth(), guiGraphics.guiHeight());
    }
}
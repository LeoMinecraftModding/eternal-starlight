package cn.leolezury.eternalstarlight.fabric.mixins;

import cn.leolezury.eternalstarlight.common.client.handler.ClientHandlers;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiGraphics;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Gui.class)
public abstract class GuiMixin {
    @Shadow
    private int screenWidth;
    @Shadow
    private int screenHeight;

    @Inject(method = "render", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/player/LocalPlayer;getTicksFrozen()I", shift = At.Shift.BEFORE))
    private void es_render(GuiGraphics guiGraphics, float f, CallbackInfo ci) {
        ClientHandlers.renderEtherErosion(((Gui) (Object) this), guiGraphics);
        ClientHandlers.renderOrbOfProphecyUse(((Gui) (Object) this), guiGraphics);
        ClientHandlers.renderDreamCatcher(guiGraphics);
        ClientHandlers.renderCrystallineInfection(guiGraphics);
    }

    @Inject(method = "renderPlayerHealth", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/Minecraft;getProfiler()Lnet/minecraft/util/profiling/ProfilerFiller;", ordinal = 1, shift = At.Shift.BEFORE))
    public void es_renderPlayerHealth(GuiGraphics guiGraphics, CallbackInfo ci) {
        ClientHandlers.renderEtherArmor(guiGraphics, screenWidth, screenHeight);
    }
}
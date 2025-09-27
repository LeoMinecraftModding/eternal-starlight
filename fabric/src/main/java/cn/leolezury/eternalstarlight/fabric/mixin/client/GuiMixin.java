package cn.leolezury.eternalstarlight.fabric.mixin.client;

import cn.leolezury.eternalstarlight.common.client.handler.ClientHandlers;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.mojang.blaze3d.systems.RenderSystem;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.DeltaTracker;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.resources.ResourceLocation;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Environment(EnvType.CLIENT)
@Mixin(Gui.class)
public abstract class GuiMixin {
	@Inject(method = "renderCameraOverlays", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/player/LocalPlayer;getTicksFrozen()I", shift = At.Shift.AFTER))
	private void renderCameraOverlays(GuiGraphics guiGraphics, DeltaTracker deltaTracker, CallbackInfo ci) {
		ClientHandlers.renderSpellCrosshair(guiGraphics, guiGraphics.guiWidth(), guiGraphics.guiHeight());
		ClientHandlers.renderEtherErosion(guiGraphics);
		ClientHandlers.renderOrbOfProphecyUse(guiGraphics);
		ClientHandlers.renderDreamCatcher(guiGraphics);
		ClientHandlers.renderCurrentCrest(guiGraphics);
		ClientHandlers.renderCarvedLunarisCactusFruitBlur(guiGraphics);
		ClientHandlers.renderPortalOverlay(guiGraphics);
	}

	@Inject(method = "renderPlayerHealth", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/Minecraft;getProfiler()Lnet/minecraft/util/profiling/ProfilerFiller;", ordinal = 1, shift = At.Shift.AFTER))
	public void renderPlayerHealth(GuiGraphics guiGraphics, CallbackInfo ci) {
		ClientHandlers.renderEtherArmor(guiGraphics, guiGraphics.guiWidth(), guiGraphics.guiHeight());
	}

	@Inject(method = "renderPlayerHealth", at = @At(value = "INVOKE", target = "Lcom/mojang/blaze3d/systems/RenderSystem;enableBlend()V", remap = false))
	public void renderPlayerHealthBeforeAir(GuiGraphics guiGraphics, CallbackInfo ci) {
		ClientHandlers.setAirBubbleColor();
	}

	@Inject(method = "renderPlayerHealth", at = @At(value = "INVOKE", target = "Lcom/mojang/blaze3d/systems/RenderSystem;disableBlend()V", remap = false))
	public void renderPlayerHealthAfterAir(GuiGraphics guiGraphics, CallbackInfo ci) {
		RenderSystem.setShaderColor(1, 1, 1, 1);
	}

	@WrapOperation(method = "renderPlayerHealth", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/GuiGraphics;blitSprite(Lnet/minecraft/resources/ResourceLocation;IIII)V"))
	public void renderPlayerHealthOffsetAirBubbles(GuiGraphics instance, ResourceLocation location, int x, int y, int width, int height, Operation<Void> original) {
		original.call(instance, location, x, y + ClientHandlers.getAirBubbleYOffset(), width, height);
	}
}
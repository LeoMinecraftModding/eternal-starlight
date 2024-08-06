package cn.leolezury.eternalstarlight.common.mixin.client;

import cn.leolezury.eternalstarlight.common.client.handler.ClientHandlers;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.client.renderer.FogRenderer;
import net.minecraft.util.Mth;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(FogRenderer.class)
public abstract class FogRendererMixin {
	/*@Shadow
	private static float fogRed;

	@Shadow
	private static float fogGreen;

	@Shadow
	private static float fogBlue;

	@Inject(method = "setupColor", at = @At(value = "INVOKE", target = "Lcom/mojang/blaze3d/systems/RenderSystem;clearColor(FFFF)V", ordinal = 1, remap = false, shift = At.Shift.BEFORE))
	private static void setupColor(Camera camera, float partialTicks, ClientLevel level, int renderDistanceChunks, float bossColorModifier, CallbackInfo ci) {
		if (camera.getFluidInCamera() == FogType.WATER) {
			float modifier = Mth.lerp(partialTicks, ClientHandlers.oldAbyssalFogColorModifier, ClientHandlers.abyssalFogColorModifier);
			fogRed *= modifier;
			fogGreen *= modifier;
			fogBlue *= modifier;
		}
	}*/

	@WrapOperation(method = "setupColor", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/player/LocalPlayer;getWaterVision()F"))
	private static float setupColor(LocalPlayer instance, Operation<Float> original) {
		float modifier = Mth.lerp(Minecraft.getInstance().getTimer().getGameTimeDeltaPartialTick(Minecraft.getInstance().level != null && Minecraft.getInstance().level.tickRateManager().runsNormally()), ClientHandlers.oldAbyssalFogModifier, ClientHandlers.abyssalFogModifier);
		return Mth.lerp(modifier, 0, original.call(instance));
	}
}

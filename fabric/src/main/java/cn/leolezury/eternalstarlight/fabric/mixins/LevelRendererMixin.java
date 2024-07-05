package cn.leolezury.eternalstarlight.fabric.mixins;

import cn.leolezury.eternalstarlight.common.client.handler.ClientHandlers;
import cn.leolezury.eternalstarlight.common.client.renderer.world.ESWeatherRenderer;
import cn.leolezury.eternalstarlight.common.data.ESDimensions;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.Camera;
import net.minecraft.client.DeltaTracker;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.client.renderer.LevelRenderer;
import net.minecraft.client.renderer.LightTexture;
import org.jetbrains.annotations.Nullable;
import org.joml.Matrix4f;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Environment(EnvType.CLIENT)
@Mixin(LevelRenderer.class)
public abstract class LevelRendererMixin {
	@Shadow
	private @Nullable ClientLevel level;
	@Shadow
	private int ticks;

	@Inject(method = "renderLevel", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/particle/ParticleEngine;render(Lnet/minecraft/client/renderer/LightTexture;Lnet/minecraft/client/Camera;F)V", shift = At.Shift.AFTER))
	private void renderLevelAfterParticles(DeltaTracker deltaTracker, boolean bl, Camera camera, GameRenderer gameRenderer, LightTexture lightTexture, Matrix4f matrix4f, Matrix4f matrix4f2, CallbackInfo ci) {
		ClientHandlers.onAfterRenderParticles();
	}

	@Inject(method = "renderLevel", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/LevelRenderer;renderSnowAndRain(Lnet/minecraft/client/renderer/LightTexture;FDDD)V", shift = At.Shift.AFTER))
	private void renderLevelAfterWeather(DeltaTracker deltaTracker, boolean bl, Camera camera, GameRenderer gameRenderer, LightTexture lightTexture, Matrix4f matrix4f, Matrix4f matrix4f2, CallbackInfo ci) {
		ClientHandlers.onAfterRenderWeather();
	}

	/*@Inject(method = "renderSky", at = @At(value = "HEAD"), cancellable = true)
	private void renderSky(Matrix4f matrix4f, Matrix4f matrix4f2, float f, Camera camera, boolean bl, Runnable runnable, CallbackInfo ci) {
		if (level.dimension() == ESDimensions.STARLIGHT_KEY) {
			ESSkyRenderer.renderSky(level, matrix4f, matrix4f2, f, camera, runnable);
			ci.cancel();
		}
	}*/

	@Inject(method = "renderSnowAndRain", at = @At(value = "HEAD"), cancellable = true)
	private void renderSnowAndRain(LightTexture lightTexture, float partialTick, double camX, double camY, double camZ, CallbackInfo ci) {
		if (level != null && level.dimension() == ESDimensions.STARLIGHT_KEY) {
			if (ESWeatherRenderer.renderCustomWeather(level, ticks, partialTick, lightTexture, camX, camY, camZ)) {
				ci.cancel();
			}
		}
	}
}

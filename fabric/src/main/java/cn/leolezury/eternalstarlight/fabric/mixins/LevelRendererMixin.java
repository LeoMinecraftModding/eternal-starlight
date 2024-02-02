package cn.leolezury.eternalstarlight.fabric.mixins;

import cn.leolezury.eternalstarlight.common.client.renderer.world.ESSkyRenderer;
import cn.leolezury.eternalstarlight.common.client.renderer.world.ESWeatherRenderer;
import cn.leolezury.eternalstarlight.common.data.DimensionInit;
import com.mojang.blaze3d.vertex.PoseStack;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.Camera;
import net.minecraft.client.multiplayer.ClientLevel;
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
    @Shadow private @Nullable ClientLevel level;
    @Shadow private int ticks;


    @Inject(method = "renderSky", at = @At(value = "HEAD"), cancellable = true)
    private void es_renderSky(PoseStack poseStack, Matrix4f matrix4f, float f, Camera camera, boolean bl, Runnable runnable, CallbackInfo ci) {
        if (level.dimension() == DimensionInit.STARLIGHT_KEY) {
            ESSkyRenderer.renderSky(level, poseStack, matrix4f, f, camera, runnable);
            ci.cancel();
        }
    }

    @Inject(method = "renderSnowAndRain", at = @At(value = "HEAD"), cancellable = true)
    private void es_renderSnowAndRain(LightTexture lightTexture, float partialTick, double camX, double camY, double camZ, CallbackInfo ci) {
        if (level.dimension() == DimensionInit.STARLIGHT_KEY) {
            if (ESWeatherRenderer.renderCustomWeather(level, ticks, partialTick, lightTexture, camX, camY, camZ)) {
                ci.cancel();
            }
        }
    }
}

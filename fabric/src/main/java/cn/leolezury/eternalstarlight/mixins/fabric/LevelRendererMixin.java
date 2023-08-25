package cn.leolezury.eternalstarlight.mixins.fabric;

import cn.leolezury.eternalstarlight.client.renderer.world.ESSkyRenderer;
import cn.leolezury.eternalstarlight.datagen.DimensionInit;
import com.mojang.blaze3d.vertex.PoseStack;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.Camera;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.renderer.LevelRenderer;
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

    @Inject(method = "renderSky", at = @At(value = "HEAD"), cancellable = true)
    private void renderSky(PoseStack poseStack, Matrix4f matrix4f, float f, Camera camera, boolean bl, Runnable runnable, CallbackInfo ci) {
        if (level.dimension() == DimensionInit.STARLIGHT_KEY) {
            ESSkyRenderer.renderSky(level, poseStack, matrix4f, f, camera, runnable);
            ci.cancel();
        }
    }
}

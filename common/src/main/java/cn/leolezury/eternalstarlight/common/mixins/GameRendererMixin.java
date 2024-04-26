package cn.leolezury.eternalstarlight.common.mixins;

import cn.leolezury.eternalstarlight.common.EternalStarlight;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(GameRenderer.class)
public abstract class GameRendererMixin {
    @Shadow protected abstract void loadEffect(ResourceLocation resourceLocation);

    @Inject(method = "checkEntityPostEffect", at = @At("TAIL"))
    private void es_checkEntityPostEffect(Entity entity, CallbackInfo ci) {
        loadEffect(new ResourceLocation(EternalStarlight.MOD_ID, "shaders/post/soulit_spectator.json"));
    }
}
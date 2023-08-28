package cn.leolezury.eternalstarlight.fabric.mixins;

import cn.leolezury.eternalstarlight.common.handler.CommonHandlers;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.phys.HitResult;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Projectile.class)
public abstract class ProjectileMixin {
    @Inject(method = "onHit", at = @At(value = "HEAD"))
    private void hurt(HitResult hitResult, CallbackInfo ci) {
        CommonHandlers.onArrowHit((Projectile) (Object) this, hitResult);
    }
}

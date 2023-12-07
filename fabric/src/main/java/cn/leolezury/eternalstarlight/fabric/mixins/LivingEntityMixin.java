package cn.leolezury.eternalstarlight.fabric.mixins;

import cn.leolezury.eternalstarlight.common.handler.CommonHandlers;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.LivingEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin {
    @Inject(method = "hurt", at = @At(value = "HEAD"))
    private void es_hurt(DamageSource damageSource, float amount, CallbackInfoReturnable<Boolean> cir) {
        CommonHandlers.onLivingHurt((LivingEntity) (Object) this, damageSource, amount);
    }

    @Inject(method = "tick", at = @At(value = "RETURN"))
    private void es_tick(CallbackInfo ci) {
        CommonHandlers.onLivingTick((LivingEntity) (Object) this);
    }
}

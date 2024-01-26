package cn.leolezury.eternalstarlight.fabric.mixins;

import cn.leolezury.eternalstarlight.common.handler.CommonHandlers;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.LivingEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin {
    @ModifyVariable(method = "hurt", at = @At(value = "LOAD", ordinal = 0), ordinal = 0, argsOnly = true)
    private float es_hurt(float amount, DamageSource damageSource) {
        return CommonHandlers.onLivingHurt((LivingEntity) (Object) this, damageSource, amount);
    }

    @Inject(method = "tick", at = @At(value = "RETURN"))
    private void es_tick(CallbackInfo ci) {
        CommonHandlers.onLivingTick((LivingEntity) (Object) this);
    }
}

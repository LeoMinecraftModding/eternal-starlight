package cn.leolezury.eternalstarlight.common.mixin;

import cn.leolezury.eternalstarlight.common.entity.projectile.EnergySpark;
import cn.leolezury.eternalstarlight.common.entity.projectile.WiltedPetal;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.minecraft.world.entity.projectile.ThrowableProjectile;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(ThrowableProjectile.class)
public abstract class ThrowableProjectileMixin {
	@WrapOperation(method = "tick", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/projectile/ThrowableProjectile;isInWater()Z"))
	private boolean tick(ThrowableProjectile instance, Operation<Boolean> original) {
		return ((Object) this instanceof WiltedPetal || (Object) this instanceof EnergySpark) ? false : original.call(instance);
	}
}

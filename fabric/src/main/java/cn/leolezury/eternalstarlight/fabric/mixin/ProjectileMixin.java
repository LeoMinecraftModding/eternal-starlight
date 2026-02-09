package cn.leolezury.eternalstarlight.fabric.mixin;

import cn.leolezury.eternalstarlight.common.handler.ESCommonHandler;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.phys.HitResult;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Projectile.class)
public abstract class ProjectileMixin {
	@Inject(method = "onHit", at = @At("HEAD"))
	private void onHit(HitResult hitResult, CallbackInfo ci) {
		if (hitResult.getType() != HitResult.Type.MISS) {
			ESCommonHandler.onProjectileImpact((Projectile) (Object) this, hitResult);
		}
	}
}

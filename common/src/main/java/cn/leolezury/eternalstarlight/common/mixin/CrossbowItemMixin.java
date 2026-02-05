package cn.leolezury.eternalstarlight.common.mixin;

import cn.leolezury.eternalstarlight.common.registry.ESItems;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.item.CrossbowItem;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(CrossbowItem.class)
public abstract class CrossbowItemMixin {
	@Inject(method = "shootProjectile", at = @At("RETURN"))
	private void shootProjectile(LivingEntity livingEntity, Projectile projectile, int i, float f, float g, float h, LivingEntity livingEntity2, CallbackInfo ci) {
		if ((Object) this == ESItems.MECHANICAL_CROSSBOW.get()) {
			projectile.setDeltaMovement(projectile.getDeltaMovement().scale(1.75));
		}
		if ((Object) this == ESItems.CRYSTAL_CROSSBOW.get()) {
			projectile.setDeltaMovement(projectile.getDeltaMovement().scale(1.35));
		}
		if ((Object) this == ESItems.WILTED_CROSSBOW.get()) {
			projectile.setDeltaMovement(projectile.getDeltaMovement().scale(1.5));
		}
	}
}

package cn.leolezury.eternalstarlight.common.mixin;

import cn.leolezury.eternalstarlight.common.handler.CommonHandlers;
import cn.leolezury.eternalstarlight.common.registry.ESItems;
import cn.leolezury.eternalstarlight.common.util.ESEntityUtil;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.item.CrossbowItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(CrossbowItem.class)
public abstract class CrossbowItemMixin {
	@Inject(method = "createProjectile", at = @At("RETURN"), cancellable = true)
	private void createProjectile(Level level, LivingEntity livingEntity, ItemStack itemStack, ItemStack itemStack2, boolean bl, CallbackInfoReturnable<Projectile> cir) {
		if (itemStack.is(ESItems.CRYSTAL_CROSSBOW.get())) {
			Projectile projectile = cir.getReturnValue();
			ESEntityUtil.getPersistentData(projectile).putBoolean(CommonHandlers.TAG_CRYSTAL_ARROW, true);
			cir.setReturnValue(projectile);
		}
		if (itemStack.is(ESItems.MECHANICAL_CROSSBOW.get())) {
			Projectile projectile = cir.getReturnValue();
			if (projectile instanceof AbstractArrow arrow) {
				arrow.setPierceLevel((byte) ((int) arrow.getPierceLevel() + 1));
			}
			cir.setReturnValue(projectile);
		}
	}

	@Inject(method = "shootProjectile", at = @At("RETURN"))
	private void shootProjectile(LivingEntity livingEntity, Projectile projectile, int i, float f, float g, float h, LivingEntity livingEntity2, CallbackInfo ci) {
		if ((Object) this == ESItems.MECHANICAL_CROSSBOW.get()) {
			projectile.setDeltaMovement(projectile.getDeltaMovement().scale(1.5));
		}
	}
}

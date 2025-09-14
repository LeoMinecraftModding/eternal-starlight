package cn.leolezury.eternalstarlight.common.mixin;

import cn.leolezury.eternalstarlight.common.handler.CommonHandlers;
import cn.leolezury.eternalstarlight.common.registry.ESDataAttachments;
import cn.leolezury.eternalstarlight.common.registry.ESItems;
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
	@Inject(method = "createProjectile", at = @At("RETURN"))
	private void createProjectile(Level level, LivingEntity livingEntity, ItemStack itemStack, ItemStack itemStack2, boolean bl, CallbackInfoReturnable<Projectile> cir) {
		if (itemStack.is(ESItems.STARFIRE_CROSSBOW.get())) {
			Projectile projectile = cir.getReturnValue();
			ESDataAttachments.ARROW_TYPE.setData(projectile, CommonHandlers.STARFIRE_ARROW);
			if (projectile instanceof AbstractArrow arrow) {
				arrow.setBaseDamage(arrow.getBaseDamage() + 0.25);
			}
		}
		if (itemStack.is(ESItems.CRYSTAL_CROSSBOW.get())) {
			Projectile projectile = cir.getReturnValue();
			ESDataAttachments.ARROW_TYPE.setData(projectile, CommonHandlers.CRYSTAL_ARROW);
		}
		if (itemStack.is(ESItems.MECHANICAL_CROSSBOW.get())) {
			Projectile projectile = cir.getReturnValue();
			if (projectile instanceof AbstractArrow arrow) {
				arrow.setPierceLevel((byte) ((int) arrow.getPierceLevel() + 1));
			}
			ESDataAttachments.ARROW_TYPE.setData(projectile, CommonHandlers.MECHANICAL_ARROW);
		}
		if (itemStack.is(ESItems.WILTED_CROSSBOW.get())) {
			Projectile projectile = cir.getReturnValue();
			ESDataAttachments.ARROW_TYPE.setData(projectile, CommonHandlers.WILTED_ARROW);
			if (projectile instanceof AbstractArrow arrow) {
				arrow.setBaseDamage(arrow.getBaseDamage() + 1.5);
			}
		}
	}

	@Inject(method = "shootProjectile", at = @At("RETURN"))
	private void shootProjectile(LivingEntity livingEntity, Projectile projectile, int i, float f, float g, float h, LivingEntity livingEntity2, CallbackInfo ci) {
		if ((Object) this == ESItems.CRYSTAL_CROSSBOW.get()) {
			projectile.setDeltaMovement(projectile.getDeltaMovement().scale(1.35));
		}
		if ((Object) this == ESItems.MECHANICAL_CROSSBOW.get()) {
			projectile.setDeltaMovement(projectile.getDeltaMovement().scale(1.75));
		}
		if ((Object) this == ESItems.WILTED_CROSSBOW.get()) {
			projectile.setDeltaMovement(projectile.getDeltaMovement().scale(1.5));
		}
	}
}

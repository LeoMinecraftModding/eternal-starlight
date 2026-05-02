package cn.leolezury.eternalstarlight.fabric.mixin;

import cn.leolezury.eternalstarlight.common.handler.ESCommonHandler;
import cn.leolezury.eternalstarlight.common.handler.ESCommonSetupHandler;
import cn.leolezury.eternalstarlight.common.util.ESTags;
import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import com.llamalad7.mixinextras.injector.wrapmethod.WrapMethod;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.tags.TagKey;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.material.Fluid;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin {
	@Shadow
	public abstract boolean isUsingItem();

	@Shadow
	public abstract ItemStack getUseItem();

	@Shadow
	protected abstract void jumpInLiquid(TagKey<Fluid> tagKey);

	@ModifyVariable(method = "actuallyHurt", at = @At(value = "STORE", ordinal = 1), ordinal = 0, argsOnly = true)
	private float modifyActualHurtDamage(float original, @Local(argsOnly = true) DamageSource source) {
		return ESCommonHandler.onModifyLivingActualHurtDamage((LivingEntity) (Object) this, source, original);
	}

	@Inject(method = "actuallyHurt", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/LivingEntity;setHealth(F)V", shift = At.Shift.AFTER))
	private void actuallyHurt(DamageSource source, float amount, CallbackInfo ci) {
		ESCommonHandler.onPostLivingHurt((LivingEntity) (Object) this, source, amount);
	}

	@WrapMethod(method = "heal")
	private void heal(float amount, Operation<Void> original) {
		float modified = ESCommonHandler.onLivingHeal((LivingEntity) (Object) this, amount);
		original.call(modified);
	}

	@WrapMethod(method = "hurt")
	private boolean hurt(DamageSource source, float amount, Operation<Boolean> original) {
		float modified = ESCommonHandler.onModifyLivingHurtDamage((LivingEntity) (Object) this, source, amount);
		return original.call(source, modified);
	}

	@Inject(method = "hurt", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/LivingEntity;actuallyHurt(Lnet/minecraft/world/damagesource/DamageSource;F)V", shift = At.Shift.AFTER))
	private void modifyPostAttackInvulnerabilityTicks(DamageSource source, float amount, CallbackInfoReturnable<Boolean> cir) {
		LivingEntity living = (LivingEntity) (Object) this;
		living.invulnerableTime = ESCommonHandler.onModifyPostAttackInvulnerabilityTicks(living, source, amount, living.invulnerableTime);
	}

	@Inject(method = "hurt", at = @At("HEAD"))
	private void hurt(DamageSource source, float amount, CallbackInfoReturnable<Boolean> cir) {
		if (isUsingItem() && ESCommonSetupHandler.SHIELDS.stream().anyMatch(itemSupplier -> getUseItem().is(itemSupplier.get()))) {
			ESCommonHandler.onShieldBlock((LivingEntity) (Object) this, source);
		}
	}

	@Inject(method = "isBlocking", at = @At("RETURN"), cancellable = true)
	private void isBlocking(CallbackInfoReturnable<Boolean> cir) {
		if (isUsingItem() && ESCommonSetupHandler.SHIELDS.stream().anyMatch(itemSupplier -> getUseItem().is(itemSupplier.get()))) {
			cir.setReturnValue(true);
		}
	}

	@Inject(method = "aiStep", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/LivingEntity;isInLava()Z", ordinal = 0, shift = At.Shift.AFTER))
	private void aiStep(CallbackInfo ci) {
		if (((LivingEntity) (Object) this).getFluidHeight(ESTags.Fluids.ETHER) > 0) {
			jumpInLiquid(ESTags.Fluids.ETHER);
		}
	}

	@Inject(method = "decreaseAirSupply", at = @At("RETURN"), cancellable = true)
	private void decreaseAirSupply(int i, CallbackInfoReturnable<Integer> cir) {
		LivingEntity entity = (LivingEntity) (Object) this;
		int result = ESCommonHandler.onLivingDecreaseAirSupply(entity);
		if (result > 0) {
			cir.setReturnValue(Math.min(i + result, entity.getMaxAirSupply()));
		}
		if (result < 0) {
			cir.setReturnValue(i - Math.max(Math.max(i - cir.getReturnValue(), 0) + result, 0));
		}
	}

	@ModifyReturnValue(method = "getVisibilityPercent", at = @At("RETURN"))
	private double getVisibilityPercent(double original, @Local(argsOnly = true) Entity lookingEntity) {
		LivingEntity entity = (LivingEntity) (Object) this;
		return ESCommonHandler.onLivingVisibility(entity, lookingEntity, original) * original;
	}
}

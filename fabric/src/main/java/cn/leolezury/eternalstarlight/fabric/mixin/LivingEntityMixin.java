package cn.leolezury.eternalstarlight.fabric.mixin;

import cn.leolezury.eternalstarlight.common.handler.CommonHandlers;
import cn.leolezury.eternalstarlight.common.handler.CommonSetupHandlers;
import cn.leolezury.eternalstarlight.common.util.ESTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.damagesource.DamageSource;
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

	@ModifyVariable(method = "actuallyHurt", at = @At(value = "LOAD", ordinal = 0), ordinal = 0, argsOnly = true)
	private float actuallyHurt(float amount, DamageSource damageSource) {
		return CommonHandlers.onLivingHurt((LivingEntity) (Object) this, damageSource, amount);
	}

	@Inject(method = "hurt", at = @At(value = "HEAD"))
	private void hurt(DamageSource damageSource, float amount, CallbackInfoReturnable<Boolean> cir) {
		if (isUsingItem() && CommonSetupHandlers.SHIELDS.stream().anyMatch(itemSupplier -> getUseItem().is(itemSupplier.get()))) {
			CommonHandlers.onShieldBlock((LivingEntity) (Object) this, damageSource);
		}
	}

	@Inject(method = "tick", at = @At(value = "RETURN"))
	private void tick(CallbackInfo ci) {
		CommonHandlers.onLivingTick((LivingEntity) (Object) this);
	}

	@Inject(method = "isBlocking", at = @At("RETURN"), cancellable = true)
	private void isBlocking(CallbackInfoReturnable<Boolean> cir) {
		if (isUsingItem() && CommonSetupHandlers.SHIELDS.stream().anyMatch(itemSupplier -> getUseItem().is(itemSupplier.get()))) {
			cir.setReturnValue(true);
		}
	}

	@Inject(method = "aiStep", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/LivingEntity;isInLava()Z", ordinal = 0, shift = At.Shift.AFTER))
	private void aiStep(CallbackInfo ci) {
		if (((LivingEntity) (Object) this).getFluidHeight(ESTags.Fluids.ETHER) > 0) {
			jumpInLiquid(ESTags.Fluids.ETHER);
		}
	}
}

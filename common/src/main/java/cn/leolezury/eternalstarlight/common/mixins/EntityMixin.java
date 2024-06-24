package cn.leolezury.eternalstarlight.common.mixins;

import cn.leolezury.eternalstarlight.common.entity.interfaces.PersistentDataHolder;
import cn.leolezury.eternalstarlight.common.util.ESTags;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.material.Fluid;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Entity.class)
public abstract class EntityMixin implements PersistentDataHolder {
	@Shadow
	public abstract void resetFallDistance();

	@Shadow
	public abstract void clearFire();

	@Shadow
	public abstract boolean updateFluidHeightAndDoFluidPushing(TagKey<Fluid> tagKey, double d);

	@Shadow
	protected boolean wasTouchingWater;
	@Unique
	private CompoundTag esPersistentData;

	@Override
	public CompoundTag getESPersistentData() {
		if (esPersistentData == null) {
			esPersistentData = new CompoundTag();
		}
		return esPersistentData;
	}

	@Inject(method = "saveWithoutId", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/Entity;addAdditionalSaveData(Lnet/minecraft/nbt/CompoundTag;)V"))
	private void save(CompoundTag compoundTag, CallbackInfoReturnable<Boolean> info) {
		if (esPersistentData != null && compoundTag != null) {
			compoundTag.put("ESData", esPersistentData.copy());
		}
	}

	@Inject(method = "load", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/Entity;readAdditionalSaveData(Lnet/minecraft/nbt/CompoundTag;)V"))
	private void load(CompoundTag compoundTag, CallbackInfo info) {
		if (compoundTag != null && compoundTag.contains("ESData", CompoundTag.TAG_COMPOUND)) {
			esPersistentData = compoundTag.getCompound("ESData");
		}
	}

	@Inject(method = "updateInWaterStateAndDoFluidPushing", at = @At("RETURN"), cancellable = true)
	private void updateInWaterStateAndDoFluidPushing(CallbackInfoReturnable<Boolean> cir) {
		if (this.updateFluidHeightAndDoFluidPushing(ESTags.Fluids.ETHER, 0.014)) {
			this.resetFallDistance();
			this.clearFire();
			this.wasTouchingWater = true;
			cir.setReturnValue(true);
		}
	}
}

package cn.leolezury.eternalstarlight.common.mixin;

import cn.leolezury.eternalstarlight.common.entity.interfaces.PersistentDataHolder;
import cn.leolezury.eternalstarlight.common.registry.ESItems;
import cn.leolezury.eternalstarlight.common.registry.ESMobEffects;
import cn.leolezury.eternalstarlight.common.registry.ESParticles;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.tags.FluidTags;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
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
	public abstract boolean isInWater();

	@Shadow
	public abstract Level level();

	@Shadow
	public abstract AABB getBoundingBox();

	@Unique
	private CompoundTag esPersistentData;

	@Unique
	private boolean feetInWater = false;

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
			compoundTag.put("es_data", esPersistentData.copy());
		}
	}

	@Inject(method = "load", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/Entity;readAdditionalSaveData(Lnet/minecraft/nbt/CompoundTag;)V"))
	private void load(CompoundTag compoundTag, CallbackInfo info) {
		if (compoundTag != null && compoundTag.contains("es_data", CompoundTag.TAG_COMPOUND)) {
			esPersistentData = compoundTag.getCompound("es_data");
		}
	}

	@Inject(method = "isStateClimbable", at = @At("RETURN"), cancellable = true)
	private void isStateClimbable(BlockState blockState, CallbackInfoReturnable<Boolean> cir) {
		Entity entity = (Entity) (Object) this;
		if (entity instanceof LivingEntity living && living.hasEffect(ESMobEffects.STICKY.asHolder())) {
			cir.setReturnValue(true);
		}
	}

	@Inject(method = "tick", at = @At("RETURN"))
	private void tick(CallbackInfo ci) {
		feetInWater = isInWater() && level().getFluidState(BlockPos.containing(getBoundingBox().getBottomCenter())).is(FluidTags.WATER);
		Entity entity = (Entity) (Object) this;
		if (level().isClientSide && feetInWater && entity instanceof LivingEntity living && living.getDeltaMovement().length() > 0.01 && living.getItemBySlot(EquipmentSlot.FEET).is(ESItems.AIR_SAC_BOOTS.get())) {
			Vec3 pos = living.getBoundingBox().getBottomCenter().offsetRandom(living.getRandom(), living.getBbWidth());
			Vec3 speed = living.getDeltaMovement().normalize().offsetRandom(living.getRandom(), 0.3f).scale(-0.2);
			level().addParticle(ESParticles.TOWER_SQUID_INK.get(), pos.x, pos.y, pos.z, speed.x, speed.y, speed.z);
		}
	}

	@Inject(method = "getGravity", at = @At("RETURN"), cancellable = true)
	private void getGravity(CallbackInfoReturnable<Double> cir) {
		Entity entity = (Entity) (Object) this;
		if (entity instanceof LivingEntity living && feetInWater && living.getItemBySlot(EquipmentSlot.FEET).is(ESItems.AIR_SAC_BOOTS.get())) {
			cir.setReturnValue(0.0);
		}
	}
}

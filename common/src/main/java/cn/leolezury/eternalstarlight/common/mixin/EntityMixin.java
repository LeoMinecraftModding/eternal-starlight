package cn.leolezury.eternalstarlight.common.mixin;

import cn.leolezury.eternalstarlight.common.registry.ESDataAttachments;
import cn.leolezury.eternalstarlight.common.registry.ESItems;
import cn.leolezury.eternalstarlight.common.registry.ESMobEffects;
import cn.leolezury.eternalstarlight.common.registry.ESParticles;
import cn.leolezury.eternalstarlight.common.util.ESTags;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ColorParticleOption;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.FastColor;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MoverType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.entity.projectile.ProjectileDeflection;
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
public abstract class EntityMixin {
	@Shadow
	public abstract boolean isInWater();

	@Shadow
	public abstract Level level();

	@Shadow
	public abstract AABB getBoundingBox();

	@Shadow
	public int tickCount;

	@Unique
	private boolean bottomInWater = false;

	@Inject(method = "isStateClimbable", at = @At("RETURN"), cancellable = true)
	private void isStateClimbable(BlockState blockState, CallbackInfoReturnable<Boolean> cir) {
		Entity entity = (Entity) (Object) this;
		if (entity instanceof LivingEntity living && living.hasEffect(ESMobEffects.STICKY.asHolder())) {
			cir.setReturnValue(true);
		}
	}

	@Inject(method = "tick", at = @At("RETURN"))
	private void tick(CallbackInfo ci) {
		bottomInWater = isInWater() && level().getFluidState(BlockPos.containing(getBoundingBox().getBottomCenter())).is(FluidTags.WATER);
		Entity entity = (Entity) (Object) this;
		if (level().isClientSide && bottomInWater && entity instanceof LivingEntity living && living.getDeltaMovement().length() > 0.01 && living.getItemBySlot(EquipmentSlot.FEET).is(ESItems.AIR_SAC_BOOTS.get())) {
			Vec3 pos = living.getBoundingBox().getBottomCenter().offsetRandom(living.getRandom(), living.getBbWidth());
			Vec3 speed = living.getDeltaMovement().normalize().offsetRandom(living.getRandom(), 0.3f).scale(-0.2);
			level().addParticle(ColorParticleOption.create(ESParticles.COLORED_INK.get(), FastColor.ARGB32.color(255, 51, 61, 58)), pos.x, pos.y, pos.z, speed.x, speed.y, speed.z);
		}
	}

	@Inject(method = "getGravity", at = @At("RETURN"), cancellable = true)
	private void getGravity(CallbackInfoReturnable<Double> cir) {
		Entity entity = (Entity) (Object) this;
		if (entity instanceof LivingEntity living && bottomInWater && living.getItemBySlot(EquipmentSlot.FEET).is(ESItems.AIR_SAC_BOOTS.get())) {
			cir.setReturnValue(0.0);
		}
	}

	@Inject(method = "checkInsideBlocks", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/block/state/BlockState;entityInside(Lnet/minecraft/world/level/Level;Lnet/minecraft/core/BlockPos;Lnet/minecraft/world/entity/Entity;)V"))
	private void checkInsideBlocks(CallbackInfo ci, @Local(ordinal = 0) BlockState state) {
		if (state.getFluidState().is(ESTags.Fluids.ETHER)) {
			ESDataAttachments.IN_ETHER.setData((Entity) (Object) this, true);
		}
	}

	@Inject(method = "deflection", at = @At("RETURN"), cancellable = true)
	private void deflection(Projectile projectile, CallbackInfoReturnable<ProjectileDeflection> cir) {
		Entity entity = (Entity) (Object) this;
		if (entity instanceof LivingEntity living && living.isBlocking() && living.getUseItem().is(ESItems.FLOWGLAZE_SHIELD.get())) {
			Vec3 viewVector = living.calculateViewVector(0.0F, living.getYHeadRot());
			Vec3 offset = projectile.position().vectorTo(living.position());
			offset = new Vec3(offset.x, 0.0, offset.z).normalize();
			if (offset.dot(viewVector) < 0.0) {
				cir.setReturnValue(ProjectileDeflection.AIM_DEFLECT);
			}
		}
	}

	@Inject(method = "move", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/Entity;setPos(DDD)V", ordinal = 1))
	private void move(MoverType moverType, Vec3 vec3, CallbackInfo ci, @Local(ordinal = 1) Vec3 movement) {
		Entity entity = (Entity) (Object) this;
		int lastUpdate = ESDataAttachments.LAST_MOVEMENT_UPDATE.getData(entity);
		if (lastUpdate != tickCount) {
			ESDataAttachments.MOVEMENT.setData(entity, movement);
			ESDataAttachments.LAST_MOVEMENT_UPDATE.setData(entity, tickCount);
		} else {
			ESDataAttachments.MOVEMENT.setData(entity, ESDataAttachments.MOVEMENT.getData(entity).add(movement));
		}
	}

	@WrapOperation(method = "doWaterSplashEffect", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/Entity;playSound(Lnet/minecraft/sounds/SoundEvent;FF)V", ordinal = 0))
	private void playSplashSound(Entity instance, SoundEvent soundEvent, float volume, float pitch, Operation<Void> original) {
		if (!((Entity) (Object) this instanceof LivingEntity living && living.getItemBySlot(EquipmentSlot.LEGS).is(ESItems.UNREALIUM_LEGGINGS.get()))) {
			original.call(instance, soundEvent, volume, pitch);
		}
	}

	@WrapOperation(method = "doWaterSplashEffect", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/Entity;playSound(Lnet/minecraft/sounds/SoundEvent;FF)V", ordinal = 1))
	private void playHighSpeedSplashSound(Entity instance, SoundEvent soundEvent, float volume, float pitch, Operation<Void> original) {
		if (!((Entity) (Object) this instanceof LivingEntity living && living.getItemBySlot(EquipmentSlot.LEGS).is(ESItems.UNREALIUM_LEGGINGS.get()))) {
			original.call(instance, soundEvent, volume, pitch);
		}
	}

	@Inject(method = "isInWall", at = @At("HEAD"), cancellable = true)
	private void isInWall(CallbackInfoReturnable<Boolean> cir) {
		Entity self = (Entity) (Object) this;
		if (self instanceof Player player && player.hasEffect(ESMobEffects.OBLIVION.asHolder())) {
			cir.setReturnValue(false);
		}
	}
}
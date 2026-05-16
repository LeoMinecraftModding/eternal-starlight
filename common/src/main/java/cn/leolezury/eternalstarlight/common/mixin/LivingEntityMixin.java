package cn.leolezury.eternalstarlight.common.mixin;

import cn.leolezury.eternalstarlight.common.data.ESDamageTypes;
import cn.leolezury.eternalstarlight.common.entity.living.monster.Stranghoul;
import cn.leolezury.eternalstarlight.common.network.ParticlePacket;
import cn.leolezury.eternalstarlight.common.particle.ExplosionShockParticleOptions;
import cn.leolezury.eternalstarlight.common.platform.ESPlatform;
import cn.leolezury.eternalstarlight.common.registry.*;
import cn.leolezury.eternalstarlight.common.util.ESAccessoryUtil;
import cn.leolezury.eternalstarlight.common.util.ESEntityUtil;
import cn.leolezury.eternalstarlight.common.util.ESTags;
import cn.leolezury.eternalstarlight.common.vfx.ScreenShakeVfx;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.llamalad7.mixinextras.sugar.Local;
import com.llamalad7.mixinextras.sugar.ref.LocalRef;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeMap;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.targeting.TargetingConditions;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin {
	@Shadow
	public abstract ItemStack getItemInHand(InteractionHand interactionHand);

	@Shadow
	public abstract boolean isUsingItem();

	@Shadow
	public abstract ItemStack getUseItem();

	@Shadow
	public abstract Collection<MobEffectInstance> getActiveEffects();

	@Shadow
	public abstract boolean hasEffect(Holder<MobEffect> holder);

	@Shadow
	public abstract boolean removeEffect(Holder<MobEffect> holder);

	@Shadow
	@Nullable
	protected ItemStack autoSpinAttackItemStack;

	@Shadow
	@NotNull
	public abstract ItemStack getWeaponItem();

	@Shadow
	private Optional<BlockPos> lastClimbablePos;

	@Shadow
	public abstract ItemStack getItemBySlot(EquipmentSlot equipmentSlot);

	@Shadow
	public abstract AttributeMap getAttributes();

	@Shadow
	@Nullable
	public abstract LivingEntity getKillCredit();

	@Inject(method = "isBlocking", at = @At("RETURN"), cancellable = true)
	private void isBlocking(CallbackInfoReturnable<Boolean> cir) {
		if (isUsingItem() && getUseItem().is(ESTags.Items.GREATSWORDS)) {
			cir.setReturnValue(true);
		}
	}

	@Inject(method = "getKnockback", at = @At("RETURN"), cancellable = true)
	private void getKnockback(Entity target, DamageSource damageSource, CallbackInfoReturnable<Float> cir) {
		if (getWeaponItem().is(ESTags.Items.HAMMERS)) {
			cir.setReturnValue(cir.getReturnValue() + 1);
		}
	}

	@Inject(method = "createLivingAttributes", at = @At("RETURN"))
	private static void createLivingAttributes(CallbackInfoReturnable<AttributeSupplier.Builder> cir) {
		cir.getReturnValue()
			.add(ESAttributes.THROWN_POTION_DISTANCE.asHolder())
			.add(ESAttributes.ETHER_RESISTANCE.asHolder())
			.add(ESAttributes.FIRE_RESISTANCE.asHolder())
			.add(ESAttributes.METEOR_COUNTERATTACK_CHANCE.asHolder())
			.add(ESAttributes.HEAL_MULTIPLIER.asHolder())
			.add(ESAttributes.ENEMY_FOLLOW_RANGE_MULTIPLIER.asHolder());
	}

	@Inject(method = "checkAutoSpinAttack", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/LivingEntity;setDeltaMovement(Lnet/minecraft/world/phys/Vec3;)V", shift = At.Shift.AFTER))
	private void checkAutoSpinAttack(CallbackInfo ci) {
		doCrescentSpearDamage();
	}

	@Inject(method = "checkAutoSpinAttack", at = @At("HEAD"))
	private void checkAutoSpinAttackTail(CallbackInfo ci) {
		LivingEntity entity = (LivingEntity) (Object) this;
		if (entity.horizontalCollision) {
			doCrescentSpearDamage();
		}
	}

	@Unique
	private void doCrescentSpearDamage() {
		LivingEntity entity = (LivingEntity) (Object) this;
		if (autoSpinAttackItemStack != null && autoSpinAttackItemStack.is(ESItems.CRESCENT_SPEAR.get())) {
			if (!entity.level().isClientSide) {
				for (LivingEntity living : entity.level().getNearbyEntities(LivingEntity.class, TargetingConditions.DEFAULT, entity, entity.getBoundingBox().inflate(3))) {
					if (ESEntityUtil.shouldHarm(entity, living) && entity instanceof Player player) {
						player.attackStrengthTicker = Mth.ceil(player.getCurrentItemAttackStrengthDelay());
						player.attack(living);
						if (entity.level() instanceof ServerLevel serverLevel) {
							Vec3 targetPos = living.position().add((entity.getRandom().nextDouble() - 0.5) * living.getBbWidth(), entity.getRandom().nextDouble() * living.getBbHeight(), (entity.getRandom().nextDouble() - 0.5) * living.getBbWidth());
							Vec3 speed = targetPos.subtract(entity.position().add(0, entity.getBbHeight() / 2, 0));
							Vec3 pos = targetPos.subtract(speed.normalize().scale(1));
							for (int i = 0; i < 2; i++) {
								ESPlatform.INSTANCE.sendToAllClients(serverLevel, new ParticlePacket(ESParticles.LUNAR_SLASH.get(), pos.x, pos.y, pos.z, speed.x, speed.y, speed.z));
							}
						}
					}
				}
				if (entity.level() instanceof ServerLevel serverLevel) {
					Vec3 centerPos = entity.position().add(0, entity.getBbHeight() / 2, 0);
					for (int i = 0; i < 10; i++) {
						Vec3 speed = new Vec3((entity.getRandom().nextFloat() - entity.getRandom().nextFloat()) * 0.1F, entity.getRandom().nextFloat() * 0.05F, (entity.getRandom().nextFloat() - entity.getRandom().nextFloat()) * 0.1F).normalize();
						ESPlatform.INSTANCE.sendToAllClients(serverLevel, new ParticlePacket(ExplosionShockParticleOptions.CRESCENT_SPEAR, centerPos.x + speed.x * 1.5, centerPos.y + speed.y * 1.5, centerPos.z + speed.z * 1.5, speed.x, speed.y, speed.z));
					}
					ScreenShakeVfx.createInstance(entity.level().dimension(), entity.position(), 40, 20, 0.12f, 0.24f, 3, 5.5f).send(serverLevel);
				}
				entity.invulnerableTime = Math.max(entity.invulnerableTime, 10);
			}
			ESDataAttachments.CRESCENT_SPEAR_DASH.setData(entity, false);
		}
	}

	@Inject(method = "eat(Lnet/minecraft/world/level/Level;Lnet/minecraft/world/item/ItemStack;Lnet/minecraft/world/food/FoodProperties;)Lnet/minecraft/world/item/ItemStack;", at = @At("HEAD"))
	private void eat(Level level, ItemStack itemStack, FoodProperties foodProperties, CallbackInfoReturnable<ItemStack> cir) {
		if (itemStack.is(ESItems.LUNARIS_CACTUS_GEL.get())) {
			List<Holder<MobEffect>> effectsToRemove = new ArrayList<>();
			for (MobEffectInstance effectInstance : getActiveEffects()) {
				if (!effectInstance.getEffect().value().isBeneficial()) {
					effectsToRemove.add(effectInstance.getEffect());
				}
			}
			for (Holder<MobEffect> effect : effectsToRemove) {
				if (hasEffect(effect)) {
					removeEffect(effect);
				}
			}
		} else if (itemStack.is(ESItems.PUNGENCY_STEW.get())) {
			removeEffect(MobEffects.HUNGER);
		}
	}

	@WrapOperation(method = "eat(Lnet/minecraft/world/level/Level;Lnet/minecraft/world/item/ItemStack;Lnet/minecraft/world/food/FoodProperties;)Lnet/minecraft/world/item/ItemStack;", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/item/ItemStack;consume(ILnet/minecraft/world/entity/LivingEntity;)V"))
	private void consume(ItemStack instance, int count, LivingEntity livingEntity, Operation<Void> original, @Local(argsOnly = true) LocalRef<ItemStack> foodStack) {
		if (instance.is(ESItems.POPPED_NOCTURNAL_MILLET_BUCKET.get())) {
			if (instance.isDamageableItem() && !livingEntity.hasInfiniteMaterials()) {
				instance.setDamageValue(instance.getDamageValue() + 1);
				if (instance.getDamageValue() >= instance.getMaxDamage()) {
					foodStack.set(Items.BUCKET.getDefaultInstance());
				}
			}
		} else {
			original.call(instance, count, livingEntity);
		}
	}

	@WrapOperation(method = "triggerItemUseEffects", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/item/ItemStack;getUseAnimation()Lnet/minecraft/world/item/UseAnim;"))
	private UseAnim getUseAnimation(ItemStack instance, Operation<UseAnim> original) {
		if (ESAccessoryUtil.getActiveAccessoriesOnArmors((LivingEntity) (Object) this).contains(ESItems.FUNGUS_AMULET.get()) && instance.is(ESTags.Items.CONSUMABLE_WHEN_WEARING_FUNGUS_AMULET)) {
			return UseAnim.EAT;
		}
		return original.call(instance);
	}

	@WrapOperation(method = "triggerItemUseEffects", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/LivingEntity;playSound(Lnet/minecraft/sounds/SoundEvent;FF)V"))
	private void playEatingOrDrinkingSound(LivingEntity instance, SoundEvent soundEvent, float volume, float pitch, Operation<Void> original) {
		if (!((LivingEntity) (Object) this).getItemBySlot(EquipmentSlot.HEAD).is(ESItems.UNREALIUM_HELMET.get())) {
			original.call(instance, soundEvent, volume, pitch);
		}
	}

	@WrapOperation(method = "eat(Lnet/minecraft/world/level/Level;Lnet/minecraft/world/item/ItemStack;Lnet/minecraft/world/food/FoodProperties;)Lnet/minecraft/world/item/ItemStack;", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/Level;playSound(Lnet/minecraft/world/entity/player/Player;DDDLnet/minecraft/sounds/SoundEvent;Lnet/minecraft/sounds/SoundSource;FF)V"))
	private void playEatSound(Level instance, Player player, double x, double y, double z, SoundEvent soundEvent, SoundSource soundSource, float volume, float pitch, Operation<Void> original) {
		if (!((LivingEntity) (Object) this).getItemBySlot(EquipmentSlot.HEAD).is(ESItems.UNREALIUM_HELMET.get())) {
			original.call(instance, player, x, y, z, soundEvent, soundSource, volume, pitch);
		}
	}

	@WrapOperation(method = "causeFallDamage", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/LivingEntity;playSound(Lnet/minecraft/sounds/SoundEvent;FF)V"))
	private void playFallSound(LivingEntity instance, SoundEvent soundEvent, float volume, float pitch, Operation<Void> original) {
		if (!((LivingEntity) (Object) this).getItemBySlot(EquipmentSlot.LEGS).is(ESItems.UNREALIUM_LEGGINGS.get())) {
			original.call(instance, soundEvent, volume, pitch);
		}
	}

	@Inject(method = "playBlockFallSound", at = @At("HEAD"), cancellable = true)
	private void playBlockFallSound(CallbackInfo ci) {
		if (((LivingEntity) (Object) this).getItemBySlot(EquipmentSlot.LEGS).is(ESItems.UNREALIUM_LEGGINGS.get())) {
			ci.cancel();
		}
	}

	@Inject(method = "onClimbable", at = @At("RETURN"), cancellable = true)
	private void onClimbable(CallbackInfoReturnable<Boolean> cir) {
		LivingEntity livingEntity = ((LivingEntity) (Object) this);
		if (hasEffect(ESMobEffects.STICKY.asHolder())) {
			boolean climbable = false;
			AABB box = livingEntity.getBoundingBox();
			BlockPos fromPos = BlockPos.containing(box.minX - 1.0E-3, box.minY + 1.0E-7, box.minZ - 1.0E-3);
			BlockPos toPos = BlockPos.containing(box.maxX + 1.0E-3, box.maxY - 1.0E-7, box.maxZ + 1.0E-3);
			BlockPos.MutableBlockPos mutableBlockPos = new BlockPos.MutableBlockPos();
			for (int i = fromPos.getX(); i <= toPos.getX(); ++i) {
				for (int j = fromPos.getY(); j <= toPos.getY(); ++j) {
					for (int k = fromPos.getZ(); k <= toPos.getZ(); ++k) {
						mutableBlockPos.set(i, j, k);
						if (livingEntity.level().getBlockState(mutableBlockPos).isCollisionShapeFullBlock(livingEntity.level(), mutableBlockPos)) {
							climbable = true;
						}
					}
				}
			}
			if (climbable) {
				this.lastClimbablePos = Optional.of(livingEntity.blockPosition());
				cir.setReturnValue(true);
			}
		}
	}

	@Inject(method = "tickEffects", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/LivingEntity;updateGlowingStatus()V", shift = At.Shift.AFTER))
	private void tickEffects(CallbackInfo ci) {
		LivingEntity livingEntity = ((LivingEntity) (Object) this);
		if (!hasEffect(ESMobEffects.NUMBNESS.asHolder())) {
			float damage = ESDataAttachments.NUMBNESS_DAMAGE.getData(livingEntity);
			if (damage != 0) {
				livingEntity.hurt(ESDamageTypes.getDamageSource(livingEntity.level(), ESDamageTypes.NUMBNESS), damage);
				ESDataAttachments.NUMBNESS_DAMAGE.setData(livingEntity, 0f);
			}
		}
	}

	@WrapOperation(method = "hurt", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/LivingEntity;knockback(DDD)V"))
	private void knockback(LivingEntity instance, double strength, double x, double z, Operation<Void> original, @Local(argsOnly = true) DamageSource source) {
		if (source.getDirectEntity() instanceof AbstractArrow arrow) {
			ItemStack weaponItem = arrow.getWeaponItem();
			if (weaponItem != null && weaponItem.is(ESItems.UNREALIUM_CROSSBOW.get())) {
				original.call(instance, strength * 0.5, x, z);
				return;
			}
		}
		original.call(instance, strength, x, z);
	}

	@Inject(method = "die", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/LivingEntity;gameEvent(Lnet/minecraft/core/Holder;)V", shift = At.Shift.AFTER))
	private void die(CallbackInfo ci) {
		LivingEntity livingEntity = ((LivingEntity) (Object) this);
		if (livingEntity.getType().is(ESTags.EntityTypes.STRANGHOUL_PREYS) && getKillCredit() instanceof Stranghoul) {
			List<Player> players = livingEntity.level().getNearbyPlayers(TargetingConditions.forNonCombat(), livingEntity, livingEntity.getBoundingBox().inflate(20));
			for (Player player : players) {
				if (player instanceof ServerPlayer serverPlayer) {
					ESCriteriaTriggers.WITNESS_STRANGHOUL_HUNT.get().trigger(serverPlayer);
				}
			}
		}
	}
}

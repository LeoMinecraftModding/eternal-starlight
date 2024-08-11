package cn.leolezury.eternalstarlight.common.entity.living.boss.monstrosity;

import cn.leolezury.eternalstarlight.common.EternalStarlight;
import cn.leolezury.eternalstarlight.common.data.ESDamageTypes;
import cn.leolezury.eternalstarlight.common.entity.interfaces.RayAttackUser;
import cn.leolezury.eternalstarlight.common.entity.living.boss.ESBoss;
import cn.leolezury.eternalstarlight.common.entity.living.boss.ESServerBossEvent;
import cn.leolezury.eternalstarlight.common.entity.living.goal.LookAtTargetGoal;
import cn.leolezury.eternalstarlight.common.entity.living.phase.BehaviorManager;
import cn.leolezury.eternalstarlight.common.network.ParticlePacket;
import cn.leolezury.eternalstarlight.common.particle.ESSmokeParticleOptions;
import cn.leolezury.eternalstarlight.common.particle.RingExplosionParticleOptions;
import cn.leolezury.eternalstarlight.common.platform.ESPlatform;
import cn.leolezury.eternalstarlight.common.registry.ESSoundEvents;
import cn.leolezury.eternalstarlight.common.util.ESBookUtil;
import cn.leolezury.eternalstarlight.common.util.ESMathUtil;
import cn.leolezury.eternalstarlight.common.util.ESTags;
import net.minecraft.core.particles.BlockParticleOption;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.BossEvent;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.FloatGoal;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.ai.targeting.TargetingConditions;
import net.minecraft.world.entity.animal.IronGolem;
import net.minecraft.world.entity.npc.AbstractVillager;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Explosion;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;

import java.util.List;

public class LunarMonstrosity extends ESBoss implements RayAttackUser {
	public LunarMonstrosity(EntityType<? extends ESBoss> entityType, Level level) {
		super(entityType, level);
	}

	private final ESServerBossEvent bossEvent = new ESServerBossEvent(this, getUUID(), BossEvent.BossBarColor.PURPLE, true);

	private final BehaviorManager<LunarMonstrosity> behaviorManager = new BehaviorManager<>(this, List.of(
		new LunarMonstrosityToxicBreathPhase(),
		new LunarMonstrositySporePhase(),
		new LunarMonstrosityThornPhase(),
		new LunarMonstrosityBitePhase(),
		new LunarMonstrosityDigPhase(),
		new LunarMonstrositySneakPhase(),
		new LunarMonstrosityEmergePhase(),
		new LunarMonstrositySoulPhase(),
		new LunarMonstrosityStunPhase()
	));

	public AnimationState toxicBreathAnimationState = new AnimationState();
	public AnimationState sporeAnimationState = new AnimationState();
	public AnimationState thornAnimationState = new AnimationState();
	public AnimationState biteAnimationState = new AnimationState();
	public AnimationState digAnimationState = new AnimationState();
	public AnimationState sneakAnimationState = new AnimationState();
	public AnimationState emergeAnimationState = new AnimationState();
	public AnimationState switchPhaseAnimationState = new AnimationState();
	public AnimationState deathAnimationState = new AnimationState();
	public Vec3 headPos = Vec3.ZERO;

	@Override
	public void readAdditionalSaveData(CompoundTag compoundTag) {
		super.readAdditionalSaveData(compoundTag);
		bossEvent.setId(getUUID());
	}

	@Override
	public void startSeenByPlayer(ServerPlayer serverPlayer) {
		super.startSeenByPlayer(serverPlayer);
		bossEvent.addPlayer(serverPlayer);
		ESBookUtil.unlockFor(serverPlayer, EternalStarlight.id("lunar_monstrosity_seen"));
	}

	@Override
	public void stopSeenByPlayer(ServerPlayer serverPlayer) {
		super.stopSeenByPlayer(serverPlayer);
		bossEvent.removePlayer(serverPlayer);
	}

	@Override
	protected void registerGoals() {
		super.registerGoals();
		goalSelector.addGoal(0, new FloatGoal(this));
		goalSelector.addGoal(1, new LunarMonstrosityMeleeAttackGoal(this, 1.0D, false));
		goalSelector.addGoal(2, new MonstrosityLookAtTargetGoal());
		goalSelector.addGoal(3, new LookAtPlayerGoal(this, Player.class, 3.0F, 1.0F));
		goalSelector.addGoal(4, new LookAtPlayerGoal(this, Mob.class, 8.0F));

		targetSelector.addGoal(0, new HurtByTargetGoal(this, LunarMonstrosity.class).setAlertOthers());
		targetSelector.addGoal(1, new NearestAttackableTargetGoal<>(this, Player.class, true));
		targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, AbstractVillager.class, true));
		targetSelector.addGoal(3, new NearestAttackableTargetGoal<>(this, IronGolem.class, true));
	}

	private class MonstrosityLookAtTargetGoal extends LookAtTargetGoal {
		public MonstrosityLookAtTargetGoal() {
			super(LunarMonstrosity.this);
		}

		@Override
		public void tick() {
			if (LunarMonstrosity.this.getBehaviorState() != LunarMonstrosityToxicBreathPhase.ID) {
				super.tick();
			}
		}
	}

	private static class LunarMonstrosityMeleeAttackGoal extends MeleeAttackGoal {
		private LunarMonstrosityMeleeAttackGoal(PathfinderMob mob, double speed, boolean followingTargetEvenIfNotSeen) {
			super(mob, speed, followingTargetEvenIfNotSeen);
		}

		@Override
		public boolean canUse() {
			boolean canUse = true;
			if (mob instanceof LunarMonstrosity boss) {
				canUse = boss.getBehaviorState() == LunarMonstrositySneakPhase.ID;
			}
			return super.canUse() && canUse;
		}

		@Override
		public boolean canContinueToUse() {
			boolean canUse = true;
			if (mob instanceof LunarMonstrosity boss) {
				canUse = boss.getBehaviorState() == LunarMonstrositySneakPhase.ID;
			}
			return super.canContinueToUse() && canUse;
		}
	}

	@Override
	public boolean isRayFollowingHeadRotation() {
		return false;
	}

	@Override
	public Vec3 getRayRotationTarget() {
		return getTarget() == null ? position().add(getBbWidth() * (getRandom().nextFloat() - 0.5f), getBbHeight() * getRandom().nextFloat(), getBbWidth() * (getRandom().nextFloat() - 0.5f)) : getTarget().position().add(getTarget().getBbWidth() * (getRandom().nextFloat() - 0.5f), getTarget().getBbHeight() * getRandom().nextFloat(), getTarget().getBbWidth() * (getRandom().nextFloat() - 0.5f));
	}

	@Override
	public void updateRayEnd(Vec3 endPos) {
		setXRot(-ESMathUtil.positionToPitch(position(), endPos));
		setYHeadRot(ESMathUtil.positionToYaw(position(), endPos) - 90);
		setYRot(ESMathUtil.positionToYaw(position(), endPos) - 90);
	}

	public static AttributeSupplier.Builder createAttributes() {
		return createMonsterAttributes()
			.add(Attributes.MOVEMENT_SPEED, 0.35F)
			.add(Attributes.FOLLOW_RANGE, 200.0D)
			.add(Attributes.MAX_HEALTH, 200.0D)
			.add(Attributes.ATTACK_DAMAGE, 5.0D)
			.add(Attributes.ARMOR, 12.0D)
			.add(Attributes.KNOCKBACK_RESISTANCE, 1.0D);
	}

	@Override
	protected void tickDeath() {
		if (deathTime == 0) {
			stopAllAnimStates();
			deathAnimationState.start(tickCount);
			setBehaviorState(0);
		}
		++deathTime;
		if (deathTime == 75 && !level().isClientSide()) {
			level().broadcastEntityEvent(this, (byte) 60);
			remove(Entity.RemovalReason.KILLED);
		}
	}

	public void stopAllAnimStates() {
		toxicBreathAnimationState.stop();
		sporeAnimationState.stop();
		thornAnimationState.stop();
		biteAnimationState.stop();
		digAnimationState.stop();
		sneakAnimationState.stop();
		emergeAnimationState.stop();
		switchPhaseAnimationState.stop();
	}

	@Override
	public void onSyncedDataUpdated(EntityDataAccessor<?> accessor) {
		if (accessor.equals(BEHAVIOR_STATE) && getBehaviorState() != 0) {
			stopAllAnimStates();
			switch (getBehaviorState()) {
				case LunarMonstrosityToxicBreathPhase.ID -> toxicBreathAnimationState.start(tickCount);
				case LunarMonstrositySporePhase.ID -> sporeAnimationState.start(tickCount);
				case LunarMonstrosityThornPhase.ID -> thornAnimationState.start(tickCount);
				case LunarMonstrosityBitePhase.ID -> biteAnimationState.start(tickCount);
				case LunarMonstrosityDigPhase.ID -> digAnimationState.start(tickCount);
				case LunarMonstrositySneakPhase.ID -> sneakAnimationState.start(tickCount);
				case LunarMonstrosityEmergePhase.ID -> emergeAnimationState.start(tickCount);
				case LunarMonstrositySoulPhase.ID -> switchPhaseAnimationState.start(tickCount);
			}
		}
		super.onSyncedDataUpdated(accessor);
	}

	@Override
	protected void blockedByShield(LivingEntity blocker) {
		if (getBehaviorState() == LunarMonstrosityBitePhase.ID && getTarget() != null && blocker.getUUID().equals(getTarget().getUUID())) {
			setBehaviorState(LunarMonstrosityStunPhase.ID);
		}
		super.blockedByShield(blocker);
	}

	@Override
	public boolean doHurtTarget(Entity target) {
		if (getBehaviorState() == LunarMonstrositySneakPhase.ID) {
			setBehaviorState(LunarMonstrosityEmergePhase.ID);
			setBehaviorTicks(0);
		}
		return super.doHurtTarget(target);
	}

	@Override
	public boolean hurt(DamageSource source, float amount) {
		if (source.is(DamageTypeTags.BYPASSES_INVULNERABILITY)) {
			return super.hurt(source, amount);
		}
		if (getBehaviorState() == LunarMonstrositySneakPhase.ID || (source.getEntity() != null && source.getEntity().getType().is(ESTags.EntityTypes.LUNAR_MONSTROSITY_ALLYS))) {
			return false;
		}
		if (getPhase() == 0 && getHealth() / getMaxHealth() < 0.5) {
			setPhase(1);
			setBehaviorState(LunarMonstrositySoulPhase.ID);
			setBehaviorTicks(0);
			return super.hurt(source, amount / 2f);
		}
		if (source.getEntity() != null && getTarget() != null) {
			if (getBehaviorState() == LunarMonstrosityBitePhase.ID && source.getEntity().getUUID().equals(getTarget().getUUID()) && amount >= 6) {
				setBehaviorState(LunarMonstrosityStunPhase.ID);
				setBehaviorTicks(0);
			}
		}
		if (isOnFire() || getBehaviorState() == LunarMonstrosityStunPhase.ID) {
			return super.hurt(source, amount);
		} else {
			if (source.getEntity() instanceof Player player) {
				player.displayClientMessage(Component.translatable(getType().getDescriptionId() + ".tip"), true);
			}
			return super.hurt(source, Math.min(1, amount));
		}
	}

	@Override
	public boolean isAlliedTo(Entity entity) {
		return super.isAlliedTo(entity) || entity.getType().is(ESTags.EntityTypes.LUNAR_MONSTROSITY_ALLYS);
	}

	@Override
	protected InteractionResult mobInteract(Player player, InteractionHand hand) {
		ItemStack itemStack = player.getItemInHand(hand);
		if (itemStack.is(ESTags.Items.LUNAR_MONSTROSITY_IGNITERS)) {
			SoundEvent soundEvent = itemStack.isDamageableItem() ? SoundEvents.FLINTANDSTEEL_USE : SoundEvents.FIRECHARGE_USE;
			this.level().playSound(player, this.getX(), this.getY(), this.getZ(), soundEvent, this.getSoundSource(), 1.0F, this.random.nextFloat() * 0.4F + 0.8F);
			if (!this.level().isClientSide) {
				setRemainingFireTicks(Math.max(getRemainingFireTicks(), getPhase() == 0 ? 100 : 20));
				if (!itemStack.isDamageableItem()) {
					itemStack.consume(1, player);
				} else {
					itemStack.hurtAndBreak(1, player, getSlotForHand(hand));
				}
			}
			return InteractionResult.sidedSuccess(this.level().isClientSide);
		} else {
			return super.mobInteract(player, hand);
		}
	}

	@Override
	public EntityDimensions getDefaultDimensions(Pose pose) {
		return getBehaviorState() == LunarMonstrositySneakPhase.ID ? super.getDefaultDimensions(pose).scale(0.1f) : super.getDefaultDimensions(pose);
	}

	@Override
	public boolean displayFireAnimation() {
		return getBehaviorState() != LunarMonstrositySneakPhase.ID && super.displayFireAnimation();
	}

	@Override
	public SoundEvent getBossMusic() {
		return ESSoundEvents.MUSIC_BOSS_LUNAR_MONSTROSITY.get();
	}

	public boolean canBite() {
		LivingEntity target = getTarget();
		if (target == null) return false;
		for (LivingEntity livingEntity : level().getNearbyEntities(LivingEntity.class, TargetingConditions.DEFAULT, this, getBoundingBox().inflate(3))) {
			Vec3 vec3 = livingEntity.position().vectorTo(this.position()).normalize();
			vec3 = new Vec3(vec3.x, 0.0D, vec3.z);
			if (vec3.dot(this.getViewVector(1.0F)) < 0.0D && target.getUUID().equals(livingEntity.getUUID())) {
				return true;
			}
		}
		return false;
	}

	public void doBiteDamage(float damage) {
		for (LivingEntity livingEntity : level().getNearbyEntities(LivingEntity.class, TargetingConditions.DEFAULT, this, getBoundingBox().inflate(3))) {
			Vec3 vec3 = livingEntity.position().vectorTo(this.position()).normalize();
			vec3 = new Vec3(vec3.x, 0.0D, vec3.z);
			if (vec3.dot(this.getViewVector(1.0F)) < 0.0D) {
				livingEntity.hurt(ESDamageTypes.getEntityDamageSource(level(), ESDamageTypes.BITE, this), damage);
			}
		}
	}

	public void knockbackNearbyEntities(float radius, float strength, boolean damage) {
		for (LivingEntity living : level().getNearbyEntities(LivingEntity.class, TargetingConditions.DEFAULT, this, getBoundingBox().inflate(radius, 0, radius))) {
			if (!living.getType().is(ESTags.EntityTypes.LUNAR_MONSTROSITY_ALLYS)) {
				Vec3 motion = living.position().subtract(position()).normalize().scale(strength);
				living.hurtMarked = true;
				living.setDeltaMovement(living.getDeltaMovement().add(motion));
				if (damage) {
					living.hurt(damageSources().mobAttack(this), strength * 3);
				}
			}
		}
		if (level() instanceof ServerLevel serverLevel) {
			ESPlatform.INSTANCE.sendToAllClients(serverLevel, new ParticlePacket(RingExplosionParticleOptions.LUNAR, getX(), getY(), getZ(), 0, 0.2, 0));
		}
	}

	@Override
	public boolean ignoreExplosion(Explosion explosion) {
		LivingEntity cause = explosion.getIndirectSourceEntity();
		return cause != null && cause.getType().is(ESTags.EntityTypes.LUNAR_MONSTROSITY_ALLYS);
	}

	@Override
	public void aiStep() {
		super.aiStep();
		bossEvent.update();
		refreshDimensions();
		if (!level().isClientSide) {
			if (getTarget() != null && !getTarget().isAlive()) {
				setTarget(null);
			}
			if (!isNoAi() && isAlive()) {
				behaviorManager.tick();
			}
			if (getBehaviorState() != LunarMonstrositySneakPhase.ID) {
				getNavigation().stop();
			}
		} else {
			level().addParticle(ESSmokeParticleOptions.LUNAR_SHORT, getX() + (getRandom().nextDouble() - 0.5) * 3, getY() + 1 + (getRandom().nextDouble() - 0.5) * 3, getZ() + (getRandom().nextDouble() - 0.5) * 3, 0, 0, 0);
			if (deathTime <= 0) {
				if (getBehaviorState() == LunarMonstrositySneakPhase.ID) {
					RandomSource randomsource = this.getRandom();
					BlockState blockstate = this.getBlockStateOn();
					if (blockstate.getRenderShape() != RenderShape.INVISIBLE) {
						for (int i = 0; i < 30; ++i) {
							double d0 = this.getX() + (double) Mth.randomBetween(randomsource, -0.7F, 0.7F);
							double d1 = this.getY();
							double d2 = this.getZ() + (double) Mth.randomBetween(randomsource, -0.7F, 0.7F);
							level().addParticle(new BlockParticleOption(ParticleTypes.BLOCK, blockstate), d0, d1, d2, 0.0D, 0.0D, 0.0D);
						}
					}
				} else if (getBehaviorState() == LunarMonstrosityStunPhase.ID) {
					level().addParticle(ParticleTypes.CRIT, headPos.x + getRandom().nextDouble() - 0.5, headPos.y, headPos.z + getRandom().nextDouble() - 0.5, getRandom().nextDouble() / 10, 0.8, getRandom().nextDouble() / 10);
				}
			}
		}
	}

	@Override
	protected SoundEvent getHurtSound(DamageSource damageSource) {
		return ESSoundEvents.LUNAR_MONSTROSITY_HURT.get();
	}

	@Override
	protected SoundEvent getDeathSound() {
		return ESSoundEvents.LUNAR_MONSTROSITY_DEATH.get();
	}
}

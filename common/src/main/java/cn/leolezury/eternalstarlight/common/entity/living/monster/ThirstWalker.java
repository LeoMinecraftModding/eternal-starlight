package cn.leolezury.eternalstarlight.common.entity.living.monster;

import cn.leolezury.eternalstarlight.common.entity.living.phase.BehaviorManager;
import cn.leolezury.eternalstarlight.common.entity.living.phase.MeleeAttackPhase;
import cn.leolezury.eternalstarlight.common.entity.living.phase.MultiBehaviorUser;
import cn.leolezury.eternalstarlight.common.registry.ESSoundEvents;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.util.TimeUtil;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.ResetUniversalAngerTargetGoal;
import net.minecraft.world.entity.ai.targeting.TargetingConditions;
import net.minecraft.world.entity.ai.util.LandRandomPos;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.UUID;

public class ThirstWalker extends Monster implements MultiBehaviorUser, NeutralMob {
	private static final String TAG_HUNGER_LEVEL = "hunger_level";

	private static final UniformInt PERSISTENT_ANGER_TIME = TimeUtil.rangeOfSeconds(20, 39);
	private int remainingPersistentAngerTime;
	@Nullable
	private UUID persistentAngerTarget;
	private float hungerLevel = 1;
	private Vec3 fleeTo;
	private int fleeTicks;
	private Entity fleeFrom;
	private static final int MELEE_ID = 1;

	public AnimationState idleAnimationState = new AnimationState();
	public AnimationState meleeAnimationState = new AnimationState();

	protected static final EntityDataAccessor<Integer> BEHAVIOR_STATE = SynchedEntityData.defineId(ThirstWalker.class, EntityDataSerializers.INT);

	public int getBehaviorState() {
		return this.getEntityData().get(BEHAVIOR_STATE);
	}

	public void setBehaviorState(int attackState) {
		this.getEntityData().set(BEHAVIOR_STATE, attackState);
	}

	protected static final EntityDataAccessor<Integer> BEHAVIOR_TICKS = SynchedEntityData.defineId(ThirstWalker.class, EntityDataSerializers.INT);

	public int getBehaviorTicks() {
		return this.getEntityData().get(BEHAVIOR_TICKS);
	}

	public void setBehaviorTicks(int behaviourTicks) {
		this.getEntityData().set(BEHAVIOR_TICKS, behaviourTicks);
	}

	protected static final EntityDataAccessor<Boolean> INTENTIONAL_ATTACK = SynchedEntityData.defineId(ThirstWalker.class, EntityDataSerializers.BOOLEAN);

	public boolean isIntentionalAttack() {
		return this.getEntityData().get(INTENTIONAL_ATTACK);
	}

	public void setIntentionalAttack(boolean intentionalAttack) {
		this.getEntityData().set(INTENTIONAL_ATTACK, intentionalAttack);
	}

	private final BehaviorManager<ThirstWalker> behaviorManager = new BehaviorManager<>(this, List.of(
		new MeleeAttackPhase<ThirstWalker>(MELEE_ID, 1, 20, 10).with(2, 7)
	));

	public ThirstWalker(EntityType<? extends Monster> entityType, Level level) {
		super(entityType, level);
	}

	@Override
	protected void defineSynchedData(SynchedEntityData.Builder builder) {
		super.defineSynchedData(builder);
		builder.define(BEHAVIOR_STATE, 0)
			.define(BEHAVIOR_TICKS, 0)
			.define(INTENTIONAL_ATTACK, false);
	}

	@Override
	protected void registerGoals() {
		this.goalSelector.addGoal(0, new FloatGoal(this));
		this.goalSelector.addGoal(1, new MeleeAttackGoal(this, 1.0, false) {
			@Override
			protected void checkAndPerformAttack(LivingEntity livingEntity) {

			}
		});
		this.goalSelector.addGoal(2, new WaterAvoidingRandomStrollGoal(this, 1.0, 0.0F));
		this.goalSelector.addGoal(3, new LookAtPlayerGoal(this, Player.class, 8.0F));
		this.goalSelector.addGoal(4, new RandomLookAroundGoal(this));
		this.targetSelector.addGoal(0, new HurtByTargetGoal(this));
		this.targetSelector.addGoal(1, new ResetUniversalAngerTargetGoal<>(this, false));
	}

	public static AttributeSupplier.Builder createAttributes() {
		return Monster.createMonsterAttributes()
			.add(Attributes.MAX_HEALTH, 40.0)
			.add(Attributes.MOVEMENT_SPEED, 0.3)
			.add(Attributes.ATTACK_DAMAGE, 7.0)
			.add(Attributes.FOLLOW_RANGE, 64.0)
			.add(Attributes.STEP_HEIGHT, 1.0);
	}

	@Override
	public void setTarget(@Nullable LivingEntity livingEntity) {
		super.setTarget(livingEntity);
		if (livingEntity != null) {
			setIntentionalAttack(false);
		}
	}

	@Override
	public boolean doHurtTarget(Entity entity) {
		boolean flag = super.doHurtTarget(entity);
		if (flag && this.getMainHandItem().isEmpty() && entity instanceof LivingEntity living) {
			float f = this.level().getCurrentDifficultyAt(this.blockPosition()).getEffectiveDifficulty();
			living.addEffect(new MobEffectInstance(MobEffects.HUNGER, 140 * (int) f), this);
			hungerLevel = Math.min(hungerLevel + (isIntentionalAttack() ? 0.6f : 0.1f), 1);
			if (isIntentionalAttack()) {
				stopBeingAngry();
				fleeFrom = entity;
				fleeTicks = 100;
				tryFlee();
			}
		}
		return flag;
	}

	@Override
	protected void customServerAiStep() {
		super.customServerAiStep();
		if (getTarget() != null && !getTarget().isAlive()) {
			setTarget(null);
		}
		if (!isNoAi()) {
			this.behaviorManager.tick();
		}
		if (fleeTicks > 0) {
			fleeTicks--;
			if (this.tickCount % 20 == 0) {
				tryFlee();
			}
		}
	}

	private void tryFlee() {
		if (fleeFrom != null) {
			Vec3 fleePos = LandRandomPos.getPosAway(this, 20, 8, fleeFrom.position());
			if (fleePos != null) {
				fleeTo = fleePos;
			}
		}
		if (fleeTo != null) {
			this.getNavigation().stop();
			this.getNavigation().moveTo(fleeTo.x, fleeTo.y, fleeTo.z, 1.5);
			this.getMoveControl().setWantedPosition(fleeTo.x, fleeTo.y, fleeTo.z, 1.5);
		}
	}

	@Override
	public void tick() {
		super.tick();
		if (!level().isClientSide && level() instanceof ServerLevel serverLevel) {
			this.updatePersistentAnger(serverLevel, true);
			if (hungerLevel < 0.3 && getTarget() == null) {
				List<Player> players = level().getNearbyEntities(Player.class, TargetingConditions.DEFAULT, this, getBoundingBox().inflate(20));
				if (!players.isEmpty()) {
					Player target = players.get(getRandom().nextInt(players.size()));
					setTarget(target);
					setIntentionalAttack(true);
				}
			}
			hungerLevel = Math.max(hungerLevel - 0.001f, 0);
		}
		if (level().isClientSide) {
			idleAnimationState.startIfStopped(tickCount);
		}
	}

	@Override
	public void onSyncedDataUpdated(EntityDataAccessor<?> accessor) {
		if (accessor.equals(BEHAVIOR_STATE) && getBehaviorState() != 0) {
			if (getBehaviorState() == MELEE_ID) {
				meleeAnimationState.start(tickCount);
			} else {
				meleeAnimationState.stop();
			}
		}
		super.onSyncedDataUpdated(accessor);
	}

	@Override
	public void addAdditionalSaveData(CompoundTag compoundTag) {
		super.addAdditionalSaveData(compoundTag);
		this.addPersistentAngerSaveData(compoundTag);
		compoundTag.putFloat(TAG_HUNGER_LEVEL, hungerLevel);
	}

	@Override
	public void readAdditionalSaveData(CompoundTag compoundTag) {
		super.readAdditionalSaveData(compoundTag);
		this.readPersistentAngerSaveData(this.level(), compoundTag);
		if (compoundTag.contains(TAG_HUNGER_LEVEL, CompoundTag.TAG_FLOAT)) {
			hungerLevel = compoundTag.getFloat(TAG_HUNGER_LEVEL);
		}
	}

	@Nullable
	@Override
	protected SoundEvent getAmbientSound() {
		return ESSoundEvents.THIRST_WALKER_AMBIENT.get();
	}

	@Override
	protected SoundEvent getHurtSound(DamageSource damageSource) {
		return ESSoundEvents.THIRST_WALKER_HURT.get();
	}

	@Override
	protected SoundEvent getDeathSound() {
		return ESSoundEvents.THIRST_WALKER_DEATH.get();
	}

	@Override
	public int getRemainingPersistentAngerTime() {
		return remainingPersistentAngerTime;
	}

	@Override
	public void setRemainingPersistentAngerTime(int i) {
		this.remainingPersistentAngerTime = i;
	}

	@Nullable
	@Override
	public UUID getPersistentAngerTarget() {
		return persistentAngerTarget;
	}

	@Override
	public void setPersistentAngerTarget(@Nullable UUID uuid) {
		this.persistentAngerTarget = uuid;
	}

	@Override
	public void startPersistentAngerTimer() {
		this.setRemainingPersistentAngerTime(PERSISTENT_ANGER_TIME.sample(this.random));
	}
}

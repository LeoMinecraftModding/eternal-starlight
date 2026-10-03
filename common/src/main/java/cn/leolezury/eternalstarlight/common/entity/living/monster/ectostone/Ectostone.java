package cn.leolezury.eternalstarlight.common.entity.living.monster.ectostone;

import cn.leolezury.eternalstarlight.common.config.ESConfig;
import cn.leolezury.eternalstarlight.common.data.ESEctostoneVariants;
import cn.leolezury.eternalstarlight.common.data.ESRegistries;
import cn.leolezury.eternalstarlight.common.entity.living.goal.LookAtTargetGoal;
import cn.leolezury.eternalstarlight.common.entity.living.goal.RandomFlyGoal;
import cn.leolezury.eternalstarlight.common.entity.living.phase.BehaviorManager;
import cn.leolezury.eternalstarlight.common.entity.living.phase.MultiBehaviorUser;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.core.particles.DustColorTransitionOptions;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.control.FlyingMoveControl;
import net.minecraft.world.entity.ai.control.MoveControl;
import net.minecraft.world.entity.ai.goal.FloatGoal;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.ai.navigation.FlyingPathNavigation;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.npc.AbstractVillager;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.joml.Vector3f;

import java.util.EnumSet;
import java.util.List;
import java.util.Optional;

public class Ectostone extends Monster implements MultiBehaviorUser, VariantHolder<Holder<EctostoneVariant>> {
	private static final String TAG_VARIANT = "variant";
	private static final String TAG_DORMANT = "dormant";
	private static final String TAG_FLIGHT_ALTITUDE = "flight_altitude";

	public static final double GROUND_CHECK_DISTANCE = 16;
	public static final double SLEEP_GROUND_DISTANCE = 8;

	public final Vec3[] particlePositions = new Vec3[4];
	public AnimationState idleAnimationState = new AnimationState();
	public AnimationState fallAsleepAnimationState = new AnimationState();
	public AnimationState wakeUpAnimationState = new AnimationState();
	public AnimationState shootAnimationState = new AnimationState();
	public AnimationState continuousShootAnimationState = new AnimationState();
	public AnimationState smashAnimationState = new AnimationState();
	public AnimationState smashTransitionAnimationState = new AnimationState();
	public AnimationState smashEndAnimationState = new AnimationState();

	protected static final EntityDataAccessor<String> VARIANT = SynchedEntityData.defineId(Ectostone.class, EntityDataSerializers.STRING);
	protected static final EntityDataAccessor<Boolean> DORMANT = SynchedEntityData.defineId(Ectostone.class, EntityDataSerializers.BOOLEAN);
	protected static final EntityDataAccessor<Integer> BEHAVIOR_STATE = SynchedEntityData.defineId(Ectostone.class, EntityDataSerializers.INT);
	protected static final EntityDataAccessor<Integer> BEHAVIOR_TICKS = SynchedEntityData.defineId(Ectostone.class, EntityDataSerializers.INT);

	private final MoveControl restingMoveControl = new MoveControl(this) {
		@Override
		public void tick() {
		}
	};
	private final MoveControl flyingMoveControl;

	private double flightAltitude = Double.NaN;

	private final BehaviorManager<Ectostone> behaviorManager = new BehaviorManager<>(this, List.of(
		new EctostoneShootPhase(),
		new EctostoneSmashPhase(),
		new EctostoneSmashTransitionPhase(),
		new EctostoneSmashEndPhase(),
		new EctostoneContinuousShootPhase(),
		new EctostoneFallAsleepPhase(),
		new EctostoneWakeUpPhase()
	));

	public Ectostone(EntityType<? extends Ectostone> entityType, Level level) {
		super(entityType, level);
		this.flyingMoveControl = new FlyingMoveControl(this, 5, true);
		this.moveControl = this.flyingMoveControl;
		this.setNoGravity(true);
	}

	@Override
	protected void defineSynchedData(SynchedEntityData.Builder builder) {
		super.defineSynchedData(builder);
		builder.define(VARIANT, ESEctostoneVariants.GRIMSTONE.location().toString());
		builder.define(DORMANT, false);
		builder.define(BEHAVIOR_STATE, 0);
		builder.define(BEHAVIOR_TICKS, 0);
	}

	@Override
	protected PathNavigation createNavigation(Level level) {
		FlyingPathNavigation navigation = new FlyingPathNavigation(this, level) {
			@Override
			public boolean isStableDestination(BlockPos blockPos) {
				return this.level.getBlockState(blockPos).isAir();
			}
		};
		navigation.setCanOpenDoors(true);
		navigation.setCanFloat(true);
		navigation.setCanPassDoors(true);
		return navigation;
	}

	@Override
	public void onSyncedDataUpdated(EntityDataAccessor<?> accessor) {
		if (accessor.equals(BEHAVIOR_STATE)) {
			stopAllAnimStates();
			switch (getBehaviorState()) {
				case EctostoneShootPhase.ID -> shootAnimationState.start(tickCount);
				case EctostoneSmashPhase.ID -> smashAnimationState.start(tickCount);
				case EctostoneSmashTransitionPhase.ID -> smashTransitionAnimationState.start(tickCount);
				case EctostoneSmashEndPhase.ID -> smashEndAnimationState.start(tickCount);
				case EctostoneContinuousShootPhase.ID -> continuousShootAnimationState.start(tickCount);
				case EctostoneFallAsleepPhase.ID -> fallAsleepAnimationState.start(tickCount);
				case EctostoneWakeUpPhase.ID -> wakeUpAnimationState.start(tickCount);
			}
		}
		super.onSyncedDataUpdated(accessor);
	}

	public void stopAllAnimStates() {
		fallAsleepAnimationState.stop();
		wakeUpAnimationState.stop();
		shootAnimationState.stop();
		continuousShootAnimationState.stop();
		smashAnimationState.stop();
		smashTransitionAnimationState.stop();
		smashEndAnimationState.stop();
	}

	public ResourceLocation getVariantId() {
		return ResourceLocation.parse(this.getEntityData().get(VARIANT));
	}

	public void setVariantId(ResourceLocation variant) {
		this.getEntityData().set(VARIANT, variant.toString());
	}

	@Override
	public void setVariant(Holder<EctostoneVariant> variant) {
		if (variant.isBound()) {
			ResourceLocation key = level().registryAccess().registryOrThrow(ESRegistries.ECTOSTONE_VARIANT).getKey(variant.value());
			if (key != null) {
				setVariantId(key);
			}
		}
	}

	@Override
	public Holder<EctostoneVariant> getVariant() {
		Registry<EctostoneVariant> variants = level().registryAccess().registryOrThrow(ESRegistries.ECTOSTONE_VARIANT);
		Optional<Holder.Reference<EctostoneVariant>> optional = variants.getHolder(getVariantId());
		return optional.isPresent() ? optional.get() : EctostoneVariant.getDefault(level().registryAccess());
	}

	public boolean isDormant() {
		return this.entityData.get(DORMANT);
	}

	public void setDormant(boolean dormant) {
		this.entityData.set(DORMANT, dormant);
	}

	public boolean isFallingAsleep() {
		return getBehaviorState() == EctostoneFallAsleepPhase.ID;
	}

	public boolean isWakingUp() {
		return getBehaviorState() == EctostoneWakeUpPhase.ID;
	}

	public boolean isFreeToMove() {
		return !isDormant() && getBehaviorState() == 0;
	}

	public int getBehaviorState() {
		return this.getEntityData().get(BEHAVIOR_STATE);
	}

	public void setBehaviorState(int state) {
		this.getEntityData().set(BEHAVIOR_STATE, state);
	}

	public int getBehaviorTicks() {
		return this.getEntityData().get(BEHAVIOR_TICKS);
	}

	public void setBehaviorTicks(int ticks) {
		this.getEntityData().set(BEHAVIOR_TICKS, ticks);
	}

	@Override
	public BehaviorManager<Ectostone> getBehaviorManager() {
		return behaviorManager;
	}

	@Override
	protected void registerGoals() {
		goalSelector.addGoal(0, new EctostoneDoNothingGoal());
		goalSelector.addGoal(1, new FloatGoal(this));
		goalSelector.addGoal(2, new EctostoneFlyToTargetGoal());
		goalSelector.addGoal(3, new RandomFlyGoal(this, () -> getVariant().value().skyFlying() ? getFlightAltitude() : Double.NaN) {
			@Override
			public boolean canUse() {
				return super.canUse() && Ectostone.this.isFreeToMove() && Ectostone.this.getTarget() == null;
			}

			@Override
			public boolean canContinueToUse() {
				return super.canContinueToUse() && Ectostone.this.isFreeToMove() && Ectostone.this.getTarget() == null;
			}
		});
		goalSelector.addGoal(4, new LookAtTargetGoal(this));
		goalSelector.addGoal(5, new LookAtPlayerGoal(this, Player.class, 3.0F, 1.0F));
		goalSelector.addGoal(6, new LookAtPlayerGoal(this, Mob.class, 8.0F));
		this.targetSelector.addGoal(0, new HurtByTargetGoal(this).setAlertOthers());
		this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, AbstractVillager.class, true));
		this.targetSelector.addGoal(1, new NearestAttackableTargetGoal<>(this, Player.class, true));
	}

	class EctostoneFlyToTargetGoal extends Goal {
		public EctostoneFlyToTargetGoal() {
			setFlags(EnumSet.of(Flag.MOVE));
		}

		@Override
		public boolean canUse() {
			return isFreeToMove() && getTarget() != null;
		}

		@Override
		public boolean canContinueToUse() {
			return isFreeToMove() && getTarget() != null;
		}

		@Override
		public boolean requiresUpdateEveryTick() {
			return true;
		}

		@Override
		public void tick() {
			LivingEntity target = getTarget();
			if (target != null) {
				Vec3 targetPos = target.position();
				BlockHitResult result = level().clip(new ClipContext(targetPos.add(0, target.getBbHeight() / 2, 0), targetPos.add(0, target.getBbHeight() / 2 + getBbHeight(), 0), ClipContext.Block.COLLIDER, ClipContext.Fluid.NONE, Ectostone.this));
				if (result.getType() != HitResult.Type.MISS) {
					targetPos = result.getLocation();
				} else {
					targetPos = targetPos.add(0, target.getBbHeight() / 2 + getBbHeight(), 0);
				}
				getNavigation().moveTo(targetPos.x, targetPos.y - getBbHeight(), targetPos.z, 1);
			}
		}
	}

	class EctostoneDoNothingGoal extends Goal {
		public EctostoneDoNothingGoal() {
			this.setFlags(EnumSet.of(Flag.MOVE, Flag.JUMP, Flag.LOOK));
		}

		@Override
		public boolean canUse() {
			return Ectostone.this.isDormant();
		}

		@Override
		public boolean requiresUpdateEveryTick() {
			return true;
		}

		@Override
		public void tick() {
			Ectostone.this.stopInPlace();
		}
	}

	public static AttributeSupplier.Builder createAttributes() {
		return Monster.createMonsterAttributes()
			.add(Attributes.MAX_HEALTH, ESConfig.INSTANCE.mobsConfig.ectostone.maxHealth())
			.add(Attributes.ARMOR, ESConfig.INSTANCE.mobsConfig.ectostone.armor())
			.add(Attributes.ATTACK_DAMAGE, ESConfig.INSTANCE.mobsConfig.ectostone.attackDamage())
			.add(Attributes.FOLLOW_RANGE, ESConfig.INSTANCE.mobsConfig.ectostone.followRange())
			.add(Attributes.MOVEMENT_SPEED, 0.3)
			.add(Attributes.FLYING_SPEED, 1);
	}

	public double getFlightAltitude() {
		if (Double.isNaN(flightAltitude)) {
			flightAltitude = getY();
		}
		return flightAltitude;
	}

	public boolean hasGroundBelow(double distance) {
		return level().clip(new ClipContext(position(), position().subtract(0, distance, 0), ClipContext.Block.COLLIDER, ClipContext.Fluid.NONE, this)).getType() != HitResult.Type.MISS;
	}

	@Override
	public boolean hurt(@NotNull DamageSource source, float amount) {
		if (source.is(DamageTypeTags.BYPASSES_INVULNERABILITY)) {
			return super.hurt(source, amount);
		}
		return !this.isDormant() && super.hurt(source, amount);
	}

	@Override
	public boolean isPushable() {
		return super.isPushable() && !this.isDormant();
	}

	@Override
	public void aiStep() {
		super.aiStep();
		boolean dormant = isDormant();
		if (level().isClientSide) {
			if (dormant) {
				idleAnimationState.stop();
			} else {
				idleAnimationState.startIfStopped(tickCount);
				for (Vec3 pos : this.particlePositions) {
					if (pos == null) continue;
					for (int i = 0; i < 2; i++) {
						this.level().addParticle(
							new DustColorTransitionOptions(
								new Vector3f(255 / 255F, 71 / 255F, 180 / 255F),
								new Vector3f(151 / 255F, 63 / 255F, 115 / 255F),
								1.2F
							),
							pos.x, pos.y, pos.z,
							0, 0, 0
						);
					}
				}
			}
		} else {
			if (getTarget() != null && !getTarget().isAlive()) {
				setTarget(null);
			}
			if (!isNoAi() && isAlive()) {
				this.behaviorManager.tick();
			}
		}
		setNoGravity(!dormant);
		moveControl = dormant ? restingMoveControl : flyingMoveControl;
	}

	@Override
	public boolean causeFallDamage(float f, float g, DamageSource damageSource) {
		return false;
	}

	@Override
	protected void checkFallDamage(double y, boolean onGround, BlockState state, BlockPos pos) {
	}

	@Override
	public SpawnGroupData finalizeSpawn(ServerLevelAccessor level, DifficultyInstance instance, MobSpawnType spawnType, @Nullable SpawnGroupData data) {
		setVariant(EctostoneVariant.getSpawnVariant(level.registryAccess(), level.getBiome(blockPosition()), level, blockPosition(), level.getRandom()));
		return super.finalizeSpawn(level, instance, spawnType, data);
	}

	@Override
	public void readAdditionalSaveData(CompoundTag compoundTag) {
		super.readAdditionalSaveData(compoundTag);
		setVariantId(ResourceLocation.read(compoundTag.getString(TAG_VARIANT)).getOrThrow());
		setDormant(compoundTag.getBoolean(TAG_DORMANT));
		if (compoundTag.contains(TAG_FLIGHT_ALTITUDE)) {
			flightAltitude = compoundTag.getDouble(TAG_FLIGHT_ALTITUDE);
		}
	}

	@Override
	public void addAdditionalSaveData(CompoundTag compoundTag) {
		super.addAdditionalSaveData(compoundTag);
		compoundTag.putString(TAG_VARIANT, getVariantId().toString());
		compoundTag.putBoolean(TAG_DORMANT, this.isDormant());
		if (!Double.isNaN(flightAltitude)) {
			compoundTag.putDouble(TAG_FLIGHT_ALTITUDE, flightAltitude);
		}
	}

	public static boolean checkEctostoneSpawnRules(EntityType<? extends Ectostone> type, LevelAccessor level, MobSpawnType spawnType, BlockPos pos, RandomSource random) {
		return checkAnyLightMonsterSpawnRules(type, level, spawnType, pos, random) && ESConfig.INSTANCE.mobsConfig.ectostone.canSpawn()
			&& EctostoneVariant.getCandidates(level.registryAccess(), level.getBiome(pos)).stream().anyMatch(candidate -> candidate.value().matchesStone(level, pos));
	}
}

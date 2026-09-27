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
import net.minecraft.world.entity.ai.targeting.TargetingConditions;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.npc.AbstractVillager;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
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
import java.util.function.BooleanSupplier;

public class Ectostone extends Monster implements MultiBehaviorUser, VariantHolder<Holder<EctostoneVariant>> {
	private static final String TAG_VARIANT = "variant";
	private static final String TAG_DORMANCY = "dormancy";
	private static final String TAG_DORMANCY_STATE = "dormancy_state";

	public static final int DORMANCY_STATE_NORMAL = 0;
	public static final int DORMANCY_STATE_AWAKENING = 1;
	public static final int DORMANCY_STATE_FALLING_ASLEEP = 2;

	public Vec3 particle1Pos = Vec3.ZERO;
	public Vec3 particle2Pos = Vec3.ZERO;
	public Vec3 particle3Pos = Vec3.ZERO;
	public Vec3 particle4Pos = Vec3.ZERO;
	public AnimationState idleAnimationState = new AnimationState();
	public AnimationState dormancyAnimationState = new AnimationState();
	public AnimationState startdormancyAnimationState = new AnimationState();
	public AnimationState awakeAnimationState = new AnimationState();
	public AnimationState shootAnimationState = new AnimationState();
	public AnimationState coshootAnimationState = new AnimationState();
	public AnimationState smashAnimationState = new AnimationState();
	protected static final EntityDataAccessor<String> VARIANT = SynchedEntityData.defineId(Ectostone.class, EntityDataSerializers.STRING);
	protected static final EntityDataAccessor<Boolean> IS_DORMANCY = SynchedEntityData.defineId(Ectostone.class, EntityDataSerializers.BOOLEAN);
	protected static final EntityDataAccessor<Integer> BEHAVIOR_STATE = SynchedEntityData.defineId(Ectostone.class, EntityDataSerializers.INT);
	protected static final EntityDataAccessor<Integer> BEHAVIOR_TICKS = SynchedEntityData.defineId(Ectostone.class, EntityDataSerializers.INT);
	protected static final EntityDataAccessor<Integer> DORMANCY_STATE = SynchedEntityData.defineId(Ectostone.class, EntityDataSerializers.INT);
	protected static final EntityDataAccessor<Integer> DORMANCY_COOLDOWN = SynchedEntityData.defineId(Ectostone.class, EntityDataSerializers.INT);
	private final MoveControl groundMoveControl = new MoveControl(this);
	private final MoveControl flyingMoveControl;

	public ResourceLocation getVariantId() {
		return ResourceLocation.parse(this.getEntityData().get(VARIANT));
	}

	public void setVariantId(ResourceLocation variant) {
		this.getEntityData().set(VARIANT, variant.toString());
	}

	public boolean isDormancy() {
		return this.entityData.get(IS_DORMANCY);
	}

	public void setDormancy(boolean dormancy) {
		this.entityData.set(IS_DORMANCY, dormancy);
		if (dormancy) {
			this.setTarget(null);
			this.getNavigation().stop();
			this.setDormancyCooldown(100);
		}
	}

	public int getDormancyState() {
		return this.entityData.get(DORMANCY_STATE);
	}

	public void setDormancyState(int state) {
		this.entityData.set(DORMANCY_STATE, state);
	}

	public int getDormancyCooldown() {
		return this.entityData.get(DORMANCY_COOLDOWN);
	}

	public void setDormancyCooldown(int cooldown) {
		this.entityData.set(DORMANCY_COOLDOWN, cooldown);
	}

	public boolean isAwakening() {
		return getDormancyState() == DORMANCY_STATE_AWAKENING;
	}

	public boolean isFallingAsleep() {
		return getDormancyState() == DORMANCY_STATE_FALLING_ASLEEP;
	}

	public boolean isTrulyDormant() {
		return isDormancy() && getDormancyState() == DORMANCY_STATE_NORMAL;
	}

	public boolean isTransitioning() {
		return isAwakening() || isFallingAsleep();
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
		ResourceLocation key = getVariantId();
		Registry<EctostoneVariant> registry = level().registryAccess().registryOrThrow(ESRegistries.ECTOSTONE_VARIANT);
		Optional<Holder.Reference<EctostoneVariant>> optional = registry.getHolder(key);
		return optional.orElse(registry.getHolder(ESEctostoneVariants.GRIMSTONE).orElseThrow());
	}

	public Ectostone(EntityType<? extends Ectostone> entityType, Level level) {
		super(entityType, level);
		this.flyingMoveControl = new FlyingMoveControl(this, 5, true);
		this.moveControl = this.flyingMoveControl;
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

	private final BehaviorManager<Ectostone> behaviorManager = new BehaviorManager<>(this, List.of(
		new EctostoneShootPhase(),
		new EctostoneSmashPhase(),
		new EctostoneContinuousShootPhase()
	));

	@Override
	public BehaviorManager<Ectostone> getBehaviorManager() {
		return behaviorManager;
	}

	public void stopAllAnimStates() {
		startdormancyAnimationState.stop();
		shootAnimationState.stop();
		coshootAnimationState.stop();
		awakeAnimationState.stop();
		smashAnimationState.stop();
	}

	@Override
	public void onSyncedDataUpdated(EntityDataAccessor<?> accessor) {
		if (accessor.equals(BEHAVIOR_STATE)) {
			stopAllAnimStates();
			switch (getBehaviorState()) {
				case EctostoneShootPhase.ID -> shootAnimationState.start(tickCount);
				case EctostoneSmashPhase.ID -> smashAnimationState.start(tickCount);
				case EctostoneContinuousShootPhase.ID -> coshootAnimationState.start(tickCount);
			}
		}

		if (accessor.equals(DORMANCY_STATE)) {
			stopAllAnimStates();
			switch (getDormancyState()) {
				case DORMANCY_STATE_AWAKENING -> awakeAnimationState.start(tickCount);
				case DORMANCY_STATE_FALLING_ASLEEP -> startdormancyAnimationState.start(tickCount);
			}
			if (isDormancy()) {
				dormancyAnimationState.startIfStopped(tickCount);
			} else {
				dormancyAnimationState.stop();
			}
		}
		super.onSyncedDataUpdated(accessor);
	}

	@Override
	protected void defineSynchedData(SynchedEntityData.Builder builder) {
		super.defineSynchedData(builder);
		builder.define(VARIANT, ESEctostoneVariants.GRIMSTONE.location().toString());
		builder.define(IS_DORMANCY, false);
		builder.define(BEHAVIOR_STATE, 0);
		builder.define(BEHAVIOR_TICKS, 0);
		builder.define(DORMANCY_STATE, DORMANCY_STATE_NORMAL);
		builder.define(DORMANCY_COOLDOWN, 0);
	}

	@Override
	protected void registerGoals() {
		goalSelector.addGoal(0, new FreezeGoal(this, () -> this.isDormancy() || this.isFallingAsleep() || this.isAwakening()));
		goalSelector.addGoal(0, new FloatGoal(this));
		goalSelector.addGoal(1, new EctostoneFlyToTargetGoal());
		goalSelector.addGoal(2, new RandomFlyGoal(this) {
			@Override
			public boolean canUse() {
				return super.canUse() && !Ectostone.this.isDormancy() && !Ectostone.this.isFallingAsleep()
					&& !Ectostone.this.isAwakening()
					&& Ectostone.this.getTarget() == null && Ectostone.this.getBehaviorState() == 0;
			}

			@Override
			public boolean canContinueToUse() {
				return super.canContinueToUse() && !Ectostone.this.isDormancy() && !Ectostone.this.isFallingAsleep()
					&& !Ectostone.this.isAwakening()
					&& Ectostone.this.getTarget() == null && Ectostone.this.getBehaviorState() == 0;
			}
		});
		goalSelector.addGoal(3, new LookAtTargetGoal(this));
		goalSelector.addGoal(4, new LookAtPlayerGoal(this, Player.class, 3.0F, 1.0F));
		goalSelector.addGoal(5, new LookAtPlayerGoal(this, Mob.class, 8.0F));
		this.targetSelector.addGoal(0, new HurtByTargetGoal(this).setAlertOthers());
		this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, AbstractVillager.class, true));
		this.targetSelector.addGoal(1, new NearestAttackableTargetGoal<>(this, Player.class, true));
	}

	public class FreezeGoal extends Goal {
		private final Mob mob;
		private final BooleanSupplier supplier;

		public FreezeGoal(Mob mob, BooleanSupplier supplier) {
			this.mob = mob;
			this.supplier = supplier;
			this.setFlags(EnumSet.of(Flag.MOVE, Flag.JUMP, Flag.LOOK));
		}

		@Override
		public boolean canUse() {
			return this.supplier.getAsBoolean();
		}

		@Override
		public boolean canContinueToUse() {
			return this.supplier.getAsBoolean();
		}

		@Override
		public void tick() {
			super.tick();
			this.mob.setTarget(null);
			this.mob.getNavigation().stop();
		}

		@Override
		public boolean requiresUpdateEveryTick() {
			return true;
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

	public class EctostoneFlyToTargetGoal extends Goal {
		public EctostoneFlyToTargetGoal() {
			setFlags(EnumSet.of(Flag.MOVE));
		}

		@Override
		public boolean canUse() {
			return !Ectostone.this.isDormancy() && !Ectostone.this.isFallingAsleep()
				&& !Ectostone.this.isAwakening()
				&& Ectostone.this.getBehaviorState() == 0 && Ectostone.this.getTarget() != null;
		}

		@Override
		public boolean canContinueToUse() {
			return !Ectostone.this.isDormancy() && !Ectostone.this.isFallingAsleep()
				&& !Ectostone.this.isAwakening()
				&& Ectostone.this.getBehaviorState() == 0 && Ectostone.this.getTarget() != null;
		}

		@Override
		public boolean requiresUpdateEveryTick() {
			return true;
		}

		@Override
		public void tick() {
			LivingEntity target = Ectostone.this.getTarget();
			if (target != null) {
				Vec3 targetPos = target.position();
				BlockHitResult result = Ectostone.this.level().clip(new ClipContext(targetPos.add(0, target.getBbHeight() / 2, 0), targetPos.add(0, target.getBbHeight() / 2 + Ectostone.this.getBbHeight(), 0), ClipContext.Block.COLLIDER, ClipContext.Fluid.NONE, Ectostone.this));
				if (result.getType() != HitResult.Type.MISS) {
					targetPos = result.getLocation();
				} else {
					targetPos = targetPos.add(0, target.getBbHeight() / 2 + Ectostone.this.getBbHeight(), 0);
				}
				Ectostone.this.getNavigation().moveTo(targetPos.x, targetPos.y - Ectostone.this.getBbHeight(), targetPos.z, 1);
			}
		}
	}

	@Override
	public boolean hurt(@NotNull DamageSource source, float amount) {
		if (source.is(DamageTypeTags.BYPASSES_INVULNERABILITY)) {
			return super.hurt(source, amount);
		}
		return !this.isTrulyDormant() && super.hurt(source, amount);
	}

	@Override
	public boolean isPushable() {
		return super.isPushable() && !this.isTrulyDormant();
	}

	@Override
	protected boolean shouldDespawnInPeaceful() {
		return !this.isTrulyDormant();
	}

	@Override
	public void aiStep() {
		super.aiStep();
		boolean dormant = this.isDormancy() || this.isFallingAsleep();
		this.setNoGravity(!dormant);
		this.moveControl = dormant ? this.groundMoveControl : this.flyingMoveControl;
	}

	@Override
	protected void customServerAiStep() {
		super.customServerAiStep();
		if (getTarget() != null && !getTarget().isAlive()) {
			setTarget(null);
		}
		if (this.isTransitioning()) {
			this.setTarget(null);
			this.getNavigation().stop();
		}
		if (!isNoAi() && isAlive() && !this.isTransitioning() && !this.isTrulyDormant()) {
			this.behaviorManager.tick();
		}
	}

	@Override
	public void tick() {
		super.tick();

		if (this.isTransitioning()) {
			this.setTarget(null);
			this.getNavigation().stop();
		}

		if (this.getDormancyCooldown() > 0) {
			this.setDormancyCooldown(this.getDormancyCooldown() - 1);
		}

		if (!this.level().isClientSide) {
			switch (getDormancyState()) {
				case DORMANCY_STATE_NORMAL -> {
					if (isDormancy()) {
						LivingEntity nearTarget = this.level().getNearestEntity(
							this.level().getEntitiesOfClass(LivingEntity.class,
								this.getBoundingBox().inflate(10.0),
								EntitySelector.NO_CREATIVE_OR_SPECTATOR.and(e -> !(e instanceof Ectostone))),
							TargetingConditions.DEFAULT,
							this, this.getX(), this.getEyeY(), this.getZ()
						);
						if (this.getDormancyCooldown() <= 0 && nearTarget != null) {
							this.setDormancy(false);
							this.setDormancyState(DORMANCY_STATE_AWAKENING);
							this.setBehaviorTicks(0);
						}
					} else {
						if (getTarget() == null && getBehaviorState() == 0) {
							this.setBehaviorTicks(getBehaviorTicks() + 1);
							if (getBehaviorTicks() >= 400) {
								this.setDormancyState(DORMANCY_STATE_FALLING_ASLEEP);
								this.setBehaviorTicks(0);
								this.setTarget(null);
								this.getNavigation().stop();
							}
						}
					}
				}
				case DORMANCY_STATE_AWAKENING -> {
					this.setBehaviorTicks(getBehaviorTicks() + 1);
					if (getBehaviorTicks() >= 40) {
						this.setDormancyState(DORMANCY_STATE_NORMAL);
						this.setBehaviorTicks(0);
					}
				}
				case DORMANCY_STATE_FALLING_ASLEEP -> {
					this.setBehaviorTicks(getBehaviorTicks() + 1);
					if (getBehaviorTicks() >= 25) {
						this.setDormancy(true);
						this.setDormancyState(DORMANCY_STATE_NORMAL);
						this.setBehaviorTicks(0);
					}
				}
			}
		}

		if (this.level().isClientSide && (!this.isDormancy() || this.isAwakening() || this.isFallingAsleep())) {
			for (Vec3 pos : new Vec3[]{this.particle1Pos, this.particle2Pos, this.particle3Pos, this.particle4Pos}) {
				if (pos == Vec3.ZERO) continue;
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
		setVariant(EctostoneVariant.getSpawnVariant(level.registryAccess(), level.getBiome(blockPosition())));
		return super.finalizeSpawn(level, instance, spawnType, data);
	}

	@Override
	public void readAdditionalSaveData(CompoundTag compoundTag) {
		super.readAdditionalSaveData(compoundTag);
		setVariantId(ResourceLocation.read(compoundTag.getString(TAG_VARIANT)).getOrThrow());
		if (compoundTag.contains(TAG_DORMANCY)) {
			this.setDormancy(compoundTag.getBoolean(TAG_DORMANCY));
		}
		if (compoundTag.contains(TAG_DORMANCY_STATE)) {
			this.setDormancyState(compoundTag.getInt(TAG_DORMANCY_STATE));
		}
	}

	@Override
	public void addAdditionalSaveData(CompoundTag compoundTag) {
		super.addAdditionalSaveData(compoundTag);
		compoundTag.putString(TAG_VARIANT, getVariantId().toString());
		compoundTag.putBoolean(TAG_DORMANCY, this.isDormancy());
		compoundTag.putInt(TAG_DORMANCY_STATE, getDormancyState());
	}
}
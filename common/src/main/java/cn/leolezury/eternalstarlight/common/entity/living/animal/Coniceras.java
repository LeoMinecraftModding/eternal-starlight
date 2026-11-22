package cn.leolezury.eternalstarlight.common.entity.living.animal;

import cn.leolezury.eternalstarlight.common.EternalStarlight;
import cn.leolezury.eternalstarlight.common.config.ESConfig;
import cn.leolezury.eternalstarlight.common.entity.interfaces.Charger;
import cn.leolezury.eternalstarlight.common.entity.living.goal.ChargeAttackGoal;
import cn.leolezury.eternalstarlight.common.registry.ESEntities;
import cn.leolezury.eternalstarlight.common.registry.ESSoundEvents;
import cn.leolezury.eternalstarlight.common.util.ESTags;
import net.minecraft.core.BlockPos;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.control.LookControl;
import net.minecraft.world.entity.ai.control.SmoothSwimmingMoveControl;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.entity.ai.navigation.WaterBoundPathNavigation;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.vehicle.Boat;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.pathfinder.PathType;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;

public class Coniceras extends Animal implements Charger {
	private static final Ingredient FOOD_ITEMS = Ingredient.of(ESTags.Items.CONICERAS_FOOD);

	public float xBodyRot;
	public float xBodyRotO;
	public float zBodyRot;
	public float zBodyRotO;

	public AnimationState chargeAnimationState = new AnimationState();

	public Coniceras(EntityType<? extends Coniceras> entityType, Level level) {
		super(entityType, level);
		this.setPathfindingMalus(PathType.WATER, 0.0F);
		this.moveControl = new SmoothSwimmingMoveControl(this, 85, 40, 0.02F, 0.1F, true);
		this.lookControl = new ConicerasLookControl(this, 40);
	}

	public static final EntityDataAccessor<Boolean> CHARGING = SynchedEntityData.defineId(Coniceras.class, EntityDataSerializers.BOOLEAN);

	public boolean isCharging() {
		return this.getEntityData().get(CHARGING);
	}

	@Override
	public void setCharging(boolean charging) {
		if (charging != this.isCharging()) {
			this.getEntityData().set(CHARGING, charging);
		}
	}

	@Override
	protected void defineSynchedData(SynchedEntityData.Builder builder) {
		super.defineSynchedData(builder);
		builder.define(CHARGING, false);
	}

	public static AttributeSupplier.Builder createAttributes() {
		return Mob.createMobAttributes()
			.add(Attributes.MAX_HEALTH, ESConfig.INSTANCE.mobsConfig.coniceras.maxHealth())
			.add(Attributes.ARMOR, ESConfig.INSTANCE.mobsConfig.coniceras.armor())
			.add(Attributes.ATTACK_DAMAGE, ESConfig.INSTANCE.mobsConfig.coniceras.attackDamage())
			.add(Attributes.FOLLOW_RANGE, ESConfig.INSTANCE.mobsConfig.coniceras.followRange());
	}

	@Override
	protected void registerGoals() {
		this.goalSelector.addGoal(0, new TryFindWaterGoal(this) {
			@Override
			public boolean canUse() {
				return super.canUse() && !Coniceras.this.isCharging();
			}
		});
		this.goalSelector.addGoal(1, new AvoidEntityGoal<>(this, TwilightGaze.class, 8.0F, 1.6, 1.4) {
			@Override
			public boolean canUse() {
				return super.canUse() && !Coniceras.this.isCharging();
			}
		});
		this.goalSelector.addGoal(2, new ConicerasDrillGoal());
		this.goalSelector.addGoal(3, new BreedGoal(this, 1.0D));
		this.goalSelector.addGoal(4, new TemptGoal(this, 1.2D, FOOD_ITEMS, false));
		this.goalSelector.addGoal(5, new FollowParentGoal(this, 1.1D));
		this.goalSelector.addGoal(6, new RandomSwimmingGoal(this, 1.0, 7));
		this.targetSelector.addGoal(0, new HurtByTargetGoal(this).setAlertOthers());
	}

	private class ConicerasDrillGoal extends ChargeAttackGoal {
		private static final int ATTACK_INTERVAL = 2;
		private static final double BOAT_CHARGE_RANGE = 16.0D;
		private static final int WINDUP_TICKS = 38;
		private static final int DASH_TICKS = 62;
		private static final int DECEL_TICKS = 20;
		private static final int CHARGE_COOLDOWN = 40;
		private static final double MIN_DASH_SPEED = 0.25D;
		private static final double MAX_DASH_SPEED = 0.6D;
		private int attackCooldown;

		private ConicerasDrillGoal() {
			super(Coniceras.this, MAX_DASH_SPEED, WINDUP_TICKS, 0.5F, 20.0D, 0.0D, 1.25F, CHARGE_COOLDOWN, true, false, DASH_TICKS, DECEL_TICKS);
		}

		@Override
		protected double getDashSpeed(double distance) {
			return Mth.clamp(distance / DASH_TICKS, MIN_DASH_SPEED, MAX_DASH_SPEED);
		}

		@Override
		protected float getWindupLookSpeed(int chargeTick) {
			return chargeTick == 0 ? 360.0F : 30.0F;
		}

		@Override
		public boolean canUse() {
			return !Coniceras.this.isBaby() && super.canUse();
		}

		@Override
		public void start() {
			attackCooldown = 0;
			super.start();
		}

		@Nullable
		@Override
		protected Entity findTarget() {
			Entity target = super.findTarget();
			return target != null ? target : findNearestBoat();
		}

		@Nullable
		private Boat findNearestBoat() {
			Boat nearest = null;
			double nearestDistSqr = BOAT_CHARGE_RANGE * BOAT_CHARGE_RANGE;
			for (Boat boat : Coniceras.this.level().getEntitiesOfClass(Boat.class, Coniceras.this.getBoundingBox().inflate(BOAT_CHARGE_RANGE))) {
				if (boat.isRemoved()) {
					continue;
				}
				double distSqr = Coniceras.this.distanceToSqr(boat);
				if (distSqr < nearestDistSqr) {
					nearestDistSqr = distSqr;
					nearest = boat;
				}
			}
			return nearest;
		}

		@Override
		protected void onChargeHit(Entity target) {
			if (attackCooldown > 0) {
				attackCooldown--;
				return;
			}
			attackCooldown = ATTACK_INTERVAL;
			target.invulnerableTime = 0;
			target.hurt(Coniceras.this.damageSources().mobAttack(Coniceras.this), (float) Coniceras.this.getAttributeValue(Attributes.ATTACK_DAMAGE));
		}
	}

	// [Vanilla copy] SmoothSwimmingLookControl, with the hardcoded head yaw and pitch offsets removed
	private static class ConicerasLookControl extends LookControl {
		private final int maxYRotFromCenter;

		private ConicerasLookControl(Mob mob, int maxYRotFromCenter) {
			super(mob);
			this.maxYRotFromCenter = maxYRotFromCenter;
		}

		@Override
		public void tick() {
			if (this.lookAtCooldown > 0) {
				this.lookAtCooldown--;
				this.getYRotD().ifPresent(yRot -> this.mob.yHeadRot = this.rotateTowards(this.mob.yHeadRot, yRot, this.yMaxRotSpeed));
				this.getXRotD().ifPresent(xRot -> this.mob.setXRot(this.rotateTowards(this.mob.getXRot(), xRot, this.xMaxRotAngle)));
			} else {
				if (this.mob.getNavigation().isDone()) {
					this.mob.setXRot(this.rotateTowards(this.mob.getXRot(), 0.0F, 5.0F));
				}
				this.mob.yHeadRot = this.rotateTowards(this.mob.yHeadRot, this.mob.yBodyRot, this.yMaxRotSpeed);
			}
			float f = Mth.wrapDegrees(this.mob.yHeadRot - this.mob.yBodyRot);
			if (f < (float) (-this.maxYRotFromCenter)) {
				this.mob.yBodyRot -= 4.0F;
			} else if (f > (float) this.maxYRotFromCenter) {
				this.mob.yBodyRot += 4.0F;
			}
		}
	}

	@Override
	protected PathNavigation createNavigation(Level level) {
		return new WaterBoundPathNavigation(this, level);
	}

	@Override
	public void travel(Vec3 vec3) {
		if (this.isEffectiveAi() && this.isInWater()) {
			this.moveRelative(this.getSpeed(), vec3);
			this.move(MoverType.SELF, this.getDeltaMovement());
			this.setDeltaMovement(this.getDeltaMovement().scale(0.9D));
			if (this.getTarget() == null) {
				this.setDeltaMovement(this.getDeltaMovement().add(0.0D, -0.005D, 0.0D));
			}
		} else {
			super.travel(vec3);
		}
	}

	@Override
	public void aiStep() {
		if (!this.level().isClientSide) {
			LivingEntity target = this.getTarget();
			if (target != null && !this.canAttack(target)) {
				this.setTarget(null);
			}
		}
		super.aiStep();
		this.xBodyRotO = this.xBodyRot;
		this.zBodyRotO = this.zBodyRot;

		if (this.isCharging()) {
			this.xBodyRot = -this.getXRot();
		} else if (this.isInWaterOrBubble()) {
			this.xBodyRot += (-this.getXRot() - this.xBodyRot) * 0.1F;
		} else {
			this.xBodyRot += (-90.0F - this.xBodyRot) * 0.02F;
		}

		if (!this.level().isClientSide && this.isCharging()) {
			this.setYRot(this.yHeadRot);
			this.yBodyRot = this.yHeadRot;
		}
		if (this.isCharging()) {
			this.chargeAnimationState.startIfStopped(this.tickCount);
		}
	}

	@Override
	public void onSyncedDataUpdated(EntityDataAccessor<?> accessor) {
		super.onSyncedDataUpdated(accessor);
		if (!accessor.equals(CHARGING)) {
			return;
		}
		if (isCharging()) {
			chargeAnimationState.start(tickCount);
			EternalStarlight.getClientHelper().playConicerasDrillSound(this);
		}
	}

	@Nullable
	@Override
	protected SoundEvent getHurtSound(DamageSource damageSource) {
		return ESSoundEvents.CONICERAS_HURT.get();
	}

	@Nullable
	@Override
	protected SoundEvent getDeathSound() {
		return ESSoundEvents.CONICERAS_DEATH.get();
	}

	@Override
	public boolean checkSpawnObstruction(LevelReader level) {
		return level.isUnobstructed(this);
	}

	@Override
	public float getWalkTargetValue(BlockPos pos, LevelReader level) {
		return 0.0F;
	}

	@Override
	public void baseTick() {
		int airSupply = this.getAirSupply();
		super.baseTick();
		this.handleAirSupply(airSupply);
	}

	private void handleAirSupply(int airSupply) {
		if (this.isAlive() && !this.isInWaterOrBubble()) {
			this.setAirSupply(airSupply - 1);
			if (this.getAirSupply() == -20) {
				this.setAirSupply(0);
				this.hurt(this.damageSources().drown(), 2.0F);
			}
		} else {
			this.setAirSupply(300);
		}
	}

	@Override
	public boolean isPushedByFluid() {
		return false;
	}

	@Override
	public boolean canBeLeashed() {
		return false;
	}

	@Override
	public boolean isFood(ItemStack stack) {
		return FOOD_ITEMS.test(stack);
	}

	@Nullable
	@Override
	public AgeableMob getBreedOffspring(ServerLevel serverLevel, AgeableMob ageableMob) {
		return ESEntities.CONICERAS.get().create(serverLevel);
	}

	public static boolean checkConicerasSpawnRules(EntityType<? extends Coniceras> entityType, LevelAccessor levelAccessor, MobSpawnType mobSpawnType, BlockPos blockPos, RandomSource randomSource) {
		int seaLevel = levelAccessor.getSeaLevel();
		return blockPos.getY() <= seaLevel - 10 && levelAccessor.getFluidState(blockPos.below()).is(FluidTags.WATER) && levelAccessor.getBlockState(blockPos.above()).is(Blocks.WATER) && ESConfig.INSTANCE.mobsConfig.coniceras.canSpawn();
	}

}

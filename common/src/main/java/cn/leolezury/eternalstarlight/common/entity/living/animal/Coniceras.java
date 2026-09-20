package cn.leolezury.eternalstarlight.common.entity.living.animal;

import cn.leolezury.eternalstarlight.common.config.ESConfig;
import cn.leolezury.eternalstarlight.common.entity.interfaces.Charger;
import cn.leolezury.eternalstarlight.common.entity.living.goal.ConicerasChargeDrillGoal;
import cn.leolezury.eternalstarlight.common.registry.ESSoundEvents;
import net.minecraft.core.BlockPos;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.control.SmoothSwimmingLookControl;
import net.minecraft.world.entity.ai.control.SmoothSwimmingMoveControl;
import net.minecraft.world.entity.ai.goal.AvoidEntityGoal;
import net.minecraft.world.entity.ai.goal.RandomSwimmingGoal;
import net.minecraft.world.entity.ai.goal.TryFindWaterGoal;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.entity.ai.navigation.WaterBoundPathNavigation;
import net.minecraft.world.entity.animal.WaterAnimal;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;

public class Coniceras extends WaterAnimal implements Charger {
	public float xBodyRot;
	public float xBodyRotO;
	public float zBodyRot;
	public float zBodyRotO;

	public Coniceras(EntityType<? extends Coniceras> entityType, Level level) {
		super(entityType, level);
		this.moveControl = new SmoothSwimmingMoveControl(this, 85, 40, 0.02F, 0.1F, true);
		this.lookControl = new SmoothSwimmingLookControl(this, 40);
	}

	public static final EntityDataAccessor<Boolean> CHARGING = SynchedEntityData.defineId(Coniceras.class, EntityDataSerializers.BOOLEAN);

	public boolean isCharging() {
		return this.getEntityData().get(CHARGING);
	}

	@Override
	public void setCharging(boolean charging) {
		this.getEntityData().set(CHARGING, charging);
	}

	public AnimationState chargeAnimationState = new AnimationState();

	@Override
	public boolean hurt(DamageSource source, float amount) {
		boolean hurt = super.hurt(source, amount);
		if (hurt && !this.level().isClientSide && source.getEntity() instanceof LivingEntity attacker) {
			this.setTarget(attacker);
		}
		return hurt;
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
		this.goalSelector.addGoal(1, new TryFindWaterGoal(this));
		this.goalSelector.addGoal(2, new AvoidEntityGoal<>(this, TwilightGaze.class, 8.0F, 1.6, 1.4));
		this.goalSelector.addGoal(3, new ConicerasChargeDrillGoal(this, 6f, 2.0f, 16.0));
		this.goalSelector.addGoal(4, new RandomSwimmingGoal(this, 1.0, 7));
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
		super.aiStep();
		this.xBodyRotO = this.xBodyRot;
		this.zBodyRotO = this.zBodyRot;

		if (this.isInWaterOrBubble()) {
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
		if (accessor.equals(CHARGING) && isCharging()) {
			chargeAnimationState.start(tickCount);
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

	public static boolean checkConicerasSpawnRules(EntityType<? extends Coniceras> entityType, LevelAccessor levelAccessor, MobSpawnType mobSpawnType, BlockPos blockPos, RandomSource randomSource) {
		int seaLevel = levelAccessor.getSeaLevel();
		return blockPos.getY() <= seaLevel - 10 && levelAccessor.getFluidState(blockPos.below()).is(FluidTags.WATER) && levelAccessor.getBlockState(blockPos.above()).is(Blocks.WATER) && ESConfig.INSTANCE.mobsConfig.coniceras.canSpawn();
	}
}

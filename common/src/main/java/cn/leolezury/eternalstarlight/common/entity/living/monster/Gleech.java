package cn.leolezury.eternalstarlight.common.entity.living.monster;

import cn.leolezury.eternalstarlight.common.config.ESConfig;
import cn.leolezury.eternalstarlight.common.network.ClientDismountPacket;
import cn.leolezury.eternalstarlight.common.network.ClientMountPacket;
import cn.leolezury.eternalstarlight.common.platform.ESPlatform;
import cn.leolezury.eternalstarlight.common.util.ESMathUtil;
import net.minecraft.Util;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.ClimbOnTopOfPowderSnowGoal;
import net.minecraft.world.entity.ai.goal.FloatGoal;
import net.minecraft.world.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.world.entity.ai.goal.WaterAvoidingRandomStrollGoal;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.state.BlockState;

public class Gleech extends Monster {
	private static final String TAG_LARVAL = "larval";
	private static final String TAG_GROWTH_TICKS = "growth_ticks";

	public static final CompoundTag NOT_LARVAL = Util.make(() -> {
		CompoundTag tag = new CompoundTag();
		tag.putBoolean(TAG_LARVAL, false);
		return tag;
	});

	protected static final EntityDataAccessor<Boolean> LARVAL = SynchedEntityData.defineId(Gleech.class, EntityDataSerializers.BOOLEAN);

	public boolean isLarval() {
		return this.getEntityData().get(LARVAL);
	}

	public void setLarval(boolean larval) {
		this.getEntityData().set(LARVAL, larval);
	}

	private int growthTicks;
	private int attachTicks;

	public AnimationState idleAnimationState = new AnimationState();

	public Gleech(EntityType<? extends Monster> entityType, Level level) {
		super(entityType, level);
	}

	@Override
	protected void defineSynchedData(SynchedEntityData.Builder builder) {
		super.defineSynchedData(builder);
		builder.define(LARVAL, false);
	}

	@Override
	protected void registerGoals() {
		this.goalSelector.addGoal(0, new FloatGoal(this));
		this.goalSelector.addGoal(1, new ClimbOnTopOfPowderSnowGoal(this, this.level()));
		this.goalSelector.addGoal(2, new MeleeAttackGoal(this, 1.0, false));
		this.goalSelector.addGoal(3, new WaterAvoidingRandomStrollGoal(this, 1.0));
		this.targetSelector.addGoal(0, new HurtByTargetGoal(this).setAlertOthers());
		this.targetSelector.addGoal(1, new NearestAttackableTargetGoal<>(this, Player.class, true) {
			@Override
			public boolean canUse() {
				return super.canUse() && Gleech.this.getVehicle() == null && Gleech.this.getTarget() == null; // always attack the mob that is hit by the egg
			}
		});
	}

	public static AttributeSupplier.Builder createAttributes() {
		return Monster.createMonsterAttributes()
			.add(Attributes.MAX_HEALTH, ESConfig.INSTANCE.mobsConfig.gleech.maxHealth())
			.add(Attributes.ARMOR, ESConfig.INSTANCE.mobsConfig.gleech.armor())
			.add(Attributes.ATTACK_DAMAGE, ESConfig.INSTANCE.mobsConfig.gleech.attackDamage())
			.add(Attributes.FOLLOW_RANGE, ESConfig.INSTANCE.mobsConfig.gleech.followRange())
			.add(Attributes.MOVEMENT_SPEED, 0.25);
	}

	@Override
	protected Entity.MovementEmission getMovementEmission() {
		return MovementEmission.EVENTS;
	}

	@Override
	protected SoundEvent getAmbientSound() {
		return SoundEvents.SILVERFISH_AMBIENT;
	}

	@Override
	protected SoundEvent getHurtSound(DamageSource damageSource) {
		return SoundEvents.SILVERFISH_HURT;
	}

	@Override
	protected SoundEvent getDeathSound() {
		return SoundEvents.SILVERFISH_DEATH;
	}

	@Override
	protected void playStepSound(BlockPos blockPos, BlockState blockState) {
		this.playSound(SoundEvents.SILVERFISH_STEP, 0.15F, 1.0F);
	}

	@Override
	public boolean isBaby() {
		return isLarval();
	}

	@Override
	public void tick() {
		this.yBodyRot = this.getYRot();
		super.tick();
		if (!level().isClientSide) {
			setNoGravity(getVehicle() != null);
			if (isLarval()) {
				growthTicks++;
				if (growthTicks > 12000) {
					growthTicks = 0;
					setLarval(false);
				}
			}
		} else {
			idleAnimationState.startIfStopped(tickCount);
		}
	}

	public void attachTo(LivingEntity livingEntity) {
		if (!level().isClientSide && level() instanceof ServerLevel serverLevel) {
			startRiding(livingEntity, true);
			ESPlatform.INSTANCE.sendToAllClients(serverLevel, new ClientMountPacket(getId(), livingEntity.getId()));
		}
	}

	@Override
	public void rideTick() {
		super.rideTick();
		if (!level().isClientSide) {
			attachTicks++;
			if (attachTicks > 80) {
				attachTicks = 0;
				stopRiding();
				ESPlatform.INSTANCE.sendToAllClients((ServerLevel) level(), new ClientDismountPacket(getId()));
			}
		}
		if (getVehicle() instanceof LivingEntity livingEntity) {
			setYRot(livingEntity.getYRot());
			setPos(ESMathUtil.rotationToPosition(getVehicle().position().add(0, getVehicle().getBbHeight() / 2, 0), (getVehicle().getBbWidth() / 2) * 0.75f, 0, getVehicle().getYRot() + 90));
		}
	}

	@Override
	public void setYBodyRot(float f) {
		float rotation = f;
		if (getVehicle() instanceof LivingEntity livingEntity) {
			rotation = livingEntity.getYRot();
		}
		this.setYRot(rotation);
		super.setYBodyRot(rotation);
	}

	@Override
	public void setYRot(float f) {
		float rotation = f;
		if (getVehicle() instanceof LivingEntity livingEntity) {
			rotation = livingEntity.getYRot();
		}
		super.setYRot(rotation);
	}

	@Override
	public boolean doHurtTarget(Entity entity) {
		return super.doHurtTarget(entity);
	}

	@Override
	public void readAdditionalSaveData(CompoundTag compoundTag) {
		super.readAdditionalSaveData(compoundTag);
		setLarval(compoundTag.getBoolean(TAG_LARVAL));
		growthTicks = compoundTag.getInt(TAG_GROWTH_TICKS);
	}

	@Override
	public void addAdditionalSaveData(CompoundTag compoundTag) {
		super.addAdditionalSaveData(compoundTag);
		compoundTag.putBoolean(TAG_LARVAL, isLarval());
		compoundTag.putInt(TAG_GROWTH_TICKS, growthTicks);
	}

	public static boolean checkGleechSpawnRules(EntityType<? extends Gleech> type, LevelAccessor level, MobSpawnType spawnType, BlockPos pos, RandomSource random) {
		return level.getBlockState(pos.below()).is(BlockTags.SAND) && ESConfig.INSTANCE.mobsConfig.gleech.canSpawn();
	}
}


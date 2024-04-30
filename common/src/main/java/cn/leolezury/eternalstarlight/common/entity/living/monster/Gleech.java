package cn.leolezury.eternalstarlight.common.entity.living.monster;

import cn.leolezury.eternalstarlight.common.network.ClientMountPacket;
import cn.leolezury.eternalstarlight.common.platform.ESPlatform;
import cn.leolezury.eternalstarlight.common.util.ESMathUtil;
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
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.state.BlockState;

public class Gleech extends Monster {
    protected static final EntityDataAccessor<Boolean> LARVAL = SynchedEntityData.defineId(Gleech.class, EntityDataSerializers.BOOLEAN);
    public boolean isLarval() {
        return entityData.get(LARVAL);
    }
    public void setLarval(boolean larval) {
        entityData.set(LARVAL, larval);
    }

    private int growthTicks;

    public AnimationState idleAnimationState = new AnimationState();

    public Gleech(EntityType<? extends Monster> entityType, Level level) {
        super(entityType, level);
    }

    @Override
    protected void defineSynchedData(SynchedEntityData.Builder builder) {
        super.defineSynchedData(builder);
        builder.define(LARVAL, false);
    }

    protected void registerGoals() {
        this.goalSelector.addGoal(0, new FloatGoal(this));
        this.goalSelector.addGoal(1, new ClimbOnTopOfPowderSnowGoal(this, this.level()));
        this.goalSelector.addGoal(2, new MeleeAttackGoal(this, 1.0, false));
        this.targetSelector.addGoal(0, new HurtByTargetGoal(this).setAlertOthers());
        this.targetSelector.addGoal(1, new NearestAttackableTargetGoal<>(this, Player.class, true));
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Monster.createMonsterAttributes()
                .add(Attributes.MAX_HEALTH, 8.0)
                .add(Attributes.MOVEMENT_SPEED, 0.25)
                .add(Attributes.ATTACK_DAMAGE, 1.0);
    }

    protected Entity.MovementEmission getMovementEmission() {
        return MovementEmission.EVENTS;
    }

    protected SoundEvent getAmbientSound() {
        return SoundEvents.SILVERFISH_AMBIENT;
    }

    protected SoundEvent getHurtSound(DamageSource damageSource) {
        return SoundEvents.SILVERFISH_HURT;
    }

    protected SoundEvent getDeathSound() {
        return SoundEvents.SILVERFISH_DEATH;
    }

    protected void playStepSound(BlockPos blockPos, BlockState blockState) {
        this.playSound(SoundEvents.SILVERFISH_STEP, 0.15F, 1.0F);
    }

    public void tick() {
        this.yBodyRot = this.getYRot();
        super.tick();
        if (!level().isClientSide) {
            setNoGravity(getVehicle() != null);
            // todo stop riding after some time we cannot attack our vehicle
            if (getTarget() != null && isLarval()) {
                startRiding(getTarget(), true);
                ESPlatform.INSTANCE.sendToAllClients((ServerLevel) level(), new ClientMountPacket(getId(), getTarget().getId()));
            }
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

    @Override
    public void rideTick() {
        super.rideTick();
        if (getVehicle() instanceof LivingEntity livingEntity) {
            setYRot(livingEntity.getYRot());
            setPos(ESMathUtil.rotationToPosition(getVehicle().position().add(0, getVehicle().getBbHeight() / 2, 0), (getVehicle().getBbWidth() / 2) * 0.75f, 0, getVehicle().getYRot() + 90));
        }
    }

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
    public void readAdditionalSaveData(CompoundTag compoundTag) {
        super.readAdditionalSaveData(compoundTag);
        setLarval(compoundTag.getBoolean("Larval"));
        growthTicks = compoundTag.getInt("GrowthTicks");
    }

    @Override
    public void addAdditionalSaveData(CompoundTag compoundTag) {
        super.addAdditionalSaveData(compoundTag);
        compoundTag.putBoolean("Larval", isLarval());
        compoundTag.putInt("GrowthTicks", growthTicks);
    }

    public static boolean checkGleechSpawnRules(EntityType<? extends Gleech> type, LevelAccessor level, MobSpawnType spawnType, BlockPos pos, RandomSource random) {
        return level.getBlockState(pos.below()).is(BlockTags.SAND);
    }
}


package cn.leolezury.eternalstarlight.common.entity.living.monster;

import cn.leolezury.eternalstarlight.common.registry.ESCriteriaTriggers;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.Mth;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.control.MoveControl;
import net.minecraft.world.entity.ai.goal.FloatGoal;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.ProjectileUtil;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;

import java.util.EnumSet;

public class TangledSkull extends Monster {
    protected static final EntityDataAccessor<Boolean> CHARGING = SynchedEntityData.defineId(TangledSkull.class, EntityDataSerializers.BOOLEAN);
    public boolean isCharging() {
        return entityData.get(CHARGING);
    }
    public void setCharging(boolean charging) {
        entityData.set(CHARGING, charging);
    }

    public int skullDeathTime;

    @Nullable
    private Vec3 shotMovement;

    public void setShotMovement(@Nullable Vec3 shotMovement) {
        this.shotMovement = shotMovement;
    }

    public TangledSkull(EntityType<? extends Monster> entityType, Level level) {
        super(entityType, level);
        this.moveControl = new TangledSkullMoveControl(this);
    }

    protected void registerGoals() {
        this.goalSelector.addGoal(0, new FloatGoal(this));
        this.goalSelector.addGoal(1, new TangledSkullChargeAttackGoal());
        this.goalSelector.addGoal(2, new TangledSkullRandomMoveGoal());
        this.goalSelector.addGoal(3, new LookAtPlayerGoal(this, Player.class, 3.0F, 1.0F));
        this.goalSelector.addGoal(4, new LookAtPlayerGoal(this, Mob.class, 8.0F));
        this.targetSelector.addGoal(0, new HurtByTargetGoal(this).setAlertOthers());
        this.targetSelector.addGoal(1, new NearestAttackableTargetGoal<>(this, Player.class, true));
    }

    private class TangledSkullChargeAttackGoal extends Goal {
        public TangledSkullChargeAttackGoal() {
            this.setFlags(EnumSet.of(Flag.MOVE));
        }

        public boolean canUse() {
            LivingEntity livingEntity = TangledSkull.this.getTarget();
            if (livingEntity != null && livingEntity.isAlive() && !TangledSkull.this.getMoveControl().hasWanted() && TangledSkull.this.random.nextInt(reducedTickDelay(7)) == 0) {
                return TangledSkull.this.distanceToSqr(livingEntity) > 4.0;
            } else {
                return false;
            }
        }

        public boolean canContinueToUse() {
            return TangledSkull.this.getMoveControl().hasWanted() && TangledSkull.this.isCharging() && TangledSkull.this.getTarget() != null && TangledSkull.this.getTarget().isAlive();
        }

        public void start() {
            LivingEntity livingEntity = TangledSkull.this.getTarget();
            if (livingEntity != null) {
                Vec3 vec3 = livingEntity.getEyePosition();
                TangledSkull.this.moveControl.setWantedPosition(vec3.x, vec3.y, vec3.z, 1.0);
            }

            TangledSkull.this.setCharging(true);
        }

        public void stop() {
            TangledSkull.this.setCharging(false);
        }

        public boolean requiresUpdateEveryTick() {
            return true;
        }

        public void tick() {
            LivingEntity livingEntity = TangledSkull.this.getTarget();
            if (livingEntity != null) {
                if (TangledSkull.this.getBoundingBox().intersects(livingEntity.getBoundingBox())) {
                    TangledSkull.this.doHurtTarget(livingEntity);
                    TangledSkull.this.setCharging(false);
                } else {
                    double d = TangledSkull.this.distanceToSqr(livingEntity);
                    if (d < 9.0) {
                        Vec3 vec3 = livingEntity.getEyePosition();
                        TangledSkull.this.moveControl.setWantedPosition(vec3.x, vec3.y, vec3.z, 1.0);
                    }
                }

            }
        }
    }

    private class TangledSkullRandomMoveGoal extends Goal {
        public TangledSkullRandomMoveGoal() {
            this.setFlags(EnumSet.of(Flag.MOVE));
        }

        public boolean canUse() {
            return !TangledSkull.this.getMoveControl().hasWanted() && TangledSkull.this.random.nextInt(reducedTickDelay(7)) == 0;
        }

        public boolean canContinueToUse() {
            return false;
        }

        public void tick() {
            BlockPos blockPos = TangledSkull.this.blockPosition();

            for(int i = 0; i < 3; ++i) {
                BlockPos blockPos2 = blockPos.offset(TangledSkull.this.random.nextInt(15) - 7, TangledSkull.this.random.nextInt(11) - 5, TangledSkull.this.random.nextInt(15) - 7);
                if (TangledSkull.this.level().isEmptyBlock(blockPos2)) {
                    TangledSkull.this.moveControl.setWantedPosition((double)blockPos2.getX() + 0.5, (double)blockPos2.getY() + 0.5, (double)blockPos2.getZ() + 0.5, 0.25);
                    if (TangledSkull.this.getTarget() == null) {
                        TangledSkull.this.getLookControl().setLookAt((double)blockPos2.getX() + 0.5, (double)blockPos2.getY() + 0.5, (double)blockPos2.getZ() + 0.5, 180.0F, 20.0F);
                    }
                    break;
                }
            }

        }
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Monster.createMonsterAttributes()
                .add(Attributes.MAX_HEALTH, 10.0)
                .add(Attributes.ATTACK_DAMAGE, 3.0)
                .add(Attributes.FOLLOW_RANGE, 64.0);
    }

    @Override
    protected void defineSynchedData(SynchedEntityData.Builder builder) {
        super.defineSynchedData(builder);
        builder.define(CHARGING, false);
    }

    public void tick() {
        this.noPhysics = true;
        super.tick();
        this.noPhysics = false;
        this.setNoGravity(true);
        if (!level().isClientSide) {
            if (shotMovement != null) {
                setDeltaMovement(shotMovement);
                HitResult result = ProjectileUtil.getHitResultOnMoveVector(this, entity -> true);
                if (result.getType() != HitResult.Type.MISS) {
                    this.level().explode(this, this.getX(), this.getY(), this.getZ(), 2, Level.ExplosionInteraction.NONE);
                    discard();
                }
            }
        } else {
            level().addParticle(ParticleTypes.SOUL_FIRE_FLAME, getX(), getY(0.5), getZ(), 0, 0, 0);
        }
    }

    @Override
    protected void tickDeath() {
        setDeltaMovement(Vec3.ZERO);
        ++this.skullDeathTime;
        if (this.skullDeathTime >= 80 && !this.level().isClientSide() && !this.isRemoved()) {
            this.level().explode(this, this.getX(), this.getY(), this.getZ(), 2, Level.ExplosionInteraction.NONE);
            this.level().broadcastEntityEvent(this, (byte)60);
            this.remove(RemovalReason.KILLED);
        }
    }

    @Override
    public void die(DamageSource source) {
        super.die(source);
        if (!this.level().isClientSide() && source.getEntity() instanceof TangledSkull killer && killer.getKillCredit() instanceof ServerPlayer player) {
            ESCriteriaTriggers.CHAIN_TANGLED_SKULL_EXPLOSION.get().trigger(player);
        }
    }

    @Nullable
    @Override
    public LivingEntity getTarget() {
        return shotMovement == null ? super.getTarget() : null;
    }

    @Override
    public void setDeltaMovement(Vec3 vec3) {
        super.setDeltaMovement(shotMovement == null ? vec3 : shotMovement);
    }

    @Override
    public void addAdditionalSaveData(CompoundTag compoundTag) {
        super.addAdditionalSaveData(compoundTag);
        if (shotMovement != null) {
            compoundTag.putDouble("ShotMovementX", shotMovement.x);
            compoundTag.putDouble("ShotMovementY", shotMovement.y);
            compoundTag.putDouble("ShotMovementZ", shotMovement.z);
        }
    }

    @Override
    public void readAdditionalSaveData(CompoundTag compoundTag) {
        super.readAdditionalSaveData(compoundTag);
        if (compoundTag.contains("ShotMovementX", CompoundTag.TAG_DOUBLE) && compoundTag.contains("ShotMovementY", CompoundTag.TAG_DOUBLE) && compoundTag.contains("ShotMovementZ", CompoundTag.TAG_DOUBLE)) {
            setShotMovement(new Vec3(compoundTag.getDouble("ShotMovementX"), compoundTag.getDouble("ShotMovementY"), compoundTag.getDouble("ShotMovementZ")));
        }
    }

    @Nullable
    @Override
    protected SoundEvent getAmbientSound() {
        return SoundEvents.SKELETON_AMBIENT;
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource damageSource) {
        return SoundEvents.SKELETON_HURT;
    }

    @Override
    protected SoundEvent getDeathSound() {
        return SoundEvents.SKELETON_DEATH;
    }

    private class TangledSkullMoveControl extends MoveControl {
        public TangledSkullMoveControl(final TangledSkull skull) {
            super(skull);
        }

        public void tick() {
            if (this.operation == Operation.MOVE_TO) {
                Vec3 vec3 = new Vec3(this.wantedX - TangledSkull.this.getX(), this.wantedY - TangledSkull.this.getY(), this.wantedZ - TangledSkull.this.getZ());
                double d = vec3.length();
                if (d < TangledSkull.this.getBoundingBox().getSize()) {
                    this.operation = Operation.WAIT;
                    TangledSkull.this.setDeltaMovement(TangledSkull.this.getDeltaMovement().scale(0.5));
                } else {
                    TangledSkull.this.setDeltaMovement(TangledSkull.this.getDeltaMovement().add(vec3.scale(this.speedModifier * 0.05 / d)));
                    if (TangledSkull.this.getTarget() == null) {
                        Vec3 vec32 = TangledSkull.this.getDeltaMovement();
                        TangledSkull.this.setYRot(-((float) Mth.atan2(vec32.x, vec32.z)) * 57.295776F);
                        TangledSkull.this.yBodyRot = TangledSkull.this.getYRot();
                    } else {
                        double e = TangledSkull.this.getTarget().getX() - TangledSkull.this.getX();
                        double f = TangledSkull.this.getTarget().getZ() - TangledSkull.this.getZ();
                        TangledSkull.this.setYRot(-((float)Mth.atan2(e, f)) * 57.295776F);
                        TangledSkull.this.yBodyRot = TangledSkull.this.getYRot();
                    }
                }

            }
        }
    }
}

package cn.leolezury.eternalstarlight.common.entity.projectile;

import cn.leolezury.eternalstarlight.common.platform.ESPlatform;
import cn.leolezury.eternalstarlight.common.registry.ESEntities;
import cn.leolezury.eternalstarlight.common.registry.ESParticles;
import cn.leolezury.eternalstarlight.common.util.ESMathUtil;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.Fireball;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;

import java.util.UUID;

public class GatekeeperFireball extends Fireball {
    public GatekeeperFireball(EntityType<? extends Fireball> entityType, Level level) {
        super(entityType, level);
    }

    public GatekeeperFireball(Level level, LivingEntity livingEntity, double d, double e, double f) {
        super(ESEntities.GATEKEEPER_FIREBALL.get(), livingEntity, d, e, f, level);
    }

    protected static final EntityDataAccessor<Integer> SPAWNED_TICKS = SynchedEntityData.defineId(GatekeeperFireball.class, EntityDataSerializers.INT);
    public int getSpawnedTicks() {
        return entityData.get(SPAWNED_TICKS);
    }
    public void setSpawnedTicks(int spawnedTicks) {
        entityData.set(SPAWNED_TICKS, spawnedTicks);
    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        entityData.define(SPAWNED_TICKS, 0);
    }

    @Nullable
    private LivingEntity target;
    @Nullable
    private UUID targetId;

    public LivingEntity getTarget() {
        return target;
    }
    public void setTarget(LivingEntity target) {
        this.targetId = target.getUUID();
        this.target = target;
    }

    protected ParticleOptions getTrailParticle() {
        return ESParticles.FLAME_SMOKE.get();
    }

    @Override
    public boolean isPickable() {
        return false;
    }

    public boolean isOnFire() {
        return false;
    }

    public boolean hurt(DamageSource damageSource, float amount) {
        return false;
    }

    protected boolean shouldBurn() {
        return false;
    }

    private boolean canReachTarget(double range) {
        LivingEntity target = getTarget();
        if (target == null) {
            return false;
        }
        for (LivingEntity livingEntity : level().getEntitiesOfClass(LivingEntity.class, getBoundingBox().inflate(range))) {
            if (livingEntity.getUUID().equals(target.getUUID())) {
                return true;
            }
        }
        return false;
    }

    protected void onHit(HitResult hitResult) {
        super.onHit(hitResult);
        if (!this.level().isClientSide && (target == null || canReachTarget(5))) {
            boolean bl = ESPlatform.INSTANCE.postMobGriefingEvent(level(), this);
            this.level().explode(this, this.getX(), this.getY(), this.getZ(), 2, bl, Level.ExplosionInteraction.MOB);
            this.discard();
        }
    }

    protected void onHitEntity(EntityHitResult entityHitResult) {
        super.onHitEntity(entityHitResult);
        if (!this.level().isClientSide && (target == null || canReachTarget(5))) {
            Entity entity = entityHitResult.getEntity();
            Entity entity2 = this.getOwner();
            entity.hurt(this.damageSources().fireball(this, entity2), 8.0F);
            if (entity2 instanceof LivingEntity) {
                this.doEnchantDamageEffects((LivingEntity)entity2, entity);
            }
        }
    }

    @Override
    public void tick() {
        super.tick();
        if (!level().isClientSide) {
            if (target == null && targetId != null) {
                if (((ServerLevel)this.level()).getEntity(targetId) instanceof LivingEntity livingEntity) {
                    target = livingEntity;
                }
                if (target == null) {
                    targetId = null;
                }
            }
            setSpawnedTicks(getSpawnedTicks() + 1);
            if (getSpawnedTicks() == 60 && getTarget() != null) {
                Vec3 power = getTarget().position().subtract(position()).normalize().scale(0.4f);
                setDeltaMovement(Vec3.ZERO);
                this.xPower = power.x;
                this.yPower = power.y;
                this.zPower = power.z;
            }
        }
        if (getSpawnedTicks() < 60 && getOwner() != null) {
            Entity owner = getOwner();
            float yaw = ESMathUtil.positionToYaw(owner.position(), position());
            float pitch = ESMathUtil.positionToPitch(owner.position(), position());
            Vec3 newPos = ESMathUtil.rotationToPosition(owner.position(), distanceTo(owner), pitch, yaw + 5);
            setPos(newPos);
        }
    }

    public void readAdditionalSaveData(CompoundTag compoundTag) {
        super.readAdditionalSaveData(compoundTag);
        if (compoundTag.hasUUID("Target")) {
            targetId = compoundTag.getUUID("Target");
        }
        setSpawnedTicks(compoundTag.getInt("SpawnedTicks"));
    }

    public void addAdditionalSaveData(CompoundTag compoundTag) {
        super.addAdditionalSaveData(compoundTag);
        if (target != null) {
            compoundTag.putUUID("Target", target.getUUID());
        }
        compoundTag.putInt("SpawnedTicks", getSpawnedTicks());
    }
}

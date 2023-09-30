package cn.leolezury.eternalstarlight.common.entity.projectile;

import cn.leolezury.eternalstarlight.common.client.particle.lightning.LightningParticleOptions;
import cn.leolezury.eternalstarlight.common.data.DamageTypeInit;
import cn.leolezury.eternalstarlight.common.entity.misc.CameraShake;
import cn.leolezury.eternalstarlight.common.init.EntityInit;
import cn.leolezury.eternalstarlight.common.init.ItemInit;
import cn.leolezury.eternalstarlight.common.util.ESUtil;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.RandomSource;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntityDimensions;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.entity.monster.Enemy;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractHurtingProjectile;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;
import org.joml.Vector3f;

import java.util.UUID;

public class AetherSentMeteor extends AbstractHurtingProjectile {
    protected static final EntityDataAccessor<Integer> SIZE = SynchedEntityData.defineId(AetherSentMeteor.class, EntityDataSerializers.INT);

    public int getSize() {
        return entityData.get(SIZE);
    }

    public void setSize(int size) {
        entityData.set(SIZE, size);
    }

    protected static final EntityDataAccessor<Integer> TICKS_SINCE_LANDED = SynchedEntityData.defineId(AetherSentMeteor.class, EntityDataSerializers.INT);

    public int getTicksSinceLanded() {
        return entityData.get(TICKS_SINCE_LANDED);
    }

    public void setTicksSinceLanded(int ticksSinceLanded) {
        entityData.set(TICKS_SINCE_LANDED, ticksSinceLanded);
    }

    @Nullable
    private LivingEntity target;
    @Nullable
    private UUID targetId;

    public LivingEntity getTarget() {
        return target;
    }

    public void setTarget(LivingEntity target) {
        this.target = target;
    }

    @Nullable
    private Vec3 targetPos;

    public void setTargetPos(Vec3 targetPos) {
        this.targetPos = targetPos;
    }

    public boolean onlyHurtEnemy = true;

    public AetherSentMeteor(EntityType<? extends AbstractHurtingProjectile> type, Level level) {
        super(type, level);
    }

    public AetherSentMeteor(Level level, LivingEntity entity, double x, double y, double z) {
        this(EntityInit.AETHERSENT_METEOR.get(), level);
        xo = x;
        yo = y;
        zo = z;
        setPos(x, y, z);
        setOwner(entity);
    }

    public static void createMeteorShower(Level level, LivingEntity entity, LivingEntity target, double targetX, double targetY, double targetZ, double height, boolean onlyHurtEnemy) {
        if (!level.isClientSide) {
            CompoundTag tag = ESUtil.getPersistentData(entity);
            if (tag.getInt("MeteorCoolDown") > 0) {
                return;
            }
            tag.putInt("MeteorCoolDown", 1);
            for (int x = -1; x <= 1; x++) {
                for (int z = -1; z <= 1; z++) {
                    RandomSource random = entity.getRandom();
                    AetherSentMeteor meteor = new AetherSentMeteor(level, entity, targetX + x + (random.nextFloat() - 0.5) * 3, targetY + height + (random.nextFloat() - 0.5) * 5, targetZ + z + (random.nextFloat() - 0.5) * 3);
                    meteor.setSize(random.nextInt(2, 5));
                    meteor.setTarget(target);
                    meteor.setTargetPos(new Vec3(targetX, targetY, targetZ));
                    meteor.onlyHurtEnemy = onlyHurtEnemy;
                    level.addFreshEntity(meteor);
                    if (level instanceof ServerLevel serverLevel) {
                        serverLevel.sendParticles(ParticleTypes.EXPLOSION, meteor.getX(), meteor.getY(), meteor.getZ(), 2, 0.2D, 0.2D, 0.2D, 0.0D);
                    }
                }
            }
        }
    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        entityData.define(SIZE, 0);
        entityData.define(TICKS_SINCE_LANDED, 0);
    }

    public void readAdditionalSaveData(CompoundTag compoundTag) {
        setSize(compoundTag.getInt("Size"));
        setTicksSinceLanded(compoundTag.getInt("TicksSinceLanded"));
        if (compoundTag.hasUUID("Target")) {
            targetId = compoundTag.getUUID("Target");
        }
        targetPos = new Vec3(compoundTag.getDouble("TargetX"), compoundTag.getDouble("TargetY"), compoundTag.getDouble("TargetZ"));
        onlyHurtEnemy = compoundTag.getBoolean("OnlyHurtEnemy");
    }

    public void addAdditionalSaveData(CompoundTag compoundTag) {
        compoundTag.putInt("Size", getSize());
        compoundTag.putInt("TicksSinceLanded", getTicksSinceLanded());
        if (target != null) {
            compoundTag.putUUID("Target", target.getUUID());
        }
        if (targetPos != null) {
            compoundTag.putDouble("TargetX", targetPos.x);
            compoundTag.putDouble("TargetY", targetPos.y);
            compoundTag.putDouble("TargetZ", targetPos.z);
        }
        compoundTag.putBoolean("OnlyHurtEnemy", onlyHurtEnemy);
    }

    private void doHurtEntity(float damageScale) {
        for (LivingEntity livingEntity : level().getEntitiesOfClass(LivingEntity.class, getBoundingBox().inflate(getSize()))) {
            if ((!(getOwner() instanceof Player) || livingEntity instanceof Enemy || !onlyHurtEnemy) && (getOwner() == null || !getOwner().getUUID().equals(livingEntity.getUUID()))) {
                livingEntity.invulnerableTime = 0;
                livingEntity.hurt(DamageTypeInit.getEntityDamageSource(level(), DamageTypeInit.METEOR, getOwner()), getSize() * damageScale * (getOwner() instanceof LivingEntity ? 0.01f : 1f));
            }
        }
    }

    private void hit() {
        doHurtEntity(5);
        if (target != null) {
            for (LivingEntity entity : level().getEntitiesOfClass(LivingEntity.class, getBoundingBox().move(new Vec3(target.position().x - getX(), 0, target.position().z - getZ())).inflate(getSize()))) {
                if (entity.getUUID().equals(targetId)) {
                    land();
                }
            }
        } else if (targetPos != null && getBoundingBox().move(new Vec3(targetPos.x - getX(), 0, targetPos.z - getZ())).inflate(getSize()).contains(targetPos)) {
            land();
        }
    }

    private void land() {
        playSound(SoundEvents.GENERIC_EXPLODE, getSoundVolume(), getVoicePitch());
        if (level().isClientSide) {
            level().addParticle(getSize() >= 8 ? ParticleTypes.EXPLOSION_EMITTER : ParticleTypes.EXPLOSION, getX(), getY() + 0.05 * getSize(), getZ(), 0, 0, 0);
            for (int i = 0; i < 20; i++) {
                float pitch = random.nextInt(361);
                float yaw = random.nextInt(361);
                float len = random.nextInt(getSize());
                Vec3 particleTarget = ESUtil.rotationToPosition(position(), getSize() / 2f, pitch, yaw);
                Vec3 particleStart = ESUtil.rotationToPosition(position(), len, pitch, yaw);
                Vec3 motion = particleTarget.subtract(particleStart);
                level().addParticle(new LightningParticleOptions(new Vector3f(0.7f, 0.07f, 0.78f)), particleStart.x, particleStart.y, particleStart.z, motion.x, motion.y, motion.z);
            }
        } else {
            if (getOwner() == null && getSize() >= 10) {
                spawnAtLocation(ItemInit.AETHERSENT_BLOCK.get());
            }
            discard();
        }
    }

    @Override
    protected void onHitBlock(BlockHitResult result) {
        super.onHitBlock(result);
        CameraShake.createCameraShake(level(), position(), getSize() * 20, 0.001f * getSize(), 20, 20);
        hit();
        if (!level().isClientSide && target == null && targetPos == null) {
            land();
        }
    }

    @Override
    protected void onHitEntity(EntityHitResult entityHitResult) {
        super.onHitEntity(entityHitResult);
        CameraShake.createCameraShake(level(), position(), getSize() * 20, 0.001f * getSize(), 20, 20);
        hit();
    }

    @Override
    public void tick() {
        super.tick();
        setDeltaMovement(0, -2, 0);
        if (tickCount % 10 == 0) {
            refreshDimensions();
        }
        if (level().isClientSide) {
            Vec3 motion = getDeltaMovement();
            for (int i = 0; i < 5; i++) {
                float r = 0.65f + random.nextFloat() * 0.1f;
                float g = 0.02f + random.nextFloat() * 0.1f;
                float b = 0.73f + random.nextFloat() * 0.1f;
                level().addParticle(new LightningParticleOptions(new Vector3f(r, g, b)), getX(), getY(), getZ(), -motion.x * 3, -motion.y * 3, -motion.z * 3);
            }
        }
    }

    @Override
    public EntityDimensions getDimensions(Pose pose) {
        return super.getDimensions(pose).scale(getSize() / 10f);
    }

    protected float getSoundVolume() {
        return 1.0F;
    }

    public float getVoicePitch() {
        return (this.random.nextFloat() - this.random.nextFloat()) * 0.2F + 1.0F;
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
}

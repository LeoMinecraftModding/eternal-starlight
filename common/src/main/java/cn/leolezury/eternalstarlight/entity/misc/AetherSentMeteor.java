package cn.leolezury.eternalstarlight.entity.misc;

import cn.leolezury.eternalstarlight.client.particle.lightning.LightningParticleOptions;
import cn.leolezury.eternalstarlight.datagen.generator.DamageTypeGenerator;
import cn.leolezury.eternalstarlight.init.EntityInit;
import cn.leolezury.eternalstarlight.init.ItemInit;
import cn.leolezury.eternalstarlight.util.MathUtil;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntityDimensions;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.entity.monster.Enemy;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractHurtingProjectile;
import net.minecraft.world.entity.projectile.ProjectileUtil;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.event.ForgeEventFactory;
import org.jetbrains.annotations.Nullable;
import org.joml.Vector3f;

import java.util.UUID;

public class AethersentMeteor extends AbstractHurtingProjectile {
    protected static final EntityDataAccessor<Integer> SIZE = SynchedEntityData.defineId(AethersentMeteor.class, EntityDataSerializers.INT);

    public int getSize() {
        return entityData.get(SIZE);
    }

    public void setSize(int size) {
        entityData.set(SIZE, size);
    }

    protected static final EntityDataAccessor<Integer> TICKS_SINCE_LANDED = SynchedEntityData.defineId(AethersentMeteor.class, EntityDataSerializers.INT);

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

    public Vec3 getTargetPos() {
        return targetPos;
    }

    public void setTargetPos(Vec3 targetPos) {
        this.targetPos = targetPos;
    }

    public boolean onlyHurtEnemy = true;

    public AethersentMeteor(EntityType<? extends AbstractHurtingProjectile> type, Level level) {
        super(type, level);
        noPhysics = true;
    }

    public AethersentMeteor(Level level, LivingEntity entity, double x, double y, double z) {
        this(EntityInit.AETHERSENT_METEOR.get(), level);
        xo = x;
        yo = y;
        zo = z;
        setPos(x, y, z);
        setOwner(entity);
    }

    public static void createMeteorShower(Level level, LivingEntity entity, LivingEntity target, double targetX, double targetY, double targetZ, double height, boolean onlyHurtEnemy) {
        if (entity.getPersistentData().getInt("MeteorCoolDown") > 0) {
            return;
        }
        entity.getPersistentData().putInt("MeteorCoolDown", 1);
        for (int x = -1; x <= 1; x++) {
            for (int z = -1; z <= 1; z++) {
                AethersentMeteor meteor = new AethersentMeteor(level, entity, targetX + x, targetY + height, targetZ + z);
                meteor.setSize(entity.getRandom().nextInt(2, 5));
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

    @Override
    protected void defineSynchedData() {
        entityData.define(SIZE, 0);
        entityData.define(TICKS_SINCE_LANDED, 0);
        super.defineSynchedData();
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
                livingEntity.hurt(DamageTypeGenerator.getEntityDamageSource(level(), DamageTypeGenerator.METEOR, getOwner()), getSize() * damageScale * (getOwner() instanceof LivingEntity ? 0.01f : 1f));
            }
        }
    }

    private void land() {
        setTicksSinceLanded(1);
        playSound(SoundEvents.GENERIC_EXPLODE, getSoundVolume(), getVoicePitch());
    }

    @Override
    protected void onHit(HitResult result) {
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
        super.onHit(result);
    }

    @Override
    protected void onHitBlock(BlockHitResult result) {
        if (target == null && targetPos == null) {
            land();
        }
        super.onHitBlock(result);
    }

    @Override
    public void tick() {
        super.tick();
        if (getSize() <= 0) {
            discard();
            return;
        }
        if (target == null && targetId != null && !level().isClientSide) {
            if (((ServerLevel) this.level()).getEntity(targetId) instanceof LivingEntity livingEntity) {
                target = livingEntity;
            }
            if (target == null) {
                targetId = null;
            }
        }
        refreshDimensions();
        if (getTicksSinceLanded() > 0) {
            setDeltaMovement(0, 0, 0);
            if (getTicksSinceLanded() % 5 == 0) {
                CameraShake.createCameraShake(level(), position(), getSize() * 20, 0.005f * getSize(), 5, 5);
            }
            setTicksSinceLanded(getTicksSinceLanded() + 1);
            if (getTicksSinceLanded() > getSize() * 10) {
                discard();
                if (getOwner() == null && getSize() >= 10) {
                    spawnAtLocation(ItemInit.AETHERSENT_BLOCK.get());
                }
            }
            if (level().isClientSide) {
                level().addParticle(getSize() >= 8 ? ParticleTypes.EXPLOSION_EMITTER : ParticleTypes.EXPLOSION, getX(), getY() + 0.05 * getSize(), getZ(), 0, 0, 0);
                for (int i = 0; i < 5; i++) {
                    float pitch = random.nextInt(361);
                    float yaw = random.nextInt(361);
                    float len = random.nextInt(getSize());
                    Vec3 particleTarget = MathUtil.rotationToPosition(position(), getSize() / 2f, pitch, yaw);
                    Vec3 particleStart = MathUtil.rotationToPosition(position(), len, pitch, yaw);
                    Vec3 motion = particleTarget.subtract(particleStart);
                    level().addParticle(new LightningParticleOptions(new Vector3f(0.7f, 0.07f, 0.78f)), particleStart.x, particleStart.y, particleStart.z, motion.x, motion.y, motion.z);
                }
            } else if (getTicksSinceLanded() % 5 == 0) {
                doHurtEntity(0.5f);
            }
        } else {
            HitResult result = ProjectileUtil.getHitResultOnMoveVector(this, this::canHitEntity);
            if (result.getType() != HitResult.Type.MISS && !ForgeEventFactory.onProjectileImpact(this, result)) {
                this.onHit(result);
            }
            setDeltaMovement(0, -2, 0);
            if (level().isClientSide) {
                Vec3 motion = getDeltaMovement();
                for (int i = 0; i < 2; i++) {
                    level().addParticle(new LightningParticleOptions(new Vector3f(0.7f, 0.07f, 0.78f)), getX(), getY(), getZ(), -motion.x * 3, -motion.y * 3, -motion.z * 3);
                }
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

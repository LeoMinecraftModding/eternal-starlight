package cn.leolezury.eternalstarlight.common.entity.projectile;

import cn.leolezury.eternalstarlight.common.data.ESDamageTypes;
import cn.leolezury.eternalstarlight.common.entity.misc.CameraShake;
import cn.leolezury.eternalstarlight.common.particle.LightningParticleOptions;
import cn.leolezury.eternalstarlight.common.registry.ESBlocks;
import cn.leolezury.eternalstarlight.common.registry.ESEntities;
import cn.leolezury.eternalstarlight.common.registry.ESItems;
import cn.leolezury.eternalstarlight.common.registry.ESParticles;
import cn.leolezury.eternalstarlight.common.util.ESEntityUtil;
import cn.leolezury.eternalstarlight.common.util.ESTags;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.RandomSource;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntityDimensions;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.monster.Enemy;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractHurtingProjectile;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.Vec3;
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

    private Vec3 targetPos = Vec3.ZERO;

    public void setTargetPos(Vec3 targetPos) {
        this.targetPos = targetPos;
    }

    private boolean onlyHurtEnemy = true;

    public void setOnlyHurtEnemy(boolean onlyHurtEnemy) {
        this.onlyHurtEnemy = onlyHurtEnemy;
    }

    private boolean natural = true;

    public AethersentMeteor(EntityType<? extends AbstractHurtingProjectile> type, Level level) {
        super(type, level);
    }

    public AethersentMeteor(Level level, LivingEntity entity, double x, double y, double z) {
        this(ESEntities.AETHERSENT_METEOR.get(), level);
        xo = x;
        yo = y;
        zo = z;
        setPos(x, y, z);
        setOwner(entity);
    }

    public static void createMeteorShower(Level level, LivingEntity entity, LivingEntity target, double targetX, double targetY, double targetZ, double height, boolean onlyHurtEnemy) {
        if (!level.isClientSide) {
            CompoundTag tag = ESEntityUtil.getPersistentData(entity);
            if (tag.getInt("MeteorCooldown") > 0) {
                return;
            }
            tag.putInt("MeteorCooldown", 1);
            for (int x = -1; x <= 1; x++) {
                for (int z = -1; z <= 1; z++) {
                    RandomSource random = entity.getRandom();
                    AethersentMeteor meteor = new AethersentMeteor(level, entity, targetX + x + (random.nextFloat() - 0.5) * 3, targetY + height + (random.nextFloat() - 0.5) * 5, targetZ + z + (random.nextFloat() - 0.5) * 3);
                    meteor.setSize(random.nextInt(2, 5));
                    meteor.setTarget(target);
                    meteor.setTargetPos(new Vec3(targetX, targetY, targetZ));
                    meteor.onlyHurtEnemy = onlyHurtEnemy;
                    meteor.natural = false;
                    level.addFreshEntity(meteor);
                    if (level instanceof ServerLevel serverLevel) {
                        serverLevel.sendParticles(ParticleTypes.EXPLOSION, meteor.getX(), meteor.getY(), meteor.getZ(), 2, 0.2D, 0.2D, 0.2D, 0.0D);
                    }
                }
            }
        }
    }

    @Override
    protected void defineSynchedData(SynchedEntityData.Builder builder) {
        super.defineSynchedData(builder);
        builder.define(SIZE, 0);
    }

    public void readAdditionalSaveData(CompoundTag compoundTag) {
        setSize(compoundTag.getInt("Size"));
        if (compoundTag.hasUUID("Target")) {
            targetId = compoundTag.getUUID("Target");
        }
        targetPos = new Vec3(compoundTag.getDouble("TargetX"), compoundTag.getDouble("TargetY"), compoundTag.getDouble("TargetZ"));
        if (compoundTag.contains("OnlyHurtEnemy", CompoundTag.TAG_BYTE)) {
            onlyHurtEnemy = compoundTag.getBoolean("OnlyHurtEnemy");
        }
        if (compoundTag.contains("Natural", CompoundTag.TAG_BYTE)) {
            natural = compoundTag.getBoolean("Natural");
        }
    }

    public void addAdditionalSaveData(CompoundTag compoundTag) {
        compoundTag.putInt("Size", getSize());
        if (target != null) {
            compoundTag.putUUID("Target", target.getUUID());
        }
        if (targetPos != null) {
            compoundTag.putDouble("TargetX", targetPos.x);
            compoundTag.putDouble("TargetY", targetPos.y);
            compoundTag.putDouble("TargetZ", targetPos.z);
        }
        compoundTag.putBoolean("OnlyHurtEnemy", onlyHurtEnemy);
        compoundTag.putBoolean("Natural", natural);
    }

    private void handleHit() {
        if (natural) {
            dropAndDiscard();
        }
        for (LivingEntity livingEntity : level().getEntitiesOfClass(LivingEntity.class, getBoundingBox().inflate(getSize()))) {
            if ((!(getOwner() instanceof Player) || livingEntity instanceof Enemy || !onlyHurtEnemy) && (getOwner() == null || !getOwner().getUUID().equals(livingEntity.getUUID()))) {
                livingEntity.invulnerableTime = 0;
                livingEntity.hurt(ESDamageTypes.getEntityDamageSource(level(), ESDamageTypes.METEOR, getOwner()), getSize() * (float) 5 * (getOwner() instanceof LivingEntity ? 0.01f : 1f));
            }
        }
        if (getTarget() != null && getY() < getTarget().getY()) {
            dropAndDiscard();
        } else if (getY() < targetPos.y) {
            dropAndDiscard();
        }
    }

    private void dropAndDiscard() {
        if (!isRemoved()) {
            playSound(SoundEvents.GENERIC_EXPLODE.value(), getSoundVolume(), getVoicePitch());
            if (!level().isClientSide) {
                ((ServerLevel) level()).sendParticles(getSize() >= 8 ? ParticleTypes.EXPLOSION_EMITTER : ParticleTypes.EXPLOSION, getX(), getY() + 0.05 * getSize(), getZ(), 1, 0, 0, 0, 0);
                if (natural && getSize() >= 10) {
                    ItemEntity entity = spawnAtLocation(new ItemStack(ESItems.RAW_AETHERSENT.get(), random.nextInt(5, 9)));
                    for (int x = -3; x <= 3; x++) {
                        for (int y = -3; y <= 3; y++) {
                            for (int z = -3; z <= 3; z++) {
                                BlockPos pos = blockPosition().offset(x, y, z);
                                if (pos.distToCenterSqr(blockPosition().getCenter()) <= 3.5 && level().getBlockState(pos).is(ESTags.Blocks.AETHERSENT_METEOR_REPLACEABLE)) {
                                    level().setBlockAndUpdate(pos, random.nextBoolean() ? ESBlocks.RAW_AETHERSENT_BLOCK.get().defaultBlockState() : ESBlocks.NEBULAITE.get().defaultBlockState());
                                }
                            }
                        }
                    }
                    if (entity != null) {
                        entity.setGlowingTag(true);
                    }
                    for (int m = 0; m < ((ServerLevel) level()).players().size(); ++m) {
                        ServerPlayer serverPlayer = ((ServerLevel) level()).players().get(m);
                        ((ServerLevel) level()).sendParticles(serverPlayer, ESParticles.AETHERSENT_EXPLOSION.get(), true, getX(), getY(), getZ(), 1, 0, 0, 0, 0);
                    }
                }
                discard();
            }
        }
    }

    @Override
    protected void onHitBlock(BlockHitResult result) {
        super.onHitBlock(result);
        CameraShake.createCameraShake(level(), position(), getSize() * 20, 0.0002f * getSize(), 20, 20);
        handleHit();
        if (!level().isClientSide && target == null && targetPos == null) {
            dropAndDiscard();
        }
    }

    @Override
    protected void onHitEntity(EntityHitResult entityHitResult) {
        super.onHitEntity(entityHitResult);
        CameraShake.createCameraShake(level(), position(), getSize() * 20, 0.0002f * getSize(), 20, 20);
        handleHit();
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

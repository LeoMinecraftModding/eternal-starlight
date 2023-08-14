package cn.leolezury.eternalstarlight.entity.attack;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundAddEntityPacket;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MoverType;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

import java.util.UUID;

public abstract class AbstractOwnedEntity extends Entity {
    public AbstractOwnedEntity(EntityType<?> type, Level level) {
        super(type, level);
    }
    @Nullable
    private LivingEntity owner;
    @Nullable
    private UUID ownerId;
    public LivingEntity getOwner() {
        return owner;
    }
    public void setOwner(LivingEntity owner) {
        this.owner = owner;
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
    protected static final EntityDataAccessor<Integer> SPAWNED_TICKS = SynchedEntityData.defineId(AbstractOwnedEntity.class, EntityDataSerializers.INT);
    public int getSpawnedTicks() {
        return entityData.get(SPAWNED_TICKS);
    }
    public void setSpawnedTicks(int spawnedTicks) {
        entityData.set(SPAWNED_TICKS, spawnedTicks);
    }
    protected static final EntityDataAccessor<Integer> ATTACK_MODE = SynchedEntityData.defineId(AbstractOwnedEntity.class, EntityDataSerializers.INT);
    public int getAttackMode() {
        return entityData.get(ATTACK_MODE);
    }
    public void setAttackMode(int attackMode) {
        entityData.set(ATTACK_MODE, attackMode);
    }

    protected void readAdditionalSaveData(CompoundTag compoundTag) {
        if (compoundTag.hasUUID("Owner")) {
            ownerId = compoundTag.getUUID("Owner");
        }
        if (compoundTag.hasUUID("Target")) {
            targetId = compoundTag.getUUID("Target");
        }
        setSpawnedTicks(compoundTag.getInt("SpawnedTicks"));
        setAttackMode(compoundTag.getInt("AttackMode"));
    }

    protected void addAdditionalSaveData(CompoundTag compoundTag) {
        if (owner != null) {
            compoundTag.putUUID("Owner", owner.getUUID());
        }
        if (target != null) {
            compoundTag.putUUID("Target", target.getUUID());
        }
        compoundTag.putInt("SpawnedTicks", getSpawnedTicks());
        compoundTag.putInt("AttackMode", getAttackMode());
    }

    protected void defineSynchedData() {
        entityData.define(SPAWNED_TICKS, 0);
        entityData.define(ATTACK_MODE, 0);
    }

    public boolean shouldContinueToTick() {
        return false;
    }

    @Override
    public Packet<ClientGamePacketListener> getAddEntityPacket() {
        return new ClientboundAddEntityPacket(this);
    }

    protected float getSoundVolume() {
        return 1.0F;
    }

    public float getVoicePitch() {
        return (this.random.nextFloat() - this.random.nextFloat()) * 0.2F + 1.0F;
    }

    @Override
    public boolean hurt(DamageSource damageSource, float amount) {
        if (damageSource.equals(damageSources().genericKill())) {
            discard();
        }
        return false;
    }

    @Override
    public void tick() {
        super.tick();
        if (!level().isClientSide) {
            if (owner == null && ownerId != null) {
                if (((ServerLevel)this.level()).getEntity(ownerId) instanceof LivingEntity livingEntity) {
                    owner = livingEntity;
                }
                if (owner == null) {
                    ownerId = null;
                }
            }
            if (target == null && targetId != null) {
                if (((ServerLevel)this.level()).getEntity(targetId) instanceof LivingEntity livingEntity) {
                    target = livingEntity;
                }
                if (target == null) {
                    targetId = null;
                }
            }
            if (shouldContinueToTick()) {
                setSpawnedTicks(getSpawnedTicks() + 1);
            }
            this.move(MoverType.SELF, this.getDeltaMovement());
            if (!onGround() && !isNoGravity()) {
                setDeltaMovement(getDeltaMovement().add(0, isNoGravity() ? 0 : -0.2, 0));
            }
            setDeltaMovement(getDeltaMovement().scale(0.8));
        }
    }
}

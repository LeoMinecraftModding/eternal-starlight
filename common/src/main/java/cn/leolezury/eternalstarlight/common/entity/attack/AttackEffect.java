package cn.leolezury.eternalstarlight.common.entity.attack;

import net.minecraft.nbt.CompoundTag;
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

public class AttackEffect extends Entity {
	private static final String TAG_OWNER = "owner";
	private static final String TAG_TARGET = "target";
	private static final String TAG_SPAWNED_TICKS = "spawned_ticks";
	private static final String TAG_ATTACK_MODE = "attack_mode";

	public AttackEffect(EntityType<?> type, Level level) {
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
		this.ownerId = owner.getUUID();
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
		this.targetId = target.getUUID();
		this.target = target;
	}

	protected static final EntityDataAccessor<Integer> SPAWNED_TICKS = SynchedEntityData.defineId(AttackEffect.class, EntityDataSerializers.INT);

	public int getSpawnedTicks() {
		return this.getEntityData().get(SPAWNED_TICKS);
	}

	public void setSpawnedTicks(int spawnedTicks) {
		this.getEntityData().set(SPAWNED_TICKS, spawnedTicks);
	}

	protected static final EntityDataAccessor<Integer> ATTACK_MODE = SynchedEntityData.defineId(AttackEffect.class, EntityDataSerializers.INT);

	public int getAttackMode() {
		return this.getEntityData().get(ATTACK_MODE);
	}

	public void setAttackMode(int attackMode) {
		this.getEntityData().set(ATTACK_MODE, attackMode);
	}

	@Override
	protected void readAdditionalSaveData(CompoundTag compoundTag) {
		if (compoundTag.hasUUID(TAG_OWNER)) {
			ownerId = compoundTag.getUUID(TAG_OWNER);
		}
		if (compoundTag.hasUUID(TAG_TARGET)) {
			targetId = compoundTag.getUUID(TAG_TARGET);
		}
		setSpawnedTicks(compoundTag.getInt(TAG_SPAWNED_TICKS));
		setAttackMode(compoundTag.getInt(TAG_ATTACK_MODE));
	}

	@Override
	protected void addAdditionalSaveData(CompoundTag compoundTag) {
		if (owner != null) {
			compoundTag.putUUID(TAG_OWNER, owner.getUUID());
		}
		if (target != null) {
			compoundTag.putUUID(TAG_TARGET, target.getUUID());
		}
		compoundTag.putInt(TAG_SPAWNED_TICKS, getSpawnedTicks());
		compoundTag.putInt(TAG_ATTACK_MODE, getAttackMode());
	}

	@Override
	protected void defineSynchedData(SynchedEntityData.Builder builder) {
		builder.define(SPAWNED_TICKS, 0)
			.define(ATTACK_MODE, 0);
	}

	public boolean shouldContinueToTick() {
		return false;
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
				if (((ServerLevel) this.level()).getEntity(ownerId) instanceof LivingEntity livingEntity) {
					owner = livingEntity;
				}
				if (owner == null) {
					ownerId = null;
				}
			}
			if (target == null && targetId != null) {
				if (((ServerLevel) this.level()).getEntity(targetId) instanceof LivingEntity livingEntity) {
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
				applyGravity();
			}
			setDeltaMovement(getDeltaMovement().scale(0.8));
		}
	}
}

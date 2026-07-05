package cn.leolezury.eternalstarlight.common.entity.projectile;

import cn.leolezury.eternalstarlight.common.data.ESDamageTypes;
import cn.leolezury.eternalstarlight.common.entity.interfaces.TrailOwner;
import cn.leolezury.eternalstarlight.common.particle.RippleParticleOptions;
import cn.leolezury.eternalstarlight.common.util.ESEntityUtil;
import cn.leolezury.eternalstarlight.common.util.TrailEffect;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.ThrowableProjectile;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import org.joml.Vector4f;

import java.util.UUID;

public class SolarStarProjectile extends ThrowableProjectile implements TrailOwner {
	private static final String TAG_TARGET = "target";

	public SolarStarProjectile(EntityType<? extends SolarStarProjectile> entityType, Level level) {
		super(entityType, level);
		setNoGravity(true);
	}

	@Override
	protected void defineSynchedData(SynchedEntityData.Builder builder) {
	}

	private Entity target;
	private UUID targetId;

	public Entity getTarget() {
		return target;
	}

	public void setTarget(Entity target) {
		this.targetId = target.getUUID();
		this.target = target;
	}

	@Override
	public void tick() {
		super.tick();
		if (!level().isClientSide) {
			if (target == null && targetId != null && level() instanceof ServerLevel serverLevel) {
				Entity entity = serverLevel.getEntity(targetId);
				if (entity != null) {
					target = entity;
				}
				if (target == null) {
					targetId = null;
				}
			}
			if (target != null && target.isAlive()) {
				Vec3 diff = target.position().add(0, target.getBbHeight() / 2, 0).subtract(position()).normalize();
				setDeltaMovement(getDeltaMovement().add(diff.scale(0.03)));
			}
			if (tickCount > 300) {
				explode();
			}
		}
	}

	@Override
	protected void onHit(HitResult hitResult) {
		super.onHit(hitResult);
		if (!level().isClientSide && hitResult.getType() != HitResult.Type.MISS && (target == null || level().getEntitiesOfClass(Entity.class, getBoundingBox().inflate(2.5)).contains(target))) {
			explode();
		}
	}

	@Override
	protected void onHitBlock(BlockHitResult hitResult) {
		super.onHitBlock(hitResult);
		switch (hitResult.getDirection().getAxis()) {
			case X -> setDeltaMovement(getDeltaMovement().multiply(-0.5, 1, 1));
			case Y -> setDeltaMovement(getDeltaMovement().multiply(1, -0.5, 1));
			case Z -> setDeltaMovement(getDeltaMovement().multiply(1, 1, -0.5));
		}
	}

	private void explode() {
		if (!level().isClientSide) {
			for (LivingEntity entity : level().getEntitiesOfClass(LivingEntity.class, getBoundingBox().inflate(2.5))) {
				if (ESEntityUtil.shouldHarm(getOwner() != null ? getOwner() : this, entity)) {
					entity.hurt(ESDamageTypes.getIndirectEntityDamageSource(level(), ESDamageTypes.METEOR, this, getOwner()), 8);
				}
			}
			if (level() instanceof ServerLevel serverLevel) {
				RippleParticleOptions.addBlueExplosionRippleParticles(serverLevel, getX(), getY() + getBbHeight() / 2, getZ(), random);
			}
		}
		discard();
	}

	@Override
	public boolean isOnFire() {
		return false;
	}

	@Override
	public void readAdditionalSaveData(CompoundTag compoundTag) {
		super.readAdditionalSaveData(compoundTag);
		if (compoundTag.hasUUID(TAG_TARGET)) {
			targetId = compoundTag.getUUID(TAG_TARGET);
		}
	}

	@Override
	public void addAdditionalSaveData(CompoundTag compoundTag) {
		super.addAdditionalSaveData(compoundTag);
		if (target != null) {
			compoundTag.putUUID(TAG_TARGET, target.getUUID());
		}
	}

	@Override
	public TrailEffect createNewTrail() {
		return new TrailEffect(0.1f, 5);
	}

	@Override
	public void updateTrail(TrailEffect effect) {
		Vec3 oldPos = new Vec3(xOld, yOld, zOld);
		effect.update(getTrailPosition(oldPos));
		if (isRemoved()) {
			effect.setLength(Math.max(effect.getLength() - 0.5f, 0));
		}
	}

	@Override
	public Vector4f getTrailColor() {
		return new Vector4f(1f, 1f, 1f, 1f);
	}

	@Override
	public boolean isTrailFullBright() {
		return true;
	}

	@Override
	public boolean isTrailSolid() {
		return true;
	}
}

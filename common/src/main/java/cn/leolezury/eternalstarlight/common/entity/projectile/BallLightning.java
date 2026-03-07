package cn.leolezury.eternalstarlight.common.entity.projectile;

import cn.leolezury.eternalstarlight.common.data.ESDamageTypes;
import cn.leolezury.eternalstarlight.common.entity.interfaces.TrailOwner;
import cn.leolezury.eternalstarlight.common.network.ParticlePacket;
import cn.leolezury.eternalstarlight.common.particle.ESExplosionParticleOptions;
import cn.leolezury.eternalstarlight.common.particle.ExplosionShockParticleOptions;
import cn.leolezury.eternalstarlight.common.platform.ESPlatform;
import cn.leolezury.eternalstarlight.common.registry.ESEntities;
import cn.leolezury.eternalstarlight.common.util.ESEntityUtil;
import cn.leolezury.eternalstarlight.common.util.TrailEffect;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.ThrowableProjectile;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.CollisionContext;
import org.joml.Vector4f;

import java.util.UUID;

public class BallLightning extends ThrowableProjectile implements TrailOwner {
	private static final String TAG_TARGET = "target";
	private static final String TAG_SPAWNED_TICKS = "spawned_ticks";

	protected static final EntityDataAccessor<Integer> SPAWNED_TICKS = SynchedEntityData.defineId(BallLightning.class, EntityDataSerializers.INT);

	public int getSpawnedTicks() {
		return this.getEntityData().get(SPAWNED_TICKS);
	}

	public void setSpawnedTicks(int spawnedTicks) {
		this.getEntityData().set(SPAWNED_TICKS, spawnedTicks);
	}

	protected static final EntityDataAccessor<Integer> TARGET_ID = SynchedEntityData.defineId(BallLightning.class, EntityDataSerializers.INT);

	public void setTargetId(int targetId) {
		this.getEntityData().set(TARGET_ID, targetId);
	}

	public int getTargetId() {
		return this.getEntityData().get(TARGET_ID);
	}

	public BallLightning(EntityType<? extends BallLightning> entityType, Level level) {
		super(entityType, level);
	}

	public BallLightning(Level level, LivingEntity livingEntity) {
		super(ESEntities.BALL_LIGHTNING.get(), livingEntity, level);
	}

	public BallLightning(Level level, double x, double y, double z) {
		super(ESEntities.BALL_LIGHTNING.get(), x, y, z, level);
	}

	@Override
	protected void defineSynchedData(SynchedEntityData.Builder builder) {
		builder.define(SPAWNED_TICKS, 0)
			.define(TARGET_ID, -1);
	}

	private Entity target;
	private UUID targetId;

	public Entity getTarget() {
		return target;
	}

	public void setTarget(Entity target) {
		this.targetId = target.getUUID();
		this.target = target;
		if (!level().isClientSide) {
			setTargetId(target.getId());
		}
	}

	@Override
	public void tick() {
		Vec3 movement = getDeltaMovement();
		super.tick();
		setDeltaMovement(getDeltaMovement().normalize().scale(movement.length()));
		if (!level().isClientSide) {
			Entity owner = getOwner();
			if (owner != null && owner.distanceTo(this) > 32) {
				explodeAndDiscard();
				discard();
			}
			if (target != null && target.distanceTo(this) > 16) {
				target = null;
				targetId = null;
			}
			if (target == null && targetId != null && level() instanceof ServerLevel serverLevel) {
				Entity entity = serverLevel.getEntity(targetId);
				if (entity != null) {
					target = entity;
				}
				if (target == null) {
					targetId = null;
				}
			}
			if (target != null) {
				setTargetId(target.getId());
				ESEntityUtil.RaytraceResult result = ESEntityUtil.raytrace(level(), CollisionContext.of(this), position().add(0, getBbHeight() / 2, 0), target.position().add(0, target.getBbHeight() / 2, 0));
				for (Entity entity : result.entities()) {
					if (entity instanceof LivingEntity && ESEntityUtil.shouldHarm(getOwner(), entity) && entity.hurt(ESDamageTypes.getIndirectEntityDamageSource(level(), ESDamageTypes.ELECTRIC_SHOCK, this, getOwner()), 8)) {
						entity.igniteForSeconds(2);
					}
				}
			}
			if (getSpawnedTicks() > 600) {
				explodeAndDiscard();
				discard();
			}
			setSpawnedTicks(getSpawnedTicks() + 1);
		} else {
			this.noCulling = true;
		}
	}

	@Override
	public boolean shouldRender(double x, double y, double z) {
		return this.shouldRenderAtSqrDistance(distanceToSqr(x, y, z) / 16);
	}

	@Override
	protected void applyGravity() {

	}

	@Override
	public boolean isOnFire() {
		return false;
	}

	@Override
	protected void onHitBlock(BlockHitResult hitResult) {
		super.onHitBlock(hitResult);
		switch (hitResult.getDirection().getAxis()) {
			case X -> setDeltaMovement(getDeltaMovement().multiply(-1, 1, 1));
			case Y -> setDeltaMovement(getDeltaMovement().multiply(1, -1, 1));
			case Z -> setDeltaMovement(getDeltaMovement().multiply(1, 1, -1));
		}
	}

	private void explodeAndDiscard() {
		playSound(SoundEvents.GENERIC_EXPLODE.value());
		if (level() instanceof ServerLevel serverLevel) {
			serverLevel.sendParticles(ESExplosionParticleOptions.ENERGY, getX(), getY() + getBbHeight() / 2, getZ(), 20, getBbWidth() / 2, getBbHeight() / 2, getBbWidth() / 2, 0);
			for (int i = 0; i < 20; i++) {
				Vec3 speed = new Vec3((this.random.nextFloat() - this.random.nextFloat()) * 0.1F, this.random.nextFloat() * 0.05F, (this.random.nextFloat() - this.random.nextFloat()) * 0.1F).normalize();
				ESPlatform.INSTANCE.sendToAllClients(serverLevel, new ParticlePacket(ExplosionShockParticleOptions.ENERGY, position().x + speed.x * 1.2, position().y + speed.y * 1.2, position().z + speed.z * 1.2, speed.x, speed.y, speed.z));
			}
		}
		for (LivingEntity entity : level().getEntitiesOfClass(LivingEntity.class, getBoundingBox().inflate(2))) {
			if (ESEntityUtil.shouldHarm(getOwner(), entity)) {
				if (getOwner() instanceof Player) {
					entity.invulnerableTime = 0;
				}
				entity.hurt(ESDamageTypes.getIndirectEntityDamageSource(level(), ESDamageTypes.ENERGIZED_FLAME, this, getOwner()), 8);
			}
		}
		discard();
	}

	@Override
	public void readAdditionalSaveData(CompoundTag compoundTag) {
		super.readAdditionalSaveData(compoundTag);
		if (compoundTag.hasUUID(TAG_TARGET)) {
			targetId = compoundTag.getUUID(TAG_TARGET);
		}
		setSpawnedTicks(compoundTag.getInt(TAG_SPAWNED_TICKS));
	}

	@Override
	public void addAdditionalSaveData(CompoundTag compoundTag) {
		super.addAdditionalSaveData(compoundTag);
		if (target != null) {
			compoundTag.putUUID(TAG_TARGET, target.getUUID());
		}
		compoundTag.putInt(TAG_SPAWNED_TICKS, getSpawnedTicks());
	}

	@Override
	public TrailEffect createNewTrail() {
		return new TrailEffect(0.125f, 10);
	}

	@Override
	public void updateTrail(TrailEffect effect) {
		Vec3 oldPos = new Vec3(xOld, yOld, zOld);
		effect.update(oldPos.add(0, getBbHeight() / 2, 0));
		if (isRemoved()) {
			effect.setLength(Math.max(effect.getLength() - 0.5f, 0));
		}
	}

	@Override
	public Vector4f getTrailColor() {
		return new Vector4f(128 / 255f, 255 / 255f, 255 / 255f, 2f);
	}
}

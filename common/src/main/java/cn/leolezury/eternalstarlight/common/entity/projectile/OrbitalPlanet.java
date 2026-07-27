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
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.CollisionContext;
import org.joml.Vector4f;

import java.util.UUID;

public class OrbitalPlanet extends ThrowableProjectile implements TrailOwner {
	private static final String TAG_ORBIT_RADIUS = "orbit_radius";
	private static final String TAG_ORBIT_SPEED = "orbit_speed";
	private static final String TAG_ORBIT_ANGLE = "orbit_angle";
	private static final String TAG_PRE_FIRE_TICKS = "pre_fire_ticks";
	private static final String TAG_FIRED = "fired";
	private static final String TAG_LIFE_TICKS = "life_ticks";

	public OrbitalPlanet(EntityType<? extends OrbitalPlanet> entityType, Level level) {
		super(entityType, level);
		setNoGravity(true);
	}

	@Override
	protected void defineSynchedData(SynchedEntityData.Builder builder) {
	}

	private Entity target;
	private UUID targetId;

	private float orbitRadius;
	private float orbitSpeed;
	private float orbitAngle;
	private int preFireTicks;
	private boolean fired;
	private int lifeTicks;

	private int lerpSteps;
	private double lerpX, lerpY, lerpZ;
	private float lerpYRot, lerpXRot;

	@Override
	public void lerpTo(double x, double y, double z, float yRot, float xRot, int steps) {
		this.lerpSteps = steps;
		this.lerpX = x;
		this.lerpY = y;
		this.lerpZ = z;
		this.lerpYRot = yRot;
		this.lerpXRot = xRot;
	}

	@Override
	public double lerpTargetX() {
		return this.lerpX;
	}

	@Override
	public double lerpTargetY() {
		return this.lerpY;
	}

	@Override
	public double lerpTargetZ() {
		return this.lerpZ;
	}

	@Override
	public float lerpTargetYRot() {
		return this.lerpYRot;
	}

	@Override
	public float lerpTargetXRot() {
		return this.lerpXRot;
	}

	public void setOrbitRadius(float radius) {
		this.orbitRadius = radius;
	}

	public float getOrbitRadius() {
		return orbitRadius;
	}

	public void setOrbitSpeed(float speed) {
		this.orbitSpeed = speed;
	}

	public float getOrbitSpeed() {
		return orbitSpeed;
	}

	public void setOrbitAngle(float angle) {
		this.orbitAngle = angle;
	}

	public float getOrbitAngle() {
		return orbitAngle;
	}

	public void setPreFireTicks(int ticks) {
		this.preFireTicks = ticks;
	}

	public int getPreFireTicks() {
		return preFireTicks;
	}

	public void setTarget(Entity target) {
		this.targetId = target.getUUID();
		this.target = target;
	}

	public void setOrbitData(float radius, float speed, float angle, int preFireTicks) {
		setOrbitRadius(radius);
		setOrbitSpeed(speed);
		setOrbitAngle(angle);
		setPreFireTicks(preFireTicks);
	}

	@Override
	public void tick() {
		super.tick();
		if (level().isClientSide && this.lerpSteps > 0) {
			this.lerpPositionAndRotationStep(this.lerpSteps, this.lerpX, this.lerpY, this.lerpZ, this.lerpYRot, this.lerpXRot);
			this.lerpSteps--;
		}
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

			Entity owner = getOwner();

			lifeTicks++;
			if (!fired) {
				if (owner != null) {
					Vec3 orbitCenter = owner.position().add(0, owner.getBbHeight() / 2, 0);
					float currentRadius = Math.min(lifeTicks / (preFireTicks * 0.4f), 1f) * orbitRadius;
					float nextAngle = orbitAngle + orbitSpeed;
					double x = orbitCenter.x + currentRadius * Math.cos(Math.toRadians(nextAngle));
					double z = orbitCenter.z + currentRadius * Math.sin(Math.toRadians(nextAngle));
					Vec3 targetPos = new Vec3(x, orbitCenter.y, z);
					Vec3 movement = targetPos.subtract(position()).normalize();
					setPos(targetPos);
					orbitAngle = nextAngle;
					if (tickCount > preFireTicks && target != null && target.isAlive()) {
						Vec3 toTarget = target.position().add(0, target.getBbHeight() / 2, 0).subtract(position()).normalize();
						if (movement.dot(toTarget) >= Math.cos(Math.toRadians(60)) || tickCount > preFireTicks + 60) {
							fired = true;
							setDeltaMovement(movement.scale(0.6));
						}
					}
				}
			} else {
				if (target != null && target.isAlive()) {
					Vec3 diff = target.position().add(0, target.getBbHeight() / 2, 0).subtract(position()).normalize();
					setDeltaMovement(getDeltaMovement().add(diff.scale(0.15)).normalize().scale(0.6));
					Vec3 dir = getDeltaMovement().normalize();
					Vec3 end = position().add(dir.scale(12));
					if (ESEntityUtil.raytrace(level(), CollisionContext.of(this), position(), end, 0).entities().contains(target)) {
						target = null;
						targetId = null;
					}
				} else {
					setDeltaMovement(getDeltaMovement().normalize().scale(0.6));
				}
			}
			if (lifeTicks > preFireTicks + 240 || (owner == null && lifeTicks > 20)) {
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

	private void explode() {
		if (!level().isClientSide) {
			for (LivingEntity entity : level().getEntitiesOfClass(LivingEntity.class, getBoundingBox().inflate(2.5))) {
				if (ESEntityUtil.shouldHarm(getOwner() != null ? getOwner() : this, entity)) {
					entity.hurt(ESDamageTypes.getIndirectEntityDamageSource(level(), ESDamageTypes.METEOR, this, getOwner()), 8);
				}
			}
			if (level() instanceof ServerLevel serverLevel) {
				RippleParticleOptions.addFlareExplosionRippleParticles(serverLevel, getX(), getY() + getBbHeight() / 2, getZ(), random, 1.2f, RippleParticleOptions.GOLD, RippleParticleOptions.ORANGE);
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
		orbitRadius = compoundTag.getFloat(TAG_ORBIT_RADIUS);
		orbitSpeed = compoundTag.getFloat(TAG_ORBIT_SPEED);
		orbitAngle = compoundTag.getFloat(TAG_ORBIT_ANGLE);
		preFireTicks = compoundTag.getInt(TAG_PRE_FIRE_TICKS);
		fired = compoundTag.getBoolean(TAG_FIRED);
		lifeTicks = compoundTag.getInt(TAG_LIFE_TICKS);
	}

	@Override
	public void addAdditionalSaveData(CompoundTag compoundTag) {
		super.addAdditionalSaveData(compoundTag);
		compoundTag.putFloat(TAG_ORBIT_RADIUS, orbitRadius);
		compoundTag.putFloat(TAG_ORBIT_SPEED, orbitSpeed);
		compoundTag.putFloat(TAG_ORBIT_ANGLE, orbitAngle);
		compoundTag.putInt(TAG_PRE_FIRE_TICKS, preFireTicks);
		compoundTag.putBoolean(TAG_FIRED, fired);
		compoundTag.putInt(TAG_LIFE_TICKS, lifeTicks);
	}

	@Override
	public TrailEffect createNewTrail() {
		return new TrailEffect(0.12f, 10);
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

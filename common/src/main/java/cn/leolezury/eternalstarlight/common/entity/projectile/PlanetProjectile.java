package cn.leolezury.eternalstarlight.common.entity.projectile;

import cn.leolezury.eternalstarlight.common.data.ESDamageTypes;
import cn.leolezury.eternalstarlight.common.entity.interfaces.TrailOwner;
import cn.leolezury.eternalstarlight.common.particle.RippleParticleOptions;
import cn.leolezury.eternalstarlight.common.util.ESEntityUtil;
import cn.leolezury.eternalstarlight.common.util.TrailEffect;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.ThrowableProjectile;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import org.joml.Vector4f;

public class PlanetProjectile extends ThrowableProjectile implements TrailOwner {
	private float orbitAngle = 0f;
	private float orbitRadius = 3f;
	private float orbitSpeed = 2f;
	private int orbitPhase = 0;
	private int orbitTicks = 0;
	private int maxOrbitTicks = 250;
	private LivingEntity orbitTarget;
	private Vec3 orbitNormal;
	private Vec3 orbitU, orbitV;
	private int life = 0;
	private int maxLife = 500;
	private float baseDamage = 7f;
	private LivingEntity target;
	private float homingStrength = 0.03f;

	public PlanetProjectile(EntityType<? extends PlanetProjectile> entityType, Level level) {
		super(entityType, level);
		setNoGravity(true);
	}

	@Override
	protected void defineSynchedData(SynchedEntityData.Builder builder) {
	}

	@Override
	public void tick() {
		super.tick();
		life++;
		if (orbitPhase == 0 && orbitTarget != null && orbitTarget.isAlive()) {
			orbitTicks++;
			orbitAngle += orbitSpeed * 0.05f;

			if (orbitNormal == null) {
				Vec3 toTarget;
				if (target != null && target.isAlive()) {
					toTarget = target.position().subtract(orbitTarget.position()).normalize();
				} else {
					toTarget = new Vec3(1, 0, 0);
				}
				Vec3 up = new Vec3(0, 1, 0);
				orbitNormal = up.subtract(toTarget.scale(up.dot(toTarget))).normalize();
				orbitU = toTarget.cross(orbitNormal).normalize();
				orbitV = orbitNormal.cross(orbitU).normalize();
			}

			Vec3 bossCenter = orbitTarget.position().add(0, orbitTarget.getBbHeight() / 2, 0);
			if (target != null && target.isAlive()) {
				float targetDist = orbitTarget.distanceTo(target);
				orbitRadius += (Math.min(targetDist, 12f) - orbitRadius) * 0.005f;
			}

			double x = bossCenter.x + orbitU.x * Math.cos(orbitAngle) * orbitRadius + orbitV.x * Math.sin(orbitAngle) * orbitRadius;
			double y = bossCenter.y + orbitU.y * Math.cos(orbitAngle) * orbitRadius + orbitV.y * Math.sin(orbitAngle) * orbitRadius;
			double z = bossCenter.z + orbitU.z * Math.cos(orbitAngle) * orbitRadius + orbitV.z * Math.sin(orbitAngle) * orbitRadius;
			setPos(x, y, z);

			if (orbitTicks >= maxOrbitTicks) {
				orbitPhase = 1;
				if (target != null && target.isAlive()) {
					Vec3 toTarget = target.position().add(0, target.getBbHeight() / 2, 0).subtract(position()).normalize();
					setDeltaMovement(toTarget.scale(0.8));
				}
			}
		} else if (orbitPhase == 1) {
			if (target != null && target.isAlive() && life % 3 == 0) {
				Vec3 diff = target.getEyePosition().subtract(position()).normalize();
				setDeltaMovement(getDeltaMovement().add(diff.scale(homingStrength)));
			}
		}
		if (life > maxLife) {
			explode();
		}
	}

	@Override
	protected void onHit(HitResult hitResult) {
		if (hitResult.getType() != HitResult.Type.MISS && orbitPhase == 1) {
			if (!level().isClientSide) {
				for (LivingEntity entity : level().getEntitiesOfClass(LivingEntity.class, getBoundingBox().inflate(2))) {
					if (ESEntityUtil.shouldHarm(getOwner() != null ? getOwner() : this, entity)) {
						entity.hurt(ESDamageTypes.getIndirectEntityDamageSource(level(), ESDamageTypes.METEOR, this, getOwner()), baseDamage);
					}
				}
			}
		}
	}

	private void explode() {
		if (!level().isClientSide) {
			for (LivingEntity entity : level().getEntitiesOfClass(LivingEntity.class, getBoundingBox().inflate(3))) {
				if (ESEntityUtil.shouldHarm(getOwner() != null ? getOwner() : this, entity)) {
					entity.hurt(ESDamageTypes.getIndirectEntityDamageSource(level(), ESDamageTypes.METEOR, this, getOwner()), baseDamage);
				}
			}
			if (level() instanceof ServerLevel serverLevel) {
				RippleParticleOptions.addFlareExplosionRippleParticles(serverLevel, getX(), getY() + getBbHeight() / 2, getZ(), random);
			}
		}
		discard();
	}

	public void setOrbitTarget(LivingEntity orbitTarget) {
		this.orbitTarget = orbitTarget;
	}

	public void setTarget(LivingEntity target) {
		this.target = target;
	}

	public void setOrbitRadius(float orbitRadius) {
		this.orbitRadius = orbitRadius;
	}

	public void setOrbitSpeed(float orbitSpeed) {
		this.orbitSpeed = orbitSpeed;
	}

	public void setOrbitAngle(float orbitAngle) {
		this.orbitAngle = orbitAngle;
	}

	public void setMaxOrbitTicks(int maxOrbitTicks) {
		this.maxOrbitTicks = maxOrbitTicks;
	}

	public int getOrbitPhase() {
		return orbitPhase;
	}

	@Override
	public TrailEffect createNewTrail() {
		return new TrailEffect(0.12f, 5);
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
		return new Vector4f(1f, 0.6f, 0.2f, 1f);
	}

	@Override
	public boolean isTrailFullBright() {
		return true;
	}

	@Override
	public boolean isTrailSolid() {
		return true;
	}

	@Override
	public void readAdditionalSaveData(CompoundTag compoundTag) {
		super.readAdditionalSaveData(compoundTag);
		life = compoundTag.getInt("life");
		orbitPhase = compoundTag.getInt("orbit_phase");
		orbitTicks = compoundTag.getInt("orbit_ticks");
		orbitAngle = compoundTag.getFloat("orbit_angle");
	}

	@Override
	public void addAdditionalSaveData(CompoundTag compoundTag) {
		super.addAdditionalSaveData(compoundTag);
		compoundTag.putInt("life", life);
		compoundTag.putInt("orbit_phase", orbitPhase);
		compoundTag.putInt("orbit_ticks", orbitTicks);
		compoundTag.putFloat("orbit_angle", orbitAngle);
	}
}

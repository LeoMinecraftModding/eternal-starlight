package cn.leolezury.eternalstarlight.common.entity.attack;

import cn.leolezury.eternalstarlight.common.data.ESDamageTypes;
import cn.leolezury.eternalstarlight.common.particle.RippleParticleOptions;
import cn.leolezury.eternalstarlight.common.util.ESEntityUtil;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.util.Mth;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.TraceableEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.CollisionContext;
import org.jetbrains.annotations.Nullable;
import org.joml.Vector3f;

import java.util.UUID;

public class SolarStrikeEntity extends Entity implements TraceableEntity {
	private static final String TAG_OWNER = "owner";
	private static final String TAG_SPAWNED_TICKS = "spawned_ticks";
	private static final int MAX_LENGTH = 20;

	public static final int DAMAGE_TIME = 3;
	public static final int FADE_TIME = 4;

	@Nullable
	private LivingEntity owner;
	@Nullable
	private UUID ownerId;

	@Override
	public LivingEntity getOwner() {
		return owner;
	}

	public void setOwner(LivingEntity owner) {
		this.ownerId = owner.getUUID();
		this.owner = owner;
	}

	protected static final EntityDataAccessor<Integer> SPAWNED_TICKS = SynchedEntityData.defineId(SolarStrikeEntity.class, EntityDataSerializers.INT);

	public int getSpawnedTicks() {
		return this.getEntityData().get(SPAWNED_TICKS);
	}

	public void setSpawnedTicks(int spawnedTicks) {
		this.getEntityData().set(SPAWNED_TICKS, spawnedTicks);
	}

	protected static final EntityDataAccessor<Integer> CHARGE_DURATION = SynchedEntityData.defineId(SolarStrikeEntity.class, EntityDataSerializers.INT);

	public int getChargeDuration() {
		return this.getEntityData().get(CHARGE_DURATION);
	}

	public void setChargeDuration(int chargeDuration) {
		this.getEntityData().set(CHARGE_DURATION, chargeDuration);
	}

	protected static final EntityDataAccessor<Vector3f> BEAM_TARGET = SynchedEntityData.defineId(SolarStrikeEntity.class, EntityDataSerializers.VECTOR3);

	public Vec3 getBeamTarget() {
		Vec3 beamTarget = new Vec3(this.getEntityData().get(BEAM_TARGET));
		if (beamTarget.distanceTo(position()) > MAX_LENGTH) {
			beamTarget = position().add(beamTarget.subtract(position()).normalize().scale(MAX_LENGTH));
		}
		return beamTarget;
	}

	public void setBeamTarget(Vec3 beamTarget) {
		if (beamTarget.distanceTo(position()) > MAX_LENGTH) {
			beamTarget = position().add(beamTarget.subtract(position()).normalize().scale(MAX_LENGTH));
		}
		this.getEntityData().set(BEAM_TARGET, beamTarget.toVector3f());
	}

	private int oldAnimationTicks, animationTicks;

	public float getAnimationTicks(float partialTicks) {
		return Mth.lerp(partialTicks, oldAnimationTicks, animationTicks);
	}

	public SolarStrikeEntity(EntityType<? extends SolarStrikeEntity> entityType, Level level) {
		super(entityType, level);
		noCulling = true;
	}

	@Override
	protected void defineSynchedData(SynchedEntityData.Builder builder) {
		builder.define(SPAWNED_TICKS, 0)
			.define(CHARGE_DURATION, 20)
			.define(BEAM_TARGET, new Vector3f());
	}

	@Override
	public boolean isOnFire() {
		return false;
	}

	@Override
	public void tick() {
		super.tick();
		if (!level().isClientSide) {
			if (level() instanceof ServerLevel serverLevel && owner == null && ownerId != null) {
				if (serverLevel.getEntity(ownerId) instanceof LivingEntity livingEntity) {
					owner = livingEntity;
				}
				if (owner == null) {
					ownerId = null;
				}
			}
			if (getSpawnedTicks() >= getChargeDuration() + DAMAGE_TIME + FADE_TIME) {
				discard();
			}
			if (level() instanceof ServerLevel serverLevel && getSpawnedTicks() == getChargeDuration() + 1 && getOwner() != null) {
				ESEntityUtil.RaytraceResult result = ESEntityUtil.raytrace(level(), CollisionContext.of(this), position().add(position().subtract(getBeamTarget())), getBeamTarget());
				for (Entity entity : result.entities()) {
					if (entity instanceof LivingEntity && ESEntityUtil.shouldHarm(getOwner(), entity) && entity.hurt(ESDamageTypes.getIndirectEntityDamageSource(level(), ESDamageTypes.ELECTRIC_SHOCK, this, getOwner()), 8)) {
						entity.igniteForSeconds(2);
					}
				}
				RippleParticleOptions.addFlareExplosionRippleParticles(serverLevel, getX(), getY(), getZ(), random, 0.8f, RippleParticleOptions.ORANGE, RippleParticleOptions.GOLD);
				if (random.nextFloat() < 0.3f) {
					for (int i = 0; i < 2; i++) {
						Vec3 pos = position().add(getBeamTarget().subtract(position()).scale((random.nextDouble() - 0.5) * 2));
						RippleParticleOptions.addFlareExplosionRippleParticles(serverLevel, pos.x, pos.y, pos.z, random, 0.6f, RippleParticleOptions.ORANGE, RippleParticleOptions.GOLD);
					}
				}
			}
			setSpawnedTicks(getSpawnedTicks() + 1);
		} else {
			oldAnimationTicks = animationTicks;
			if (animationTicks == 0) {
				animationTicks = getSpawnedTicks();
			} else {
				animationTicks++;
			}
		}
	}

	@Override
	public boolean shouldRenderAtSqrDistance(double d) {
		return true;
	}

	@Override
	public boolean hurt(DamageSource damageSource, float amount) {
		if (damageSource.is(DamageTypeTags.BYPASSES_INVULNERABILITY)) {
			discard();
			return true;
		}
		return false;
	}

	@Override
	protected void readAdditionalSaveData(CompoundTag tag) {
		if (tag.hasUUID(TAG_OWNER)) {
			ownerId = tag.getUUID(TAG_OWNER);
		}
		setSpawnedTicks(tag.getInt(TAG_SPAWNED_TICKS));
	}

	@Override
	protected void addAdditionalSaveData(CompoundTag tag) {
		if (owner != null) {
			tag.putUUID(TAG_OWNER, owner.getUUID());
		}
		tag.putInt(TAG_SPAWNED_TICKS, getSpawnedTicks());
	}
}

package cn.leolezury.eternalstarlight.common.entity.attack.ray;

import cn.leolezury.eternalstarlight.common.data.ESDamageTypes;
import cn.leolezury.eternalstarlight.common.entity.interfaces.RayAttackUser;
import cn.leolezury.eternalstarlight.common.util.ESEntityUtil;
import cn.leolezury.eternalstarlight.common.util.ESMathUtil;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.util.Mth;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.TraceableEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.material.PushReaction;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.CollisionContext;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;

public class RayAttack extends Entity implements TraceableEntity {
	protected static final EntityDataAccessor<Integer> CASTER = SynchedEntityData.defineId(RayAttack.class, EntityDataSerializers.INT);

	public int getCasterId() {
		return this.getEntityData().get(CASTER);
	}

	public Optional<Entity> getCaster() {
		if (level().isClientSide) {
			int casterId = this.getCasterId();
			if (caster == null || casterId != caster.getId()) {
				caster = level().getEntity(casterId);
			}
		}
		return Optional.ofNullable(caster);
	}

	public void setCaster(Entity caster) {
		this.getEntityData().set(CASTER, caster != null ? caster.getId() : -1);
		this.caster = caster;
	}

	protected static final EntityDataAccessor<Float> PITCH = SynchedEntityData.defineId(RayAttack.class, EntityDataSerializers.FLOAT);

	public float getPitch() {
		return this.getEntityData().get(PITCH);
	}

	public void setPitch(float pitch) {
		this.getEntityData().set(PITCH, pitch);
	}

	protected static final EntityDataAccessor<Float> YAW = SynchedEntityData.defineId(RayAttack.class, EntityDataSerializers.FLOAT);

	public float getYaw() {
		return this.getEntityData().get(YAW);
	}

	public void setYaw(float yaw) {
		this.getEntityData().set(YAW, yaw);
	}

	protected static final EntityDataAccessor<Float> LENGTH = SynchedEntityData.defineId(RayAttack.class, EntityDataSerializers.FLOAT);

	public float getLength() {
		return this.getEntityData().get(LENGTH);
	}

	public void setLength(float length) {
		this.getEntityData().set(LENGTH, length);
	}

	public float renderPitch, renderYaw, prevPitch, prevYaw;
	private Entity caster;

	public RayAttack(EntityType<? extends RayAttack> type, Level world) {
		super(type, world);
		noCulling = true;
	}

	public RayAttack(EntityType<? extends RayAttack> type, Level world, LivingEntity caster, double x, double y, double z, float yaw, float pitch) {
		this(type, world);
		this.setCaster(caster);
		this.setYaw(yaw);
		this.setPitch(pitch);
		this.setLength(getRadius());
		this.setPos(x, y, z);
	}

	@Override
	protected void defineSynchedData(SynchedEntityData.Builder builder) {
		builder.define(CASTER, -1)
			.define(PITCH, 0f)
			.define(YAW, 0f)
			.define(LENGTH, 0f);
	}

	@Override
	public PushReaction getPistonPushReaction() {
		return PushReaction.IGNORE;
	}

	@Override
	public void tick() {
		super.tick();
		if (!level().isClientSide) {
			update();
			Vec3 idealEndPos = ESMathUtil.rotationToPosition(position(), getRadius(), getPitch(), getYaw());
			Vec3 endPos = idealEndPos;
			ESEntityUtil.RaytraceResult result = ESEntityUtil.raytrace(level(), CollisionContext.of(this), position(), idealEndPos);
			boolean hasBlock = result.blockHitResult() != null && result.blockHitResult().getType() != HitResult.Type.MISS;
			if (hasBlock) {
				endPos = result.blockHitResult().getLocation();
			}
			setLength((float) endPos.distanceTo(position()));
			result = ESEntityUtil.raytrace(level(), CollisionContext.of(this), position(), endPos);
			onHit(result);
			if ((caster != null && caster.getId() != getCasterId()) || tickCount % 20 == 0) {
				setCaster(caster);
			}
		} else {
			prevYaw = renderYaw;
			prevPitch = renderPitch;
			renderYaw = getYaw();
			renderPitch = getPitch();
			xo = getX();
			yo = getY();
			zo = getZ();
			Vec3 endPos = ESMathUtil.rotationToPosition(position(), getLength(), getPitch(), getYaw());
			addEndParticles(endPos);
		}
	}

	@Override
	public boolean isOnFire() {
		return false;
	}

	@Override
	public boolean hurt(DamageSource damageSource, float amount) {
		if (damageSource.is(DamageTypeTags.BYPASSES_INVULNERABILITY)) {
			discard();
			return true;
		}
		return false;
	}

	public int getRadius() {
		return 20;
	}

	public void onHit(ESEntityUtil.RaytraceResult result) {
		for (Entity target : result.entities()) {
			if (target instanceof LivingEntity living && (getCaster().isEmpty() || living != getCaster().get())) {
				doHurtTarget(living);
			}
		}
	}

	public void doHurtTarget(LivingEntity target) {
		getCaster().ifPresent(caster -> {
			if (ESEntityUtil.shouldHarm(caster, target) && target.hurt(ESDamageTypes.getIndirectEntityDamageSource(level(), ESDamageTypes.LASER, this, caster), getAttackDamage())) {
				target.setRemainingFireTicks(Math.max(target.getRemainingFireTicks(), 100));
			}
		});
	}

	public void addEndParticles(Vec3 endPos) {
	}

	public float getRotationSpeed() {
		return 1.0f;
	}

	public float getAttackDamage() {
		return 8f;
	}

	@Override
	public void push(Entity entity) {
	}

	@Override
	public boolean isPickable() {
		return false;
	}

	@Override
	public boolean isPushable() {
		return false;
	}

	@Override
	public boolean shouldRenderAtSqrDistance(double distance) {
		return distance < 1024;
	}

	@Override
	protected void readAdditionalSaveData(CompoundTag compoundTag) {

	}

	@Override
	protected void addAdditionalSaveData(CompoundTag compoundTag) {

	}

	private void update() {
		updateRotations();
		updatePosition();
	}

	protected float rotateTowards(float from, float to, float maxAngle) {
		float f = Mth.degreesDifference(from, to);
		float f1 = Mth.clamp(f, -maxAngle, maxAngle);
		return from + f1;
	}

	public void updateRotations() {
		getCaster().ifPresent(caster -> {
			if (caster instanceof LivingEntity living) {
				if (caster instanceof RayAttackUser shooter && !shooter.isRayFollowingHeadRotation()) {
					Vec3 idealEndPos = ESMathUtil.rotationToPosition(position(), getRadius(), getPitch(), getYaw());
					shooter.updateRayEnd(idealEndPos);
					Vec3 wantedPos = shooter.getRayRotationTarget();

					float wantedYaw = ESMathUtil.positionToYaw(position(), wantedPos);
					float wantedPitch = ESMathUtil.positionToPitch(position(), wantedPos);
					float currentYaw = getYaw();
					float currentPitch = getPitch();
					setYaw(rotateTowards(currentYaw, wantedYaw, getRotationSpeed()));
					setPitch(rotateTowards(currentPitch, wantedPitch, getRotationSpeed()));
				} else {
					this.setYaw(living.yHeadRot + 90);
					this.setPitch(-caster.getXRot());
				}
			}
		});
	}

	public void updatePosition() {
		getCaster().ifPresent(caster -> {
			setPos(getPositionForCaster(caster, caster.position()));
		});
	}

	public Vec3 getPositionForCaster(Entity caster, Vec3 casterPos) {
		return casterPos;
	}

	@Override
	public @Nullable Entity getOwner() {
		return getCaster().orElse(null);
	}
}
package cn.leolezury.eternalstarlight.common.entity.attack.ray;

import cn.leolezury.eternalstarlight.common.data.ESDamageTypes;
import cn.leolezury.eternalstarlight.common.entity.interfaces.RayAttackUser;
import cn.leolezury.eternalstarlight.common.particle.ESExplosionParticleOptions;
import cn.leolezury.eternalstarlight.common.platform.ESPlatform;
import cn.leolezury.eternalstarlight.common.util.ESEntityUtil;
import cn.leolezury.eternalstarlight.common.util.ESMathUtil;
import it.unimi.dsi.fastutil.objects.Object2IntArrayMap;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.PushReaction;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.CollisionContext;

import java.util.Optional;

public class RayAttack extends Entity {
	protected static final EntityDataAccessor<Integer> CASTER = SynchedEntityData.defineId(RayAttack.class, EntityDataSerializers.INT);

	public Optional<Entity> getCaster() {
		return Optional.ofNullable(level().getEntity(entityData.get(CASTER)));
	}

	public void setCaster(Entity caster) {
		entityData.set(CASTER, caster.getId());
	}

	protected static final EntityDataAccessor<Float> PITCH = SynchedEntityData.defineId(RayAttack.class, EntityDataSerializers.FLOAT);

	public float getPitch() {
		return entityData.get(PITCH);
	}

	public void setPitch(float pitch) {
		entityData.set(PITCH, pitch);
	}

	protected static final EntityDataAccessor<Float> YAW = SynchedEntityData.defineId(RayAttack.class, EntityDataSerializers.FLOAT);

	public float getYaw() {
		return entityData.get(YAW);
	}

	public void setYaw(float yaw) {
		entityData.set(YAW, yaw);
	}

	protected static final EntityDataAccessor<Float> LENGTH = SynchedEntityData.defineId(RayAttack.class, EntityDataSerializers.FLOAT);

	public float getLength() {
		return entityData.get(LENGTH);
	}

	public void setLength(float length) {
		entityData.set(LENGTH, length);
	}

	public float prevPitch, prevYaw;
	private final Object2IntArrayMap<BlockPos> destroyProgresses = new Object2IntArrayMap<>();

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
			onFirstHit(result);
			boolean hasBlock = result.blockHitResult() != null && result.blockHitResult().getType() != HitResult.Type.MISS;
			if (hasBlock) {
				endPos = result.blockHitResult().getLocation();
			}
			setLength((float) endPos.distanceTo(position()));
			result = ESEntityUtil.raytrace(level(), CollisionContext.of(this), position(), endPos);
			onHit(result);
		} else {
			prevYaw = getYaw();
			prevPitch = getPitch();
			xo = getX();
			yo = getY();
			zo = getZ();
			Vec3 endPos = ESMathUtil.rotationToPosition(position(), getLength(), getPitch(), getYaw());
			addEndParticles(endPos);
		}
	}

	public int getRadius() {
		return 20;
	}

	public void onFirstHit(ESEntityUtil.RaytraceResult result) {
		getCaster().ifPresent(caster -> {
			if (result.blockHitResult() != null) {
				BlockPos hitPos = result.blockHitResult().getBlockPos();
				destroyProgresses.put(hitPos, destroyProgresses.containsKey(hitPos) ? destroyProgresses.getInt(hitPos) + 1 : 1);
				if (destroyProgresses.getInt(hitPos) > 60) {
					boolean canDestroy = ESPlatform.INSTANCE.postMobGriefingEvent(this.level(), caster);
					if (canDestroy) {
						BlockState blockState = level().getBlockState(hitPos);
						if (blockState.getDestroySpeed(level(), hitPos) >= 0) {
							level().destroyBlock(hitPos, true, this);
						}
					}
				}
			}
		});
		while (destroyProgresses.size() > 32) {
			destroyProgresses.removeInt(destroyProgresses.keySet().stream().sorted().toList().getFirst());
		}
	}

	public void onHit(ESEntityUtil.RaytraceResult result) {
		for (Entity target : result.entities()) {
			if (target instanceof LivingEntity living) {
				doHurtTarget(living);
			}
		}
		BlockPos origin = BlockPos.containing(ESMathUtil.rotationToPosition(position(), getLength(), getPitch(), getYaw()));
		for (int x = -1; x <= 1; x++) {
			for (int y = -1; y <= 1; y++) {
				for (int z = -1; z <= 1; z++) {
					BlockPos firePos = origin.offset(x, y, z);
					if (random.nextBoolean() && level().isEmptyBlock(firePos) && level().getBlockState(firePos.below()).isFaceSturdy(level(), firePos.below(), Direction.UP)) {
						level().setBlockAndUpdate(firePos, Blocks.FIRE.defaultBlockState());
					}
				}
			}
		}
	}

	public void doHurtTarget(LivingEntity target) {
		getCaster().ifPresent(caster -> {
			if (target.hurt(ESDamageTypes.getIndirectEntityDamageSource(level(), ESDamageTypes.LASER, this, caster), getAttackDamage())) {
				target.setRemainingFireTicks(target.getRemainingFireTicks() + 60);
			}
		});
	}

	public void addEndParticles(Vec3 endPos) {
		for (int i = 0; i < 3; i++) {
			level().addParticle(ESExplosionParticleOptions.ENERGY, endPos.x + random.nextFloat() - 0.5f, endPos.y + random.nextFloat() - 0.5f, endPos.z + random.nextFloat() - 0.5f, 0, 0, 0);
		}
	}

	public float getRotationSpeed() {
		return 1.6f;
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
			setPos(caster.position());
		});
	}
}
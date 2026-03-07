package cn.leolezury.eternalstarlight.common.entity.projectile;

import cn.leolezury.eternalstarlight.common.data.ESDamageTypes;
import cn.leolezury.eternalstarlight.common.entity.interfaces.TrailOwner;
import cn.leolezury.eternalstarlight.common.registry.ESEntities;
import cn.leolezury.eternalstarlight.common.util.TrailEffect;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.ThrowableProjectile;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;
import org.joml.Vector4f;

import java.util.UUID;

public class EnergySpark extends ThrowableProjectile implements TrailOwner {
	private static final String TAG_TARGET = "target";
	private static final String TAG_SPAWNED_TICKS = "spawned_ticks";

	public float oSpin, spin;

	protected static final EntityDataAccessor<Integer> SPAWNED_TICKS = SynchedEntityData.defineId(EnergySpark.class, EntityDataSerializers.INT);

	public int getSpawnedTicks() {
		return this.getEntityData().get(SPAWNED_TICKS);
	}

	public void setSpawnedTicks(int spawnedTicks) {
		this.getEntityData().set(SPAWNED_TICKS, spawnedTicks);
	}

	public EnergySpark(EntityType<? extends EnergySpark> entityType, Level level) {
		super(entityType, level);
	}

	public EnergySpark(Level level, LivingEntity livingEntity) {
		super(ESEntities.ENERGY_SPARK.get(), livingEntity, level);
	}

	public EnergySpark(Level level, double x, double y, double z) {
		super(ESEntities.ENERGY_SPARK.get(), x, y, z, level);
	}

	@Override
	protected void defineSynchedData(SynchedEntityData.Builder builder) {
		builder.define(SPAWNED_TICKS, 0);
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

	@Override
	public void tick() {
		super.tick();
		if (!level().isClientSide) {
			if (target == null && targetId != null && level() instanceof ServerLevel serverLevel) {
				if (serverLevel.getEntity(targetId) instanceof LivingEntity livingEntity) {
					target = livingEntity;
				}
				if (target == null) {
					targetId = null;
				}
			}
			if (target != null && !target.isAlive()) {
				target = null;
				targetId = null;
			}
			Vec3 targetPos = getOwner() instanceof Player ?
				position().add(getRandom().nextInt(-10, 11), getRandom().nextInt(-10, 11), getRandom().nextInt(-10, 11))
				: position().add(getDeltaMovement().normalize());
			if (target != null && (getOwner() instanceof Player || tickCount < 80)) {
				targetPos = target.position().add(0, target.getBbHeight() / 2, 0);
			}
			if (getSpawnedTicks() <= 40 && tickCount % 2 == 0 && getOwner() instanceof Player) {
				Vec3 delta = new Vec3(getRandom().nextFloat() - 0.5, getRandom().nextFloat() - 0.5, getRandom().nextFloat() - 0.5);
				double length = this.getDeltaMovement().length();
				this.setDeltaMovement(this.getDeltaMovement().add(delta.normalize().scale(Math.max(length * 0.05, 0.005))).normalize().scale(length));
			}
			if (getSpawnedTicks() > 40) {
				Vec3 delta = targetPos.subtract(position());
				double lengthSqr = delta.lengthSqr();
				if (lengthSqr < 100) {
					double e = 1.0 - Math.sqrt(lengthSqr) / 10.0;
					this.setDeltaMovement(this.getDeltaMovement().add(delta.normalize().scale(e * e * 0.03)));
				} else {
					this.setDeltaMovement(this.getDeltaMovement().add(delta.normalize().scale(0.05)));
				}
				if (target != null && target.getBoundingBox().intersects(getBoundingBox())) {
					hurtTarget(target);
				}
			}
			if (getSpawnedTicks() > (getOwner() instanceof Player ? 600 : 120)) {
				discard();
			}
			setSpawnedTicks(getSpawnedTicks() + 1);
		}
		oSpin = spin;
		spin += Mth.PI * 0.06f * (getSpawnedTicks() / 100f);
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

	@Override
	protected void onHitEntity(EntityHitResult hitResult) {
		super.onHitEntity(hitResult);
		hurtTarget(hitResult.getEntity());
	}

	private void hurtTarget(Entity entity) {
		if ((getTarget() == null || entity == getTarget()) && entity != getOwner()) {
			entity.invulnerableTime = 0;
			entity.hurt(ESDamageTypes.getIndirectEntityDamageSource(level(), ESDamageTypes.ENERGIZED_FLAME, this, getOwner()), 3);
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
		return new TrailEffect(0.125f, 3);
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

package cn.leolezury.eternalstarlight.common.entity.projectile;

import cn.leolezury.eternalstarlight.common.EternalStarlight;
import cn.leolezury.eternalstarlight.common.entity.interfaces.TrailOwner;
import cn.leolezury.eternalstarlight.common.particle.ESSmokeParticleOptions;
import cn.leolezury.eternalstarlight.common.platform.ESPlatform;
import cn.leolezury.eternalstarlight.common.registry.ESEntities;
import cn.leolezury.eternalstarlight.common.util.ESMathUtil;
import cn.leolezury.eternalstarlight.common.util.TrailEffect;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.Fireball;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;
import org.joml.Vector4f;

import java.util.UUID;

public class GatekeeperFireball extends Fireball implements TrailOwner {
	private static final String TAG_TARGET = "target";
	private static final String TAG_SPAWNED_TICKS = "spawned_ticks";

	private static final ResourceLocation TRAIL_TEXTURE = EternalStarlight.id("textures/entity/trail.png");

	public GatekeeperFireball(EntityType<? extends Fireball> entityType, Level level) {
		super(entityType, level);
	}

	public GatekeeperFireball(Level level, LivingEntity livingEntity, Vec3 motion) {
		super(ESEntities.GATEKEEPER_FIREBALL.get(), livingEntity, motion, level);
	}

	protected static final EntityDataAccessor<Integer> SPAWNED_TICKS = SynchedEntityData.defineId(GatekeeperFireball.class, EntityDataSerializers.INT);

	public int getSpawnedTicks() {
		return entityData.get(SPAWNED_TICKS);
	}

	public void setSpawnedTicks(int spawnedTicks) {
		entityData.set(SPAWNED_TICKS, spawnedTicks);
	}

	@Override
	protected void defineSynchedData(SynchedEntityData.Builder builder) {
		super.defineSynchedData(builder);
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

	protected ParticleOptions getTrailParticle() {
		return ESSmokeParticleOptions.FLAME;
	}

	@Override
	public boolean isPickable() {
		return false;
	}

	public boolean isOnFire() {
		return false;
	}

	public boolean hurt(DamageSource damageSource, float amount) {
		return false;
	}

	protected boolean shouldBurn() {
		return false;
	}

	private boolean canReachTarget(double range) {
		LivingEntity target = getTarget();
		if (target == null) {
			return false;
		}
		for (LivingEntity livingEntity : level().getEntitiesOfClass(LivingEntity.class, getBoundingBox().inflate(range))) {
			if (livingEntity.getUUID().equals(target.getUUID())) {
				return true;
			}
		}
		return false;
	}

	protected void onHit(HitResult hitResult) {
		super.onHit(hitResult);
		if (!this.level().isClientSide && (target == null || canReachTarget(5))) {
			boolean bl = ESPlatform.INSTANCE.postMobGriefingEvent(level(), this);
			this.level().explode(this, this.getX(), this.getY(), this.getZ(), 2, bl, Level.ExplosionInteraction.MOB);
			this.discard();
		}
	}

	protected void onHitEntity(EntityHitResult entityHitResult) {
		super.onHitEntity(entityHitResult);
		if (level() instanceof ServerLevel serverLevel && (target == null || canReachTarget(5))) {
			Entity entity = entityHitResult.getEntity();
			Entity entity2 = this.getOwner();
			DamageSource damageSource = this.damageSources().fireball(this, entity2);
			entity.hurt(damageSource, 8.0F);
			EnchantmentHelper.doPostAttackEffects(serverLevel, entity, damageSource);
		}
	}

	@Override
	public void tick() {
		super.tick();
		if (!level().isClientSide) {
			if (target == null && targetId != null) {
				if (((ServerLevel) this.level()).getEntity(targetId) instanceof LivingEntity livingEntity) {
					target = livingEntity;
				}
				if (target == null) {
					targetId = null;
				}
			}
			setSpawnedTicks(getSpawnedTicks() + 1);
			if (getSpawnedTicks() == 60 && getTarget() != null) {
				Vec3 power = getTarget().position().subtract(position()).normalize().scale(0.4f);
				setDeltaMovement(power);
			}
		}
		if (getSpawnedTicks() < 60 && getOwner() != null) {
			Entity owner = getOwner();
			float yaw = ESMathUtil.positionToYaw(owner.position(), position());
			float pitch = ESMathUtil.positionToPitch(owner.position(), position());
			Vec3 newPos = ESMathUtil.rotationToPosition(owner.position(), distanceTo(owner), pitch, yaw + 5);
			setPos(newPos);
		}
	}

	public void readAdditionalSaveData(CompoundTag compoundTag) {
		super.readAdditionalSaveData(compoundTag);
		if (compoundTag.hasUUID(TAG_TARGET)) {
			targetId = compoundTag.getUUID(TAG_TARGET);
		}
		setSpawnedTicks(compoundTag.getInt(TAG_SPAWNED_TICKS));
	}

	public void addAdditionalSaveData(CompoundTag compoundTag) {
		super.addAdditionalSaveData(compoundTag);
		if (target != null) {
			compoundTag.putUUID(TAG_TARGET, target.getUUID());
		}
		compoundTag.putInt(TAG_SPAWNED_TICKS, getSpawnedTicks());
	}

	@Override
	public TrailEffect newTrail() {
		return new TrailEffect(0.5f, 18);
	}

	@Override
	public void updateTrail(TrailEffect effect) {
		Vec3 oldPos = new Vec3(xOld, yOld, zOld);
		effect.update(oldPos.add(0, getBbHeight() / 2, 0), position().subtract(oldPos));
	}

	@Override
	public Vector4f getTrailColor() {
		return new Vector4f(250 / 255f, 150 / 255f, 5 / 255f, 0.8f);
	}

	@Override
	public boolean isTrailFullBright() {
		return true;
	}

	@Environment(EnvType.CLIENT)
	@Override
	public RenderType getTrailRenderType() {
		return RenderType.entityTranslucent(TRAIL_TEXTURE);
	}
}

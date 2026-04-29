package cn.leolezury.eternalstarlight.common.entity.projectile;

import cn.leolezury.eternalstarlight.common.data.ESDamageTypes;
import cn.leolezury.eternalstarlight.common.entity.attack.EnergizedFlame;
import cn.leolezury.eternalstarlight.common.entity.attack.PermafrostCloud;
import cn.leolezury.eternalstarlight.common.entity.interfaces.TrailOwner;
import cn.leolezury.eternalstarlight.common.particle.ESExplosionParticleOptions;
import cn.leolezury.eternalstarlight.common.registry.ESEntities;
import cn.leolezury.eternalstarlight.common.registry.ESMobEffects;
import cn.leolezury.eternalstarlight.common.util.ESEntityUtil;
import cn.leolezury.eternalstarlight.common.util.ESMathUtil;
import cn.leolezury.eternalstarlight.common.util.TrailEffect;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.projectile.ThrowableProjectile;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import org.joml.Vector4f;

public class PermafrostSpit extends ThrowableProjectile implements TrailOwner {
	private static final String TAG_SMALL = "small";

	protected static final EntityDataAccessor<Boolean> SMALL = SynchedEntityData.defineId(PermafrostSpit.class, EntityDataSerializers.BOOLEAN);

	public boolean isSmall() {
		return this.getEntityData().get(SMALL);
	}

	public void setSmall(boolean small) {
		this.getEntityData().set(SMALL, small);
	}

	public PermafrostSpit(EntityType<? extends PermafrostSpit> entityType, Level level) {
		super(entityType, level);
	}

	public PermafrostSpit(Level level, LivingEntity livingEntity) {
		super(ESEntities.PERMAFROST_SPIT.get(), livingEntity, level);
	}

	public PermafrostSpit(Level level, double x, double y, double z) {
		super(ESEntities.PERMAFROST_SPIT.get(), x, y, z, level);
	}

	@Override
	protected void defineSynchedData(SynchedEntityData.Builder builder) {
		builder.define(SMALL, false);
	}

	@Override
	public boolean isOnFire() {
		return false;
	}

	@Override
	protected void onHit(HitResult hitResult) {
		super.onHit(hitResult);
		if (hitResult.getType() != HitResult.Type.MISS) {
			PermafrostCloud cloud = new PermafrostCloud(ESEntities.PERMAFROST_CLOUD.get(), level());
			if (getOwner() instanceof LivingEntity living) {
				cloud.setOwner(living);
			}
			cloud.setSmall(isSmall());
			cloud.setPos(hitResult.getLocation());
			level().addFreshEntity(cloud);
			if (!isSmall() && getOwner() instanceof LivingEntity living) {
				for (int i = 0; i < 5; i++) {
					Vec3 delta = ESMathUtil.rotationToPosition(1, 35, 360f * i / 5f + getRandom().nextInt(30));
					PermafrostSpit spit = new PermafrostSpit(level(), living);
					spit.shoot(delta.x, delta.y, delta.z, 0.4f, 0.1f);
					spit.setPos(position());
					spit.setSmall(true);
					level().addFreshEntity(spit);
				}
			}
			if (level() instanceof ServerLevel serverLevel) {
				serverLevel.sendParticles(ESExplosionParticleOptions.ENERGY, getX(), getY() + getBbHeight() / 2, getZ(), 20, getBbWidth() / 2, getBbHeight() / 2, getBbWidth() / 2, 0);
			}
			for (int x = -4; x <= 4; x++) {
				for (int y = -4; y <= 4; y++) {
					for (int z = -4; z <= 4; z++) {
						BlockPos pos = blockPosition().offset(x, y, z);
						if (level().getBlockState(pos).is(Blocks.FIRE) && blockPosition().distSqr(pos) <= 4 * 4) {
							level().setBlockAndUpdate(pos, Blocks.AIR.defaultBlockState());
						}
						if (level().getBlockState(pos).is(Blocks.WATER) && level().getFluidState(pos).isSource() && blockPosition().distSqr(pos) <= 4 * 4) {
							level().setBlockAndUpdate(pos, Blocks.ICE.defaultBlockState());
						}
						if (level().getBlockState(pos).is(Blocks.LAVA) && level().getFluidState(pos).isSource() && blockPosition().distSqr(pos) <= 2 * 2) {
							level().setBlockAndUpdate(pos, Blocks.OBSIDIAN.defaultBlockState());
						}
					}
				}
			}
			level().getEntitiesOfClass(EnergizedFlame.class, getBoundingBox().inflate(5)).forEach(Entity::discard);
			discard();
		}
	}

	@Override
	protected void onHitEntity(EntityHitResult hitResult) {
		if (hitResult.getType() != HitResult.Type.MISS && getOwner() instanceof LivingEntity owner && ESEntityUtil.shouldHarm(owner, hitResult.getEntity())) {
			hitResult.getEntity().hurt(ESDamageTypes.getIndirectEntityDamageSource(level(), ESDamageTypes.FREEZE, this, owner), (float) ((owner.getAttribute(Attributes.ATTACK_SPEED) != null ? owner.getAttributeValue(Attributes.ATTACK_SPEED) : 12) * 1.25));
			if (hitResult.getEntity() instanceof LivingEntity living && living.canFreeze()) {
				living.addEffect(new MobEffectInstance(ESMobEffects.BRITTLE.asHolder(), 200, 0));
			}
		}
	}

	@Override
	protected void readAdditionalSaveData(CompoundTag tag) {
		super.readAdditionalSaveData(tag);
		setSmall(tag.getBoolean(TAG_SMALL));
	}

	@Override
	protected void addAdditionalSaveData(CompoundTag tag) {
		super.addAdditionalSaveData(tag);
		tag.putBoolean(TAG_SMALL, isSmall());
	}

	@Override
	public TrailEffect createNewTrail() {
		return new TrailEffect(0.3f, 8);
	}

	@Override
	public void updateTrail(TrailEffect effect) {
		Vec3 oldPos = new Vec3(xOld, yOld, zOld);
		effect.update(oldPos.add(0, getBbHeight() / 2, 0));
		if (isRemoved()) {
			effect.setLength(Math.max(effect.getLength() - 0.9f, 0));
		}
	}

	@Override
	public Vector4f getTrailColor() {
		return new Vector4f(104 / 255f, 204 / 255f, 255 / 255f, 1f);
	}
}
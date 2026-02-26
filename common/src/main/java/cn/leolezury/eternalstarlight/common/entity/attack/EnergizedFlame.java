package cn.leolezury.eternalstarlight.common.entity.attack;

import cn.leolezury.eternalstarlight.common.data.ESDamageTypes;
import cn.leolezury.eternalstarlight.common.particle.GatheringTrailParticleOptions;
import cn.leolezury.eternalstarlight.common.particle.RingExplosionParticleOptions;
import cn.leolezury.eternalstarlight.common.util.ESEntityUtil;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.TraceableEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import org.jetbrains.annotations.Nullable;

import java.util.UUID;

public class EnergizedFlame extends Entity implements TraceableEntity {
	private static final String TAG_OWNER = "owner";
	private static final double HEIGHT = 6;

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

	public EnergizedFlame(EntityType<? extends EnergizedFlame> type, Level level) {
		super(type, level);
	}

	@Override
	protected void defineSynchedData(SynchedEntityData.Builder builder) {

	}

	@Override
	public boolean isOnFire() {
		return false;
	}

	@Override
	public void tick() {
		super.tick();
		if (tickCount > 60) {
			discard();
		}
		if (!level().isClientSide) {
			if (level() instanceof ServerLevel serverLevel && owner == null && ownerId != null) {
				if (serverLevel.getEntity(ownerId) instanceof LivingEntity livingEntity) {
					owner = livingEntity;
				}
				if (owner == null) {
					ownerId = null;
				}
			}
			if (tickCount == 20) {
				playSound(SoundEvents.FIRECHARGE_USE, 1, (getRandom().nextFloat() - getRandom().nextFloat()) * 0.2F + 1.0F);
			}
			if (tickCount > 20 && getOwner() != null) {
				AABB box = getBoundingBox().inflate(0.5, 0, 0.5);
				box = box.setMaxY(box.minY + HEIGHT);
				for (LivingEntity living : level().getEntitiesOfClass(LivingEntity.class, box)) {
					if (ESEntityUtil.shouldHarm(getOwner(), living)) {
						living.hurt(ESDamageTypes.getIndirectEntityDamageSource(level(), ESDamageTypes.ENERGIZED_FLAME, this, getOwner()), 2);
						living.igniteForSeconds(3);
					}
				}
			}
		} else {
			double dx = random.nextDouble() - 0.5;
			double dy = random.nextDouble() - 0.5;
			double dz = random.nextDouble() - 0.5;
			level().addParticle(GatheringTrailParticleOptions.ENERGY, getX() - dx, getY() - dy, getZ() - dz, dz * 0.25, HEIGHT, dx * 0.25);
			if (tickCount % 20 == 0 && tickCount >= 20) {
				level().addParticle(RingExplosionParticleOptions.ENERGY_SMALL, getX(), getY() + 0.12, getZ(), 0, 0, 0);
			}
		}
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
	protected void readAdditionalSaveData(CompoundTag compoundTag) {
		if (compoundTag.hasUUID(TAG_OWNER)) {
			ownerId = compoundTag.getUUID(TAG_OWNER);
		}
	}

	@Override
	protected void addAdditionalSaveData(CompoundTag compoundTag) {
		if (owner != null) {
			compoundTag.putUUID(TAG_OWNER, owner.getUUID());
		}
	}
}

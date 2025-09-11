package cn.leolezury.eternalstarlight.common.entity.attack;

import cn.leolezury.eternalstarlight.common.data.ESDamageTypes;
import cn.leolezury.eternalstarlight.common.util.ESEntityUtil;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import org.jetbrains.annotations.Nullable;

import java.util.UUID;

public class EnergizedFlame extends Entity {
	private static final String TAG_OWNER = "owner";

	@Nullable
	private LivingEntity owner;
	@Nullable
	private UUID ownerId;

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
	public void tick() {
		super.tick();
		if (tickCount > 100) {
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
				AABB box = getBoundingBox().inflate(0.5, 1, 0.5);
				for (LivingEntity livingEntity : level().getEntitiesOfClass(LivingEntity.class, box)) {
					if (ESEntityUtil.shouldHarm(getOwner(), livingEntity)) {
						livingEntity.hurt(ESDamageTypes.getIndirectEntityDamageSource(level(), ESDamageTypes.ENERGIZED_FLAME, this, getOwner()), 2);
						livingEntity.setRemainingFireTicks(Math.max(livingEntity.getRemainingFireTicks(), 60));
					}
				}
			}
		} else {
			level().addParticle(ParticleTypes.SMOKE, getX() + (random.nextDouble() - 0.5) * 1, getY() + 0.25 + (random.nextDouble() - 0.5) * 1, getZ() + (random.nextDouble() - 0.5) * 1, 0, 1, 0);
			level().addParticle(ParticleTypes.LARGE_SMOKE, getX() + (random.nextDouble() - 0.5) * 1, getY() + 0.25 + (random.nextDouble() - 0.5) * 1, getZ() + (random.nextDouble() - 0.5) * 1, 0, 0.2, 0);
		}
	}

	@Override
	public boolean hurt(DamageSource damageSource, float amount) {
		if (damageSource.is(DamageTypeTags.BYPASSES_INVULNERABILITY)) {
			discard();
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

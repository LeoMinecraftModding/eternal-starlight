package cn.leolezury.eternalstarlight.common.entity.attack;

import cn.leolezury.eternalstarlight.common.data.ESDamageTypes;
import cn.leolezury.eternalstarlight.common.particle.ESSmokeParticleOptions;
import cn.leolezury.eternalstarlight.common.util.ESEntityUtil;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.util.Mth;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.TraceableEntity;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

import java.util.UUID;

public class PoisonousCloud extends Entity implements TraceableEntity {
	private static final String TAG_OWNER = "owner";

	private int attackCooldown;

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

	public PoisonousCloud(EntityType<? extends PoisonousCloud> type, Level level) {
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
			if (attackCooldown > 0) {
				attackCooldown--;
			}
			if (getOwner() != null && attackCooldown <= 0) {
				boolean success = false;
				for (LivingEntity livingEntity : level().getEntitiesOfClass(LivingEntity.class, getBoundingBox())) {
					if (ESEntityUtil.shouldHarm(getOwner(), livingEntity)) {
						int oldInvulnerableTime = livingEntity.invulnerableTime;
						livingEntity.invulnerableTime = 0;
						success = livingEntity.hurt(ESDamageTypes.getIndirectEntityDamageSource(level(), ESDamageTypes.POISON, this, getOwner()), 5) || success;
						livingEntity.invulnerableTime = oldInvulnerableTime;
						livingEntity.addEffect(new MobEffectInstance(MobEffects.POISON, 80, 1));
					}
				}
				if (success) {
					attackCooldown = 10;
				}
			}
		} else {
			int count = Mth.ceil((Math.PI * (getBbWidth() / 2) * (getBbWidth() / 2)) / 8);
			float bbWidth = getBbWidth();

			for (int i = 0; i < count; i++) {
				float angle = this.random.nextFloat() * (float) (Math.PI * 2);
				float scale = Mth.sqrt(this.random.nextFloat()) * bbWidth;
				double x = this.getX() + Mth.cos(angle) * scale;
				double y = this.getY() + this.random.nextFloat() * getBbHeight();
				double z = this.getZ() + Mth.sin(angle) * scale;
				this.level().addAlwaysVisibleParticle(ESSmokeParticleOptions.LUNAR_ATTACK, x, y, z, 0.0, 0.0, 0.0);
			}
		}
		this.setOldPosAndRot();
		this.setPos(position().add(getDeltaMovement()));
		this.setDeltaMovement(getDeltaMovement().scale(0.95));
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
	}

	@Override
	protected void addAdditionalSaveData(CompoundTag tag) {
		if (owner != null) {
			tag.putUUID(TAG_OWNER, owner.getUUID());
		}
	}
}

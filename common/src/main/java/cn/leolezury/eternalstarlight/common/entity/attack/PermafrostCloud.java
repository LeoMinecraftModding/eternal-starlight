package cn.leolezury.eternalstarlight.common.entity.attack;

import cn.leolezury.eternalstarlight.common.data.ESDamageTypes;
import cn.leolezury.eternalstarlight.common.util.ESEntityUtil;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.util.Mth;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

import java.util.UUID;

public class PermafrostCloud extends Entity implements TraceableEntity {
	private static final String TAG_OWNER = "owner";
	private static final String TAG_SMALL = "small";

	protected static final EntityDataAccessor<Boolean> SMALL = SynchedEntityData.defineId(PermafrostCloud.class, EntityDataSerializers.BOOLEAN);

	public boolean isSmall() {
		return this.getEntityData().get(SMALL);
	}

	public void setSmall(boolean small) {
		this.getEntityData().set(SMALL, small);
		this.refreshDimensions();
	}

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

	public PermafrostCloud(EntityType<? extends PermafrostCloud> type, Level level) {
		super(type, level);
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
	public void tick() {
		super.tick();
		if (tickCount > 200) {
			discard();
		}
		if (tickCount % 3 == 0) {
			refreshDimensions();
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
						success = livingEntity.hurt(ESDamageTypes.getIndirectEntityDamageSource(level(), ESDamageTypes.FREEZE, this, getOwner()), 4) || success;
						livingEntity.invulnerableTime = oldInvulnerableTime;
						if (livingEntity.canFreeze()) {
							livingEntity.setTicksFrozen(Math.min(livingEntity.getTicksFrozen() + 8, 300));
						}
					}
				}
				if (success) {
					attackCooldown = 10;
				}
			}
		} else {
			int count = Mth.ceil(Math.PI * (getBbWidth() / 2) * (getBbWidth() / 2));
			float bbWidth = getBbWidth();

			for (int i = 0; i < count; i++) {
				float angle = this.random.nextFloat() * (float) (Math.PI * 2);
				float scale = Mth.sqrt(this.random.nextFloat()) * bbWidth;
				double x = this.getX() + Mth.cos(angle) * scale;
				double y = this.getY();
				double z = this.getZ() + Mth.sin(angle) * scale;
				this.level().addAlwaysVisibleParticle(ParticleTypes.SNOWFLAKE, x, y, z, 0.0, 0.0, 0.0);
			}
		}
	}

	@Override
	public EntityDimensions getDimensions(Pose pose) {
		return isSmall() ? super.getDimensions(pose).scale(0.5f, 1) : super.getDimensions(pose);
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
		setSmall(tag.getBoolean(TAG_SMALL));
	}

	@Override
	protected void addAdditionalSaveData(CompoundTag tag) {
		if (owner != null) {
			tag.putUUID(TAG_OWNER, owner.getUUID());
		}
		tag.putBoolean(TAG_SMALL, isSmall());
	}
}

package cn.leolezury.eternalstarlight.common.entity.attack;

import cn.leolezury.eternalstarlight.common.data.ESDamageTypes;
import cn.leolezury.eternalstarlight.common.particle.ESSmokeParticleOptions;
import cn.leolezury.eternalstarlight.common.util.ESEntityUtil;
import cn.leolezury.eternalstarlight.common.util.Easing;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.DamageTypeTags;
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

public class LunarThorn extends Entity implements TraceableEntity {
	private static final String TAG_ATTACK_MODE = "attack_mode";
	private static final String TAG_OWNER = "owner";
	private static final String TAG_SPAWNED_TICKS = "spawned_ticks";

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

	protected static final EntityDataAccessor<Integer> SPAWNED_TICKS = SynchedEntityData.defineId(LunarThorn.class, EntityDataSerializers.INT);

	public int getSpawnedTicks() {
		return this.getEntityData().get(SPAWNED_TICKS);
	}

	public void setSpawnedTicks(int spawnedTicks) {
		this.getEntityData().set(SPAWNED_TICKS, spawnedTicks);
	}

	private int attackMode;

	public int getAttackMode() {
		return attackMode;
	}

	public void setAttackMode(int attackMode) {
		this.attackMode = attackMode;
	}

	public float oldClientScale = 0f;
	public float clientScale = 0f;

	public LunarThorn(EntityType<? extends LunarThorn> type, Level level) {
		super(type, level);
	}

	@Override
	protected void defineSynchedData(SynchedEntityData.Builder builder) {
		builder.define(SPAWNED_TICKS, 0);
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
			if (getSpawnedTicks() >= 200) {
				discard();
			}
			if (getSpawnedTicks() > 40 && getOwner() != null) {
				for (LivingEntity livingEntity : level().getEntitiesOfClass(LivingEntity.class, getBoundingBox().inflate(0.5))) {
					if (ESEntityUtil.shouldHarm(getOwner(), livingEntity)) {
						if (getAttackMode() == 1 && !livingEntity.hasEffect(MobEffects.MOVEMENT_SLOWDOWN)) {
							livingEntity.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 10));
						}
						livingEntity.hurt(ESDamageTypes.getIndirectEntityDamageSource(level(), ESDamageTypes.POISON, this, getOwner()), getAttackMode() == 0 ? 4 : 3);
					}
				}
			}
			setSpawnedTicks(getSpawnedTicks() + 1);
		} else {
			float scale = getSpawnedTicks() <= 40 ? Easing.OUT_QUART.calculate(getSpawnedTicks() / 40f) : 1;
			if (getSpawnedTicks() >= 160) {
				scale = Easing.OUT_QUART.calculate((200 - getSpawnedTicks()) / 40f);
			}
			oldClientScale = clientScale;
			clientScale = scale;
			if (random.nextInt(20) == 0) {
				level().addParticle(ESSmokeParticleOptions.LUNAR_SHORT, getRandomX(1.5), getRandomY(), getRandomZ(1.5), 0, 0, 0);
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
		setSpawnedTicks(compoundTag.getInt(TAG_SPAWNED_TICKS));
		setAttackMode(compoundTag.getInt(TAG_ATTACK_MODE));
	}

	@Override
	protected void addAdditionalSaveData(CompoundTag compoundTag) {
		if (owner != null) {
			compoundTag.putUUID(TAG_OWNER, owner.getUUID());
		}
		compoundTag.putInt(TAG_SPAWNED_TICKS, getSpawnedTicks());
		compoundTag.putInt(TAG_ATTACK_MODE, getAttackMode());
	}
}

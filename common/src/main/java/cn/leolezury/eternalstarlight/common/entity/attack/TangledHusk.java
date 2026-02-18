package cn.leolezury.eternalstarlight.common.entity.attack;

import cn.leolezury.eternalstarlight.common.data.ESDamageTypes;
import cn.leolezury.eternalstarlight.common.entity.projectile.LunarSpore;
import cn.leolezury.eternalstarlight.common.network.ParticlePacket;
import cn.leolezury.eternalstarlight.common.particle.ESExplosionParticleOptions;
import cn.leolezury.eternalstarlight.common.particle.ESSmokeParticleOptions;
import cn.leolezury.eternalstarlight.common.particle.RingExplosionParticleOptions;
import cn.leolezury.eternalstarlight.common.platform.ESPlatform;
import cn.leolezury.eternalstarlight.common.util.ESEntityUtil;
import cn.leolezury.eternalstarlight.common.util.ESMathUtil;
import cn.leolezury.eternalstarlight.common.vfx.ScreenShakeVfx;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.util.Mth;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.targeting.TargetingConditions;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.UUID;

public class TangledHusk extends LivingEntity implements TraceableEntity {
	private static final String TAG_OWNER = "owner";
	private static final String TAG_SPAWNED_TICKS = "spawned_ticks";

	public Player cachedPlayerDisplay;

	public TangledHusk(EntityType<? extends TangledHusk> type, Level level) {
		super(type, level);
	}

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

	protected static final EntityDataAccessor<Integer> OWNER_ID = SynchedEntityData.defineId(TangledHusk.class, EntityDataSerializers.INT);

	public int getOwnerId() {
		return this.getEntityData().get(OWNER_ID);
	}

	public void setOwnerId(int ownerId) {
		this.getEntityData().set(OWNER_ID, ownerId);
	}

	protected static final EntityDataAccessor<Integer> SPAWNED_TICKS = SynchedEntityData.defineId(TangledHusk.class, EntityDataSerializers.INT);

	public int getSpawnedTicks() {
		return this.getEntityData().get(SPAWNED_TICKS);
	}

	public void setSpawnedTicks(int spawnedTicks) {
		this.getEntityData().set(SPAWNED_TICKS, spawnedTicks);
	}

	public static AttributeSupplier.Builder createAttributes() {
		return createLivingAttributes().add(Attributes.STEP_HEIGHT, 0);
	}

	@Override
	public void readAdditionalSaveData(CompoundTag compoundTag) {
		super.readAdditionalSaveData(compoundTag);
		if (compoundTag.hasUUID(TAG_OWNER)) {
			ownerId = compoundTag.getUUID(TAG_OWNER);
		}
		setSpawnedTicks(compoundTag.getInt(TAG_SPAWNED_TICKS));
	}

	@Override
	public void addAdditionalSaveData(CompoundTag compoundTag) {
		super.addAdditionalSaveData(compoundTag);
		if (owner != null) {
			compoundTag.putUUID(TAG_OWNER, owner.getUUID());
		}
		compoundTag.putInt(TAG_SPAWNED_TICKS, getSpawnedTicks());
	}

	@Override
	protected void defineSynchedData(SynchedEntityData.Builder builder) {
		super.defineSynchedData(builder);
		builder.define(OWNER_ID, -1)
			.define(SPAWNED_TICKS, 0);
	}

	@Override
	public boolean hurt(DamageSource source, float amount) {
		if (source.is(DamageTypeTags.BYPASSES_INVULNERABILITY)) {
			return super.hurt(source, amount);
		}
		return false;
	}

	@Override
	public boolean isOnFire() {
		return false;
	}

	@Override
	public void tick() {
		super.tick();
		if (!level().isClientSide && level() instanceof ServerLevel serverLevel) {
			if (owner == null && ownerId != null) {
				if (serverLevel.getEntity(ownerId) instanceof LivingEntity livingEntity) {
					owner = livingEntity;
				}
				if (owner == null) {
					ownerId = null;
				}
			}
			if (owner == null) {
				discard();
			} else {
				setOwnerId(owner.getId());
			}
			for (Mob mob : level().getEntitiesOfClass(Mob.class, getBoundingBox().inflate(10))) {
				if (mob.getTarget() == getOwner()) {
					mob.setTarget(this);
					mob.setLastHurtByMob(this);
					mob.getBrain().setMemory(MemoryModuleType.ATTACK_TARGET, this);
				}
			}
			setSpawnedTicks(getSpawnedTicks() + 1);
			if (getSpawnedTicks() > 100) {
				serverLevel.sendParticles(ESExplosionParticleOptions.LUNAR, getX(), getY() + getBbHeight() / 2, getZ(), 20, getBbWidth() / 2, getBbHeight() / 2, getBbWidth() / 2, 0);
				ESPlatform.INSTANCE.sendToAllClients(serverLevel, new ParticlePacket(RingExplosionParticleOptions.LUNAR, getX(), getY(), getZ(), 0, 0.2, 0));
				ScreenShakeVfx.createInstance(level().dimension(), position(), 40, 50, 0.3f, 0.3f, 3, 5.5f).send(serverLevel);
				playSound(SoundEvents.GENERIC_EXPLODE.value());
				for (LivingEntity living : level().getNearbyEntities(LivingEntity.class, TargetingConditions.DEFAULT, this, getBoundingBox().inflate(2))) {
					if (ESEntityUtil.shouldHarm(getOwner(), living) && living.hurt(ESDamageTypes.getEntityDamageSource(level(), ESDamageTypes.POISON, getOwner()), 10)) {
						living.hurtMarked = true;
						living.addDeltaMovement(living.position().subtract(position()).normalize().scale(1.25));
					}
				}
				for (int i = 0; i < 15; i++) {
					Vec3 shootPos = position().add(0, getBbHeight() / 2, 0);
					LunarSpore spore = new LunarSpore(level(), getOwner(), shootPos.x, shootPos.y, shootPos.z);
					spore.setDeltaMovement(ESMathUtil.rotationToPosition(0.9f, Mth.wrapDegrees(random.nextFloat() * 360), Mth.wrapDegrees(random.nextFloat() * 360)));
					level().addFreshEntity(spore);
				}
				discard();
			}
		} else {
			if (random.nextInt(5) == 0) {
				level().addParticle(ESSmokeParticleOptions.LUNAR_SHORT, getRandomX(1.5), getRandomY(), getRandomZ(1.5), 0, 0, 0);
			}
		}
	}

	@Override
	public Iterable<ItemStack> getArmorSlots() {
		return List.of();
	}

	@Override
	public ItemStack getItemBySlot(EquipmentSlot equipmentSlot) {
		return ItemStack.EMPTY;
	}

	@Override
	public void setItemSlot(EquipmentSlot equipmentSlot, ItemStack itemStack) {

	}

	@Override
	public HumanoidArm getMainArm() {
		return HumanoidArm.RIGHT;
	}
}

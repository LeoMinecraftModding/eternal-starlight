package cn.leolezury.eternalstarlight.common.entity.misc;

import cn.leolezury.eternalstarlight.common.registry.ESEntities;
import cn.leolezury.eternalstarlight.common.registry.ESItems;
import cn.leolezury.eternalstarlight.common.registry.ESSoundEvents;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ItemParticleOption;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.ItemSupplier;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

import java.util.Optional;
import java.util.UUID;

public class EyeOfSeeking extends Entity implements ItemSupplier {
	private static final String TAG_ITEM = "item";
	private static final String TAG_TARGET = "target";
	private static final String TAG_OWNER = "owner";

	private static final EntityDataAccessor<ItemStack> ITEM_STACK = SynchedEntityData.defineId(EyeOfSeeking.class, EntityDataSerializers.ITEM_STACK);
	private static final EntityDataAccessor<Optional<BlockPos>> TARGET = SynchedEntityData.defineId(EyeOfSeeking.class, EntityDataSerializers.OPTIONAL_BLOCK_POS);
	private static final EntityDataAccessor<Integer> OWNER_ID = SynchedEntityData.defineId(EyeOfSeeking.class, EntityDataSerializers.INT);

	private UUID ownerId;
	private Player cachedOwner;

	private int lerpSteps;
	private double lerpX, lerpY, lerpZ;
	private float lerpYRot, lerpXRot;

	public EyeOfSeeking(EntityType<? extends EyeOfSeeking> entityType, Level level) {
		super(entityType, level);
		this.noCulling = true;
	}

	public EyeOfSeeking(Level level, double x, double y, double z) {
		this(ESEntities.EYE_OF_SEEKING.get(), level);
		this.setPos(x, y, z);
	}

	public void setItem(ItemStack itemStack) {
		if (itemStack.isEmpty()) {
			this.getEntityData().set(ITEM_STACK, this.getDefaultItem());
		} else {
			this.getEntityData().set(ITEM_STACK, itemStack.copyWithCount(1));
		}
	}

	public void setOwner(UUID ownerId) {
		this.ownerId = ownerId;
		this.cachedOwner = null;
	}

	public int getOwnerId() {
		return this.getEntityData().get(OWNER_ID);
	}

	public void setOwnerId(int ownerId) {
		this.getEntityData().set(OWNER_ID, ownerId);
	}

	public void signalTo(BlockPos blockPos) {
		this.getEntityData().set(TARGET, Optional.of(blockPos.immutable()));
	}

	public Optional<BlockPos> getTarget() {
		return this.getEntityData().get(TARGET);
	}

	@Override
	public ItemStack getItem() {
		return this.getEntityData().get(ITEM_STACK);
	}

	@Override
	protected void defineSynchedData(SynchedEntityData.Builder builder) {
		builder.define(ITEM_STACK, this.getDefaultItem());
		builder.define(TARGET, Optional.empty());
		builder.define(OWNER_ID, -1);
	}

	@Override
	public void lerpTo(double x, double y, double z, float yRot, float xRot, int steps) {
		this.lerpSteps = steps;
		this.lerpX = x;
		this.lerpY = y;
		this.lerpZ = z;
		this.lerpYRot = yRot;
		this.lerpXRot = xRot;
	}

	@Override
	public double lerpTargetX() {
		return this.lerpX;
	}

	@Override
	public double lerpTargetY() {
		return this.lerpY;
	}

	@Override
	public double lerpTargetZ() {
		return this.lerpZ;
	}

	@Override
	public float lerpTargetYRot() {
		return this.lerpYRot;
	}

	@Override
	public float lerpTargetXRot() {
		return this.lerpXRot;
	}

	@Override
	public boolean shouldRenderAtSqrDistance(double d) {
		double e = this.getBoundingBox().getSize() * 4.0;
		if (Double.isNaN(e)) {
			e = 4.0;
		}
		e *= 64.0;
		return d < e * e;
	}

	public Player getOwner() {
		if (cachedOwner != null && (cachedOwner.isRemoved() || cachedOwner.level() != level() || !cachedOwner.getUUID().equals(ownerId))) {
			cachedOwner = null;
		}
		if (cachedOwner == null) {
			if (level().isClientSide) {
				Entity entity = level().getEntity(getOwnerId());
				if (entity instanceof Player player) {
					cachedOwner = player;
				}
			} else if (ownerId != null && level() instanceof ServerLevel serverLevel) {
				cachedOwner = serverLevel.getPlayerByUUID(ownerId);
			}
		}
		return cachedOwner;
	}

	public Vec3 getTargetPosition() {
		return getTarget().map(Vec3::atCenterOf).orElse(null);
	}

	@Override
	public void tick() {
		super.tick();
		if (level().isClientSide && this.lerpSteps > 0) {
			this.lerpPositionAndRotationStep(this.lerpSteps, this.lerpX, this.lerpY, this.lerpZ, this.lerpYRot, this.lerpXRot);
			this.lerpSteps--;
		}
		Vec3 targetPos = getTargetPosition();
		if (targetPos == null) {
			if (!level().isClientSide) {
				this.discard();
			}
			return;
		}
		if (!level().isClientSide) {
			Player owner = getOwner();
			if (owner == null || owner.level() != level()) {
				this.discard();
				return;
			}
			int ownerEntityId = owner.getId();
			if (getOwnerId() != ownerEntityId) {
				setOwnerId(ownerEntityId);
			}
			Vec3 ownerPos = owner.position().add(0, owner.getBbHeight() / 2, 0);
			setPos(getPositionForOwner(ownerPos, targetPos));
		}
	}

	public Vec3 getPositionForOwner(Vec3 ownerPos, Vec3 targetPos) {
		Vec3 toTarget = targetPos.subtract(ownerPos);
		Vec3 horizontal = new Vec3(toTarget.x, 0, toTarget.z);
		double length = Math.max(toTarget.length(), 0.1);
		double horizontalLength = Math.max(horizontal.length(), 0.1);
		return ownerPos.add(toTarget.scale(Math.min(1.5 / horizontalLength, 2 / length)));
	}

	@Override
	public InteractionResult interact(Player player, InteractionHand hand) {
		if (!level().isClientSide) {
			this.playSound(ESSoundEvents.SEEKING_EYE_DEATH.get(), 1.0F, 1.0F);
			if (this.random.nextFloat() < 0.2F) {
				if (level() instanceof ServerLevel serverLevel) {
					serverLevel.sendParticles(new ItemParticleOption(ParticleTypes.ITEM, this.getItem()), this.getX(), this.getY() + this.getBbHeight() / 2, this.getZ(), 10, 0.2, 0.2, 0.2, 0.1);
				}
			} else if (!player.hasInfiniteMaterials()) {
				ItemStack item = this.getItem();
				if (!player.getInventory().add(item)) {
					this.level().addFreshEntity(new ItemEntity(this.level(), this.getX(), this.getY(), this.getZ(), item));
				}
			}
			this.discard();
		}
		return InteractionResult.SUCCESS;
	}

	@Override
	public void addAdditionalSaveData(CompoundTag compoundTag) {
		compoundTag.put(TAG_ITEM, this.getItem().save(this.registryAccess()));
		getTarget().ifPresent(pos -> compoundTag.putLong(TAG_TARGET, pos.asLong()));
		if (ownerId != null) {
			compoundTag.putUUID(TAG_OWNER, ownerId);
		}
	}

	@Override
	public void readAdditionalSaveData(CompoundTag compoundTag) {
		if (compoundTag.contains(TAG_ITEM, CompoundTag.TAG_COMPOUND)) {
			this.setItem(ItemStack.parse(this.registryAccess(), compoundTag.getCompound(TAG_ITEM)).orElse(this.getDefaultItem()));
		} else {
			this.setItem(this.getDefaultItem());
		}
		if (compoundTag.contains(TAG_TARGET)) {
			this.signalTo(BlockPos.of(compoundTag.getLong(TAG_TARGET)));
		}
		if (compoundTag.hasUUID(TAG_OWNER)) {
			this.ownerId = compoundTag.getUUID(TAG_OWNER);
		}
	}

	private ItemStack getDefaultItem() {
		return new ItemStack(ESItems.SEEKING_EYE.get());
	}

	@Override
	public boolean isPickable() {
		return true;
	}

	@Override
	public float getPickRadius() {
		return 1.0F;
	}
}

package cn.leolezury.eternalstarlight.common.entity.attack;

import cn.leolezury.eternalstarlight.common.item.combat.WhipItem;
import cn.leolezury.eternalstarlight.common.platform.ESPlatform;
import cn.leolezury.eternalstarlight.common.registry.ESDataAttachments;
import cn.leolezury.eternalstarlight.common.registry.ESSoundEvents;
import cn.leolezury.eternalstarlight.common.util.ESEntityUtil;
import cn.leolezury.eternalstarlight.common.util.ESMathUtil;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.util.Mth;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.CollisionContext;
import org.jetbrains.annotations.Nullable;

import java.util.Comparator;
import java.util.List;
import java.util.UUID;

public abstract class Whip extends Entity {
	private static final String TAG_SPAWNED_TICKS = "spawned_ticks";
	private static final String TAG_OWNER = "owner";
	private static final String TAG_WEAPON = "weapon";
	private static final String TAG_DAMAGE_SCALE = "damage_scale";

	protected static final EntityDataAccessor<Integer> SPAWNED_TICKS = SynchedEntityData.defineId(Whip.class, EntityDataSerializers.INT);

	public int getSpawnedTicks() {
		return this.getEntityData().get(SPAWNED_TICKS);
	}

	public void setSpawnedTicks(int spawnedTicks) {
		this.getEntityData().set(SPAWNED_TICKS, spawnedTicks);
	}

	public static final EntityDataAccessor<Integer> OWNER_ID = SynchedEntityData.defineId(Whip.class, EntityDataSerializers.INT);

	private void setOwnerId(int ownerId) {
		this.getEntityData().set(OWNER_ID, ownerId);
	}

	public int getOwnerId() {
		return this.getEntityData().get(OWNER_ID);
	}

	private static final EntityDataAccessor<Boolean> FOIL = SynchedEntityData.defineId(Whip.class, EntityDataSerializers.BOOLEAN);

	private float damageScale = 1;

	@Nullable
	private ItemStack firedFromWeapon;

	@Nullable
	private Entity owner;
	@Nullable
	private UUID ownerId;

	public Entity getOwner() {
		return level().isClientSide ? level().getEntity(getOwnerId()) : owner;
	}

	public void setOwner(Entity owner) {
		if (owner != null) {
			this.ownerId = owner.getUUID();
		} else {
			this.ownerId = null;
		}
		this.owner = owner;
		this.updateOwnerInfo(this);
	}

	private int oldAnimationTicks, animationTicks;

	public float getAnimationTicks(float partialTicks) {
		return Mth.lerp(partialTicks, oldAnimationTicks, animationTicks);
	}

	public Whip(EntityType<? extends Whip> entityType, Level level) {
		super(entityType, level);
		this.noCulling = true;
	}

	public Whip(EntityType<? extends Whip> entityType, Level level, Player player, @Nullable ItemStack weapon, float damageScale) {
		this(entityType, level);
		this.setOwner(player);
		this.setPos(player.getEyePosition());
		this.firedFromWeapon = weapon;
		if (firedFromWeapon != null) {
			this.entityData.set(FOIL, firedFromWeapon.hasFoil());
		}
		this.damageScale = damageScale;
	}

	@Override
	protected void defineSynchedData(SynchedEntityData.Builder builder) {
		builder.define(SPAWNED_TICKS, 0)
			.define(OWNER_ID, -1)
			.define(FOIL, false);
	}

	@Override
	public boolean shouldRenderAtSqrDistance(double d) {
		return true;
	}

	@Override
	protected double getDefaultGravity() {
		return 0;
	}

	public boolean isFoil() {
		return this.entityData.get(FOIL);
	}

	@Override
	public void tick() {
		super.tick();
		if (!level().isClientSide) {
			if (owner == null && ownerId != null && level() instanceof ServerLevel serverLevel) {
				Entity entity = serverLevel.getEntity(ownerId);
				if (entity != null) {
					owner = entity;
				}
				if (owner == null) {
					ownerId = null;
				}
			}
			setOwnerId(owner != null ? owner.getId() : -1);
			if (getSpawnedTicks() >= getLifespan()) {
				discard();
			}
			setSpawnedTicks(getSpawnedTicks() + 1);
			Player player = getPlayerOwner();
			if (!(player != null && !player.isRemoved() && player.isAlive() && firedFromWeapon != null && ItemStack.isSameItemSameComponents(player.getMainHandItem(), firedFromWeapon))) {
				discard();
			}
			if (player != null) {
				setPos(player.getEyePosition());
				if (getSpawnedTicks() == getLifespan() / 2) {
					playSound(ESSoundEvents.WHIP_CRACK.get(), 1.0F, 1.0F / (this.getRandom().nextFloat() * 0.4F + 0.8F));
					Vec3 endPos = ESMathUtil.rotationToPosition(player.getEyePosition(), getWhipRange((float) player.getAttributeValue(Attributes.ENTITY_INTERACTION_RANGE)), -owner.getXRot(), owner.getYHeadRot() + 90);
					BlockHitResult hitResult = level().clip(new ClipContext(player.getEyePosition(), endPos, ClipContext.Block.COLLIDER, ClipContext.Fluid.NONE, CollisionContext.of(this)));
					if (hitResult.getType() != HitResult.Type.MISS) {
						endPos = hitResult.getLocation();
					}
					List<Entity> entities = level().getEntitiesOfClass(Entity.class, new AABB(player.getEyePosition(), endPos).inflate(1));
					entities.sort(Comparator.comparingDouble(e -> e.distanceToSqr(this)));
					for (Entity pickedEntity : entities) {
						LivingEntity entity = null;
						if (pickedEntity instanceof LivingEntity living) {
							entity = living;
						}
						if (ESPlatform.INSTANCE.getPartEntityParent(pickedEntity) instanceof LivingEntity living) {
							entity = living;
						}
						if (entity != null) {
							AABB aabb = entity.getBoundingBox().inflate(entity.getPickRadius() + 1.5f);
							if (ESEntityUtil.shouldHarm(player, entity) && entity.isPickable() && (aabb.contains(player.getEyePosition()) || aabb.clip(player.getEyePosition(), endPos).isPresent())) {
								DamageSource damageSource = damageSources().playerAttack(player);
								float damage = (float) player.getAttributeValue(Attributes.ATTACK_DAMAGE) * damageScale;
								float knockback = player.getKnockback(entity, damageSource);
								if (level() instanceof ServerLevel serverLevel && this.getWeaponItem() != null) {
									damage = EnchantmentHelper.modifyDamage(serverLevel, this.getWeaponItem(), entity, damageSource, damage);
									knockback = EnchantmentHelper.modifyKnockback(serverLevel, this.getWeaponItem(), player, damageSource, knockback);
								}
								if (entity.hurt(damageSource, damage)) {
									if (getWeaponItem() != null && getWeaponItem().getItem() instanceof WhipItem whipItem) {
										whipItem.doPostHurtEffects(this, entity);
									}
									if (level() instanceof ServerLevel serverLevel) {
										EnchantmentHelper.doPostAttackEffectsWithItemSource(serverLevel, entity, damageSource, this.getWeaponItem());
									}
									entity.knockback(knockback * 0.5F, Mth.sin(player.getYRot() * Mth.DEG_TO_RAD), -Mth.cos(player.getYRot() * Mth.DEG_TO_RAD));
								}
							}
						}
					}
				}
			}
		} else {
			int currentOwnerId = owner != null ? owner.getId() : -1;
			if (currentOwnerId != getOwnerId()) {
				setOwner(level().getEntity(getOwnerId()));
			}
			oldAnimationTicks = animationTicks;
			if (animationTicks == 0) {
				animationTicks = getSpawnedTicks();
			} else {
				animationTicks++;
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

	public abstract int getLifespan();

	public abstract float getWhipRange(float interactionRange);

	@Nullable
	@Override
	public ItemStack getWeaponItem() {
		return firedFromWeapon;
	}

	@Override
	public void addAdditionalSaveData(CompoundTag compoundTag) {
		compoundTag.putInt(TAG_SPAWNED_TICKS, getSpawnedTicks());
		if (ownerId != null) {
			compoundTag.putUUID(TAG_OWNER, ownerId);
		}
		if (this.firedFromWeapon != null && !this.firedFromWeapon.isEmpty()) {
			compoundTag.put(TAG_WEAPON, firedFromWeapon.save(registryAccess(), new CompoundTag()));
		}
		compoundTag.putFloat(TAG_DAMAGE_SCALE, damageScale);
	}

	@Override
	public void readAdditionalSaveData(CompoundTag compoundTag) {
		setSpawnedTicks(compoundTag.getInt(TAG_SPAWNED_TICKS));
		if (compoundTag.hasUUID(TAG_OWNER)) {
			ownerId = compoundTag.getUUID(TAG_OWNER);
		}
		if (compoundTag.contains(TAG_WEAPON, CompoundTag.TAG_COMPOUND)) {
			firedFromWeapon = ItemStack.parse(registryAccess(), compoundTag.getCompound(TAG_WEAPON)).orElse(null);
			if (firedFromWeapon != null) {
				this.entityData.set(FOIL, firedFromWeapon.hasFoil());
			}
		} else {
			firedFromWeapon = null;
		}
		if (compoundTag.contains(TAG_DAMAGE_SCALE, CompoundTag.TAG_ANY_NUMERIC)) {
			damageScale = compoundTag.getFloat(TAG_DAMAGE_SCALE);
		}
	}

	@Override
	public boolean isOnFire() {
		return false;
	}

	@Override
	protected MovementEmission getMovementEmission() {
		return MovementEmission.NONE;
	}

	@Override
	public void remove(RemovalReason removalReason) {
		this.updateOwnerInfo(null);
		super.remove(removalReason);
	}

	private void updateOwnerInfo(@Nullable Whip whip) {
		Player player = this.getPlayerOwner();
		if (player != null) {
			ESDataAttachments.WHIP.setData(player, whip == null ? -1 : whip.getId());
		}
	}

	@Nullable
	public Player getPlayerOwner() {
		Entity entity = this.getOwner();
		return entity instanceof Player ? (Player) entity : null;
	}

	@Override
	public boolean canChangeDimensions(Level level, Level level2) {
		return false;
	}
}

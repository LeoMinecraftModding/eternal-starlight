package cn.leolezury.eternalstarlight.common.entity.projectile;

import cn.leolezury.eternalstarlight.common.config.ESConfig;
import cn.leolezury.eternalstarlight.common.data.ESDamageTypes;
import cn.leolezury.eternalstarlight.common.entity.interfaces.Grappling;
import cn.leolezury.eternalstarlight.common.network.ParticlePacket;
import cn.leolezury.eternalstarlight.common.particle.GatheringTrailParticleOptions;
import cn.leolezury.eternalstarlight.common.platform.ESPlatform;
import cn.leolezury.eternalstarlight.common.registry.ESDataAttachments;
import cn.leolezury.eternalstarlight.common.registry.ESEntities;
import cn.leolezury.eternalstarlight.common.registry.ESSoundEvents;
import cn.leolezury.eternalstarlight.common.util.ESBlockUtil;
import cn.leolezury.eternalstarlight.common.util.ESEntityUtil;
import cn.leolezury.eternalstarlight.common.util.ESTags;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.game.ClientboundAddEntityPacket;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.decoration.ArmorStand;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.entity.projectile.ProjectileUtil;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.HitResult.Type;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;

import java.util.UUID;
import java.util.concurrent.atomic.AtomicBoolean;

public class ChainOfSouls extends Projectile implements Grappling {
	private static final String TAG_REACHED_TARGET = "reached_target";
	private static final String TAG_LENGTH = "length";
	private static final String TAG_TIP_DIRECTION = "tip_direction";
	private static final String TAG_TARGET = "target";
	private static final String TAG_WEAPON = "weapon";

	private static final double SPEED = 5.0;

	public static final EntityDataAccessor<Boolean> REACHED_TARGET = SynchedEntityData.defineId(ChainOfSouls.class, EntityDataSerializers.BOOLEAN);
	public static final EntityDataAccessor<Float> LENGTH = SynchedEntityData.defineId(ChainOfSouls.class, EntityDataSerializers.FLOAT);
	public static final EntityDataAccessor<Direction> TIP_DIRECTION = SynchedEntityData.defineId(ChainOfSouls.class, EntityDataSerializers.DIRECTION);
	public static final EntityDataAccessor<Integer> TARGET_ID = SynchedEntityData.defineId(ChainOfSouls.class, EntityDataSerializers.INT);

	@Nullable
	private ItemStack firedFromWeapon;
	private int absorbSoulTicks;

	@Nullable
	private Entity target;
	@Nullable
	private UUID targetId;

	public Entity getTarget() {
		return level().isClientSide ? level().getEntity(getTargetId()) : target;
	}

	public void setTarget(Entity target) {
		this.targetId = target.getUUID();
		this.target = target;
	}

	public ChainOfSouls(EntityType<? extends ChainOfSouls> entityType, Level level) {
		super(entityType, level);
		this.noCulling = true;
	}

	public ChainOfSouls(Level level, Player player, @Nullable ItemStack weapon) {
		this(ESEntities.CHAIN_OF_SOULS.get(), level);
		this.setOwner(player);
		this.setPos(player.getX(), player.getEyeY() - 0.1, player.getZ());
		this.setDeltaMovement(player.getViewVector(1.0F).scale(SPEED));
		this.firedFromWeapon = weapon;
	}

	@Override
	protected void defineSynchedData(SynchedEntityData.Builder builder) {
		builder.define(REACHED_TARGET, false)
			.define(LENGTH, 0.0F)
			.define(TIP_DIRECTION, Direction.DOWN)
			.define(TARGET_ID, -1);
	}

	@Override
	public boolean shouldRenderAtSqrDistance(double d) {
		return true;
	}

	@Override
	protected double getDefaultGravity() {
		return 0.05;
	}

	@Override
	public boolean isOnFire() {
		return false;
	}

	@Override
	public void tick() {
		super.tick();
		Player player = this.getPlayerOwner();
		if (!level().isClientSide) {
			if (target == null && targetId != null && level() instanceof ServerLevel serverLevel) {
				Entity entity = serverLevel.getEntity(targetId);
				if (entity != null) {
					target = entity;
				}
				if (target == null) {
					targetId = null;
				}
			}

			if (target != null) {
				setTargetId(target.getId());
				setDeltaMovement(Vec3.ZERO);
				if (!isValidTarget(target)) {
					target = null;
					targetId = null;
				} else {
					Vec3 targetPos = target.position().add(0, target.getBbHeight() / 2, 0);

					this.setPos(targetPos);

					if (target instanceof LivingEntity && !(target instanceof ArmorStand) && level() instanceof ServerLevel serverLevel) {
						Player playerOwner = getPlayerOwner();
						if (playerOwner != null) {
							float damage = (float) ESConfig.INSTANCE.itemsConfig.chainOfSouls.soulAbsorbDamage();
							DamageSource damageSource = ESDamageTypes.getIndirectEntityDamageSource(level(), ESDamageTypes.SOUL_ABSORB, this, playerOwner);
							if (getWeaponItem() != null) {
								damage = EnchantmentHelper.modifyDamage(serverLevel, getWeaponItem(), target, damageSource, damage);
							}
							if (target.hurt(damageSource, damage)) {
								EnchantmentHelper.doPostAttackEffects(serverLevel, target, damageSource);
								playerOwner.heal((float) (damage * ESConfig.INSTANCE.itemsConfig.chainOfSouls.healPercentage()));
								playSound(ESSoundEvents.CHAIN_OF_SOULS_ABSORB.get());
								for (int i = 0; i < 7; i++) {
									serverLevel.sendParticles(ParticleTypes.TRIAL_SPAWNER_DETECTED_PLAYER_OMINOUS, target.getRandomX(1), target.getRandomY(), target.getRandomZ(1), 5, 0, 0, 0, 0);
								}
								for (int i = 0; i < 2; i++) {
									Vec3 randomPlayerPos = new Vec3(playerOwner.getRandomX(1), playerOwner.getRandomY(), playerOwner.getRandomZ(1));
									Vec3 randomTargetPos = new Vec3(target.getRandomX(1), target.getRandomY(), target.getRandomZ(1));
									ESPlatform.INSTANCE.sendToTrackingClients(serverLevel, this, new ParticlePacket(GatheringTrailParticleOptions.SOUL_THIN, randomTargetPos.x, randomTargetPos.y, randomTargetPos.z, randomPlayerPos.x - randomTargetPos.x, randomPlayerPos.y - randomTargetPos.y, randomPlayerPos.z - randomTargetPos.z));
								}
							}

							if (!target.getType().is(ESTags.EntityTypes.CHAIN_OF_SOULS_CANNOT_PULL)) {
								Vec3 ownerPos = playerOwner.position().add(0, playerOwner.getBbHeight() / 2, 0);
								Vec3 posDiff = ownerPos.subtract(targetPos);
								if (posDiff.length() > length() * 1.2f) {
									double scale = 1.0 - Math.sqrt(Math.min(posDiff.lengthSqr(), 64 * 64)) / 64.0;
									target.addDeltaMovement(posDiff.normalize().scale(scale * scale));
									target.hurtMarked = true;
								}
							}

							absorbSoulTicks++;
							if (absorbSoulTicks > 50) {
								discard();
							}
						}
					}
				}
			} else {
				absorbSoulTicks = 0;
			}

			AtomicBoolean blockSupport = new AtomicBoolean(false);
			ESBlockUtil.getBlocksInBoundingBox(getBoundingBox().inflate(0.1, 0.05, 0.1).move(0, 0.05, 0)).forEach(pos ->
				blockSupport.set(blockSupport.get()
					|| level().getBlockState(pos).getCollisionShape(level(), pos).toAabbs().stream().anyMatch(box ->
					box.move(pos).intersects(getBoundingBox()))));
			if (reachedTarget() && target == null && !blockSupport.get()) {
				applyGravity();
			}
		}
		if (player != null && (this.level().isClientSide() || !this.shouldRetract(player))) {
			HitResult hitResult = ProjectileUtil.getHitResultOnMoveVector(this, this::canHitEntity);
			if (hitResult.getType() != Type.MISS && target == null) {
				this.onHit(hitResult);
			}
			if (target == null) {
				this.setPos(hitResult.getLocation());
			}
			this.checkInsideBlocks();
		} else {
			this.discard();
		}
	}

	private boolean shouldRetract(Player player) {
		if (!player.isRemoved() && player.isAlive() && (firedFromWeapon != null && (ItemStack.isSameItemSameComponents(player.getMainHandItem(), firedFromWeapon) || ItemStack.isSameItemSameComponents(player.getOffhandItem(), firedFromWeapon))) && !(this.distanceToSqr(player) > getMaxRange() * getMaxRange())) {
			return false;
		} else {
			this.discard();
			return true;
		}
	}

	private double getMaxRange() {
		return ESConfig.INSTANCE.itemsConfig.chainOfSouls.maxRange();
	}

	public boolean isValidTarget(Entity entity) {
		return entity instanceof LivingEntity && entity.isAlive() && entity.distanceToSqr(this) <= getMaxRange() * getMaxRange();
	}

	@Override
	protected boolean canHitEntity(Entity entity) {
		return entity != getOwner();
	}

	@Override
	protected void onHitEntity(EntityHitResult hitResult) {
		super.onHitEntity(hitResult);
		if (!level().isClientSide && target == null && isValidTarget(hitResult.getEntity()) && ESEntityUtil.shouldHarm(getPlayerOwner(), hitResult.getEntity())) {
			this.setTarget(hitResult.getEntity());
			Player player = this.getPlayerOwner();
			if (player != null && !reachedTarget()) {
				double d = player.getEyePosition().subtract(hitResult.getLocation()).length();
				this.setLength(Math.max((float) d * 0.5F - 1.0F, 1.5F));
			}
			this.setReachedTarget(true);
		}
	}

	@Override
	protected void onHitBlock(BlockHitResult hitResult) {
		super.onHitBlock(hitResult);
		this.setDeltaMovement(Vec3.ZERO);
		if (!level().isClientSide) {
			Player player = this.getPlayerOwner();
			if (player != null && !reachedTarget()) {
				double d = player.getEyePosition().subtract(hitResult.getLocation()).length();
				this.setLength(Math.max((float) d * 0.5F - 3.0F, 1.5F));
			}
			this.setTipDirection(hitResult.getDirection().getOpposite());
			this.setReachedTarget(true);
		}
	}

	@Nullable
	@Override
	public ItemStack getWeaponItem() {
		return firedFromWeapon;
	}

	@Override
	public void addAdditionalSaveData(CompoundTag compoundTag) {
		super.addAdditionalSaveData(compoundTag);
		compoundTag.putBoolean(TAG_REACHED_TARGET, this.reachedTarget());
		compoundTag.putFloat(TAG_LENGTH, this.length());
		compoundTag.putInt(TAG_TIP_DIRECTION, getTipDirection().get3DDataValue());
		if (target != null) {
			compoundTag.putUUID(TAG_TARGET, target.getUUID());
		}
		if (this.firedFromWeapon != null && !this.firedFromWeapon.isEmpty()) {
			compoundTag.put(TAG_WEAPON, firedFromWeapon.save(registryAccess(), new CompoundTag()));
		}
	}

	@Override
	public void readAdditionalSaveData(CompoundTag compoundTag) {
		super.readAdditionalSaveData(compoundTag);
		this.setReachedTarget(compoundTag.getBoolean(TAG_REACHED_TARGET));
		this.setLength(compoundTag.getFloat(TAG_LENGTH));
		this.setTipDirection(Direction.from3DDataValue(compoundTag.getInt(TAG_TIP_DIRECTION)));
		if (compoundTag.hasUUID(TAG_TARGET)) {
			targetId = compoundTag.getUUID(TAG_TARGET);
		}
		if (compoundTag.contains(TAG_WEAPON, CompoundTag.TAG_COMPOUND)) {
			firedFromWeapon = ItemStack.parse(registryAccess(), compoundTag.getCompound(TAG_WEAPON)).orElse(null);
		} else {
			firedFromWeapon = null;
		}
	}

	private void setReachedTarget(boolean reachedTarget) {
		this.getEntityData().set(REACHED_TARGET, reachedTarget);
	}

	private void setLength(float length) {
		this.getEntityData().set(LENGTH, length);
	}

	private void setTipDirection(Direction direction) {
		this.getEntityData().set(TIP_DIRECTION, direction);
	}

	private void setTargetId(int targetId) {
		this.getEntityData().set(TARGET_ID, targetId);
	}

	@Override
	public boolean reachedTarget() {
		return this.getEntityData().get(REACHED_TARGET);
	}

	@Override
	public boolean shouldPull() {
		return getTarget() == null;
	}

	@Override
	public float length() {
		return this.getEntityData().get(LENGTH);
	}

	public Direction getTipDirection() {
		return this.getEntityData().get(TIP_DIRECTION);
	}

	public int getTargetId() {
		return this.getEntityData().get(TARGET_ID);
	}

	@Override
	protected Entity.MovementEmission getMovementEmission() {
		return MovementEmission.NONE;
	}

	@Override
	public void remove(Entity.RemovalReason removalReason) {
		this.updateOwnerInfo(null);
		super.remove(removalReason);
	}

	@Override
	public void setOwner(@Nullable Entity entity) {
		super.setOwner(entity);
		this.updateOwnerInfo(this);
	}

	private void updateOwnerInfo(@Nullable ChainOfSouls chain) {
		Player player = this.getPlayerOwner();
		if (player != null) {
			ESDataAttachments.GRAPPLING.setData(player, chain == null ? -1 : chain.getId());
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

	@Override
	public void recreateFromPacket(ClientboundAddEntityPacket packet) {
		super.recreateFromPacket(packet);
		if (this.getPlayerOwner() == null) {
			this.kill();
		}
	}
}

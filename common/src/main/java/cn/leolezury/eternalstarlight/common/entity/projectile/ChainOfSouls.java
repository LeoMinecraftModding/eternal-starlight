package cn.leolezury.eternalstarlight.common.entity.projectile;

import cn.leolezury.eternalstarlight.common.data.ESDamageTypes;
import cn.leolezury.eternalstarlight.common.entity.interfaces.Grappling;
import cn.leolezury.eternalstarlight.common.entity.interfaces.GrapplingOwner;
import cn.leolezury.eternalstarlight.common.registry.ESEntities;
import cn.leolezury.eternalstarlight.common.registry.ESSoundEvents;
import cn.leolezury.eternalstarlight.common.util.ESBlockUtil;
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
	private static final float MAX_RANGE = 100.0F;
	private static final double SPEED = 5.0;

	public static final EntityDataAccessor<Boolean> REACHED_TARGET;
	public static final EntityDataAccessor<Float> LENGTH;

	@Nullable
	private ItemStack firedFromWeapon;
	private int absorbSoulTicks;

	@Nullable
	private Entity target;
	@Nullable
	private UUID targetId;

	public Entity getTarget() {
		return target;
	}

	public void setTarget(Entity target) {
		this.targetId = target.getUUID();
		this.target = target;
	}

	public ChainOfSouls(EntityType<? extends Projectile> entityType, Level level) {
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

	protected void defineSynchedData(SynchedEntityData.Builder builder) {
		builder.define(REACHED_TARGET, false);
		builder.define(LENGTH, 0.0F);
	}

	public boolean shouldRenderAtSqrDistance(double d) {
		return true;
	}

	@Override
	protected double getDefaultGravity() {
		return 0.05;
	}

	public void tick() {
		super.tick();
		Player player = this.getPlayerOwner();
		if (!level().isClientSide) {
			if (target == null && targetId != null) {
				Entity entity = ((ServerLevel) this.level()).getEntity(targetId);
				if (entity != null) {
					target = entity;
				}
				if (target == null) {
					targetId = null;
				}
			}

			if (target != null) {
				setDeltaMovement(Vec3.ZERO);
				if (!target.isAlive() || target.isRemoved() || target.distanceToSqr(this) > MAX_RANGE * MAX_RANGE) {
					target = null;
					targetId = null;
				} else {
					this.setPos(target.position().add(0, target.getBbHeight() / 2, 0));

					if (target instanceof LivingEntity && !(target instanceof ArmorStand) && level() instanceof ServerLevel serverLevel) {
						Player playerOwner = getPlayerOwner();
						if (playerOwner != null) {
							float damage = 3;
							DamageSource damageSource = ESDamageTypes.getIndirectEntityDamageSource(level(), ESDamageTypes.SOUL_ABSORB, this, playerOwner);
							if (getWeaponItem() != null) {
								damage = EnchantmentHelper.modifyDamage(serverLevel, getWeaponItem(), target, damageSource, damage);
							}
							if (target.hurt(damageSource, damage)) {
								playerOwner.heal(damage / 2);
								playSound(ESSoundEvents.CHAIN_OF_SOULS_ABSORB.get());
								for (int i = 0; i < 7; i++) {
									serverLevel.sendParticles(ParticleTypes.TRIAL_SPAWNER_DETECTED_PLAYER_OMINOUS, target.getRandomX(1), target.getRandomY(), target.getRandomZ(1), 5, 0, 0, 0, 0);
								}
								for (int i = 0; i < 7; i++) {
									serverLevel.sendParticles(ParticleTypes.TRIAL_SPAWNER_DETECTED_PLAYER_OMINOUS, playerOwner.getRandomX(1), playerOwner.getRandomY(), playerOwner.getRandomZ(1), 5, 0, 0, 0, 0);
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
		if (!player.isRemoved() && player.isAlive() && (player.getMainHandItem() == firedFromWeapon || player.getOffhandItem() == firedFromWeapon) && !(this.distanceToSqr(player) > MAX_RANGE * MAX_RANGE)) {
			return false;
		} else {
			this.discard();
			return true;
		}
	}

	@Override
	protected boolean canHitEntity(Entity entity) {
		return entity != getOwner();
	}

	@Override
	protected void onHitEntity(EntityHitResult hitResult) {
		super.onHitEntity(hitResult);
		if (target == null && !level().isClientSide) {
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
			this.setReachedTarget(true);
		}
	}

	@Nullable
	@Override
	public ItemStack getWeaponItem() {
		return firedFromWeapon;
	}

	public void addAdditionalSaveData(CompoundTag compoundTag) {
		compoundTag.putBoolean("ReachedTarget", this.reachedTarget());
		compoundTag.putFloat("Length", this.length());
		if (target != null) {
			compoundTag.putUUID("Target", target.getUUID());
		}
		if (this.firedFromWeapon != null) {
			compoundTag.put("Weapon", firedFromWeapon.save(registryAccess(), new CompoundTag()));
		}
	}

	public void readAdditionalSaveData(CompoundTag compoundTag) {
		this.setReachedTarget(compoundTag.getBoolean("ReachedTarget"));
		this.setLength(compoundTag.getFloat("Length"));
		if (compoundTag.hasUUID("Target")) {
			targetId = compoundTag.getUUID("Target");
		}
		if (compoundTag.contains("Weapon", CompoundTag.TAG_COMPOUND)) {
			firedFromWeapon = ItemStack.parse(registryAccess(), compoundTag.getCompound("Weapon")).orElse(null);
		} else {
			firedFromWeapon = null;
		}
	}

	private void setReachedTarget(boolean reachedTarget) {
		this.getEntityData().set(REACHED_TARGET, reachedTarget);
	}

	private void setLength(float f) {
		this.getEntityData().set(LENGTH, f);
	}

	@Override
	public boolean reachedTarget() {
		return this.getEntityData().get(REACHED_TARGET);
	}

	@Override
	public float length() {
		return this.getEntityData().get(LENGTH);
	}

	protected Entity.MovementEmission getMovementEmission() {
		return MovementEmission.NONE;
	}

	public void remove(Entity.RemovalReason removalReason) {
		this.updateOwnerInfo(null);
		super.remove(removalReason);
	}

	public void onClientRemoval() {
		this.updateOwnerInfo(null);
	}

	public void setOwner(@Nullable Entity entity) {
		super.setOwner(entity);
		this.updateOwnerInfo(this);
	}

	private void updateOwnerInfo(@Nullable ChainOfSouls chain) {
		Player player = this.getPlayerOwner();
		if (player instanceof GrapplingOwner owner) {
			owner.setGrappling(chain);
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

	public void recreateFromPacket(ClientboundAddEntityPacket packet) {
		super.recreateFromPacket(packet);
		if (this.getPlayerOwner() == null) {
			this.kill();
		}
	}

	static {
		REACHED_TARGET = SynchedEntityData.defineId(ChainOfSouls.class, EntityDataSerializers.BOOLEAN);
		LENGTH = SynchedEntityData.defineId(ChainOfSouls.class, EntityDataSerializers.FLOAT);
	}
}

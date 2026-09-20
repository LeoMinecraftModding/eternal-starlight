package cn.leolezury.eternalstarlight.common.entity.projectile;

import cn.leolezury.eternalstarlight.common.registry.ESEntities;
import cn.leolezury.eternalstarlight.common.registry.ESItems;
import cn.leolezury.eternalstarlight.common.registry.ESParticles;
import net.minecraft.core.particles.ColorParticleOption;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.util.FastColor;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;

public class SpiralArrow extends AbstractArrow {
	@Nullable
	private LivingEntity attachedTarget;
	private int attachedTicks;

	protected static final EntityDataAccessor<Boolean> ATTACHED = SynchedEntityData.defineId(SpiralArrow.class, EntityDataSerializers.BOOLEAN);

	public SpiralArrow(EntityType<? extends SpiralArrow> entityType, Level level) {
		super(entityType, level);
	}

	public SpiralArrow(Level level, LivingEntity livingEntity, ItemStack itemStack, @Nullable ItemStack itemStack2) {
		super(ESEntities.SPIRAL_ARROW.get(), livingEntity, level, itemStack, itemStack2);
	}

	public SpiralArrow(Level level, double d, double e, double f, ItemStack itemStack, @Nullable ItemStack itemStack2) {
		super(ESEntities.SPIRAL_ARROW.get(), d, e, f, level, itemStack, itemStack2);
	}

	@Override
	protected float getWaterInertia() {
		return 0.99f;
	}

	@Override
	protected void applyGravity() {
		if (isInWater()) {
			double d = this.getGravity();
			if (d != 0) {
				this.setDeltaMovement(this.getDeltaMovement().add(0, -d * 0.5, 0));
			}
		} else {
			super.applyGravity();
		}
	}

	@Override
	protected void onHitEntity(EntityHitResult result) {
		if (this.level().isClientSide) return;
		if (this.attachedTarget != null) return;
		if (!(result.getEntity() instanceof LivingEntity living)) return;
		if (!living.isAlive()) return;

		this.attachedTarget = living;
		this.attachedTicks = 20;
		this.pickup = Pickup.DISALLOWED;
		this.setDeltaMovement(Vec3.ZERO);
		this.setNoGravity(true);
		this.setAttached(true);
	}

	@Override
	protected boolean canHitEntity(Entity target) {
		return this.attachedTarget == null && super.canHitEntity(target);
	}

	@Override
	public void tick() {
		super.tick();
		if (!this.level().isClientSide && this.attachedTarget != null) {
			if (this.attachedTarget.isRemoved() || !this.attachedTarget.isAlive()) {
				this.attachedTarget = null;
				this.discard();
			} else {
				this.setPos(this.attachedTarget.getX(), this.attachedTarget.getY() + this.attachedTarget.getBbHeight() * 0.5, this.attachedTarget.getZ());
				if (this.attachedTicks % 2 == 0) {
					Entity owner = this.getOwner();
					this.attachedTarget.invulnerableTime = 0;
					this.attachedTarget.hurt(this.damageSources().arrow(this, owner != null ? owner : this), 1.0F);
				}
				this.attachedTicks--;
				if (this.attachedTicks <= 0) {
					this.attachedTarget = null;
					this.discard();
				}
			}
		}
		if (this.level().isClientSide && !this.inGround && isInWater()) {
			Vec3 pos = getBoundingBox().getBottomCenter().offsetRandom(getRandom(), getBbWidth());
			Vec3 speed = getDeltaMovement().normalize().offsetRandom(getRandom(), 0.3f).scale(-0.2);
			level().addParticle(ColorParticleOption.create(ESParticles.COLORED_INK.get(), FastColor.ARGB32.color(255, 51, 61, 58)), pos.x, pos.y, pos.z, speed.x, speed.y, speed.z);
		}
	}

	@Override
	protected ItemStack getDefaultPickupItem() {
		return ESItems.SPIRAL_ARROW.get().getDefaultInstance();
	}

	@Override
	protected void defineSynchedData(SynchedEntityData.Builder builder) {
		super.defineSynchedData(builder);
		builder.define(ATTACHED, false);
	}

	public boolean isAttached() {
		return this.entityData.get(ATTACHED);
	}

	public void setAttached(boolean attached) {
		this.entityData.set(ATTACHED, attached);
	}
}

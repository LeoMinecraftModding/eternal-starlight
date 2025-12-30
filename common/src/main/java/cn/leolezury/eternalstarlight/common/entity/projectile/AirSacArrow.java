package cn.leolezury.eternalstarlight.common.entity.projectile;

import cn.leolezury.eternalstarlight.common.registry.ESEntities;
import cn.leolezury.eternalstarlight.common.registry.ESItems;
import cn.leolezury.eternalstarlight.common.registry.ESParticles;
import net.minecraft.core.particles.ColorParticleOption;
import net.minecraft.util.FastColor;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;

public class AirSacArrow extends AbstractArrow {
	public AirSacArrow(EntityType<? extends AirSacArrow> entityType, Level level) {
		super(entityType, level);
	}

	public AirSacArrow(Level level, LivingEntity livingEntity, ItemStack itemStack, @Nullable ItemStack itemStack2) {
		super(ESEntities.AIR_SAC_ARROW.get(), livingEntity, level, itemStack, itemStack2);
	}

	public AirSacArrow(Level level, double d, double e, double f, ItemStack itemStack, @Nullable ItemStack itemStack2) {
		super(ESEntities.AIR_SAC_ARROW.get(), d, e, f, level, itemStack, itemStack2);
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
	public void tick() {
		super.tick();
		if (this.level().isClientSide && !this.inGround && isInWater()) {
			Vec3 pos = getBoundingBox().getBottomCenter().offsetRandom(getRandom(), getBbWidth());
			Vec3 speed = getDeltaMovement().normalize().offsetRandom(getRandom(), 0.3f).scale(-0.2);
			level().addParticle(ColorParticleOption.create(ESParticles.COLORED_INK.get(), FastColor.ARGB32.color(255, 51, 61, 58)), pos.x, pos.y, pos.z, speed.x, speed.y, speed.z);
		}
	}

	@Override
	protected ItemStack getDefaultPickupItem() {
		return ESItems.AIR_SAC_ARROW.get().getDefaultInstance();
	}
}

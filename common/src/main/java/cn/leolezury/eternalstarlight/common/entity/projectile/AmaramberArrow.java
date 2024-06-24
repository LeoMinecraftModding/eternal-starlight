package cn.leolezury.eternalstarlight.common.entity.projectile;

import cn.leolezury.eternalstarlight.common.registry.ESEntities;
import cn.leolezury.eternalstarlight.common.registry.ESItems;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

public class AmaramberArrow extends AbstractArrow {
	public AmaramberArrow(EntityType<? extends AmaramberArrow> entityType, Level level) {
		super(entityType, level);
	}

	public AmaramberArrow(Level level, LivingEntity livingEntity, ItemStack itemStack, @Nullable ItemStack itemStack2) {
		super(ESEntities.AMARAMBER_ARROW.get(), livingEntity, level, itemStack, itemStack2);
	}

	public AmaramberArrow(Level level, double d, double e, double f, ItemStack itemStack, @Nullable ItemStack itemStack2) {
		super(ESEntities.AMARAMBER_ARROW.get(), d, e, f, level, itemStack, itemStack2);
	}

	public void tick() {
		super.tick();
		if (this.level().isClientSide && !this.inGround) {
			this.level().addParticle(ParticleTypes.CRIMSON_SPORE, this.getX(), this.getY(), this.getZ(), 0.0, 0.0, 0.0);
		}
	}

	@Override
	protected ItemStack getDefaultPickupItem() {
		return ESItems.AMARAMBER_ARROW.get().getDefaultInstance();
	}
}

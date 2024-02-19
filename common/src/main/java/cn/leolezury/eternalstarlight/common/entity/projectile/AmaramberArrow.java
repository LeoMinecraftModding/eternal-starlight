package cn.leolezury.eternalstarlight.common.entity.projectile;

import cn.leolezury.eternalstarlight.common.init.ESEntities;
import cn.leolezury.eternalstarlight.common.init.ESItems;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class AmaramberArrow extends AbstractArrow {
    public AmaramberArrow(EntityType<? extends AmaramberArrow> entityType, Level level) {
        super(entityType, level, new ItemStack(ESItems.AMARAMBER_ARROW.get()));
    }

    public AmaramberArrow(Level level, LivingEntity livingEntity, ItemStack itemStack) {
        super(ESEntities.AMARAMBER_ARROW.get(), livingEntity, level, itemStack);
    }

    public AmaramberArrow(Level level, double d, double e, double f, ItemStack itemStack) {
        super(ESEntities.AMARAMBER_ARROW.get(), d, e, f, level, itemStack);
    }

    public void tick() {
        super.tick();
        if (this.level().isClientSide && !this.inGround) {
            this.level().addParticle(ParticleTypes.CRIMSON_SPORE, this.getX(), this.getY(), this.getZ(), 0.0, 0.0, 0.0);
        }
    }
}

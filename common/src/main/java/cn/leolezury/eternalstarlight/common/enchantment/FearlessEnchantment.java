package cn.leolezury.eternalstarlight.common.enchantment;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.enchantment.Enchantment;

public class FearlessEnchantment extends Enchantment {
    public FearlessEnchantment(Enchantment.EnchantmentDefinition definition) {
        super(definition);
    }

    public boolean isTreasureOnly() {
        return true;
    }

    @Override
    public void doPostAttack(LivingEntity livingEntity, Entity entity, int level) {
        if (level > 0) {
            double x = entity.getX() - livingEntity.getX();
            double y = entity.getY() - livingEntity.getY();
            double z = entity.getZ() - livingEntity.getZ();
            double d = x * x + y * y + z * z;
            livingEntity.hurtMarked = true;
            livingEntity.setDeltaMovement(livingEntity.getDeltaMovement().add((x / d) * level / 2, (y / d) * level / 2, (z / d) * level / 2));
            entity.hurtMarked = true;
            entity.setDeltaMovement(entity.getDeltaMovement().add((x / d) * level / 3, (y / d) * level / 3, (z / d) * level / 3));
        }
        super.doPostAttack(livingEntity, entity, level);
    }
}

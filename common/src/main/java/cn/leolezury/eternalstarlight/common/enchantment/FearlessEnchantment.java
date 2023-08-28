package cn.leolezury.eternalstarlight.common.enchantment;

import cn.leolezury.eternalstarlight.common.platform.ESPlatform;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentCategory;

public class FearlessEnchantment extends Enchantment {
    private static final EnchantmentCategory ES_WEAPON = ESPlatform.INSTANCE.getESWeaponEnchantmentCategory();
    public FearlessEnchantment(Enchantment.Rarity rarity, EquipmentSlot... slots) {
        super(rarity, ES_WEAPON, slots);
    }

    public int getMinCost(int p_45102_) {
        return p_45102_ * 5;
    }

    public int getMaxCost(int p_45105_) {
        return this.getMinCost(p_45105_) + 25;
    }

    public boolean isTreasureOnly() {
        return true;
    }

    public int getMaxLevel() {
        return 2;
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

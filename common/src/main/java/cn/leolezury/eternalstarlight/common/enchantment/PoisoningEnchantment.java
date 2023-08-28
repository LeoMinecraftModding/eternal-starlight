package cn.leolezury.eternalstarlight.common.enchantment;

import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentCategory;

public class PoisoningEnchantment extends Enchantment {
    public PoisoningEnchantment(Enchantment.Rarity rarity, EquipmentSlot... slots) {
        super(rarity, EnchantmentCategory.ARMOR, slots);
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
        return 5;
    }
}

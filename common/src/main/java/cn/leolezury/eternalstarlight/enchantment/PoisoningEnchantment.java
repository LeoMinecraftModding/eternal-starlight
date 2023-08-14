package cn.leolezury.eternalstarlight.enchantment;

import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentCategory;

public class PoisoningEnchantment extends Enchantment {
    public PoisoningEnchantment(Enchantment.Rarity p_45098_, EquipmentSlot... p_45099_) {
        super(p_45098_, EnchantmentCategory.ARMOR, p_45099_);
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

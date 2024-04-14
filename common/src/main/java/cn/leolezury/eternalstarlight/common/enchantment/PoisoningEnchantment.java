package cn.leolezury.eternalstarlight.common.enchantment;

import net.minecraft.world.item.enchantment.Enchantment;

public class PoisoningEnchantment extends Enchantment {
    public PoisoningEnchantment(Enchantment.EnchantmentDefinition definition) {
        super(definition);
    }

    public boolean isTreasureOnly() {
        return true;
    }
}

package cn.leolezury.eternalstarlight.asm;

import cn.leolezury.eternalstarlight.item.weapon.HammerItem;
import cn.leolezury.eternalstarlight.item.weapon.ScytheItem;
import cn.leolezury.eternalstarlight.mixins.quilt.EnchantmentCategoryMixin;
import net.minecraft.world.item.AxeItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.SwordItem;

public class ESWeaponEnchantmentCategory extends EnchantmentCategoryMixin {
    @Override
    public boolean canEnchant(Item item) {
        return item instanceof SwordItem || item instanceof AxeItem || item instanceof ScytheItem || item instanceof HammerItem;
    }
}

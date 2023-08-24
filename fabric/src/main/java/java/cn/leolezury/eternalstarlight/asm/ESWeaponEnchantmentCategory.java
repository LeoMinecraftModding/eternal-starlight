package java.cn.leolezury.eternalstarlight.asm;

import cn.leolezury.eternalstarlight.item.weapon.HammerItem;
import cn.leolezury.eternalstarlight.item.weapon.ScytheItem;
import net.minecraft.world.item.AxeItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.SwordItem;

import java.cn.leolezury.eternalstarlight.mixins.fabric.EnchantmentCategoryMixin;

public class ESWeaponEnchantmentCategory extends EnchantmentCategoryMixin {
    @Override
    public boolean canEnchant(Item item) {
        return item instanceof SwordItem || item instanceof AxeItem || item instanceof ScytheItem || item instanceof HammerItem;
    }
}

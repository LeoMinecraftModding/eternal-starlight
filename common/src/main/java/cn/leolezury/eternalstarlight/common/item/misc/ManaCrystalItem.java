package cn.leolezury.eternalstarlight.common.item.misc;

import cn.leolezury.eternalstarlight.common.spell.ManaType;
import cn.leolezury.eternalstarlight.common.util.Color;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

public class ManaCrystalItem extends Item {
    private final ManaType manaType;

    public ManaCrystalItem(ManaType type, Properties properties) {
        super(properties);
        this.manaType = type;
    }

    @Override
    public int getBarColor(ItemStack itemStack) {
        float f = Math.max(0.0F, ((float)this.getMaxDamage() - (float)itemStack.getDamageValue()) / (float)this.getMaxDamage());
        float r = ((manaType.getColor() >> 16) & 0xFF) / 255f;
        float g = ((manaType.getColor() >> 8) & 0xFF) / 255f;
        float b = (manaType.getColor() & 0xFF) / 255f;
        return new Color(r * f, g * f, b * f, 0).rgb();
    }
}

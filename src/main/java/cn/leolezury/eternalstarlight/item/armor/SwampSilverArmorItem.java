package cn.leolezury.eternalstarlight.item.armor;

import cn.leolezury.eternalstarlight.EternalStarlight;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class SwampSilverArmorItem extends ArmorItem {
    public SwampSilverArmorItem(ArmorMaterial p_40386_, Type p_266831_, Properties p_40388_) {
        super(p_40386_, p_266831_, p_40388_);
    }

    @Override
    public void appendHoverText(ItemStack p_41421_, @Nullable Level p_41422_, List<Component> p_41423_, TooltipFlag p_41424_) {
        p_41423_.add(Component.translatable("tooltip." + EternalStarlight.MOD_ID + ".swamp_silver_armor"));
        super.appendHoverText(p_41421_, p_41422_, p_41423_, p_41424_);
    }
}

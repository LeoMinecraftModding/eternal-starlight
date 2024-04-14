package cn.leolezury.eternalstarlight.common.item.armor;

import cn.leolezury.eternalstarlight.common.EternalStarlight;
import net.minecraft.ChatFormatting;
import net.minecraft.core.Holder;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;

import java.util.List;

public class ThermalSpringStoneArmorItem extends ArmorItem {
    public ThermalSpringStoneArmorItem(Holder<ArmorMaterial> holder, Type type, Properties properties) {
        super(holder, type, properties);
    }

    public static String getTexture(EquipmentSlot slot) {
        return EternalStarlight.MOD_ID + ":textures/armor/thermal_springstone_layer_" + ((slot == EquipmentSlot.LEGS) ? "2.png" : "1.png");
    }

    @Override
    public void appendHoverText(ItemStack itemStack, TooltipContext tooltipContext, List<Component> list, TooltipFlag tooltipFlag) {
        list.add(Component.translatable("tooltip." + EternalStarlight.MOD_ID + ".thermal_springstone_armor").withStyle(ChatFormatting.GOLD));
        super.appendHoverText(itemStack, tooltipContext, list, tooltipFlag);
    }
}

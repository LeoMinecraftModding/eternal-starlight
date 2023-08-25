package cn.leolezury.eternalstarlight.item.armor;

import cn.leolezury.eternalstarlight.EternalStarlight;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class ThermalSpringStoneArmorItem extends ArmorItem {
    public ThermalSpringStoneArmorItem(ArmorMaterial material, Type type, Properties properties) {
        super(material, type, properties);
    }

    public static String getTexture(ItemStack stack, Entity entity, EquipmentSlot slot, String type) {
        return EternalStarlight.MOD_ID + ":textures/armor/thermal_springstone_layer_" + ((slot == EquipmentSlot.LEGS) ? "2.png" : "1.png");
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> component, TooltipFlag flag) {
        component.add(Component.translatable("tooltip." + EternalStarlight.MOD_ID + ".thermal_springstone_armor").withStyle(ChatFormatting.GOLD));
        super.appendHoverText(stack, level, component, flag);
    }
}

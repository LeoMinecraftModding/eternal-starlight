package cn.leolezury.eternalstarlight.common.item.armor;

import cn.leolezury.eternalstarlight.common.EternalStarlight;
import net.minecraft.ChatFormatting;
import net.minecraft.core.Holder;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;

import java.util.List;

public class GlaciteArmorItem extends ArmorItem {
	public GlaciteArmorItem(Holder<ArmorMaterial> holder, Type type, Properties properties) {
		super(holder, type, properties);
	}

	@Override
	public void appendHoverText(ItemStack itemStack, TooltipContext tooltipContext, List<Component> list, TooltipFlag tooltipFlag) {
		list.add(Component.translatable("item.modifiers.armor").withStyle(ChatFormatting.BLUE));
		list.add(Component.translatable("tooltip." + EternalStarlight.ID + ".glacite_armor").withStyle(ChatFormatting.AQUA));
		super.appendHoverText(itemStack, tooltipContext, list, tooltipFlag);
	}
}

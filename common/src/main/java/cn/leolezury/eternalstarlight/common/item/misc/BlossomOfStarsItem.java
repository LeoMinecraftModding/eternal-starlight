package cn.leolezury.eternalstarlight.common.item.misc;

import cn.leolezury.eternalstarlight.common.EternalStarlight;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;

import java.util.List;

public class BlossomOfStarsItem extends Item {
    public BlossomOfStarsItem(Properties properties) {
        super(properties);
    }

    @Override
    public void appendHoverText(ItemStack itemStack, TooltipContext tooltipContext, List<Component> list, TooltipFlag tooltipFlag) {
        list.add(Component.translatable("tooltip." + EternalStarlight.MOD_ID + ".blossom_of_stars").withColor(0x5187c4).withStyle(ChatFormatting.ITALIC));
        super.appendHoverText(itemStack, tooltipContext, list, tooltipFlag);
    }
}

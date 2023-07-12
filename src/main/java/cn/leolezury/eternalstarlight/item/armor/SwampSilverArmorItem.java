package cn.leolezury.eternalstarlight.item.armor;

import cn.leolezury.eternalstarlight.EternalStarlight;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class SwampSilverArmorItem extends ArmorItem {
    public SwampSilverArmorItem(ArmorMaterial material, Type type, Properties properties) {
        super(material, type, properties);
    }

    @Override
    public void onArmorTick(ItemStack stack, Level level, Player player) {
        for (MobEffectInstance effectInstance : player.getActiveEffects()) {
            if (!effectInstance.getEffect().isBeneficial()) {
                player.removeEffect(effectInstance.getEffect());
            }
        }
        super.onArmorTick(stack, level, player);
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> component, TooltipFlag flag) {
        component.add(Component.translatable("tooltip." + EternalStarlight.MOD_ID + ".swamp_silver_armor").withStyle(ChatFormatting.YELLOW));
        super.appendHoverText(stack, level, component, flag);
    }
}

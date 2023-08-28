package cn.leolezury.eternalstarlight.common.item.armor;

import cn.leolezury.eternalstarlight.common.EternalStarlight;
import cn.leolezury.eternalstarlight.common.item.interfaces.TickableArmor;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class SwampSilverArmorItem extends ArmorItem implements TickableArmor {
    public SwampSilverArmorItem(ArmorMaterial material, Type type, Properties properties) {
        super(material, type, properties);
    }

    @Override
    public void tick(Level level, LivingEntity livingEntity, ItemStack armor) {
        for (MobEffectInstance effectInstance : livingEntity.getActiveEffects()) {
            if (!effectInstance.getEffect().isBeneficial()) {
                livingEntity.removeEffect(effectInstance.getEffect());
            }
        }
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> component, TooltipFlag flag) {
        component.add(Component.translatable("tooltip." + EternalStarlight.MOD_ID + ".swamp_silver_armor").withStyle(ChatFormatting.YELLOW));
        super.appendHoverText(stack, level, component, flag);
    }
}

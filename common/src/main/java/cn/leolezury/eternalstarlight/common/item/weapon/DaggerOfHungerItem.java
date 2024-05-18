package cn.leolezury.eternalstarlight.common.item.weapon;

import cn.leolezury.eternalstarlight.common.EternalStarlight;
import cn.leolezury.eternalstarlight.common.data.ESDamageTypes;
import cn.leolezury.eternalstarlight.common.registry.ESDataComponents;
import net.minecraft.ChatFormatting;
import net.minecraft.core.component.DataComponentPatch;
import net.minecraft.network.chat.Component;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.SwordItem;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;

import java.util.List;

public class DaggerOfHungerItem extends SwordItem {
    public DaggerOfHungerItem(Tier tier, Properties properties) {
        super(tier, properties);
    }

    @Override
    public boolean hurtEnemy(ItemStack stack, LivingEntity entity, LivingEntity attacker) {
        entity.addEffect(new MobEffectInstance(MobEffects.HUNGER, 60));
        if (attacker instanceof Player player) {
            player.getFoodData().eat(2, 0.3f);
        }
        float hungerLevel = stack.getOrDefault(ESDataComponents.HUNGER_LEVEL.get(), 0f);
        float newHungerLevel = Math.min(1, hungerLevel + 0.05f);
        stack.applyComponentsAndValidate(DataComponentPatch.builder().set(ESDataComponents.HUNGER_LEVEL.get(), newHungerLevel).build());
        return super.hurtEnemy(stack, entity, attacker);
    }

    @Override
    public void inventoryTick(ItemStack stack, Level level, Entity entity, int slot, boolean bl) {
        if (entity.tickCount % 1200 == 0) {
            float hungerLevel = stack.getOrDefault(ESDataComponents.HUNGER_LEVEL.get(), 0f);
            float newHungerLevel = Math.max(-1, hungerLevel - 0.00005f * 1200);
            stack.applyComponentsAndValidate(DataComponentPatch.builder().set(ESDataComponents.HUNGER_LEVEL.get(), newHungerLevel).build());
            if (hungerLevel == -1) {
                entity.hurt(ESDamageTypes.getDamageSource(level, ESDamageTypes.DAGGER_OF_HUNGER), 3);
                newHungerLevel = Math.min(1, newHungerLevel + 0.05f);
                stack.applyComponentsAndValidate(DataComponentPatch.builder().set(ESDataComponents.HUNGER_LEVEL.get(), newHungerLevel).build());
            }
        }
        super.inventoryTick(stack, level, entity, slot, bl);
    }

    @Override
    public void appendHoverText(ItemStack itemStack, TooltipContext tooltipContext, List<Component> list, TooltipFlag tooltipFlag) {
        float hungerLevel = itemStack.getOrDefault(ESDataComponents.HUNGER_LEVEL.get(), 0f);
        list.add(Component.translatable("tooltip." + EternalStarlight.ID + ".dagger_of_hunger.hunger_value").append(Math.round((hungerLevel + 1) * 50) + "%").withStyle(ChatFormatting.DARK_PURPLE));
        list.add(Component.translatable("tooltip." + EternalStarlight.ID + ".dagger_of_hunger.when_attack").withStyle(ChatFormatting.GOLD));
        list.add(Component.translatable("tooltip." + EternalStarlight.ID + ".dagger_of_hunger.attack_bonus").withStyle(ChatFormatting.GOLD));
        list.add(Component.translatable("tooltip." + EternalStarlight.ID + ".dagger_of_hunger.hurt_player").withStyle(ChatFormatting.GOLD));
        super.appendHoverText(itemStack, tooltipContext, list, tooltipFlag);
    }
}

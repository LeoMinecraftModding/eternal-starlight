package cn.leolezury.eternalstarlight.common.item.armor;

import cn.leolezury.eternalstarlight.common.EternalStarlight;
import cn.leolezury.eternalstarlight.common.item.interfaces.TickableArmor;
import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Multimap;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.UUID;

public class SwampSilverArmorItem extends ArmorItem implements TickableArmor {
    private final Multimap<Attribute, AttributeModifier> extraModifiers;

    public SwampSilverArmorItem(ArmorMaterial material, Type type, Properties properties) {
        super(material, type, properties);
        ImmutableMultimap.Builder<Attribute, AttributeModifier> builder = ImmutableMultimap.builder();
        builder.put(Attributes.ATTACK_SPEED, new AttributeModifier(UUID.fromString("CBADB27C-C029-49BF-8C9F-C00173A58B6B"), "Armor modifier", 0.05, AttributeModifier.Operation.ADDITION));
        builder.put(Attributes.MOVEMENT_SPEED, new AttributeModifier(UUID.fromString("C001727C-D029-018D-8D9F-CBAD88178B6B"), "Armor modifier", 0.1, AttributeModifier.Operation.ADDITION));
        this.extraModifiers = builder.build();
    }

    @Override
    public Multimap<Attribute, AttributeModifier> getDefaultAttributeModifiers(EquipmentSlot equipmentSlot) {
        Multimap<Attribute, AttributeModifier> modifiers = super.getDefaultAttributeModifiers(equipmentSlot);
        ImmutableMultimap.Builder<Attribute, AttributeModifier> builder = ImmutableMultimap.builder();
        builder.putAll(modifiers);
        builder.putAll(extraModifiers);
        Multimap<Attribute, AttributeModifier> allModifiers = builder.build();
        return equipmentSlot == this.type.getSlot() ? allModifiers : super.getDefaultAttributeModifiers(equipmentSlot);
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

package cn.leolezury.eternalstarlight.common.item.armor;

import cn.leolezury.eternalstarlight.common.EternalStarlight;
import cn.leolezury.eternalstarlight.common.item.interfaces.TickableArmor;
import com.google.common.base.Suppliers;
import net.minecraft.ChatFormatting;
import net.minecraft.Util;
import net.minecraft.core.Holder;
import net.minecraft.network.chat.Component;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.EquipmentSlotGroup;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.*;
import net.minecraft.world.item.component.ItemAttributeModifiers;
import net.minecraft.world.level.Level;

import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;
import java.util.UUID;
import java.util.function.Supplier;

public class SwampSilverArmorItem extends ArmorItem implements TickableArmor {
    private static final EnumMap<Type, UUID> ARMOR_MODIFIER_UUID_PER_TYPE = Util.make(new EnumMap(Type.class), (enumMap) -> {
        enumMap.put(Type.BOOTS, UUID.fromString("845DB27C-C624-495F-8C9F-6020A9A58B6B"));
        enumMap.put(Type.LEGGINGS, UUID.fromString("D8499B04-0E66-4726-AB29-64469D734E0D"));
        enumMap.put(Type.CHESTPLATE, UUID.fromString("9F3D476D-C118-4544-8365-64846904B48E"));
        enumMap.put(Type.HELMET, UUID.fromString("2AD3F246-FEE1-4E67-B886-69FD380BB150"));
    });

    private final Supplier<ItemAttributeModifiers> extraModifiers;

    public SwampSilverArmorItem(Holder<ArmorMaterial> materialHolder, Type type, Item.Properties properties) {
        super(materialHolder, type, properties);
        this.extraModifiers = Suppliers.memoize(() -> {
            ItemAttributeModifiers.Builder builder = ItemAttributeModifiers.builder();
            EquipmentSlotGroup equipmentSlotGroup = EquipmentSlotGroup.bySlot(type.getSlot());
            UUID uuid = ARMOR_MODIFIER_UUID_PER_TYPE.get(type);
            builder.add(Attributes.ATTACK_SPEED, new AttributeModifier(uuid, "Armor modifier", 0.05, AttributeModifier.Operation.ADD_VALUE), equipmentSlotGroup);
            builder.add(Attributes.MOVEMENT_SPEED, new AttributeModifier(uuid, "Armor modifier", 0.1, AttributeModifier.Operation.ADD_VALUE), equipmentSlotGroup);
            return builder.build();
        });
    }

    @Override
    public ItemAttributeModifiers getDefaultAttributeModifiers() {
        ItemAttributeModifiers modifiers = super.getDefaultAttributeModifiers();
        ItemAttributeModifiers.Builder builder = ItemAttributeModifiers.builder();
        for (ItemAttributeModifiers.Entry entry : modifiers.modifiers()) {
            builder.add(entry.attribute(), entry.modifier(), entry.slot());
        }
        for (ItemAttributeModifiers.Entry entry : extraModifiers.get().modifiers()) {
            builder.add(entry.attribute(), entry.modifier(), entry.slot());
        }
        return builder.build();
    }

    @Override
    public void tick(Level level, LivingEntity livingEntity, ItemStack armor) {
        List<Holder<MobEffect>> effectsToRemove = new ArrayList<>();
        for (MobEffectInstance effectInstance : livingEntity.getActiveEffects()) {
            if (!effectInstance.getEffect().value().isBeneficial()) {
                effectsToRemove.add(effectInstance.getEffect());
            }
        }
        for (Holder<MobEffect> effect : effectsToRemove) {
            if (livingEntity.hasEffect(effect)) {
                livingEntity.removeEffect(effect);
            }
        }
    }

    @Override
    public void appendHoverText(ItemStack itemStack, TooltipContext tooltipContext, List<Component> list, TooltipFlag tooltipFlag) {
        list.add(Component.translatable("tooltip." + EternalStarlight.ID + ".swamp_silver_armor").withStyle(ChatFormatting.YELLOW));
        super.appendHoverText(itemStack, tooltipContext, list, tooltipFlag);
    }
}

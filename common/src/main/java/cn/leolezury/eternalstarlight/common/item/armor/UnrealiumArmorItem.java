package cn.leolezury.eternalstarlight.common.item.armor;

import cn.leolezury.eternalstarlight.common.EternalStarlight;
import cn.leolezury.eternalstarlight.common.registry.ESAttributes;
import com.google.common.base.Suppliers;
import net.minecraft.ChatFormatting;
import net.minecraft.core.Holder;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EquipmentSlotGroup;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.component.ItemAttributeModifiers;

import java.util.List;
import java.util.function.Supplier;

public class UnrealiumArmorItem extends ArmorItem {
	private final Supplier<ItemAttributeModifiers> extraModifiers;

	public UnrealiumArmorItem(Holder<ArmorMaterial> materialHolder, Type type, Properties properties) {
		super(materialHolder, type, properties);
		this.extraModifiers = Suppliers.memoize(() -> {
			ItemAttributeModifiers.Builder builder = ItemAttributeModifiers.builder();
			EquipmentSlotGroup equipmentSlotGroup = EquipmentSlotGroup.bySlot(type.getSlot());
			ResourceLocation resourceLocation = ResourceLocation.withDefaultNamespace("armor." + type.getName());
			builder.add(Attributes.MOVEMENT_SPEED, new AttributeModifier(resourceLocation, 0.05, AttributeModifier.Operation.ADD_VALUE), equipmentSlotGroup);
			builder.add(Attributes.MOVEMENT_SPEED, new AttributeModifier(resourceLocation, 0.1, AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL), equipmentSlotGroup);
			builder.add(Attributes.KNOCKBACK_RESISTANCE, new AttributeModifier(resourceLocation, -0.05, AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL), equipmentSlotGroup);
			builder.add(ESAttributes.ENEMY_FOLLOW_RANGE_MULTIPLIER.asHolder(), new AttributeModifier(resourceLocation, -0.15, AttributeModifier.Operation.ADD_VALUE), equipmentSlotGroup);
			if (type == Type.HELMET) {
				builder.add(ESAttributes.FOG_VISION.asHolder(), new AttributeModifier(resourceLocation, 50, AttributeModifier.Operation.ADD_VALUE), equipmentSlotGroup);
			}
			if (type == Type.CHESTPLATE) {
				builder.add(Attributes.EXPLOSION_KNOCKBACK_RESISTANCE, new AttributeModifier(resourceLocation, 1, AttributeModifier.Operation.ADD_VALUE), equipmentSlotGroup);
			}
			if (type == Type.LEGGINGS) {
				builder.add(Attributes.GRAVITY, new AttributeModifier(resourceLocation, -0.02, AttributeModifier.Operation.ADD_VALUE), equipmentSlotGroup);
			}
			if (type == Type.BOOTS) {
				builder.add(Attributes.MOVEMENT_EFFICIENCY, new AttributeModifier(resourceLocation, 1, AttributeModifier.Operation.ADD_VALUE), equipmentSlotGroup);
				builder.add(Attributes.STEP_HEIGHT, new AttributeModifier(resourceLocation, 0.5, AttributeModifier.Operation.ADD_VALUE), equipmentSlotGroup);
			}
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
	public void appendHoverText(ItemStack itemStack, TooltipContext tooltipContext, List<Component> list, TooltipFlag tooltipFlag) {
		if (getType() == Type.HELMET) {
			list.add(CommonComponents.EMPTY);
			list.add(Component.translatable("item.modifiers.armor").withStyle(ChatFormatting.GRAY));
			list.add(Component.literal(" ").append(Component.translatable("tooltip." + EternalStarlight.ID + ".unrealium_helmet")).withStyle(ChatFormatting.DARK_PURPLE));
			list.add(Component.literal(" ").append(Component.translatable("tooltip." + EternalStarlight.ID + ".unrealium_helmet.mute")).withStyle(ChatFormatting.DARK_PURPLE));
		} else if (getType() == Type.CHESTPLATE) {
			list.add(CommonComponents.EMPTY);
			list.add(Component.translatable("item.modifiers.armor").withStyle(ChatFormatting.GRAY));
			list.add(Component.literal(" ").append(Component.translatable("tooltip." + EternalStarlight.ID + ".unrealium_chestplate")).withStyle(ChatFormatting.DARK_PURPLE));
			list.add(Component.literal(" ").append(Component.translatable("tooltip." + EternalStarlight.ID + ".unrealium_chestplate.mute")).withStyle(ChatFormatting.DARK_PURPLE));
		} else if (getType() == Type.LEGGINGS) {
			list.add(CommonComponents.EMPTY);
			list.add(Component.translatable("item.modifiers.armor").withStyle(ChatFormatting.GRAY));
			list.add(Component.literal(" ").append(Component.translatable("tooltip." + EternalStarlight.ID + ".unrealium_leggings.mute")).withStyle(ChatFormatting.DARK_PURPLE));
		} else if (getType() == Type.BOOTS) {
			list.add(CommonComponents.EMPTY);
			list.add(Component.translatable("item.modifiers.armor").withStyle(ChatFormatting.GRAY));
			list.add(Component.literal(" ").append(Component.translatable("tooltip." + EternalStarlight.ID + ".unrealium_boots.mute")).withStyle(ChatFormatting.DARK_PURPLE));
		}
		super.appendHoverText(itemStack, tooltipContext, list, tooltipFlag);
	}
}

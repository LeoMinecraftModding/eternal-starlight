package cn.leolezury.eternalstarlight.common.item.armor;

import cn.leolezury.eternalstarlight.common.EternalStarlight;
import cn.leolezury.eternalstarlight.common.registry.ESAttributes;
import com.google.common.base.Suppliers;
import net.minecraft.ChatFormatting;
import net.minecraft.core.Holder;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.EquipmentSlotGroup;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.component.ItemAttributeModifiers;

import java.util.List;
import java.util.function.Supplier;

public class ThermalSpringstoneArmorItem extends ArmorItem {
	private final Supplier<ItemAttributeModifiers> extraModifiers;

	public ThermalSpringstoneArmorItem(Holder<ArmorMaterial> holder, Type type, Properties properties) {
		super(holder, type, properties);
		this.extraModifiers = Suppliers.memoize(() -> {
			ItemAttributeModifiers.Builder builder = ItemAttributeModifiers.builder();
			EquipmentSlotGroup equipmentSlotGroup = EquipmentSlotGroup.bySlot(type.getSlot());
			ResourceLocation resourceLocation = ResourceLocation.withDefaultNamespace("armor." + type.getName());
			builder.add(ESAttributes.FIRE_RESISTANCE.asHolder(), new AttributeModifier(resourceLocation, 0.24, AttributeModifier.Operation.ADD_VALUE), equipmentSlotGroup);
			return builder.build();
		});
	}

	public static ResourceLocation getTexture(EquipmentSlot slot) {
		return EternalStarlight.id("textures/armor/thermal_springstone_layer_" + ((slot == EquipmentSlot.LEGS) ? "2.png" : "1.png"));
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
		list.add(CommonComponents.EMPTY);
		list.add(Component.translatable("item.modifiers.armor").withStyle(ChatFormatting.GRAY));
		list.add(Component.literal(" ").append(Component.translatable("tooltip." + EternalStarlight.ID + ".thermal_springstone_armor")).withStyle(ChatFormatting.GOLD));
		super.appendHoverText(itemStack, tooltipContext, list, tooltipFlag);
	}
}

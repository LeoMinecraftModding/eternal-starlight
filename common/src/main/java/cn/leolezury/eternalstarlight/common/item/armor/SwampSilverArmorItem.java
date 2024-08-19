package cn.leolezury.eternalstarlight.common.item.armor;

import cn.leolezury.eternalstarlight.common.EternalStarlight;
import cn.leolezury.eternalstarlight.common.codecs.SwampSilverRemovableEffects;
import cn.leolezury.eternalstarlight.common.data.ESRegistries;
import cn.leolezury.eternalstarlight.common.item.interfaces.TickableArmor;
import com.google.common.base.Suppliers;
import net.minecraft.ChatFormatting;
import net.minecraft.core.Holder;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
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
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.Supplier;

public class SwampSilverArmorItem extends ArmorItem implements TickableArmor {
	private final Supplier<ItemAttributeModifiers> extraModifiers;

	public SwampSilverArmorItem(Holder<ArmorMaterial> materialHolder, Type type, Item.Properties properties) {
		super(materialHolder, type, properties);
		this.extraModifiers = Suppliers.memoize(() -> {
			ItemAttributeModifiers.Builder builder = ItemAttributeModifiers.builder();
			EquipmentSlotGroup equipmentSlotGroup = EquipmentSlotGroup.bySlot(type.getSlot());
			ResourceLocation resourceLocation = ResourceLocation.withDefaultNamespace("armor." + type.getName());
			builder.add(Attributes.ATTACK_SPEED, new AttributeModifier(resourceLocation, 0.05, AttributeModifier.Operation.ADD_VALUE), equipmentSlotGroup);
			builder.add(Attributes.MOVEMENT_SPEED, new AttributeModifier(resourceLocation, 0.01, AttributeModifier.Operation.ADD_VALUE), equipmentSlotGroup);
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
		AtomicBoolean fullSet = new AtomicBoolean(true);
		livingEntity.getArmorSlots().forEach(stack -> {
			if (!(stack.getItem() instanceof SwampSilverArmorItem)) {
				fullSet.set(false);
			}
		});
		if (fullSet.get()) {
			level.registryAccess().registryOrThrow(ESRegistries.REMOVABLE_EFFECTS).forEach(effects -> {
				for (var effect : effects.effects()) {
					if (livingEntity.hasEffect(effect)) {
						livingEntity.removeEffect(effect);
					}
				}
			});
		}
	}

	@Override
	public void appendHoverText(ItemStack itemStack, TooltipContext tooltipContext, List<Component> list, TooltipFlag tooltipFlag) {
		list.add(Component.translatable("tooltip." + EternalStarlight.ID + ".full_set").withStyle(ChatFormatting.BLUE));
		list.add(Component.translatable("tooltip." + EternalStarlight.ID + ".swamp_silver_armor").withStyle(ChatFormatting.YELLOW));
		super.appendHoverText(itemStack, tooltipContext, list, tooltipFlag);
	}
}

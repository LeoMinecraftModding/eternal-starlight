package cn.leolezury.eternalstarlight.common.item.armor;

import cn.leolezury.eternalstarlight.common.EternalStarlight;
import cn.leolezury.eternalstarlight.common.registry.ESAttributes;
import com.google.common.base.Suppliers;
import net.minecraft.core.Holder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EquipmentSlotGroup;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.component.ItemAttributeModifiers;

import java.util.function.Supplier;

public class AlchemistArmorItem extends ArmorItem {
    public static final ResourceLocation TEXTURE = EternalStarlight.id("textures/armor/alchemist.png");

    private final Supplier<ItemAttributeModifiers> extraModifiers;

    public AlchemistArmorItem(Holder<ArmorMaterial> holder, Type type, Properties properties) {
        super(holder, type, properties);
        this.extraModifiers = Suppliers.memoize(() -> {
            ItemAttributeModifiers.Builder builder = ItemAttributeModifiers.builder();
            EquipmentSlotGroup equipmentSlotGroup = EquipmentSlotGroup.bySlot(type.getSlot());
            ResourceLocation resourceLocation = ResourceLocation.withDefaultNamespace("armor." + type.getName());
            builder.add(ESAttributes.THROWN_POTION_DISTANCE.asHolder(), new AttributeModifier(resourceLocation, 0.25, AttributeModifier.Operation.ADD_VALUE), equipmentSlotGroup);
            builder.add(ESAttributes.ETHER_RESISTANCE.asHolder(), new AttributeModifier(resourceLocation, 0.3, AttributeModifier.Operation.ADD_VALUE), equipmentSlotGroup);
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
}

package cn.leolezury.eternalstarlight.forge.item.armor;

import cn.leolezury.eternalstarlight.common.item.armor.ThermalSpringStoneArmorItem;
import net.minecraft.core.Holder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.Nullable;

public class ForgeThermalSpringStoneArmorItem extends ThermalSpringStoneArmorItem {
	public ForgeThermalSpringStoneArmorItem(Holder<ArmorMaterial> material, Type type, Properties properties) {
		super(material, type, properties);
	}

	@Override
	public @Nullable ResourceLocation getArmorTexture(ItemStack stack, Entity entity, EquipmentSlot slot, ArmorMaterial.Layer layer, boolean innerModel) {
		return getTexture(slot);
	}
}

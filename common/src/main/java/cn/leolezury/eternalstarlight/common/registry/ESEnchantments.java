package cn.leolezury.eternalstarlight.common.registry;

import cn.leolezury.eternalstarlight.common.EternalStarlight;
import cn.leolezury.eternalstarlight.common.enchantment.FearlessEnchantment;
import cn.leolezury.eternalstarlight.common.enchantment.PoisoningEnchantment;
import cn.leolezury.eternalstarlight.common.platform.registry.RegistrationProvider;
import cn.leolezury.eternalstarlight.common.platform.registry.RegistryObject;
import net.minecraft.core.registries.Registries;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.enchantment.Enchantment;

public class ESEnchantments {
    public static final RegistrationProvider<Enchantment> ENCHANTMENTS = RegistrationProvider.get(Registries.ENCHANTMENT, EternalStarlight.MOD_ID);
    public static final RegistryObject<Enchantment, Enchantment> POISONING = ENCHANTMENTS.register("poisoning", () -> new PoisoningEnchantment(Enchantment.definition(ItemTags.ARMOR_ENCHANTABLE, 10, 4, Enchantment.dynamicCost(1, 11), Enchantment.dynamicCost(12, 11), 1, EquipmentSlot.HEAD, EquipmentSlot.CHEST, EquipmentSlot.LEGS, EquipmentSlot.FEET)));
    public static final RegistryObject<Enchantment, Enchantment> FEARLESS = ENCHANTMENTS.register("fearless", () -> new FearlessEnchantment(Enchantment.definition(ItemTags.SHARP_WEAPON_ENCHANTABLE, ItemTags.SWORD_ENCHANTABLE, 10, 2, Enchantment.dynamicCost(1, 11), Enchantment.dynamicCost(21, 11), 1, EquipmentSlot.MAINHAND)));
    public static void loadClass() {}
}

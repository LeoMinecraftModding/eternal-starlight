package cn.leolezury.eternalstarlight.common.init;

import cn.leolezury.eternalstarlight.common.EternalStarlight;
import cn.leolezury.eternalstarlight.common.enchantment.FearlessEnchantment;
import cn.leolezury.eternalstarlight.common.enchantment.PoisoningEnchantment;
import cn.leolezury.eternalstarlight.common.util.register.RegistrationProvider;
import cn.leolezury.eternalstarlight.common.util.register.RegistryObject;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.enchantment.Enchantment;

public class EnchantmentInit {
    public static final RegistrationProvider<Enchantment> ENCHANTMENTS = RegistrationProvider.get(Registries.ENCHANTMENT, EternalStarlight.MOD_ID);
    private static final EquipmentSlot[] ARMOR_SLOTS = new EquipmentSlot[]{EquipmentSlot.HEAD, EquipmentSlot.CHEST, EquipmentSlot.LEGS, EquipmentSlot.FEET};
    public static final RegistryObject<Enchantment> POISONING = ENCHANTMENTS.register("poisoning", () -> new PoisoningEnchantment(Enchantment.Rarity.RARE, ARMOR_SLOTS));
    public static final RegistryObject<Enchantment> FEARLESS = ENCHANTMENTS.register("fearless", () -> new FearlessEnchantment(Enchantment.Rarity.RARE, EquipmentSlot.MAINHAND));
    public static void postRegistry() {}
}

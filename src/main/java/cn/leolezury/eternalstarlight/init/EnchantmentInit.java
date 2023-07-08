package cn.leolezury.eternalstarlight.init;

import cn.leolezury.eternalstarlight.EternalStarlight;
import cn.leolezury.eternalstarlight.enchantment.FearlessEnchantment;
import cn.leolezury.eternalstarlight.enchantment.PoisoningEnchantment;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class EnchantmentInit {
    public static final DeferredRegister<Enchantment> ENCHANTMENTS = DeferredRegister.create(ForgeRegistries.ENCHANTMENTS, EternalStarlight.MOD_ID);
    private static final EquipmentSlot[] ARMOR_SLOTS = new EquipmentSlot[]{EquipmentSlot.HEAD, EquipmentSlot.CHEST, EquipmentSlot.LEGS, EquipmentSlot.FEET};
    public static final RegistryObject<Enchantment> POISONING = ENCHANTMENTS.register("poisoning", () -> new PoisoningEnchantment(Enchantment.Rarity.RARE, ARMOR_SLOTS));
    public static final RegistryObject<Enchantment> FEARLESS = ENCHANTMENTS.register("fearless", () -> new FearlessEnchantment(Enchantment.Rarity.RARE, EquipmentSlot.MAINHAND));
}

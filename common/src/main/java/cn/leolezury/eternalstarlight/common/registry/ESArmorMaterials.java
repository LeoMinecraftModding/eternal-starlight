package cn.leolezury.eternalstarlight.common.registry;

import cn.leolezury.eternalstarlight.common.EternalStarlight;
import cn.leolezury.eternalstarlight.common.platform.registry.RegistrationProvider;
import cn.leolezury.eternalstarlight.common.platform.registry.RegistryObject;
import net.minecraft.Util;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.crafting.Ingredient;

import java.util.EnumMap;
import java.util.List;

public class ESArmorMaterials {
    public static final RegistrationProvider<ArmorMaterial> ARMOR_MATERIALS = RegistrationProvider.get(Registries.ARMOR_MATERIAL, EternalStarlight.MOD_ID);
    public static final RegistryObject<ArmorMaterial, ArmorMaterial> AMARAMBER = ARMOR_MATERIALS.register("amaramber", () -> new ArmorMaterial(Util.make(new EnumMap<>(ArmorItem.Type.class), (map) -> {
        map.put(ArmorItem.Type.BOOTS, 2);
        map.put(ArmorItem.Type.LEGGINGS, 5);
        map.put(ArmorItem.Type.CHESTPLATE, 5);
        map.put(ArmorItem.Type.HELMET, 2);
    }), 9, ESSoundEvents.ARMOR_EQUIP_AMARAMBER.asHolder(), () -> Ingredient.of(ESItems.AMARAMBER_INGOT.get()), List.of(new ArmorMaterial.Layer(new ResourceLocation(EternalStarlight.MOD_ID, "amaramber"))), 0F, 0F));
    public static final RegistryObject<ArmorMaterial, ArmorMaterial> AETHERSENT = ARMOR_MATERIALS.register("aethersent", () -> new ArmorMaterial(Util.make(new EnumMap<>(ArmorItem.Type.class), (map) -> {
        map.put(ArmorItem.Type.BOOTS, 2);
        map.put(ArmorItem.Type.LEGGINGS, 5);
        map.put(ArmorItem.Type.CHESTPLATE, 5);
        map.put(ArmorItem.Type.HELMET, 2);
    }), 35, ESSoundEvents.ARMOR_EQUIP_AETHERSENT.asHolder(), () -> Ingredient.of(ESItems.AETHERSENT_INGOT.get()), List.of(new ArmorMaterial.Layer(new ResourceLocation(EternalStarlight.MOD_ID, "aethersent"))), 0F, 0F));
    public static final RegistryObject<ArmorMaterial, ArmorMaterial> THERMAL_SPRINGSTONE = ARMOR_MATERIALS.register("thermal_springstone", () -> new ArmorMaterial(Util.make(new EnumMap<>(ArmorItem.Type.class), (map) -> {
        map.put(ArmorItem.Type.BOOTS, 2);
        map.put(ArmorItem.Type.LEGGINGS, 5);
        map.put(ArmorItem.Type.CHESTPLATE, 6);
        map.put(ArmorItem.Type.HELMET, 3);
    }), 15, ESSoundEvents.ARMOR_EQUIP_THERMAL_SPRINGSTONE.asHolder(), () -> Ingredient.of(ESItems.THERMAL_SPRINGSTONE_INGOT.get()), List.of(new ArmorMaterial.Layer(new ResourceLocation(EternalStarlight.MOD_ID, "thermal_springstone"))), 0.5F, 0F));
    public static final RegistryObject<ArmorMaterial, ArmorMaterial> GLACITE = ARMOR_MATERIALS.register("glacite", () -> new ArmorMaterial(Util.make(new EnumMap<>(ArmorItem.Type.class), (map) -> {
        map.put(ArmorItem.Type.BOOTS, 3);
        map.put(ArmorItem.Type.LEGGINGS, 5);
        map.put(ArmorItem.Type.CHESTPLATE, 6);
        map.put(ArmorItem.Type.HELMET, 3);
    }), 9, ESSoundEvents.ARMOR_EQUIP_GLACITE.asHolder(), () -> Ingredient.of(ESItems.GLACITE_SHARD.get()), List.of(new ArmorMaterial.Layer(new ResourceLocation(EternalStarlight.MOD_ID, "glacite"))), 0.5F, 0F));
    public static final RegistryObject<ArmorMaterial, ArmorMaterial> SWAMP_SILVER = ARMOR_MATERIALS.register("swamp_silver", () -> new ArmorMaterial(Util.make(new EnumMap<>(ArmorItem.Type.class), (map) -> {
        map.put(ArmorItem.Type.BOOTS, 2);
        map.put(ArmorItem.Type.LEGGINGS, 5);
        map.put(ArmorItem.Type.CHESTPLATE, 6);
        map.put(ArmorItem.Type.HELMET, 2);
    }), 25, ESSoundEvents.ARMOR_EQUIP_SWAMP_SILVER.asHolder(), () -> Ingredient.of(ESItems.SWAMP_SILVER_INGOT.get()), List.of(new ArmorMaterial.Layer(new ResourceLocation(EternalStarlight.MOD_ID, "swamp_silver"))), 0.5F, 0.2F));
}

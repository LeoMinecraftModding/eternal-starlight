package cn.leolezury.eternalstarlight.common.item.armor;

import cn.leolezury.eternalstarlight.common.EternalStarlight;
import cn.leolezury.eternalstarlight.common.registry.ESItems;
import cn.leolezury.eternalstarlight.common.registry.ESSoundEvents;
import net.minecraft.Util;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.util.LazyLoadedValue;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.crafting.Ingredient;

import java.util.EnumMap;
import java.util.function.Supplier;

public enum ESArmorMaterials implements ArmorMaterial {
    AMARAMBER(EternalStarlight.MOD_ID + ":amaramber", 15, Util.make(new EnumMap<>(ArmorItem.Type.class), (map) -> {
        map.put(ArmorItem.Type.BOOTS, 2);
        map.put(ArmorItem.Type.LEGGINGS, 5);
        map.put(ArmorItem.Type.CHESTPLATE, 5);
        map.put(ArmorItem.Type.HELMET, 2);
    }), 9, ESSoundEvents.ARMOR_EQUIP_AMARAMBER.get(), 0F, 0F, () -> Ingredient.of(ESItems.AMARAMBER_INGOT.get())),
    AETHERSENT(EternalStarlight.MOD_ID + ":aethersent", 15, Util.make(new EnumMap<>(ArmorItem.Type.class), (map) -> {
        map.put(ArmorItem.Type.BOOTS, 2);
        map.put(ArmorItem.Type.LEGGINGS, 5);
        map.put(ArmorItem.Type.CHESTPLATE, 5);
        map.put(ArmorItem.Type.HELMET, 2);
    }), 35, ESSoundEvents.ARMOR_EQUIP_AETHERSENT.get(), 0F, 0F, () -> Ingredient.of(ESItems.AETHERSENT_INGOT.get())),
    THERMAL_SPRINGSTONE(EternalStarlight.MOD_ID + ":thermal_springstone", 12, Util.make(new EnumMap<>(ArmorItem.Type.class), (map) -> {
        map.put(ArmorItem.Type.BOOTS, 2);
        map.put(ArmorItem.Type.LEGGINGS, 5);
        map.put(ArmorItem.Type.CHESTPLATE, 6);
        map.put(ArmorItem.Type.HELMET, 3);
    }), 15, ESSoundEvents.ARMOR_EQUIP_THERMAL_SPRINGSTONE.get(), 0.5F, 0.0F, () -> Ingredient.of(ESItems.THERMAL_SPRINGSTONE_INGOT.get())),
    GLACITE(EternalStarlight.MOD_ID + ":glacite", 15, Util.make(new EnumMap<>(ArmorItem.Type.class), (map) -> {
        map.put(ArmorItem.Type.BOOTS, 3);
        map.put(ArmorItem.Type.LEGGINGS, 5);
        map.put(ArmorItem.Type.CHESTPLATE, 6);
        map.put(ArmorItem.Type.HELMET, 3);
    }), 15, ESSoundEvents.ARMOR_EQUIP_GLACITE.get(), 0.5F, 0.0F, () -> Ingredient.of(ESItems.GLACITE_SHARD.get())),
    SWAMP_SILVER(EternalStarlight.MOD_ID + ":swamp_silver", 33, Util.make(new EnumMap<>(ArmorItem.Type.class), (map) -> {
        map.put(ArmorItem.Type.BOOTS, 2);
        map.put(ArmorItem.Type.LEGGINGS, 5);
        map.put(ArmorItem.Type.CHESTPLATE, 6);
        map.put(ArmorItem.Type.HELMET, 2);
    }), 25, ESSoundEvents.ARMOR_EQUIP_SWAMP_SILVER.get(), 1.5F, 0.2F, () -> Ingredient.of(ESItems.SWAMP_SILVER_INGOT.get()));

    private static final EnumMap<ArmorItem.Type, Integer> HEALTH_FUNCTION_FOR_TYPE = Util.make(new EnumMap<>(ArmorItem.Type.class), (map) -> {
        map.put(ArmorItem.Type.BOOTS, 13);
        map.put(ArmorItem.Type.LEGGINGS, 15);
        map.put(ArmorItem.Type.CHESTPLATE, 16);
        map.put(ArmorItem.Type.HELMET, 11);
    });
    private final String name;
    private final int durabilityMultiplier;
    private final EnumMap<ArmorItem.Type, Integer> protectionFunctionForType;
    private final int enchantmentValue;
    private final SoundEvent sound;
    private final float toughness;
    private final float knockbackResistance;
    private final LazyLoadedValue<Ingredient> repairIngredient;

    private ESArmorMaterials(String name, int multiplier, EnumMap<ArmorItem.Type, Integer> map, int enchantability, SoundEvent sound, float toughness, float resistance, Supplier<Ingredient> ingredient) {
        this.name = name;
        this.durabilityMultiplier = multiplier;
        this.protectionFunctionForType = map;
        this.enchantmentValue = enchantability;
        this.sound = sound;
        this.toughness = toughness;
        this.knockbackResistance = resistance;
        this.repairIngredient = new LazyLoadedValue<>(ingredient);
    }

    public int getDurabilityForType(ArmorItem.Type type) {
        return HEALTH_FUNCTION_FOR_TYPE.get(type) * this.durabilityMultiplier;
    }

    public int getDefenseForType(ArmorItem.Type type) {
        return this.protectionFunctionForType.get(type);
    }

    public int getEnchantmentValue() {
        return this.enchantmentValue;
    }

    public SoundEvent getEquipSound() {
        return this.sound;
    }

    public Ingredient getRepairIngredient() {
        return this.repairIngredient.get();
    }

    public String getName() {
        return this.name;
    }

    public float getToughness() {
        return this.toughness;
    }

    public float getKnockbackResistance() {
        return this.knockbackResistance;
    }
}

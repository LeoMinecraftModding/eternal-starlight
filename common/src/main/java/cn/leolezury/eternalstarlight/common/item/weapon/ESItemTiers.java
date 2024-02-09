package cn.leolezury.eternalstarlight.common.item.weapon;

import cn.leolezury.eternalstarlight.common.init.ItemInit;
import net.minecraft.util.LazyLoadedValue;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.crafting.Ingredient;

import java.util.function.Supplier;

public enum ESItemTiers implements Tier {
    AMARAMBER(2, 250, 6.0F, 2.0F, 14, () -> Ingredient.of(ItemInit.AMARAMBER_INGOT.get())),
    AETHERSENT(2, 400, 6.0F, 1.0F, 22, () -> Ingredient.of(ItemInit.AETHERSENT_INGOT.get())),
    THERMAL_SPRINGSTONE(2, 400, 6.0F, 2.0F, 10, () -> Ingredient.of(ItemInit.THERMAL_SPRINGSTONE_INGOT.get())),
    SWAMP_SILVER(3, 800, 12.0F, 2.0F, 10, () -> Ingredient.of(ItemInit.SWAMP_SILVER_INGOT.get())),
    DOOMEDEN(3, 2000, 7.5F, 2.5F, 10, () -> Ingredient.of(ItemInit.BROKEN_DOOMEDEN_BONE.get())),
    PETAL(3, 1500, 7.5F, 3.5F, 22, () -> Ingredient.of(ItemInit.TENACIOUS_PETAL.get())),
    AURORA_DEER_ANTLER(2, 400, 6.0F, 1.0F, 22, () -> Ingredient.of(ItemInit.THERMAL_SPRINGSTONE_INGOT.get()));

    private final int level;
    private final int uses;
    private final float speed;
    private final float damage;
    private final int enchantmentValue;
    private final LazyLoadedValue<Ingredient> repairIngredient;

    ESItemTiers(int level, int durability, float miningSpeed, float damage, int enchantability, Supplier<Ingredient> repairIngredient) {
        this.level = level;
        this.uses = durability;
        this.speed = miningSpeed;
        this.damage = damage;
        this.enchantmentValue = enchantability;
        this.repairIngredient = new LazyLoadedValue<>(repairIngredient);
    }

    public int getUses() {
        return this.uses;
    }

    public float getSpeed() {
        return this.speed;
    }

    public float getAttackDamageBonus() {
        return this.damage;
    }

    public int getLevel() {
        return this.level;
    }

    public int getEnchantmentValue() {
        return this.enchantmentValue;
    }

    public Ingredient getRepairIngredient() {
        return this.repairIngredient.get();
    }
}

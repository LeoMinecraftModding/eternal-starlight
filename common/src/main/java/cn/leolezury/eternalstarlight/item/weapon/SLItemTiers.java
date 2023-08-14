package cn.leolezury.eternalstarlight.item.weapon;

import cn.leolezury.eternalstarlight.init.ItemInit;
import net.minecraft.util.LazyLoadedValue;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.crafting.Ingredient;

import java.util.function.Supplier;

public enum SLItemTiers implements Tier {
    THERMAL_SPRINGSTONE(2, 1000, 10F, 2.5F, 10, () -> Ingredient.of(ItemInit.THERMAL_SPRINGSTONE_INGOT.get())),
    SWAMP_SILVER(3, 2000, 7.5F, 2.5F, 10, () -> Ingredient.of(ItemInit.SWAMP_SILVER_INGOT.get())),
    PETAL(3, 1500, 7.5F, 3.5F, 22, () -> Ingredient.of(ItemInit.TENACIOUS_PETAL.get()));

    private final int level;
    private final int uses;
    private final float speed;
    private final float damage;
    private final int enchantmentValue;
    private final LazyLoadedValue<Ingredient> repairIngredient;

    SLItemTiers(int level, int durability, float miningSpeed, float damage, int enchantability, Supplier<Ingredient> repairIngredient) {
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

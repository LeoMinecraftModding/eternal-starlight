package cn.leolezury.eternalstarlight.common.item.weapon;

import cn.leolezury.eternalstarlight.common.registry.ESItems;
import com.google.common.base.Suppliers;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.block.Block;

import java.util.Objects;
import java.util.function.Supplier;

public enum ESItemTiers implements Tier {
    AMARAMBER(BlockTags.INCORRECT_FOR_IRON_TOOL, 250, 6.0F, 2.0F, 14, () -> Ingredient.of(ESItems.AMARAMBER_INGOT.get())),
    AETHERSENT(BlockTags.INCORRECT_FOR_IRON_TOOL, 400, 6.0F, 1.0F, 22, () -> Ingredient.of(ESItems.AETHERSENT_INGOT.get())),
    THERMAL_SPRINGSTONE(BlockTags.INCORRECT_FOR_IRON_TOOL, 400, 6.0F, 2.0F, 10, () -> Ingredient.of(ESItems.THERMAL_SPRINGSTONE_INGOT.get())),
    GLACITE(BlockTags.INCORRECT_FOR_IRON_TOOL, 500, 6.0F, 2.5F, 10, () -> Ingredient.of(ESItems.GLACITE_SHARD.get())),
    SWAMP_SILVER(BlockTags.INCORRECT_FOR_DIAMOND_TOOL, 800, 12.0F, 2.0F, 10, () -> Ingredient.of(ESItems.SWAMP_SILVER_INGOT.get())),
    DOOMEDEN(BlockTags.INCORRECT_FOR_DIAMOND_TOOL, 2000, 7.5F, 2.5F, 10, () -> Ingredient.of(ESItems.BROKEN_DOOMEDEN_BONE.get())),
    PETAL(BlockTags.INCORRECT_FOR_DIAMOND_TOOL, 1500, 7.5F, 3.5F, 22, () -> Ingredient.of(ESItems.TENACIOUS_PETAL.get())),
    AURORA_DEER_ANTLER(BlockTags.INCORRECT_FOR_IRON_TOOL, 400, 6.0F, 1.0F, 22, () -> Ingredient.EMPTY),
    TOOTH_OF_HUNGER(BlockTags.INCORRECT_FOR_IRON_TOOL, 250, 6.0F, 2.5F, 14, () -> Ingredient.of(ESItems.TOOTH_OF_HUNGER.get()));

    private final TagKey<Block> incorrectBlocksForDrops;
    private final int uses;
    private final float speed;
    private final float damage;
    private final int enchantmentValue;
    private final Supplier<Ingredient> repairIngredient;

    ESItemTiers(final TagKey<Block> incorrect, final int uses, final float speed, final float damage, final int enchantmentValue, final Supplier<Ingredient> supplier) {
        this.incorrectBlocksForDrops = incorrect;
        this.uses = uses;
        this.speed = speed;
        this.damage = damage;
        this.enchantmentValue = enchantmentValue;
        Objects.requireNonNull(supplier);
        this.repairIngredient = Suppliers.memoize(supplier::get);
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

    public TagKey<Block> getIncorrectBlocksForDrops() {
        return this.incorrectBlocksForDrops;
    }

    public int getEnchantmentValue() {
        return this.enchantmentValue;
    }

    public Ingredient getRepairIngredient() {
        return this.repairIngredient.get();
    }
}

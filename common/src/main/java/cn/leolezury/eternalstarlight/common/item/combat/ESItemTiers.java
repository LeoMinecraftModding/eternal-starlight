package cn.leolezury.eternalstarlight.common.item.combat;

import cn.leolezury.eternalstarlight.common.registry.ESItems;
import cn.leolezury.eternalstarlight.common.util.ESConventionalTags;
import com.google.common.base.Suppliers;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.block.Block;

import java.util.Objects;
import java.util.function.Supplier;

public enum ESItemTiers implements Tier {
	AETHERSENT(BlockTags.INCORRECT_FOR_DIAMOND_TOOL, 1750, 12.0F, 1.0F, 22, () -> Ingredient.of(ESConventionalTags.Items.INGOTS_AETHERSENT)),
	THERMAL_SPRINGSTONE(BlockTags.INCORRECT_FOR_IRON_TOOL, 400, 7.0F, 2.0F, 10, () -> Ingredient.of(ESConventionalTags.Items.INGOTS_THERMAL_SPRINGSTONE)),
	GLACITE(BlockTags.INCORRECT_FOR_IRON_TOOL, 500, 7.0F, 2.5F, 10, () -> Ingredient.of(ESConventionalTags.Items.GEMS_GLACITE)),
	STARLIT_DIAMOND(BlockTags.INCORRECT_FOR_DIAMOND_TOOL, 2500, 9.0F, 4.0F, 20, () -> Ingredient.of(ESConventionalTags.Items.GEMS_STARLIT_DIAMOND)),
	DEEPSILVER(BlockTags.INCORRECT_FOR_IRON_TOOL, 800, 12.0F, 2.0F, 10, () -> Ingredient.of(ESConventionalTags.Items.INGOTS_DEEPSILVER)),
	UNREALIUM(BlockTags.INCORRECT_FOR_DIAMOND_TOOL, 1200, 15.0F, 4.0F, 22, () -> Ingredient.of(ESConventionalTags.Items.INGOTS_UNREALIUM)),
	MALARITE(BlockTags.INCORRECT_FOR_IRON_TOOL, 400, 6.0F, 2.0F, 10, () -> Ingredient.of(ESConventionalTags.Items.GEMS_MALARITE)),
	PUNGENCY_FRUIT(BlockTags.INCORRECT_FOR_IRON_TOOL, 600, 8.0F, 2.5F, 12, () -> Ingredient.of(ESItems.PUNGENCY_FRUIT.get())),
	STARFIRE(BlockTags.INCORRECT_FOR_DIAMOND_TOOL, 1000, 8.5F, 2.5F, 15, () -> Ingredient.of(ESItems.STARFIRE.get())),
	FLOWGLAZE(BlockTags.INCORRECT_FOR_DIAMOND_TOOL, 1000, 8.5F, 2.5F, 15, () -> Ingredient.of(ESItems.FLOWGLAZE.get())),
	AMARAMBER(BlockTags.INCORRECT_FOR_IRON_TOOL, 250, 6.0F, 2.0F, 14, () -> Ingredient.of(ESConventionalTags.Items.INGOTS_AMARAMBER)),
	AURORA_DEER_ANTLER(BlockTags.INCORRECT_FOR_IRON_TOOL, 400, 6.0F, 1.0F, 22, () -> Ingredient.EMPTY),
	TENTACLE_SPIKE(BlockTags.INCORRECT_FOR_IRON_TOOL, 200, 6.0F, 1.5F, 14, () -> Ingredient.EMPTY),
	TOOTH_OF_HUNGER(BlockTags.INCORRECT_FOR_IRON_TOOL, 250, 6.0F, 2.5F, 14, () -> Ingredient.of(ESItems.TOOTH_OF_HUNGER.get())),
	GLISTERING(BlockTags.INCORRECT_FOR_IRON_TOOL, 8000, 6.0F, 2.0F, 14, () -> Ingredient.of(ESConventionalTags.Items.INGOTS_DEEPSILVER)),
	GOLEM_STEEL(BlockTags.INCORRECT_FOR_DIAMOND_TOOL, 1000, 7.5F, 3.0F, 22, () -> Ingredient.of(ESConventionalTags.Items.INGOTS_GOLEM_STEEL)),
	PETAL(BlockTags.INCORRECT_FOR_DIAMOND_TOOL, 1500, 7.5F, 3.5F, 22, () -> Ingredient.of(ESItems.TENACIOUS_PETAL.get(), ESItems.TENACIOUS_VINE.get())),
	DOOMEDEN(BlockTags.INCORRECT_FOR_DIAMOND_TOOL, 2000, 7.5F, 2.5F, 10, () -> Ingredient.of(ESItems.BROKEN_DOOMEDEN_BONE.get()));

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

	@Override
	public int getUses() {
		return this.uses;
	}

	@Override
	public float getSpeed() {
		return this.speed;
	}

	@Override
	public float getAttackDamageBonus() {
		return this.damage;
	}

	@Override
	public TagKey<Block> getIncorrectBlocksForDrops() {
		return this.incorrectBlocksForDrops;
	}

	@Override
	public int getEnchantmentValue() {
		return this.enchantmentValue;
	}

	@Override
	public Ingredient getRepairIngredient() {
		return this.repairIngredient.get();
	}
}

package cn.leolezury.eternalstarlight.common.item.recipe;

import cn.leolezury.eternalstarlight.common.util.ESTags;
import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.AdvancementRequirements;
import net.minecraft.advancements.AdvancementRewards;
import net.minecraft.advancements.critereon.InventoryChangeTrigger;
import net.minecraft.advancements.critereon.ItemPredicate;
import net.minecraft.advancements.critereon.RecipeUnlockedTrigger;
import net.minecraft.core.NonNullList;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.util.valueproviders.ConstantInt;
import net.minecraft.util.valueproviders.IntProvider;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.ItemLike;

import java.util.LinkedHashMap;
import java.util.Map;

public class AlloyRecipeBuilder {
	private final NonNullList<AlloyRecipe.Result> results = NonNullList.create();
	private final NonNullList<Ingredient> ingredients = NonNullList.create();
	private final int burnTime;
	private final Map<String, ItemPredicate> itemPredicates = new LinkedHashMap<>();

	public AlloyRecipeBuilder(ItemLike result, IntProvider amount, int burnTime) {
		this(result.asItem().getDefaultInstance(), amount, burnTime);
	}

	public AlloyRecipeBuilder(ItemStack result, IntProvider amount, int burnTime) {
		this.results.add(new AlloyRecipe.Result(result, amount));
		this.burnTime = burnTime;
	}

	public static AlloyRecipeBuilder alloy(ItemStack result, int burnTime) {
		return new AlloyRecipeBuilder(result, ConstantInt.of(1), burnTime);
	}

	public static AlloyRecipeBuilder alloy(ItemStack result, int count, int burnTime) {
		return new AlloyRecipeBuilder(result, ConstantInt.of(count), burnTime);
	}

	public static AlloyRecipeBuilder alloy(ItemStack result, IntProvider amount, int burnTime) {
		return new AlloyRecipeBuilder(result, amount, burnTime);
	}

	public AlloyRecipeBuilder result(ItemStack result) {
		return result(result, 1);
	}

	public AlloyRecipeBuilder result(ItemStack result, int count) {
		return result(result, ConstantInt.of(count));
	}

	public AlloyRecipeBuilder result(ItemStack result, IntProvider amount) {
		this.results.add(new AlloyRecipe.Result(result, amount));
		return this;
	}

	public AlloyRecipeBuilder requires(TagKey<Item> tag) {
		return this.requires(Ingredient.of(tag));
	}

	public AlloyRecipeBuilder requires(TagKey<Item> tag, int quantity) {
		for (int i = 0; i < quantity; i++) {
			this.requires(Ingredient.of(tag));
		}
		return this;
	}

	public AlloyRecipeBuilder requires(ItemLike item) {
		return this.requires(item, 1);
	}

	public AlloyRecipeBuilder requires(ItemLike item, int quantity) {
		for (int i = 0; i < quantity; i++) {
			this.requires(Ingredient.of(item));
		}
		return this;
	}

	public AlloyRecipeBuilder requires(Ingredient ingredient) {
		return this.requires(ingredient, 1);
	}

	public AlloyRecipeBuilder requires(Ingredient ingredient, int quantity) {
		for (int i = 0; i < quantity; i++) {
			this.ingredients.add(ingredient);
		}

		return this;
	}

	public AlloyRecipeBuilder unlockedBy(String name, Item item) {
		return unlockedBy(name, ItemPredicate.Builder.item().of(item).build());
	}

	public AlloyRecipeBuilder unlockedBy(String name, TagKey<Item> tag) {
		return unlockedBy(name, ItemPredicate.Builder.item().of(tag).build());
	}

	public AlloyRecipeBuilder unlockedBy(String name, ItemPredicate itemPredicate) {
		this.itemPredicates.put(name, itemPredicate);
		return this;
	}

	public void save(RecipeOutput recipeOutput, ResourceLocation id) {
		this.ensureValid(id);
		Advancement.Builder advancementBuilder = recipeOutput.advancement()
			.addCriterion("has_the_recipe", RecipeUnlockedTrigger.unlocked(id))
			.rewards(AdvancementRewards.Builder.recipe(id))
			.requirements(AdvancementRequirements.Strategy.OR);
		this.itemPredicates.forEach((name, predicate) -> advancementBuilder.addCriterion(name, InventoryChangeTrigger.TriggerInstance.hasItems(predicate, ItemPredicate.Builder.item().of(ESTags.Items.ALLOY_FURNACES).build())));
		AlloyRecipe recipe = new AlloyRecipe(this.results, this.ingredients, this.burnTime);
		recipeOutput.accept(id, recipe, advancementBuilder.build(id.withPrefix("recipes/alloy/")));
	}

	private void ensureValid(ResourceLocation id) {
		if (this.itemPredicates.isEmpty()) {
			throw new IllegalStateException("No way of obtaining alloy recipe " + id);
		}
		if (this.results.size() > 3) {
			throw new IllegalStateException("Too many results for alloy recipe " + id);
		}
	}
}

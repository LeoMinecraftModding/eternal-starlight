package cn.leolezury.eternalstarlight.common.compat.jei.category;

import cn.leolezury.eternalstarlight.common.EternalStarlight;
import cn.leolezury.eternalstarlight.common.item.recipe.EtherConversionRecipe;
import cn.leolezury.eternalstarlight.common.registry.ESItems;
import cn.leolezury.eternalstarlight.common.registry.ESRecipes;
import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.gui.builder.IRecipeSlotBuilder;
import mezz.jei.api.gui.ingredient.IRecipeSlotDrawable;
import mezz.jei.api.gui.widgets.IRecipeExtrasBuilder;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.recipe.IFocusGroup;
import mezz.jei.api.recipe.RecipeIngredientRole;
import mezz.jei.api.recipe.RecipeType;
import mezz.jei.api.recipe.category.AbstractRecipeCategory;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeHolder;

import java.text.DecimalFormat;
import java.util.Arrays;
import java.util.List;

public class EtherConversionCategory extends AbstractRecipeCategory<RecipeHolder<EtherConversionRecipe>> {
	public static final RecipeType<RecipeHolder<EtherConversionRecipe>> ETHER_CONVERSION = RecipeType.createFromVanilla(ESRecipes.ETHER_CONVERSION.get());

	public EtherConversionCategory(IGuiHelper guiHelper) {
		super(
			ETHER_CONVERSION,
			Component.translatable("gui." + EternalStarlight.ID + ".jei.category.ether_conversion"),
			guiHelper.createDrawableItemLike(ESItems.ETHER_BUCKET.get()),
			144,
			44
		);
	}

	@Override
	public void setRecipe(IRecipeLayoutBuilder builder, RecipeHolder<EtherConversionRecipe> recipeHolder, IFocusGroup focuses) {
		EtherConversionRecipe recipe = recipeHolder.value();

		List<ItemStack> ingredients = Arrays.asList(recipe.input().getItems());
		ingredients.forEach(stack -> stack.applyComponents(recipe.components().asPatch()));

		builder.addInputSlot(1, 14)
			.setStandardSlotBackground()
			.addItemStacks(ingredients);

		int totalWeight = 0;
		for (EtherConversionRecipe.WeightedOutput output : recipe.outputs()) {
			totalWeight += output.weight();
		}

		for (EtherConversionRecipe.WeightedOutput output : recipe.outputs()) {
			IRecipeSlotBuilder slot = builder.addSlot(RecipeIngredientRole.OUTPUT)
				.addItemStack(output.output());
			if (totalWeight > 0 && output.weight() < totalWeight) {
				String chance = new DecimalFormat("0.##").format(output.weight() / (float) totalWeight * 100.0F);
				slot.addRichTooltipCallback((recipeSlotView, tooltip) -> tooltip.add(Component.translatable(
					"gui." + EternalStarlight.ID + ".jei.category.ether_conversion.chance", chance
				)));
			}
		}
	}

	@Override
	public void createRecipeExtras(IRecipeExtrasBuilder builder, RecipeHolder<EtherConversionRecipe> recipeHolder, IFocusGroup focuses) {
		builder.addRecipeArrowWidget().setPosition(26, 14);

		List<IRecipeSlotDrawable> outputs = builder.getRecipeSlots().getSlots(RecipeIngredientRole.OUTPUT);
		builder.addScrollGridWidget(outputs, 4, 2)
			.setPosition(57, 5);
	}
}

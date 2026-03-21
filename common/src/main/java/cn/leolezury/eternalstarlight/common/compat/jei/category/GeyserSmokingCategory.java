package cn.leolezury.eternalstarlight.common.compat.jei.category;

import cn.leolezury.eternalstarlight.common.EternalStarlight;
import cn.leolezury.eternalstarlight.common.item.recipe.GeyserSmokingRecipe;
import cn.leolezury.eternalstarlight.common.registry.ESItems;
import cn.leolezury.eternalstarlight.common.registry.ESRecipes;
import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.gui.widgets.IRecipeExtrasBuilder;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.recipe.IFocusGroup;
import mezz.jei.api.recipe.RecipeType;
import mezz.jei.api.recipe.category.AbstractRecipeCategory;
import mezz.jei.library.util.RecipeUtil;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeHolder;

public class GeyserSmokingCategory extends AbstractRecipeCategory<RecipeHolder<GeyserSmokingRecipe>> {
	public static final RecipeType<RecipeHolder<GeyserSmokingRecipe>> GEYSER_SMOKING = RecipeType.createFromVanilla(ESRecipes.GEYSER_SMOKING.get());

	public GeyserSmokingCategory(IGuiHelper guiHelper) {
		super(
			GEYSER_SMOKING,
			Component.translatable("gui." + EternalStarlight.ID + ".jei.category.geyser_smoking"),
			guiHelper.createDrawableItemLike(ESItems.ABYSSAL_GEYSER.get()),
			82,
			34
		);
	}

	@Override
	public void setRecipe(IRecipeLayoutBuilder builder, RecipeHolder<GeyserSmokingRecipe> recipeHolder, IFocusGroup focuses) {
		GeyserSmokingRecipe recipe = recipeHolder.value();

		builder.addInputSlot(1, 9)
			.setStandardSlotBackground()
			.addItemStack(new ItemStack(recipe.input(), recipe.inputCount()));

		builder.addOutputSlot(61, 9)
			.setOutputSlotBackground()
			.addItemStack(RecipeUtil.getResultItem(recipe));
	}

	@Override
	public void createRecipeExtras(IRecipeExtrasBuilder builder, RecipeHolder<GeyserSmokingRecipe> recipeHolder, IFocusGroup focuses) {
		builder.addRecipeArrow().setPosition(26, 9);
	}
}

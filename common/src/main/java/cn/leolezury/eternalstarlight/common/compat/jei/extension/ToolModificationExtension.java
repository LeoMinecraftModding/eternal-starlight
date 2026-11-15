package cn.leolezury.eternalstarlight.common.compat.jei.extension;

import cn.leolezury.eternalstarlight.common.EternalStarlight;
import cn.leolezury.eternalstarlight.common.item.recipe.ToolModificationRecipe;
import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.gui.builder.IRecipeSlotBuilder;
import mezz.jei.api.gui.ingredient.ICraftingGridHelper;
import mezz.jei.api.recipe.IFocusGroup;
import mezz.jei.api.recipe.category.extensions.vanilla.crafting.ICraftingCategoryExtension;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.crafting.RecipeHolder;

import java.util.List;

public class ToolModificationExtension implements ICraftingCategoryExtension<ToolModificationRecipe> {
	@Override
	public void setRecipe(RecipeHolder<ToolModificationRecipe> recipeHolder, IRecipeLayoutBuilder builder, ICraftingGridHelper craftingGridHelper, IFocusGroup focuses) {
		ToolModificationRecipe recipe = recipeHolder.value();

		List<IRecipeSlotBuilder> inputs = craftingGridHelper.createAndSetInputs(builder, VanillaTypes.ITEM_STACK,
			List.of(List.of(recipe.tool().getDefaultInstance()), List.of(recipe.input().getDefaultInstance())), 3, 3);
		inputs.getFirst().addRichTooltipCallback((slotView, tooltip) -> tooltip.add(
			Component.translatable("gui." + EternalStarlight.ID + ".jei.tool_modification.damaged").withStyle(ChatFormatting.GRAY)
		));

		craftingGridHelper.createAndSetOutputs(builder, List.of(recipe.output()));
	}
}

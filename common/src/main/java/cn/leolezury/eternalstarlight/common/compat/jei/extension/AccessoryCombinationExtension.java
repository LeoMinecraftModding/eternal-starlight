package cn.leolezury.eternalstarlight.common.compat.jei.extension;

import cn.leolezury.eternalstarlight.common.item.recipe.AccessoryCombinationRecipe;
import cn.leolezury.eternalstarlight.common.util.ESAccessoryUtil;
import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.gui.ingredient.ICraftingGridHelper;
import mezz.jei.api.gui.ingredient.IRecipeSlotDrawable;
import mezz.jei.api.recipe.IFocus;
import mezz.jei.api.recipe.IFocusGroup;
import mezz.jei.api.recipe.RecipeIngredientRole;
import mezz.jei.api.recipe.category.extensions.vanilla.crafting.ICraftingCategoryExtension;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeHolder;

import java.util.List;

public class AccessoryCombinationExtension implements ICraftingCategoryExtension<AccessoryCombinationRecipe> {
	@Override
	public void setRecipe(RecipeHolder<AccessoryCombinationRecipe> recipeHolder, IRecipeLayoutBuilder builder, ICraftingGridHelper craftingGridHelper, IFocusGroup focuses) {
		List<ItemStack> accessories = ESAccessoryUtil.getAccessoryItems();
		ItemStack accessory = focusedAccessory(focuses, accessories);
		List<ItemStack> targets = ESAccessoryUtil.getCombinationTargets(accessory);

		craftingGridHelper.createAndSetInputs(builder, VanillaTypes.ITEM_STACK, List.of(List.of(accessory), targets), 3, 3);
		builder.addInvisibleIngredients(RecipeIngredientRole.INPUT).addItemStacks(accessories);
		builder.addInvisibleIngredients(RecipeIngredientRole.INPUT).addItemStacks(ESAccessoryUtil.getAllCombinationTargets());

		craftingGridHelper.createAndSetOutputs(builder, List.of(firstResult(accessory, targets)));
	}

	@Override
	public void onDisplayedIngredientsUpdate(RecipeHolder<AccessoryCombinationRecipe> recipeHolder, List<IRecipeSlotDrawable> recipeSlots, IFocusGroup focuses) {
		List<IRecipeSlotDrawable> inputs = recipeSlots.stream()
			.filter(slot -> slot.getRole() == RecipeIngredientRole.INPUT)
			.toList();
		IRecipeSlotDrawable output = recipeSlots.stream()
			.filter(slot -> slot.getRole() == RecipeIngredientRole.OUTPUT)
			.findFirst()
			.orElse(null);
		if (inputs.size() < 2 || output == null) {
			return;
		}
		ItemStack accessory = inputs.get(0).getDisplayedItemStack().orElse(ItemStack.EMPTY);
		ItemStack equipment = inputs.get(1).getDisplayedItemStack().orElse(ItemStack.EMPTY);
		ItemStack result = ESAccessoryUtil.copyAndApplyAccessory(equipment, accessory);
		if (!result.isEmpty()) {
			output.createDisplayOverrides().addItemStack(result);
		}
	}

	private static ItemStack focusedAccessory(IFocusGroup focuses, List<ItemStack> accessories) {
		for (IFocus<ItemStack> focus : focuses.getItemStackFocuses().toList()) {
			ItemStack focused = focus.getTypedValue().getIngredient();
			for (ItemStack accessory : accessories) {
				if (accessory.is(focused.getItem())) {
					return accessory;
				}
			}
			for (ItemStack accessory : accessories) {
				if (ESAccessoryUtil.getCombinationTargets(accessory).stream().anyMatch(target -> target.is(focused.getItem()))) {
					return accessory;
				}
			}
		}
		return accessories.isEmpty() ? ItemStack.EMPTY : accessories.getFirst();
	}

	private static ItemStack firstResult(ItemStack accessory, List<ItemStack> targets) {
		return targets.isEmpty() ? ItemStack.EMPTY : ESAccessoryUtil.copyAndApplyAccessory(targets.getFirst(), accessory);
	}
}

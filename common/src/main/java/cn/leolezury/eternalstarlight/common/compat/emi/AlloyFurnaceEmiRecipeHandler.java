package cn.leolezury.eternalstarlight.common.compat.emi;

import cn.leolezury.eternalstarlight.common.item.menu.AlloyFurnaceMenu;
import dev.emi.emi.api.recipe.EmiRecipe;
import dev.emi.emi.api.recipe.EmiRecipeCategory;
import dev.emi.emi.api.recipe.VanillaEmiRecipeCategories;
import dev.emi.emi.api.recipe.handler.StandardRecipeHandler;
import net.minecraft.world.inventory.Slot;

import java.util.ArrayList;
import java.util.List;

/**
 * Lets EMI's fill button move ingredients into the alloy furnace, for the alloy grid as well as the
 * single fuel and cooling slots.
 */
public class AlloyFurnaceEmiRecipeHandler implements StandardRecipeHandler<AlloyFurnaceMenu> {
	@Override
	public List<Slot> getInputSources(AlloyFurnaceMenu menu) {
		List<Slot> slots = new ArrayList<>();
		for (int i = AlloyFurnaceMenu.INV_SLOT_START; i < AlloyFurnaceMenu.USE_ROW_SLOT_END; i++) {
			slots.add(menu.getSlot(i));
		}
		for (int i = AlloyFurnaceMenu.INGREDIENT_SLOT_START; i < AlloyFurnaceMenu.INGREDIENT_SLOT_END; i++) {
			slots.add(menu.getSlot(i));
		}
		slots.add(menu.getSlot(AlloyFurnaceMenu.FUEL_SLOT));
		slots.add(menu.getSlot(AlloyFurnaceMenu.COOLING_SLOT));
		return slots;
	}

	@Override
	public List<Slot> getCraftingSlots(AlloyFurnaceMenu menu) {
		List<Slot> slots = new ArrayList<>();
		for (int i = AlloyFurnaceMenu.INGREDIENT_SLOT_START; i < AlloyFurnaceMenu.INGREDIENT_SLOT_END; i++) {
			slots.add(menu.getSlot(i));
		}
		return slots;
	}

	@Override
	public List<Slot> getCraftingSlots(EmiRecipe recipe, AlloyFurnaceMenu menu) {
		EmiRecipeCategory category = recipe.getCategory();
		if (category == VanillaEmiRecipeCategories.FUEL) {
			return List.of(menu.getSlot(AlloyFurnaceMenu.FUEL_SLOT));
		}
		if (category == ESEmiPlugin.ALLOY_FURNACE_COOLING) {
			return List.of(menu.getSlot(AlloyFurnaceMenu.COOLING_SLOT));
		}
		return getCraftingSlots(menu);
	}

	@Override
	public boolean supportsRecipe(EmiRecipe recipe) {
		EmiRecipeCategory category = recipe.getCategory();
		return category == ESEmiPlugin.ALLOY
			|| category == ESEmiPlugin.ALLOY_FURNACE_COOLING
			|| category == VanillaEmiRecipeCategories.FUEL;
	}

	@Override
	public boolean alwaysDisplaySupport(EmiRecipe recipe) {
		return false;
	}
}

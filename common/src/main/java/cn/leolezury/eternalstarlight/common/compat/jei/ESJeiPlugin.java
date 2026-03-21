package cn.leolezury.eternalstarlight.common.compat.jei;

import cn.leolezury.eternalstarlight.common.EternalStarlight;
import cn.leolezury.eternalstarlight.common.block.AlloyFurnaceBlock;
import cn.leolezury.eternalstarlight.common.client.gui.screen.AlloyFurnaceScreen;
import cn.leolezury.eternalstarlight.common.compat.jei.category.AlloyCategory;
import cn.leolezury.eternalstarlight.common.compat.jei.category.AlloyFurnaceCoolingCategory;
import cn.leolezury.eternalstarlight.common.compat.jei.category.DryingCategory;
import cn.leolezury.eternalstarlight.common.compat.jei.category.GeyserSmokingCategory;
import cn.leolezury.eternalstarlight.common.compat.jei.recipe.AlloyFurnaceCoolingRecipe;
import cn.leolezury.eternalstarlight.common.item.menu.AlloyFurnaceMenu;
import cn.leolezury.eternalstarlight.common.registry.ESBlocks;
import cn.leolezury.eternalstarlight.common.registry.ESMenuTypes;
import cn.leolezury.eternalstarlight.common.registry.ESRecipes;
import mezz.jei.api.IModPlugin;
import mezz.jei.api.JeiPlugin;
import mezz.jei.api.constants.RecipeTypes;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.helpers.IJeiHelpers;
import mezz.jei.api.registration.*;
import net.minecraft.client.Minecraft;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.RecipeManager;

@JeiPlugin
public class ESJeiPlugin implements IModPlugin {
	@Override
	public void registerCategories(IRecipeCategoryRegistration registration) {
		IJeiHelpers jeiHelpers = registration.getJeiHelpers();
		IGuiHelper guiHelper = jeiHelpers.getGuiHelper();
		registration.addRecipeCategories(
			new GeyserSmokingCategory(guiHelper),
			new DryingCategory(guiHelper),
			new AlloyCategory(guiHelper),
			new AlloyFurnaceCoolingCategory(guiHelper)
		);
	}

	@Override
	public void registerRecipes(IRecipeRegistration registration) {
		if (Minecraft.getInstance().level != null) {
			RecipeManager manager = Minecraft.getInstance().level.getRecipeManager();
			registration.addRecipes(GeyserSmokingCategory.GEYSER_SMOKING, manager.getAllRecipesFor(ESRecipes.GEYSER_SMOKING.get()));
			registration.addRecipes(DryingCategory.DRYING, manager.getAllRecipesFor(ESRecipes.DRYING.get()));
			registration.addRecipes(AlloyCategory.ALLOY, manager.getAllRecipesFor(ESRecipes.ALLOY.get()));
		}
		registration.addRecipes(AlloyFurnaceCoolingCategory.ALLOY_FURNACE_COOLING, AlloyFurnaceBlock.getCoolingRegistry().entrySet().stream().map(entry -> new AlloyFurnaceCoolingRecipe(entry.getKey(), entry.getValue())).toList());
	}

	@Override
	public void registerGuiHandlers(IGuiHandlerRegistration registration) {
		registration.addRecipeClickArea(AlloyFurnaceScreen.class, 88, 14, 28, 23, AlloyCategory.ALLOY, RecipeTypes.FUELING, AlloyFurnaceCoolingCategory.ALLOY_FURNACE_COOLING);
	}

	@Override
	public void registerRecipeTransferHandlers(IRecipeTransferRegistration registration) {
		registration.addRecipeTransferHandler(AlloyFurnaceMenu.class, ESMenuTypes.ALLOY_FURNACE.get(), AlloyCategory.ALLOY, AlloyFurnaceMenu.INGREDIENT_SLOT_START, 9, AlloyFurnaceMenu.INV_SLOT_START, 36);
		registration.addRecipeTransferHandler(AlloyFurnaceMenu.class, ESMenuTypes.ALLOY_FURNACE.get(), RecipeTypes.FUELING, AlloyFurnaceMenu.FUEL_SLOT, 1, AlloyFurnaceMenu.INV_SLOT_START, 36);
		registration.addRecipeTransferHandler(AlloyFurnaceMenu.class, ESMenuTypes.ALLOY_FURNACE.get(), AlloyFurnaceCoolingCategory.ALLOY_FURNACE_COOLING, AlloyFurnaceMenu.COOLING_SLOT, 1, AlloyFurnaceMenu.INV_SLOT_START, 36);
	}

	@Override
	public void registerRecipeCatalysts(IRecipeCatalystRegistration registration) {
		registration.addRecipeCatalysts(GeyserSmokingCategory.GEYSER_SMOKING,
			ESBlocks.ABYSSAL_GEYSER.get(),
			ESBlocks.THERMABYSSAL_GEYSER.get(),
			ESBlocks.CRYOBYSSAL_GEYSER.get()
		);
		registration.addRecipeCatalysts(DryingCategory.DRYING,
			ESBlocks.DRYING_RACK.get()
		);
		registration.addRecipeCatalysts(AlloyCategory.ALLOY,
			ESBlocks.ALLOY_FURNACE.get(),
			ESBlocks.WAXED_ALLOY_FURNACE.get(),
			ESBlocks.OXIDIZED_ALLOY_FURNACE.get()
		);
		registration.addRecipeCatalysts(RecipeTypes.FUELING,
			ESBlocks.ALLOY_FURNACE.get(),
			ESBlocks.WAXED_ALLOY_FURNACE.get(),
			ESBlocks.OXIDIZED_ALLOY_FURNACE.get()
		);
		registration.addRecipeCatalysts(AlloyFurnaceCoolingCategory.ALLOY_FURNACE_COOLING,
			ESBlocks.ALLOY_FURNACE.get(),
			ESBlocks.WAXED_ALLOY_FURNACE.get(),
			ESBlocks.OXIDIZED_ALLOY_FURNACE.get()
		);
	}

	@Override
	public ResourceLocation getPluginUid() {
		return EternalStarlight.id("jei_plugin");
	}
}

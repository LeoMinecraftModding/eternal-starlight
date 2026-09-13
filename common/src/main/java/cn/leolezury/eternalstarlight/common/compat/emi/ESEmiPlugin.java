package cn.leolezury.eternalstarlight.common.compat.emi;

import cn.leolezury.eternalstarlight.common.EternalStarlight;
import cn.leolezury.eternalstarlight.common.block.AlloyFurnaceBlock;
import cn.leolezury.eternalstarlight.common.compat.emi.recipe.*;
import cn.leolezury.eternalstarlight.common.registry.ESBlocks;
import cn.leolezury.eternalstarlight.common.registry.ESItems;
import cn.leolezury.eternalstarlight.common.registry.ESMenuTypes;
import cn.leolezury.eternalstarlight.common.registry.ESRecipes;
import dev.emi.emi.api.EmiEntrypoint;
import dev.emi.emi.api.EmiPlugin;
import dev.emi.emi.api.EmiRegistry;
import dev.emi.emi.api.recipe.EmiRecipeCategory;
import dev.emi.emi.api.recipe.VanillaEmiRecipeCategories;
import dev.emi.emi.api.render.EmiRenderable;
import dev.emi.emi.api.stack.EmiStack;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.crafting.RecipeManager;
import net.minecraft.world.level.ItemLike;

/**
 * Native EMI support. Registered through the {@code emi} entrypoint on Fabric and through
 * {@link EmiEntrypoint} on NeoForge; mirrors the categories of
 * {@link cn.leolezury.eternalstarlight.common.compat.jei.ESJeiPlugin} and reuses its translation keys.
 */
@EmiEntrypoint
public class ESEmiPlugin implements EmiPlugin {
	public static final EmiRecipeCategory GEYSER_SMOKING = category("geyser_smoking", EmiStack.of(ESItems.ABYSSAL_GEYSER.get()));
	public static final EmiRecipeCategory DRYING = category("drying", EmiStack.of(ESItems.DRYING_RACK.get()));
	public static final EmiRecipeCategory ALLOY = category("alloy", EmiStack.of(ESItems.ALLOY_FURNACE.get()));
	public static final EmiRecipeCategory ALLOY_FURNACE_COOLING = category("alloy_furnace_cooling", EmiStack.of(ESItems.FROZEN_TUBE.get()));
	public static final EmiRecipeCategory ETHER_CONVERSION = category("ether_conversion", EmiStack.of(ESItems.ETHER_BUCKET.get()));

	private static final ItemLike[] ALLOY_FURNACES = {
		ESBlocks.ALLOY_FURNACE.get(),
		ESBlocks.WAXED_ALLOY_FURNACE.get(),
		ESBlocks.OXIDIZED_ALLOY_FURNACE.get()
	};

	@Override
	public void register(EmiRegistry registry) {
		registry.addCategory(GEYSER_SMOKING);
		registry.addCategory(DRYING);
		registry.addCategory(ALLOY);
		registry.addCategory(ALLOY_FURNACE_COOLING);
		registry.addCategory(ETHER_CONVERSION);

		addWorkstations(registry, GEYSER_SMOKING,
			ESBlocks.ABYSSAL_GEYSER.get(),
			ESBlocks.THERMABYSSAL_GEYSER.get(),
			ESBlocks.CRYOBYSSAL_GEYSER.get()
		);
		addWorkstations(registry, DRYING, ESBlocks.DRYING_RACK.get());
		addWorkstations(registry, ALLOY, ALLOY_FURNACES);
		addWorkstations(registry, ALLOY_FURNACE_COOLING, ALLOY_FURNACES);
		addWorkstations(registry, VanillaEmiRecipeCategories.FUEL, ALLOY_FURNACES);
		addWorkstations(registry, ETHER_CONVERSION, ESItems.ETHER_BUCKET.get());

		RecipeManager manager = registry.getRecipeManager();
		manager.getAllRecipesFor(ESRecipes.GEYSER_SMOKING.get()).forEach(holder -> registry.addRecipe(new GeyserSmokingEmiRecipe(holder)));
		manager.getAllRecipesFor(ESRecipes.DRYING.get()).forEach(holder -> registry.addRecipe(new DryingEmiRecipe(holder)));
		manager.getAllRecipesFor(ESRecipes.ALLOY.get()).forEach(holder -> registry.addRecipe(new AlloyEmiRecipe(holder)));
		AlloyFurnaceBlock.getCoolingRegistry().forEach((item, cooling) -> registry.addRecipe(new AlloyFurnaceCoolingEmiRecipe(item, cooling)));
		manager.getAllRecipesFor(ESRecipes.ETHER_CONVERSION.get()).forEach(holder -> registry.addRecipe(new EtherConversionEmiRecipe(holder)));

		registry.addRecipeHandler(ESMenuTypes.ALLOY_FURNACE.get(), new AlloyFurnaceEmiRecipeHandler());
	}

	private static void addWorkstations(EmiRegistry registry, EmiRecipeCategory category, ItemLike... workstations) {
		for (ItemLike workstation : workstations) {
			registry.addWorkstation(category, EmiStack.of(workstation));
		}
	}

	private static EmiRecipeCategory category(String path, EmiRenderable icon) {
		// the JEI keys are the ones Crowdin already translates, so both viewers share them
		Component name = Component.translatable("gui." + EternalStarlight.ID + ".jei.category." + path);
		return new EmiRecipeCategory(EternalStarlight.id(path), icon) {
			@Override
			public Component getName() {
				return name;
			}
		};
	}
}

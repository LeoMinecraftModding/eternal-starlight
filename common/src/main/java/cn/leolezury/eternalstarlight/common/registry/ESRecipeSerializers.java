package cn.leolezury.eternalstarlight.common.registry;

import cn.leolezury.eternalstarlight.common.EternalStarlight;
import cn.leolezury.eternalstarlight.common.item.recipe.*;
import cn.leolezury.eternalstarlight.common.platform.registry.RegistrationProvider;
import cn.leolezury.eternalstarlight.common.platform.registry.RegistryObject;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.SimpleCraftingRecipeSerializer;

public class ESRecipeSerializers {
	public static final RegistrationProvider<RecipeSerializer<?>> RECIPE_SERIALIZERS = RegistrationProvider.get(Registries.RECIPE_SERIALIZER, EternalStarlight.ID);

	public static final RegistryObject<RecipeSerializer<?>, RecipeSerializer<GeyserSmokingRecipe>> GEYSER_SMOKING = RECIPE_SERIALIZERS.register("geyser_smoking", GeyserSmokingRecipe.Serializer::new);
	public static final RegistryObject<RecipeSerializer<?>, RecipeSerializer<DryingRecipe>> DRYING = RECIPE_SERIALIZERS.register("drying", DryingRecipe.Serializer::new);
	public static final RegistryObject<RecipeSerializer<?>, RecipeSerializer<ManaCrystalRecipe>> MANA_CRYSTAL = RECIPE_SERIALIZERS.register("mana_crystal", ManaCrystalRecipe.Serializer::new);
	public static final RegistryObject<RecipeSerializer<?>, RecipeSerializer<ToolModificationRecipe>> TOOL_MODIFICATION = RECIPE_SERIALIZERS.register("tool_modification", ToolModificationRecipe.Serializer::new);
	public static final RegistryObject<RecipeSerializer<?>, RecipeSerializer<AccessoryCombinationRecipe>> ACCESSORY_COMBINATION = RECIPE_SERIALIZERS.register("accessory_combination", () -> new SimpleCraftingRecipeSerializer<>(AccessoryCombinationRecipe::new));
	public static final RegistryObject<RecipeSerializer<?>, RecipeSerializer<AlloyRecipe>> ALLOY = RECIPE_SERIALIZERS.register("alloy", AlloyRecipe.Serializer::new);

	public static void loadClass() {
	}
}

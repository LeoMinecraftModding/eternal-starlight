package cn.leolezury.eternalstarlight.common.registry;

import cn.leolezury.eternalstarlight.common.EternalStarlight;
import cn.leolezury.eternalstarlight.common.item.recipe.GeyserSmokingRecipe;
import cn.leolezury.eternalstarlight.common.platform.registry.RegistrationProvider;
import cn.leolezury.eternalstarlight.common.platform.registry.RegistryObject;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.crafting.RecipeType;

public class ESRecipes {
    public static final RegistrationProvider<RecipeType<?>> RECIPES = RegistrationProvider.get(Registries.RECIPE_TYPE, EternalStarlight.ID);

    public static final RegistryObject<RecipeType<?>, RecipeType<GeyserSmokingRecipe>> GEYSER_SMOKING = RECIPES.register("geyser_smoking", GeyserSmokingRecipe.Type::new);

    public static void loadClass() {}
}

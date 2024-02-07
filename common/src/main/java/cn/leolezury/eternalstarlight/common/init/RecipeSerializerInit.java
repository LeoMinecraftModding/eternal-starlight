package cn.leolezury.eternalstarlight.common.init;

import cn.leolezury.eternalstarlight.common.EternalStarlight;
import cn.leolezury.eternalstarlight.common.item.recipe.GeyserSmokingRecipe;
import cn.leolezury.eternalstarlight.common.item.recipe.ManaCrystalRecipe;
import cn.leolezury.eternalstarlight.common.platform.registry.RegistrationProvider;
import cn.leolezury.eternalstarlight.common.platform.registry.RegistryObject;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.crafting.RecipeSerializer;

public class RecipeSerializerInit {
    public static final RegistrationProvider<RecipeSerializer<?>> RECIPE_SERIALIZERS = RegistrationProvider.get(Registries.RECIPE_SERIALIZER, EternalStarlight.MOD_ID);

    public static final RegistryObject<RecipeSerializer<?>, RecipeSerializer<ManaCrystalRecipe>> MANA_CRYSTAL = RECIPE_SERIALIZERS.register("mana_crystal", ManaCrystalRecipe.Serializer::new);
    public static final RegistryObject<RecipeSerializer<?>, RecipeSerializer<GeyserSmokingRecipe>> GEYSER_SMOKING = RECIPE_SERIALIZERS.register("geyser_smoking", GeyserSmokingRecipe.Serializer::new);

    public static void loadClass() {}
}

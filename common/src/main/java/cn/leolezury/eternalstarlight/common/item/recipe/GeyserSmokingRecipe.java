package cn.leolezury.eternalstarlight.common.item.recipe;

import cn.leolezury.eternalstarlight.common.EternalStarlight;
import cn.leolezury.eternalstarlight.common.registry.ESRecipeSerializers;
import cn.leolezury.eternalstarlight.common.registry.ESRecipes;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.RegistryAccess;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.Container;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;

public record GeyserSmokingRecipe(Item input, Item output) implements Recipe<Container> {
    @Override
    public boolean matches(Container container, Level level) {
        return true;
    }

    @Override
    public ItemStack assemble(Container container, RegistryAccess registryAccess) {
        return ItemStack.EMPTY;
    }

    @Override
    public boolean canCraftInDimensions(int i, int j) {
        return true;
    }

    @Override
    public ItemStack getResultItem(RegistryAccess registryAccess) {
        return ItemStack.EMPTY;
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return ESRecipeSerializers.GEYSER_SMOKING.get();
    }

    @Override
    public RecipeType<?> getType() {
        return ESRecipes.GEYSER_SMOKING.get();
    }

    public static class Type implements RecipeType<GeyserSmokingRecipe> {
        public static final ResourceLocation ID = new ResourceLocation(EternalStarlight.MOD_ID, "geyser_smoking");

        @Override
        public String toString() {
            return ID.toString();
        }
    }

    public static class Serializer implements RecipeSerializer<GeyserSmokingRecipe> {
        private static final Codec<GeyserSmokingRecipe> CODEC = RecordCodecBuilder.create(instance -> instance.group(
                BuiltInRegistries.ITEM.byNameCodec().fieldOf("input").forGetter(GeyserSmokingRecipe::input),
                BuiltInRegistries.ITEM.byNameCodec().fieldOf("output").forGetter(GeyserSmokingRecipe::output)
        ).apply(instance, GeyserSmokingRecipe::new));

        @Override
        public Codec<GeyserSmokingRecipe> codec() {
            return CODEC;
        }

        @Override
        public GeyserSmokingRecipe fromNetwork(FriendlyByteBuf friendlyByteBuf) {
            Item input = friendlyByteBuf.readById(BuiltInRegistries.ITEM);
            Item output = friendlyByteBuf.readById(BuiltInRegistries.ITEM);
            return new GeyserSmokingRecipe(input, output);
        }

        @Override
        public void toNetwork(FriendlyByteBuf friendlyByteBuf, GeyserSmokingRecipe recipe) {
            friendlyByteBuf.writeId(BuiltInRegistries.ITEM, recipe.input());
            friendlyByteBuf.writeId(BuiltInRegistries.ITEM, recipe.output());
        }
    }
}

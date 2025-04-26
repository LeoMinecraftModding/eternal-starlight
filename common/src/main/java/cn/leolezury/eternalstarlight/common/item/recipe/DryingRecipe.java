package cn.leolezury.eternalstarlight.common.item.recipe;

import cn.leolezury.eternalstarlight.common.EternalStarlight;
import cn.leolezury.eternalstarlight.common.registry.ESRecipeSerializers;
import cn.leolezury.eternalstarlight.common.registry.ESRecipes;
import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.HolderLookup;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.*;
import net.minecraft.world.level.Level;

public record DryingRecipe(Ingredient input, ItemStack output, boolean fireBelow) implements Recipe<RecipeInput> {
	@Override
	public boolean matches(RecipeInput container, Level level) {
		return true;
	}

	@Override
	public ItemStack assemble(RecipeInput container, HolderLookup.Provider provider) {
		return ItemStack.EMPTY;
	}

	@Override
	public boolean canCraftInDimensions(int i, int j) {
		return true;
	}

	@Override
	public ItemStack getResultItem(HolderLookup.Provider provider) {
		return ItemStack.EMPTY;
	}

	@Override
	public RecipeSerializer<?> getSerializer() {
		return ESRecipeSerializers.DRYING.get();
	}

	@Override
	public RecipeType<?> getType() {
		return ESRecipes.DRYING.get();
	}

	public static class Type implements RecipeType<DryingRecipe> {
		public static final ResourceLocation ID = EternalStarlight.id("drying");

		@Override
		public String toString() {
			return ID.toString();
		}
	}

	public static class Serializer implements RecipeSerializer<DryingRecipe> {
		private static final MapCodec<DryingRecipe> CODEC = RecordCodecBuilder.mapCodec(instance -> instance.group(
			Ingredient.CODEC.fieldOf("input").forGetter(DryingRecipe::input),
			ItemStack.OPTIONAL_CODEC.fieldOf("output").forGetter(DryingRecipe::output),
			Codec.BOOL.fieldOf("fire_below").forGetter(DryingRecipe::fireBelow)
		).apply(instance, DryingRecipe::new));

		@Override
		public MapCodec<DryingRecipe> codec() {
			return CODEC;
		}

		@Override
		public StreamCodec<RegistryFriendlyByteBuf, DryingRecipe> streamCodec() {
			return new StreamCodec<>() {
				@Override
				public DryingRecipe decode(RegistryFriendlyByteBuf friendlyByteBuf) {
					Ingredient input = Ingredient.CONTENTS_STREAM_CODEC.decode(friendlyByteBuf);
					ItemStack output = ItemStack.STREAM_CODEC.decode(friendlyByteBuf);
					boolean fireBelow = friendlyByteBuf.readBoolean();
					return new DryingRecipe(input, output, fireBelow);
				}

				@Override
				public void encode(RegistryFriendlyByteBuf friendlyByteBuf, DryingRecipe recipe) {
					Ingredient.CONTENTS_STREAM_CODEC.encode(friendlyByteBuf, recipe.input());
					ItemStack.STREAM_CODEC.encode(friendlyByteBuf, recipe.output());
					friendlyByteBuf.writeBoolean(recipe.fireBelow());
				}
			};
		}
	}
}

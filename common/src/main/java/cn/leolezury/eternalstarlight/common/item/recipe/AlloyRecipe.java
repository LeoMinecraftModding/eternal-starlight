package cn.leolezury.eternalstarlight.common.item.recipe;

import cn.leolezury.eternalstarlight.common.EternalStarlight;
import cn.leolezury.eternalstarlight.common.registry.ESItems;
import cn.leolezury.eternalstarlight.common.registry.ESRecipeSerializers;
import cn.leolezury.eternalstarlight.common.registry.ESRecipes;
import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.NonNullList;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.RandomSource;
import net.minecraft.util.valueproviders.ConstantInt;
import net.minecraft.util.valueproviders.IntProvider;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.*;
import net.minecraft.world.level.Level;

public record AlloyRecipe(NonNullList<Result> results, NonNullList<Ingredient> ingredients, int burnTime) implements Recipe<CraftingInput> {
	@Override
	public RecipeSerializer<?> getSerializer() {
		return ESRecipeSerializers.ALLOY.get();
	}

	@Override
	public RecipeType<?> getType() {
		return ESRecipes.ALLOY.get();
	}

	public static class Type implements RecipeType<AlloyRecipe> {
		public static final ResourceLocation ID = EternalStarlight.id("alloy");

		@Override
		public String toString() {
			return ID.toString();
		}
	}

	@Override
	public ItemStack getToastSymbol() {
		return ESItems.ALLOY_FURNACE.get().getDefaultInstance();
	}

	@Override
	public ItemStack getResultItem(HolderLookup.Provider provider) {
		return this.results.getFirst().item();
	}

	@Override
	public NonNullList<Ingredient> getIngredients() {
		return this.ingredients;
	}

	@Override
	public boolean matches(CraftingInput craftingInput, Level level) {
		if (craftingInput.ingredientCount() != this.ingredients.size()) {
			return false;
		} else {
			return craftingInput.size() == 1 && this.ingredients.size() == 1 ? this.ingredients.getFirst().test(craftingInput.getItem(0)) : craftingInput.stackedContents().canCraft(this, null);
		}
	}

	@Override
	public ItemStack assemble(CraftingInput craftingInput, HolderLookup.Provider provider) {
		return this.results.getFirst().item().copy();
	}

	@Override
	public boolean canCraftInDimensions(int i, int j) {
		return i * j >= this.ingredients.size();
	}

	public record Result(ItemStack item, IntProvider amount) {
		public static final Codec<Result> CODEC = RecordCodecBuilder.create((instance) -> instance.group(
			ItemStack.STRICT_SINGLE_ITEM_CODEC.fieldOf("item").forGetter(Result::item),
			IntProvider.NON_NEGATIVE_CODEC.fieldOf("amount").forGetter(Result::amount)
		).apply(instance, Result::new));

		public static final StreamCodec<? super RegistryFriendlyByteBuf, Result> STREAM_CODEC = StreamCodec.composite(
			ItemStack.STREAM_CODEC, Result::item,
			ByteBufCodecs.fromCodec(IntProvider.NON_NEGATIVE_CODEC), Result::amount,
			Result::new
		);

		public ItemStack getResultItem(RandomSource random) {
			return item().copyWithCount(amount().sample(random));
		}

		public ItemStack getMaxResultItem() {
			return item().copyWithCount(amount().getMaxValue());
		}
	}

	public static class Serializer implements RecipeSerializer<AlloyRecipe> {
		private static final MapCodec<AlloyRecipe> CODEC = RecordCodecBuilder.mapCodec((instance) -> instance.group(
			Result.CODEC.listOf().fieldOf("results").flatXmap((list) -> {
				Result[] items = list.stream().filter((stack) -> !stack.item().isEmpty()).toArray(Result[]::new);
				if (items.length == 0) {
					return DataResult.error(() -> "No result for alloy recipe");
				} else {
					return items.length > 3 ? DataResult.error(() -> "Too many results for alloy recipe") : DataResult.success(NonNullList.of(new Result(ItemStack.EMPTY, ConstantInt.of(0)), items));
				}
			}, DataResult::success).forGetter(AlloyRecipe::results),
			Ingredient.CODEC_NONEMPTY.listOf().fieldOf("ingredients").flatXmap((list) -> {
				Ingredient[] ingredients = list.stream().filter((ingredient) -> !ingredient.isEmpty()).toArray(Ingredient[]::new);
				if (ingredients.length == 0) {
					return DataResult.error(() -> "No ingredient for alloy recipe");
				} else {
					return ingredients.length > 9 ? DataResult.error(() -> "Too many ingredients for alloy recipe") : DataResult.success(NonNullList.of(Ingredient.EMPTY, ingredients));
				}
			}, DataResult::success).forGetter(AlloyRecipe::ingredients),
			Codec.INT.fieldOf("burn_time").forGetter(AlloyRecipe::burnTime)
		).apply(instance, AlloyRecipe::new));
		public static final StreamCodec<RegistryFriendlyByteBuf, AlloyRecipe> STREAM_CODEC = StreamCodec.of(Serializer::toNetwork, Serializer::fromNetwork);

		@Override
		public MapCodec<AlloyRecipe> codec() {
			return CODEC;
		}

		@Override
		public StreamCodec<RegistryFriendlyByteBuf, AlloyRecipe> streamCodec() {
			return STREAM_CODEC;
		}

		private static AlloyRecipe fromNetwork(RegistryFriendlyByteBuf byteBuf) {
			int resultSize = byteBuf.readVarInt();
			NonNullList<Result> results = NonNullList.withSize(resultSize, new Result(ItemStack.EMPTY, ConstantInt.of(1)));
			results.replaceAll(result -> Result.STREAM_CODEC.decode(byteBuf));
			int ingredientSize = byteBuf.readVarInt();
			NonNullList<Ingredient> ingredients = NonNullList.withSize(ingredientSize, Ingredient.EMPTY);
			ingredients.replaceAll(ingredient -> Ingredient.CONTENTS_STREAM_CODEC.decode(byteBuf));
			int burnTime = byteBuf.readInt();
			return new AlloyRecipe(results, ingredients, burnTime);
		}

		private static void toNetwork(RegistryFriendlyByteBuf byteBuf, AlloyRecipe alloyRecipe) {
			byteBuf.writeVarInt(alloyRecipe.results.size());
			for (Result result : alloyRecipe.results) {
				Result.STREAM_CODEC.encode(byteBuf, result);
			}
			byteBuf.writeVarInt(alloyRecipe.ingredients.size());
			for (Ingredient ingredient : alloyRecipe.ingredients) {
				Ingredient.CONTENTS_STREAM_CODEC.encode(byteBuf, ingredient);
			}
			byteBuf.writeInt(alloyRecipe.burnTime);
		}
	}
}

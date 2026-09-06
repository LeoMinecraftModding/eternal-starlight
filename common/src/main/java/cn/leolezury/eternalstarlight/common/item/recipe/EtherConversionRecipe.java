package cn.leolezury.eternalstarlight.common.item.recipe;

import cn.leolezury.eternalstarlight.common.EternalStarlight;
import cn.leolezury.eternalstarlight.common.registry.ESRecipeSerializers;
import cn.leolezury.eternalstarlight.common.registry.ESRecipes;
import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.component.DataComponentPredicate;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.*;
import net.minecraft.world.level.Level;

import java.util.List;

public record EtherConversionRecipe(Ingredient input, int inputCount, DataComponentPredicate components, List<WeightedOutput> outputs) implements Recipe<SingleRecipeInput> {
	@Override
	public boolean matches(SingleRecipeInput container, Level level) {
		ItemStack stack = container.getItem(0);
		return !stack.isEmpty() && stack.getCount() >= inputCount() && input().test(stack) && components().test(stack);
	}

	@Override
	public ItemStack assemble(SingleRecipeInput container, HolderLookup.Provider provider) {
		return outputs().getFirst().output().copy();
	}

	@Override
	public boolean canCraftInDimensions(int i, int j) {
		return true;
	}

	@Override
	public ItemStack getResultItem(HolderLookup.Provider provider) {
		return outputs().getFirst().output();
	}

	public ItemStack rollOutput(RandomSource random) {
		int totalWeight = 0;
		for (WeightedOutput output : outputs()) {
			totalWeight += output.weight();
		}
		if (totalWeight <= 0) {
			return ItemStack.EMPTY;
		}
		int roll = random.nextInt(totalWeight);
		for (WeightedOutput output : outputs()) {
			roll -= output.weight();
			if (roll < 0) {
				return output.output().copy();
			}
		}
		return ItemStack.EMPTY;
	}

	@Override
	public RecipeSerializer<?> getSerializer() {
		return ESRecipeSerializers.ETHER_CONVERSION.get();
	}

	@Override
	public RecipeType<?> getType() {
		return ESRecipes.ETHER_CONVERSION.get();
	}

	public record WeightedOutput(ItemStack output, int weight) {
		public static final Codec<WeightedOutput> CODEC = RecordCodecBuilder.create(instance -> instance.group(
			ItemStack.OPTIONAL_CODEC.fieldOf("output").forGetter(WeightedOutput::output),
			Codec.INT.fieldOf("weight").forGetter(WeightedOutput::weight)
		).apply(instance, WeightedOutput::new));
	}

	public static class Type implements RecipeType<EtherConversionRecipe> {
		public static final ResourceLocation ID = EternalStarlight.id("ether_conversion");

		@Override
		public String toString() {
			return ID.toString();
		}
	}

	public static class Serializer implements RecipeSerializer<EtherConversionRecipe> {
		private static final MapCodec<EtherConversionRecipe> CODEC = RecordCodecBuilder.mapCodec(instance -> instance.group(
			Ingredient.CODEC.fieldOf("input").forGetter(EtherConversionRecipe::input),
			Codec.INT.optionalFieldOf("input_count", 1).forGetter(EtherConversionRecipe::inputCount),
			DataComponentPredicate.CODEC.optionalFieldOf("components", DataComponentPredicate.EMPTY).forGetter(EtherConversionRecipe::components),
			WeightedOutput.CODEC.listOf().fieldOf("outputs").forGetter(EtherConversionRecipe::outputs)
		).apply(instance, EtherConversionRecipe::new));

		@Override
		public MapCodec<EtherConversionRecipe> codec() {
			return CODEC;
		}

		@Override
		public StreamCodec<RegistryFriendlyByteBuf, EtherConversionRecipe> streamCodec() {
			return new StreamCodec<>() {
				@Override
				public EtherConversionRecipe decode(RegistryFriendlyByteBuf friendlyByteBuf) {
					Ingredient input = Ingredient.CONTENTS_STREAM_CODEC.decode(friendlyByteBuf);
					int inputCount = friendlyByteBuf.readInt();
					DataComponentPredicate components = DataComponentPredicate.STREAM_CODEC.decode(friendlyByteBuf);
					int outputCount = friendlyByteBuf.readVarInt();
					List<WeightedOutput> outputs = new java.util.ArrayList<>(outputCount);
					for (int i = 0; i < outputCount; i++) {
						ItemStack output = ItemStack.STREAM_CODEC.decode(friendlyByteBuf);
						int weight = friendlyByteBuf.readInt();
						outputs.add(new WeightedOutput(output, weight));
					}
					return new EtherConversionRecipe(input, inputCount, components, outputs);
				}

				@Override
				public void encode(RegistryFriendlyByteBuf friendlyByteBuf, EtherConversionRecipe recipe) {
					Ingredient.CONTENTS_STREAM_CODEC.encode(friendlyByteBuf, recipe.input());
					friendlyByteBuf.writeInt(recipe.inputCount());
					DataComponentPredicate.STREAM_CODEC.encode(friendlyByteBuf, recipe.components());
					friendlyByteBuf.writeVarInt(recipe.outputs().size());
					for (WeightedOutput output : recipe.outputs()) {
						ItemStack.STREAM_CODEC.encode(friendlyByteBuf, output.output());
						friendlyByteBuf.writeInt(output.weight());
					}
				}
			};
		}
	}
}

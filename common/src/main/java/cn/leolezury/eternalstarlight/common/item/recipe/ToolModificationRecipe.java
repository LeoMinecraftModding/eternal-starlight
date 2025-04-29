package cn.leolezury.eternalstarlight.common.item.recipe;

import cn.leolezury.eternalstarlight.common.registry.ESRecipeSerializers;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.NonNullList;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.*;
import net.minecraft.world.level.Level;

public class ToolModificationRecipe extends CustomRecipe {
	private final Item tool, input;
	private final ItemStack output;

	public ToolModificationRecipe(CraftingBookCategory category, Item tool, Item input, ItemStack output) {
		super(category);
		this.tool = tool;
		this.input = input;
		this.output = output;
	}

	@Override
	public boolean matches(CraftingInput recipeInput, Level level) {
		if (recipeInput.items().stream().anyMatch(stack -> !stack.isEmpty() && !stack.is(tool) && !stack.is(input))) {
			return false;
		}
		if (recipeInput.items().stream().filter(stack -> stack.is(tool)).count() != 1) {
			return false;
		}
		return recipeInput.items().stream().filter(stack -> stack.is(input)).count() == 1;
	}

	@Override
	public ItemStack assemble(CraftingInput recipeInput, HolderLookup.Provider provider) {
		return output.copy();
	}

	@Override
	public NonNullList<ItemStack> getRemainingItems(CraftingInput recipeInput) {
		NonNullList<ItemStack> items = NonNullList.withSize(recipeInput.size(), ItemStack.EMPTY);

		for (int i = 0; i < items.size(); ++i) {
			if (recipeInput.getItem(i).is(tool)) {
				ItemStack remaining = recipeInput.getItem(i).copy();
				if (remaining.getDamageValue() + 1 < remaining.getMaxDamage()) {
					remaining.setDamageValue(remaining.getDamageValue() + 1);
					items.set(i, remaining);
				}
			}
		}

		return items;
	}

	@Override
	public boolean canCraftInDimensions(int i, int j) {
		return true;
	}

	@Override
	public RecipeSerializer<?> getSerializer() {
		return ESRecipeSerializers.TOOL_MODIFICATION.get();
	}

	public static class Serializer implements RecipeSerializer<ToolModificationRecipe> {
		private static final MapCodec<ToolModificationRecipe> CODEC = RecordCodecBuilder.mapCodec(instance -> instance.group(
			CraftingBookCategory.CODEC.fieldOf("category").orElse(CraftingBookCategory.MISC).forGetter(CraftingRecipe::category),
			BuiltInRegistries.ITEM.byNameCodec().fieldOf("tool").forGetter(o -> o.tool),
			BuiltInRegistries.ITEM.byNameCodec().fieldOf("input").forGetter(o -> o.input),
			ItemStack.OPTIONAL_CODEC.fieldOf("output").forGetter(o -> o.output)
		).apply(instance, ToolModificationRecipe::new));

		@Override
		public MapCodec<ToolModificationRecipe> codec() {
			return CODEC;
		}

		@Override
		public StreamCodec<RegistryFriendlyByteBuf, ToolModificationRecipe> streamCodec() {
			return new StreamCodec<>() {
				@Override
				public ToolModificationRecipe decode(RegistryFriendlyByteBuf friendlyByteBuf) {
					CraftingBookCategory category = friendlyByteBuf.readEnum(CraftingBookCategory.class);
					Item tool = friendlyByteBuf.readById(BuiltInRegistries.ITEM::byId);
					Item input = friendlyByteBuf.readById(BuiltInRegistries.ITEM::byId);
					ItemStack output = ItemStack.STREAM_CODEC.decode(friendlyByteBuf);
					return new ToolModificationRecipe(category, tool, input, output);
				}

				@Override
				public void encode(RegistryFriendlyByteBuf friendlyByteBuf, ToolModificationRecipe recipe) {
					friendlyByteBuf.writeEnum(recipe.category());
					friendlyByteBuf.writeById(BuiltInRegistries.ITEM::getId, recipe.tool);
					friendlyByteBuf.writeById(BuiltInRegistries.ITEM::getId, recipe.input);
					ItemStack.STREAM_CODEC.encode(friendlyByteBuf, recipe.output);
				}
			};
		}
	}
}

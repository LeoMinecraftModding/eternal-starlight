package cn.leolezury.eternalstarlight.common.item.recipe;

import cn.leolezury.eternalstarlight.common.registry.ESItems;
import cn.leolezury.eternalstarlight.common.registry.ESRecipeSerializers;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.NonNullList;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.CraftingBookCategory;
import net.minecraft.world.item.crafting.CraftingInput;
import net.minecraft.world.item.crafting.CustomRecipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.level.Level;

public class MatchboxTorchRecipe extends CustomRecipe {
	public MatchboxTorchRecipe(CraftingBookCategory category) {
		super(category);
	}

	@Override
	public boolean matches(CraftingInput recipeInput, Level level) {
		if (recipeInput.items().stream().anyMatch(stack -> !stack.isEmpty() && !stack.is(ESItems.SALTPETER_MATCHBOX.get()) && !stack.is(Items.STICK))) {
			return false;
		}
		if (recipeInput.items().stream().filter(stack -> stack.is(ESItems.SALTPETER_MATCHBOX.get())).count() != 1) {
			return false;
		}
		return recipeInput.items().stream().filter(stack -> stack.is(Items.STICK)).count() == 1;
	}

	@Override
	public ItemStack assemble(CraftingInput recipeInput, HolderLookup.Provider provider) {
		if (recipeInput.items().stream().anyMatch(stack -> !stack.isEmpty() && !stack.is(ESItems.SALTPETER_MATCHBOX.get()) && !stack.is(Items.STICK))) {
			return ItemStack.EMPTY;
		}
		if (recipeInput.items().stream().filter(stack -> stack.is(ESItems.SALTPETER_MATCHBOX.get())).count() != 1) {
			return ItemStack.EMPTY;
		}
		if (recipeInput.items().stream().filter(stack -> stack.is(Items.STICK)).count() != 1) {
			return ItemStack.EMPTY;
		}
		return Items.TORCH.getDefaultInstance();
	}

	@Override
	public NonNullList<ItemStack> getRemainingItems(CraftingInput recipeInput) {
		NonNullList<ItemStack> items = NonNullList.withSize(recipeInput.size(), ItemStack.EMPTY);

		for (int i = 0; i < items.size(); ++i) {
			if (recipeInput.getItem(i).is(ESItems.SALTPETER_MATCHBOX.get())) {
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
		return ESRecipeSerializers.MATCHBOX_TORCH.get();
	}
}

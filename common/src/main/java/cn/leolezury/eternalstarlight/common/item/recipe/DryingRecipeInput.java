package cn.leolezury.eternalstarlight.common.item.recipe;

import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeInput;

public record DryingRecipeInput(ItemStack input, boolean fireBelow) implements RecipeInput {
	@Override
	public ItemStack getItem(int i) {
		return input();
	}

	@Override
	public int size() {
		return 1;
	}
}

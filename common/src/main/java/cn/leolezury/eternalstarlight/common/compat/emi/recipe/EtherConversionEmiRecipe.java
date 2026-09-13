package cn.leolezury.eternalstarlight.common.compat.emi.recipe;

import cn.leolezury.eternalstarlight.common.compat.emi.ESEmiPlugin;
import cn.leolezury.eternalstarlight.common.item.recipe.EtherConversionRecipe;
import dev.emi.emi.api.recipe.BasicEmiRecipe;
import dev.emi.emi.api.recipe.EmiRecipe;
import dev.emi.emi.api.render.EmiTexture;
import dev.emi.emi.api.stack.EmiIngredient;
import dev.emi.emi.api.stack.EmiStack;
import dev.emi.emi.api.widget.SlotWidget;
import dev.emi.emi.api.widget.WidgetHolder;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeHolder;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class EtherConversionEmiRecipe extends BasicEmiRecipe {
	private final List<EmiStack> outputStacks;

	public EtherConversionEmiRecipe(RecipeHolder<EtherConversionRecipe> holder) {
		super(ESEmiPlugin.ETHER_CONVERSION, holder.id(), holder.value().outputs().size() > 8 ? 142 : 130, 44);
		EtherConversionRecipe recipe = holder.value();

		List<ItemStack> ingredients = Arrays.asList(recipe.input().getItems());
		ingredients.forEach(stack -> stack.applyComponents(recipe.components().asPatch()));
		ingredients.forEach(stack -> this.inputs.add(EmiStack.of(stack)));

		int totalWeight = 0;
		for (EtherConversionRecipe.WeightedOutput output : recipe.outputs()) {
			totalWeight += output.weight();
		}

		this.outputStacks = new ArrayList<>();
		for (EtherConversionRecipe.WeightedOutput output : recipe.outputs()) {
			EmiStack stack = EmiStack.of(output.output());
			if (totalWeight > 0 && output.weight() < totalWeight) {
				stack.setChance(output.weight() / (float) totalWeight);
			}
			this.outputStacks.add(stack);
			this.outputs.add(stack);
		}
	}

	@Override
	public void addWidgets(WidgetHolder widgets) {
		widgets.addTexture(EmiTexture.EMPTY_ARROW, 25, 13);
		widgets.addSlot(EmiIngredient.of(inputs), 0, 13);

		int pageCount = Math.max(outputStacks.size() - 1, 0) / 8 + 1;
		AtomicInteger page = new AtomicInteger();
		for (int i = 0; i < 8; i++) {
			widgets.add(new PagedOutputSlot(outputStacks, i, page, 56 + i % 4 * 18, 4 + i / 4 * 18));
		}
		if (pageCount > 1) {
			widgets.addButton(128, 4, 12, 12, 0, 0, () -> true,
				(mouseX, mouseY, button) -> page.set((page.get() - 1 + pageCount) % pageCount));
			widgets.addButton(128, 16, 12, 12, 12, 0, () -> true,
				(mouseX, mouseY, button) -> page.set((page.get() + 1) % pageCount));
		}
	}

	private class PagedOutputSlot extends SlotWidget {
		private final List<EmiStack> stacks;
		private final int index;
		private final AtomicInteger page;

		public PagedOutputSlot(List<EmiStack> stacks, int index, AtomicInteger page, int x, int y) {
			super(EmiStack.EMPTY, x, y);
			this.stacks = stacks;
			this.index = index;
			this.page = page;
			recipeContext(EtherConversionEmiRecipe.this);
		}

		@Override
		public EmiIngredient getStack() {
			int i = page.get() * 8 + index;
			return i >= 0 && i < stacks.size() ? stacks.get(i) : EmiStack.EMPTY;
		}

		@Override
		public EmiRecipe getRecipe() {
			return getStack().isEmpty() ? null : super.getRecipe();
		}
	}
}

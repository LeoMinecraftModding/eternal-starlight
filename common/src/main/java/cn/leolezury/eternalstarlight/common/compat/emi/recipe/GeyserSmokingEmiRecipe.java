package cn.leolezury.eternalstarlight.common.compat.emi.recipe;

import cn.leolezury.eternalstarlight.common.compat.emi.ESEmiPlugin;
import cn.leolezury.eternalstarlight.common.item.recipe.GeyserSmokingRecipe;
import dev.emi.emi.api.recipe.BasicEmiRecipe;
import dev.emi.emi.api.render.EmiTexture;
import dev.emi.emi.api.stack.EmiStack;
import dev.emi.emi.api.widget.WidgetHolder;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeHolder;

public class GeyserSmokingEmiRecipe extends BasicEmiRecipe {
	public GeyserSmokingEmiRecipe(RecipeHolder<GeyserSmokingRecipe> holder) {
		super(ESEmiPlugin.GEYSER_SMOKING, holder.id(), 82, 34);
		GeyserSmokingRecipe recipe = holder.value();
		this.inputs.add(EmiStack.of(new ItemStack(recipe.input(), recipe.inputCount())));
		this.outputs.add(EmiStack.of(recipe.output()));
	}

	@Override
	public void addWidgets(WidgetHolder widgets) {
		widgets.addTexture(EmiTexture.EMPTY_ARROW, 26, 9);
		widgets.addSlot(inputs.getFirst(), 0, 8);
		widgets.addSlot(outputs.getFirst(), 56, 4).large(true).recipeContext(this);
	}
}

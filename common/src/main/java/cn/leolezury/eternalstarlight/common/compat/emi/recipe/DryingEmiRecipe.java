package cn.leolezury.eternalstarlight.common.compat.emi.recipe;

import cn.leolezury.eternalstarlight.common.compat.emi.ESEmiPlugin;
import cn.leolezury.eternalstarlight.common.item.recipe.DryingRecipe;
import dev.emi.emi.api.recipe.BasicEmiRecipe;
import dev.emi.emi.api.stack.EmiIngredient;
import dev.emi.emi.api.stack.EmiStack;
import dev.emi.emi.api.widget.TextWidget;
import dev.emi.emi.api.widget.WidgetHolder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.item.crafting.RecipeHolder;

import java.util.stream.StreamSupport;

public class DryingEmiRecipe extends BasicEmiRecipe {
	private final int durationTicks;
	private final EmiIngredient fireBelow;

	public DryingEmiRecipe(RecipeHolder<DryingRecipe> holder) {
		super(ESEmiPlugin.DRYING, holder.id(), 82, 44);
		DryingRecipe recipe = holder.value();
		this.durationTicks = recipe.durationTicks();
		this.inputs.add(EmiIngredient.of(recipe.input()));
		this.outputs.add(EmiStack.of(recipe.output()));
		this.fireBelow = recipe.fireBelow() ? campfires() : EmiStack.EMPTY;
	}

	private static EmiIngredient campfires() {
		return EmiIngredient.of(StreamSupport.stream(BuiltInRegistries.BLOCK.getTagOrEmpty(BlockTags.CAMPFIRES).spliterator(), false)
			.<EmiIngredient>map(holder -> EmiStack.of(holder.value()))
			.toList());
	}

	@Override
	public void addWidgets(WidgetHolder widgets) {
		widgets.addFillingArrow(26, 9, Math.max(durationTicks * 50, 1));
		widgets.addSlot(inputs.getFirst(), 0, 8);
		widgets.addSlot(outputs.getFirst(), 56, 4).large(true).recipeContext(this);
		if (!fireBelow.isEmpty()) {
			// purely informative: the rack has to sit above a lit campfire
			widgets.addSlot(fireBelow, 0, 27);
		}
		if (durationTicks > 0) {
			widgets.addText(Component.translatable("emi.cooking.time", durationTicks / 20), width, height - 9, 0xFF808080, false)
				.horizontalAlign(TextWidget.Alignment.END);
		}
	}
}

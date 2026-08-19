package cn.leolezury.eternalstarlight.common.compat.emi.recipe;

import cn.leolezury.eternalstarlight.common.EternalStarlight;
import cn.leolezury.eternalstarlight.common.compat.emi.ESEmiPlugin;
import cn.leolezury.eternalstarlight.common.item.recipe.AlloyRecipe;
import dev.emi.emi.api.recipe.BasicEmiRecipe;
import dev.emi.emi.api.stack.EmiIngredient;
import dev.emi.emi.api.stack.EmiStack;
import dev.emi.emi.api.widget.TextWidget;
import dev.emi.emi.api.widget.WidgetHolder;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeHolder;

import java.util.List;

public class AlloyEmiRecipe extends BasicEmiRecipe {
	private static final ResourceLocation BACKGROUND = EternalStarlight.id("textures/gui/jei/alloy_furnace/background.png");
	private static final ResourceLocation BURN_PROGRESS = EternalStarlight.id("textures/gui/sprites/screen/alloy_furnace/burn_progress.png");
	private static final ResourceLocation LIT_PROGRESS = EternalStarlight.id("textures/gui/sprites/screen/alloy_furnace/lit_progress.png");
	private static final ResourceLocation COOLING_PROGRESS = EternalStarlight.id("textures/gui/sprites/screen/alloy_furnace/cooling_progress.png");
	private static final List<int[]> OUTPUT_POSITIONS = List.of(new int[]{114, 5}, new int[]{105, 40}, new int[]{123, 40});
	/** Free-running loop for the decorative flame and cooling icons, in milliseconds. */
	private static final int IDLE_ANIMATION = 15000;

	private final int burnTime;

	public AlloyEmiRecipe(RecipeHolder<AlloyRecipe> holder) {
		super(ESEmiPlugin.ALLOY, holder.id(), 141, 78);
		AlloyRecipe recipe = holder.value();
		this.burnTime = recipe.burnTime();
		for (Ingredient ingredient : recipe.getIngredients()) {
			this.inputs.add(EmiIngredient.of(ingredient));
		}
		for (AlloyRecipe.Result result : recipe.results()) {
			// EMI has no random-count display, so advertise the best case like JEI does
			this.outputs.add(EmiStack.of(result.getMaxResultItem()));
		}
	}

	@Override
	public void addWidgets(WidgetHolder widgets) {
		widgets.addTexture(BACKGROUND, 0, 1, 141, 57, 0, 0, 141, 57, 141, 57);
		widgets.addAnimatedTexture(BURN_PROGRESS, 80, 5, 24, 16, 0, 0, 24, 16, 24, 16, Math.max(burnTime * 50, 1), true, false, false);
		widgets.addAnimatedTexture(LIT_PROGRESS, 1, 24, 14, 14, 0, 0, 14, 14, 14, 14, IDLE_ANIMATION, false, true, true);
		widgets.addAnimatedTexture(COOLING_PROGRESS, 76, 22, 16, 16, 0, 0, 16, 16, 16, 16, IDLE_ANIMATION, false, true, true);

		for (int i = 0; i < inputs.size() && i < 9; i++) {
			widgets.addSlot(inputs.get(i), 20 + (i % 3) * 18, 4 + (i / 3) * 18).drawBack(false);
		}

		for (int i = 0; i < outputs.size() && i < OUTPUT_POSITIONS.size(); i++) {
			int[] position = OUTPUT_POSITIONS.get(i);
			widgets.addSlot(outputs.get(i), position[0], position[1]).drawBack(false).recipeContext(this);
		}

		if (burnTime > 0) {
			widgets.addText(Component.translatable("emi.cooking.time", burnTime / 20), width, height - 9, 0xFF808080, false)
				.horizontalAlign(TextWidget.Alignment.END);
		}
	}
}

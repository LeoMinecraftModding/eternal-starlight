package cn.leolezury.eternalstarlight.common.compat.jei.category;

import cn.leolezury.eternalstarlight.common.EternalStarlight;
import cn.leolezury.eternalstarlight.common.compat.jei.recipe.AlloyFurnaceCoolingRecipe;
import cn.leolezury.eternalstarlight.common.registry.ESItems;
import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.gui.drawable.IDrawable;
import mezz.jei.api.gui.drawable.IDrawableAnimated;
import mezz.jei.api.gui.drawable.IDrawableStatic;
import mezz.jei.api.gui.placement.HorizontalAlignment;
import mezz.jei.api.gui.placement.VerticalAlignment;
import mezz.jei.api.gui.widgets.IRecipeExtrasBuilder;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.recipe.IFocusGroup;
import mezz.jei.api.recipe.RecipeType;
import mezz.jei.api.recipe.category.AbstractRecipeCategory;
import mezz.jei.common.gui.elements.DrawableAnimated;
import mezz.jei.common.gui.elements.DrawableCombined;
import mezz.jei.library.gui.elements.DrawableBuilder;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class AlloyFurnaceCoolingCategory extends AbstractRecipeCategory<AlloyFurnaceCoolingRecipe> {
	public static final RecipeType<AlloyFurnaceCoolingRecipe> ALLOY_FURNACE_COOLING = RecipeType.create(EternalStarlight.ID, "alloy_furnace_cooling", AlloyFurnaceCoolingRecipe.class);

	public AlloyFurnaceCoolingCategory(IGuiHelper guiHelper) {
		super(
			ALLOY_FURNACE_COOLING,
			Component.translatable("gui." + EternalStarlight.ID + ".jei.category.alloy_furnace_cooling"),
			guiHelper.createDrawableItemLike(ESItems.FROZEN_TUBE.get()),
			getMaxWidth(),
			34
		);
	}

	private static int getMaxWidth() {
		// width of the recipe depends on the text, which is different in each language
		Minecraft minecraft = Minecraft.getInstance();
		Font fontRenderer = minecraft.font;
		int maxStringWidth = Math.max(fontRenderer.width(createDurationText(10000000 * 200).getString()), fontRenderer.width(createEfficiencyText(10000000 * 200).getString()));
		int textPadding = 20;
		return 18 + textPadding + maxStringWidth;
	}

	@Override
	public void setRecipe(IRecipeLayoutBuilder builder, AlloyFurnaceCoolingRecipe recipe, IFocusGroup focuses) {
		builder.addInputSlot(1, 17)
			.setStandardSlotBackground()
			.addItemLike(recipe.item());
	}

	@Override
	public void createRecipeExtras(IRecipeExtrasBuilder builder, AlloyFurnaceCoolingRecipe recipe, IFocusGroup focuses) {
		int duration = recipe.cooling().duration();
		int efficiency = recipe.cooling().efficiency();

		IDrawableStatic coolingIcon = new DrawableBuilder(EternalStarlight.id("textures/gui/jei/alloy_furnace/cooling_progress.png"), 0, 0, 15, 15).setTextureSize(15, 15).build();
		IDrawableAnimated animatedFill = new DrawableAnimated(coolingIcon, duration, IDrawableAnimated.StartDirection.TOP, true);
		IDrawable drawableCombined = new DrawableCombined(new DrawableBuilder(EternalStarlight.id("textures/gui/jei/alloy_furnace/cooling_background.png"), 0, 0, 15, 15).setTextureSize(15, 15).build(), animatedFill);
		builder.addDrawable(drawableCombined)
			.setPosition(1, 0);

		builder.addText(List.of(createDurationText(duration), createEfficiencyText(efficiency)), getWidth() - 20, getHeight())
			.setPosition(20, 0)
			.setTextAlignment(HorizontalAlignment.CENTER)
			.setTextAlignment(VerticalAlignment.CENTER)
			.setColor(0xFF808080);
	}

	public static Component createDurationText(int duration) {
		int durationSeconds = duration / 20;
		return Component.translatable("gui.jei.category.smelting.time.seconds", durationSeconds);
	}

	public static Component createEfficiencyText(int efficiency) {
		return Component.translatable("gui." + EternalStarlight.ID + ".jei.category.alloy_furnace_cooling.efficiency", efficiency);
	}

	@Override
	public @Nullable ResourceLocation getRegistryName(AlloyFurnaceCoolingRecipe recipe) {
		return null;
	}
}

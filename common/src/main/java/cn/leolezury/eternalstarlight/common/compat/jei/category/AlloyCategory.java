package cn.leolezury.eternalstarlight.common.compat.jei.category;

import cn.leolezury.eternalstarlight.common.EternalStarlight;
import cn.leolezury.eternalstarlight.common.item.recipe.AlloyRecipe;
import cn.leolezury.eternalstarlight.common.registry.ESItems;
import cn.leolezury.eternalstarlight.common.registry.ESRecipes;
import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.gui.drawable.IDrawableAnimated;
import mezz.jei.api.gui.drawable.IDrawableStatic;
import mezz.jei.api.gui.ingredient.IRecipeSlotDrawable;
import mezz.jei.api.gui.ingredient.IRecipeSlotsView;
import mezz.jei.api.gui.placement.HorizontalAlignment;
import mezz.jei.api.gui.placement.VerticalAlignment;
import mezz.jei.api.gui.widgets.IRecipeExtrasBuilder;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.recipe.IFocusGroup;
import mezz.jei.api.recipe.RecipeIngredientRole;
import mezz.jei.api.recipe.RecipeType;
import mezz.jei.api.recipe.category.AbstractRecipeCategory;
import mezz.jei.common.gui.elements.DrawableAnimated;
import mezz.jei.library.gui.elements.DrawableBuilder;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeHolder;

import java.util.List;

public class AlloyCategory extends AbstractRecipeCategory<RecipeHolder<AlloyRecipe>> {
	public static final RecipeType<RecipeHolder<AlloyRecipe>> ALLOY = RecipeType.createFromVanilla(ESRecipes.ALLOY.get());
	private static final RandomSource RANDOM = RandomSource.create();

	private final IDrawableStatic background;

	public AlloyCategory(IGuiHelper guiHelper) {
		super(
			ALLOY,
			Component.translatable("gui." + EternalStarlight.ID + ".jei.category.alloy"),
			guiHelper.createDrawableItemLike(ESItems.ALLOY_FURNACE.get()),
			141,
			57 + 1 + 20
		);
		this.background = guiHelper.drawableBuilder(EternalStarlight.id("textures/gui/jei/alloy_furnace/background.png"), 0, 0, 141, 57)
			.setTextureSize(141, 57)
			.build();
	}

	@Override
	public void draw(RecipeHolder<AlloyRecipe> recipe, IRecipeSlotsView recipeSlotsView, GuiGraphics guiGraphics, double mouseX, double mouseY) {
		background.draw(guiGraphics, 0, 1);
	}

	@Override
	public void setRecipe(IRecipeLayoutBuilder builder, RecipeHolder<AlloyRecipe> recipeHolder, IFocusGroup focuses) {
		AlloyRecipe recipe = recipeHolder.value();
		List<Ingredient> ingredients = recipe.getIngredients();
		List<AlloyRecipe.Result> results = recipe.results();

		for (int x = 0; x < 3; x++) {
			for (int y = 0; y < 3; y++) {
				int index = y * 3 + x;
				if (ingredients.size() > index) {
					builder.addInputSlot(20 + x * 18 + 1, 4 + y * 18 + 1)
						.addIngredients(ingredients.get(index));
				}
			}
		}

		builder.addSlot(RecipeIngredientRole.RENDER_ONLY, 1, 41);
		builder.addSlot(RecipeIngredientRole.RENDER_ONLY, 77, 41);

		builder.addOutputSlot(115, 6)
			.addItemStack(recipe.results().getFirst().getMaxResultItem());
		if (results.size() > 1) {
			builder.addOutputSlot(106, 41)
				.addItemStack(recipe.results().get(1).getMaxResultItem());
		}
		if (results.size() > 2) {
			builder.addOutputSlot(124, 41)
				.addItemStack(recipe.results().get(2).getMaxResultItem());
		}
	}

	@Override
	public void onDisplayedIngredientsUpdate(RecipeHolder<AlloyRecipe> recipe, List<IRecipeSlotDrawable> recipeSlots, IFocusGroup focuses) {
		for (int i = 0; i < 3; i++) {
			if (recipe.value().results().size() > i) {
				recipeSlots.get(i + recipe.value().getIngredients().size() + 2).createDisplayOverrides().addItemStack(recipe.value().results().get(i).getResultItem(RANDOM));
			}
		}
	}

	@Override
	public void createRecipeExtras(IRecipeExtrasBuilder builder, RecipeHolder<AlloyRecipe> recipeHolder, IFocusGroup focuses) {
		AlloyRecipe recipe = recipeHolder.value();
		int burnTime = recipe.burnTime();

		addRecipeArrow(builder, burnTime);
		addRecipeFlame(builder);
		addRecipeCooling(builder);

		addCookTime(builder, burnTime);
	}

	protected void addRecipeArrow(IRecipeExtrasBuilder builder, int burnTime) {
		IDrawableStatic icon = new DrawableBuilder(EternalStarlight.id("textures/gui/sprites/screen/alloy_furnace/burn_progress.png"), 0, 0, 24, 16).setTextureSize(24, 16).build();
		IDrawableAnimated animatedFill = new DrawableAnimated(icon, burnTime, IDrawableAnimated.StartDirection.LEFT, false);
		builder.addDrawable(animatedFill)
			.setPosition(80, 5);
	}

	protected void addRecipeFlame(IRecipeExtrasBuilder builder) {
		IDrawableStatic icon = new DrawableBuilder(EternalStarlight.id("textures/gui/sprites/screen/alloy_furnace/lit_progress.png"), 0, 0, 14, 14).setTextureSize(14, 14).build();
		IDrawableAnimated animatedFill = new DrawableAnimated(icon, 300, IDrawableAnimated.StartDirection.TOP, true);
		builder.addDrawable(animatedFill)
			.setPosition(1, 24);
	}

	protected void addRecipeCooling(IRecipeExtrasBuilder builder) {
		IDrawableStatic icon = new DrawableBuilder(EternalStarlight.id("textures/gui/sprites/screen/alloy_furnace/cooling_progress.png"), 0, 0, 16, 16).setTextureSize(16, 16).build();
		IDrawableAnimated animatedFill = new DrawableAnimated(icon, 300, IDrawableAnimated.StartDirection.TOP, true);
		builder.addDrawable(animatedFill)
			.setPosition(76, 22);
	}

	protected void addCookTime(IRecipeExtrasBuilder builder, int burnTime) {
		if (burnTime > 0) {
			int burnTimeSeconds = burnTime / 20;
			Component timeString = Component.translatable("gui.jei.category.smelting.time.seconds", burnTimeSeconds);
			builder.addText(timeString, getWidth() - 20, 10)
				.setPosition(0, 0, getWidth(), getHeight(), HorizontalAlignment.RIGHT, VerticalAlignment.BOTTOM)
				.setTextAlignment(HorizontalAlignment.RIGHT)
				.setTextAlignment(VerticalAlignment.BOTTOM)
				.setColor(0xFF808080);
		}
	}
}
package cn.leolezury.eternalstarlight.common.compat.emi.recipe;

import cn.leolezury.eternalstarlight.common.EternalStarlight;
import cn.leolezury.eternalstarlight.common.item.recipe.ToolModificationRecipe;
import dev.emi.emi.api.recipe.EmiPatternCraftingRecipe;
import dev.emi.emi.api.stack.EmiStack;
import dev.emi.emi.api.widget.SlotWidget;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;

import java.util.List;

public class ToolModificationEmiRecipe extends EmiPatternCraftingRecipe {
	private final ItemStack tool;
	private final ItemStack input;
	private final ItemStack output;

	public ToolModificationEmiRecipe(ResourceLocation id, ToolModificationRecipe recipe) {
		super(
			List.of(EmiStack.of(recipe.tool()), EmiStack.of(recipe.input())),
			EmiStack.of(recipe.output()),
			id
		);
		this.tool = recipe.tool().getDefaultInstance();
		this.input = recipe.input().getDefaultInstance();
		this.output = recipe.output();
	}

	@Override
	public SlotWidget getInputWidget(int slot, int x, int y) {
		if (slot == 0) {
			return new SlotWidget(EmiStack.of(this.tool), x, y)
				.appendTooltip(Component.translatable("gui." + EternalStarlight.ID + ".jei.tool_modification.damaged").withStyle(ChatFormatting.GRAY));
		}
		if (slot == 1) {
			return new SlotWidget(EmiStack.of(this.input), x, y);
		}
		return new SlotWidget(EmiStack.EMPTY, x, y);
	}

	@Override
	public SlotWidget getOutputWidget(int x, int y) {
		return new SlotWidget(EmiStack.of(this.output), x, y);
	}
}

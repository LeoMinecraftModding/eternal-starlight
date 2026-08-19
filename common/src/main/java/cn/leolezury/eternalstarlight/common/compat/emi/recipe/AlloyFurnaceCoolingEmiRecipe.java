package cn.leolezury.eternalstarlight.common.compat.emi.recipe;

import cn.leolezury.eternalstarlight.common.EternalStarlight;
import cn.leolezury.eternalstarlight.common.block.AlloyFurnaceCoolingItem;
import cn.leolezury.eternalstarlight.common.compat.emi.ESEmiPlugin;
import dev.emi.emi.api.recipe.BasicEmiRecipe;
import dev.emi.emi.api.stack.EmiStack;
import dev.emi.emi.api.widget.TextWidget;
import dev.emi.emi.api.widget.WidgetHolder;
import net.minecraft.client.Minecraft;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;

public class AlloyFurnaceCoolingEmiRecipe extends BasicEmiRecipe {
	private static final ResourceLocation COOLING_BACKGROUND = EternalStarlight.id("textures/gui/jei/alloy_furnace/cooling_background.png");
	private static final ResourceLocation COOLING_PROGRESS = EternalStarlight.id("textures/gui/jei/alloy_furnace/cooling_progress.png");

	private final AlloyFurnaceCoolingItem cooling;

	public AlloyFurnaceCoolingEmiRecipe(Item item, AlloyFurnaceCoolingItem cooling) {
		super(ESEmiPlugin.ALLOY_FURNACE_COOLING, syntheticId(item), maxWidth(), 34);
		this.cooling = cooling;
		this.inputs.add(EmiStack.of(item));
	}

	/** Cooling entries are code-driven, so they need an ID EMI can tell apart from a real recipe. */
	private static ResourceLocation syntheticId(Item item) {
		ResourceLocation itemId = BuiltInRegistries.ITEM.getKey(item);
		return EternalStarlight.id("/alloy_furnace_cooling/" + itemId.getNamespace() + "/" + itemId.getPath());
	}

	/** Width depends on the translated text, so every cooling entry shares the widest one. */
	private static int maxWidth() {
		var font = Minecraft.getInstance().font;
		int text = Math.max(font.width(durationText(10000000 * 200)), font.width(efficiencyText(10000000 * 200)));
		return 18 + 20 + text;
	}

	public static Component durationText(int duration) {
		return Component.translatable("emi.cooking.time", duration / 20);
	}

	public static Component efficiencyText(int efficiency) {
		return Component.translatable("gui." + EternalStarlight.ID + ".jei.category.alloy_furnace_cooling.efficiency", efficiency);
	}

	@Override
	public boolean supportsRecipeTree() {
		return false;
	}

	@Override
	public void addWidgets(WidgetHolder widgets) {
		widgets.addTexture(COOLING_BACKGROUND, 1, 0, 15, 15, 0, 0, 15, 15, 15, 15);
		widgets.addAnimatedTexture(COOLING_PROGRESS, 1, 0, 15, 15, 0, 0, 15, 15, 15, 15, Math.max(cooling.duration() * 50, 1), false, true, true);
		widgets.addSlot(inputs.getFirst(), 0, 16);

		int textCenter = 20 + (width - 20) / 2;
		widgets.addText(durationText(cooling.duration()), textCenter, 8, 0xFF808080, false)
			.horizontalAlign(TextWidget.Alignment.CENTER);
		widgets.addText(efficiencyText(cooling.efficiency()), textCenter, 17, 0xFF808080, false)
			.horizontalAlign(TextWidget.Alignment.CENTER);
	}
}

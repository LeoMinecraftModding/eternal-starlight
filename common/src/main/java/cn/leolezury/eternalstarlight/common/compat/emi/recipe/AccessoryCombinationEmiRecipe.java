package cn.leolezury.eternalstarlight.common.compat.emi.recipe;

import cn.leolezury.eternalstarlight.common.util.ESAccessoryUtil;
import dev.emi.emi.api.recipe.EmiPatternCraftingRecipe;
import dev.emi.emi.api.stack.EmiIngredient;
import dev.emi.emi.api.stack.EmiStack;
import dev.emi.emi.api.widget.GeneratedSlotWidget;
import dev.emi.emi.api.widget.SlotWidget;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;

import java.util.List;
import java.util.Random;

public class AccessoryCombinationEmiRecipe extends EmiPatternCraftingRecipe {
	private final ItemStack accessory;
	private final List<ItemStack> targets;

	public AccessoryCombinationEmiRecipe(ResourceLocation id, ItemStack accessory) {
		super(
			List.of(EmiStack.of(accessory), EmiIngredient.of(stacks(ESAccessoryUtil.getCombinationTargets(accessory)))),
			EmiStack.of(firstResult(accessory)),
			id
		);
		this.accessory = accessory;
		this.targets = ESAccessoryUtil.getCombinationTargets(accessory);
	}

	@Override
	public SlotWidget getInputWidget(int slot, int x, int y) {
		if (slot == 0) {
			return new SlotWidget(EmiStack.of(this.accessory), x, y);
		}
		if (slot == 1) {
			return new GeneratedSlotWidget(random -> EmiStack.of(target(random)), this.unique, x, y);
		}
		return new SlotWidget(EmiStack.EMPTY, x, y);
	}

	@Override
	public SlotWidget getOutputWidget(int x, int y) {
		return new GeneratedSlotWidget(random -> {
			ItemStack target = target(random);
			return target.isEmpty() ? EmiStack.EMPTY : EmiStack.of(ESAccessoryUtil.copyAndApplyAccessory(target, this.accessory));
		}, this.unique, x, y);
	}

	// both generated slots use this recipe's seed, so the cycling equipment and the result always match
	private ItemStack target(Random random) {
		return this.targets.isEmpty() ? ItemStack.EMPTY : this.targets.get(random.nextInt(this.targets.size()));
	}

	private static ItemStack firstResult(ItemStack accessory) {
		List<ItemStack> targets = ESAccessoryUtil.getCombinationTargets(accessory);
		return targets.isEmpty() ? ItemStack.EMPTY : ESAccessoryUtil.copyAndApplyAccessory(targets.getFirst(), accessory);
	}

	private static List<EmiIngredient> stacks(List<ItemStack> items) {
		return items.stream().<EmiIngredient>map(EmiStack::of).toList();
	}
}

package cn.leolezury.eternalstarlight.common.item.menu;

import cn.leolezury.eternalstarlight.common.block.entity.AlloyFurnaceBlockEntity;
import net.minecraft.world.Container;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;

public class AlloyFurnaceCoolingSlot extends Slot {
	public AlloyFurnaceCoolingSlot(Container container, int slot, int xPosition, int yPosition) {
		super(container, slot, xPosition, yPosition);
	}

	@Override
	public boolean mayPlace(ItemStack stack) {
		return AlloyFurnaceBlockEntity.isCoolingItem(stack);
	}
}

package cn.leolezury.eternalstarlight.common.item.menu;

import cn.leolezury.eternalstarlight.common.block.entity.AlloyFurnaceBlockEntity;
import net.minecraft.world.Container;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;

public class AlloyFurnaceFuelSlot extends Slot {
	public AlloyFurnaceFuelSlot(Container container, int slot, int xPosition, int yPosition) {
		super(container, slot, xPosition, yPosition);
	}

	@Override
	public boolean mayPlace(ItemStack stack) {
		return AlloyFurnaceBlockEntity.isFuel(stack);
	}
}

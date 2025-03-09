package cn.leolezury.eternalstarlight.common.item.menu;

import cn.leolezury.eternalstarlight.common.block.entity.CrystalbornCatalystBlockEntity;
import net.minecraft.world.Container;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;

public class CrystalbornCatalystFuelSlot extends Slot {
	public CrystalbornCatalystFuelSlot(Container container, int slot, int xPosition, int yPosition) {
		super(container, slot, xPosition, yPosition);
	}

	@Override
	public boolean mayPlace(ItemStack stack) {
		return CrystalbornCatalystBlockEntity.isFuel(stack);
	}
}

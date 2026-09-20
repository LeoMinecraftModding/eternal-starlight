package cn.leolezury.eternalstarlight.common.item.menu;

import cn.leolezury.eternalstarlight.common.block.entity.AlloyFurnaceBlockEntity;
import net.minecraft.core.HolderLookup;
import net.minecraft.world.Container;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;

public class AlloyFurnaceCoolingSlot extends Slot {
	private final HolderLookup.Provider registries;

	public AlloyFurnaceCoolingSlot(Container container, int slot, int xPosition, int yPosition, HolderLookup.Provider registries) {
		super(container, slot, xPosition, yPosition);
		this.registries = registries;
	}

	@Override
	public boolean mayPlace(ItemStack stack) {
		return AlloyFurnaceBlockEntity.isCoolingItem(registries, stack);
	}
}

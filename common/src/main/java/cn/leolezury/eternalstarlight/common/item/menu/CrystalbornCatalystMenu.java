package cn.leolezury.eternalstarlight.common.item.menu;

import cn.leolezury.eternalstarlight.common.block.entity.CrystalbornCatalystBlockEntity;
import cn.leolezury.eternalstarlight.common.registry.ESMenuTypes;
import net.minecraft.util.Mth;
import net.minecraft.world.Container;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.inventory.SimpleContainerData;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;

public class CrystalbornCatalystMenu extends AbstractContainerMenu {
	private final Container container;
	private final ContainerData data;

	public CrystalbornCatalystMenu(int containerId, Inventory playerInventory) {
		this(containerId, playerInventory, new SimpleContainer(15), new SimpleContainerData(1));
	}

	public CrystalbornCatalystMenu(int containerId, Inventory inventory, Container container, ContainerData data) {
		super(ESMenuTypes.CRYSTALBORN_CATALYST.get(), containerId);
		checkContainerSize(container, 15);
		checkContainerDataCount(data, 1);
		this.container = container;
		this.data = data;
		container.startOpen(inventory.player);

		this.addSlot(new CrystalbornCatalystFuelSlot(container, 0, 8, 36));

		for (int y = 0; y < 2; y++) {
			for (int x = 0; x < 7; x++) {
				this.addSlot(new Slot(container, x + y * 7 + 1, 44 + x * 18, 18 + y * 18));
			}
		}

		for (int y = 0; y < 3; y++) {
			for (int x = 0; x < 9; x++) {
				this.addSlot(new Slot(inventory, x + y * 9 + 9, 8 + x * 18, 66 + y * 18));
			}
		}

		for (int y = 0; y < 9; y++) {
			this.addSlot(new Slot(inventory, y, 8 + y * 18, 124));
		}

		this.addDataSlots(data);
	}

	public Container getContainer() {
		return container;
	}

	@Override
	public boolean stillValid(Player player) {
		return this.container.stillValid(player);
	}

	@Override
	public ItemStack quickMoveStack(Player player, int index) {
		ItemStack itemStack = ItemStack.EMPTY;
		Slot slot = this.slots.get(index);
		if (slot.hasItem()) {
			ItemStack slotItem = slot.getItem();
			itemStack = slotItem.copy();
			if (index < this.container.getContainerSize()) {
				if (!this.moveItemStackTo(slotItem, this.container.getContainerSize(), this.slots.size(), true)) {
					return ItemStack.EMPTY;
				}
			} else if (CrystalbornCatalystBlockEntity.isFuel(slotItem) && !this.moveItemStackTo(slotItem, 0, 1, false) && !this.moveItemStackTo(slotItem, 1, this.container.getContainerSize(), false)) {
				return ItemStack.EMPTY;
			} else if (!this.moveItemStackTo(slotItem, 1, this.container.getContainerSize(), false)) {
				return ItemStack.EMPTY;
			}

			if (slotItem.isEmpty()) {
				slot.setByPlayer(ItemStack.EMPTY);
			} else {
				slot.setChanged();
			}
		}

		return itemStack;
	}

	public float getChargeProgress() {
		return Mth.clamp(this.data.get(0) / 30f, 0f, 1f);
	}

	@Override
	public void removed(Player player) {
		super.removed(player);
		this.container.stopOpen(player);
	}
}

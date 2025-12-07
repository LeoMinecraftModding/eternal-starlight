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
	private static final int FUEL_SLOT = 0;
	private static final int STORAGE_SLOT_START = 1;
	private static final int STORAGE_SLOT_END = 15; // excluded
	private static final int INV_SLOT_START = 15;
	private static final int INV_SLOT_END = 42; // excluded
	private static final int USE_ROW_SLOT_START = 42;
	private static final int USE_ROW_SLOT_END = 51; // excluded

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

		for (int i = 0; i < 9; i++) {
			this.addSlot(new Slot(inventory, i, 8 + i * 18, 124));
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
		ItemStack stack = ItemStack.EMPTY;
		Slot slot = this.slots.get(index);
		if (slot != null && slot.hasItem()) {
			ItemStack slotItem = slot.getItem();
			stack = slotItem.copy();
			if (index == FUEL_SLOT || index >= STORAGE_SLOT_START && index < STORAGE_SLOT_END) {
				if (!this.moveItemStackTo(slotItem, INV_SLOT_START, USE_ROW_SLOT_END, false)) {
					return ItemStack.EMPTY;
				}

				slot.onQuickCraft(slotItem, stack);
			} else if (index >= INV_SLOT_START && index < USE_ROW_SLOT_END) {
				if (!((CrystalbornCatalystBlockEntity.isFuel(slotItem) && this.moveItemStackTo(slotItem, FUEL_SLOT, FUEL_SLOT + 1, false)) || this.moveItemStackTo(slotItem, STORAGE_SLOT_START, STORAGE_SLOT_END, false))) {
					if (index < INV_SLOT_END) {
						if (!this.moveItemStackTo(slotItem, USE_ROW_SLOT_START, USE_ROW_SLOT_END, false)) {
							return ItemStack.EMPTY;
						}
					} else if (!this.moveItemStackTo(slotItem, INV_SLOT_START, INV_SLOT_END, false)) {
						return ItemStack.EMPTY;
					}
				}
			} else if (!this.moveItemStackTo(slotItem, INV_SLOT_START, USE_ROW_SLOT_END, false)) {
				return ItemStack.EMPTY;
			}

			if (slotItem.isEmpty()) {
				slot.setByPlayer(ItemStack.EMPTY);
			} else {
				slot.setChanged();
			}

			if (slotItem.getCount() == stack.getCount()) {
				return ItemStack.EMPTY;
			}

			slot.onTake(player, slotItem);
		}

		return stack;
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

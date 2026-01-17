package cn.leolezury.eternalstarlight.common.item.menu;

import cn.leolezury.eternalstarlight.common.block.entity.AlloyFurnaceBlockEntity;
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

public class AlloyFurnaceMenu extends AbstractContainerMenu {
	public static final int INGREDIENT_SLOT_START = 0;
	public static final int INGREDIENT_SLOT_END = 9; // excluded
	public static final int FUEL_SLOT = 9;
	public static final int COOLING_SLOT = 10;
	public static final int RESULT_SLOT_START = 11;
	public static final int RESULT_SLOT_END = 14; // excluded
	public static final int INV_SLOT_START = 14;
	public static final int INV_SLOT_END = 41; // excluded
	public static final int USE_ROW_SLOT_START = 41;
	public static final int USE_ROW_SLOT_END = 50; // excluded

	private final Container container;
	private final ContainerData data;

	public AlloyFurnaceMenu(int containerId, Inventory playerInventory) {
		this(containerId, playerInventory, new SimpleContainer(14), new SimpleContainerData(7));
	}

	public AlloyFurnaceMenu(int containerId, Inventory playerInventory, Container container, ContainerData data) {
		super(ESMenuTypes.ALLOY_FURNACE.get(), containerId);
		this.container = container;
		this.data = data;
		checkContainerSize(container, 14);
		checkContainerDataCount(data, 7);

		for (int y = 0; y < 3; y++) {
			for (int x = 0; x < 3; x++) {
				this.addSlot(new Slot(this.container, x + y * 3, 30 + x * 18, 17 + y * 18));
			}
		}

		this.addSlot(new AlloyFurnaceFuelSlot(this.container, FUEL_SLOT, 10, 53));
		this.addSlot(new AlloyFurnaceCoolingSlot(this.container, COOLING_SLOT, 86, 53));

		this.addSlot(new AlloyResultSlot(playerInventory.player, this.container, RESULT_SLOT_START, 124, 18));
		this.addSlot(new AlloyResultSlot(playerInventory.player, this.container, RESULT_SLOT_START + 1, 115, 53));
		this.addSlot(new AlloyResultSlot(playerInventory.player, this.container, RESULT_SLOT_START + 2, 133, 53));

		for (int y = 0; y < 3; y++) {
			for (int x = 0; x < 9; x++) {
				this.addSlot(new Slot(playerInventory, x + y * 9 + 9, 8 + x * 18, 84 + y * 18));
			}
		}

		for (int i = 0; i < 9; i++) {
			this.addSlot(new Slot(playerInventory, i, 8 + i * 18, 142));
		}

		this.addDataSlots(data);
	}

	@Override
	public boolean stillValid(Player player) {
		return this.container.stillValid(player);
	}

	public float getLitProgress() {
		int lit = this.data.get(0);
		int total = this.data.get(1);
		return total != 0 && lit != 0 ? Mth.clamp((float) lit / (float) total, 0.0F, 1.0F) : 0.0F;
	}

	public float getBurnProgress() {
		int burn = this.data.get(2);
		int total = this.data.get(3);
		return total != 0 && burn != 0 ? Mth.clamp((float) burn / (float) total, 0.0F, 1.0F) : 0.0F;
	}

	public float getOverheatProgress() {
		int overheat = this.data.get(4);
		return Mth.clamp((float) overheat / AlloyFurnaceBlockEntity.getTotalOverheatTicks(), 0.0F, 1.0F);
	}

	public float getCoolingProgress() {
		int cooling = this.data.get(5);
		int total = this.data.get(6);
		return total != 0 && cooling != 0 ? Mth.clamp((float) cooling / (float) total, 0.0F, 1.0F) : 0.0F;
	}

	@Override
	public ItemStack quickMoveStack(Player player, int index) {
		ItemStack stack = ItemStack.EMPTY;
		Slot slot = this.slots.get(index);
		if (slot != null && slot.hasItem()) {
			ItemStack slotItem = slot.getItem();
			stack = slotItem.copy();
			if (index >= RESULT_SLOT_START && index < RESULT_SLOT_END) {
				if (!this.moveItemStackTo(slotItem, INV_SLOT_START, USE_ROW_SLOT_END, true)) {
					return ItemStack.EMPTY;
				}

				slot.onQuickCraft(slotItem, stack);
			} else if (index >= INV_SLOT_START && index < USE_ROW_SLOT_END) {
				if (!((AlloyFurnaceBlockEntity.isFuel(slotItem) && this.moveItemStackTo(slotItem, FUEL_SLOT, FUEL_SLOT + 1, false)) || (AlloyFurnaceBlockEntity.isCoolingItem(slotItem) && this.moveItemStackTo(slotItem, COOLING_SLOT, COOLING_SLOT + 1, false)) || this.moveItemStackTo(slotItem, INGREDIENT_SLOT_START, INGREDIENT_SLOT_END, false))) {
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
}

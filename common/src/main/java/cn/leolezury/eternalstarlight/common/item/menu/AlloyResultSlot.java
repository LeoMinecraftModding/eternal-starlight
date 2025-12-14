package cn.leolezury.eternalstarlight.common.item.menu;

import net.minecraft.world.Container;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;

public class AlloyResultSlot extends Slot {
	private final Player player;
	private int removeCount;

	public AlloyResultSlot(Player player, Container container, int slot, int xPosition, int yPosition) {
		super(container, slot, xPosition, yPosition);
		this.player = player;
	}

	@Override
	public boolean mayPlace(ItemStack stack) {
		return false;
	}

	@Override
	public ItemStack remove(int amount) {
		if (this.hasItem()) {
			this.removeCount = this.removeCount + Math.min(amount, this.getItem().getCount());
		}

		return super.remove(amount);
	}

	@Override
	protected void onQuickCraft(ItemStack stack, int amount) {
		this.removeCount += amount;
		this.checkTakeAchievements(stack);
	}

	@Override
	protected void onSwapCraft(int numItemsCrafted) {
		this.removeCount += numItemsCrafted;
	}

	@Override
	protected void checkTakeAchievements(ItemStack stack) {
		if (this.removeCount > 0) {
			stack.onCraftedBy(this.player.level(), this.player, this.removeCount);
		}

		this.removeCount = 0;
	}

	@Override
	public void onTake(Player player, ItemStack stack) {
		super.onTake(player, stack);
		this.checkTakeAchievements(stack);
	}
}

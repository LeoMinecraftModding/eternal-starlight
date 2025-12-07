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
		// TODO: WIP
        /*CraftingInput.Positioned positionedCraftInput = this.craftSlots.asPositionedCraftInput();
        CraftingInput craftingInput = positionedCraftInput.input();
        int left = positionedCraftInput.left();
        int top = positionedCraftInput.top();
        NonNullList<ItemStack> remains = player.level().getRecipeManager().getRemainingItemsFor(ESRecipes.ALLOY.get(), craftingInput, player.level());

        for (int y = 0; y < craftingInput.height(); y++) {
            for (int x = 0; x < craftingInput.width(); x++) {
                int index = x + left + (y + top) * this.craftSlots.getWidth();
                ItemStack craftItem = this.craftSlots.getItem(index);
                ItemStack remainingItem = remains.get(x + y * craftingInput.width());
                if (!craftItem.isEmpty()) {
                    this.craftSlots.removeItem(index, 1);
                    craftItem = this.craftSlots.getItem(index);
                }

                if (!remainingItem.isEmpty()) {
                    if (craftItem.isEmpty()) {
                        this.craftSlots.setItem(index, remainingItem);
                    } else if (ItemStack.isSameItemSameComponents(craftItem, remainingItem)) {
                        remainingItem.grow(craftItem.getCount());
                        this.craftSlots.setItem(index, remainingItem);
                    } else if (!this.player.getInventory().add(remainingItem)) {
                        this.player.drop(remainingItem, false);
                    }
                }
            }
        }*/
	}
}

package cn.leolezury.eternalstarlight.common.block.entity;

import cn.leolezury.eternalstarlight.common.EternalStarlight;
import cn.leolezury.eternalstarlight.common.item.menu.AlloyFurnaceMenu;
import cn.leolezury.eternalstarlight.common.item.recipe.AlloyRecipe;
import cn.leolezury.eternalstarlight.common.platform.ESPlatform;
import cn.leolezury.eternalstarlight.common.registry.ESBlockEntities;
import cn.leolezury.eternalstarlight.common.registry.ESRecipes;
import cn.leolezury.eternalstarlight.common.util.ESTags;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.util.Mth;
import net.minecraft.world.ContainerHelper;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.CraftingInput;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.item.crafting.RecipeManager;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BaseContainerBlockEntity;
import net.minecraft.world.level.block.entity.FurnaceBlockEntity;
import net.minecraft.world.level.block.state.BlockState;

import java.util.List;
import java.util.Optional;

// TODO: WIP
public class AlloyFurnaceBlockEntity extends BaseContainerBlockEntity {
	private static final String TAG_LIT_TICKS = "lit_ticks";
	private static final String TAG_TOTAL_LIT_TICKS = "total_lit_ticks";
	private static final String TAG_BURN_TICKS = "burn_ticks";
	private static final String TAG_TOTAL_BURN_TICKS = "total_burn_ticks";
	private static final String TAG_OVERHEAT_TICKS = "overheat_ticks";
	private static final String TAG_COOLING_TICKS = "cooling_ticks";

	protected final ContainerData dataAccess = new ContainerData() {
		@Override
		public int get(int id) {
			return switch (id) {
				case 0 -> {
					if (totalLitTicks > Short.MAX_VALUE) {
						yield Mth.floor(((double) litTicks / totalLitTicks) * Short.MAX_VALUE);
					}
					yield AlloyFurnaceBlockEntity.this.litTicks;
				}
				case 1 -> Math.min(AlloyFurnaceBlockEntity.this.totalLitTicks, Short.MAX_VALUE);
				case 2 -> AlloyFurnaceBlockEntity.this.burnTicks;
				case 3 -> AlloyFurnaceBlockEntity.this.totalBurnTicks;
				case 4 -> AlloyFurnaceBlockEntity.this.overheatTicks;
				case 5 -> AlloyFurnaceBlockEntity.this.coolingTicks;
				default -> 0;
			};
		}

		@Override
		public void set(int id, int value) {
			switch (id) {
				case 0:
					AlloyFurnaceBlockEntity.this.litTicks = value;
					break;
				case 1:
					AlloyFurnaceBlockEntity.this.totalLitTicks = value;
					break;
				case 2:
					AlloyFurnaceBlockEntity.this.burnTicks = value;
					break;
				case 3:
					AlloyFurnaceBlockEntity.this.totalBurnTicks = value;
				case 4:
					AlloyFurnaceBlockEntity.this.overheatTicks = value;
				case 5:
					AlloyFurnaceBlockEntity.this.coolingTicks = value;
			}
		}

		@Override
		public int getCount() {
			return 6;
		}
	};

	private final RecipeManager.CachedCheck<CraftingInput, AlloyRecipe> quickCheck;

	private NonNullList<ItemStack> items = NonNullList.withSize(14, ItemStack.EMPTY);
	private int litTicks, totalLitTicks, burnTicks, totalBurnTicks, overheatTicks, coolingTicks;

	public AlloyFurnaceBlockEntity(BlockPos pos, BlockState state) {
		super(ESBlockEntities.ALLOY_FURNACE.get(), pos, state);
		this.quickCheck = RecipeManager.createCheck(ESRecipes.ALLOY.get());
	}

	public static void tick(Level level, BlockPos pos, BlockState state, AlloyFurnaceBlockEntity entity) {
		if (!level.isClientSide) {
			boolean changed = false;
			if (entity.litTicks > 0) {
				entity.litTicks--;
				if (entity.overheatTicks < 1200 * 3) {
					entity.overheatTicks++;
				}
			}
			if (entity.coolingTicks > 0) {
				entity.coolingTicks--;
			}
			if (entity.litTicks <= 0) {
				entity.totalLitTicks = 0;
			}
			List<ItemStack> ingredients = entity.getIngredientItems();
			RecipeHolder<AlloyRecipe> recipeHolder = entity.quickCheck.getRecipeFor(CraftingInput.of(3, 3, ingredients), level).orElse(null);
			if (entity.canBurn() && entity.litTicks <= 0) {
				ItemStack fuel = entity.getItem(AlloyFurnaceMenu.FUEL_SLOT);
				if (!fuel.isEmpty() && isFuel(fuel)) {
					entity.totalLitTicks = ESPlatform.INSTANCE.getBurnTime(fuel, ESRecipes.ALLOY.get());
					entity.litTicks = entity.totalLitTicks;
					fuel.shrink(1);
					// TODO: wtf is this plz test it out
					if (ESPlatform.INSTANCE.hasCraftingRemainingItem(fuel)) {
						ESPlatform.INSTANCE.getCraftingRemainingItem(fuel).ifPresent(remaining -> {
							if (fuel.isEmpty() || ItemStack.isSameItemSameComponents(fuel, remaining)) {
								int remainingCount = (fuel.getCount() + remaining.getCount()) - remaining.getMaxStackSize();
								entity.setItem(AlloyFurnaceMenu.FUEL_SLOT, remaining.copyWithCount(Math.min(fuel.getCount() + remaining.getCount(), remaining.getMaxStackSize())));
								if (remainingCount > 0) {
									Block.popResource(level, pos, remaining.copyWithCount(remainingCount));
								}
							} else {
								Block.popResource(level, pos, remaining.copy());
							}
						});
					}
					changed = true;
				}
			}
			entity.burnTicks += (entity.litTicks > 0 && entity.canBurn()) ? 1 : -1;
			entity.burnTicks = Mth.clamp(entity.burnTicks, 0, entity.totalBurnTicks);
			if (entity.canBurn() && entity.burnTicks == entity.totalBurnTicks && recipeHolder != null) {
				// TODO: set the results, shrink the ingredients, and don't forget about the CraftingRemainingItem
			}
			if (changed) {
				entity.setChanged();
			}
		}
	}

	private List<ItemStack> getIngredientItems() {
		return getItems().subList(AlloyFurnaceMenu.INGREDIENT_SLOT_START, AlloyFurnaceMenu.INGREDIENT_SLOT_END);
	}

	private boolean canBurn() {
		if (level == null) {
			return false;
		}
		Optional<RecipeHolder<AlloyRecipe>> recipeHolder = quickCheck.getRecipeFor(CraftingInput.of(3, 3, getIngredientItems()), level);
		if (getIngredientItems().stream().anyMatch(stack -> !stack.isEmpty()) && recipeHolder.isPresent()) {
			AlloyRecipe recipe = recipeHolder.get().value();
			for (int i = 0; i < Math.min(recipe.results().size(), 3); i++) {
				ItemStack stack = recipe.results().get(i);
				if (stack.isEmpty()) {
					return false;
				} else {
					ItemStack existingResult = getItem(AlloyFurnaceMenu.RESULT_SLOT_START + i);
					if (!existingResult.isEmpty() && !ItemStack.isSameItemSameComponents(existingResult, stack)) {
						return false;
					} else if (existingResult.getCount() + stack.getCount() > stack.getMaxStackSize()) {
						return false;
					}
				}
			}
			return true;
		} else {
			return false;
		}
	}

	public static boolean isFuel(ItemStack stack) {
		return FurnaceBlockEntity.isFuel(stack);
	}

	public static boolean isFreezingItem(ItemStack stack) {
		return stack.is(ESTags.Items.COOLS_ALLOY_FURNACE);
	}

	@Override
	public void setItem(int index, ItemStack stack) {
		ItemStack original = this.items.get(index);
		boolean unchanged = !stack.isEmpty() && ItemStack.isSameItemSameComponents(original, stack);
		if (level != null && index >= AlloyFurnaceMenu.INGREDIENT_SLOT_START && index < AlloyFurnaceMenu.INGREDIENT_SLOT_END && !unchanged) {
			this.quickCheck.getRecipeFor(CraftingInput.of(3, 3, getIngredientItems()), level).ifPresentOrElse(holder -> this.totalBurnTicks = holder.value().burnTime(), () -> this.totalBurnTicks = 0);
			this.burnTicks = 0;
			this.setChanged();
		}
		super.setItem(index, stack);
	}

	@Override
	public boolean canPlaceItem(int index, ItemStack stack) {
		if (index == AlloyFurnaceMenu.FUEL_SLOT && !isFuel(stack)) {
			return false;
		}
		if (index == AlloyFurnaceMenu.FREEZING_SLOT && !isFreezingItem(stack)) {
			return false;
		}
		return super.canPlaceItem(index, stack);
	}

	@Override
	public int getContainerSize() {
		return 14;
	}

	@Override
	public void loadAdditional(CompoundTag compoundTag, HolderLookup.Provider provider) {
		super.loadAdditional(compoundTag, provider);
		ContainerHelper.loadAllItems(compoundTag, this.items, provider);
	}

	@Override
	protected void saveAdditional(CompoundTag compoundTag, HolderLookup.Provider provider) {
		super.saveAdditional(compoundTag, provider);
		ContainerHelper.saveAllItems(compoundTag, this.items, provider);
	}

	@Override
	protected Component getDefaultName() {
		return Component.translatable("container." + EternalStarlight.ID + ".alloy_furnace");
	}

	@Override
	protected NonNullList<ItemStack> getItems() {
		return items;
	}

	@Override
	protected void setItems(NonNullList<ItemStack> items) {
		this.items = items;
	}

	@Override
	protected AbstractContainerMenu createMenu(int id, Inventory player) {
		return new AlloyFurnaceMenu(id, player.player.getInventory(), this, dataAccess);
	}
}

package cn.leolezury.eternalstarlight.common.block.entity;

import cn.leolezury.eternalstarlight.common.EternalStarlight;
import cn.leolezury.eternalstarlight.common.block.AlloyFurnaceBlock;
import cn.leolezury.eternalstarlight.common.config.ESConfig;
import cn.leolezury.eternalstarlight.common.item.menu.AlloyFurnaceMenu;
import cn.leolezury.eternalstarlight.common.item.recipe.AlloyRecipe;
import cn.leolezury.eternalstarlight.common.particle.ESExplosionParticleOptions;
import cn.leolezury.eternalstarlight.common.platform.ESPlatform;
import cn.leolezury.eternalstarlight.common.registry.ESBlockEntities;
import cn.leolezury.eternalstarlight.common.registry.ESParticles;
import cn.leolezury.eternalstarlight.common.registry.ESRecipes;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.world.ContainerHelper;
import net.minecraft.world.WorldlyContainer;
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
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Optional;

public class AlloyFurnaceBlockEntity extends BaseContainerBlockEntity implements WorldlyContainer {
	private static final String TAG_LIT_TICKS = "lit_ticks";
	private static final String TAG_TOTAL_LIT_TICKS = "total_lit_ticks";
	private static final String TAG_BURN_TICKS = "burn_ticks";
	private static final String TAG_TOTAL_BURN_TICKS = "total_burn_ticks";
	private static final String TAG_OVERHEAT_TICKS = "overheat_ticks";
	private static final String TAG_COOLING_TICKS = "cooling_ticks";
	private static final String TAG_TOTAL_COOLING_TICKS = "total_cooling_ticks";
	private static final String TAG_COOLING_EFFICIENCY = "cooling_efficiency";

	private static final int[] SLOTS_FOR_DOWN = new int[]{
		AlloyFurnaceMenu.RESULT_SLOT_START,
		AlloyFurnaceMenu.RESULT_SLOT_START + 1,
		AlloyFurnaceMenu.RESULT_SLOT_START + 2
	};
	private static final int[] SLOTS_FOR_UP = new int[]{
		AlloyFurnaceMenu.INGREDIENT_SLOT_START,
		AlloyFurnaceMenu.INGREDIENT_SLOT_START + 1,
		AlloyFurnaceMenu.INGREDIENT_SLOT_START + 2,
		AlloyFurnaceMenu.INGREDIENT_SLOT_START + 3,
		AlloyFurnaceMenu.INGREDIENT_SLOT_START + 4,
		AlloyFurnaceMenu.INGREDIENT_SLOT_START + 5,
		AlloyFurnaceMenu.INGREDIENT_SLOT_START + 6,
		AlloyFurnaceMenu.INGREDIENT_SLOT_START + 7,
		AlloyFurnaceMenu.INGREDIENT_SLOT_START + 8
	};
	private static final int[] SLOTS_FOR_FRONT_AND_BACK = new int[]{
		AlloyFurnaceMenu.FUEL_SLOT
	};
	private static final int[] SLOTS_FOR_SIDES = new int[]{
		AlloyFurnaceMenu.COOLING_SLOT
	};

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
				case 6 -> AlloyFurnaceBlockEntity.this.totalCoolingTicks;
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
				case 6:
					AlloyFurnaceBlockEntity.this.totalCoolingTicks = value;
			}
		}

		@Override
		public int getCount() {
			return 7;
		}
	};

	private final RecipeManager.CachedCheck<CraftingInput, AlloyRecipe> quickCheck;

	private NonNullList<ItemStack> items = NonNullList.withSize(14, ItemStack.EMPTY);
	private int litTicks, totalLitTicks, burnTicks, totalBurnTicks, overheatTicks, coolingTicks, totalCoolingTicks, coolingEfficiency;
	private int checkStructureTicks;
	public float oldClientOverheatAmplitude, clientOverheatAmplitude;
	public int oldClientAnimationTicks, clientAnimationTicks;

	public AlloyFurnaceBlockEntity(BlockPos pos, BlockState state) {
		super(ESBlockEntities.ALLOY_FURNACE.get(), pos, state);
		this.quickCheck = RecipeManager.createCheck(ESRecipes.ALLOY.get());
	}

	public static int getTotalOverheatTicks() {
		return ESConfig.INSTANCE.itemsConfig.alloyFurnace.totalOverheatTicks();
	}

	public static int getOverheatAnimationThreshold() {
		return getTotalOverheatTicks() * 5 / 6;
	}

	public boolean isLit() {
		return this.litTicks > 0;
	}

	public boolean isCooling() {
		return this.coolingTicks > 0;
	}

	public static void tick(Level level, BlockPos pos, BlockState state, AlloyFurnaceBlockEntity entity) {
		if (!level.isClientSide) {
			boolean oldLit = entity.litTicks > 0;
			boolean oldCooling = entity.coolingTicks > 0;
			boolean oldShowOverheatAnimation = entity.overheatTicks >= getOverheatAnimationThreshold();
			boolean changed = false;
			if (entity.litTicks > 0) {
				entity.litTicks--;
			}
			if (entity.coolingTicks > 0) {
				entity.coolingTicks--;
			}
			if (entity.litTicks <= 0) {
				entity.totalLitTicks = 0;
			}
			if (entity.coolingTicks <= 0) {
				entity.totalCoolingTicks = 0;
				entity.coolingEfficiency = 0;
			}
			List<ItemStack> ingredients = entity.getIngredientItems();
			RecipeHolder<AlloyRecipe> recipeHolder = entity.quickCheck.getRecipeFor(CraftingInput.of(3, 3, ingredients), level).orElse(null);
			if (entity.canBurn() && entity.litTicks <= 0) {
				ItemStack fuel = entity.getItem(AlloyFurnaceMenu.FUEL_SLOT);
				if (!fuel.isEmpty() && isFuel(fuel)) {
					entity.totalLitTicks = ESPlatform.INSTANCE.getBurnTime(fuel, ESRecipes.ALLOY.get());
					entity.litTicks = entity.totalLitTicks;
					ItemStack originalFuel = fuel.copy();
					fuel.shrink(1);
					if (ESPlatform.INSTANCE.hasCraftingRemainingItem(originalFuel)) {
						ESPlatform.INSTANCE.getCraftingRemainingItem(originalFuel).ifPresent(remaining -> {
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
			if (entity.overheatTicks > 0 && entity.coolingTicks <= 0) {
				ItemStack coolingItem = entity.getItem(AlloyFurnaceMenu.COOLING_SLOT);
				if (!coolingItem.isEmpty() && isCoolingItem(coolingItem)) {
					entity.totalCoolingTicks = AlloyFurnaceBlock.getCoolDuration(coolingItem.getItem());
					entity.coolingTicks = entity.totalCoolingTicks;
					entity.coolingEfficiency = AlloyFurnaceBlock.getCoolEfficiency(coolingItem.getItem());
					coolingItem.shrink(1);
					if (ESPlatform.INSTANCE.hasCraftingRemainingItem(coolingItem)) {
						ESPlatform.INSTANCE.getCraftingRemainingItem(coolingItem).ifPresent(remaining -> {
							if (coolingItem.isEmpty() || ItemStack.isSameItemSameComponents(coolingItem, remaining)) {
								int remainingCount = (coolingItem.getCount() + remaining.getCount()) - remaining.getMaxStackSize();
								entity.setItem(AlloyFurnaceMenu.COOLING_SLOT, remaining.copyWithCount(Math.min(coolingItem.getCount() + remaining.getCount(), remaining.getMaxStackSize())));
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
			if (entity.litTicks > 0 && (entity.coolingTicks <= 0 || entity.litTicks % entity.coolingEfficiency == 0)) {
				entity.overheatTicks += 1;
			}
			if (entity.litTicks <= 0) {
				entity.overheatTicks -= (1 + (entity.coolingTicks > 0 ? entity.coolingEfficiency : 0));
			}
			entity.overheatTicks = Mth.clamp(entity.overheatTicks, 0, getTotalOverheatTicks());
			if (entity.canBurn() && entity.burnTicks == entity.totalBurnTicks && recipeHolder != null) {
				AlloyRecipe recipe = recipeHolder.value();
				for (int i = 0; i < Math.min(recipe.results().size(), 3); i++) {
					AlloyRecipe.Result result = recipe.results().get(i);
					ItemStack stack = result.getResultItem(level.getRandom());
					if (stack.isEmpty()) {
						break;
					} else {
						ItemStack existingResult = entity.getItem(AlloyFurnaceMenu.RESULT_SLOT_START + i);
						entity.setItem(AlloyFurnaceMenu.RESULT_SLOT_START + i, stack.copyWithCount(stack.getCount() + existingResult.getCount()));
					}
				}
				CraftingInput.Positioned craftingInput = CraftingInput.ofPositioned(3, 3, ingredients);
				NonNullList<ItemStack> remaining = recipeHolder.value().getRemainingItems(craftingInput.input());
				for (int y = 0; y < craftingInput.input().height(); y++) {
					for (int x = 0; x < craftingInput.input().width(); x++) {
						int index = (x + craftingInput.left()) + (y + craftingInput.top()) * 3;
						ItemStack craftItem = entity.getItem(AlloyFurnaceMenu.INGREDIENT_SLOT_START + index);
						ItemStack remainingItem = remaining.get(x + y * craftingInput.input().width());
						if (!craftItem.isEmpty()) {
							craftItem.shrink(1);
							entity.setItem(AlloyFurnaceMenu.INGREDIENT_SLOT_START + index, craftItem);
						}
						if (!remainingItem.isEmpty()) {
							if (craftItem.isEmpty() || ItemStack.isSameItemSameComponents(craftItem, remainingItem)) {
								int remainingCount = (craftItem.getCount() + remainingItem.getCount()) - remainingItem.getMaxStackSize();
								entity.setItem(AlloyFurnaceMenu.INGREDIENT_SLOT_START + index, remainingItem.copyWithCount(Math.min(craftItem.getCount() + remainingItem.getCount(), remainingItem.getMaxStackSize())));
								if (remainingCount > 0) {
									Block.popResource(level, pos, remainingItem.copyWithCount(remainingCount));
								}
							} else {
								Block.popResource(level, pos, remainingItem.copy());
							}
						}
					}
				}
				entity.burnTicks = 0;
				changed = true;
			}
			if ((entity.litTicks > 0) != oldLit || (entity.coolingTicks > 0) != oldCooling || (entity.overheatTicks >= getOverheatAnimationThreshold()) != oldShowOverheatAnimation) {
				level.sendBlockUpdated(pos, state, state, 3);
				changed = true;
			}
			if (changed) {
				entity.setChanged();
			}
			entity.checkStructureTicks++;
			if (entity.checkStructureTicks > 20 && state.getBlock() instanceof AlloyFurnaceBlock block) {
				block.checkStructure(level, pos);
				entity.checkStructureTicks = 0;
			}
			if (entity.overheatTicks >= getTotalOverheatTicks()) {
				level.destroyBlock(pos, false);
				if (level instanceof ServerLevel serverLevel) {
					serverLevel.sendParticles(ESExplosionParticleOptions.LAVA, pos.getX() + level.getRandom().nextFloat(), pos.getY() + level.getRandom().nextFloat() + 1.5, pos.getZ() + level.getRandom().nextFloat(), 20, 1.5, 1.5, 1.5, 0);
					serverLevel.sendParticles(ESParticles.SMOKE_TRAIL.get(), pos.getX() + level.getRandom().nextFloat(), pos.getY() + level.getRandom().nextFloat(), pos.getZ() + level.getRandom().nextFloat(), 20, 0, 0, 0, 0.5);
				}
				level.explode(null, null, null, pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5, ESConfig.INSTANCE.itemsConfig.alloyFurnace.explosionRadius(), true, Level.ExplosionInteraction.BLOCK, ESExplosionParticleOptions.LAVA, ESExplosionParticleOptions.LAVA, SoundEvents.GENERIC_EXPLODE);
			}
		} else {
			entity.oldClientOverheatAmplitude = entity.clientOverheatAmplitude;
			entity.clientOverheatAmplitude += entity.overheatTicks >= getOverheatAnimationThreshold() ? 0.003f : -0.003f;
			entity.clientOverheatAmplitude = Mth.clamp(entity.clientOverheatAmplitude, 0, 1);
			entity.oldClientAnimationTicks = entity.clientAnimationTicks;
			entity.clientAnimationTicks++;
			if (level.getRandom().nextDouble() < 0.1) {
				level.playLocalSound(pos.getX() + 0.5, pos.getY(), pos.getX() + 0.5, SoundEvents.FURNACE_FIRE_CRACKLE, SoundSource.BLOCKS, 1.0F, 1.0F, false);
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
				AlloyRecipe.Result result = recipe.results().get(i);
				ItemStack stack = result.getMaxResultItem();
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

	public static boolean isCoolingItem(ItemStack stack) {
		return AlloyFurnaceBlock.getCoolingItem(stack.getItem()) != null;
	}

	@Override
	public void setItem(int index, ItemStack stack) {
		ItemStack original = this.items.get(index);
		boolean unchanged = !stack.isEmpty() && ItemStack.isSameItemSameComponents(original, stack);
		if (level != null && index >= AlloyFurnaceMenu.INGREDIENT_SLOT_START && index < AlloyFurnaceMenu.INGREDIENT_SLOT_END && !unchanged) {
			List<ItemStack> ingredients = getItems().subList(AlloyFurnaceMenu.INGREDIENT_SLOT_START, AlloyFurnaceMenu.INGREDIENT_SLOT_END);
			ingredients.set(index - AlloyFurnaceMenu.INGREDIENT_SLOT_START, stack);
			this.quickCheck.getRecipeFor(CraftingInput.of(3, 3, ingredients), level).ifPresentOrElse(holder -> this.totalBurnTicks = holder.value().burnTime(), () -> this.totalBurnTicks = 0);
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
		if (index == AlloyFurnaceMenu.COOLING_SLOT && !isCoolingItem(stack)) {
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
		this.litTicks = compoundTag.getShort(TAG_LIT_TICKS);
		this.totalLitTicks = compoundTag.getShort(TAG_TOTAL_LIT_TICKS);
		this.burnTicks = compoundTag.getShort(TAG_BURN_TICKS);
		this.totalBurnTicks = compoundTag.getShort(TAG_TOTAL_BURN_TICKS);
		this.overheatTicks = compoundTag.getShort(TAG_OVERHEAT_TICKS);
		this.coolingTicks = compoundTag.getShort(TAG_COOLING_TICKS);
		this.totalCoolingTicks = compoundTag.getShort(TAG_TOTAL_COOLING_TICKS);
		this.coolingEfficiency = compoundTag.getShort(TAG_COOLING_EFFICIENCY);
	}

	@Override
	protected void saveAdditional(CompoundTag compoundTag, HolderLookup.Provider provider) {
		super.saveAdditional(compoundTag, provider);
		ContainerHelper.saveAllItems(compoundTag, this.items, provider);
		compoundTag.putShort(TAG_LIT_TICKS, (short) this.litTicks);
		compoundTag.putShort(TAG_TOTAL_LIT_TICKS, (short) this.totalLitTicks);
		compoundTag.putShort(TAG_BURN_TICKS, (short) this.burnTicks);
		compoundTag.putShort(TAG_TOTAL_BURN_TICKS, (short) this.totalBurnTicks);
		compoundTag.putShort(TAG_OVERHEAT_TICKS, (short) this.overheatTicks);
		compoundTag.putShort(TAG_COOLING_TICKS, (short) this.coolingTicks);
		compoundTag.putShort(TAG_TOTAL_COOLING_TICKS, (short) this.totalCoolingTicks);
		compoundTag.putShort(TAG_COOLING_EFFICIENCY, (short) this.coolingEfficiency);
	}

	@Nullable
	@Override
	public Packet<ClientGamePacketListener> getUpdatePacket() {
		return ClientboundBlockEntityDataPacket.create(this);
	}

	@Override
	public CompoundTag getUpdateTag(HolderLookup.Provider provider) {
		return saveCustomOnly(provider);
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

	@Override
	public int[] getSlotsForFace(Direction side) {
		if (side == Direction.DOWN) {
			return SLOTS_FOR_DOWN;
		} else if (side == Direction.UP) {
			return SLOTS_FOR_UP;
		} else if (side.getAxis() == getBlockState().getValue(AlloyFurnaceBlock.FACING).getAxis()) {
			return SLOTS_FOR_FRONT_AND_BACK;
		} else {
			return SLOTS_FOR_SIDES;
		}
	}

	@Override
	public boolean canPlaceItemThroughFace(int index, ItemStack stack, @Nullable Direction direction) {
		if (index == AlloyFurnaceMenu.FUEL_SLOT || index == AlloyFurnaceMenu.COOLING_SLOT || level == null) {
			return canPlaceItem(index, stack);
		} else {
			Optional<RecipeHolder<AlloyRecipe>> recipeHolder = quickCheck.getRecipeFor(CraftingInput.of(3, 3, getIngredientItems()), level);
			if (recipeHolder.isPresent()) {
				return !getItem(index).isEmpty() && canPlaceItem(index, stack);
			} else {
				return canPlaceItem(index, stack);
			}
		}
	}

	@Override
	public boolean canTakeItemThroughFace(int index, ItemStack stack, Direction direction) {
		return true;
	}
}

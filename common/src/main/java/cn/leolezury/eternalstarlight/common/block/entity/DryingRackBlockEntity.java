package cn.leolezury.eternalstarlight.common.block.entity;

import cn.leolezury.eternalstarlight.common.block.DryingRackBlock;
import cn.leolezury.eternalstarlight.common.item.recipe.DryingRecipe;
import cn.leolezury.eternalstarlight.common.item.recipe.DryingRecipeInput;
import cn.leolezury.eternalstarlight.common.registry.ESBlockEntities;
import cn.leolezury.eternalstarlight.common.registry.ESRecipes;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.world.ContainerHelper;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.item.crafting.RecipeManager;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Optional;

public class DryingRackBlockEntity extends SimpleContainerBlockEntity {
	private static final String TAG_DRYING_TICKS = "drying_ticks";

	protected DryingRackBlockEntity(BlockEntityType<?> entityType, BlockPos pos, BlockState state) {
		super(entityType, pos, state);
	}

	public DryingRackBlockEntity(BlockPos blockPos, BlockState blockState) {
		this(ESBlockEntities.DRYING_RACK.get(), blockPos, blockState);
	}

	private final RecipeManager.CachedCheck<DryingRecipeInput, DryingRecipe> quickCheck = RecipeManager.createCheck(ESRecipes.DRYING.get());
	private boolean lastLit;
	private int dryingTicks = 0;
	private NonNullList<ItemStack> items = NonNullList.withSize(1, ItemStack.EMPTY);

	public ItemStack getItem() {
		return items.getFirst();
	}

	public void setItem(ItemStack item) {
		setItem(0, item);
		this.dryingTicks = 0;
	}

	public boolean canBeDried(ItemStack stack, boolean fireBelow) {
		if (level == null) {
			return false;
		}
		List<RecipeHolder<DryingRecipe>> list = level.getRecipeManager().getAllRecipesFor(ESRecipes.DRYING.get());
		for (RecipeHolder<DryingRecipe> holder : list) {
			DryingRecipe recipe = holder.value();
			if (fireBelow == recipe.fireBelow() && recipe.input().test(stack)) {
				return true;
			}
		}
		return false;
	}

	public static void tick(Level level, BlockPos pos, BlockState state, DryingRackBlockEntity entity) {
		if (!level.isClientSide) {
			if (!entity.getItem().isEmpty()) {
				boolean lit = state.getValue(DryingRackBlock.LIT);
				if (entity.lastLit != lit) {
					entity.dryingTicks = 0;
					entity.lastLit = lit;
				}
				Optional<RecipeHolder<DryingRecipe>> optionalRecipe = entity.quickCheck.getRecipeFor(new DryingRecipeInput(entity.items.getFirst(), lit), level);
				if (optionalRecipe.isPresent()) {
					DryingRecipe recipe = optionalRecipe.get().value();
					entity.dryingTicks++;
					if (entity.dryingTicks > recipe.durationTicks()) {
						entity.dryingTicks = 0;
						entity.setItem(recipe.output().copy());
					}
				} else {
					entity.dryingTicks = 0;
				}
			} else {
				entity.dryingTicks = 0;
			}
		}
	}

	@Nullable
	@Override
	public Packet<ClientGamePacketListener> getUpdatePacket() {
		return ClientboundBlockEntityDataPacket.create(this);
	}

	@Override
	public CompoundTag getUpdateTag(HolderLookup.Provider provider) {
		return saveWithFullMetadata(provider);
	}

	@Override
	public void setChanged() {
		super.setChanged();
		if (getLevel() != null) {
			this.getLevel().sendBlockUpdated(this.getBlockPos(), this.getBlockState(), this.getBlockState(), 3);
		}
	}

	@Override
	public void loadAdditional(CompoundTag compoundTag, HolderLookup.Provider provider) {
		super.loadAdditional(compoundTag, provider);
		this.dryingTicks = compoundTag.getInt(TAG_DRYING_TICKS);
		this.items = NonNullList.withSize(this.getContainerSize(), ItemStack.EMPTY);
		ContainerHelper.loadAllItems(compoundTag, this.items, provider);
	}

	@Override
	protected void saveAdditional(CompoundTag compoundTag, HolderLookup.Provider provider) {
		super.saveAdditional(compoundTag, provider);
		compoundTag.putInt(TAG_DRYING_TICKS, this.dryingTicks);
		ContainerHelper.saveAllItems(compoundTag, this.items, provider);
	}

	@Override
	protected NonNullList<ItemStack> getItems() {
		return items;
	}

	@Override
	public int getContainerSize() {
		return 1;
	}

	@Override
	public int getMaxStackSize() {
		return 1;
	}

	@Override
	public boolean canPlaceItem(int index, ItemStack stack) {
		BlockState state = getBlockState();
		return canBeDried(stack, state.hasProperty(DryingRackBlock.LIT) && state.getValue(DryingRackBlock.LIT)) && this.getItem(index).isEmpty() && stack.getCount() <= this.getMaxStackSize();
	}
}

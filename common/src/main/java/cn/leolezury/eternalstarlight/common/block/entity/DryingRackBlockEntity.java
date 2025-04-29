package cn.leolezury.eternalstarlight.common.block.entity;

import cn.leolezury.eternalstarlight.common.block.DryingRackBlock;
import cn.leolezury.eternalstarlight.common.item.recipe.DryingRecipe;
import cn.leolezury.eternalstarlight.common.item.recipe.DryingRecipeInput;
import cn.leolezury.eternalstarlight.common.registry.ESBlockEntities;
import cn.leolezury.eternalstarlight.common.registry.ESRecipes;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.item.crafting.RecipeManager;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Optional;

public class DryingRackBlockEntity extends BlockEntity {
	private static final String TAG_DRYING_TICKS = "drying_ticks";
	private static final String TAG_ITEM = "item";

	protected DryingRackBlockEntity(BlockEntityType<?> entityType, BlockPos pos, BlockState state) {
		super(entityType, pos, state);
	}

	public DryingRackBlockEntity(BlockPos blockPos, BlockState blockState) {
		this(ESBlockEntities.DRYING_RACK.get(), blockPos, blockState);
	}

	private final RecipeManager.CachedCheck<DryingRecipeInput, DryingRecipe> quickCheck = RecipeManager.createCheck(ESRecipes.DRYING.get());
	private boolean lastLit;
	private int dryingTicks = 0;
	private ItemStack item = ItemStack.EMPTY;

	public ItemStack getItem() {
		return item;
	}

	public void setItem(ItemStack item) {
		this.item = item;
		this.dryingTicks = 0;
		markUpdated();
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
		if (!entity.item.isEmpty()) {
			boolean lit = state.getValue(DryingRackBlock.LIT);
			if (entity.lastLit != lit) {
				entity.dryingTicks = 0;
				entity.lastLit = lit;
			}
			Optional<RecipeHolder<DryingRecipe>> optionalRecipe = entity.quickCheck.getRecipeFor(new DryingRecipeInput(entity.item, lit), level);
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

	@Nullable
	@Override
	public Packet<ClientGamePacketListener> getUpdatePacket() {
		return ClientboundBlockEntityDataPacket.create(this);
	}

	@Override
	public CompoundTag getUpdateTag(HolderLookup.Provider provider) {
		return saveWithFullMetadata(provider);
	}

	private void markUpdated() {
		if (getLevel() != null) {
			this.setChanged();
			this.getLevel().sendBlockUpdated(this.getBlockPos(), this.getBlockState(), this.getBlockState(), 3);
		}
	}

	@Override
	public void loadAdditional(CompoundTag compoundTag, HolderLookup.Provider provider) {
		super.loadAdditional(compoundTag, provider);
		this.dryingTicks = compoundTag.getInt(TAG_DRYING_TICKS);
		setItem(ItemStack.parseOptional(provider, compoundTag.getCompound(TAG_ITEM)));
	}

	@Override
	protected void saveAdditional(CompoundTag compoundTag, HolderLookup.Provider provider) {
		super.saveAdditional(compoundTag, provider);
		compoundTag.putInt(TAG_DRYING_TICKS, this.dryingTicks);
		compoundTag.put(TAG_ITEM, this.item.saveOptional(provider));
	}
}

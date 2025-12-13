package cn.leolezury.eternalstarlight.common.block;

import cn.leolezury.eternalstarlight.common.block.entity.AlloyFurnaceBlockEntity;
import cn.leolezury.eternalstarlight.common.registry.ESBlockEntities;
import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.TagKey;
import net.minecraft.world.Containers;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.Map;

// TODO: WIP
public class AlloyFurnaceBlock extends BaseEntityBlock {
	public static final MapCodec<AlloyFurnaceBlock> CODEC = simpleCodec(AlloyFurnaceBlock::new);
	private static final Map<Item, AlloyFurnaceCoolingItem> COOLING_REGISTRY = new HashMap<>();

	public AlloyFurnaceBlock(Properties properties) {
		super(properties);
	}

	@Override
	protected MapCodec<AlloyFurnaceBlock> codec() {
		return CODEC;
	}

	public static void registerCoolingItem(Item item, AlloyFurnaceCoolingItem coolingItem) {
		COOLING_REGISTRY.put(item, coolingItem);
	}

	public static void registerCoolingItem(TagKey<Item> itemTag, AlloyFurnaceCoolingItem coolingItem) {
		for (Holder<Item> holder : BuiltInRegistries.ITEM.getTagOrEmpty(itemTag)) {
			COOLING_REGISTRY.put(holder.value(), coolingItem);
		}
	}

	public static AlloyFurnaceCoolingItem getCoolingItem(Item item) {
		return COOLING_REGISTRY.get(item);
	}

	public static int getCoolDuration(Item item) {
		AlloyFurnaceCoolingItem coolingItem = getCoolingItem(item);
		return coolingItem == null ? 0 : coolingItem.duration();
	}

	public static int getCoolEfficiency(Item item) {
		AlloyFurnaceCoolingItem coolingItem = getCoolingItem(item);
		return coolingItem == null ? 0 : coolingItem.efficiency();
	}

	@Override
	protected InteractionResult useWithoutItem(BlockState state, Level level, BlockPos pos, Player player, BlockHitResult hitResult) {
		if (level.isClientSide) {
			return InteractionResult.SUCCESS;
		} else {
			BlockEntity blockEntity = level.getBlockEntity(pos);
			if (blockEntity instanceof AlloyFurnaceBlockEntity entity) {
				player.openMenu(entity);
			}
			return InteractionResult.CONSUME;
		}
	}

	@Override
	protected void onRemove(BlockState state, Level level, BlockPos pos, BlockState newState, boolean isMoving) {
		if (!state.is(newState.getBlock())) {
			BlockEntity blockentity = level.getBlockEntity(pos);
			if (blockentity instanceof AlloyFurnaceBlockEntity entity) {
				if (level instanceof ServerLevel) {
					Containers.dropContents(level, pos, entity);
				}
				super.onRemove(state, level, pos, newState, isMoving);
			} else {
				super.onRemove(state, level, pos, newState, isMoving);
			}
		}
	}

	@Override
	public RenderShape getRenderShape(BlockState blockState) {
		return RenderShape.MODEL;
	}

	@Nullable
	@Override
	public BlockEntity newBlockEntity(BlockPos blockPos, BlockState blockState) {
		return new AlloyFurnaceBlockEntity(blockPos, blockState);
	}

	@Nullable
	@Override
	public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState blockState, BlockEntityType<T> blockEntityType) {
		return createTickerHelper(blockEntityType, ESBlockEntities.ALLOY_FURNACE.get(), AlloyFurnaceBlockEntity::tick);
	}
}

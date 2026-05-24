package cn.leolezury.eternalstarlight.common.item.misc;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.CollisionContext;
import org.jetbrains.annotations.Nullable;

import java.util.Map;

public class StandingAndHangingVineBlockItem extends BlockItem {
	private final Block hangingBlock;

	public StandingAndHangingVineBlockItem(Block block, Block hangingBlock, Properties properties) {
		super(block, properties);
		this.hangingBlock = hangingBlock;
	}

	protected boolean canPlace(LevelReader level, BlockState state, BlockPos pos) {
		return state.canSurvive(level, pos);
	}

	@Nullable
	@Override
	protected BlockState getPlacementState(BlockPlaceContext context) {
		BlockState hangingState = this.hangingBlock.getStateForPlacement(context);
		BlockState result = null;
		LevelReader level = context.getLevel();
		BlockPos pos = context.getClickedPos();

		for (Direction direction : context.getNearestLookingDirections()) {
			BlockState candidate;
			if (direction == Direction.DOWN) {
				candidate = this.getBlock().getStateForPlacement(context);
			} else {
				candidate = hangingState;
			}
			if (candidate != null && this.canPlace(level, candidate, pos)) {
				result = candidate;
				break;
			}
		}

		return result != null && level.isUnobstructed(result, pos, CollisionContext.empty()) ? result : null;
	}

	@Override
	public void registerBlocks(Map<Block, Item> blockToItemMap, Item item) {
		super.registerBlocks(blockToItemMap, item);
		blockToItemMap.put(this.hangingBlock, item);
	}
}

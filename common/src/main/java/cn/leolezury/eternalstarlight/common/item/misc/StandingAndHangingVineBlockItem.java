package cn.leolezury.eternalstarlight.common.item.misc;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;

public class StandingAndHangingVineBlockItem extends BlockItem {
	private final Block hangingBlock;

	public StandingAndHangingVineBlockItem(Block block, Block hangingBlock, Properties properties) {
		super(block, properties);
		this.hangingBlock = hangingBlock;
	}

	@Nullable
	@Override
	protected BlockState getPlacementState(BlockPlaceContext context) {
		Direction face = context.getClickedFace();
		BlockPos placePos = context.getClickedPos().relative(face);
		LevelReader level = context.getLevel();

		if (face == Direction.UP) {
			BlockState state = hangingBlock.defaultBlockState();
			if (state.canSurvive(level, placePos)) {
				return state;
			}
		}
		if (face == Direction.DOWN) {
			BlockState state = getBlock().defaultBlockState();
			if (state.canSurvive(level, placePos)) {
				return state;
			}
		}
		BlockState hangingState = hangingBlock.defaultBlockState();
		if (hangingState.canSurvive(level, placePos)) {
			return hangingState;
		}
		BlockState standingState = getBlock().defaultBlockState();
		if (standingState.canSurvive(level, placePos)) {
			return standingState;
		}
		return null;
	}
}

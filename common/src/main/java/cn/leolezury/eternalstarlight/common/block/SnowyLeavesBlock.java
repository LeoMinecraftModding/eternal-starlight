package cn.leolezury.eternalstarlight.common.block;

import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.LeavesBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;

public class SnowyLeavesBlock extends LeavesBlock {
	public static final MapCodec<SnowyLeavesBlock> CODEC = simpleCodec(SnowyLeavesBlock::new);
	public static final BooleanProperty SNOWY = BlockStateProperties.SNOWY;

	public SnowyLeavesBlock(Properties properties) {
		super(properties);
		this.registerDefaultState(this.defaultBlockState().setValue(PERSISTENT, false).setValue(WATERLOGGED, false).setValue(SNOWY, false));
	}

	@Override
	public MapCodec<SnowyLeavesBlock> codec() {
		return CODEC;
	}

	@Override
	public BlockState updateShape(BlockState blockState, Direction direction, BlockState blockState2, LevelAccessor levelAccessor, BlockPos blockPos, BlockPos blockPos2) {
		return direction == Direction.UP ? blockState.setValue(SNOWY, blockState2.is(BlockTags.SNOW)) : super.updateShape(blockState, direction, blockState2, levelAccessor, blockPos, blockPos2);
	}

	@Override
	protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
		super.createBlockStateDefinition(builder);
		builder.add(SNOWY);
	}
}

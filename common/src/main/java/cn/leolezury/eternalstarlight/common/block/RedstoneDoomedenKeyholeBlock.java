package cn.leolezury.eternalstarlight.common.block;

import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.RedStoneWireBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import org.jetbrains.annotations.Nullable;

import java.util.Arrays;

public class RedstoneDoomedenKeyholeBlock extends HorizontalAxisBlock {
	public static final MapCodec<RedstoneDoomedenKeyholeBlock> CODEC = simpleCodec(RedstoneDoomedenKeyholeBlock::new);
	public static final BooleanProperty LIT = BlockStateProperties.LIT;

	public RedstoneDoomedenKeyholeBlock(Properties properties) {
		super(properties);
		this.registerDefaultState(this.getStateDefinition().any().setValue(LIT, false).setValue(AXIS, Direction.Axis.X));
	}

	@Override
	protected MapCodec<? extends HorizontalAxisBlock> codec() {
		return CODEC;
	}

	@Nullable
	@Override
	public BlockState getStateForPlacement(BlockPlaceContext blockPlaceContext) {
		return defaultBlockState().setValue(AXIS, blockPlaceContext.getHorizontalDirection().getAxis());
	}

	@Override
	protected void neighborChanged(BlockState state, Level level, BlockPos pos, Block block, BlockPos fromPos, boolean isMoving) {
		boolean lit = state.getValue(LIT);
		if (!lit && Arrays.stream(Direction.values()).filter(direction -> direction.getAxis() == state.getValue(AXIS)).anyMatch(direction -> hasInputSignal(level, pos, direction))) {
			level.setBlockAndUpdate(pos, state.setValue(LIT, true));
		}
		if (lit && Arrays.stream(Direction.values()).filter(direction -> direction.getAxis() == state.getValue(AXIS)).noneMatch(direction -> hasInputSignal(level, pos, direction))) {
			level.scheduleTick(pos, this, 4);
		}
	}

	protected boolean hasInputSignal(Level level, BlockPos pos, Direction direction) {
		BlockPos blockPos = pos.relative(direction);
		int i = level.getSignal(blockPos, direction);
		if (i >= 15) {
			return true;
		} else {
			BlockState blockState = level.getBlockState(blockPos);
			return Math.max(i, blockState.is(Blocks.REDSTONE_WIRE) ? blockState.getValue(RedStoneWireBlock.POWER) : 0) > 0;
		}
	}

	@Override
	protected void tick(BlockState blockState, ServerLevel serverLevel, BlockPos blockPos, RandomSource randomSource) {
		if (blockState.getValue(LIT) && Arrays.stream(Direction.values()).filter(direction -> direction.getAxis() == blockState.getValue(AXIS)).noneMatch(direction -> hasInputSignal(serverLevel, blockPos, direction))) {
			serverLevel.setBlockAndUpdate(blockPos, blockState.setValue(LIT, false));
		}
	}

	@Override
	public boolean isSignalSource(BlockState blockState) {
		return blockState.getValue(LIT);
	}

	@Override
	public int getSignal(BlockState blockState, BlockGetter blockGetter, BlockPos blockPos, Direction direction) {
		return blockState.getValue(LIT) && direction.getAxis() != blockState.getValue(AXIS) ? 15 : super.getSignal(blockState, blockGetter, blockPos, direction);
	}

	@Override
	protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
		builder.add(LIT);
		super.createBlockStateDefinition(builder);
	}
}

package cn.leolezury.eternalstarlight.common.block;

import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.DirectionalBlock;
import net.minecraft.world.level.block.Mirror;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import org.jetbrains.annotations.Nullable;

public class AccumulatorBlock extends DirectionalBlock {
	public static final MapCodec<AccumulatorBlock> CODEC = simpleCodec(AccumulatorBlock::new);
	public static final IntegerProperty POWER = BlockStateProperties.POWER;

	public AccumulatorBlock(Properties properties) {
		super(properties);
		this.registerDefaultState(this.stateDefinition.any().setValue(FACING, Direction.UP).setValue(POWER, 0));
	}

	@Override
	protected MapCodec<? extends AccumulatorBlock> codec() {
		return CODEC;
	}

	@Override
	@Nullable
	public BlockState getStateForPlacement(BlockPlaceContext context) {
		return this.defaultBlockState().setValue(FACING, context.getNearestLookingDirection().getOpposite());
	}

	@Override
	protected void neighborChanged(BlockState state, Level level, BlockPos pos, Block block, BlockPos fromPos, boolean isMoving) {
		if (!level.isClientSide) {
			int signal = level.getSignal(pos.relative(state.getValue(FACING)), state.getValue(FACING));
			if (state.getValue(POWER) != signal) {
				level.setBlockAndUpdate(pos, state.setValue(POWER, signal));
			}
		}
	}

	@Override
	protected void onPlace(BlockState state, Level level, BlockPos pos, BlockState oldState, boolean isMoving) {
		if (!level.isClientSide) {
			int signal = level.getSignal(pos.relative(state.getValue(FACING)), state.getValue(FACING));
			if (state.getValue(POWER) != signal) {
				level.setBlockAndUpdate(pos, state.setValue(POWER, signal));
			}
		}
	}

	@Override
	protected boolean isSignalSource(BlockState state) {
		return state.getValue(POWER) > 0;
	}

	@Override
	protected int getSignal(BlockState blockState, BlockGetter blockAccess, BlockPos pos, Direction side) {
		return side != blockState.getValue(FACING).getOpposite() ? blockState.getValue(POWER) : 0;
	}

	@Override
	protected BlockState rotate(BlockState state, Rotation rotation) {
		return state.setValue(FACING, rotation.rotate(state.getValue(FACING)));
	}

	@Override
	protected BlockState mirror(BlockState state, Mirror mirror) {
		return state.rotate(mirror.getRotation(state.getValue(FACING)));
	}

	@Override
	protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
		builder.add(FACING, POWER);
	}
}

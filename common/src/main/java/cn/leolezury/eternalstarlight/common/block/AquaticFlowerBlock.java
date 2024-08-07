package cn.leolezury.eternalstarlight.common.block;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Holder;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.FlowerBlock;
import net.minecraft.world.level.block.SimpleWaterloggedBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import org.jetbrains.annotations.Nullable;

public class AquaticFlowerBlock extends FlowerBlock implements SimpleWaterloggedBlock {
	public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;

	public AquaticFlowerBlock(Holder<MobEffect> holder, float duration, Properties properties) {
		super(holder, duration, properties);
		this.registerDefaultState(this.getStateDefinition().any().setValue(WATERLOGGED, false));
	}

	@Override
	protected boolean mayPlaceOn(BlockState blockState, BlockGetter blockGetter, BlockPos blockPos) {
		return blockState.isFaceSturdy(blockGetter, blockPos, Direction.UP);
	}

	@Override
	public BlockState updateShape(BlockState state, Direction direction, BlockState blockState, LevelAccessor levelAccessor, BlockPos pos, BlockPos blockPos) {
		if (state.getValue(WATERLOGGED)) {
			levelAccessor.scheduleTick(pos, Fluids.WATER, Fluids.WATER.getTickDelay(levelAccessor));
		}
		return super.updateShape(state, direction, blockState, levelAccessor, pos, blockPos);
	}

	@Nullable
	public BlockState getStateForPlacement(BlockPlaceContext context) {
		LevelAccessor levelaccessor = context.getLevel();
		BlockPos blockpos = context.getClickedPos();
		return this.defaultBlockState().setValue(WATERLOGGED, levelaccessor.getFluidState(blockpos).getType() == Fluids.WATER);
	}

	@Override
	public FluidState getFluidState(BlockState state) {
		return state.getValue(WATERLOGGED) ? Fluids.WATER.getSource(false) : super.getFluidState(state);
	}

	@Override
	protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
		builder.add(WATERLOGGED);
	}
}

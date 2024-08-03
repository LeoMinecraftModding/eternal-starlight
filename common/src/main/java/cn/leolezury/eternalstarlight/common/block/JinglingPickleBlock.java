package cn.leolezury.eternalstarlight.common.block;

import cn.leolezury.eternalstarlight.common.registry.ESBlocks;
import cn.leolezury.eternalstarlight.common.util.ESMathUtil;
import cn.leolezury.eternalstarlight.common.util.ESTags;
import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.level.pathfinder.PathComputationType;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class JinglingPickleBlock extends BushBlock implements BonemealableBlock, SimpleWaterloggedBlock {
	public static final MapCodec<JinglingPickleBlock> CODEC = simpleCodec(JinglingPickleBlock::new);
	private static final VoxelShape SHAPE = Block.box(6.0, 0.0, 6.0, 10.0, 12.0, 10.0);
	public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;

	public @NotNull MapCodec<JinglingPickleBlock> codec() {
		return CODEC;
	}

	public JinglingPickleBlock(BlockBehaviour.Properties properties) {
		super(properties);
		this.registerDefaultState(this.stateDefinition.any().setValue(WATERLOGGED, true));
	}

	@Nullable
	public BlockState getStateForPlacement(BlockPlaceContext blockPlaceContext) {
		BlockState state = super.getStateForPlacement(blockPlaceContext);
		if (state == null) return null;
		FluidState fluidState = blockPlaceContext.getLevel().getFluidState(blockPlaceContext.getClickedPos());
		boolean bl = fluidState.getType() == Fluids.WATER;
		return state.setValue(WATERLOGGED, bl);
	}

	public static boolean isDead(BlockState blockState) {
		return !blockState.getValue(WATERLOGGED);
	}

	@Override
	protected boolean mayPlaceOn(BlockState blockState, BlockGetter blockGetter, BlockPos blockPos) {
		return !blockState.getCollisionShape(blockGetter, blockPos).getFaceShape(Direction.UP).isEmpty() || blockState.isFaceSturdy(blockGetter, blockPos, Direction.UP);
	}

	@Override
	public boolean canSurvive(BlockState blockState, LevelReader levelReader, BlockPos blockPos) {
		BlockPos blockPos2 = blockPos.below();
		return this.mayPlaceOn(levelReader.getBlockState(blockPos2), levelReader, blockPos2);
	}

	@Override
	public BlockState updateShape(BlockState blockState, Direction direction, BlockState blockState2, LevelAccessor levelAccessor, BlockPos blockPos, BlockPos blockPos2) {
		if (!blockState.canSurvive(levelAccessor, blockPos)) {
			return Blocks.AIR.defaultBlockState();
		} else {
			if (blockState.getValue(WATERLOGGED)) {
				levelAccessor.scheduleTick(blockPos, Fluids.WATER, Fluids.WATER.getTickDelay(levelAccessor));
			}

			return super.updateShape(blockState, direction, blockState2, levelAccessor, blockPos, blockPos2);
		}
	}

	@Override
	public VoxelShape getShape(BlockState blockState, BlockGetter blockGetter, BlockPos blockPos, CollisionContext collisionContext) {
		return SHAPE;
	}

	@Override
	public FluidState getFluidState(BlockState blockState) {
		return blockState.getValue(WATERLOGGED) ? Fluids.WATER.getSource(false) : super.getFluidState(blockState);
	}

	@Override
	protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
		builder.add(WATERLOGGED);
	}

	@Override
	public boolean isValidBonemealTarget(LevelReader levelReader, BlockPos blockPos, BlockState blockState) {
		return true;
	}

	@Override
	public boolean isBonemealSuccess(Level level, RandomSource randomSource, BlockPos blockPos, BlockState blockState) {
		return true;
	}

	@Override
	public void performBonemeal(ServerLevel serverLevel, RandomSource randomSource, BlockPos blockPos, BlockState blockState) {
		if (!isDead(blockState) && serverLevel.getBlockState(blockPos.below()).is(ESTags.Blocks.CORAL_BLOCKS)) {
			for (int x = -5; x <= 5; x++) {
				for (int y = -5; y <= 5; y++) {
					for (int z = -5; z <= 5; z++) {
						if (ESMathUtil.isPointInEllipsoid(x, y, z, 5, 5, 5)) {
							BlockPos pos = blockPos.offset(x, y, z);
							if (serverLevel.getBlockState(pos.below()).is(ESTags.Blocks.CORAL_BLOCKS) && serverLevel.getBlockState(pos).is(Blocks.WATER) && randomSource.nextInt(15) == 0) {
								serverLevel.setBlockAndUpdate(pos, ESBlocks.JINGLING_PICKLE.get().defaultBlockState());
							}
						}
					}
				}
			}
		}
	}

	@Override
	protected boolean isPathfindable(BlockState blockState, PathComputationType pathComputationType) {
		return false;
	}
}

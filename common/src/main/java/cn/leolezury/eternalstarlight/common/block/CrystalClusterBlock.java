package cn.leolezury.eternalstarlight.common.block;

import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.Nullable;

public class CrystalClusterBlock extends Block implements SimpleWaterloggedBlock {
	public static final MapCodec<CrystalClusterBlock> CODEC = simpleCodec(CrystalClusterBlock::new);
	public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;
	public static final DirectionProperty FACING = BlockStateProperties.FACING;
	protected final VoxelShape northAabb;
	protected final VoxelShape southAabb;
	protected final VoxelShape eastAabb;
	protected final VoxelShape westAabb;
	protected final VoxelShape upAabb;
	protected final VoxelShape downAabb;

	public CrystalClusterBlock(BlockBehaviour.Properties properties) {
		super(properties);
		this.registerDefaultState(this.defaultBlockState().setValue(WATERLOGGED, false).setValue(FACING, Direction.UP));
		this.upAabb = Block.box(3, 0.0D, 3, (16 - 3), 5, (16 - 3));
		this.downAabb = Block.box(3, (16 - 5), 3, (16 - 3), 16.0D, (16 - 3));
		this.northAabb = Block.box(3, 3, (16 - 5), (16 - 3), (16 - 3), 16.0D);
		this.southAabb = Block.box(3, 3, 0.0D, (16 - 3), (16 - 3), 5);
		this.eastAabb = Block.box(0.0D, 3, 3, 5, (16 - 3), (16 - 3));
		this.westAabb = Block.box((16 - 5), 3, 3, 16.0D, (16 - 3), (16 - 3));
	}

	@Override
	protected MapCodec<? extends CrystalClusterBlock> codec() {
		return CODEC;
	}

	@Override
	public VoxelShape getShape(BlockState state, BlockGetter blockGetter, BlockPos pos, CollisionContext context) {
		Direction direction = state.getValue(FACING);
		switch (direction) {
			case NORTH:
				return this.northAabb;
			case SOUTH:
				return this.southAabb;
			case EAST:
				return this.eastAabb;
			case WEST:
				return this.westAabb;
			case DOWN:
				return this.downAabb;
			case UP:
			default:
				return this.upAabb;
		}
	}

	@Override
	public boolean canSurvive(BlockState state, LevelReader levelReader, BlockPos pos) {
		Direction direction = state.getValue(FACING);
		BlockPos blockpos = pos.relative(direction.getOpposite());
		return levelReader.getBlockState(blockpos).isFaceSturdy(levelReader, blockpos, direction);
	}

	@Override
	public BlockState updateShape(BlockState state, Direction direction, BlockState blockState, LevelAccessor levelAccessor, BlockPos pos, BlockPos blockPos) {
		if (state.getValue(WATERLOGGED)) {
			levelAccessor.scheduleTick(pos, Fluids.WATER, Fluids.WATER.getTickDelay(levelAccessor));
		}

		return direction == state.getValue(FACING).getOpposite() && !state.canSurvive(levelAccessor, pos) ? Blocks.AIR.defaultBlockState() : super.updateShape(state, direction, blockState, levelAccessor, pos, blockPos);
	}

	@Nullable
	public BlockState getStateForPlacement(BlockPlaceContext context) {
		LevelAccessor levelaccessor = context.getLevel();
		BlockPos blockpos = context.getClickedPos();
		return this.defaultBlockState().setValue(WATERLOGGED, Boolean.valueOf(levelaccessor.getFluidState(blockpos).getType() == Fluids.WATER)).setValue(FACING, context.getClickedFace());
	}

	@Override
	public BlockState rotate(BlockState state, Rotation rotation) {
		return state.setValue(FACING, rotation.rotate(state.getValue(FACING)));
	}

	@Override
	public BlockState mirror(BlockState state, Mirror mirror) {
		return state.rotate(mirror.getRotation(state.getValue(FACING)));
	}

	@Override
	public FluidState getFluidState(BlockState state) {
		return state.getValue(WATERLOGGED) ? Fluids.WATER.getSource(false) : super.getFluidState(state);
	}

	@Override
	protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
		builder.add(WATERLOGGED, FACING);
	}
}

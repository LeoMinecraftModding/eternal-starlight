package cn.leolezury.eternalstarlight.common.block;

import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.ScheduledTickAccess;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.Nullable;

public class DirectionalBudBlock extends Block implements SimpleWaterloggedBlock {
	public static final MapCodec<DirectionalBudBlock> CODEC = simpleCodec(DirectionalBudBlock::new);
	public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;
	public static final EnumProperty<Direction> FACING = BlockStateProperties.FACING;
	protected final VoxelShape northAabb;
	protected final VoxelShape southAabb;
	protected final VoxelShape eastAabb;
	protected final VoxelShape westAabb;
	protected final VoxelShape upAabb;
	protected final VoxelShape downAabb;

	public DirectionalBudBlock(BlockBehaviour.Properties properties) {
		super(properties);
		this.registerDefaultState(this.defaultBlockState().setValue(WATERLOGGED, false).setValue(FACING, Direction.UP));
		double border = (16.0 - getShapeWidth()) / 2.0;
		this.upAabb = Block.box(border, 0.0D, border, (16 - border), getShapeHeight(), (16 - border));
		this.downAabb = Block.box(border, (16 - getShapeHeight()), border, (16 - border), 16.0D, (16 - border));
		this.northAabb = Block.box(border, border, (16 - getShapeHeight()), (16 - border), (16 - border), 16.0D);
		this.southAabb = Block.box(border, border, 0.0D, (16 - border), (16 - border), getShapeHeight());
		this.eastAabb = Block.box(0.0D, border, border, getShapeHeight(), (16 - border), (16 - border));
		this.westAabb = Block.box((16 - getShapeHeight()), border, border, 16.0D, (16 - border), (16 - border));
	}

	@Override
	protected MapCodec<? extends DirectionalBudBlock> codec() {
		return CODEC;
	}

	protected double getShapeWidth() {
		return 10;
	}

	protected double getShapeHeight() {
		return 5;
	}

	@Override
	public VoxelShape getShape(BlockState state, BlockGetter blockGetter, BlockPos pos, CollisionContext context) {
		Direction direction = state.getValue(FACING);
		return switch (direction) {
			case NORTH -> this.northAabb;
			case SOUTH -> this.southAabb;
			case EAST -> this.eastAabb;
			case WEST -> this.westAabb;
			case DOWN -> this.downAabb;
			default -> this.upAabb;
		};
	}

	@Override
	public boolean canSurvive(BlockState state, LevelReader level, BlockPos pos) {
		Direction direction = state.getValue(FACING);
		BlockPos attachPos = pos.relative(direction.getOpposite());
		BlockState attachState = level.getBlockState(attachPos);
		return attachState.isFaceSturdy(level, attachPos, direction);
	}

	@Override
	public BlockState updateShape(BlockState state, LevelReader level, ScheduledTickAccess tickAccess, BlockPos pos, Direction direction, BlockPos neighborPos, BlockState neighborState, RandomSource random) {
		if (state.getValue(WATERLOGGED)) {
			tickAccess.scheduleTick(pos, Fluids.WATER, Fluids.WATER.getTickDelay(level));
		}
		return direction == state.getValue(FACING).getOpposite() && !state.canSurvive(level, pos) ? Blocks.AIR.defaultBlockState() : super.updateShape(state, level, tickAccess, pos, direction, neighborPos, neighborState, random);
	}

	@Nullable
	public BlockState getStateForPlacement(BlockPlaceContext context) {
		LevelAccessor levelaccessor = context.getLevel();
		BlockPos blockpos = context.getClickedPos();
		return this.defaultBlockState().setValue(WATERLOGGED, levelaccessor.getFluidState(blockpos).getType() == Fluids.WATER).setValue(FACING, context.getClickedFace());
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

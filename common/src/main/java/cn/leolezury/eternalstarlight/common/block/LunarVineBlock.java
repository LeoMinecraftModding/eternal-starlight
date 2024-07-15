package cn.leolezury.eternalstarlight.common.block;

import cn.leolezury.eternalstarlight.common.block.entity.LunarVineBlockEntity;
import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.Nullable;

public class LunarVineBlock extends HorizontalDirectionalBlock implements EntityBlock {
	public static final MapCodec<LunarVineBlock> CODEC = simpleCodec(LunarVineBlock::new);
	private static final VoxelShape WEST_AABB = Block.box(0.0, 0.0, 0.0, 1.0, 16.0, 16.0);
	private static final VoxelShape EAST_AABB = Block.box(15.0, 0.0, 0.0, 16.0, 16.0, 16.0);
	private static final VoxelShape NORTH_AABB = Block.box(0.0, 0.0, 0.0, 16.0, 16.0, 1.0);
	private static final VoxelShape SOUTH_AABB = Block.box(0.0, 0.0, 15.0, 16.0, 16.0, 16.0);

	public LunarVineBlock(Properties properties) {
		super(properties);
		this.registerDefaultState(getStateDefinition().any().setValue(FACING, Direction.NORTH));
	}

	@Override
	protected MapCodec<? extends HorizontalDirectionalBlock> codec() {
		return CODEC;
	}

	@Nullable
	public BlockState getStateForPlacement(BlockPlaceContext context) {
		if (context.getClickedFace().getAxis() != Direction.Axis.Y) {
			BlockState state = defaultBlockState().setValue(FACING, context.getClickedFace().getOpposite());
			if (canSurvive(state, context.getLevel(), context.getClickedPos())) {
				return state;
			}
		}
		return null;
	}

	@Override
	protected boolean canSurvive(BlockState state, LevelReader level, BlockPos pos) {
		return level.getBlockState(pos.relative(state.getValue(FACING))).isFaceSturdy(level, pos.relative(state.getValue(FACING)), state.getValue(FACING).getOpposite());
	}

	protected BlockState updateShape(BlockState blockState, Direction direction, BlockState blockState2, LevelAccessor level, BlockPos blockPos, BlockPos blockPos2) {
		return !blockState.canSurvive(level, blockPos) ? Blocks.AIR.defaultBlockState() : blockState;
	}

	@Override
	protected VoxelShape getShape(BlockState blockState, BlockGetter blockGetter, BlockPos blockPos, CollisionContext collisionContext) {
		return switch (blockState.getValue(FACING)) {
			case NORTH -> NORTH_AABB;
			case SOUTH -> SOUTH_AABB;
			case WEST -> WEST_AABB;
			case EAST -> EAST_AABB;
			default -> super.getShape(blockState, blockGetter, blockPos, collisionContext);
		};
	}

	@Override
	protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
		super.createBlockStateDefinition(builder);
		builder.add(FACING);
	}

	@Override
	protected RenderShape getRenderShape(BlockState blockState) {
		return RenderShape.ENTITYBLOCK_ANIMATED;
	}

	@Nullable
	@Override
	public BlockEntity newBlockEntity(BlockPos blockPos, BlockState blockState) {
		return new LunarVineBlockEntity(blockPos, blockState);
	}
}

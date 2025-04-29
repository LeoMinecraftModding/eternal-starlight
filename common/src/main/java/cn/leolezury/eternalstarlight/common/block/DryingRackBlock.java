package cn.leolezury.eternalstarlight.common.block;

import cn.leolezury.eternalstarlight.common.block.entity.DryingRackBlockEntity;
import cn.leolezury.eternalstarlight.common.registry.ESBlockEntities;
import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.Containers;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.ItemInteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.Nullable;

public class DryingRackBlock extends BaseEntityBlock {
	public static final MapCodec<DryingRackBlock> CODEC = simpleCodec(DryingRackBlock::new);
	public static final BooleanProperty LIT = BlockStateProperties.LIT;
	public static final BooleanProperty CAMPFIRE = BooleanProperty.create("campfire");
	public static final DirectionProperty FACING = BlockStateProperties.HORIZONTAL_FACING;
	private static final VoxelShape AXIS_X_SHAPE = Block.box(6.0, 0.0, 0.0, 10.0, 16.0, 16.0);
	private static final VoxelShape AXIS_Z_SHAPE = Block.box(0.0, 0.0, 6.0, 16.0, 16.0, 10.0);

	public DryingRackBlock(Properties properties) {
		super(properties);
		this.registerDefaultState(this.stateDefinition.any().setValue(LIT, false).setValue(CAMPFIRE, false).setValue(FACING, Direction.NORTH));
	}

	@Override
	protected MapCodec<? extends BaseEntityBlock> codec() {
		return CODEC;
	}

	public static boolean canSurviveOnBlock(BlockGetter level, BlockPos blockPos) {
		BlockState blockState = level.getBlockState(blockPos);
		return blockState.isFaceSturdy(level, blockPos, Direction.UP) || blockState.is(BlockTags.CAMPFIRES);
	}

	@Override
	public boolean canSurvive(BlockState blockState, LevelReader levelReader, BlockPos blockPos) {
		return canSurviveOnBlock(levelReader, blockPos.below());
	}

	@Override
	public BlockState updateShape(BlockState state, Direction direction, BlockState neighborState, LevelAccessor level, BlockPos pos, BlockPos neighborPos) {
		if (this.canSurvive(state, level, pos)) {
			if (direction == Direction.DOWN && neighborState.is(BlockTags.CAMPFIRES)) {
				return state.setValue(LIT, neighborState.hasProperty(LIT) && neighborState.getValue(LIT));
			}
			return state;
		}
		return Blocks.AIR.defaultBlockState();
	}

	@Override
	public @Nullable BlockState getStateForPlacement(BlockPlaceContext context) {
		BlockState state = super.getStateForPlacement(context);
		BlockState belowState = context.getLevel().getBlockState(context.getClickedPos().below());
		if (state != null) {
			if (belowState.is(BlockTags.CAMPFIRES)) {
				state = state.setValue(LIT, belowState.hasProperty(LIT) && belowState.getValue(LIT)).setValue(CAMPFIRE, true);
			}
			state = state.setValue(FACING, context.getHorizontalDirection().getOpposite());
		}
		return state;
	}

	@Override
	protected ItemInteractionResult useItemOn(ItemStack stack, BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hitResult) {
		if (level.getBlockEntity(pos) instanceof DryingRackBlockEntity entity) {
			if (!stack.isEmpty() && entity.getItem().isEmpty() && entity.canBeDried(stack, state.getValue(LIT))) {
				if (!level.isClientSide) {
					entity.setItem(stack.copyWithCount(1));
					stack.consume(1, player);
				}
				return ItemInteractionResult.sidedSuccess(level.isClientSide);
			} else if (stack.isEmpty() && !entity.getItem().isEmpty()) {
				if (!level.isClientSide) {
					player.setItemInHand(hand, entity.getItem().copy());
					entity.setItem(ItemStack.EMPTY);
				}
				return ItemInteractionResult.sidedSuccess(level.isClientSide);
			}
		}
		return super.useItemOn(stack, state, level, pos, player, hand, hitResult);
	}

	@Override
	public void onRemove(BlockState state, Level level, BlockPos pos, BlockState newState, boolean movedByPiston) {
		if (!state.is(newState.getBlock()) && level.getBlockEntity(pos) instanceof DryingRackBlockEntity entity) {
			Containers.dropItemStack(level, pos.getX(), pos.getY(), pos.getZ(), entity.getItem());
		}
		super.onRemove(state, level, pos, newState, movedByPiston);
	}

	@Override
	protected VoxelShape getShape(BlockState blockState, BlockGetter blockGetter, BlockPos blockPos, CollisionContext collisionContext) {
		return blockState.getValue(FACING).getAxis() == Direction.Axis.X ? AXIS_X_SHAPE : AXIS_Z_SHAPE;
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
	protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
		builder.add(LIT, CAMPFIRE, FACING);
	}

	@Override
	protected RenderShape getRenderShape(BlockState blockState) {
		return RenderShape.MODEL;
	}

	@Override
	public @Nullable BlockEntity newBlockEntity(BlockPos blockPos, BlockState blockState) {
		return new DryingRackBlockEntity(blockPos, blockState);
	}

	@Nullable
	@Override
	public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState blockState, BlockEntityType<T> blockEntityType) {
		return createTickerHelper(blockEntityType, ESBlockEntities.DRYING_RACK.get(), DryingRackBlockEntity::tick);
	}
}

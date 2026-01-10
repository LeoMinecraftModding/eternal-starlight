package cn.leolezury.eternalstarlight.common.block;

import cn.leolezury.eternalstarlight.common.block.entity.EnergyTransmitterBlockEntity;
import cn.leolezury.eternalstarlight.common.registry.ESBlockEntities;
import cn.leolezury.eternalstarlight.common.registry.ESDataAttachments;
import cn.leolezury.eternalstarlight.common.util.ESBlockUtil;
import com.mojang.math.OctahedralGroup;
import com.mojang.math.SymmetricGroup3;
import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.GlobalPos;
import net.minecraft.core.Vec3i;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
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
import net.minecraft.world.level.block.state.properties.*;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.Nullable;

public class EnergyTransmitterBlock extends BaseEntityBlock {
	public static final MapCodec<EnergyTransmitterBlock> CODEC = simpleCodec(EnergyTransmitterBlock::new);

	public static final DirectionProperty FACING = BlockStateProperties.FACING;
	public static final EnumProperty<OctahedralGroup> OFFSET_TRANSFORMATION = EnumProperty.create("offset_transformation", OctahedralGroup.class, group -> !group.inverts(Direction.Axis.Y) && (group.permutation == SymmetricGroup3.P123 || group.permutation == SymmetricGroup3.P321));
	public static final BooleanProperty POWERED = BlockStateProperties.POWERED;
	public static final IntegerProperty POWER = BlockStateProperties.POWER;
	public static final IntegerProperty DIRECT_POWER = IntegerProperty.create("direct_power", 0, 15);

	public static final int MAX_CONNECTION_DISTANCE = 64;

	private static final VoxelShape UP_SHAPE = Shapes.or(Block.box(3, 0, 3, 13, 2, 13), Block.box(6, 2, 6, 10, 3, 10));
	private static final VoxelShape DOWN_SHAPE = ESBlockUtil.rotateVoxelShape(UP_SHAPE, Direction.DOWN);
	private static final VoxelShape SOUTH_SHAPE = ESBlockUtil.rotateVoxelShape(UP_SHAPE, Direction.SOUTH);
	private static final VoxelShape NORTH_SHAPE = ESBlockUtil.rotateVoxelShape(UP_SHAPE, Direction.NORTH);
	private static final VoxelShape EAST_SHAPE = ESBlockUtil.rotateVoxelShape(UP_SHAPE, Direction.EAST);
	private static final VoxelShape WEST_SHAPE = ESBlockUtil.rotateVoxelShape(UP_SHAPE, Direction.WEST);

	public EnergyTransmitterBlock(Properties properties) {
		super(properties);
		this.registerDefaultState(this.stateDefinition.any().setValue(FACING, Direction.UP).setValue(OFFSET_TRANSFORMATION, OctahedralGroup.IDENTITY).setValue(POWERED, false).setValue(POWER, 0).setValue(DIRECT_POWER, 0));
	}

	@Override
	protected MapCodec<? extends EnergyTransmitterBlock> codec() {
		return CODEC;
	}

	@Override
	protected InteractionResult useWithoutItem(BlockState state, Level level, BlockPos pos, Player player, BlockHitResult hitResult) {
		if (level.isClientSide) {
			return InteractionResult.SUCCESS;
		} else if (level.getBlockEntity(pos) instanceof EnergyTransmitterBlockEntity entity) {
			GlobalPos sourcePos = ESDataAttachments.ENERGY_TRANSMITTER_SOURCE.getData(player);
			if (sourcePos != null
				&& sourcePos.dimension() == level.dimension()
				&& sourcePos.pos().distManhattan(pos) <= MAX_CONNECTION_DISTANCE
				&& level.getBlockEntity(sourcePos.pos()) instanceof EnergyTransmitterBlockEntity sourceEntity) {
				entity.setInputOffset(sourcePos.pos().subtract(pos));
				entity.setOutputOffset(Vec3i.ZERO);
				sourceEntity.setOutputOffset(pos.subtract(sourcePos.pos()));
				sourceEntity.setInputOffset(Vec3i.ZERO);
				ESDataAttachments.ENERGY_TRANSMITTER_SOURCE.removeData(player);
			} else {
				ESDataAttachments.ENERGY_TRANSMITTER_SOURCE.setData(player, GlobalPos.of(level.dimension(), pos));
			}
			return InteractionResult.CONSUME;
		}
		return super.useWithoutItem(state, level, pos, player, hitResult);
	}

	@Override
	@Nullable
	public BlockState getStateForPlacement(BlockPlaceContext context) {
		return this.defaultBlockState().setValue(FACING, context.getClickedFace());
	}

	@Override
	public boolean canSurvive(BlockState state, LevelReader level, BlockPos pos) {
		Direction direction = state.getValue(FACING);
		BlockPos attachPos = pos.relative(direction.getOpposite());
		BlockState attachState = level.getBlockState(attachPos);
		return attachState.isFaceSturdy(level, attachPos, direction);
	}

	@Override
	public BlockState updateShape(BlockState state, Direction direction, BlockState neighborState, LevelAccessor level, BlockPos pos, BlockPos neighborPos) {
		return direction == state.getValue(FACING).getOpposite() && !state.canSurvive(level, pos) ? Blocks.AIR.defaultBlockState() : super.updateShape(state, direction, neighborState, level, pos, neighborPos);
	}

	@Override
	protected void neighborChanged(BlockState state, Level level, BlockPos pos, Block block, BlockPos fromPos, boolean isMoving) {
		if (!level.isClientSide) {
			int signal = level.getSignal(pos.relative(state.getValue(FACING).getOpposite()), state.getValue(FACING).getOpposite());
			if (state.getValue(DIRECT_POWER) != signal) {
				level.setBlockAndUpdate(pos, state.setValue(DIRECT_POWER, signal));
			}
		}
	}

	@Override
	protected void onPlace(BlockState state, Level level, BlockPos pos, BlockState oldState, boolean isMoving) {
		if (!level.isClientSide) {
			int signal = level.getSignal(pos.relative(state.getValue(FACING).getOpposite()), state.getValue(FACING).getOpposite());
			if (state.getValue(DIRECT_POWER) != signal) {
				level.setBlockAndUpdate(pos, state.setValue(DIRECT_POWER, signal));
			}
		}
	}

	@Override
	protected boolean isSignalSource(BlockState state) {
		return state.getValue(POWER) > 0;
	}

	@Override
	protected int getSignal(BlockState blockState, BlockGetter blockAccess, BlockPos pos, Direction side) {
		return side == blockState.getValue(FACING) ? blockState.getValue(POWER) : 0;
	}

	@Override
	public RenderShape getRenderShape(BlockState state) {
		return RenderShape.MODEL;
	}

	@Override
	protected VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
		return switch (state.getValue(FACING)) {
			case DOWN -> DOWN_SHAPE;
			case UP -> UP_SHAPE;
			case NORTH -> NORTH_SHAPE;
			case SOUTH -> SOUTH_SHAPE;
			case WEST -> WEST_SHAPE;
			case EAST -> EAST_SHAPE;
		};
	}

	@Override
	protected BlockState rotate(BlockState state, Rotation rotation) {
		return state.setValue(FACING, rotation.rotate(state.getValue(FACING))).setValue(OFFSET_TRANSFORMATION, rotation.rotation().compose(state.getValue(OFFSET_TRANSFORMATION)));
	}

	@Override
	protected BlockState mirror(BlockState state, Mirror mirror) {
		return state.setValue(FACING, mirror.getRotation(state.getValue(FACING)).rotate(state.getValue(FACING))).setValue(OFFSET_TRANSFORMATION, mirror.rotation().compose(state.getValue(OFFSET_TRANSFORMATION)));
	}

	@Override
	protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
		builder.add(FACING, OFFSET_TRANSFORMATION, POWERED, POWER, DIRECT_POWER);
	}

	@Override
	public @Nullable BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
		return new EnergyTransmitterBlockEntity(pos, state);
	}

	@Nullable
	@Override
	public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState state, BlockEntityType<T> type) {
		return createTickerHelper(type, ESBlockEntities.ENERGY_TRANSMITTER.get(), EnergyTransmitterBlockEntity::tick);
	}
}

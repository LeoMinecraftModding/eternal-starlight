package cn.leolezury.eternalstarlight.common.block;

import cn.leolezury.eternalstarlight.common.block.entity.EnergyTransmitterBlockEntity;
import cn.leolezury.eternalstarlight.common.registry.ESBlockEntities;
import cn.leolezury.eternalstarlight.common.registry.ESDataAttachments;
import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.GlobalPos;
import net.minecraft.core.Vec3i;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.BooleanOp;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.Nullable;

public class EnergyTransmitterBlock extends BaseEntityBlock {
	public static final MapCodec<EnergyTransmitterBlock> CODEC = simpleCodec(EnergyTransmitterBlock::new);
	public static final BooleanProperty POWERED = BlockStateProperties.POWERED;
	public static final IntegerProperty POWER = BlockStateProperties.POWER;
	public static final IntegerProperty DIRECT_POWER = IntegerProperty.create("direct_power", 0, 15);
	public static final int MAX_CONNECTION_DISTANCE = 64;
	private static final VoxelShape SHAPE = Shapes.join(Block.box(3, 0, 3, 13, 2, 13), Block.box(6, 2, 6, 10, 3, 10), BooleanOp.OR);

	public EnergyTransmitterBlock(Properties properties) {
		super(properties);
		this.registerDefaultState(this.stateDefinition.any().setValue(POWERED, false).setValue(POWER, 0).setValue(DIRECT_POWER, 0));
	}

	@Override
	protected MapCodec<? extends BaseEntityBlock> codec() {
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
	protected void neighborChanged(BlockState state, Level level, BlockPos pos, Block block, BlockPos fromPos, boolean isMoving) {
		if (!level.isClientSide) {
			if (state.getValue(DIRECT_POWER) != level.getBestNeighborSignal(pos)) {
				level.setBlockAndUpdate(pos, state.setValue(DIRECT_POWER, level.getBestNeighborSignal(pos)));
			}
		}
	}

	@Override
	protected void onPlace(BlockState state, Level level, BlockPos pos, BlockState oldState, boolean isMoving) {
		if (!level.isClientSide) {
			if (state.getValue(DIRECT_POWER) != level.getBestNeighborSignal(pos)) {
				level.setBlockAndUpdate(pos, state.setValue(DIRECT_POWER, level.getBestNeighborSignal(pos)));
			}
		}
	}

	@Override
	protected boolean isSignalSource(BlockState state) {
		return state.getValue(POWER) > 0;
	}

	@Override
	protected int getSignal(BlockState blockState, BlockGetter blockAccess, BlockPos pos, Direction side) {
		return blockState.getValue(POWER);
	}

	@Override
	public RenderShape getRenderShape(BlockState state) {
		return RenderShape.MODEL;
	}

	@Override
	protected VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
		return SHAPE;
	}

	@Override
	protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
		builder.add(POWERED, POWER, DIRECT_POWER);
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

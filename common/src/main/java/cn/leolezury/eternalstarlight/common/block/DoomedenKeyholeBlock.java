package cn.leolezury.eternalstarlight.common.block;

import cn.leolezury.eternalstarlight.common.util.ESTags;
import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.ItemInteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
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
import net.minecraft.world.phys.BlockHitResult;
import org.jetbrains.annotations.Nullable;

import java.util.Arrays;

public class DoomedenKeyholeBlock extends HorizontalAxisBlock {
	public static final MapCodec<DoomedenKeyholeBlock> CODEC = simpleCodec(DoomedenKeyholeBlock::new);
	public static final BooleanProperty LIT = BlockStateProperties.LIT;

	public DoomedenKeyholeBlock(Properties properties) {
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
	protected ItemInteractionResult useItemOn(ItemStack itemStack, BlockState blockState, Level level, BlockPos blockPos, Player player, InteractionHand interactionHand, BlockHitResult blockHitResult) {
		if (itemStack.is(ESTags.Items.DOOMEDEN_KEYS) && !blockState.getValue(LIT)) {
			level.setBlockAndUpdate(blockPos, blockState.setValue(LIT, true));
			level.scheduleTick(blockPos, this, 15);
			return ItemInteractionResult.sidedSuccess(level.isClientSide);
		} else {
			return ItemInteractionResult.PASS_TO_DEFAULT_BLOCK_INTERACTION;
		}
	}

	@Override
	protected void neighborChanged(BlockState state, Level level, BlockPos pos, Block block, BlockPos fromPos, boolean isMoving) {
		if (level.getBlockState(fromPos).getBlock() instanceof RedstoneDoomedenKeyholeBlock && !state.getValue(LIT) && Arrays.stream(Direction.values()).anyMatch(direction -> hasInputSignal(level, pos, direction))) {
			level.setBlockAndUpdate(pos, state.setValue(LIT, true));
			level.scheduleTick(pos, this, 15);
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
		if (blockState.getValue(LIT)) {
			if (blockState.getValue(AXIS) == Direction.Axis.X) {
				for (int i = -1; i <= 1; i++) {
					for (int j = -1; j <= 1; j++) {
						if (serverLevel.getBlockState(blockPos.offset(0, i, j)).is(ESTags.Blocks.DOOMEDEN_KEYHOLE_DESTROYABLES)) {
							serverLevel.destroyBlock(blockPos.offset(0, i, j), false, null);
						}
					}
				}
			} else {
				for (int i = -1; i <= 1; i++) {
					for (int j = -1; j <= 1; j++) {
						if (serverLevel.getBlockState(blockPos.offset(i, j, 0)).is(ESTags.Blocks.DOOMEDEN_KEYHOLE_DESTROYABLES)) {
							serverLevel.destroyBlock(blockPos.offset(i, j, 0), false, null);
						}
					}
				}
			}
			serverLevel.destroyBlock(blockPos, false, null);
		}
	}

	@Override
	public boolean isSignalSource(BlockState blockState) {
		return blockState.getValue(LIT);
	}

	@Override
	public int getSignal(BlockState blockState, BlockGetter blockGetter, BlockPos blockPos, Direction direction) {
		return blockState.getValue(LIT) ? 15 : super.getSignal(blockState, blockGetter, blockPos, direction);
	}

	@Override
	protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
		builder.add(LIT);
		super.createBlockStateDefinition(builder);
	}
}

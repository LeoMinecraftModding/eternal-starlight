package cn.leolezury.eternalstarlight.common.block;

import cn.leolezury.eternalstarlight.common.registry.ESBlocks;
import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.EnchantmentTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import org.jetbrains.annotations.Nullable;

import java.util.Arrays;
import java.util.Map;

public class VelvetumossBlock extends Block implements BonemealableBlock {
	public static final MapCodec<VelvetumossBlock> CODEC = simpleCodec(VelvetumossBlock::new);
	public static final BooleanProperty NORTH = PipeBlock.NORTH;
	public static final BooleanProperty EAST = PipeBlock.EAST;
	public static final BooleanProperty SOUTH = PipeBlock.SOUTH;
	public static final BooleanProperty WEST = PipeBlock.WEST;
	public static final BooleanProperty UP = PipeBlock.UP;
	public static final BooleanProperty DOWN = PipeBlock.DOWN;
	private static final Map<Direction, BooleanProperty> PROPERTY_BY_DIRECTION = PipeBlock.PROPERTY_BY_DIRECTION;

	@Override
	public MapCodec<VelvetumossBlock> codec() {
		return CODEC;
	}

	public VelvetumossBlock(BlockBehaviour.Properties properties) {
		super(properties);
		this.registerDefaultState(
			this.stateDefinition
				.any()
				.setValue(NORTH, true)
				.setValue(EAST, true)
				.setValue(SOUTH, true)
				.setValue(WEST, true)
				.setValue(UP, true)
				.setValue(DOWN, true)
		);
	}

	@Override
	public BlockState getStateForPlacement(BlockPlaceContext context) {
		BlockGetter blockgetter = context.getLevel();
		BlockPos blockpos = context.getClickedPos();
		return this.defaultBlockState()
			.setValue(DOWN, !blockgetter.getBlockState(blockpos.below()).is(this))
			.setValue(UP, !blockgetter.getBlockState(blockpos.above()).is(this))
			.setValue(NORTH, !blockgetter.getBlockState(blockpos.north()).is(this))
			.setValue(EAST, !blockgetter.getBlockState(blockpos.east()).is(this))
			.setValue(SOUTH, !blockgetter.getBlockState(blockpos.south()).is(this))
			.setValue(WEST, !blockgetter.getBlockState(blockpos.west()).is(this));
	}

	@Override
	protected BlockState updateShape(BlockState state, Direction facing, BlockState facingState, LevelAccessor level, BlockPos currentPos, BlockPos facingPos) {
		return facingState.is(this)
			? state.setValue(PROPERTY_BY_DIRECTION.get(facing), false)
			: super.updateShape(state, facing, facingState, level, currentPos, facingPos);
	}

	@Override
	protected void randomTick(BlockState blockState, ServerLevel serverLevel, BlockPos blockPos, RandomSource randomSource) {
		if (randomSource.nextInt(12) == 0) {
			for (Direction direction : Direction.values()) {
				BlockPos growPos = blockPos.relative(direction);
				if (serverLevel.getBlockState(growPos).is(Blocks.WATER) && randomSource.nextInt(5) == 0) {
					serverLevel.setBlockAndUpdate(growPos, ESBlocks.VELVETUMOSS_VILLI.get().defaultBlockState().setValue(DirectionalBudBlock.WATERLOGGED, true).setValue(DirectionalBudBlock.FACING, direction));
				}
			}
		}
	}

	@Override
	public void playerDestroy(Level level, Player player, BlockPos blockPos, BlockState blockState, @Nullable BlockEntity blockEntity, ItemStack itemStack) {
		super.playerDestroy(level, player, blockPos, blockState, blockEntity, itemStack);
		if (!EnchantmentHelper.hasTag(itemStack, EnchantmentTags.PREVENTS_ICE_MELTING)) {
			if (level.dimensionType().ultraWarm()) {
				level.removeBlock(blockPos, false);
				return;
			}
			level.setBlockAndUpdate(blockPos, Blocks.WATER.defaultBlockState());
		}
	}

	@Override
	protected BlockState rotate(BlockState state, Rotation rot) {
		return state.setValue(PROPERTY_BY_DIRECTION.get(rot.rotate(Direction.NORTH)), state.getValue(NORTH))
			.setValue(PROPERTY_BY_DIRECTION.get(rot.rotate(Direction.SOUTH)), state.getValue(SOUTH))
			.setValue(PROPERTY_BY_DIRECTION.get(rot.rotate(Direction.EAST)), state.getValue(EAST))
			.setValue(PROPERTY_BY_DIRECTION.get(rot.rotate(Direction.WEST)), state.getValue(WEST))
			.setValue(PROPERTY_BY_DIRECTION.get(rot.rotate(Direction.UP)), state.getValue(UP))
			.setValue(PROPERTY_BY_DIRECTION.get(rot.rotate(Direction.DOWN)), state.getValue(DOWN));
	}

	@Override
	protected BlockState mirror(BlockState state, Mirror mirror) {
		return state.setValue(PROPERTY_BY_DIRECTION.get(mirror.mirror(Direction.NORTH)), state.getValue(NORTH))
			.setValue(PROPERTY_BY_DIRECTION.get(mirror.mirror(Direction.SOUTH)), state.getValue(SOUTH))
			.setValue(PROPERTY_BY_DIRECTION.get(mirror.mirror(Direction.EAST)), state.getValue(EAST))
			.setValue(PROPERTY_BY_DIRECTION.get(mirror.mirror(Direction.WEST)), state.getValue(WEST))
			.setValue(PROPERTY_BY_DIRECTION.get(mirror.mirror(Direction.UP)), state.getValue(UP))
			.setValue(PROPERTY_BY_DIRECTION.get(mirror.mirror(Direction.DOWN)), state.getValue(DOWN));
	}

	@Override
	protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
		builder.add(UP, DOWN, NORTH, EAST, SOUTH, WEST);
	}

	@Override
	public boolean isValidBonemealTarget(LevelReader levelReader, BlockPos blockPos, BlockState blockState) {
		return Arrays.stream(Direction.values()).anyMatch(direction -> levelReader.getBlockState(blockPos.relative(direction)).is(Blocks.WATER));
	}

	@Override
	public boolean isBonemealSuccess(Level level, RandomSource randomSource, BlockPos blockPos, BlockState blockState) {
		return Arrays.stream(Direction.values()).anyMatch(direction -> level.getBlockState(blockPos.relative(direction)).is(Blocks.WATER));
	}

	@Override
	public void performBonemeal(ServerLevel serverLevel, RandomSource randomSource, BlockPos blockPos, BlockState blockState) {
		for (Direction direction : Direction.values()) {
			BlockPos growPos = blockPos.relative(direction);
			if (serverLevel.getBlockState(growPos).is(Blocks.WATER) && randomSource.nextBoolean()) {
				serverLevel.setBlockAndUpdate(growPos, ESBlocks.VELVETUMOSS_VILLI.get().defaultBlockState().setValue(DirectionalBudBlock.WATERLOGGED, true).setValue(DirectionalBudBlock.FACING, direction));
			}
		}
	}
}

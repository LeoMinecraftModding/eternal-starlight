package cn.leolezury.eternalstarlight.common.block;

import cn.leolezury.eternalstarlight.common.registry.ESBlocks;
import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.CarpetBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import org.jetbrains.annotations.Nullable;

import java.util.Arrays;
import java.util.List;

public class CaveMossCarpetBlock extends CarpetBlock {
	public static final MapCodec<CaveMossCarpetBlock> CODEC = simpleCodec(CaveMossCarpetBlock::new);
	public static final BooleanProperty BOTTOM = BooleanProperty.create("bottom");

	@Override
	public MapCodec<CaveMossCarpetBlock> codec() {
		return CODEC;
	}

	public CaveMossCarpetBlock(Properties properties) {
		super(properties);
		this.registerDefaultState(this.stateDefinition.any().setValue(BOTTOM, false));
	}

	@Nullable
	@Override
	public BlockState getStateForPlacement(BlockPlaceContext context) {
		BlockState state = super.getStateForPlacement(context);
		if (state == null) return null;
		boolean sturdy = true;
		Level level = context.getLevel();
		BlockPos blockPos = context.getClickedPos();
		List<Direction> xzDirections = Arrays.stream(Direction.values()).filter(dir -> dir.getAxis() != Direction.Axis.Y).toList();
		for (Direction dir : xzDirections) {
			sturdy &= level.getBlockState(blockPos.below()).isFaceSturdy(level, blockPos.below(), dir);
		}
		return state.setValue(BOTTOM, !level.getBlockState(blockPos.below()).is(this) && !level.getBlockState(blockPos.below()).is(ESBlocks.CAVE_MOSS_BLOCK.get()) && sturdy);
	}

	@Override
	public BlockState updateShape(BlockState state, Direction direction, BlockState blockState2, LevelAccessor level, BlockPos blockPos, BlockPos blockPos2) {
		boolean sturdy = true;
		List<Direction> xzDirections = Arrays.stream(Direction.values()).filter(dir -> dir.getAxis() != Direction.Axis.Y).toList();
		for (Direction dir : xzDirections) {
			sturdy &= level.getBlockState(blockPos.below()).isFaceSturdy(level, blockPos.below(), dir);
		}
		return super.updateShape(state.setValue(BOTTOM, !level.getBlockState(blockPos.below()).is(this) && !level.getBlockState(blockPos.below()).is(ESBlocks.CAVE_MOSS_BLOCK.get()) && sturdy), direction, blockState2, level, blockPos, blockPos2);
	}

	@Override
	protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
		builder.add(BOTTOM);
	}
}

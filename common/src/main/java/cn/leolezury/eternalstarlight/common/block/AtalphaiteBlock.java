package cn.leolezury.eternalstarlight.common.block;

import cn.leolezury.eternalstarlight.common.registry.ESBlocks;
import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.tags.FluidTags;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;

import java.util.Arrays;

public class AtalphaiteBlock extends Block {
	public static final MapCodec<AtalphaiteBlock> CODEC = simpleCodec(AtalphaiteBlock::new);

	public AtalphaiteBlock(Properties properties) {
		super(properties);
	}

	@Override
	protected MapCodec<AtalphaiteBlock> codec() {
		return CODEC;
	}

	@Override
	public @Nullable BlockState getStateForPlacement(BlockPlaceContext context) {
		BlockState state = super.getStateForPlacement(context);
		return state == null ? null : (Arrays.stream(Direction.values()).anyMatch(direction -> context.getLevel().getFluidState(context.getClickedPos().relative(direction)).is(FluidTags.LAVA)) ? ESBlocks.BLAZING_ATALPHAITE_BLOCK.get().defaultBlockState() : state);
	}

	@Override
	protected BlockState updateShape(BlockState state, Direction direction, BlockState neighborState, LevelAccessor level, BlockPos pos, BlockPos neighborPos) {
		return neighborState.getFluidState().is(FluidTags.LAVA) ? ESBlocks.BLAZING_ATALPHAITE_BLOCK.get().defaultBlockState() : state;
	}
}

package cn.leolezury.eternalstarlight.common.block;

import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.ScheduledTickAccess;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.BubbleColumnBlock;
import net.minecraft.world.level.block.state.BlockState;

public class ThermalSpringstoneBlock extends Block {
	public static final MapCodec<ThermalSpringstoneBlock> CODEC = simpleCodec(ThermalSpringstoneBlock::new);

	public ThermalSpringstoneBlock(Properties properties) {
		super(properties);
	}

	@Override
	protected MapCodec<ThermalSpringstoneBlock> codec() {
		return CODEC;
	}

	@Override
	public void tick(BlockState state, ServerLevel level, BlockPos pos, RandomSource source) {
		BubbleColumnBlock.updateColumn(level, pos.above(), state);
	}

	@Override
	protected BlockState updateShape(BlockState state, LevelReader level, ScheduledTickAccess tickAccess, BlockPos pos, Direction direction, BlockPos neighborPos, BlockState neighborState, RandomSource random) {
		if (direction == Direction.UP && neighborState.is(Blocks.WATER)) {
			tickAccess.scheduleTick(pos, this, 20);
		}
		return super.updateShape(state, level, tickAccess, pos, direction, neighborPos, neighborState, random);
	}

	@Override
	public void onPlace(BlockState state, Level level, BlockPos pos, BlockState state1, boolean b) {
		level.scheduleTick(pos, this, 20);
	}
}

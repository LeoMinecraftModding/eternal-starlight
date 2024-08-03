package cn.leolezury.eternalstarlight.common.block;

import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
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
	protected MapCodec<? extends Block> codec() {
		return CODEC;
	}

	@Override
	public void tick(BlockState state, ServerLevel level, BlockPos pos, RandomSource source) {
		BubbleColumnBlock.updateColumn(level, pos.above(), state);
	}

	@Override
	public BlockState updateShape(BlockState state, Direction direction, BlockState newState, LevelAccessor level, BlockPos pos, BlockPos pos1) {
		if (direction == Direction.UP && newState.is(Blocks.WATER)) {
			level.scheduleTick(pos, this, 20);
		}

		return super.updateShape(state, direction, newState, level, pos, pos1);
	}

	@Override
	public void onPlace(BlockState state, Level level, BlockPos pos, BlockState state1, boolean b) {
		level.scheduleTick(pos, this, 20);
	}
}

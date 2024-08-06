package cn.leolezury.eternalstarlight.common.block;

import cn.leolezury.eternalstarlight.common.registry.ESBlocks;
import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;

public class BuddingSulfurQuartzBlock extends Block {
	public static final MapCodec<BuddingSulfurQuartzBlock> CODEC = simpleCodec(BuddingSulfurQuartzBlock::new);

	public BuddingSulfurQuartzBlock(Properties properties) {
		super(properties);
	}

	@Override
	protected MapCodec<BuddingSulfurQuartzBlock> codec() {
		return CODEC;
	}

	@Override
	protected void randomTick(BlockState blockState, ServerLevel serverLevel, BlockPos blockPos, RandomSource randomSource) {
		if (randomSource.nextInt(5) == 0) {
			for (Direction direction : Direction.values()) {
				BlockPos growPos = blockPos.relative(direction);
				boolean water = serverLevel.getBlockState(growPos).is(Blocks.WATER);
				if (serverLevel.getBlockState(growPos).isAir() || water) {
					serverLevel.setBlockAndUpdate(growPos, ESBlocks.THIOQUARTZ_CLUSTER.get().defaultBlockState().setValue(DirectionalBudBlock.WATERLOGGED, water).setValue(DirectionalBudBlock.FACING, direction));
				}
			}
		}
	}
}

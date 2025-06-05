package cn.leolezury.eternalstarlight.common.block;

import cn.leolezury.eternalstarlight.common.registry.ESBlocks;
import com.mojang.serialization.MapCodec;
import net.minecraft.Util;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.BonemealableBlock;
import net.minecraft.world.level.block.PipeBlock;
import net.minecraft.world.level.block.RotatedPillarBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class JinglestemLogBlock extends RotatedPillarBlock implements BonemealableBlock {
	public static final MapCodec<JinglestemLogBlock> CODEC = simpleCodec(JinglestemLogBlock::new);

	public JinglestemLogBlock(Properties properties) {
		super(properties);
	}

	@Override
	public MapCodec<JinglestemLogBlock> codec() {
		return CODEC;
	}

	@Override
	public boolean isValidBonemealTarget(LevelReader level, BlockPos pos, BlockState state) {
		for (Direction direction : Direction.values()) {
			if (level.getBlockState(pos.relative(direction)).is(Blocks.WATER)) {
				return true;
			}
		}
		return false;
	}

	@Override
	public boolean isBonemealSuccess(Level level, RandomSource random, BlockPos pos, BlockState state) {
		return true;
	}

	@Override
	public void performBonemeal(ServerLevel level, RandomSource random, BlockPos pos, BlockState state) {
		List<Direction> dirs = new ArrayList<>(Arrays.stream(Direction.values()).toList());
		Util.shuffle(dirs, random);
		for (Direction direction : dirs) {
			BlockPos placePos = pos.relative(direction);
			if (level.getBlockState(placePos).is(Blocks.WATER)) {
				if (direction == Direction.DOWN) {
					level.setBlockAndUpdate(placePos, ESBlocks.HANGING_ALGALEAVES.get().defaultBlockState());
				} else {
					level.setBlockAndUpdate(placePos, ESBlocks.ALGALEAVES.get().defaultBlockState().setValue(PipeBlock.PROPERTY_BY_DIRECTION.get(direction.getOpposite()), true).setValue(BlockStateProperties.WATERLOGGED, true));
				}
				return;
			}
		}
	}
}

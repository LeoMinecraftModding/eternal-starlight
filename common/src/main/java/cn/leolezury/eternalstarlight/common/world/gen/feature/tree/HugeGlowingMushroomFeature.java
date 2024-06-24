package cn.leolezury.eternalstarlight.common.world.gen.feature.tree;

import com.mojang.serialization.Codec;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.HugeMushroomBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.configurations.HugeMushroomFeatureConfiguration;

import java.util.ArrayList;
import java.util.List;

public class HugeGlowingMushroomFeature extends Feature<HugeMushroomFeatureConfiguration> {
	public HugeGlowingMushroomFeature(Codec<HugeMushroomFeatureConfiguration> codec) {
		super(codec);
	}

	@Override
	public boolean place(FeaturePlaceContext<HugeMushroomFeatureConfiguration> context) {
		WorldGenLevel level = context.level();
		BlockPos pos = context.origin();
		RandomSource random = context.random();
		HugeMushroomFeatureConfiguration config = context.config();
		if (!level.getBlockState(pos.below()).is(BlockTags.MUSHROOM_GROW_BLOCK) && !level.getBlockState(pos.below()).is(BlockTags.DIRT)) {
			return false;
		}
		int xzRadius = config.foliageRadius;
		int foliageHeight = 4;
		int trunkHeight = 10;
		List<BlockPos> mushroomBlocks = new ArrayList<>();
		List<BlockPos> stemBlocks = new ArrayList<>();
		for (int y = 0; y <= trunkHeight; y++) {
			stemBlocks.add(pos.offset(0, y, 0));
			if (!level.isEmptyBlock(pos.offset(0, y, 0))) {
				return false;
			}
		}
		pos = pos.offset(0, trunkHeight, 0);
		mushroomBlocks.add(pos.offset(0, 1, 0));
		if (!level.isEmptyBlock(pos.offset(0, 1, 0))) {
			return false;
		}
		for (int y = 0; y >= -foliageHeight; y--) {
			int radius = Mth.lerpInt((float) y / foliageHeight, xzRadius, 0);
			int radiusNext = Mth.lerpInt((float) (y - 1) / foliageHeight, xzRadius, 0);
			if (radius < radiusNext) {
				radius = random.nextInt(radius, radiusNext);
			}
			for (int x = -radius; x <= radius; x++) {
				for (int z = -radius; z <= radius; z++) {
					if (x * x + z * z <= radius * radius) {
						mushroomBlocks.add(pos.offset(x, y, z));
						if (!level.isEmptyBlock(pos.offset(x, y, z))) {
							return false;
						}
						for (Direction direction : Direction.values()) {
							if (random.nextInt(5) == 0) {
								mushroomBlocks.add(pos.offset(x, y, z).relative(direction));
								if (!level.isEmptyBlock(pos.offset(x, y, z).relative(direction))) {
									return false;
								}
							}
						}
					}
				}
			}
		}
		for (BlockPos blockPos : mushroomBlocks) {
			BlockState state = config.capProvider.getState(random, blockPos);
			if (mushroomBlocks.contains(blockPos.relative(Direction.UP))) {
				state = state.setValue(HugeMushroomBlock.UP, false);
			}
			if (mushroomBlocks.contains(blockPos.relative(Direction.DOWN))) {
				state = state.setValue(HugeMushroomBlock.DOWN, false);
			}
			if (mushroomBlocks.contains(blockPos.relative(Direction.NORTH))) {
				state = state.setValue(HugeMushroomBlock.NORTH, false);
			}
			if (mushroomBlocks.contains(blockPos.relative(Direction.SOUTH))) {
				state = state.setValue(HugeMushroomBlock.SOUTH, false);
			}
			if (mushroomBlocks.contains(blockPos.relative(Direction.WEST))) {
				state = state.setValue(HugeMushroomBlock.WEST, false);
			}
			if (mushroomBlocks.contains(blockPos.relative(Direction.EAST))) {
				state = state.setValue(HugeMushroomBlock.EAST, false);
			}
			if (blockPos.getY() <= pos.getY() - foliageHeight) {
				state = state.setValue(HugeMushroomBlock.DOWN, false);
				if (blockPos.getY() < pos.getY() - foliageHeight) {
					state = state.setValue(HugeMushroomBlock.UP, false)
						.setValue(HugeMushroomBlock.NORTH, false)
						.setValue(HugeMushroomBlock.SOUTH, false)
						.setValue(HugeMushroomBlock.WEST, false)
						.setValue(HugeMushroomBlock.EAST, false);
				}
			}
			setBlock(level, blockPos, state);
		}
		for (BlockPos blockPos : stemBlocks) {
			setBlock(level, blockPos, config.stemProvider.getState(random, blockPos));
		}
		return true;
	}
}

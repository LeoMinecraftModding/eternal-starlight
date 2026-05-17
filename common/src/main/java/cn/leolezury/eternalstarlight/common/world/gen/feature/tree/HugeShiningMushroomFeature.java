package cn.leolezury.eternalstarlight.common.world.gen.feature.tree;

import com.mojang.serialization.Codec;
import net.minecraft.core.BlockPos;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.HugeMushroomBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.configurations.HugeMushroomFeatureConfiguration;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class HugeShiningMushroomFeature extends Feature<HugeMushroomFeatureConfiguration> {
	public HugeShiningMushroomFeature(Codec<HugeMushroomFeatureConfiguration> codec) {
		super(codec);
	}

	@Override
	public boolean place(FeaturePlaceContext<HugeMushroomFeatureConfiguration> context) {
		WorldGenLevel level = context.level();
		BlockPos origin = context.origin();
		RandomSource random = context.random();
		HugeMushroomFeatureConfiguration config = context.config();

		BlockState groundBlock = level.getBlockState(origin.below());
		if (!groundBlock.is(BlockTags.MUSHROOM_GROW_BLOCK) && !groundBlock.is(BlockTags.DIRT)) {
			return false;
		}

		int trunkHeight = random.nextInt(5) + 4;
		if (random.nextInt(5) == 0) {
			trunkHeight *= 2;
		}

		int baseY = origin.getY();
		if (baseY < level.getMinBuildHeight() + 1 || baseY + trunkHeight + 2 >= level.getMaxBuildHeight()) {
			return false;
		}

		int radius = config.foliageRadius;
		BlockPos capCenter = origin.above(trunkHeight);
		int capY = capCenter.getY();
		BlockPos.MutableBlockPos mutablePos = new BlockPos.MutableBlockPos();

		List<BlockPos> stemPositions = new ArrayList<>();
		List<BlockPos> capPositions = new ArrayList<>();
		Set<BlockPos> allPositions = new HashSet<>();

		for (int y = 0; y < trunkHeight; y++) {
			BlockPos stemPos = origin.above(y);
			stemPositions.add(stemPos);
			allPositions.add(stemPos);
		}

		for (int x = -radius; x <= radius; x++) {
			for (int z = -radius; z <= radius; z++) {
				boolean isInner = Math.abs(x) <= radius - 1 && Math.abs(z) <= radius - 1;
				boolean isInnerXEdge = x == -(radius - 1) || x == radius - 1;
				boolean isInnerZEdge = z == -(radius - 1) || z == radius - 1;
				boolean isXEdge = x == -radius || x == radius;
				boolean isZEdge = z == -radius || z == radius;

				if (isXEdge && isZEdge) {
					continue;
				}

				BlockPos capPos = capCenter.offset(x, isInner && !(isInnerXEdge && isInnerZEdge) ? 0 : 1, z);
				capPositions.add(capPos);
				allPositions.add(capPos);
			}
		}

		for (BlockPos pos : allPositions) {
			mutablePos.set(pos);
			BlockState existing = level.getBlockState(mutablePos);

			if (!existing.isAir() && !existing.is(BlockTags.LEAVES)) {
				return false;
			}
		}

		for (BlockPos pos : stemPositions) {
			setBlock(level, pos, config.stemProvider.getState(random, pos));
		}

		for (BlockPos pos : capPositions) {
			BlockState state = config.capProvider.getState(random, pos);

			if (state.hasProperty(HugeMushroomBlock.NORTH)
				&& state.hasProperty(HugeMushroomBlock.SOUTH)
				&& state.hasProperty(HugeMushroomBlock.EAST)
				&& state.hasProperty(HugeMushroomBlock.WEST)
				&& state.hasProperty(HugeMushroomBlock.UP)
				&& state.hasProperty(HugeMushroomBlock.DOWN)) {
				state = state.setValue(HugeMushroomBlock.DOWN, false)
					.setValue(HugeMushroomBlock.UP, true)
					.setValue(HugeMushroomBlock.NORTH, pos.getY() > capY && !capPositions.contains(pos.north()))
					.setValue(HugeMushroomBlock.SOUTH, pos.getY() > capY && !capPositions.contains(pos.south()))
					.setValue(HugeMushroomBlock.EAST, pos.getY() > capY && !capPositions.contains(pos.east()))
					.setValue(HugeMushroomBlock.WEST, pos.getY() > capY && !capPositions.contains(pos.west()));
			}

			setBlock(level, pos, state);
		}

		return true;
	}
}

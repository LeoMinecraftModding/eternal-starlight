package cn.leolezury.eternalstarlight.common.world.gen.feature;

import cn.leolezury.eternalstarlight.common.registry.ESBlocks;
import cn.leolezury.eternalstarlight.common.util.ESMathUtil;
import com.mojang.serialization.Codec;
import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.util.random.SimpleWeightedRandomList;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.stateproviders.WeightedStateProvider;

public class StoneSpikeFeature extends ESFeature<NoneFeatureConfiguration> {
	private static final WeightedStateProvider stateProvider = new WeightedStateProvider(SimpleWeightedRandomList.<BlockState>builder().add(ESBlocks.GRIMSTONE.get().defaultBlockState(), 8).add(ESBlocks.GLOWING_GRIMSTONE.get().defaultBlockState(), 1).build());

	public StoneSpikeFeature(Codec<NoneFeatureConfiguration> codec) {
		super(codec);
	}

	private BlockState getBlockToPlace(RandomSource randomSource, BlockPos pos) {
		return stateProvider.getState(randomSource, pos);
	}

	@Override
	public boolean place(FeaturePlaceContext<NoneFeatureConfiguration> context) {
		WorldGenLevel level = context.level();
		BlockPos pos = context.origin();
		RandomSource random = context.random();
		// generate a sphere
		for (int x = -4; x <= 4; x++) {
			for (int y = -2; y <= 3; y++) {
				for (int z = -4; z <= 4; z++) {
					if (ESMathUtil.isPointInEllipsoid(x, y, z, 4 + random.nextInt(3) - 1, 3 + random.nextInt(3) - 1, 4 + random.nextInt(3) - 1)) {
						setBlockIfEmpty(level, pos.offset(x, y, z), getBlockToPlace(random, pos.offset(x, y, z)));
					}
				}
			}
		}
		// generate the spike
		for (int y = 0; y <= 10; y++) {
			int radius = (int) Math.round(15d / (y + 3));
			int radiusOffset = radius <= 2 ? 0 : random.nextInt(3) - 1;
			for (int x = -radius; x <= radius; x++) {
				for (int z = -radius; z <= radius; z++) {
					if (x * x + z * z <= Math.pow(radius - 1 + radiusOffset, 2)) {
						setBlockIfEmpty(level, pos.offset(x, y, z), getBlockToPlace(random, pos.offset(x, y, z)));
					}
				}
			}
		}
		return true;
	}
}

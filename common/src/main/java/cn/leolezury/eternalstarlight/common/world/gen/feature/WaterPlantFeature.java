package cn.leolezury.eternalstarlight.common.world.gen.feature;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.KelpBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;

public class WaterPlantFeature extends Feature<WaterPlantFeature.Configuration> {
	public WaterPlantFeature(Codec<WaterPlantFeature.Configuration> codec) {
		super(codec);
	}

	@Override
	public boolean place(FeaturePlaceContext<WaterPlantFeature.Configuration> context) {
		int i = 0;
		WorldGenLevel worldGenLevel = context.level();
		BlockPos blockPos = context.origin();
		RandomSource randomSource = context.random();
		int oceanFloor = worldGenLevel.getHeight(Heightmap.Types.OCEAN_FLOOR, blockPos.getX(), blockPos.getZ());
		BlockPos blockPos2 = new BlockPos(blockPos.getX(), oceanFloor, blockPos.getZ());
		if (worldGenLevel.getBlockState(blockPos2).is(Blocks.WATER)) {
			int k = 1 + randomSource.nextInt(10);

			for (int l = 0; l <= k; ++l) {
				BlockState headState = context.config().plantHead().getState(randomSource, blockPos2);
				BlockState bodyState = context.config().plantBody().getState(randomSource, blockPos2);
				if (worldGenLevel.getBlockState(blockPos2).is(Blocks.WATER) && worldGenLevel.getBlockState(blockPos2.above()).is(Blocks.WATER) && bodyState.canSurvive(worldGenLevel, blockPos2)) {
					if (l == k) {
						worldGenLevel.setBlock(blockPos2, headState.setValue(KelpBlock.AGE, randomSource.nextInt(4) + 20), 2);
						++i;
					} else {
						worldGenLevel.setBlock(blockPos2, bodyState, 2);
					}
				} else if (l > 0) {
					BlockPos blockPos3 = blockPos2.below();
					headState = context.config().plantHead().getState(randomSource, blockPos3);
					if (headState.canSurvive(worldGenLevel, blockPos3) && !worldGenLevel.getBlockState(blockPos3.below()).is(headState.getBlock())) {
						worldGenLevel.setBlock(blockPos3, headState.setValue(KelpBlock.AGE, randomSource.nextInt(4) + 20), 2);
						++i;
					}
					break;
				}

				blockPos2 = blockPos2.above();
			}
		}

		return i > 0;
	}

	public record Configuration(BlockStateProvider plantBody, BlockStateProvider plantHead) implements FeatureConfiguration {
		public static final Codec<Configuration> CODEC = RecordCodecBuilder.create((instance) -> instance.group(BlockStateProvider.CODEC.fieldOf("plant_body").forGetter(Configuration::plantBody), BlockStateProvider.CODEC.fieldOf("plant_head").forGetter(Configuration::plantHead)).apply(instance, Configuration::new));
	}
}

package cn.leolezury.eternalstarlight.common.world.gen.feature;

import cn.leolezury.eternalstarlight.common.block.OrbfloraBlock;
import cn.leolezury.eternalstarlight.common.registry.ESBlocks;
import com.mojang.serialization.Codec;
import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;

public class OrbfloraFeature extends Feature<NoneFeatureConfiguration> {
	public OrbfloraFeature(Codec<NoneFeatureConfiguration> codec) {
		super(codec);
	}

	public boolean place(FeaturePlaceContext<NoneFeatureConfiguration> context) {
		boolean success = false;
		WorldGenLevel level = context.level();
		BlockPos origin = context.origin();
		RandomSource random = context.random();
		int oceanFloor = level.getHeight(Heightmap.Types.OCEAN_FLOOR, origin.getX(), origin.getZ());
		BlockPos plantPos = new BlockPos(origin.getX(), oceanFloor, origin.getZ());
		if (level.getBlockState(plantPos).is(Blocks.WATER)) {
			BlockState state = ESBlocks.ORBFLORA.get().defaultBlockState();
			BlockState plantState = ESBlocks.ORBFLORA_PLANT.get().defaultBlockState();
			BlockState lightState = ESBlocks.ORBFLORA_LIGHT.get().defaultBlockState();
			int height = random.nextInt(250) == 0 ? 30 : 1 + random.nextInt(10); // has a chance to grow out of water

			for (int currentHeight = 0; currentHeight <= height; ++currentHeight) {
				if (level.getBlockState(plantPos).is(Blocks.WATER) && plantState.canSurvive(level, plantPos)) {
					if (level.getBlockState(plantPos.above()).is(Blocks.WATER)) {
						if (currentHeight == height) {
							level.setBlock(plantPos, state.setValue(OrbfloraBlock.AGE, random.nextInt(4) + 20), 2);
						} else {
							level.setBlock(plantPos, plantState, 2);
						}
						success = true;
					} else if (currentHeight > 2 && level.isEmptyBlock(plantPos.above())) {
						// we've reached the air
						level.setBlock(plantPos, state.setValue(OrbfloraBlock.AGE, random.nextInt(4) + 20).setValue(OrbfloraBlock.ORBFLORA_AGE, 2), 2);
						level.setBlock(plantPos.above(), lightState, 2);
						success = true;
						return success;
					}
				}
				plantPos = plantPos.above();
			}
		}

		return success;
	}
}

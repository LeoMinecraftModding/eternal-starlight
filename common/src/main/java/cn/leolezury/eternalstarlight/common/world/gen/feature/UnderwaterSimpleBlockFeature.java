package cn.leolezury.eternalstarlight.common.world.gen.feature;

import com.mojang.serialization.Codec;
import net.minecraft.core.BlockPos;
import net.minecraft.tags.FluidTags;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.DoublePlantBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.configurations.SimpleBlockConfiguration;

public class UnderwaterSimpleBlockFeature extends Feature<SimpleBlockConfiguration> {
	public UnderwaterSimpleBlockFeature(Codec<SimpleBlockConfiguration> codec) {
		super(codec);
	}

	public boolean place(FeaturePlaceContext<SimpleBlockConfiguration> featurePlaceContext) {
		SimpleBlockConfiguration simpleBlockConfiguration = featurePlaceContext.config();
		WorldGenLevel worldGenLevel = featurePlaceContext.level();
		BlockPos blockPos = featurePlaceContext.origin();
		BlockState blockState = simpleBlockConfiguration.toPlace().getState(featurePlaceContext.random(), blockPos);
		if (blockState.canSurvive(worldGenLevel, blockPos)) {
			if (blockState.getBlock() instanceof DoublePlantBlock) {
				// ES: Allow underwater placement
				if (!worldGenLevel.isEmptyBlock(blockPos.above()) && !worldGenLevel.getBlockState(blockPos.above()).is(Blocks.WATER)) {
					return false;
				}

				DoublePlantBlock.placeAt(worldGenLevel, blockState, blockPos, 2);
			} else {
				// ES: Allow underwater placement
				if (worldGenLevel.getFluidState(blockPos).is(FluidTags.WATER) && blockState.hasProperty(BlockStateProperties.WATERLOGGED)) {
					blockState = blockState.setValue(BlockStateProperties.WATERLOGGED, true);
				}
				worldGenLevel.setBlock(blockPos, blockState, 2);
			}

			return true;
		} else {
			return false;
		}
	}
}
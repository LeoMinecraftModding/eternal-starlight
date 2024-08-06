package cn.leolezury.eternalstarlight.common.world.gen.feature;

import cn.leolezury.eternalstarlight.common.data.ESBiomes;
import com.mojang.serialization.Codec;
import net.minecraft.core.BlockPos;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;
import net.minecraft.world.level.levelgen.synth.PerlinSimplexNoise;

import java.util.List;

public class SwampWaterFeature extends ESFeature<NoneFeatureConfiguration> {
	public SwampWaterFeature(Codec<NoneFeatureConfiguration> codec) {
		super(codec);
	}

	private long lastSeed;
	private PerlinSimplexNoise noise = new PerlinSimplexNoise(RandomSource.create(lastSeed), List.of(0));

	public void setSeed(long seed) {
		if (seed != lastSeed) {
			this.noise = new PerlinSimplexNoise(RandomSource.create(seed), List.of(0));
			lastSeed = seed;
		}
	}

	@Override
	public boolean place(FeaturePlaceContext<NoneFeatureConfiguration> context) {
		WorldGenLevel level = context.level();
		BlockPos chunkCoord = getChunkCoordinate(context.origin());
		setSeed(level.getSeed());
		for (int x = chunkCoord.getX(); x < chunkCoord.getX() + 16; x++) {
			for (int z = chunkCoord.getZ(); z < chunkCoord.getZ() + 16; z++) {
				if (noise.getValue(x / 20d, z / 20d, false) > -0.1) {
					int y = level.getHeight(Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, x, z) - 1;
					BlockPos waterPos = new BlockPos(x, y, z);
					if (level.getBlockState(waterPos.offset(0, 1, 0)).isAir()
						&& isValid(level.getBlockState(waterPos.offset(0, -1, 0)))
						&& isValid(level.getBlockState(waterPos.offset(1, 0, 0)))
						&& isValid(level.getBlockState(waterPos.offset(-1, 0, 0)))
						&& isValid(level.getBlockState(waterPos.offset(0, 0, 1)))
						&& isValid(level.getBlockState(waterPos.offset(0, 0, -1)))
						&& level.getBlockState(waterPos).is(BlockTags.DIRT)
						&& level.getBiome(waterPos).is(ESBiomes.DARK_SWAMP)
					) {
						setBlock(level, waterPos, Blocks.WATER.defaultBlockState());
					}
				}
			}
		}
		return true;
	}

	protected boolean isValid(BlockState state) {
		return state.is(BlockTags.DIRT) || state.is(Blocks.WATER);
	}
}

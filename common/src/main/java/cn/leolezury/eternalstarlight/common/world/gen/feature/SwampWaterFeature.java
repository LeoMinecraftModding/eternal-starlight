package cn.leolezury.eternalstarlight.common.world.gen.feature;

import cn.leolezury.eternalstarlight.common.util.ESTags;
import com.mojang.serialization.Codec;
import net.minecraft.core.BlockPos;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;
import net.minecraft.world.level.levelgen.synth.PerlinSimplexNoise;

import java.util.List;

public class SwampWaterFeature extends ESFeature<NoneFeatureConfiguration> {
    public SwampWaterFeature(Codec<NoneFeatureConfiguration> codec) {
        super(codec);
    }

    @Override
    public boolean place(FeaturePlaceContext<NoneFeatureConfiguration> context) {
        WorldGenLevel level = context.level();
        PerlinSimplexNoise noise = new PerlinSimplexNoise(RandomSource.create(level.getSeed() / 5 + 3), List.of(0));
        BlockPos chunkCoord = getChunkCoordinate(context.origin()).offset(1, 0, 1);

        for (int x = chunkCoord.getX(); x < chunkCoord.getX() + 16; x++) {
            for (int z = chunkCoord.getZ(); z < chunkCoord.getZ() + 16; z++) {
                if (noise.getValue(x, z, false) > 0) {
                    for (int y = chunkCoord.getY() - 16; y < chunkCoord.getY() + 16; y++) {
                        BlockPos waterPos = new BlockPos(x, y, z);
                        if (level.getBlockState(waterPos.offset(0, 1, 0)).isAir()
                                && isValid(level.getBlockState(waterPos.offset(0, -1, 0)))
                                && isValid(level.getBlockState(waterPos.offset(1, 0, 0)))
                                && isValid(level.getBlockState(waterPos.offset(-1, 0, 0)))
                                && isValid(level.getBlockState(waterPos.offset(0, 0, 1)))
                                && isValid(level.getBlockState(waterPos.offset(0, 0, -1)))
                                && level.getBlockState(waterPos).is(BlockTags.DIRT)
                                && level.getBiome(waterPos).is(ESTags.Biomes.DARK_SWAMP_VARIANT)
                        ) {
                            setBlock(level, waterPos, Blocks.WATER.defaultBlockState());
                        }
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

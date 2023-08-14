package cn.leolezury.eternalstarlight.world.feature;

import cn.leolezury.eternalstarlight.util.FastNoiseLite;
import com.mojang.serialization.Codec;
import net.minecraft.core.BlockPos;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;

public class SwampWaterFeature extends SLFeature<NoneFeatureConfiguration> {
    public SwampWaterFeature(Codec<NoneFeatureConfiguration> codec) {
        super(codec);
        this.fastNoiseLite = new FastNoiseLite();
    }

    private final FastNoiseLite fastNoiseLite;

    @Override
    public boolean place(FeaturePlaceContext<NoneFeatureConfiguration> context) {
        WorldGenLevel level = context.level();
        fastNoiseLite.SetSeed((int) level.getSeed());
        BlockPos chunkCoord = getChunkCoordinate(context.origin()).offset(1, 0, 1);

        for (int x = chunkCoord.getX(); x < chunkCoord.getX() + 16; x++) {
            for (int z = chunkCoord.getZ(); z < chunkCoord.getZ() + 16; z++) {
                if (fastNoiseLite.GetNoise(x, z) > 0) {
                    for (int y = chunkCoord.getY() - 16; y < chunkCoord.getY() + 16; y++) {
                        BlockPos waterPos = new BlockPos(x, y, z);
                        if (level.getBlockState(waterPos.offset(0, 1, 0)).isAir()
                                && isValid(level.getBlockState(waterPos.offset(0, -1, 0)))
                                && isValid(level.getBlockState(waterPos.offset(1, 0, 0)))
                                && isValid(level.getBlockState(waterPos.offset(-1, 0, 0)))
                                && isValid(level.getBlockState(waterPos.offset(0, 0, 1)))
                                && isValid(level.getBlockState(waterPos.offset(0, 0, -1)))
                                && level.getBlockState(waterPos).is(BlockTags.DIRT)
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

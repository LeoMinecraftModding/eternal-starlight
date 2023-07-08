package cn.leolezury.eternalstarlight.world.feature;

import cn.leolezury.eternalstarlight.util.SLTags;
import com.mojang.serialization.Codec;
import net.minecraft.core.BlockPos;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;

public class StoneSpikeFeature extends Feature<NoneFeatureConfiguration> {
    public StoneSpikeFeature(Codec<NoneFeatureConfiguration> p_66003_) {
        super(p_66003_);
    }

    private BlockState getBlockToPlace(RandomSource randomSource) {
        int i = randomSource.nextInt(3);
        switch (i) {
            case 0 -> {
                return Blocks.STONE.defaultBlockState();
            }
            case 1 -> {
                return Blocks.COBBLESTONE.defaultBlockState();
            }
            case 2 -> {
                return Blocks.MOSSY_COBBLESTONE.defaultBlockState();
            }
        }
        return Blocks.STONE.defaultBlockState();
    }

    private void placeOnTop(WorldGenLevel worldGenLevel, BlockPos blockPos, BlockState state) {
        BlockPos pos = blockPos;
        for(; !worldGenLevel.getBlockState(pos).is(BlockTags.DIRT) && !worldGenLevel.getBlockState(pos).is(BlockTags.SNOW) && !worldGenLevel.getBlockState(pos).is(SLTags.Blocks.BASE_STONE_STARLIGHT) && !worldGenLevel.getBlockState(pos).is(Blocks.COBBLESTONE) && !worldGenLevel.getBlockState(pos).is(Blocks.MOSSY_COBBLESTONE) && pos.getY() > worldGenLevel.getMinBuildHeight() + 2; pos = pos.below()) {
        }
        pos = pos.above();
        if (worldGenLevel.getBlockState(pos).isAir()) {
            setBlock(worldGenLevel, pos, state);
        }
    }

    public boolean place(FeaturePlaceContext<NoneFeatureConfiguration> p_159882_) {
        BlockPos blockPos = p_159882_.origin();
        RandomSource randomsource = p_159882_.random();
        WorldGenLevel worldGenLevel = p_159882_.level();
        int size = 2 * randomsource.nextInt(6) + 2;
        for (int i = -size / 2; i < size / 2; i++) {
            for (int j = -size / 2; j < size / 2; j++) {
                if (randomsource.nextBoolean() && Mth.sqrt((float) i * i + j * j) <= (float) size / 2 + 1) {
                    placeOnTop(worldGenLevel, blockPos.offset(i, 0, j), getBlockToPlace(randomsource));
                    if (randomsource.nextInt(4) == 0) {
                        int height = randomsource.nextInt(6) + 3;
                        for (int k = 0; k < height; k++) {
                            placeOnTop(worldGenLevel, blockPos.offset(i, k, j), getBlockToPlace(randomsource));
                        }
                        for (int k = 0; k < height - randomsource.nextInt(2) + 1; k++) {
                            placeOnTop(worldGenLevel, blockPos.offset(i + 1, k, j + 1), getBlockToPlace(randomsource));
                        }
                        for (int k = 0; k < height - randomsource.nextInt(2) + 1; k++) {
                            placeOnTop(worldGenLevel, blockPos.offset(i + 1, k, j - 1), getBlockToPlace(randomsource));
                        }
                        for (int k = 0; k < height - randomsource.nextInt(2) + 1; k++) {
                            placeOnTop(worldGenLevel, blockPos.offset(i - 1, k, j + 1), getBlockToPlace(randomsource));
                        }
                        for (int k = 0; k < height - randomsource.nextInt(2) + 1; k++) {
                            placeOnTop(worldGenLevel, blockPos.offset(i - 1, k, j - 1), getBlockToPlace(randomsource));
                        }
                    }
                }
            }
        }
        return true;
    }
}

package cn.leolezury.eternalstarlight.world.feature;

import cn.leolezury.eternalstarlight.init.BlockInit;
import cn.leolezury.eternalstarlight.util.SLTags;
import com.mojang.serialization.Codec;
import net.minecraft.core.BlockPos;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.util.random.SimpleWeightedRandomList;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.stateproviders.WeightedStateProvider;

import java.util.List;

public class StoneSpikeFeature extends SLFeature<NoneFeatureConfiguration> {
    public StoneSpikeFeature(Codec<NoneFeatureConfiguration> p_66003_) {
        super(p_66003_);
    }

    private BlockState getBlockToPlace(RandomSource randomSource, BlockPos pos) {
        WeightedStateProvider stateProvider = new WeightedStateProvider(SimpleWeightedRandomList.<BlockState>builder().add(Blocks.COBBLESTONE.defaultBlockState(), 2).add(Blocks.COBBLED_DEEPSLATE.defaultBlockState(), 2).add(Blocks.MOSSY_COBBLESTONE.defaultBlockState(), 2).add(Blocks.STONE.defaultBlockState(), 2).add(Blocks.DEEPSLATE.defaultBlockState(), 2).add(BlockInit.GRIMSTONE.get().defaultBlockState(), 1).add(BlockInit.VOIDSTONE.get().defaultBlockState(), 1).build());
        return stateProvider.getState(randomSource, pos);
    }

    public boolean place(FeaturePlaceContext<NoneFeatureConfiguration> p_159882_) {
        BlockPos blockPos = p_159882_.origin();
        RandomSource randomsource = p_159882_.random();
        WorldGenLevel worldGenLevel = p_159882_.level();
        int size = 2 * randomsource.nextInt(6) + 2;
        for (int i = -size / 2; i < size / 2; i++) {
            for (int j = -size / 2; j < size / 2; j++) {
                if (randomsource.nextBoolean() && Mth.sqrt((float) i * i + j * j) <= (float) size / 2 + 1) {
                    placeOnTop(worldGenLevel, blockPos.offset(i, 0, j), getBlockToPlace(randomsource, blockPos.offset(i, 0, j)), List.of(BlockTags.LEAVES));
                    if (randomsource.nextInt(4) == 0) {
                        int height = randomsource.nextInt(6) + 3;
                        for (int k = 0; k < height; k++) {
                            placeOnTop(worldGenLevel, blockPos.offset(i, k, j), getBlockToPlace(randomsource, blockPos.offset(i, k, j)), List.of(BlockTags.LEAVES));
                        }
                        for (int k = 0; k < height - randomsource.nextInt(2) + 1; k++) {
                            placeOnTop(worldGenLevel, blockPos.offset(i + 1, k, j + 1), getBlockToPlace(randomsource, blockPos.offset(i + 1, k, j + 1)), List.of(BlockTags.LEAVES));
                        }
                        for (int k = 0; k < height - randomsource.nextInt(2) + 1; k++) {
                            placeOnTop(worldGenLevel, blockPos.offset(i + 1, k, j - 1), getBlockToPlace(randomsource, blockPos.offset(i + 1, k, j - 1)), List.of(BlockTags.LEAVES));
                        }
                        for (int k = 0; k < height - randomsource.nextInt(2) + 1; k++) {
                            placeOnTop(worldGenLevel, blockPos.offset(i - 1, k, j + 1), getBlockToPlace(randomsource, blockPos.offset(i - 1, k, j + 1)), List.of(BlockTags.LEAVES));
                        }
                        for (int k = 0; k < height - randomsource.nextInt(2) + 1; k++) {
                            placeOnTop(worldGenLevel, blockPos.offset(i - 1, k, j - 1), getBlockToPlace(randomsource, blockPos.offset(i - 1, k, j - 1)), List.of(BlockTags.LEAVES));
                        }
                    }
                }
            }
        }
        return true;
    }
}

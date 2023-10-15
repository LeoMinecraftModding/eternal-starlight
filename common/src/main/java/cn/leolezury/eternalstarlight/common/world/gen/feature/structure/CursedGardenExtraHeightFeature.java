package cn.leolezury.eternalstarlight.common.world.gen.feature.structure;

import cn.leolezury.eternalstarlight.common.init.BlockInit;
import cn.leolezury.eternalstarlight.common.world.gen.feature.ESFeature;
import com.mojang.serialization.Codec;
import net.minecraft.core.BlockPos;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.TagKey;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;

import java.util.List;

public class CursedGardenExtraHeightFeature extends ESFeature<NoneFeatureConfiguration> {
    public CursedGardenExtraHeightFeature(Codec<NoneFeatureConfiguration> codec) {
        super(codec);
    }

    public boolean place(FeaturePlaceContext<NoneFeatureConfiguration> context) {
        WorldGenLevel worldGenLevel = context.level();
        BlockPos origin = context.origin();
        List<TagKey<Block>> ignored = List.of(BlockTags.REPLACEABLE, BlockTags.LOGS, BlockTags.LEAVES);
        RandomSource random = context.random();
        int baseHeight = random.nextInt(15,30);
        for (int x = -5; x <= 5; x++) {
            for (int z = -5; z <= 5; z++) {
                if (x <= -4 || x >= 4 || z <= -4 || z >= 4) {
                    int height = baseHeight + random.nextInt(5);
                    for (int y = 0; y <= height; y++) {
                        setBlockIfEmpty(worldGenLevel, origin.offset(x, y, z), BlockInit.GRIMSTONE_BRICKS.get().defaultBlockState(), ignored);
                    }
                }
            }
        }
        return true;
    }
}

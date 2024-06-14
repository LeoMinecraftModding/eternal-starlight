package cn.leolezury.eternalstarlight.common.world.gen.feature.structure;

import cn.leolezury.eternalstarlight.common.registry.ESBlocks;
import cn.leolezury.eternalstarlight.common.util.ESTags;
import cn.leolezury.eternalstarlight.common.world.gen.feature.ESFeature;
import com.mojang.serialization.Codec;
import net.minecraft.core.BlockPos;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.TagKey;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;

import java.util.List;

public class GolemForgeChimneyFeature extends ESFeature<NoneFeatureConfiguration> {
    public GolemForgeChimneyFeature(Codec<NoneFeatureConfiguration> codec) {
        super(codec);
    }

    public boolean place(FeaturePlaceContext<NoneFeatureConfiguration> context) {
        WorldGenLevel worldGenLevel = context.level();
        BlockPos origin = context.origin();
        List<TagKey<Block>> ignored = List.of(BlockTags.REPLACEABLE, BlockTags.DIRT, BlockTags.SAND, BlockTags.LOGS, BlockTags.LEAVES, ESTags.Blocks.BASE_STONE_STARLIGHT);
        RandomSource random = context.random();
        int height = worldGenLevel.getHeight(Heightmap.Types.WORLD_SURFACE, origin.getX(), origin.getZ()) - origin.getY();
        if (height > 0) {
            height += random.nextInt(5, 10);
            for (int x = -5; x <= 5; x++) {
                for (int z = -5; z <= 5; z++) {
                    if (!(Math.abs(x) == 5 && Math.abs(z) == 5)) {
                        for (int y = 0; y <= height; y++) {
                            if (Math.abs(x) == 5 || Math.abs(z) == 5) {
                                setBlockIfEmpty(worldGenLevel, origin.offset(x, y, z), ESBlocks.VOIDSTONE_BRICKS.get().defaultBlockState(), true, ignored);
                            } else {
                                setBlockIfEmpty(worldGenLevel, origin.offset(x, y, z), Blocks.AIR.defaultBlockState());
                            }
                        }
                    }
                }
            }
            return true;
        } else return false;
    }
}

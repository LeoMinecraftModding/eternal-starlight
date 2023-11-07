package cn.leolezury.eternalstarlight.common.world.gen.system.transformer.biome.interfaces;

import cn.leolezury.eternalstarlight.common.world.gen.system.provider.AbstractWorldGenProvider;
import cn.leolezury.eternalstarlight.common.world.gen.system.transformer.IterationWithCullTransformer;
import net.minecraft.world.level.levelgen.synth.PerlinSimplexNoise;

import java.util.Random;

public interface NoiseDataTransformer extends IterationWithCullTransformer {
    @Override
    default int transform(int[][] original, int[][] related, AbstractWorldGenProvider provider, Random random, int x, int z, int areaX, int areaZ, int size) {
        return transform(provider, random, getDataSafe(original, x, z, size), getWorldCoord(x, areaX, size), getWorldCoord(z, areaZ, size), provider.noises[0]);
    }

    int transform(AbstractWorldGenProvider provider, Random random, int original, int worldX, int worldZ, PerlinSimplexNoise noise);
}

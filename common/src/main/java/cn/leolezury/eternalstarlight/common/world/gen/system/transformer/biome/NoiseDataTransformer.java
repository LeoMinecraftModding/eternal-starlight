package cn.leolezury.eternalstarlight.common.world.gen.system.transformer.biome;

import cn.leolezury.eternalstarlight.common.world.gen.system.WorldGenProvider;
import cn.leolezury.eternalstarlight.common.world.gen.system.transformer.SkippingIterationTransformer;
import net.minecraft.world.level.levelgen.synth.PerlinSimplexNoise;

import java.util.Random;

public abstract class NoiseDataTransformer extends SkippingIterationTransformer {
    @Override
    public int transform(int[][] original, int[][] related, WorldGenProvider provider, Random random, int x, int z, int areaX, int areaZ, int size) {
        return transform(provider, random, getDataSafe(original, x, z, size), getWorldCoord(x, areaX, size), getWorldCoord(z, areaZ, size), provider.noises[0]);
    }

    public abstract int transform(WorldGenProvider provider, Random random, int original, int worldX, int worldZ, PerlinSimplexNoise noise);
}

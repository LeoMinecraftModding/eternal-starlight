package cn.leolezury.eternalstarlight.common.world.gen.system.transformer.biome.interfaces;

import cn.leolezury.eternalstarlight.common.world.gen.system.provider.AbstractWorldGenProvider;
import cn.leolezury.eternalstarlight.common.world.gen.system.transformer.IterationWithCullTransformer;

import java.util.Random;

public interface NeighborsRelatedTransformer extends IterationWithCullTransformer {
    @Override
    default int transform(int[][] original, int[][] related, AbstractWorldGenProvider provider, Random random, int x, int z, int areaX, int areaZ, int size) {
        return transform(provider, random, original[x][z], getDataSafe(original, x, z + 1, size), getDataSafe(original, x, z - 1, size), getDataSafe(original, x - 1, z, size), getDataSafe(original, x + 1, z, size));
    }

    int transform(AbstractWorldGenProvider provider, Random random, int original, int up, int down, int left, int right);

    default int chooseRandomly(Random random, int... i) {
        return i[random.nextInt(i.length)];
    }
}

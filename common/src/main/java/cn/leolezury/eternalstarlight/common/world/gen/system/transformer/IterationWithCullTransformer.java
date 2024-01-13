package cn.leolezury.eternalstarlight.common.world.gen.system.transformer;

import cn.leolezury.eternalstarlight.common.world.gen.system.provider.WorldGenProvider;

import java.util.Random;

public abstract class IterationWithCullTransformer extends DataTransformer {
    public abstract int transform(int[][] original, int[][] related, WorldGenProvider provider, Random random, int x, int z, int areaX, int areaZ, int size);

    @Override
    public int[][] transform(int[][] original, int[][] related, WorldGenProvider provider, int areaX, int areaZ, int size, long seed, long seedAddition) {
        int[][] transformed = new int[size][size];
        int from = size <= 32 ? 0 : (size / 4 - 8);
        int to = size - from;
        for (int x = 0; x < size; x++) {
            for (int z = 0; z < size; z++) {
                if (x >= from && x <= to && z >= from && z <= to) {
                    transformed[x][z] = transform(original, related, provider, getRandomForPos(x, z, areaX, areaZ, size, seed + seedAddition), x, z, areaX, areaZ, size);
                } else {
                    transformed[x][z] = original[x][z];
                }
            }
        }
        return transformed;
    }
}

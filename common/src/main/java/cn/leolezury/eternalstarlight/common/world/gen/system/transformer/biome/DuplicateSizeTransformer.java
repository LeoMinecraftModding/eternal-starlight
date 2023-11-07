package cn.leolezury.eternalstarlight.common.world.gen.system.transformer.biome;

import cn.leolezury.eternalstarlight.common.world.gen.system.provider.AbstractWorldGenProvider;
import cn.leolezury.eternalstarlight.common.world.gen.system.transformer.DataTransformer;

public class DuplicateSizeTransformer implements DataTransformer {
    @Override
    public int[][] transform(int[][] original, int[][] related, AbstractWorldGenProvider provider, int areaX, int areaZ, int size, long seed, long seedAddition) {
        int newSize = size * 2;
        int[][] transformed = new int[newSize][newSize];
        for (int x = 0; x < newSize; x++) {
            for (int z = 0; z < newSize; z++) {
                transformed[x][z] = original[x / 2][z / 2];
            }
        }
        return transformed;
    }
}

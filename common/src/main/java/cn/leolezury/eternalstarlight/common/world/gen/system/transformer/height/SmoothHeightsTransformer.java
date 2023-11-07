package cn.leolezury.eternalstarlight.common.world.gen.system.transformer.height;

import cn.leolezury.eternalstarlight.common.world.gen.system.provider.AbstractWorldGenProvider;
import cn.leolezury.eternalstarlight.common.world.gen.system.transformer.IterationWithCullTransformer;

import java.util.Random;

public class SmoothHeightsTransformer implements IterationWithCullTransformer {
    private final int size;

    public SmoothHeightsTransformer(int size) {
        this.size = size;
    }

    @Override
    public int transform(int[][] original, int[][] related, AbstractWorldGenProvider provider, Random random, int x, int z, int areaX, int areaZ, int size) {
        int totalCount = 0;
        int total = 0;
        for (int i = -this.size; i <= this.size; i++) {
            for (int j = -this.size; j <= this.size; j++) {
                total += getDataSafe(original, x + i, z + j, size);
                totalCount++;
            }
        }
        return total / totalCount;
    }
}

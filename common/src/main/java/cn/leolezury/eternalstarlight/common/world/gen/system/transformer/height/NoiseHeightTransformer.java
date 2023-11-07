package cn.leolezury.eternalstarlight.common.world.gen.system.transformer.height;

import cn.leolezury.eternalstarlight.common.world.gen.system.biome.BiomeData;
import cn.leolezury.eternalstarlight.common.world.gen.system.biome.BiomeDataRegistry;
import cn.leolezury.eternalstarlight.common.world.gen.system.provider.AbstractWorldGenProvider;
import cn.leolezury.eternalstarlight.common.world.gen.system.transformer.IterationWithCullTransformer;

import java.util.Random;

public class NoiseHeightTransformer implements IterationWithCullTransformer {
    @Override
    public int transform(int[][] original, int[][] related, AbstractWorldGenProvider provider, Random random, int x, int z, int areaX, int areaZ, int size) {
        BiomeData data = BiomeDataRegistry.getBiomeData(related[x][z]);
        int variance = data.variance();
        int maxValleyDepth = data.maxValleyDepth();
        int height = original[x][z];
        int worldX = getWorldCoord(x, areaX, size);
        int worldZ = getWorldCoord(z, areaZ, size);
        if (variance > 0) {
            height += (int) ((0.95 * provider.noises[0].getValue(worldX * 0.004, worldZ * 0.004, false) + 0.05 * provider.noises[1].getValue(worldX * 0.04, worldZ * 0.04, true)) * variance);
        }
        if (maxValleyDepth > 0) {
            double valley = 0.98 * provider.noises[0].getValue(worldX * 0.006, worldZ * 0.006, false) + 0.02 * provider.noises[1].getValue(worldX * 0.06, worldZ * 0.06, true);
            if (valley > 0) {
                height -= (int) (valley * maxValleyDepth);
            }
        }
        return height;
    }
}

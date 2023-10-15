package cn.leolezury.eternalstarlight.common.world.gen.provider.transformer.height;

import cn.leolezury.eternalstarlight.common.world.gen.provider.area.HeightsContainer;
import cn.leolezury.eternalstarlight.common.world.gen.provider.biome.BiomeData;
import cn.leolezury.eternalstarlight.common.world.gen.provider.biome.BiomeDataRegistry;
import cn.leolezury.eternalstarlight.common.world.gen.provider.transformer.height.interfaces.BiomeRelatedTransformer;

public class NoiseHeightTransformer implements BiomeRelatedTransformer {
    public NoiseHeightTransformer(){}

    @Override
    public int transform(HeightsContainer container, int original, int worldX, int worldZ, int biome) {
        BiomeData data = BiomeDataRegistry.getBiomeData(biome);
        int variance = data.variance();
        int maxValleyDepth = data.maxValleyDepth();
        int height = original;
        if (variance > 0) {
            height += (int) ((0.95 * container.provider.noises[0].getValue(worldX * 0.004, worldZ * 0.004, false) + 0.05 * container.provider.noises[1].getValue(worldX * 0.04, worldZ * 0.04, true)) * variance);
        }
        if (maxValleyDepth > 0) {
            double valley = 0.98 * container.provider.noises[0].getValue(worldX * 0.006, worldZ * 0.006, false) + 0.02 * container.provider.noises[1].getValue(worldX * 0.06, worldZ * 0.06, true);
            if (valley > 0) {
                height += (int) (valley * maxValleyDepth);
            }
        }
        return height;
    }
}

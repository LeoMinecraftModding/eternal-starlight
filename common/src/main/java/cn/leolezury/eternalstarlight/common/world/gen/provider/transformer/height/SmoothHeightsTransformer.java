package cn.leolezury.eternalstarlight.common.world.gen.provider.transformer.height;

import cn.leolezury.eternalstarlight.common.world.gen.provider.area.HeightsContainer;
import cn.leolezury.eternalstarlight.common.world.gen.provider.transformer.height.interfaces.HeightDataTransformer;

import java.util.Random;

public class SmoothHeightsTransformer implements HeightDataTransformer {
    private final int size;

    public SmoothHeightsTransformer(int size) {
        this.size = size;
    }

    @Override
    public int transform(HeightsContainer container, Random random, int x, int z) {
        int totalCount = 0;
        int total = 0;
        for (int i = -size; i <= size; i++) {
            for (int j = -size; j <= size; j++) {
                total += container.getDataSafe(x + i, z + j);
                totalCount++;
            }
        }
        return total / totalCount;
    }
}

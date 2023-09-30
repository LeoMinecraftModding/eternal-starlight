package cn.leolezury.eternalstarlight.common.world.gen.areasystem.datatransformer.height;

import cn.leolezury.eternalstarlight.common.world.gen.areasystem.area.HeightsContainer;
import cn.leolezury.eternalstarlight.common.world.gen.areasystem.datatransformer.height.interfaces.HeightDataTransformer;

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

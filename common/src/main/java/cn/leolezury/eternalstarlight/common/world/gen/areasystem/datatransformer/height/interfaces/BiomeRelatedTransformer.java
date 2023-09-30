package cn.leolezury.eternalstarlight.common.world.gen.areasystem.datatransformer.height.interfaces;

import cn.leolezury.eternalstarlight.common.world.gen.areasystem.area.HeightsContainer;

import java.util.Random;

public interface BiomeRelatedTransformer extends HeightDataTransformer {
    @Override
    default int transform(HeightsContainer container, Random random, int x, int z) {
        return transform(container, container.getDataSafe(x, z), container.getWorldX(x), container.getWorldZ(z), container.biomesContainer.getDataSafe(x, z));
    }

    int transform(HeightsContainer container, int original, int worldX, int worldZ, int biome);
}

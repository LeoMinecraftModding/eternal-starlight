package cn.leolezury.eternalstarlight.common.world.gen.provider.transformer.biome.interfaces;

import cn.leolezury.eternalstarlight.common.world.gen.provider.area.BiomesContainer;

import java.util.Random;

public interface NeighborsRelatedTransformer extends BiomeDataTransformer {
    @Override
    default int transform(BiomesContainer container, Random random, int x, int z) {
        return transform(container, random, container.getDataRaw(x, z), container.getDataSafe(x, z + 1), container.getDataSafe(x, z - 1), container.getDataSafe(x - 1, z), container.getDataSafe(x + 1, z));
    }

    int transform(BiomesContainer container, Random random, int original, int up, int down, int left, int right);

    default int chooseRandomly(Random random, int... i) {
        return i[random.nextInt(i.length)];
    }
}

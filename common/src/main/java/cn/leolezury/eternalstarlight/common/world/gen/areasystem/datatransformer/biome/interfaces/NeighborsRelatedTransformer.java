package cn.leolezury.eternalstarlight.common.world.gen.areasystem.datatransformer.biome.interfaces;

import cn.leolezury.eternalstarlight.common.world.gen.areasystem.area.BiomesContainer;

import java.util.Random;

public interface NeighborsRelatedTransformer extends BiomeDataTransformer {
    @Override
    default int transform(BiomesContainer container, Random random, int x, int z) {
        return transform(container, random, container.getDataSafe(x, z), container.getUp(x, z), container.getDown(x, z), container.getLeft(x, z), container.getRight(x, z));
    }

    int transform(BiomesContainer container, Random random, int original, int up, int down, int left, int right);

    default int chooseRandomly(Random random, int... i) {
        return i[random.nextInt(i.length)];
    }
}

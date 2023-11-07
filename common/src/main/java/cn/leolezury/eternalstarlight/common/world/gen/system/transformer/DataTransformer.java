package cn.leolezury.eternalstarlight.common.world.gen.system.transformer;

import cn.leolezury.eternalstarlight.common.world.gen.system.provider.AbstractWorldGenProvider;

import java.util.Random;

public interface DataTransformer {
    int[][] transform(int[][] original, int[][] related, AbstractWorldGenProvider provider, int areaX, int areaZ, int size, long seed, long seedAddition);

    default int getDataSafe(int[][] data, int x, int z, int size) {
        int safeX = x < 0 ? 0 : Math.min(x, size - 1);
        int safeZ = z < 0 ? 0 : Math.min(z, size - 1);
        return data[safeX][safeZ];
    }

    default int getWorldCoord(int coord, int areaCoord, int size) {
        return areaCoord * (size / 2) + coord;
    }

    default Random getRandomForPos(int x, int z, int areaX, int areaZ, int size, long seedWithAddition) {
        return new Random(seedWithAddition * AbstractWorldGenProvider.posAsLong(getWorldCoord(x, areaX, size), getWorldCoord(z, areaZ, size)));
    }
}

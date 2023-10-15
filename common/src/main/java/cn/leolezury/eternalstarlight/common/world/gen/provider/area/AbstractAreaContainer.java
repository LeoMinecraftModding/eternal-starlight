package cn.leolezury.eternalstarlight.common.world.gen.provider.area;

import cn.leolezury.eternalstarlight.common.world.gen.provider.wgenprovider.AbstractWorldGenProvider;
import cn.leolezury.eternalstarlight.common.world.gen.provider.transformer.DataTransformer;

import java.util.Random;

public abstract class AbstractAreaContainer {
    public final AbstractWorldGenProvider provider;
    public int[][] data;
    public final long seed;
    public final int areaX;
    public final int areaZ;
    public int size;
    private boolean finalized = false;

    public AbstractAreaContainer(AbstractWorldGenProvider provider, long seed, int areaX, int areaZ, int size) {
        this.provider = provider;
        this.seed = seed;
        this.areaX = areaX;
        this.areaZ = areaZ;
        this.size = size;
    }
    
    public int getDataRaw(int x, int z) {
        return data[x][z];
    }

    public int getWorldX(int x) {
        return areaX * (size / (finalized ? 1 : 2)) + x;
    }

    public int getWorldZ(int z) {
        return areaZ * (size / (finalized ? 1 : 2)) + z;
    }

    public int getDataSafe(int x, int z) {
        int safeX = x < 0 ? 0 : Math.min(x, size - 1);
        int safeZ = z < 0 ? 0 : Math.min(z, size - 1);
        return getDataRaw(safeX, safeZ);
    }

    public Random getRandomForPos(int x, int z, long seedAddition) {
        return new Random((this.seed + seedAddition) * AbstractWorldGenProvider.posAsLong(getWorldX(x), getWorldZ(z)));
    }

    public void processData(DataTransformer transformer, long seedAddition) {
        int[][] transformed = new int[this.size][this.size];
        int from = this.size <= 32 ? 0 : (this.size / 5);
        int to = this.size - from;
        for (int x = 0; x < this.size; x++) {
            for (int z = 0; z < this.size; z++) {
                if (x >= from && x <= to && z >= from && z <= to) {
                    transformed[x][z] = transformer.transform(this, getRandomForPos(x, z, seedAddition), x, z);
                } else {
                    transformed[x][z] = data[x][z];
                }
            }
        }
        this.data = transformed;
    }

    public void finalizeContainer() {
        if (!finalized) {
            int[][] transformed = new int[size / 2][size / 2];
            for (int x = 0; x < size / 2; x++) {
                for (int z = 0; z < size / 2; z++) {
                    transformed[x][z] = doFinalize(getDataRaw(x + size / 4, z + size / 4));
                }
            }
            this.size = size / 2;
            this.data = transformed;
        }
        this.finalized = true;
    }

    public abstract int doFinalize(int original);
}

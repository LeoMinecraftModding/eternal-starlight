package cn.leolezury.eternalstarlight.common.world.gen.areasystem.area;

import cn.leolezury.eternalstarlight.common.world.gen.areasystem.biomeprovider.AbstractBiomeProvider;
import cn.leolezury.eternalstarlight.common.world.gen.areasystem.datatransformer.DataTransformer;

import java.util.Random;

public abstract class AbstractAreaContainer {
    public final AbstractBiomeProvider provider;
    public int[][] data;
    public final long seed;
    public final int areaX;
    public final int areaZ;
    public int size;
    private boolean finalized = false;

    public AbstractAreaContainer(AbstractBiomeProvider provider, long seed, int areaX, int areaZ, int size) {
        this.provider = provider;
        this.seed = seed;
        this.areaX = areaX;
        this.areaZ = areaZ;
        this.size = size;
    }

    public int[][] getData() {
        return data;
    }

    public void setData(int[][] newData) {
        this.data = newData;
    }

    public int getDataRaw(int x, int z) {
        return getData()[x][z];
    }

    public int getDataSafe(int x, int z) {
        int safeX = x < 0 ? 0 : Math.min(x, size - 1);
        int safeZ = z < 0 ? 0 : Math.min(z, size - 1);
        return getDataRaw(safeX, safeZ);
    }

    public void processData(DataTransformer transformer, long seedAddition) {
        int[][] transformed = getData().clone();
        for (int x = 0; x < this.size; x++) {
            for (int z = 0; z < this.size; z++) {
                transformed[x][z] = transformer.transform(this, getRandomForPos(x, z, seedAddition), x, z);
            }
        }
        setData(transformed);
    }

    public int getWorldX(int x) {
        return areaX * (size / (finalized ? 1 : 2)) + x;
    }

    public int getWorldZ(int z) {
        return areaZ * (size / (finalized ? 1 : 2)) + z;
    }

    public Random getRandomForPos(int x, int z, long seedAddition) {
        return new Random((this.seed + seedAddition) * AbstractBiomeProvider.posAsLong(getWorldX(x), getWorldZ(z)));
    }

    public int getDown(int x, int z) {
        return getDataSafe(x, z - 1);
    }

    public int getUp(int x, int z) {
        return getDataSafe(x, z + 1);
    }

    public int getLeft(int x, int z) {
        return getDataSafe(x - 1, z);
    }

    public int getRight(int x, int z) {
        return getDataSafe(x + 1, z);
    }

    public void finialize() {
        if (!finalized) {
            doFinialize();
        }
        this.finalized = true;
    }

    public abstract void doFinialize();
}

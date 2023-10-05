package cn.leolezury.eternalstarlight.common.world.gen.areasystem.area;

import cn.leolezury.eternalstarlight.common.world.gen.areasystem.biome.BiomeDataRegistry;
import cn.leolezury.eternalstarlight.common.world.gen.areasystem.biomeprovider.AbstractBiomeProvider;

public class BiomesContainer extends AbstractAreaContainer {
    public BiomesContainer(AbstractBiomeProvider provider, long seed, int areaX, int areaZ, int size) {
        super(provider, seed, areaX, areaZ, size);
        data = new int[this.size][this.size];
        for (int x = 0; x < this.size; x++) {
            for (int z = 0; z < this.size; z++) {
                this.data[x][z] = BiomeDataRegistry.STARLIGHT_FOREST;
            }
        }
    }

    public void doubleSize() {
        int newSize = size * 2;
        int[][] transformed = new int[newSize][newSize];
        for (int x = 0; x < newSize; x++) {
            for (int z = 0; z < newSize; z++) {
                transformed[x][z] = getDataRaw(x / 2, z / 2);
            }
        }
        this.size = newSize;
        setData(transformed);
    }

    @Override
    public void doFinialize() {
        int[][] transformed = new int[size / 2][size / 2];
        for (int x = 0; x < size / 2; x++) {
            for (int z = 0; z < size / 2; z++) {
                transformed[x][z] = BiomeDataRegistry.getBiomeId(BiomeDataRegistry.getBiomeData(getDataRaw(x + size / 4, z + size / 4)).biome());
            }
        }
        this.size = size / 2;
        setData(transformed);
    }
}
package cn.leolezury.eternalstarlight.common.world.gen.provider.area;

import cn.leolezury.eternalstarlight.common.world.gen.provider.biome.BiomeDataRegistry;
import cn.leolezury.eternalstarlight.common.world.gen.provider.wgenprovider.AbstractWorldGenProvider;

public class BiomesContainer extends AbstractAreaContainer {
    public BiomesContainer(AbstractWorldGenProvider provider, long seed, int areaX, int areaZ, int size) {
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
        this.data = transformed;
    }

    @Override
    public int doFinalize(int original) {
        return BiomeDataRegistry.getBiomeId(BiomeDataRegistry.getBiomeData(original).biome());
    }
}

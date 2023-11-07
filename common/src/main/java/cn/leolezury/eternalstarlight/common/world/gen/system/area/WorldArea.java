package cn.leolezury.eternalstarlight.common.world.gen.system.area;

import cn.leolezury.eternalstarlight.common.world.gen.system.biome.BiomeDataRegistry;
import cn.leolezury.eternalstarlight.common.world.gen.system.provider.AbstractWorldGenProvider;
import cn.leolezury.eternalstarlight.common.world.gen.system.transformer.BiomeTransformers;
import cn.leolezury.eternalstarlight.common.world.gen.system.transformer.DataTransformer;
import cn.leolezury.eternalstarlight.common.world.gen.system.transformer.HeightTransformers;

public class WorldArea {
    public final AbstractWorldGenProvider provider;
    public final long seed;
    public final int areaX;
    public final int areaZ;
    private int[][] biomes;
    private int[][] heights;
    public int size;

    public WorldArea(AbstractWorldGenProvider provider, int areaX, int areaZ, int size, long seed) {
        this.provider = provider;
        this.areaX = areaX;
        this.areaZ = areaZ;
        this.size = size;
        this.seed = seed;
    }

    public void initBiomes() {
        this.biomes = new int[this.size][this.size];
        for (int x = 0; x < this.size; x++) {
            for (int z = 0; z < this.size; z++) {
                this.biomes[x][z] = BiomeDataRegistry.STARLIGHT_FOREST;
            }
        }
    }

    public void transformBiomes(DataTransformer transformer, long seedAddition) {
        this.biomes = transformer.transform(this.biomes, this.biomes, provider, areaX, areaZ, size, seed, seedAddition);
        this.size = this.biomes.length;
    }

    public void initHeights() {
        this.heights = new int[this.size][this.size];
        for (int x = 0; x < this.size; x++) {
            for (int z = 0; z < this.size; z++) {
                this.heights[x][z] = BiomeDataRegistry.getBiomeData(biomes[x][z]).height();
            }
        }
    }

    public void transformHeights(DataTransformer transformer, long seedAddition) {
        this.heights = transformer.transform(this.heights, this.biomes, provider, areaX, areaZ, size, seed, seedAddition);
    }

    public void finalizeAll() {
        int tempSize = this.size; // 2048
        transformBiomes(BiomeTransformers.FINALIZE, 0);
        int finalSize = this.size; // 1024
        this.size = tempSize; // 2048
        transformHeights(HeightTransformers.FINALIZE, 0);
        this.size = finalSize; // 1024
    }

    public int getBiome(int x, int z) {
        int dataX = ((int) Math.floor(x * size / 1024d)) & (size - 1);
        int dataZ = ((int) Math.floor(z * size / 1024d)) & (size - 1);
        return biomes[dataX][dataZ];
    }

    public int getHeight(int x, int z) {
        int dataX = ((int) Math.floor(x * size / 1024d)) & (size - 1);
        int dataZ = ((int) Math.floor(z * size / 1024d)) & (size - 1);
        return heights[dataX][dataZ];
    }
}

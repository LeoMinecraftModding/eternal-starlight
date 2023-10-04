package cn.leolezury.eternalstarlight.common.world.gen.areasystem.area;

public class WorldArea {
    private final int size;
    private final int[][] biomes;
    private final int[][] heights;

    public WorldArea(BiomesContainer biomesContainer, HeightsContainer heightsContainer) {
        biomesContainer.finialize();
        heightsContainer.finialize();
        this.size = biomesContainer.size;
        this.biomes = biomesContainer.getData();
        this.heights = heightsContainer.getData();
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

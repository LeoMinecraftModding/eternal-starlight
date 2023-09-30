package cn.leolezury.eternalstarlight.common.world.gen.areasystem.area;

import cn.leolezury.eternalstarlight.common.world.gen.areasystem.biome.BiomeDataRegistry;
import net.minecraft.util.Mth;

public class HeightsContainer extends AbstractAreaContainer {
    public final BiomesContainer biomesContainer;

    public HeightsContainer(BiomesContainer biomesContainer) {
        super(biomesContainer.provider, biomesContainer.seed, biomesContainer.areaX, biomesContainer.areaZ, biomesContainer.size);
        this.biomesContainer = biomesContainer;
        data = new int[this.size][this.size];
        for (int x = 0; x < this.size; x++) {
            for (int z = 0; z < this.size; z++) {
                this.data[x][z] = BiomeDataRegistry.getBiomeData(biomesContainer.getDataRaw(x, z)).height();
            }
        }
    }

    @Override
    public void finialize() {
        int[][] transformed = getData().clone();
        for (int x = 0; x < size; x++) {
            for (int z = 0; z < size; z++) {
                transformed[x][z] = Mth.clamp(getDataRaw(x, z), provider.minHeight, provider.maxHeight);
            }
        }
        setData(transformed);
    }
}

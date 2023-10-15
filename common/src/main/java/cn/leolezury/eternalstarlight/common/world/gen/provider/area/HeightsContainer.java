package cn.leolezury.eternalstarlight.common.world.gen.provider.area;

import cn.leolezury.eternalstarlight.common.world.gen.provider.biome.BiomeDataRegistry;
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
    public int doFinalize(int original) {
        return Mth.clamp(original, provider.minHeight, provider.maxHeight);
    }
}

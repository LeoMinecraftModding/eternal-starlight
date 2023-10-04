package cn.leolezury.eternalstarlight.common.world.gen.areasystem.datatransformer.biome;

import cn.leolezury.eternalstarlight.common.world.gen.areasystem.area.BiomesContainer;
import cn.leolezury.eternalstarlight.common.world.gen.areasystem.biome.BiomeDataRegistry;
import cn.leolezury.eternalstarlight.common.world.gen.areasystem.datatransformer.biome.interfaces.NeighborsRelatedTransformer;

import java.util.Random;

public class AddBeachesTransformer implements NeighborsRelatedTransformer {
    private final int beach;

    public AddBeachesTransformer(int beach) {
        this.beach = beach;
    }

    @Override
    public int transform(BiomesContainer container, Random random, int original, int up, int down, int left, int right) {
        if (BiomeDataRegistry.getBiomeData(original).canHaveBeaches()
                && (BiomeDataRegistry.getBiomeData(up).isOcean()
                || BiomeDataRegistry.getBiomeData(down).isOcean()
                || BiomeDataRegistry.getBiomeData(left).isOcean()
                || BiomeDataRegistry.getBiomeData(right).isOcean()
                || up == beach
                || down == beach
                || left == beach
                || right == beach)
        ) {
            return this.beach;
        }
        return original;
    }
}

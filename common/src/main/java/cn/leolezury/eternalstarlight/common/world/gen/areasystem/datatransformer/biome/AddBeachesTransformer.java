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
        // if it's at the edge of an ocean biome, return beach
        if (BiomeDataRegistry.getBiomeData(original).isOcean()
                && (BiomeDataRegistry.getBiomeData(up).canHaveBeaches())
                && (BiomeDataRegistry.getBiomeData(down).canHaveBeaches())
                && (BiomeDataRegistry.getBiomeData(left).canHaveBeaches())
                && (BiomeDataRegistry.getBiomeData(right).canHaveBeaches())
        ) {
            return beach;
        }
        return original;
    }
}

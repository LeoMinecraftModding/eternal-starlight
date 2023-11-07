package cn.leolezury.eternalstarlight.common.world.gen.system.transformer.biome;

import cn.leolezury.eternalstarlight.common.world.gen.system.biome.BiomeDataRegistry;
import cn.leolezury.eternalstarlight.common.world.gen.system.provider.AbstractWorldGenProvider;
import cn.leolezury.eternalstarlight.common.world.gen.system.transformer.biome.interfaces.NeighborsRelatedTransformer;

import java.util.Random;

public class AddBeachesTransformer implements NeighborsRelatedTransformer {
    private final int beach;

    public AddBeachesTransformer(int beach) {
        this.beach = beach;
    }

    @Override
    public int transform(AbstractWorldGenProvider provider, Random random, int original, int up, int down, int left, int right) {
        if (BiomeDataRegistry.getBiomeData(original).isOcean()
                && (BiomeDataRegistry.getBiomeData(up).canHaveBeaches()
                || BiomeDataRegistry.getBiomeData(down).canHaveBeaches()
                || BiomeDataRegistry.getBiomeData(left).canHaveBeaches()
                || BiomeDataRegistry.getBiomeData(right).canHaveBeaches())
        ) {
            return this.beach;
        }
        return original;
    }
}

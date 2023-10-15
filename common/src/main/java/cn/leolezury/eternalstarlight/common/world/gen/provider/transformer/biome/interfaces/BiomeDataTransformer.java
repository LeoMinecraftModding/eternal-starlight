package cn.leolezury.eternalstarlight.common.world.gen.provider.transformer.biome.interfaces;

import cn.leolezury.eternalstarlight.common.world.gen.provider.area.AbstractAreaContainer;
import cn.leolezury.eternalstarlight.common.world.gen.provider.area.BiomesContainer;
import cn.leolezury.eternalstarlight.common.world.gen.provider.transformer.DataTransformer;

import java.util.Random;

public interface BiomeDataTransformer extends DataTransformer {
    @Override
    default int transform(AbstractAreaContainer container, Random random, int x, int z) {
        if (container instanceof BiomesContainer biomesContainer) {
            return this.transform(biomesContainer, random, x, z);
        } else {
            return container.getDataSafe(x, z);
        }
    }

    int transform(BiomesContainer container, Random random, int x, int z);
}

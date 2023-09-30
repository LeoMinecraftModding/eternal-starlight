package cn.leolezury.eternalstarlight.common.world.gen.areasystem.datatransformer.biome.interfaces;

import cn.leolezury.eternalstarlight.common.world.gen.areasystem.area.AbstractAreaContainer;
import cn.leolezury.eternalstarlight.common.world.gen.areasystem.area.BiomesContainer;
import cn.leolezury.eternalstarlight.common.world.gen.areasystem.datatransformer.DataTransformer;

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

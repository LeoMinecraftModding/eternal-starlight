package cn.leolezury.eternalstarlight.common.world.gen.areasystem.datatransformer.height.interfaces;

import cn.leolezury.eternalstarlight.common.world.gen.areasystem.area.AbstractAreaContainer;
import cn.leolezury.eternalstarlight.common.world.gen.areasystem.area.HeightsContainer;
import cn.leolezury.eternalstarlight.common.world.gen.areasystem.datatransformer.DataTransformer;

import java.util.Random;

public interface HeightDataTransformer extends DataTransformer {
    @Override
    default int transform(AbstractAreaContainer container, Random random, int x, int z) {
        if (container instanceof HeightsContainer heightsContainer) {
            return this.transform(heightsContainer, random, x, z);
        } else {
            return container.getDataSafe(x, z);
        }
    }

    int transform(HeightsContainer container, Random random, int x, int z);
}

package cn.leolezury.eternalstarlight.common.world.gen.provider.transformer.height.interfaces;

import cn.leolezury.eternalstarlight.common.world.gen.provider.area.AbstractAreaContainer;
import cn.leolezury.eternalstarlight.common.world.gen.provider.area.HeightsContainer;
import cn.leolezury.eternalstarlight.common.world.gen.provider.transformer.DataTransformer;

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

package cn.leolezury.eternalstarlight.common.world.gen.provider.transformer;

import cn.leolezury.eternalstarlight.common.world.gen.provider.area.AbstractAreaContainer;

import java.util.Random;

public interface DataTransformer {
    int transform(AbstractAreaContainer container, Random random, int x, int z);
}

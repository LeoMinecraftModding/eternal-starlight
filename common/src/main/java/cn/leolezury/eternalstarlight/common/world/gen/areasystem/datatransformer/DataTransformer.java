package cn.leolezury.eternalstarlight.common.world.gen.areasystem.datatransformer;

import cn.leolezury.eternalstarlight.common.world.gen.areasystem.area.AbstractAreaContainer;

import java.util.Random;

public interface DataTransformer {
    int transform(AbstractAreaContainer container, Random random, int x, int z);
}

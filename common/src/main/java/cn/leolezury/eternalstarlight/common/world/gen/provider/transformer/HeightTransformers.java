package cn.leolezury.eternalstarlight.common.world.gen.provider.transformer;

import cn.leolezury.eternalstarlight.common.world.gen.provider.transformer.height.NoiseHeightTransformer;
import cn.leolezury.eternalstarlight.common.world.gen.provider.transformer.height.SmoothHeightsTransformer;

public class HeightTransformers {
    public static final DataTransformer NOISE = new NoiseHeightTransformer();
    public static final DataTransformer SMOOTH_SMALL = new SmoothHeightsTransformer(1);
    public static final DataTransformer SMOOTH_LARGE = new SmoothHeightsTransformer(5);
}

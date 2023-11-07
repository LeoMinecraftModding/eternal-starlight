package cn.leolezury.eternalstarlight.common.world.gen.system.transformer;

import cn.leolezury.eternalstarlight.common.world.gen.system.transformer.height.FinalizeHeightsTransformer;
import cn.leolezury.eternalstarlight.common.world.gen.system.transformer.height.NoiseHeightTransformer;
import cn.leolezury.eternalstarlight.common.world.gen.system.transformer.height.SmoothHeightsTransformer;

public class HeightTransformers {
    public static final DataTransformer FINALIZE = new FinalizeHeightsTransformer();

    public static final DataTransformer NOISE = new NoiseHeightTransformer();
    public static final DataTransformer SMOOTH_SMALL = new SmoothHeightsTransformer(1);
    public static final DataTransformer SMOOTH_LARGE = new SmoothHeightsTransformer(5);
}

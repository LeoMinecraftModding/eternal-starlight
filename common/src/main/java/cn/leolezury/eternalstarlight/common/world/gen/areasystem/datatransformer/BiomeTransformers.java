package cn.leolezury.eternalstarlight.common.world.gen.areasystem.datatransformer;

import cn.leolezury.eternalstarlight.common.world.gen.areasystem.biome.BiomeDataRegistry;
import cn.leolezury.eternalstarlight.common.world.gen.areasystem.datatransformer.biome.*;

public class BiomeTransformers {
    public static final DataTransformer APPLY_BIOMES = new AddBiomesTransformer(0.07);
    public static final DataTransformer ADD_OCEAN = new AddOceanTransformer(BiomeDataRegistry.STARLIT_SEA);
    public static final DataTransformer ADD_BEACHES = new AddBeachesTransformer(BiomeDataRegistry.WARM_SHORE);
    public static final DataTransformer ADD_RIVER = new AddRiverTransformer(BiomeDataRegistry.SHIMMER_RIVER);
    public static final DataTransformer ADD_RIVER_TRANSITION = new AddTransitionBiomeTransformer(BiomeDataRegistry.SHIMMER_RIVER, BiomeDataRegistry.SHIMMER_RIVER_TRANSITION);

    public static final DataTransformer RANDOMIZE = new RandomizeBiomesTransformer();
    public static final DataTransformer ASSIMILATE = new AssimilateBiomesTransformer(false);
    public static final DataTransformer ASSIMILATE_LONELY = new AssimilateBiomesTransformer(true);
}

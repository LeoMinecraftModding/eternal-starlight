package cn.leolezury.eternalstarlight.common.init;

import cn.leolezury.eternalstarlight.common.EternalStarlight;
import cn.leolezury.eternalstarlight.common.platform.registry.RegistrationProvider;
import cn.leolezury.eternalstarlight.common.platform.registry.RegistryObject;
import cn.leolezury.eternalstarlight.common.world.gen.system.transformer.DataTransformerType;
import cn.leolezury.eternalstarlight.common.world.gen.system.transformer.biome.*;
import cn.leolezury.eternalstarlight.common.world.gen.system.transformer.height.FinalizeHeightsTransformer;
import cn.leolezury.eternalstarlight.common.world.gen.system.transformer.height.NoiseHeightTransformer;
import cn.leolezury.eternalstarlight.common.world.gen.system.transformer.height.SmoothHeightsTransformer;
import com.mojang.serialization.Codec;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.ExtraCodecs;

public class DataTransformerTypeInit {
    public static final RegistrationProvider<DataTransformerType<?>> DATA_TRANSFORMER_TYPES = RegistrationProvider.newRegistry(ResourceKey.createRegistryKey(new ResourceLocation(EternalStarlight.MOD_ID, "worldgen_data_transformer_type")), EternalStarlight.MOD_ID);
    public static final Codec<DataTransformerType<?>> CODEC = ExtraCodecs.lazyInitializedCodec(DATA_TRANSFORMER_TYPES.registry()::byNameCodec);
    public static final RegistryObject<DataTransformerType<?>, DataTransformerType<? extends AddBiomesTransformer>> ADD_BIOMES = DATA_TRANSFORMER_TYPES.register("add_biomes", () -> (DataTransformerType<AddBiomesTransformer>) () -> AddBiomesTransformer.CODEC);
    public static final RegistryObject<DataTransformerType<?>, DataTransformerType<? extends AddOceanTransformer>> ADD_OCEAN = DATA_TRANSFORMER_TYPES.register("add_ocean", () -> (DataTransformerType<AddOceanTransformer>) () -> AddOceanTransformer.CODEC);
    public static final RegistryObject<DataTransformerType<?>, DataTransformerType<? extends AddBeachesTransformer>> ADD_BEACHES = DATA_TRANSFORMER_TYPES.register("add_beaches", () -> (DataTransformerType<AddBeachesTransformer>) () -> AddBeachesTransformer.CODEC);
    public static final RegistryObject<DataTransformerType<?>, DataTransformerType<? extends AddRiverTransformer>> ADD_RIVER = DATA_TRANSFORMER_TYPES.register("add_river", () -> (DataTransformerType<AddRiverTransformer>) () -> AddRiverTransformer.CODEC);
    public static final RegistryObject<DataTransformerType<?>, DataTransformerType<? extends AddTheAbyssTransformer>> ADD_THE_ABYSS = DATA_TRANSFORMER_TYPES.register("add_the_abyss", () -> (DataTransformerType<AddTheAbyssTransformer>) () -> AddTheAbyssTransformer.CODEC);
    public static final RegistryObject<DataTransformerType<?>, DataTransformerType<? extends AddTransitionBiomeTransformer>> ADD_TRANSITION = DATA_TRANSFORMER_TYPES.register("add_transition", () -> (DataTransformerType<AddTransitionBiomeTransformer>) () -> AddTransitionBiomeTransformer.CODEC);
    public static final RegistryObject<DataTransformerType<?>, DataTransformerType<? extends RandomizeBiomesTransformer>> RANDOMIZE = DATA_TRANSFORMER_TYPES.register("randomize", () -> (DataTransformerType<RandomizeBiomesTransformer>) () -> RandomizeBiomesTransformer.CODEC);
    public static final RegistryObject<DataTransformerType<?>, DataTransformerType<? extends AssimilateBiomesTransformer>> ASSIMILATE = DATA_TRANSFORMER_TYPES.register("assimilate", () -> (DataTransformerType<AssimilateBiomesTransformer>) () -> AssimilateBiomesTransformer.CODEC);
    public static final RegistryObject<DataTransformerType<?>, DataTransformerType<? extends DuplicateSizeTransformer>> DUPLICATE = DATA_TRANSFORMER_TYPES.register("duplicate", () -> (DataTransformerType<DuplicateSizeTransformer>) () -> DuplicateSizeTransformer.CODEC);
    public static final RegistryObject<DataTransformerType<?>, DataTransformerType<? extends FinalizeBiomesTransformer>> FINALIZE_BIOMES = DATA_TRANSFORMER_TYPES.register("finalize_biomes", () -> (DataTransformerType<FinalizeBiomesTransformer>) () -> FinalizeBiomesTransformer.CODEC);
    public static final RegistryObject<DataTransformerType<?>, DataTransformerType<? extends FinalizeHeightsTransformer>> FINALIZE_HEIGHTS = DATA_TRANSFORMER_TYPES.register("finalize_heights", () -> (DataTransformerType<FinalizeHeightsTransformer>) () -> FinalizeHeightsTransformer.CODEC);
    public static final RegistryObject<DataTransformerType<?>, DataTransformerType<? extends NoiseHeightTransformer>> NOISE_HEIGHT = DATA_TRANSFORMER_TYPES.register("noise_height", () -> (DataTransformerType<NoiseHeightTransformer>) () -> NoiseHeightTransformer.CODEC);
    public static final RegistryObject<DataTransformerType<?>, DataTransformerType<? extends SmoothHeightsTransformer>> SMOOTH_HEIGHTS = DATA_TRANSFORMER_TYPES.register("smooth_heights", () -> (DataTransformerType<SmoothHeightsTransformer>) () -> SmoothHeightsTransformer.CODEC);

    public static void loadClass() {}
}

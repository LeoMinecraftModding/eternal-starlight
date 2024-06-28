package cn.leolezury.eternalstarlight.common.registry;

import cn.leolezury.eternalstarlight.common.EternalStarlight;
import cn.leolezury.eternalstarlight.common.platform.registry.RegistrationProvider;
import cn.leolezury.eternalstarlight.common.platform.registry.RegistryObject;
import cn.leolezury.eternalstarlight.common.world.gen.system.transformer.DataTransformerType;
import cn.leolezury.eternalstarlight.common.world.gen.system.transformer.MergedIterationTransformer;
import cn.leolezury.eternalstarlight.common.world.gen.system.transformer.biome.*;
import cn.leolezury.eternalstarlight.common.world.gen.system.transformer.height.FinalizeHeightsTransformer;
import cn.leolezury.eternalstarlight.common.world.gen.system.transformer.height.NoiseHeightTransformer;
import cn.leolezury.eternalstarlight.common.world.gen.system.transformer.height.SmoothHeightsTransformer;
import com.mojang.serialization.Codec;
import net.minecraft.resources.ResourceKey;

public class ESDataTransformerTypes {
	public static final RegistrationProvider<DataTransformerType<?>> DATA_TRANSFORMER_TYPES = RegistrationProvider.newRegistry(ResourceKey.createRegistryKey(EternalStarlight.id("worldgen_data_transformer_type")), EternalStarlight.ID);
	public static final Codec<DataTransformerType<?>> CODEC = DATA_TRANSFORMER_TYPES.registry().byNameCodec();
	public static final RegistryObject<DataTransformerType<?>, DataTransformerType<? extends MergedIterationTransformer>> MERGED_ITERATION = DATA_TRANSFORMER_TYPES.register("merged_iteration", () -> (DataTransformerType<MergedIterationTransformer>) () -> MergedIterationTransformer.CODEC);
	public static final RegistryObject<DataTransformerType<?>, DataTransformerType<? extends AddBiomesTransformer>> ADD_BIOMES = DATA_TRANSFORMER_TYPES.register("add_biomes", () -> (DataTransformerType<AddBiomesTransformer>) () -> AddBiomesTransformer.CODEC);
	public static final RegistryObject<DataTransformerType<?>, DataTransformerType<? extends AddOceanTransformer>> ADD_OCEAN = DATA_TRANSFORMER_TYPES.register("add_ocean", () -> (DataTransformerType<AddOceanTransformer>) () -> AddOceanTransformer.CODEC);
	public static final RegistryObject<DataTransformerType<?>, DataTransformerType<? extends AddBeachesTransformer>> ADD_BEACHES = DATA_TRANSFORMER_TYPES.register("add_beaches", () -> (DataTransformerType<AddBeachesTransformer>) () -> AddBeachesTransformer.CODEC);
	public static final RegistryObject<DataTransformerType<?>, DataTransformerType<? extends AddRiversTransformer>> ADD_RIVERS = DATA_TRANSFORMER_TYPES.register("add_rivers", () -> (DataTransformerType<AddRiversTransformer>) () -> AddRiversTransformer.CODEC);
	public static final RegistryObject<DataTransformerType<?>, DataTransformerType<? extends AddTheAbyssTransformer>> ADD_THE_ABYSS = DATA_TRANSFORMER_TYPES.register("add_the_abyss", () -> (DataTransformerType<AddTheAbyssTransformer>) () -> AddTheAbyssTransformer.CODEC);
	public static final RegistryObject<DataTransformerType<?>, DataTransformerType<? extends AddTransitionBiomesTransformer>> ADD_TRANSITIONS = DATA_TRANSFORMER_TYPES.register("add_transitions", () -> (DataTransformerType<AddTransitionBiomesTransformer>) () -> AddTransitionBiomesTransformer.CODEC);
	public static final RegistryObject<DataTransformerType<?>, DataTransformerType<? extends RandomizeBiomesTransformer>> RANDOMIZE = DATA_TRANSFORMER_TYPES.register("randomize", () -> (DataTransformerType<RandomizeBiomesTransformer>) () -> RandomizeBiomesTransformer.CODEC);
	public static final RegistryObject<DataTransformerType<?>, DataTransformerType<? extends AssimilateBiomesTransformer>> ASSIMILATE = DATA_TRANSFORMER_TYPES.register("assimilate", () -> (DataTransformerType<AssimilateBiomesTransformer>) () -> AssimilateBiomesTransformer.CODEC);
	public static final RegistryObject<DataTransformerType<?>, DataTransformerType<? extends DuplicateSizeTransformer>> DUPLICATE = DATA_TRANSFORMER_TYPES.register("duplicate", () -> (DataTransformerType<DuplicateSizeTransformer>) () -> DuplicateSizeTransformer.CODEC);
	public static final RegistryObject<DataTransformerType<?>, DataTransformerType<? extends FinalizeBiomesTransformer>> FINALIZE_BIOMES = DATA_TRANSFORMER_TYPES.register("finalize_biomes", () -> (DataTransformerType<FinalizeBiomesTransformer>) () -> FinalizeBiomesTransformer.CODEC);
	public static final RegistryObject<DataTransformerType<?>, DataTransformerType<? extends FinalizeHeightsTransformer>> FINALIZE_HEIGHTS = DATA_TRANSFORMER_TYPES.register("finalize_heights", () -> (DataTransformerType<FinalizeHeightsTransformer>) () -> FinalizeHeightsTransformer.CODEC);
	public static final RegistryObject<DataTransformerType<?>, DataTransformerType<? extends NoiseHeightTransformer>> NOISE_HEIGHT = DATA_TRANSFORMER_TYPES.register("noise_height", () -> (DataTransformerType<NoiseHeightTransformer>) () -> NoiseHeightTransformer.CODEC);
	public static final RegistryObject<DataTransformerType<?>, DataTransformerType<? extends SmoothHeightsTransformer>> SMOOTH_HEIGHTS = DATA_TRANSFORMER_TYPES.register("smooth_heights", () -> (DataTransformerType<SmoothHeightsTransformer>) () -> SmoothHeightsTransformer.CODEC);

	public static void loadClass() {
	}
}

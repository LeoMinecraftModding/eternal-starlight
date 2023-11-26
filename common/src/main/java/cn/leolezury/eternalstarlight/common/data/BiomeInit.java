package cn.leolezury.eternalstarlight.common.data;

import cn.leolezury.eternalstarlight.common.EternalStarlight;
import cn.leolezury.eternalstarlight.common.init.EntityInit;
import cn.leolezury.eternalstarlight.common.init.ParticleInit;
import cn.leolezury.eternalstarlight.common.init.SoundEventInit;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BiomeDefaultFeatures;
import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.Music;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.level.biome.*;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.minecraft.world.level.levelgen.carver.ConfiguredWorldCarver;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;

public class BiomeInit {
    public static final ResourceKey<Biome> STARLIGHT_FOREST = create("starlight_forest");
    public static final ResourceKey<Biome> STARLIGHT_DENSE_FOREST = create("starlight_dense_forest");
    public static final ResourceKey<Biome> STARLIGHT_PERMAFROST_FOREST = create("starlight_permafrost_forest");
    public static final ResourceKey<Biome> DARK_SWAMP = create("dark_swamp");
    public static final ResourceKey<Biome> SHIMMER_RIVER = create("shimmer_river");
    public static final ResourceKey<Biome> STARLIT_SEA = create("starlit_sea");
    public static final ResourceKey<Biome> WARM_SHORE = create("warm_shore");

    public static final Music MUSIC_DEFAULT = new Music(SoundEventInit.MUSIC_DIMENSION_SL.asHolder(), 1200, 12000, true);
    public static final Music MUSIC_FOREST = new Music(SoundEventInit.MUSIC_BIOME_STARLIGHT_FOREST.asHolder(), 1200, 12000, true);
    public static final Music MUSIC_PERMAFROST_FOREST = new Music(SoundEventInit.MUSIC_BIOME_STARLIGHT_PERMAFROST_FOREST.asHolder(), 1200, 12000, true);
    public static final Music MUSIC_SWAMP = new Music(SoundEventInit.MUSIC_BIOME_DARK_SWAMP.asHolder(), 1200, 12000, true);

    public static void bootstrap(BootstapContext<Biome> context) {
        HolderGetter<PlacedFeature> featureHolderGetter = context.lookup(Registries.PLACED_FEATURE);
        HolderGetter<ConfiguredWorldCarver<?>> carverHolderGetter = context.lookup(Registries.CONFIGURED_CARVER);
        context.register(STARLIGHT_FOREST, baseBiomeBuilder(baseEffectsBuilder().backgroundMusic(MUSIC_FOREST), baseSpawnBuilder(), forestSettings(featureHolderGetter, carverHolderGetter)).build());
        context.register(STARLIGHT_DENSE_FOREST, baseBiomeBuilder(baseEffectsBuilder().backgroundMusic(MUSIC_FOREST), baseSpawnBuilder(), denseForestSettings(featureHolderGetter, carverHolderGetter)).build());
        context.register(STARLIGHT_PERMAFROST_FOREST, baseBiomeBuilder(baseEffectsBuilder().fogColor(14803455).skyColor(14803455).backgroundMusic(MUSIC_PERMAFROST_FOREST), baseSpawnBuilder(), snowyForestSettings(featureHolderGetter, carverHolderGetter)).temperature(-0.3f).temperatureAdjustment(Biome.TemperatureModifier.FROZEN).build());
        context.register(DARK_SWAMP, baseBiomeBuilder(baseEffectsBuilder().fogColor(1310740).foliageColorOverride(7890120).skyColor(1310740).waterColor(11494560).waterFogColor(11494560).backgroundMusic(MUSIC_SWAMP), baseSpawnBuilder(), swampSettings(featureHolderGetter, carverHolderGetter)).build());
        context.register(SHIMMER_RIVER, baseBiomeBuilder(baseEffectsBuilder(), baseSpawnBuilder(), baseGenBuilder(featureHolderGetter, carverHolderGetter)).build());
        context.register(STARLIT_SEA, baseBiomeBuilder(baseEffectsBuilder(), baseSpawnBuilder(), baseGenBuilder(featureHolderGetter, carverHolderGetter)).build());
        context.register(WARM_SHORE, baseBiomeBuilder(baseEffectsBuilder(), baseSpawnBuilder(), baseGenBuilder(featureHolderGetter, carverHolderGetter)).build());
    }

    private static Biome.BiomeBuilder baseBiomeBuilder(BiomeSpecialEffects.Builder specialEffects, MobSpawnSettings.Builder mobSpawnSettings, BiomeGenerationSettings.Builder genSettings) {
        return new Biome.BiomeBuilder()
                .hasPrecipitation(true)
                .temperature(0.5F)
                .downfall(0.5F)
                .specialEffects(specialEffects.build())
                .mobSpawnSettings(mobSpawnSettings.build())
                .generationSettings(genSettings.build())
                .temperatureAdjustment(Biome.TemperatureModifier.NONE);
    }

    private static BiomeSpecialEffects.Builder baseEffectsBuilder() {
        return new BiomeSpecialEffects.Builder()
                .fogColor(3091031)
                .foliageColorOverride(3091031)
                .grassColorOverride(3091031)
                .waterColor(6187416)
                .waterFogColor(6187416)
                .skyColor(5658761)
                .ambientParticle(new AmbientParticleSettings(ParticleInit.STARLIGHT.get(), 0.001f))
                .backgroundMusic(MUSIC_DEFAULT);
    }

    private static MobSpawnSettings.Builder baseSpawnBuilder() {
        return new MobSpawnSettings.Builder()
                .creatureGenerationProbability(0.1f)
                .addSpawn(MobCategory.MONSTER, new MobSpawnSettings.SpawnerData(EntityInit.NIGHTSHADE_SPIDER.get(), 10, 1, 2))
                .addSpawn(MobCategory.MONSTER, new MobSpawnSettings.SpawnerData(EntityInit.LONESTAR_SKELETON.get(), 5, 1, 2))
                .addSpawn(MobCategory.CREATURE, new MobSpawnSettings.SpawnerData(EntityInit.DRYAD.get(), 15, 1, 2))
                .addSpawn(MobCategory.AMBIENT, new MobSpawnSettings.SpawnerData(EntityType.BAT, 1, 1, 4));
    }

    public static BiomeGenerationSettings.Builder baseGenBuilder(HolderGetter<PlacedFeature> featureGetter, HolderGetter<ConfiguredWorldCarver<?>> carverGetter) {
        BiomeGenerationSettings.Builder builder = new BiomeGenerationSettings.Builder(featureGetter, carverGetter);

        BiomeDefaultFeatures.addForestGrass(builder);
        BiomeDefaultFeatures.addSavannaGrass(builder);
        BiomeDefaultFeatures.addDefaultGrass(builder);
        BiomeDefaultFeatures.addSavannaExtraGrass(builder);
        BiomeDefaultFeatures.addSurfaceFreezing(builder);
        builder.addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, PlacedFeatureInit.STONE_ORE);
        builder.addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, PlacedFeatureInit.DEEPSLATE_ORE);
        builder.addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, PlacedFeatureInit.NIGHTSHADE_DIRT_ORE);
        builder.addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, PlacedFeatureInit.GLOWING_VOIDSTONE_ORE);
        builder.addFeature(GenerationStep.Decoration.UNDERGROUND_DECORATION, PlacedFeatureInit.CAVE_VINE);

        return builder;
    }

    private static BiomeGenerationSettings.Builder forestSettings(HolderGetter<PlacedFeature> featureGetter, HolderGetter<ConfiguredWorldCarver<?>> carverGetter) {
        BiomeGenerationSettings.Builder builder = baseGenBuilder(featureGetter, carverGetter);

        builder.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, PlacedFeatureInit.STARLIGHT_FLOWER);
        builder.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, PlacedFeatureInit.STARLIGHT_FOREST);
        builder.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, PlacedFeatureInit.COMMON_GRASS);
        builder.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, PlacedFeatureInit.FALLEN_LUNAR_LOG);
        builder.addFeature(GenerationStep.Decoration.LOCAL_MODIFICATIONS, PlacedFeatureInit.STONE_SPIKE);

        return builder;
    }

    private static BiomeGenerationSettings.Builder denseForestSettings(HolderGetter<PlacedFeature> featureGetter, HolderGetter<ConfiguredWorldCarver<?>> carverGetter) {
        BiomeGenerationSettings.Builder builder = baseGenBuilder(featureGetter, carverGetter);

        builder.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, PlacedFeatureInit.STARLIGHT_FLOWER);
        builder.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, PlacedFeatureInit.DENSE_FOREST);
        builder.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, PlacedFeatureInit.COMMON_GRASS);
        builder.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, PlacedFeatureInit.FALLEN_LUNAR_LOG);

        return builder;
    }

    private static BiomeGenerationSettings.Builder snowyForestSettings(HolderGetter<PlacedFeature> featureGetter, HolderGetter<ConfiguredWorldCarver<?>> carverGetter) {
        BiomeGenerationSettings.Builder builder = baseGenBuilder(featureGetter, carverGetter);

        builder.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, PlacedFeatureInit.STARLIGHT_FLOWER);
        builder.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, PlacedFeatureInit.PERMAFROST_FOREST);
        builder.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, PlacedFeatureInit.FALLEN_NORTHLAND_LOG);

        return builder;
    }

    private static BiomeGenerationSettings.Builder swampSettings(HolderGetter<PlacedFeature> featureGetter, HolderGetter<ConfiguredWorldCarver<?>> carverGetter) {
        BiomeGenerationSettings.Builder builder = baseGenBuilder(featureGetter, carverGetter);

        builder.addFeature(GenerationStep.Decoration.LAKES, PlacedFeatureInit.SWAMP_WATER);
        builder.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, PlacedFeatureInit.STARLIGHT_FLOWER);
        builder.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, PlacedFeatureInit.SWAMP_FOREST);
        builder.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, PlacedFeatureInit.COMMON_GRASS);
        builder.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, PlacedFeatureInit.FALLEN_STARLIGHT_MANGROVE_LOG);

        return builder;
    }

    public static ResourceKey<Biome> create(String name) {
        return ResourceKey.create(Registries.BIOME, new ResourceLocation(EternalStarlight.MOD_ID, name));
    }
}

package cn.leolezury.eternalstarlight.common.data;

import cn.leolezury.eternalstarlight.common.EternalStarlight;
import cn.leolezury.eternalstarlight.common.init.EntityInit;
import cn.leolezury.eternalstarlight.common.init.ParticleInit;
import cn.leolezury.eternalstarlight.common.init.SoundEventInit;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BiomeDefaultFeatures;
import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.data.worldgen.placement.VegetationPlacements;
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
    public static final ResourceKey<Biome> SCARLET_FOREST = create("scarlet_forest");
    public static final ResourceKey<Biome> TORREYA_FOREST = create("torreya_forest");
    public static final ResourceKey<Biome> CRYSTALLIZED_DESERT = create("crystallized_desert");
    public static final ResourceKey<Biome> SHIMMER_RIVER = create("shimmer_river");
    public static final ResourceKey<Biome> ETHER_RIVER = create("ether_river");
    public static final ResourceKey<Biome> STARLIT_SEA = create("starlit_sea");
    public static final ResourceKey<Biome> THE_ABYSS = create("the_abyss");
    public static final ResourceKey<Biome> WARM_SHORE = create("warm_shore");

    public static final Music MUSIC_TRANQUILITY = new Music(SoundEventInit.MUSIC_DIMENSION_TRANQUILITY.asHolder(), 1200, 12000, true);
    public static final Music MUSIC_FOREST = new Music(SoundEventInit.MUSIC_BIOME_STARLIGHT_FOREST.asHolder(), 1200, 12000, true);
    public static final Music MUSIC_PERMAFROST_FOREST = new Music(SoundEventInit.MUSIC_BIOME_STARLIGHT_PERMAFROST_FOREST.asHolder(), 1200, 12000, true);
    public static final Music MUSIC_SWAMP = new Music(SoundEventInit.MUSIC_BIOME_DARK_SWAMP.asHolder(), 1200, 12000, true);

    public static void bootstrap(BootstapContext<Biome> context) {
        HolderGetter<PlacedFeature> featureHolderGetter = context.lookup(Registries.PLACED_FEATURE);
        HolderGetter<ConfiguredWorldCarver<?>> carverHolderGetter = context.lookup(Registries.CONFIGURED_CARVER);
        context.register(STARLIGHT_FOREST, baseBiomeBuilder(baseEffectsBuilder().backgroundMusic(MUSIC_FOREST), baseLandSpawnBuilder(), forestSettings(featureHolderGetter, carverHolderGetter)).build());
        context.register(STARLIGHT_DENSE_FOREST, baseBiomeBuilder(baseEffectsBuilder().backgroundMusic(MUSIC_FOREST), baseLandSpawnBuilder(), denseForestSettings(featureHolderGetter, carverHolderGetter)).build());
        context.register(STARLIGHT_PERMAFROST_FOREST, baseBiomeBuilder(baseEffectsBuilder().fogColor(14803455).skyColor(14803455).grassColorOverride(14803455).backgroundMusic(MUSIC_PERMAFROST_FOREST), permafrostForestSpawns(), snowyForestSettings(featureHolderGetter, carverHolderGetter)).temperature(-0.3f).temperatureAdjustment(Biome.TemperatureModifier.FROZEN).build());
        context.register(DARK_SWAMP, baseBiomeBuilder(baseEffectsBuilder().fogColor(1310740).foliageColorOverride(7890120).skyColor(1310740).grassColorOverride(4075082).waterColor(11494560).waterFogColor(11494560).backgroundMusic(MUSIC_SWAMP), baseLandSpawnBuilder(), swampSettings(featureHolderGetter, carverHolderGetter)).build());
        context.register(SCARLET_FOREST, baseBiomeBuilder(baseEffectsBuilder().fogColor(10313569).foliageColorOverride(10313569).skyColor(10313569).grassColorOverride(10313569).backgroundMusic(MUSIC_FOREST), baseLandSpawnBuilder(), scarletForestSettings(featureHolderGetter, carverHolderGetter)).build());
        context.register(TORREYA_FOREST, baseBiomeBuilder(baseEffectsBuilder().fogColor(7229604).foliageColorOverride(7229604).skyColor(7229604).grassColorOverride(7229604).backgroundMusic(MUSIC_FOREST), baseLandSpawnBuilder(), torreyaForestSettings(featureHolderGetter, carverHolderGetter)).build());
        context.register(CRYSTALLIZED_DESERT, baseBiomeBuilder(baseEffectsBuilder().fogColor(8349826).foliageColorOverride(8349826).skyColor(8349826).grassColorOverride(8349826).backgroundMusic(MUSIC_TRANQUILITY), baseLandSpawnBuilder(), desertSettings(featureHolderGetter, carverHolderGetter)).hasPrecipitation(false).temperature(2.0f).build());
        context.register(SHIMMER_RIVER, baseBiomeBuilder(baseEffectsBuilder(), baseAquaticSpawnBuilder(), riverSettings(featureHolderGetter, carverHolderGetter)).build());
        context.register(ETHER_RIVER, baseBiomeBuilder(baseEffectsBuilder().fogColor(14417883).foliageColorOverride(14417883).skyColor(14417883).grassColorOverride(14417883).waterColor(14417883).waterFogColor(14417883), baseAquaticSpawnBuilder(), baseAquaticGenBuilder(featureHolderGetter, carverHolderGetter)).build());
        context.register(STARLIT_SEA, baseBiomeBuilder(baseEffectsBuilder(), baseAquaticSpawnBuilder(), oceanSettings(featureHolderGetter, carverHolderGetter)).build());
        context.register(THE_ABYSS, baseBiomeBuilder(baseEffectsBuilder().waterFogColor(3145761), theAbyssSpawns(), theAbyssSettings(featureHolderGetter, carverHolderGetter)).build());
        context.register(WARM_SHORE, baseBiomeBuilder(baseEffectsBuilder(), baseLandSpawnBuilder(), baseLandGenBuilder(featureHolderGetter, carverHolderGetter)).build());
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
                .backgroundMusic(MUSIC_TRANQUILITY);
    }

    private static MobSpawnSettings.Builder baseLandSpawnBuilder() {
        return new MobSpawnSettings.Builder()
                .creatureGenerationProbability(0.2f)
                .addSpawn(MobCategory.MONSTER, new MobSpawnSettings.SpawnerData(EntityInit.NIGHTSHADE_SPIDER.get(), 10, 1, 2))
                .addSpawn(MobCategory.MONSTER, new MobSpawnSettings.SpawnerData(EntityInit.LONESTAR_SKELETON.get(), 10, 1, 2))
                .addSpawn(MobCategory.CREATURE, new MobSpawnSettings.SpawnerData(EntityInit.ENT.get(), 15, 1, 2))
                .addSpawn(MobCategory.CREATURE, new MobSpawnSettings.SpawnerData(EntityInit.RATLIN.get(), 15, 1, 4))
                .addSpawn(MobCategory.AMBIENT, new MobSpawnSettings.SpawnerData(EntityType.BAT, 1, 1, 4));
    }

    private static MobSpawnSettings.Builder permafrostForestSpawns() {
        return baseLandSpawnBuilder()
                .addSpawn(MobCategory.CREATURE, new MobSpawnSettings.SpawnerData(EntityInit.YETI.get(), 10, 3, 6))
                .addSpawn(MobCategory.CREATURE, new MobSpawnSettings.SpawnerData(EntityInit.AURORA_DEER.get(), 8, 3, 6));
    }

    private static MobSpawnSettings.Builder theAbyssSpawns() {
        return baseAquaticSpawnBuilder()
                .addSpawn(MobCategory.WATER_AMBIENT, new MobSpawnSettings.SpawnerData(EntityInit.LUMINOFISH.get(), 10, 3, 6))
                .addSpawn(MobCategory.WATER_AMBIENT, new MobSpawnSettings.SpawnerData(EntityInit.LUMINARIS.get(), 8, 3, 6));
    }

    private static MobSpawnSettings.Builder baseAquaticSpawnBuilder() {
        return new MobSpawnSettings.Builder()
                .creatureGenerationProbability(0.2f)
                .addSpawn(MobCategory.MONSTER, new MobSpawnSettings.SpawnerData(EntityInit.NIGHTSHADE_SPIDER.get(), 10, 1, 2))
                .addSpawn(MobCategory.MONSTER, new MobSpawnSettings.SpawnerData(EntityInit.LONESTAR_SKELETON.get(), 10, 1, 2))
                .addSpawn(MobCategory.AMBIENT, new MobSpawnSettings.SpawnerData(EntityType.BAT, 1, 1, 4));
    }

    public static BiomeGenerationSettings.Builder baseLandGenBuilder(HolderGetter<PlacedFeature> featureGetter, HolderGetter<ConfiguredWorldCarver<?>> carverGetter) {
        BiomeGenerationSettings.Builder builder = new BiomeGenerationSettings.Builder(featureGetter, carverGetter);

        BiomeDefaultFeatures.addForestGrass(builder);
        BiomeDefaultFeatures.addSavannaGrass(builder);
        BiomeDefaultFeatures.addDefaultGrass(builder);
        BiomeDefaultFeatures.addSavannaExtraGrass(builder);
        BiomeDefaultFeatures.addSurfaceFreezing(builder);
        builder.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, PlacedFeatureInit.NEAR_WATER_GRASS);
        builder.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, PlacedFeatureInit.CAVE_VINE);
        builder.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, PlacedFeatureInit.CAVE_MOSS);
        builder.addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, PlacedFeatureInit.STONE_ORE);
        builder.addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, PlacedFeatureInit.DEEPSLATE_ORE);
        builder.addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, PlacedFeatureInit.NIGHTSHADE_DIRT_ORE);
        builder.addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, PlacedFeatureInit.GLOWING_GRIMSTONE_ORE);
        builder.addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, PlacedFeatureInit.GLOWING_VOIDSTONE_ORE);
        builder.addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, PlacedFeatureInit.GRIMSTONE_REDSTONE_ORE);
        builder.addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, PlacedFeatureInit.VOIDSTONE_REDSTONE_ORE);
        builder.addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, PlacedFeatureInit.GRIMSTONE_SALTPETER_ORE);
        builder.addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, PlacedFeatureInit.VOIDSTONE_SALTPETER_ORE);
        builder.addFeature(GenerationStep.Decoration.LAKES, PlacedFeatureInit.HOT_SPRING);
        builder.addFeature(GenerationStep.Decoration.TOP_LAYER_MODIFICATION, PlacedFeatureInit.ETHER_FLUID_BORDER);

        builder.addCarver(GenerationStep.Carving.AIR, ConfiguredWorldCarverInit.CAVES);
        builder.addCarver(GenerationStep.Carving.AIR, ConfiguredWorldCarverInit.CAVES_EXTRA);

        return builder;
    }

    public static BiomeGenerationSettings.Builder baseAquaticGenBuilder(HolderGetter<PlacedFeature> featureGetter, HolderGetter<ConfiguredWorldCarver<?>> carverGetter) {
        BiomeGenerationSettings.Builder builder = new BiomeGenerationSettings.Builder(featureGetter, carverGetter);

        BiomeDefaultFeatures.addSurfaceFreezing(builder);
        builder.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, PlacedFeatureInit.CAVE_VINE);
        builder.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, PlacedFeatureInit.CAVE_MOSS);
        builder.addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, PlacedFeatureInit.STONE_ORE);
        builder.addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, PlacedFeatureInit.DEEPSLATE_ORE);
        builder.addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, PlacedFeatureInit.NIGHTSHADE_DIRT_ORE);
        builder.addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, PlacedFeatureInit.GLOWING_GRIMSTONE_ORE);
        builder.addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, PlacedFeatureInit.GLOWING_VOIDSTONE_ORE);
        builder.addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, PlacedFeatureInit.GRIMSTONE_REDSTONE_ORE);
        builder.addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, PlacedFeatureInit.VOIDSTONE_REDSTONE_ORE);
        builder.addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, PlacedFeatureInit.GRIMSTONE_SALTPETER_ORE);
        builder.addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, PlacedFeatureInit.VOIDSTONE_SALTPETER_ORE);
        builder.addFeature(GenerationStep.Decoration.TOP_LAYER_MODIFICATION, PlacedFeatureInit.ETHER_FLUID_BORDER);

        builder.addCarver(GenerationStep.Carving.AIR, ConfiguredWorldCarverInit.CAVES);
        builder.addCarver(GenerationStep.Carving.AIR, ConfiguredWorldCarverInit.CAVES_EXTRA);

        return builder;
    }

    private static BiomeGenerationSettings.Builder forestSettings(HolderGetter<PlacedFeature> featureGetter, HolderGetter<ConfiguredWorldCarver<?>> carverGetter) {
        BiomeGenerationSettings.Builder builder = baseLandGenBuilder(featureGetter, carverGetter);

        builder.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, PlacedFeatureInit.STARLIGHT_FOREST);
        builder.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, PlacedFeatureInit.FOREST_GRASS);
        builder.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, PlacedFeatureInit.FALLEN_LUNAR_LOG);
        builder.addFeature(GenerationStep.Decoration.LOCAL_MODIFICATIONS, PlacedFeatureInit.STONE_SPIKE);

        return builder;
    }

    private static BiomeGenerationSettings.Builder denseForestSettings(HolderGetter<PlacedFeature> featureGetter, HolderGetter<ConfiguredWorldCarver<?>> carverGetter) {
        BiomeGenerationSettings.Builder builder = baseLandGenBuilder(featureGetter, carverGetter);

        builder.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, PlacedFeatureInit.DENSE_FOREST);
        builder.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, PlacedFeatureInit.FOREST_GRASS);
        builder.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, PlacedFeatureInit.FALLEN_LUNAR_LOG);

        return builder;
    }

    private static BiomeGenerationSettings.Builder snowyForestSettings(HolderGetter<PlacedFeature> featureGetter, HolderGetter<ConfiguredWorldCarver<?>> carverGetter) {
        BiomeGenerationSettings.Builder builder = baseLandGenBuilder(featureGetter, carverGetter);

        builder.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, PlacedFeatureInit.PERMAFROST_FOREST);
        builder.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, PlacedFeatureInit.PERMAFROST_FOREST_GRASS);
        builder.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, PlacedFeatureInit.FALLEN_NORTHLAND_LOG);

        return builder;
    }

    private static BiomeGenerationSettings.Builder swampSettings(HolderGetter<PlacedFeature> featureGetter, HolderGetter<ConfiguredWorldCarver<?>> carverGetter) {
        BiomeGenerationSettings.Builder builder = baseLandGenBuilder(featureGetter, carverGetter);

        builder.addFeature(GenerationStep.Decoration.LAKES, PlacedFeatureInit.SWAMP_WATER);
        builder.addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, PlacedFeatureInit.SWAMP_SILVER_ORE);
        builder.addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, PlacedFeatureInit.GLOWING_NIGHTSHADE_MUD_ORE);
        builder.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, PlacedFeatureInit.SWAMP_FOREST);
        builder.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, PlacedFeatureInit.SWAMP_GRASS);
        builder.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, PlacedFeatureInit.FALLEN_STARLIGHT_MANGROVE_LOG);

        return builder;
    }

    private static BiomeGenerationSettings.Builder scarletForestSettings(HolderGetter<PlacedFeature> featureGetter, HolderGetter<ConfiguredWorldCarver<?>> carverGetter) {
        BiomeGenerationSettings.Builder builder = baseLandGenBuilder(featureGetter, carverGetter);

        builder.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, VegetationPlacements.PATCH_PUMPKIN);
        builder.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, PlacedFeatureInit.SCARLET_FOREST_GRASS);
        builder.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, PlacedFeatureInit.SCARLET_FOREST);
        builder.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, PlacedFeatureInit.FALLEN_SCARLET_LOG);
        builder.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, PlacedFeatureInit.SCARLET_LEAVES_PILE);

        return builder;
    }

    private static BiomeGenerationSettings.Builder torreyaForestSettings(HolderGetter<PlacedFeature> featureGetter, HolderGetter<ConfiguredWorldCarver<?>> carverGetter) {
        BiomeGenerationSettings.Builder builder = baseLandGenBuilder(featureGetter, carverGetter);

        builder.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, PlacedFeatureInit.TORREYA_FOREST_GRASS);
        builder.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, PlacedFeatureInit.TORREYA_FOREST);

        return builder;
    }

    private static BiomeGenerationSettings.Builder desertSettings(HolderGetter<PlacedFeature> featureGetter, HolderGetter<ConfiguredWorldCarver<?>> carverGetter) {
        BiomeGenerationSettings.Builder builder = baseLandGenBuilder(featureGetter, carverGetter);

        builder.addFeature(GenerationStep.Decoration.UNDERGROUND_DECORATION, PlacedFeatureInit.STARLIGHT_CRYSTAL);
        builder.addFeature(GenerationStep.Decoration.LOCAL_MODIFICATIONS, PlacedFeatureInit.STARLIGHT_CRYSTAL_SURFACE);
        builder.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, PlacedFeatureInit.DESERT_GRASS);
        builder.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, PlacedFeatureInit.DEAD_LUNAR_TREE);

        return builder;
    }

    private static BiomeGenerationSettings.Builder riverSettings(HolderGetter<PlacedFeature> featureGetter, HolderGetter<ConfiguredWorldCarver<?>> carverGetter) {
        BiomeGenerationSettings.Builder builder = baseAquaticGenBuilder(featureGetter, carverGetter);

        builder.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, PlacedFeatureInit.ABYSSAL_KELP);
        builder.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, PlacedFeatureInit.ON_WATER_PLANT);

        return builder;
    }

    private static BiomeGenerationSettings.Builder oceanSettings(HolderGetter<PlacedFeature> featureGetter, HolderGetter<ConfiguredWorldCarver<?>> carverGetter) {
        BiomeGenerationSettings.Builder builder = baseAquaticGenBuilder(featureGetter, carverGetter);

        builder.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, PlacedFeatureInit.ABYSSAL_KELP);
        builder.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, PlacedFeatureInit.OCEAN_VEGETATION);

        return builder;
    }

    private static BiomeGenerationSettings.Builder theAbyssSettings(HolderGetter<PlacedFeature> featureGetter, HolderGetter<ConfiguredWorldCarver<?>> carverGetter) {
        BiomeGenerationSettings.Builder builder = baseAquaticGenBuilder(featureGetter, carverGetter);

        builder.addFeature(GenerationStep.Decoration.LOCAL_MODIFICATIONS, PlacedFeatureInit.ABYSSLATE_PATCH);
        builder.addFeature(GenerationStep.Decoration.LOCAL_MODIFICATIONS, PlacedFeatureInit.THERMABYSSLATE_PATCH);
        builder.addFeature(GenerationStep.Decoration.LOCAL_MODIFICATIONS, PlacedFeatureInit.CRYOBYSSLATE_PATCH);
        builder.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, PlacedFeatureInit.ABYSSAL_KELP);

        return builder;
    }

    public static ResourceKey<Biome> create(String name) {
        return ResourceKey.create(Registries.BIOME, new ResourceLocation(EternalStarlight.MOD_ID, name));
    }
}

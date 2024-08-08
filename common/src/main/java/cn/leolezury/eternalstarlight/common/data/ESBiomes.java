package cn.leolezury.eternalstarlight.common.data;

import cn.leolezury.eternalstarlight.common.EternalStarlight;
import cn.leolezury.eternalstarlight.common.registry.ESEntities;
import cn.leolezury.eternalstarlight.common.registry.ESParticles;
import cn.leolezury.eternalstarlight.common.registry.ESSoundEvents;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BiomeDefaultFeatures;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.data.worldgen.placement.VegetationPlacements;
import net.minecraft.resources.ResourceKey;
import net.minecraft.sounds.Music;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.level.biome.*;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.minecraft.world.level.levelgen.carver.ConfiguredWorldCarver;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;

public class ESBiomes {
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

	public static final Music MUSIC_TRANQUILITY = new Music(ESSoundEvents.MUSIC_DIMENSION_TRANQUILITY.asHolder(), 1200, 12000, false);
	public static final Music MUSIC_FOREST = new Music(ESSoundEvents.MUSIC_BIOME_STARLIGHT_FOREST.asHolder(), 1200, 12000, false);
	public static final Music MUSIC_PERMAFROST_FOREST = new Music(ESSoundEvents.MUSIC_BIOME_STARLIGHT_PERMAFROST_FOREST.asHolder(), 1200, 12000, false);
	public static final Music MUSIC_SWAMP = new Music(ESSoundEvents.MUSIC_BIOME_DARK_SWAMP.asHolder(), 1200, 12000, false);
	public static final Music MUSIC_SCARLET_FOREST = new Music(ESSoundEvents.MUSIC_BIOME_SCARLET_FOREST.asHolder(), 1200, 12000, false);
	public static final Music MUSIC_TORREYA_FOREST = new Music(ESSoundEvents.MUSIC_BIOME_TORREYA_FOREST.asHolder(), 1200, 12000, false);
	public static final Music MUSIC_DESERT = new Music(ESSoundEvents.MUSIC_BIOME_CRYSTALLIZED_DESERT.asHolder(), 1200, 12000, false);
	public static final Music MUSIC_STARLIT_SEA = new Music(ESSoundEvents.MUSIC_BIOME_STARLIT_SEA.asHolder(), 1200, 12000, false);
	public static final Music MUSIC_ABYSS = new Music(ESSoundEvents.MUSIC_BIOME_THE_ABYSS.asHolder(), 1200, 12000, false);

	public static void bootstrap(BootstrapContext<Biome> context) {
		HolderGetter<PlacedFeature> featureHolderGetter = context.lookup(Registries.PLACED_FEATURE);
		HolderGetter<ConfiguredWorldCarver<?>> carverHolderGetter = context.lookup(Registries.CONFIGURED_CARVER);
		context.register(STARLIGHT_FOREST, baseBiomeBuilder(baseEffectsBuilder().backgroundMusic(MUSIC_FOREST), baseLandSpawnBuilder(), forestSettings(featureHolderGetter, carverHolderGetter)).build());
		context.register(STARLIGHT_DENSE_FOREST, baseBiomeBuilder(baseEffectsBuilder().backgroundMusic(MUSIC_FOREST), baseLandSpawnBuilder(), denseForestSettings(featureHolderGetter, carverHolderGetter)).build());
		context.register(STARLIGHT_PERMAFROST_FOREST, baseBiomeBuilder(baseEffectsBuilder().fogColor(14803455).skyColor(14803455).grassColorOverride(14803455).backgroundMusic(MUSIC_PERMAFROST_FOREST), permafrostForestSpawns(), permafrostForestSettings(featureHolderGetter, carverHolderGetter)).temperature(-0.3f).temperatureAdjustment(Biome.TemperatureModifier.FROZEN).build());
		context.register(DARK_SWAMP, baseBiomeBuilder(baseEffectsBuilder().fogColor(1310740).foliageColorOverride(7890120).skyColor(1310740).grassColorOverride(4075082).waterColor(7428526).waterFogColor(7428526).backgroundMusic(MUSIC_SWAMP), swampSpawns(), swampSettings(featureHolderGetter, carverHolderGetter)).build());
		context.register(SCARLET_FOREST, baseBiomeBuilder(baseEffectsBuilder().fogColor(10313569).foliageColorOverride(10313569).skyColor(10313569).grassColorOverride(10313569).backgroundMusic(MUSIC_SCARLET_FOREST), baseLandSpawnBuilder(), scarletForestSettings(featureHolderGetter, carverHolderGetter)).build());
		context.register(TORREYA_FOREST, baseBiomeBuilder(baseEffectsBuilder().fogColor(7229604).foliageColorOverride(7229604).skyColor(7229604).grassColorOverride(7229604).backgroundMusic(MUSIC_TORREYA_FOREST), baseLandSpawnBuilder(), torreyaForestSettings(featureHolderGetter, carverHolderGetter)).build());
		context.register(CRYSTALLIZED_DESERT, baseBiomeBuilder(baseEffectsBuilder().fogColor(8349826).foliageColorOverride(8349826).skyColor(8349826).grassColorOverride(8349826).backgroundMusic(MUSIC_DESERT), desertSpawns(), desertSettings(featureHolderGetter, carverHolderGetter)).hasPrecipitation(false).temperature(2.0f).build());
		context.register(SHIMMER_RIVER, baseBiomeBuilder(baseEffectsBuilder(), riverSpawns(), riverSettings(featureHolderGetter, carverHolderGetter)).build());
		context.register(ETHER_RIVER, baseBiomeBuilder(baseEffectsBuilder().fogColor(14417883).foliageColorOverride(14417883).skyColor(14417883).grassColorOverride(14417883).waterColor(14417883).waterFogColor(14417883), baseAquaticSpawnBuilder(), etherRiverSettings(featureHolderGetter, carverHolderGetter)).build());
		context.register(STARLIT_SEA, baseBiomeBuilder(baseEffectsBuilder().waterFogColor(4605040).backgroundMusic(MUSIC_STARLIT_SEA), baseAquaticSpawnBuilder(), oceanSettings(featureHolderGetter, carverHolderGetter)).build());
		context.register(THE_ABYSS, baseBiomeBuilder(baseEffectsBuilder(false).waterFogColor(3409191).backgroundMusic(MUSIC_ABYSS), theAbyssSpawns(), theAbyssSettings(featureHolderGetter, carverHolderGetter)).build());
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
		return baseEffectsBuilder(true);
	}

	private static BiomeSpecialEffects.Builder baseEffectsBuilder(boolean ambientParticle) {
		BiomeSpecialEffects.Builder builder = new BiomeSpecialEffects.Builder()
			.fogColor(5195923)
			.foliageColorOverride(5195923)
			.grassColorOverride(5195923)
			.waterColor(6187416)
			.waterFogColor(6187416)
			.skyColor(5658761)
			.backgroundMusic(MUSIC_TRANQUILITY);
		if (ambientParticle) {
			builder.ambientParticle(new AmbientParticleSettings(ESParticles.STARLIGHT.get(), 0.001f));
		}
		return builder;
	}

	private static MobSpawnSettings.Builder baseLandSpawnBuilder() {
		return new MobSpawnSettings.Builder()
			.creatureGenerationProbability(0.2f)
			.addSpawn(MobCategory.MONSTER, new MobSpawnSettings.SpawnerData(ESEntities.NIGHTFALL_SPIDER.get(), 20, 1, 2))
			.addSpawn(MobCategory.MONSTER, new MobSpawnSettings.SpawnerData(ESEntities.LONESTAR_SKELETON.get(), 20, 1, 2))
			.addSpawn(MobCategory.CREATURE, new MobSpawnSettings.SpawnerData(ESEntities.ENT.get(), 30, 1, 2))
			.addSpawn(MobCategory.CREATURE, new MobSpawnSettings.SpawnerData(ESEntities.RATLIN.get(), 24, 1, 3))
			.addSpawn(MobCategory.AMBIENT, new MobSpawnSettings.SpawnerData(EntityType.BAT, 2, 1, 2));
	}

	private static MobSpawnSettings.Builder baseAquaticSpawnBuilder() {
		return new MobSpawnSettings.Builder()
			.creatureGenerationProbability(0.2f)
			.addSpawn(MobCategory.MONSTER, new MobSpawnSettings.SpawnerData(ESEntities.NIGHTFALL_SPIDER.get(), 20, 1, 2))
			.addSpawn(MobCategory.MONSTER, new MobSpawnSettings.SpawnerData(ESEntities.LONESTAR_SKELETON.get(), 20, 1, 2))
			.addSpawn(MobCategory.AMBIENT, new MobSpawnSettings.SpawnerData(EntityType.BAT, 2, 1, 2));
	}

	private static MobSpawnSettings.Builder permafrostForestSpawns() {
		return baseLandSpawnBuilder()
			.addSpawn(MobCategory.CREATURE, new MobSpawnSettings.SpawnerData(ESEntities.YETI.get(), 20, 3, 6))
			.addSpawn(MobCategory.CREATURE, new MobSpawnSettings.SpawnerData(ESEntities.AURORA_DEER.get(), 16, 3, 6));
	}

	private static MobSpawnSettings.Builder swampSpawns() {
		return baseLandSpawnBuilder()
			.addSpawn(MobCategory.AMBIENT, new MobSpawnSettings.SpawnerData(ESEntities.SHIMMER_LACEWING.get(), 20, 1, 4));
	}

	private static MobSpawnSettings.Builder desertSpawns() {
		return baseLandSpawnBuilder()
			.addSpawn(MobCategory.AMBIENT, new MobSpawnSettings.SpawnerData(ESEntities.CRYSTALLIZED_MOTH.get(), 30, 1, 4))
			.addSpawn(MobCategory.MONSTER, new MobSpawnSettings.SpawnerData(ESEntities.GLEECH.get(), 20, 1, 3))
			.addSpawn(MobCategory.MONSTER, new MobSpawnSettings.SpawnerData(ESEntities.THIRST_WALKER.get(), 15, 1, 2));
	}

	private static MobSpawnSettings.Builder riverSpawns() {
		return baseAquaticSpawnBuilder()
			.addSpawn(MobCategory.AMBIENT, new MobSpawnSettings.SpawnerData(ESEntities.SHIMMER_LACEWING.get(), 20, 1, 4));
	}

	private static MobSpawnSettings.Builder theAbyssSpawns() {
		return baseAquaticSpawnBuilder()
			.addSpawn(MobCategory.WATER_AMBIENT, new MobSpawnSettings.SpawnerData(ESEntities.LUMINOFISH.get(), 20, 3, 6))
			.addSpawn(MobCategory.WATER_AMBIENT, new MobSpawnSettings.SpawnerData(ESEntities.LUMINARIS.get(), 16, 3, 6))
			.addSpawn(MobCategory.WATER_CREATURE, new MobSpawnSettings.SpawnerData(ESEntities.TWILIGHT_GAZE.get(), 10, 3, 6));
	}

	public static BiomeGenerationSettings.Builder baseLandGenBuilder(HolderGetter<PlacedFeature> featureGetter, HolderGetter<ConfiguredWorldCarver<?>> carverGetter) {
		return baseLandGenBuilder(featureGetter, carverGetter, true);
	}

	public static BiomeGenerationSettings.Builder baseLandGenBuilder(HolderGetter<PlacedFeature> featureGetter, HolderGetter<ConfiguredWorldCarver<?>> carverGetter, boolean grass) {
		BiomeGenerationSettings.Builder builder = new BiomeGenerationSettings.Builder(featureGetter, carverGetter);

		if (grass) {
			BiomeDefaultFeatures.addSavannaGrass(builder);
			BiomeDefaultFeatures.addSavannaExtraGrass(builder);
		}
		BiomeDefaultFeatures.addSurfaceFreezing(builder);
		builder.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, ESPlacedFeatures.WATERSIDE_VEGETATION);
		builder.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, ESPlacedFeatures.CAVE_VINE);
		builder.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, ESPlacedFeatures.CAVE_MOSS);
		builder.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, ESPlacedFeatures.CAVE_MOSS_VEIN);
		builder.addFeature(GenerationStep.Decoration.UNDERGROUND_DECORATION, ESPlacedFeatures.STELLAGMITE);
		builder.addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, ESPlacedFeatures.STONE_ORE);
		builder.addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, ESPlacedFeatures.DEEPSLATE_ORE);
		builder.addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, ESPlacedFeatures.NIGHTFALL_DIRT_ORE);
		builder.addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, ESPlacedFeatures.GLOWING_GRIMSTONE_ORE);
		builder.addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, ESPlacedFeatures.GLOWING_VOIDSTONE_ORE);
		builder.addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, ESPlacedFeatures.GRIMSTONE_REDSTONE_ORE);
		builder.addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, ESPlacedFeatures.VOIDSTONE_REDSTONE_ORE);
		builder.addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, ESPlacedFeatures.GRIMSTONE_SALTPETER_ORE);
		builder.addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, ESPlacedFeatures.VOIDSTONE_SALTPETER_ORE);
		builder.addFeature(GenerationStep.Decoration.LAKES, ESPlacedFeatures.HOT_SPRING);
		builder.addFeature(GenerationStep.Decoration.TOP_LAYER_MODIFICATION, ESPlacedFeatures.FINAL_MODIFICATION);

		builder.addCarver(GenerationStep.Carving.AIR, ESConfiguredWorldCarvers.CAVES);
		builder.addCarver(GenerationStep.Carving.AIR, ESConfiguredWorldCarvers.CAVES_EXTRA);

		return builder;
	}

	public static BiomeGenerationSettings.Builder baseAquaticGenBuilder(HolderGetter<PlacedFeature> featureGetter, HolderGetter<ConfiguredWorldCarver<?>> carverGetter) {
		BiomeGenerationSettings.Builder builder = new BiomeGenerationSettings.Builder(featureGetter, carverGetter);

		BiomeDefaultFeatures.addSurfaceFreezing(builder);
		builder.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, ESPlacedFeatures.CAVE_VINE);
		builder.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, ESPlacedFeatures.CAVE_MOSS);
		builder.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, ESPlacedFeatures.CAVE_MOSS_VEIN);
		builder.addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, ESPlacedFeatures.STONE_ORE);
		builder.addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, ESPlacedFeatures.DEEPSLATE_ORE);
		builder.addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, ESPlacedFeatures.NIGHTFALL_DIRT_ORE);
		builder.addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, ESPlacedFeatures.SAND_ORE);
		builder.addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, ESPlacedFeatures.GLOWING_GRIMSTONE_ORE);
		builder.addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, ESPlacedFeatures.GLOWING_VOIDSTONE_ORE);
		builder.addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, ESPlacedFeatures.GRIMSTONE_REDSTONE_ORE);
		builder.addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, ESPlacedFeatures.VOIDSTONE_REDSTONE_ORE);
		builder.addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, ESPlacedFeatures.GRIMSTONE_SALTPETER_ORE);
		builder.addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, ESPlacedFeatures.VOIDSTONE_SALTPETER_ORE);
		builder.addFeature(GenerationStep.Decoration.TOP_LAYER_MODIFICATION, ESPlacedFeatures.FINAL_MODIFICATION);

		builder.addCarver(GenerationStep.Carving.AIR, ESConfiguredWorldCarvers.CAVES);
		builder.addCarver(GenerationStep.Carving.AIR, ESConfiguredWorldCarvers.CAVES_EXTRA);

		return builder;
	}

	private static BiomeGenerationSettings.Builder forestSettings(HolderGetter<PlacedFeature> featureGetter, HolderGetter<ConfiguredWorldCarver<?>> carverGetter) {
		BiomeGenerationSettings.Builder builder = baseLandGenBuilder(featureGetter, carverGetter);

		builder.addFeature(GenerationStep.Decoration.RAW_GENERATION, ESPlacedFeatures.FALLEN_LUNAR_LOG);
		builder.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, ESPlacedFeatures.STARLIGHT_FOREST);
		builder.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, ESPlacedFeatures.FOREST_VEGETATION);
		builder.addFeature(GenerationStep.Decoration.LOCAL_MODIFICATIONS, ESPlacedFeatures.STONE_SPIKE);

		return builder;
	}

	private static BiomeGenerationSettings.Builder denseForestSettings(HolderGetter<PlacedFeature> featureGetter, HolderGetter<ConfiguredWorldCarver<?>> carverGetter) {
		BiomeGenerationSettings.Builder builder = baseLandGenBuilder(featureGetter, carverGetter);

		builder.addFeature(GenerationStep.Decoration.RAW_GENERATION, ESPlacedFeatures.FALLEN_LUNAR_LOG);
		builder.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, ESPlacedFeatures.DENSE_FOREST);
		builder.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, ESPlacedFeatures.FOREST_VEGETATION);

		return builder;
	}

	private static BiomeGenerationSettings.Builder permafrostForestSettings(HolderGetter<PlacedFeature> featureGetter, HolderGetter<ConfiguredWorldCarver<?>> carverGetter) {
		BiomeGenerationSettings.Builder builder = baseLandGenBuilder(featureGetter, carverGetter);

		builder.addFeature(GenerationStep.Decoration.RAW_GENERATION, ESPlacedFeatures.FALLEN_NORTHLAND_LOG);
		builder.addFeature(GenerationStep.Decoration.UNDERGROUND_DECORATION, ESPlacedFeatures.GLACITE);
		builder.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, ESPlacedFeatures.PERMAFROST_FOREST);
		builder.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, ESPlacedFeatures.PERMAFROST_FOREST_VEGETATION);

		return builder;
	}

	private static BiomeGenerationSettings.Builder swampSettings(HolderGetter<PlacedFeature> featureGetter, HolderGetter<ConfiguredWorldCarver<?>> carverGetter) {
		BiomeGenerationSettings.Builder builder = baseLandGenBuilder(featureGetter, carverGetter);

		builder.addFeature(GenerationStep.Decoration.RAW_GENERATION, ESPlacedFeatures.FALLEN_STARLIGHT_MANGROVE_LOG);
		builder.addFeature(GenerationStep.Decoration.LAKES, ESPlacedFeatures.SWAMP_WATER);
		builder.addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, ESPlacedFeatures.SWAMP_SILVER_ORE);
		builder.addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, ESPlacedFeatures.GLOWING_NIGHTFALL_MUD_ORE);
		builder.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, ESPlacedFeatures.SWAMP_FOREST);
		builder.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, ESPlacedFeatures.SWAMP_VEGETATION);

		return builder;
	}

	private static BiomeGenerationSettings.Builder scarletForestSettings(HolderGetter<PlacedFeature> featureGetter, HolderGetter<ConfiguredWorldCarver<?>> carverGetter) {
		BiomeGenerationSettings.Builder builder = baseLandGenBuilder(featureGetter, carverGetter);

		builder.addFeature(GenerationStep.Decoration.RAW_GENERATION, ESPlacedFeatures.FALLEN_SCARLET_LOG);
		builder.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, VegetationPlacements.PATCH_PUMPKIN);
		builder.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, ESPlacedFeatures.SCARLET_FOREST_VEGETATION);
		builder.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, ESPlacedFeatures.SCARLET_FOREST);
		builder.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, ESPlacedFeatures.SCARLET_LEAVES_PILE);

		return builder;
	}

	private static BiomeGenerationSettings.Builder torreyaForestSettings(HolderGetter<PlacedFeature> featureGetter, HolderGetter<ConfiguredWorldCarver<?>> carverGetter) {
		BiomeGenerationSettings.Builder builder = baseLandGenBuilder(featureGetter, carverGetter);

		builder.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, ESPlacedFeatures.TORREYA_FOREST_VEGETATION);
		builder.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, ESPlacedFeatures.TORREYA_FOREST);

		return builder;
	}

	private static BiomeGenerationSettings.Builder desertSettings(HolderGetter<PlacedFeature> featureGetter, HolderGetter<ConfiguredWorldCarver<?>> carverGetter) {
		BiomeGenerationSettings.Builder builder = baseLandGenBuilder(featureGetter, carverGetter, false);

		builder.addFeature(GenerationStep.Decoration.UNDERGROUND_DECORATION, ESPlacedFeatures.STARLIGHT_CRYSTAL);
		builder.addFeature(GenerationStep.Decoration.LOCAL_MODIFICATIONS, ESPlacedFeatures.STARLIGHT_CRYSTAL_SURFACE);
		builder.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, ESPlacedFeatures.DESERT_VEGETATION);
		builder.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, ESPlacedFeatures.DEAD_LUNAR_TREE);
		builder.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, ESPlacedFeatures.LUNARIS_CACTUS);
		builder.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, ESPlacedFeatures.CRYSTAL_CAVES_VEGETATION);

		return builder;
	}

	private static BiomeGenerationSettings.Builder riverSettings(HolderGetter<PlacedFeature> featureGetter, HolderGetter<ConfiguredWorldCarver<?>> carverGetter) {
		BiomeGenerationSettings.Builder builder = baseAquaticGenBuilder(featureGetter, carverGetter);

		builder.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, ESPlacedFeatures.ABYSSAL_KELP);
		builder.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, ESPlacedFeatures.WATER_SURFACE_VEGETATION);

		return builder;
	}

	private static BiomeGenerationSettings.Builder etherRiverSettings(HolderGetter<PlacedFeature> featureGetter, HolderGetter<ConfiguredWorldCarver<?>> carverGetter) {
		BiomeGenerationSettings.Builder builder = baseAquaticGenBuilder(featureGetter, carverGetter);

		builder.addFeature(GenerationStep.Decoration.RAW_GENERATION, ESPlacedFeatures.THIOQUARTZ_GEODE);

		return builder;
	}

	private static BiomeGenerationSettings.Builder oceanSettings(HolderGetter<PlacedFeature> featureGetter, HolderGetter<ConfiguredWorldCarver<?>> carverGetter) {
		BiomeGenerationSettings.Builder builder = baseAquaticGenBuilder(featureGetter, carverGetter);

		builder.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, ESPlacedFeatures.ABYSSAL_KELP);
		builder.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, ESPlacedFeatures.ORBFLORA);
		builder.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, ESPlacedFeatures.OCEAN_VEGETATION);

		return builder;
	}

	private static BiomeGenerationSettings.Builder theAbyssSettings(HolderGetter<PlacedFeature> featureGetter, HolderGetter<ConfiguredWorldCarver<?>> carverGetter) {
		BiomeGenerationSettings.Builder builder = baseAquaticGenBuilder(featureGetter, carverGetter);

		builder.addFeature(GenerationStep.Decoration.LOCAL_MODIFICATIONS, ESPlacedFeatures.ABYSSLATE_PATCH);
		builder.addFeature(GenerationStep.Decoration.LOCAL_MODIFICATIONS, ESPlacedFeatures.THERMABYSSLATE_PATCH);
		builder.addFeature(GenerationStep.Decoration.LOCAL_MODIFICATIONS, ESPlacedFeatures.CRYOBYSSLATE_PATCH);
		builder.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, ESPlacedFeatures.ABYSSAL_KELP);
		builder.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, ESPlacedFeatures.ORBFLORA);
		builder.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, ESPlacedFeatures.VELVETUMOSS);
		builder.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, ESPlacedFeatures.RED_VELVETUMOSS);

		return builder;
	}

	public static ResourceKey<Biome> create(String name) {
		return ResourceKey.create(Registries.BIOME, EternalStarlight.id(name));
	}
}

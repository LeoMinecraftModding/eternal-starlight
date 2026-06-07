package cn.leolezury.eternalstarlight.common.data;

import cn.leolezury.eternalstarlight.common.EternalStarlight;
import cn.leolezury.eternalstarlight.common.registry.ESBlocks;
import cn.leolezury.eternalstarlight.common.registry.ESEntities;
import cn.leolezury.eternalstarlight.common.registry.ESParticles;
import cn.leolezury.eternalstarlight.common.registry.ESSoundEvents;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BiomeDefaultFeatures;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.data.worldgen.placement.VegetationPlacements;
import net.minecraft.resources.ResourceKey;
import net.minecraft.sounds.Music;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.biome.*;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.minecraft.world.level.levelgen.carver.ConfiguredWorldCarver;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;

public class ESBiomes {
	public static final ResourceKey<Biome> STARLIGHT_FOREST = create("starlight_forest");
	public static final ResourceKey<Biome> STARLIGHT_DENSE_FOREST = create("starlight_dense_forest");
	public static final ResourceKey<Biome> UMBRAL_PLAINS = create("umbral_plains");
	public static final ResourceKey<Biome> GLIMMER_SCRUBLAND = create("glimmer_scrubland");
	public static final ResourceKey<Biome> STARLIGHT_PERMAFROST_FOREST = create("starlight_permafrost_forest");
	public static final ResourceKey<Biome> PERMAFROST_PEAKS = create("permafrost_peaks");
	public static final ResourceKey<Biome> STARLIGHT_TAIGA = create("starlight_taiga");
	public static final ResourceKey<Biome> DARK_SWAMP = create("dark_swamp");
	public static final ResourceKey<Biome> SCARLET_FOREST = create("scarlet_forest");
	public static final ResourceKey<Biome> TORREYA_FOREST = create("torreya_forest");
	public static final ResourceKey<Biome> CRYSTALLIZED_DESERT = create("crystallized_desert");
	public static final ResourceKey<Biome> LUCENT_MYCELIUM_ISLE = create("lucent_mycelium_isle");
	public static final ResourceKey<Biome> SOLARIS_ISLES = create("solaris_isles");
	public static final ResourceKey<Biome> SHIMMER_RIVER = create("shimmer_river");
	public static final ResourceKey<Biome> ETHER_RIVER = create("ether_river");
	public static final ResourceKey<Biome> STARLIT_SEA = create("starlit_sea");
	public static final ResourceKey<Biome> ICY_SEA = create("icy_sea");
	public static final ResourceKey<Biome> SPIRAL_KELP_FOREST = create("spiral_kelp_forest");
	public static final ResourceKey<Biome> LUSH_SHALLOW_SEA = create("lush_shallow_sea");
	public static final ResourceKey<Biome> THE_ABYSS = create("the_abyss");
	public static final ResourceKey<Biome> WARM_SHORE = create("warm_shore");
	public static final ResourceKey<Biome> GRIM_SHORE = create("grim_shore");

	public static final Music MUSIC_TRANQUILITY = new Music(ESSoundEvents.MUSIC_DIMENSION.asHolder(), 1200, 12000, false);
	public static final Music MUSIC_FOREST = new Music(ESSoundEvents.MUSIC_BIOME_STARLIGHT_FOREST.asHolder(), 1200, 12000, false);
	public static final Music MUSIC_PERMAFROST_FOREST = new Music(ESSoundEvents.MUSIC_BIOME_STARLIGHT_PERMAFROST_FOREST.asHolder(), 1200, 12000, false);
	public static final Music MUSIC_SWAMP = new Music(ESSoundEvents.MUSIC_BIOME_DARK_SWAMP.asHolder(), 1200, 12000, false);
	public static final Music MUSIC_SCARLET_FOREST = new Music(ESSoundEvents.MUSIC_BIOME_SCARLET_FOREST.asHolder(), 1200, 12000, false);
	public static final Music MUSIC_TORREYA_FOREST = new Music(ESSoundEvents.MUSIC_BIOME_TORREYA_FOREST.asHolder(), 1200, 12000, false);
	public static final Music MUSIC_DESERT = new Music(ESSoundEvents.MUSIC_BIOME_CRYSTALLIZED_DESERT.asHolder(), 1200, 12000, false);
	public static final Music MUSIC_STARLIT_SEA = new Music(ESSoundEvents.MUSIC_BIOME_STARLIT_SEA.asHolder(), 1200, 12000, false);
	public static final Music MUSIC_THE_ABYSS = new Music(ESSoundEvents.MUSIC_BIOME_THE_ABYSS.asHolder(), 1200, 12000, false);

	public static void bootstrap(BootstrapContext<Biome> context) {
		HolderGetter<PlacedFeature> featureHolderGetter = context.lookup(Registries.PLACED_FEATURE);
		HolderGetter<ConfiguredWorldCarver<?>> carverHolderGetter = context.lookup(Registries.CONFIGURED_CARVER);
		context.register(STARLIGHT_FOREST, baseBiomeBuilder(baseEffectsBuilder().backgroundMusic(MUSIC_FOREST), baseLushSpawnBuilder(), forestGenSettings(featureHolderGetter, carverHolderGetter)).build());
		context.register(STARLIGHT_DENSE_FOREST, baseBiomeBuilder(baseEffectsBuilder().backgroundMusic(MUSIC_FOREST), baseLushSpawnBuilder(), denseForestGenSettings(featureHolderGetter, carverHolderGetter)).build());
		context.register(UMBRAL_PLAINS, baseBiomeBuilder(baseEffectsBuilder().backgroundMusic(MUSIC_FOREST), baseLushSpawnBuilder(), plainsGenSettings(featureHolderGetter, carverHolderGetter)).build());
		context.register(GLIMMER_SCRUBLAND, baseBiomeBuilder(baseEffectsBuilder().backgroundMusic(MUSIC_FOREST), baseLushSpawnBuilder(), scrublandGenSettings(featureHolderGetter, carverHolderGetter)).build());
		context.register(STARLIGHT_PERMAFROST_FOREST, baseBiomeBuilder(baseEffectsBuilder().fogColor(14803455).skyColor(14803455).grassColorOverride(14803455).backgroundMusic(MUSIC_PERMAFROST_FOREST), permafrostForestSpawns(), permafrostForestGenSettings(featureHolderGetter, carverHolderGetter)).temperature(-0.3f).temperatureAdjustment(Biome.TemperatureModifier.FROZEN).build());
		context.register(PERMAFROST_PEAKS, baseBiomeBuilder(baseEffectsBuilder().fogColor(14803455).skyColor(14803455).grassColorOverride(14803455).backgroundMusic(MUSIC_PERMAFROST_FOREST), permafrostForestSpawns(), permafrostForestGenSettings(featureHolderGetter, carverHolderGetter)).temperature(-0.3f).temperatureAdjustment(Biome.TemperatureModifier.FROZEN).build());
		context.register(STARLIGHT_TAIGA, baseBiomeBuilder(baseEffectsBuilder().grassColorOverride(14803455).backgroundMusic(MUSIC_FOREST), taigaSpawns(), taigaGenSettings(featureHolderGetter, carverHolderGetter)).temperature(0.25f).build());
		context.register(DARK_SWAMP, baseBiomeBuilder(baseEffectsBuilder().fogColor(5250640).foliageColorOverride(7890120).skyColor(5250640).grassColorOverride(4075082).waterColor(7428526).waterFogColor(7428526).backgroundMusic(MUSIC_SWAMP), swampSpawns(), swampGenSettings(featureHolderGetter, carverHolderGetter)).build());
		context.register(SCARLET_FOREST, baseBiomeBuilder(baseEffectsBuilder().fogColor(10313569).foliageColorOverride(10313569).skyColor(10313569).grassColorOverride(10313569).backgroundMusic(MUSIC_SCARLET_FOREST), baseLushSpawnBuilder(), scarletForestGenSettings(featureHolderGetter, carverHolderGetter)).build());
		context.register(TORREYA_FOREST, baseBiomeBuilder(baseEffectsBuilder().fogColor(7229604).foliageColorOverride(7229604).skyColor(7229604).grassColorOverride(7229604).backgroundMusic(MUSIC_TORREYA_FOREST), baseLushSpawnBuilder(), torreyaForestGenSettings(featureHolderGetter, carverHolderGetter)).build());
		context.register(CRYSTALLIZED_DESERT, baseBiomeBuilder(baseEffectsBuilder().fogColor(8349826).foliageColorOverride(8349826).skyColor(8349826).grassColorOverride(8349826).backgroundMusic(MUSIC_DESERT), desertSpawns(), desertGenSettings(featureHolderGetter, carverHolderGetter)).hasPrecipitation(false).temperature(2.0f).build());
		context.register(LUCENT_MYCELIUM_ISLE, baseBiomeBuilder(baseEffectsBuilder().grassColorOverride(5260652).backgroundMusic(MUSIC_FOREST), mushroomSpawns(), mushroomGenSettings(featureHolderGetter, carverHolderGetter)).build());
		context.register(SOLARIS_ISLES, baseBiomeBuilder(baseEffectsBuilder().backgroundMusic(MUSIC_FOREST), solarisSpawns(), solarisGenSettings(featureHolderGetter, carverHolderGetter)).hasPrecipitation(false).temperature(2.0f).build());
		context.register(SHIMMER_RIVER, baseBiomeBuilder(baseEffectsBuilder(), riverSpawns(), riverGenSettings(featureHolderGetter, carverHolderGetter)).build());
		context.register(ETHER_RIVER, baseBiomeBuilder(baseEffectsBuilder().fogColor(14417883).foliageColorOverride(14417883).skyColor(14417883).grassColorOverride(14417883).waterColor(14417883).waterFogColor(14417883), baseSpawnBuilder(), etherRiverGenSettings(featureHolderGetter, carverHolderGetter)).build());
		context.register(STARLIT_SEA, baseBiomeBuilder(baseEffectsBuilder().backgroundMusic(MUSIC_STARLIT_SEA), baseAquaticSpawnBuilder(), oceanGenSettings(featureHolderGetter, carverHolderGetter)).build());
		context.register(ICY_SEA, baseBiomeBuilder(baseEffectsBuilder().backgroundMusic(MUSIC_STARLIT_SEA), baseAquaticSpawnBuilder(), icySeaGenSettings(featureHolderGetter, carverHolderGetter)).temperature(-0.3f).temperatureAdjustment(Biome.TemperatureModifier.FROZEN).build());
		context.register(SPIRAL_KELP_FOREST, baseBiomeBuilder(baseEffectsBuilder().backgroundMusic(MUSIC_STARLIT_SEA).waterColor(7883610).waterFogColor(1576722), baseAquaticSpawnBuilder(), spiralKelpForestGenSettings(featureHolderGetter, carverHolderGetter)).build());
		context.register(LUSH_SHALLOW_SEA, baseBiomeBuilder(baseEffectsBuilder().backgroundMusic(MUSIC_STARLIT_SEA).waterColor(6124624).waterFogColor(4737072), baseAquaticSpawnBuilder(), lushShallowSeaGenSettings(featureHolderGetter, carverHolderGetter)).build());
		context.register(THE_ABYSS, baseBiomeBuilder(baseEffectsBuilder(false).waterFogColor(3409191).backgroundMusic(MUSIC_THE_ABYSS), theAbyssSpawns(), theAbyssGenSettings(featureHolderGetter, carverHolderGetter)).build());
		context.register(WARM_SHORE, baseBiomeBuilder(baseEffectsBuilder(), baseLushSpawnBuilder(), warmShoreGenSettings(featureHolderGetter, carverHolderGetter)).build());
		context.register(GRIM_SHORE, baseBiomeBuilder(baseEffectsBuilder(), baseLushSpawnBuilder(), grimShoreGenSettings(featureHolderGetter, carverHolderGetter)).build());
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
			.waterFogColor(1184291)
			.skyColor(5658761)
			.backgroundMusic(MUSIC_TRANQUILITY);
		if (ambientParticle) {
			builder.ambientParticle(new AmbientParticleSettings(ESParticles.STARLIGHT.get(), 0.001f));
		}
		return builder;
	}

	private static MobSpawnSettings.Builder baseSpawnBuilder() {
		return baseSpawnBuilder(true);
	}

	private static MobSpawnSettings.Builder baseSpawnBuilder(boolean hostile) {
		MobSpawnSettings.Builder builder = new MobSpawnSettings.Builder()
			.creatureGenerationProbability(0.2f);
		if (hostile) {
			builder.addSpawn(MobCategory.MONSTER, new MobSpawnSettings.SpawnerData(ESEntities.NIGHTFALL_SPIDER.get(), 15, 1, 2))
				.addSpawn(MobCategory.MONSTER, new MobSpawnSettings.SpawnerData(ESEntities.LONESTAR_SKELETON.get(), 10, 1, 2))
				.addSpawn(MobCategory.MONSTER, new MobSpawnSettings.SpawnerData(ESEntities.SEEKER.get(), 15, 1, 2))
				.addMobCharge(ESEntities.NIGHTFALL_SPIDER.get(), 1, 0.5)
				.addMobCharge(ESEntities.LONESTAR_SKELETON.get(), 1, 0.5)
				.addMobCharge(ESEntities.SEEKER.get(), 1, 0.5);
		}
		return builder.addSpawn(MobCategory.AMBIENT, new MobSpawnSettings.SpawnerData(EntityType.BAT, 2, 1, 2));
	}

	private static MobSpawnSettings.Builder baseLushSpawnBuilder() {
		return baseSpawnBuilder()
			.addSpawn(MobCategory.CREATURE, new MobSpawnSettings.SpawnerData(ESEntities.ENT.get(), 30, 1, 2))
			.addSpawn(MobCategory.CREATURE, new MobSpawnSettings.SpawnerData(ESEntities.RATLIN.get(), 24, 1, 3))
			.addSpawn(MobCategory.CREATURE, new MobSpawnSettings.SpawnerData(ESEntities.SHADOW_SNAIL.get(), 18, 1, 2));
	}

	private static MobSpawnSettings.Builder baseAquaticSpawnBuilder() {
		return baseSpawnBuilder()
			.addSpawn(MobCategory.WATER_CREATURE, new MobSpawnSettings.SpawnerData(ESEntities.ROOKFISH.get(), 16, 2, 4));
	}

	private static MobSpawnSettings.Builder permafrostForestSpawns() {
		return baseLushSpawnBuilder()
			.addSpawn(MobCategory.CREATURE, new MobSpawnSettings.SpawnerData(ESEntities.YETI.get(), 20, 3, 6))
			.addSpawn(MobCategory.CREATURE, new MobSpawnSettings.SpawnerData(ESEntities.AURORA_DEER.get(), 16, 3, 6));
	}

	private static MobSpawnSettings.Builder taigaSpawns() {
		return baseLushSpawnBuilder()
			.addSpawn(MobCategory.CREATURE, new MobSpawnSettings.SpawnerData(ESEntities.AURORA_DEER.get(), 16, 3, 6));
	}

	private static MobSpawnSettings.Builder swampSpawns() {
		return baseLushSpawnBuilder()
			.addSpawn(MobCategory.CREATURE, new MobSpawnSettings.SpawnerData(ESEntities.ZOMBIFIED_RATLIN.get(), 24, 1, 3))
			.addSpawn(MobCategory.AMBIENT, new MobSpawnSettings.SpawnerData(ESEntities.SHIMMER_LACEWING.get(), 10, 1, 2));
	}

	private static MobSpawnSettings.Builder desertSpawns() {
		return baseSpawnBuilder()
			.addSpawn(MobCategory.MONSTER, new MobSpawnSettings.SpawnerData(ESEntities.CRYSTALLIZED_MOTH.get(), 8, 1, 4))
			.addSpawn(MobCategory.MONSTER, new MobSpawnSettings.SpawnerData(ESEntities.GLEECH.get(), 10, 1, 3))
			.addSpawn(MobCategory.MONSTER, new MobSpawnSettings.SpawnerData(ESEntities.THIRST_WALKER.get(), 10, 1, 2))
			.addMobCharge(ESEntities.CRYSTALLIZED_MOTH.get(), 1, 0.75)
			.addMobCharge(ESEntities.GLEECH.get(), 1, 0.75)
			.addMobCharge(ESEntities.THIRST_WALKER.get(), 1, 0.75);
	}

	private static MobSpawnSettings.Builder mushroomSpawns() {
		return baseSpawnBuilder(false)
			.addSpawn(MobCategory.CREATURE, new MobSpawnSettings.SpawnerData(ESEntities.ENT.get(), 30, 1, 2))
			.addSpawn(MobCategory.CREATURE, new MobSpawnSettings.SpawnerData(ESEntities.RATLIN.get(), 24, 1, 3))
			.addSpawn(MobCategory.CREATURE, new MobSpawnSettings.SpawnerData(ESEntities.SHADOW_SNAIL.get(), 18, 1, 2));
	}

	private static MobSpawnSettings.Builder solarisSpawns() {
		return baseSpawnBuilder(false)
			.addSpawn(MobCategory.CREATURE, new MobSpawnSettings.SpawnerData(ESEntities.ENT.get(), 10, 1, 2))
			.addSpawn(MobCategory.CREATURE, new MobSpawnSettings.SpawnerData(ESEntities.RATLIN.get(), 8, 1, 3))
			.addSpawn(MobCategory.CREATURE, new MobSpawnSettings.SpawnerData(ESEntities.SHADOW_SNAIL.get(), 6, 1, 2));
	}

	private static MobSpawnSettings.Builder riverSpawns() {
		return baseAquaticSpawnBuilder()
			.addSpawn(MobCategory.AMBIENT, new MobSpawnSettings.SpawnerData(ESEntities.SHIMMER_LACEWING.get(), 20, 1, 2));
	}

	private static MobSpawnSettings.Builder theAbyssSpawns() {
		return baseAquaticSpawnBuilder()
			.addSpawn(MobCategory.WATER_AMBIENT, new MobSpawnSettings.SpawnerData(ESEntities.LUMINOFISH.get(), 20, 3, 6))
			.addSpawn(MobCategory.WATER_AMBIENT, new MobSpawnSettings.SpawnerData(ESEntities.LUMINARIS.get(), 16, 3, 6))
			.addSpawn(MobCategory.WATER_CREATURE, new MobSpawnSettings.SpawnerData(ESEntities.TWILIGHT_GAZE.get(), 10, 3, 6));
	}

	public static BiomeGenerationSettings.Builder baseLandGenSettings(HolderGetter<PlacedFeature> featureGetter, HolderGetter<ConfiguredWorldCarver<?>> carverGetter) {
		return baseLandGenSettings(featureGetter, carverGetter, true);
	}

	public static BiomeGenerationSettings.Builder baseLandGenSettings(HolderGetter<PlacedFeature> featureGetter, HolderGetter<ConfiguredWorldCarver<?>> carverGetter, boolean grass) {
		BiomeGenerationSettings.Builder builder = new BiomeGenerationSettings.Builder(featureGetter, carverGetter);

		if (grass) {
			BiomeDefaultFeatures.addSavannaGrass(builder);
			BiomeDefaultFeatures.addSavannaExtraGrass(builder);
		}
		BiomeDefaultFeatures.addSurfaceFreezing(builder);
		builder.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, ESPlacedFeatures.WATERSIDE_VEGETATION);
		builder.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, ESPlacedFeatures.CAVE_MOSS);
		builder.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, ESPlacedFeatures.CAVE_MOSS_VEIN);
		if (grass) {
			builder.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, ESPlacedFeatures.CAVE_MOSS_PATCH);
		}
		builder.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, ESPlacedFeatures.BOULDERSHROOM);
		builder.addFeature(GenerationStep.Decoration.UNDERGROUND_DECORATION, ESPlacedFeatures.STELLAGMITE);
		builder.addFeature(GenerationStep.Decoration.UNDERGROUND_DECORATION, ESPlacedFeatures.VOIDSTONE_SPIKE);
		builder.addFeature(GenerationStep.Decoration.UNDERGROUND_DECORATION, ESPlacedFeatures.HANGING_VOIDSTONE_SPIKE);
		builder.addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, ESPlacedFeatures.STONE_ORE);
		builder.addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, ESPlacedFeatures.DEEPSLATE_ORE);
		builder.addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, ESPlacedFeatures.NIGHTFALL_DIRT_ORE);
		builder.addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, ESPlacedFeatures.DIMSLAG_ORE);
		builder.addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, ESPlacedFeatures.GLOWING_GRIMSTONE_ORE);
		builder.addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, ESPlacedFeatures.GLOWING_VOIDSTONE_ORE);
		builder.addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, ESPlacedFeatures.UPPER_STARLIT_DIAMOND_ORE);
		builder.addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, ESPlacedFeatures.LOWER_STARLIT_DIAMOND_ORE);
		builder.addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, ESPlacedFeatures.UPPER_DEEPSILVER_ORE);
		builder.addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, ESPlacedFeatures.LOWER_DEEPSILVER_ORE);
		builder.addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, ESPlacedFeatures.UPPER_REDSTONE_ORE);
		builder.addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, ESPlacedFeatures.LOWER_REDSTONE_ORE);
		builder.addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, ESPlacedFeatures.UPPER_SALTPETER_ORE);
		builder.addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, ESPlacedFeatures.LOWER_SALTPETER_ORE);
		builder.addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, ESPlacedFeatures.UPPER_STARCORE_ORE);
		builder.addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, ESPlacedFeatures.LOWER_STARCORE_ORE);
		builder.addFeature(GenerationStep.Decoration.LAKES, ESPlacedFeatures.HOT_SPRING);
		builder.addFeature(GenerationStep.Decoration.TOP_LAYER_MODIFICATION, ESPlacedFeatures.FINAL_MODIFICATION);

		builder.addCarver(GenerationStep.Carving.AIR, ESConfiguredWorldCarvers.CAVES);

		return builder;
	}

	public static BiomeGenerationSettings.Builder baseAquaticGenSettings(HolderGetter<PlacedFeature> featureGetter, HolderGetter<ConfiguredWorldCarver<?>> carverGetter) {
		BiomeGenerationSettings.Builder builder = new BiomeGenerationSettings.Builder(featureGetter, carverGetter);

		BiomeDefaultFeatures.addSurfaceFreezing(builder);
		builder.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, ESPlacedFeatures.CAVE_MOSS);
		builder.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, ESPlacedFeatures.CAVE_MOSS_VEIN);
		builder.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, ESPlacedFeatures.CAVE_MOSS_PATCH);
		builder.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, ESPlacedFeatures.BOULDERSHROOM);
		builder.addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, ESPlacedFeatures.STONE_ORE);
		builder.addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, ESPlacedFeatures.DEEPSLATE_ORE);
		builder.addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, ESPlacedFeatures.NIGHTFALL_DIRT_ORE);
		builder.addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, ESPlacedFeatures.DIMSLAG_ORE);
		builder.addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, ESPlacedFeatures.SAND_ORE);
		builder.addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, ESPlacedFeatures.GLOWING_GRIMSTONE_ORE);
		builder.addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, ESPlacedFeatures.GLOWING_VOIDSTONE_ORE);
		builder.addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, ESPlacedFeatures.UPPER_STARLIT_DIAMOND_ORE);
		builder.addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, ESPlacedFeatures.LOWER_STARLIT_DIAMOND_ORE);
		builder.addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, ESPlacedFeatures.UPPER_DEEPSILVER_ORE);
		builder.addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, ESPlacedFeatures.LOWER_DEEPSILVER_ORE);
		builder.addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, ESPlacedFeatures.UPPER_REDSTONE_ORE);
		builder.addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, ESPlacedFeatures.LOWER_REDSTONE_ORE);
		builder.addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, ESPlacedFeatures.UPPER_SALTPETER_ORE);
		builder.addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, ESPlacedFeatures.LOWER_SALTPETER_ORE);
		builder.addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, ESPlacedFeatures.UPPER_STARCORE_ORE);
		builder.addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, ESPlacedFeatures.LOWER_STARCORE_ORE);
		builder.addFeature(GenerationStep.Decoration.TOP_LAYER_MODIFICATION, ESPlacedFeatures.FINAL_MODIFICATION);

		builder.addCarver(GenerationStep.Carving.AIR, ESConfiguredWorldCarvers.CAVES);

		return builder;
	}

	private static BiomeGenerationSettings.Builder forestGenSettings(HolderGetter<PlacedFeature> featureGetter, HolderGetter<ConfiguredWorldCarver<?>> carverGetter) {
		BiomeGenerationSettings.Builder builder = baseLandGenSettings(featureGetter, carverGetter);

		builder.addFeature(GenerationStep.Decoration.RAW_GENERATION, ESPlacedFeatures.FALLEN_LUNAR_LOG);
		builder.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, ESPlacedFeatures.STARLIGHT_FOREST);
		builder.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, ESPlacedFeatures.FOREST_VEGETATION);
		builder.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, ESPlacedFeatures.CAVE_VINE);
		builder.addFeature(GenerationStep.Decoration.LOCAL_MODIFICATIONS, ESPlacedFeatures.STONE_SPIKE);

		return builder;
	}

	private static BiomeGenerationSettings.Builder denseForestGenSettings(HolderGetter<PlacedFeature> featureGetter, HolderGetter<ConfiguredWorldCarver<?>> carverGetter) {
		BiomeGenerationSettings.Builder builder = baseLandGenSettings(featureGetter, carverGetter);

		builder.addFeature(GenerationStep.Decoration.RAW_GENERATION, ESPlacedFeatures.FALLEN_LUNAR_LOG);
		builder.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, ESPlacedFeatures.DENSE_FOREST);
		builder.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, ESPlacedFeatures.FOREST_VEGETATION);
		builder.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, ESPlacedFeatures.CAVE_VINE);

		return builder;
	}

	private static BiomeGenerationSettings.Builder plainsGenSettings(HolderGetter<PlacedFeature> featureGetter, HolderGetter<ConfiguredWorldCarver<?>> carverGetter) {
		BiomeGenerationSettings.Builder builder = baseLandGenSettings(featureGetter, carverGetter);

		builder.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, ESPlacedFeatures.SPARSE_FOREST);
		builder.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, ESPlacedFeatures.PLAINS_VEGETATION);
		builder.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, ESPlacedFeatures.CAVE_VINE);
		builder.addFeature(GenerationStep.Decoration.LOCAL_MODIFICATIONS, ESPlacedFeatures.LUSH_MONOLITH);

		return builder;
	}

	private static BiomeGenerationSettings.Builder scrublandGenSettings(HolderGetter<PlacedFeature> featureGetter, HolderGetter<ConfiguredWorldCarver<?>> carverGetter) {
		BiomeGenerationSettings.Builder builder = baseLandGenSettings(featureGetter, carverGetter);

		builder.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, ESPlacedFeatures.SCRUBLAND_FOREST);
		builder.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, ESPlacedFeatures.PLAINS_VEGETATION);
		builder.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, ESPlacedFeatures.CAVE_VINE);
		builder.addFeature(GenerationStep.Decoration.LOCAL_MODIFICATIONS, ESPlacedFeatures.LUSH_MONOLITH);

		return builder;
	}

	private static BiomeGenerationSettings.Builder permafrostForestGenSettings(HolderGetter<PlacedFeature> featureGetter, HolderGetter<ConfiguredWorldCarver<?>> carverGetter) {
		BiomeGenerationSettings.Builder builder = baseLandGenSettings(featureGetter, carverGetter);

		builder.addFeature(GenerationStep.Decoration.RAW_GENERATION, ESPlacedFeatures.FALLEN_NORTHLAND_LOG);
		builder.addFeature(GenerationStep.Decoration.UNDERGROUND_DECORATION, ESPlacedFeatures.GLACITE);
		builder.addFeature(GenerationStep.Decoration.UNDERGROUND_DECORATION, ESPlacedFeatures.ICICLE);
		builder.addFeature(GenerationStep.Decoration.UNDERGROUND_DECORATION, ESPlacedFeatures.HANGING_ICICLE);
		builder.addFeature(GenerationStep.Decoration.UNDERGROUND_DECORATION, ESPlacedFeatures.ASHEN_SNOW);
		builder.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, ESPlacedFeatures.PERMAFROST_FOREST);
		builder.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, ESPlacedFeatures.PERMAFROST_FOREST_VEGETATION);

		return builder;
	}

	private static BiomeGenerationSettings.Builder taigaGenSettings(HolderGetter<PlacedFeature> featureGetter, HolderGetter<ConfiguredWorldCarver<?>> carverGetter) {
		BiomeGenerationSettings.Builder builder = baseLandGenSettings(featureGetter, carverGetter);

		builder.addFeature(GenerationStep.Decoration.RAW_GENERATION, ESPlacedFeatures.FALLEN_NORTHLAND_LOG);
		builder.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, ESPlacedFeatures.TAIGA_FOREST);
		builder.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, ESPlacedFeatures.PERMAFROST_FOREST_VEGETATION);
		builder.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, ESPlacedFeatures.CAVE_VINE);
		builder.addFeature(GenerationStep.Decoration.LOCAL_MODIFICATIONS, ESPlacedFeatures.MONOLITH);

		return builder;
	}

	private static BiomeGenerationSettings.Builder swampGenSettings(HolderGetter<PlacedFeature> featureGetter, HolderGetter<ConfiguredWorldCarver<?>> carverGetter) {
		BiomeGenerationSettings.Builder builder = baseLandGenSettings(featureGetter, carverGetter);

		builder.addFeature(GenerationStep.Decoration.RAW_GENERATION, ESPlacedFeatures.FALLEN_BANYIN_LOG);
		builder.addFeature(GenerationStep.Decoration.LAKES, ESPlacedFeatures.SWAMP_WATER);
		builder.addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, ESPlacedFeatures.UPPER_MALARITE_ORE);
		builder.addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, ESPlacedFeatures.LOWER_MALARITE_ORE);
		builder.addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, ESPlacedFeatures.GLOWING_NIGHTFALL_MUD_ORE);
		builder.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, ESPlacedFeatures.SWAMP_FOREST);
		builder.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, ESPlacedFeatures.SWAMP_VEGETATION);
		builder.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, ESPlacedFeatures.WATER_SURFACE_VEGETATION);
		builder.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, ESPlacedFeatures.UNDERGROUND_SWAMP_VEGETATION);
		builder.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, ESPlacedFeatures.HANGING_FANTAGRASS);

		return builder;
	}

	private static BiomeGenerationSettings.Builder scarletForestGenSettings(HolderGetter<PlacedFeature> featureGetter, HolderGetter<ConfiguredWorldCarver<?>> carverGetter) {
		BiomeGenerationSettings.Builder builder = baseLandGenSettings(featureGetter, carverGetter);

		builder.addFeature(GenerationStep.Decoration.RAW_GENERATION, ESPlacedFeatures.FALLEN_SCARLET_LOG);
		builder.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, VegetationPlacements.PATCH_PUMPKIN);
		builder.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, ESPlacedFeatures.SCARLET_FOREST_VEGETATION);
		builder.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, ESPlacedFeatures.SCARLET_FOREST);
		builder.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, ESPlacedFeatures.SCARLET_LEAVES_PILE);

		return builder;
	}

	private static BiomeGenerationSettings.Builder torreyaForestGenSettings(HolderGetter<PlacedFeature> featureGetter, HolderGetter<ConfiguredWorldCarver<?>> carverGetter) {
		BiomeGenerationSettings.Builder builder = baseLandGenSettings(featureGetter, carverGetter);

		builder.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, ESPlacedFeatures.TORREYA_FOREST_VEGETATION);
		builder.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, ESPlacedFeatures.TORREYA_FOREST);

		return builder;
	}

	private static BiomeGenerationSettings.Builder desertGenSettings(HolderGetter<PlacedFeature> featureGetter, HolderGetter<ConfiguredWorldCarver<?>> carverGetter) {
		BiomeGenerationSettings.Builder builder = baseLandGenSettings(featureGetter, carverGetter, false);

		builder.addFeature(GenerationStep.Decoration.UNDERGROUND_DECORATION, ESPlacedFeatures.STARLIGHT_CRYSTAL);
		builder.addFeature(GenerationStep.Decoration.LOCAL_MODIFICATIONS, ESPlacedFeatures.STARLIGHT_CRYSTAL_SURFACE);
		builder.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, ESPlacedFeatures.DESERT_VEGETATION);
		builder.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, ESPlacedFeatures.DEAD_LUNAR_TREE);
		builder.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, ESPlacedFeatures.LUNARIS_CACTUS);
		builder.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, ESPlacedFeatures.CRYSTAL_CAVES_VEGETATION);
		builder.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, ESPlacedFeatures.RED_CRYSTAL_MOSS_PATCH);
		builder.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, ESPlacedFeatures.RED_CRYSTAL_MOSS_PATCH_CEILING);
		builder.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, ESPlacedFeatures.BLUE_CRYSTAL_MOSS_PATCH);
		builder.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, ESPlacedFeatures.BLUE_CRYSTAL_MOSS_PATCH_CEILING);

		return builder;
	}

	private static BiomeGenerationSettings.Builder mushroomGenSettings(HolderGetter<PlacedFeature> featureGetter, HolderGetter<ConfiguredWorldCarver<?>> carverGetter) {
		BiomeGenerationSettings.Builder builder = baseLandGenSettings(featureGetter, carverGetter);

		builder.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, ESPlacedFeatures.MUSHROOM_FOREST);
		builder.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, ESPlacedFeatures.MUSHROOM_VEGETATION);
		builder.addFeature(GenerationStep.Decoration.LOCAL_MODIFICATIONS, ESPlacedFeatures.MONOLITH);

		return builder;
	}

	private static BiomeGenerationSettings.Builder solarisGenSettings(HolderGetter<PlacedFeature> featureGetter, HolderGetter<ConfiguredWorldCarver<?>> carverGetter) {
		BiomeGenerationSettings.Builder builder = new BiomeGenerationSettings.Builder(featureGetter, carverGetter);

		builder.addFeature(GenerationStep.Decoration.TOP_LAYER_MODIFICATION, ESPlacedFeatures.SOLARIS_ISLAND);

		return builder;
	}

	private static BiomeGenerationSettings.Builder riverGenSettings(HolderGetter<PlacedFeature> featureGetter, HolderGetter<ConfiguredWorldCarver<?>> carverGetter) {
		BiomeGenerationSettings.Builder builder = baseAquaticGenSettings(featureGetter, carverGetter);

		builder.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, ESPlacedFeatures.WATER_SURFACE_VEGETATION);

		return builder;
	}

	private static BiomeGenerationSettings.Builder etherRiverGenSettings(HolderGetter<PlacedFeature> featureGetter, HolderGetter<ConfiguredWorldCarver<?>> carverGetter) {
		BiomeGenerationSettings.Builder builder = baseAquaticGenSettings(featureGetter, carverGetter);

		builder.addFeature(GenerationStep.Decoration.RAW_GENERATION, ESPlacedFeatures.THIOQUARTZ_GEODE);

		return builder;
	}

	private static BiomeGenerationSettings.Builder oceanGenSettings(HolderGetter<PlacedFeature> featureGetter, HolderGetter<ConfiguredWorldCarver<?>> carverGetter) {
		BiomeGenerationSettings.Builder builder = baseAquaticGenSettings(featureGetter, carverGetter);

		builder.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, ESPlacedFeatures.ORBFLORA);
		builder.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, ESPlacedFeatures.OCEAN_VEGETATION);

		return builder;
	}

	private static BiomeGenerationSettings.Builder icySeaGenSettings(HolderGetter<PlacedFeature> featureGetter, HolderGetter<ConfiguredWorldCarver<?>> carverGetter) {
		BiomeGenerationSettings.Builder builder = baseAquaticGenSettings(featureGetter, carverGetter);

		builder.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, ESPlacedFeatures.ICE_SPIKE);
		builder.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, ESPlacedFeatures.OCEAN_VEGETATION);

		return builder;
	}

	private static BiomeGenerationSettings.Builder spiralKelpForestGenSettings(HolderGetter<PlacedFeature> featureGetter, HolderGetter<ConfiguredWorldCarver<?>> carverGetter) {
		BiomeGenerationSettings.Builder builder = baseAquaticGenSettings(featureGetter, carverGetter);

		builder.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, ESPlacedFeatures.SPIRAL_KELP);
		builder.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, ESPlacedFeatures.SEA_ROSA);
		builder.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, ESPlacedFeatures.SPIRAL_KELP_FOREST_VEGETATION);

		return builder;
	}

	private static BiomeGenerationSettings.Builder lushShallowSeaGenSettings(HolderGetter<PlacedFeature> featureGetter, HolderGetter<ConfiguredWorldCarver<?>> carverGetter) {
		BiomeGenerationSettings.Builder builder = baseAquaticGenSettings(featureGetter, carverGetter);

		builder.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, ESPlacedFeatures.VELVETUMOSS);
		builder.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, ESPlacedFeatures.LUMENSTEM);
		builder.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, ESPlacedFeatures.JINGLESTEM_FOREST);
		builder.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, ESPlacedFeatures.LUSH_SHALLOW_SEA_VEGETATION);

		return builder;
	}

	private static BiomeGenerationSettings.Builder theAbyssGenSettings(HolderGetter<PlacedFeature> featureGetter, HolderGetter<ConfiguredWorldCarver<?>> carverGetter) {
		BiomeGenerationSettings.Builder builder = baseAquaticGenSettings(featureGetter, carverGetter);

		builder.addFeature(GenerationStep.Decoration.RAW_GENERATION, ESPlacedFeatures.ABYSSAL_CAVE);
		builder.addFeature(GenerationStep.Decoration.LOCAL_MODIFICATIONS, ESPlacedFeatures.ABYSSLATE_PATCH);
		builder.addFeature(GenerationStep.Decoration.LOCAL_MODIFICATIONS, ESPlacedFeatures.THERMABYSSLATE_PATCH);
		builder.addFeature(GenerationStep.Decoration.LOCAL_MODIFICATIONS, ESPlacedFeatures.CRYOBYSSLATE_PATCH);
		builder.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, ESPlacedFeatures.ABYSSAL_KELP);
		builder.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, ESPlacedFeatures.ORBFLORA);
		builder.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, ESPlacedFeatures.RED_VELVETUMOSS);

		return builder;
	}

	private static BiomeGenerationSettings.Builder warmShoreGenSettings(HolderGetter<PlacedFeature> featureGetter, HolderGetter<ConfiguredWorldCarver<?>> carverGetter) {
		BiomeGenerationSettings.Builder builder = baseLandGenSettings(featureGetter, carverGetter, false);

		builder.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, ESPlacedFeatures.BEACH_VEGETATION);

		return builder;
	}

	private static BiomeGenerationSettings.Builder grimShoreGenSettings(HolderGetter<PlacedFeature> featureGetter, HolderGetter<ConfiguredWorldCarver<?>> carverGetter) {
		BiomeGenerationSettings.Builder builder = baseLandGenSettings(featureGetter, carverGetter, false);

		builder.addFeature(GenerationStep.Decoration.LOCAL_MODIFICATIONS, ESPlacedFeatures.STONE_SPIKE);
		builder.addFeature(GenerationStep.Decoration.LOCAL_MODIFICATIONS, ESPlacedFeatures.MONOLITH);

		return builder;
	}

	public static boolean anyNearbyGoldenGrassBlock(LevelAccessor level, BlockPos pos) {
		return BlockPos.withinManhattanStream(pos, 3, 3, 3).anyMatch(nearPos -> level.getBlockState(nearPos).is(ESBlocks.GOLDEN_GRASS_BLOCK.get()));
	}

	public static ResourceKey<Biome> create(String name) {
		return ResourceKey.create(Registries.BIOME, EternalStarlight.id(name));
	}
}

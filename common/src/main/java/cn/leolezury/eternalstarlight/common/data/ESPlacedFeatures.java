package cn.leolezury.eternalstarlight.common.data;

import cn.leolezury.eternalstarlight.common.EternalStarlight;
import cn.leolezury.eternalstarlight.common.registry.ESBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.data.worldgen.placement.PlacementUtils;
import net.minecraft.data.worldgen.placement.VegetationPlacements;
import net.minecraft.resources.ResourceKey;
import net.minecraft.util.valueproviders.ConstantInt;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.levelgen.VerticalAnchor;
import net.minecraft.world.level.levelgen.blockpredicates.BlockPredicate;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.placement.*;
import net.minecraft.world.level.material.Fluids;

import java.util.ArrayList;
import java.util.List;

public class ESPlacedFeatures {
	public static final ResourceKey<PlacedFeature> FINAL_MODIFICATION = create("final_modification");
	public static final ResourceKey<PlacedFeature> STONE_SPIKE = create("stone_spike");
	public static final ResourceKey<PlacedFeature> GLACITE = create("glacite");
	public static final ResourceKey<PlacedFeature> STONE_ORE = create("stone_ore");
	public static final ResourceKey<PlacedFeature> DEEPSLATE_ORE = create("deepslate_ore");
	public static final ResourceKey<PlacedFeature> GLOWING_GRIMSTONE_ORE = create("glowing_grimstone_ore");
	public static final ResourceKey<PlacedFeature> GLOWING_VOIDSTONE_ORE = create("glowing_voidstone_ore");
	public static final ResourceKey<PlacedFeature> GLOWING_NIGHTFALL_MUD_ORE = create("glowing_nightfall_mud_ore");
	public static final ResourceKey<PlacedFeature> NIGHTFALL_DIRT_ORE = create("nightfall_dirt_ore");
	public static final ResourceKey<PlacedFeature> SAND_ORE = create("sand_ore");
	public static final ResourceKey<PlacedFeature> SWAMP_SILVER_ORE = create("swamp_silver_ore");
	public static final ResourceKey<PlacedFeature> GRIMSTONE_REDSTONE_ORE = create("grimstone_redstone_ore");
	public static final ResourceKey<PlacedFeature> VOIDSTONE_REDSTONE_ORE = create("voidstone_redstone_ore");
	public static final ResourceKey<PlacedFeature> GRIMSTONE_SALTPETER_ORE = create("grimstone_saltpeter_ore");
	public static final ResourceKey<PlacedFeature> VOIDSTONE_SALTPETER_ORE = create("voidstone_saltpeter_ore");
	public static final ResourceKey<PlacedFeature> GRIMSTONE_ATALPHAITE_ORE = create("grimstone_atalphaite_ore");
	public static final ResourceKey<PlacedFeature> VOIDSTONE_ATALPHAITE_ORE = create("voidstone_atalphaite_ore");
	public static final ResourceKey<PlacedFeature> FALLEN_LUNAR_LOG = create("fallen_lunar_log");
	public static final ResourceKey<PlacedFeature> FALLEN_NORTHLAND_LOG = create("fallen_northland_log");
	public static final ResourceKey<PlacedFeature> FALLEN_STARLIGHT_MANGROVE_LOG = create("fallen_starlight_mangrove_log");
	public static final ResourceKey<PlacedFeature> FALLEN_SCARLET_LOG = create("fallen_scarlet_log");
	public static final ResourceKey<PlacedFeature> SCARLET_LEAVES_PILE = create("scarlet_leaves_pile");
	public static final ResourceKey<PlacedFeature> STARLIGHT_CRYSTAL = create("starlight_crystal");
	public static final ResourceKey<PlacedFeature> STARLIGHT_CRYSTAL_SURFACE = create("starlight_crystal_surface");
	public static final ResourceKey<PlacedFeature> STELLAGMITE = create("stellagmite");
	public static final ResourceKey<PlacedFeature> THIOQUARTZ_GEODE = create("thioquartz_geode");
	public static final ResourceKey<PlacedFeature> CAVE_VINE = create("cave_vine");
	public static final ResourceKey<PlacedFeature> CAVE_MOSS = create("cave_moss");
	public static final ResourceKey<PlacedFeature> CAVE_MOSS_VEIN = create("cave_moss_vein");
	public static final ResourceKey<PlacedFeature> ABYSSAL_KELP = create("abyssal_kelp");
	public static final ResourceKey<PlacedFeature> ORBFLORA = create("orbflora");
	public static final ResourceKey<PlacedFeature> OCEAN_VEGETATION = create("ocean_vegetation");
	public static final ResourceKey<PlacedFeature> ABYSSLATE_PATCH = create("abysslate_patch");
	public static final ResourceKey<PlacedFeature> THERMABYSSLATE_PATCH = create("thermabysslate_patch");
	public static final ResourceKey<PlacedFeature> CRYOBYSSLATE_PATCH = create("cryobysslate_patch");
	public static final ResourceKey<PlacedFeature> VELVETUMOSS = create("velvetumoss");
	public static final ResourceKey<PlacedFeature> RED_VELVETUMOSS = create("red_velvetumoss");
	public static final ResourceKey<PlacedFeature> LUNAR_TREE_CHECKED = create("lunar_tree_checked");
	public static final ResourceKey<PlacedFeature> LUNAR_HUGE_TREE_CHECKED = create("lunar_huge_tree_checked");
	public static final ResourceKey<PlacedFeature> NORTHLAND_TREE_CHECKED = create("northland_tree_checked");
	public static final ResourceKey<PlacedFeature> STARLIGHT_MANGROVE_TREE_CHECKED = create("starlight_mangrove_tree_checked");
	public static final ResourceKey<PlacedFeature> SCARLET_TREE_CHECKED = create("scarlet_tree_checked");
	public static final ResourceKey<PlacedFeature> TORREYA_TREE_CHECKED = create("torreya_tree_checked");
	public static final ResourceKey<PlacedFeature> HUGE_GLOWING_MUSHROOM_CHECKED = create("huge_glowing_mushroom_checked");
	public static final ResourceKey<PlacedFeature> NORTHLAND_ON_SNOW = create("northland_on_snow");
	public static final ResourceKey<PlacedFeature> STARLIGHT_FOREST = create("starlight_forest");
	public static final ResourceKey<PlacedFeature> DENSE_FOREST = create("dense_forest");
	public static final ResourceKey<PlacedFeature> SWAMP_FOREST = create("swamp_forest");
	public static final ResourceKey<PlacedFeature> PERMAFROST_FOREST = create("permafrost_forest");
	public static final ResourceKey<PlacedFeature> SCARLET_FOREST = create("scarlet_forest");
	public static final ResourceKey<PlacedFeature> TORREYA_FOREST = create("torreya_forest");
	public static final ResourceKey<PlacedFeature> DEAD_LUNAR_TREE = create("dead_lunar_tree");
	public static final ResourceKey<PlacedFeature> LUNARIS_CACTUS = create("lunaris_cactus");
	public static final ResourceKey<PlacedFeature> FOREST_VEGETATION = create("forest_vegetation");
	public static final ResourceKey<PlacedFeature> SWAMP_VEGETATION = create("swamp_vegetation");
	public static final ResourceKey<PlacedFeature> PERMAFROST_FOREST_VEGETATION = create("permafrost_forest_vegetation");
	public static final ResourceKey<PlacedFeature> SCARLET_FOREST_VEGETATION = create("scarlet_forest_vegetation");
	public static final ResourceKey<PlacedFeature> TORREYA_FOREST_VEGETATION = create("torreya_forest_vegetation");
	public static final ResourceKey<PlacedFeature> DESERT_VEGETATION = create("desert_vegetation");
	public static final ResourceKey<PlacedFeature> WATERSIDE_VEGETATION = create("waterside_vegetation");
	public static final ResourceKey<PlacedFeature> WATER_SURFACE_VEGETATION = create("water_surface_plant");
	public static final ResourceKey<PlacedFeature> CRYSTAL_CAVES_VEGETATION = create("crystal_caves_vegetation");
	public static final ResourceKey<PlacedFeature> SWAMP_WATER = create("swamp_water");
	public static final ResourceKey<PlacedFeature> HOT_SPRING = create("hot_spring");

	// structure features
	public static final ResourceKey<PlacedFeature> GOLEM_FORGE_CHIMNEY = create("golem_forge_chimney");

	public static void bootstrap(BootstrapContext<PlacedFeature> context) {
		HolderGetter<ConfiguredFeature<?, ?>> configuredFeatures = context.lookup(Registries.CONFIGURED_FEATURE);
		BlockPredicate snowPredicate = BlockPredicate.matchesBlocks(Direction.DOWN.getNormal(), Blocks.SNOW_BLOCK, Blocks.POWDER_SNOW);
		List<PlacementModifier> onSnow = List.of(EnvironmentScanPlacement.scanningFor(Direction.UP, BlockPredicate.not(BlockPredicate.matchesBlocks(Blocks.POWDER_SNOW)), 8), BlockPredicateFilter.forPredicate(snowPredicate));
		List<BlockPredicate> nearWater = new ArrayList<>();
		for (int x = -3; x <= 3; x++) {
			for (int y = -2; y <= 2; y++) {
				for (int z = -3; z <= 3; z++) {
					nearWater.add(BlockPredicate.matchesFluids(new BlockPos(x, y, z), Fluids.WATER, Fluids.FLOWING_WATER));
				}
			}
		}

		PlacementUtils.register(context, FINAL_MODIFICATION, configuredFeatures.getOrThrow(ESConfiguredFeatures.FINAL_MODIFICATION), PlacementUtils.HEIGHTMAP_WORLD_SURFACE, BiomeFilter.biome());
		PlacementUtils.register(context, STONE_SPIKE, configuredFeatures.getOrThrow(ESConfiguredFeatures.STONE_SPIKE), RarityFilter.onAverageOnceEvery(20), InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP_WORLD_SURFACE, BiomeFilter.biome());
		PlacementUtils.register(context, GLACITE, configuredFeatures.getOrThrow(ESConfiguredFeatures.GLACITE), InSquarePlacement.spread(), HeightRangePlacement.uniform(VerticalAnchor.bottom(), VerticalAnchor.absolute(45)), BlockPredicateFilter.forPredicate(BlockPredicate.ONLY_IN_AIR_PREDICATE), BiomeFilter.biome());
		PlacementUtils.register(context, STONE_ORE, configuredFeatures.getOrThrow(ESConfiguredFeatures.STONE_ORE), commonOrePlacement(2, HeightRangePlacement.uniform(VerticalAnchor.absolute(0), VerticalAnchor.top())));
		PlacementUtils.register(context, DEEPSLATE_ORE, configuredFeatures.getOrThrow(ESConfiguredFeatures.DEEPSLATE_ORE), commonOrePlacement(2, HeightRangePlacement.uniform(VerticalAnchor.absolute(-64), VerticalAnchor.absolute(0))));
		PlacementUtils.register(context, GLOWING_GRIMSTONE_ORE, configuredFeatures.getOrThrow(ESConfiguredFeatures.GLOWING_GRIMSTONE_ORE), commonOrePlacement(2, HeightRangePlacement.uniform(VerticalAnchor.absolute(0), VerticalAnchor.top())));
		PlacementUtils.register(context, GLOWING_VOIDSTONE_ORE, configuredFeatures.getOrThrow(ESConfiguredFeatures.GLOWING_VOIDSTONE_ORE), commonOrePlacement(2, HeightRangePlacement.uniform(VerticalAnchor.absolute(-64), VerticalAnchor.absolute(0))));
		PlacementUtils.register(context, GLOWING_NIGHTFALL_MUD_ORE, configuredFeatures.getOrThrow(ESConfiguredFeatures.GLOWING_NIGHTFALL_MUD_ORE), commonOrePlacement(7, HeightRangePlacement.uniform(VerticalAnchor.absolute(0), VerticalAnchor.top())));
		PlacementUtils.register(context, NIGHTFALL_DIRT_ORE, configuredFeatures.getOrThrow(ESConfiguredFeatures.NIGHTFALL_DIRT_ORE), commonOrePlacement(7, HeightRangePlacement.uniform(VerticalAnchor.absolute(0), VerticalAnchor.top())));
		PlacementUtils.register(context, SAND_ORE, configuredFeatures.getOrThrow(ESConfiguredFeatures.SAND_ORE), commonOrePlacement(8, HeightRangePlacement.uniform(VerticalAnchor.absolute(0), VerticalAnchor.top())));
		PlacementUtils.register(context, SWAMP_SILVER_ORE, configuredFeatures.getOrThrow(ESConfiguredFeatures.SWAMP_SILVER_ORE), commonOrePlacement(15, HeightRangePlacement.uniform(VerticalAnchor.absolute(0), VerticalAnchor.top())));
		PlacementUtils.register(context, GRIMSTONE_REDSTONE_ORE, configuredFeatures.getOrThrow(ESConfiguredFeatures.GRIMSTONE_REDSTONE_ORE), commonOrePlacement(15, HeightRangePlacement.uniform(VerticalAnchor.absolute(0), VerticalAnchor.top())));
		PlacementUtils.register(context, VOIDSTONE_REDSTONE_ORE, configuredFeatures.getOrThrow(ESConfiguredFeatures.VOIDSTONE_REDSTONE_ORE), commonOrePlacement(10, HeightRangePlacement.uniform(VerticalAnchor.absolute(-64), VerticalAnchor.absolute(0))));
		PlacementUtils.register(context, GRIMSTONE_SALTPETER_ORE, configuredFeatures.getOrThrow(ESConfiguredFeatures.GRIMSTONE_SALTPETER_ORE), commonOrePlacement(18, HeightRangePlacement.uniform(VerticalAnchor.absolute(0), VerticalAnchor.top())));
		PlacementUtils.register(context, VOIDSTONE_SALTPETER_ORE, configuredFeatures.getOrThrow(ESConfiguredFeatures.VOIDSTONE_SALTPETER_ORE), commonOrePlacement(7, HeightRangePlacement.uniform(VerticalAnchor.absolute(-64), VerticalAnchor.absolute(0))));
		PlacementUtils.register(context, GRIMSTONE_ATALPHAITE_ORE, configuredFeatures.getOrThrow(ESConfiguredFeatures.GRIMSTONE_ATALPHAITE_ORE), commonOrePlacement(6, HeightRangePlacement.uniform(VerticalAnchor.absolute(0), VerticalAnchor.top())));
		PlacementUtils.register(context, VOIDSTONE_ATALPHAITE_ORE, configuredFeatures.getOrThrow(ESConfiguredFeatures.VOIDSTONE_ATALPHAITE_ORE), commonOrePlacement(5, HeightRangePlacement.uniform(VerticalAnchor.absolute(-64), VerticalAnchor.absolute(0))));
		PlacementUtils.register(context, FALLEN_LUNAR_LOG, configuredFeatures.getOrThrow(ESConfiguredFeatures.FALLEN_LUNAR_LOG), RarityFilter.onAverageOnceEvery(12), CountPlacement.of(2), InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP_WORLD_SURFACE, BiomeFilter.biome());
		PlacementUtils.register(context, FALLEN_NORTHLAND_LOG, configuredFeatures.getOrThrow(ESConfiguredFeatures.FALLEN_NORTHLAND_LOG), RarityFilter.onAverageOnceEvery(12), CountPlacement.of(2), InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP_WORLD_SURFACE, BiomeFilter.biome());
		PlacementUtils.register(context, FALLEN_STARLIGHT_MANGROVE_LOG, configuredFeatures.getOrThrow(ESConfiguredFeatures.FALLEN_STARLIGHT_MANGROVE_LOG), RarityFilter.onAverageOnceEvery(12), CountPlacement.of(2), InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP_WORLD_SURFACE, BiomeFilter.biome());
		PlacementUtils.register(context, FALLEN_SCARLET_LOG, configuredFeatures.getOrThrow(ESConfiguredFeatures.FALLEN_SCARLET_LOG), RarityFilter.onAverageOnceEvery(12), CountPlacement.of(2), InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP_WORLD_SURFACE, BiomeFilter.biome());
		PlacementUtils.register(context, SCARLET_LEAVES_PILE, configuredFeatures.getOrThrow(ESConfiguredFeatures.SCARLET_LEAVES_PILE), InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP_WORLD_SURFACE, BiomeFilter.biome());
		PlacementUtils.register(context, STARLIGHT_CRYSTAL, configuredFeatures.getOrThrow(ESConfiguredFeatures.STARLIGHT_CRYSTAL), CountPlacement.of(5), InSquarePlacement.spread(), HeightRangePlacement.uniform(VerticalAnchor.bottom(), VerticalAnchor.absolute(45)), EnvironmentScanPlacement.scanningFor(Direction.DOWN, BlockPredicate.hasSturdyFace(Direction.UP), BlockPredicate.ONLY_IN_AIR_PREDICATE, 12), RandomOffsetPlacement.vertical(ConstantInt.of(-1)), BiomeFilter.biome());
		PlacementUtils.register(context, STARLIGHT_CRYSTAL_SURFACE, configuredFeatures.getOrThrow(ESConfiguredFeatures.STARLIGHT_CRYSTAL), RarityFilter.onAverageOnceEvery(25), InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP_WORLD_SURFACE, BiomeFilter.biome());
		PlacementUtils.register(context, STELLAGMITE, configuredFeatures.getOrThrow(ESConfiguredFeatures.STELLAGMITE), RarityFilter.onAverageOnceEvery(3), InSquarePlacement.spread(), HeightRangePlacement.uniform(VerticalAnchor.bottom(), VerticalAnchor.absolute(45)), EnvironmentScanPlacement.scanningFor(Direction.DOWN, BlockPredicate.hasSturdyFace(Direction.UP), BlockPredicate.ONLY_IN_AIR_PREDICATE, 12), RandomOffsetPlacement.vertical(ConstantInt.of(-1)), BiomeFilter.biome());
		PlacementUtils.register(context, THIOQUARTZ_GEODE, configuredFeatures.getOrThrow(ESConfiguredFeatures.THIOQUARTZ_GEODE), RarityFilter.onAverageOnceEvery(20), InSquarePlacement.spread(), HeightRangePlacement.uniform(VerticalAnchor.aboveBottom(6), VerticalAnchor.absolute(30)), BiomeFilter.biome());
		PlacementUtils.register(context, CAVE_VINE, configuredFeatures.getOrThrow(ESConfiguredFeatures.CAVE_VINE), CountPlacement.of(25), InSquarePlacement.spread(), PlacementUtils.RANGE_BOTTOM_TO_MAX_TERRAIN_HEIGHT, EnvironmentScanPlacement.scanningFor(Direction.UP, BlockPredicate.hasSturdyFace(Direction.DOWN), BlockPredicate.ONLY_IN_AIR_PREDICATE, 12), RandomOffsetPlacement.vertical(ConstantInt.of(-1)), BiomeFilter.biome());
		PlacementUtils.register(context, CAVE_MOSS, configuredFeatures.getOrThrow(ESConfiguredFeatures.CAVE_MOSS), CountPlacement.of(25), InSquarePlacement.spread(), PlacementUtils.RANGE_BOTTOM_TO_MAX_TERRAIN_HEIGHT, EnvironmentScanPlacement.scanningFor(Direction.UP, BlockPredicate.hasSturdyFace(Direction.DOWN), BlockPredicate.ONLY_IN_AIR_PREDICATE, 12), RandomOffsetPlacement.vertical(ConstantInt.of(-1)), BiomeFilter.biome());
		PlacementUtils.register(context, CAVE_MOSS_VEIN, configuredFeatures.getOrThrow(ESConfiguredFeatures.CAVE_MOSS_VEIN), CountPlacement.of(UniformInt.of(15, 20)), PlacementUtils.RANGE_BOTTOM_TO_MAX_TERRAIN_HEIGHT, InSquarePlacement.spread(), SurfaceRelativeThresholdFilter.of(Heightmap.Types.OCEAN_FLOOR_WG, Integer.MIN_VALUE, -13), BiomeFilter.biome());
		PlacementUtils.register(context, ABYSSAL_KELP, configuredFeatures.getOrThrow(ESConfiguredFeatures.ABYSSAL_KELP), NoiseBasedCountPlacement.of(20, 80.0, 0.0), InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP_TOP_SOLID, BiomeFilter.biome());
		PlacementUtils.register(context, ORBFLORA, configuredFeatures.getOrThrow(ESConfiguredFeatures.ORBFLORA), NoiseBasedCountPlacement.of(15, 90.0, 0.0), InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP_TOP_SOLID, BiomeFilter.biome());
		PlacementUtils.register(context, OCEAN_VEGETATION, configuredFeatures.getOrThrow(ESConfiguredFeatures.OCEAN_VEGETATION), NoiseBasedCountPlacement.of(15, 400.0, 0.0), InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP_TOP_SOLID, BiomeFilter.biome());
		PlacementUtils.register(context, ABYSSLATE_PATCH, configuredFeatures.getOrThrow(ESConfiguredFeatures.ABYSSLATE_PATCH), RarityFilter.onAverageOnceEvery(3), InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP_TOP_SOLID, BiomeFilter.biome());
		PlacementUtils.register(context, THERMABYSSLATE_PATCH, configuredFeatures.getOrThrow(ESConfiguredFeatures.THERMABYSSLATE_PATCH), RarityFilter.onAverageOnceEvery(3), InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP_TOP_SOLID, BiomeFilter.biome());
		PlacementUtils.register(context, CRYOBYSSLATE_PATCH, configuredFeatures.getOrThrow(ESConfiguredFeatures.CRYOBYSSLATE_PATCH), RarityFilter.onAverageOnceEvery(3), InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP_TOP_SOLID, BiomeFilter.biome());
		PlacementUtils.register(context, VELVETUMOSS, configuredFeatures.getOrThrow(ESConfiguredFeatures.VELVETUMOSS), RarityFilter.onAverageOnceEvery(6), InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP_TOP_SOLID, BiomeFilter.biome());
		PlacementUtils.register(context, RED_VELVETUMOSS, configuredFeatures.getOrThrow(ESConfiguredFeatures.RED_VELVETUMOSS), RarityFilter.onAverageOnceEvery(4), InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP_TOP_SOLID, BiomeFilter.biome());
		PlacementUtils.register(context, LUNAR_TREE_CHECKED, configuredFeatures.getOrThrow(ESConfiguredFeatures.LUNAR), PlacementUtils.filteredByBlockSurvival(ESBlocks.LUNAR_SAPLING.get()));
		PlacementUtils.register(context, LUNAR_HUGE_TREE_CHECKED, configuredFeatures.getOrThrow(ESConfiguredFeatures.LUNAR_HUGE), PlacementUtils.filteredByBlockSurvival(ESBlocks.LUNAR_SAPLING.get()));
		PlacementUtils.register(context, NORTHLAND_TREE_CHECKED, configuredFeatures.getOrThrow(ESConfiguredFeatures.NORTHLAND), PlacementUtils.filteredByBlockSurvival(ESBlocks.NORTHLAND_SAPLING.get()));
		PlacementUtils.register(context, STARLIGHT_MANGROVE_TREE_CHECKED, configuredFeatures.getOrThrow(ESConfiguredFeatures.STARLIGHT_MANGROVE), PlacementUtils.filteredByBlockSurvival(ESBlocks.STARLIGHT_MANGROVE_SAPLING.get()));
		PlacementUtils.register(context, SCARLET_TREE_CHECKED, configuredFeatures.getOrThrow(ESConfiguredFeatures.SCARLET), PlacementUtils.filteredByBlockSurvival(ESBlocks.SCARLET_SAPLING.get()));
		PlacementUtils.register(context, TORREYA_TREE_CHECKED, configuredFeatures.getOrThrow(ESConfiguredFeatures.TORREYA), PlacementUtils.filteredByBlockSurvival(ESBlocks.TORREYA_SAPLING.get()));
		PlacementUtils.register(context, HUGE_GLOWING_MUSHROOM_CHECKED, configuredFeatures.getOrThrow(ESConfiguredFeatures.HUGE_GLOWING_MUSHROOM), PlacementUtils.filteredByBlockSurvival(ESBlocks.GLOWING_MUSHROOM.get()));
		PlacementUtils.register(context, NORTHLAND_ON_SNOW, configuredFeatures.getOrThrow(ESConfiguredFeatures.NORTHLAND), onSnow);
		PlacementUtils.register(context, STARLIGHT_FOREST, configuredFeatures.getOrThrow(ESConfiguredFeatures.STARLIGHT_FOREST), VegetationPlacements.treePlacement(PlacementUtils.countExtra(12, 0.1F, 1)));
		PlacementUtils.register(context, DENSE_FOREST, configuredFeatures.getOrThrow(ESConfiguredFeatures.DENSE_FOREST), VegetationPlacements.treePlacement(PlacementUtils.countExtra(15, 0.1F, 2)));
		PlacementUtils.register(context, PERMAFROST_FOREST, configuredFeatures.getOrThrow(ESConfiguredFeatures.PERMAFROST_FOREST), VegetationPlacements.treePlacement(PlacementUtils.countExtra(4, 0.2F, 2)));
		PlacementUtils.register(context, SWAMP_FOREST, configuredFeatures.getOrThrow(ESConfiguredFeatures.SWAMP_FOREST), VegetationPlacements.treePlacement(PlacementUtils.countExtra(4, 0.1F, 1)));
		PlacementUtils.register(context, SCARLET_FOREST, configuredFeatures.getOrThrow(ESConfiguredFeatures.SCARLET_FOREST), VegetationPlacements.treePlacement(PlacementUtils.countExtra(6, 0.1F, 2)));
		PlacementUtils.register(context, TORREYA_FOREST, configuredFeatures.getOrThrow(ESConfiguredFeatures.TORREYA_FOREST), VegetationPlacements.treePlacement(PlacementUtils.countExtra(12, 0.1F, 1)));
		PlacementUtils.register(context, DEAD_LUNAR_TREE, configuredFeatures.getOrThrow(ESConfiguredFeatures.DEAD_LUNAR_TREE), RarityFilter.onAverageOnceEvery(20), InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP_WORLD_SURFACE, BiomeFilter.biome());
		PlacementUtils.register(context, LUNARIS_CACTUS, configuredFeatures.getOrThrow(ESConfiguredFeatures.LUNARIS_CACTUS), RarityFilter.onAverageOnceEvery(6), InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP, BiomeFilter.biome());
		PlacementUtils.register(context, FOREST_VEGETATION, configuredFeatures.getOrThrow(ESConfiguredFeatures.FOREST_VEGETATION), InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP_WORLD_SURFACE, BiomeFilter.biome());
		PlacementUtils.register(context, SWAMP_VEGETATION, configuredFeatures.getOrThrow(ESConfiguredFeatures.SWAMP_VEGETATION), InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP_WORLD_SURFACE, BiomeFilter.biome());
		PlacementUtils.register(context, PERMAFROST_FOREST_VEGETATION, configuredFeatures.getOrThrow(ESConfiguredFeatures.PERMAFROST_FOREST_VEGETATION), InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP_WORLD_SURFACE, BiomeFilter.biome());
		PlacementUtils.register(context, SCARLET_FOREST_VEGETATION, configuredFeatures.getOrThrow(ESConfiguredFeatures.SCARLET_FOREST_VEGETATION), InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP_WORLD_SURFACE, BiomeFilter.biome());
		PlacementUtils.register(context, TORREYA_FOREST_VEGETATION, configuredFeatures.getOrThrow(ESConfiguredFeatures.TORREYA_FOREST_VEGETATION), InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP_WORLD_SURFACE, BiomeFilter.biome());
		PlacementUtils.register(context, DESERT_VEGETATION, configuredFeatures.getOrThrow(ESConfiguredFeatures.DESERT_VEGETATION), InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP_WORLD_SURFACE, BiomeFilter.biome());
		PlacementUtils.register(context, WATERSIDE_VEGETATION, configuredFeatures.getOrThrow(ESConfiguredFeatures.WATERSIDE_VEGETATION), InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP_WORLD_SURFACE, BlockPredicateFilter.forPredicate(BlockPredicate.anyOf(nearWater)), BiomeFilter.biome());
		PlacementUtils.register(context, WATER_SURFACE_VEGETATION, configuredFeatures.getOrThrow(ESConfiguredFeatures.WATER_SURFACE_VEGETATION), InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP_WORLD_SURFACE, BiomeFilter.biome());
		PlacementUtils.register(context, CRYSTAL_CAVES_VEGETATION, configuredFeatures.getOrThrow(ESConfiguredFeatures.CRYSTAL_CAVES_VEGETATION), CountPlacement.of(15), InSquarePlacement.spread(), HeightRangePlacement.uniform(VerticalAnchor.bottom(), VerticalAnchor.absolute(45)), EnvironmentScanPlacement.scanningFor(Direction.DOWN, BlockPredicate.hasSturdyFace(Direction.UP), BlockPredicate.ONLY_IN_AIR_PREDICATE, 12), BiomeFilter.biome());
		PlacementUtils.register(context, SWAMP_WATER, configuredFeatures.getOrThrow(ESConfiguredFeatures.SWAMP_WATER), PlacementUtils.HEIGHTMAP_WORLD_SURFACE, BiomeFilter.biome());
		PlacementUtils.register(context, HOT_SPRING, configuredFeatures.getOrThrow(ESConfiguredFeatures.HOT_SPRING), RarityFilter.onAverageOnceEvery(50), InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP_WORLD_SURFACE, BiomeFilter.biome());

		// structure features
		PlacementUtils.register(context, GOLEM_FORGE_CHIMNEY, configuredFeatures.getOrThrow(ESConfiguredFeatures.GOLEM_FORGE_CHIMNEY));
	}


	private static ResourceKey<PlacedFeature> create(String name) {
		return ResourceKey.create(Registries.PLACED_FEATURE, EternalStarlight.id(name));
	}

	public static List<PlacementModifier> orePlacement(PlacementModifier modifier, PlacementModifier modifier1) {
		return List.of(modifier, InSquarePlacement.spread(), modifier1, BiomeFilter.biome());
	}

	public static List<PlacementModifier> commonOrePlacement(int count, PlacementModifier modifier) {
		return orePlacement(CountPlacement.of(count), modifier);
	}
}

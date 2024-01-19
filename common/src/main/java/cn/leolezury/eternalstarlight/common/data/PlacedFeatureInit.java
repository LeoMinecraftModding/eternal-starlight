package cn.leolezury.eternalstarlight.common.data;

import cn.leolezury.eternalstarlight.common.EternalStarlight;
import cn.leolezury.eternalstarlight.common.init.BlockInit;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.data.worldgen.placement.PlacementUtils;
import net.minecraft.data.worldgen.placement.VegetationPlacements;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.valueproviders.ConstantInt;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.VerticalAnchor;
import net.minecraft.world.level.levelgen.blockpredicates.BlockPredicate;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.placement.*;
import net.minecraft.world.level.material.Fluids;

import java.util.ArrayList;
import java.util.List;

public class PlacedFeatureInit {
    public static final ResourceKey<PlacedFeature> STONE_SPIKE = create("stone_spike");
    public static final ResourceKey<PlacedFeature> STONE_ORE = create("stone_ore");
    public static final ResourceKey<PlacedFeature> DEEPSLATE_ORE = create("deepslate_ore");
    public static final ResourceKey<PlacedFeature> GLOWING_GRIMSTONE_ORE = create("glowing_grimstone_ore");
    public static final ResourceKey<PlacedFeature> GLOWING_VOIDSTONE_ORE = create("glowing_voidstone_ore");
    public static final ResourceKey<PlacedFeature> GLOWING_NIGHTSHADE_MUD_ORE = create("glowing_nightshade_mud_ore");
    public static final ResourceKey<PlacedFeature> NIGHTSHADE_DIRT_ORE = create("nightshade_dirt_ore");
    public static final ResourceKey<PlacedFeature> SWAMP_SILVER_ORE = create("swamp_silver_ore");
    public static final ResourceKey<PlacedFeature> FALLEN_LUNAR_LOG = create("fallen_lunar_log");
    public static final ResourceKey<PlacedFeature> FALLEN_NORTHLAND_LOG = create("fallen_northland_log");
    public static final ResourceKey<PlacedFeature> FALLEN_STARLIGHT_MANGROVE_LOG = create("fallen_starlight_mangrove_log");
    public static final ResourceKey<PlacedFeature> FALLEN_SCARLET_LOG = create("fallen_scarlet_log");
    public static final ResourceKey<PlacedFeature> SCARLET_LEAVES_PILE = create("scarlet_leaves_pile");
    public static final ResourceKey<PlacedFeature> STARLIGHT_CRYSTAL = create("starlight_crystal");
    public static final ResourceKey<PlacedFeature> STARLIGHT_CRYSTAL_SURFACE = create("starlight_crystal_surface");
    public static final ResourceKey<PlacedFeature> CAVE_VINE = create("cave_vine");
    public static final ResourceKey<PlacedFeature> CAVE_MOSS = create("cave_moss");
    public static final ResourceKey<PlacedFeature> ABYSSAL_KELP = create("abyssal_kelp");
    public static final ResourceKey<PlacedFeature> OCEAN_VEGETATION = create("ocean_vegetation");
    public static final ResourceKey<PlacedFeature> ABYSSLATE_PATCH = create("abysslate_patch");
    public static final ResourceKey<PlacedFeature> THERMABYSSLATE_PATCH = create("thermabysslate_patch");
    public static final ResourceKey<PlacedFeature> CRYOBYSSLATE_PATCH = create("cryobysslate_patch");
    public static final ResourceKey<PlacedFeature> LUNAR_TREE_CHECKED = create("lunar_tree_checked");
    public static final ResourceKey<PlacedFeature> LUNAR_HUGE_TREE_CHECKED = create("lunar_huge_tree_checked");
    public static final ResourceKey<PlacedFeature> NORTHLAND_TREE_CHECKED = create("northland_tree_checked");
    public static final ResourceKey<PlacedFeature> STARLIGHT_MANGROVE_TREE_CHECKED = create("starlight_mangrove_tree_checked");
    public static final ResourceKey<PlacedFeature> SCARLET_TREE_CHECKED = create("scarlet_tree_checked");
    public static final ResourceKey<PlacedFeature> HUGE_GLOWING_MUSHROOM_CHECKED = create("huge_glowing_mushroom_checked");
    public static final ResourceKey<PlacedFeature> NORTHLAND_ON_SNOW = create("northland_on_snow");
    public static final ResourceKey<PlacedFeature> STARLIGHT_FOREST = create("starlight_forest");
    public static final ResourceKey<PlacedFeature> DENSE_FOREST = create("dense_forest");
    public static final ResourceKey<PlacedFeature> SWAMP_FOREST = create("swamp_forest");
    public static final ResourceKey<PlacedFeature> PERMAFROST_FOREST = create("permafrost_forest");
    public static final ResourceKey<PlacedFeature> SCARLET_FOREST = create("scarlet_forest");
    public static final ResourceKey<PlacedFeature> DEAD_LUNAR_TREE = create("dead_lunar_tree");
    public static final ResourceKey<PlacedFeature> FOREST_GRASS = create("forest_grass");
    public static final ResourceKey<PlacedFeature> SWAMP_GRASS = create("swamp_grass");
    public static final ResourceKey<PlacedFeature> PERMAFROST_FOREST_GRASS = create("permafrost_forest_grass");
    public static final ResourceKey<PlacedFeature> SCARLET_FOREST_GRASS = create("scarlet_forest_grass");
    public static final ResourceKey<PlacedFeature> DESERT_GRASS = create("desert_grass");
    public static final ResourceKey<PlacedFeature> NEAR_WATER_GRASS = create("near_water_grass");
    public static final ResourceKey<PlacedFeature> SWAMP_WATER = create("swamp_water");
    public static final ResourceKey<PlacedFeature> HOT_SPRING = create("hot_spring");

    // structure features
    public static final ResourceKey<PlacedFeature> CURSED_GARDEN_EXTRA_HEIGHT = create("cursed_garden_extra_height");

    public static void bootstrap(BootstapContext<PlacedFeature> context) {
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

        register(context, STONE_SPIKE, configuredFeatures.getOrThrow(ConfiguredFeatureInit.STONE_SPIKE), RarityFilter.onAverageOnceEvery(20), InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP_WORLD_SURFACE, BiomeFilter.biome());
        register(context, STONE_ORE, configuredFeatures.getOrThrow(ConfiguredFeatureInit.STONE_ORE), commonOrePlacement(2, HeightRangePlacement.uniform(VerticalAnchor.absolute(0), VerticalAnchor.top())));
        register(context, DEEPSLATE_ORE, configuredFeatures.getOrThrow(ConfiguredFeatureInit.DEEPSLATE_ORE), commonOrePlacement(2, HeightRangePlacement.uniform(VerticalAnchor.absolute(-64), VerticalAnchor.absolute(0))));
        register(context, GLOWING_GRIMSTONE_ORE, configuredFeatures.getOrThrow(ConfiguredFeatureInit.GLOWING_GRIMSTONE_ORE), commonOrePlacement(2, HeightRangePlacement.uniform(VerticalAnchor.absolute(0), VerticalAnchor.top())));
        register(context, GLOWING_VOIDSTONE_ORE, configuredFeatures.getOrThrow(ConfiguredFeatureInit.GLOWING_VOIDSTONE_ORE), commonOrePlacement(2, HeightRangePlacement.uniform(VerticalAnchor.absolute(-64), VerticalAnchor.absolute(0))));
        register(context, GLOWING_NIGHTSHADE_MUD_ORE, configuredFeatures.getOrThrow(ConfiguredFeatureInit.GLOWING_NIGHTSHADE_MUD_ORE), commonOrePlacement(7, HeightRangePlacement.uniform(VerticalAnchor.absolute(0), VerticalAnchor.top())));
        register(context, NIGHTSHADE_DIRT_ORE, configuredFeatures.getOrThrow(ConfiguredFeatureInit.NIGHTSHADE_DIRT_ORE), commonOrePlacement(7, HeightRangePlacement.uniform(VerticalAnchor.absolute(0), VerticalAnchor.top())));
        register(context, SWAMP_SILVER_ORE, configuredFeatures.getOrThrow(ConfiguredFeatureInit.SWAMP_SILVER_ORE), commonOrePlacement(30, HeightRangePlacement.uniform(VerticalAnchor.absolute(0), VerticalAnchor.top())));
        register(context, FALLEN_LUNAR_LOG, configuredFeatures.getOrThrow(ConfiguredFeatureInit.FALLEN_LUNAR_LOG), RarityFilter.onAverageOnceEvery(10), CountPlacement.of(2), InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP_WORLD_SURFACE, BiomeFilter.biome());
        register(context, FALLEN_NORTHLAND_LOG, configuredFeatures.getOrThrow(ConfiguredFeatureInit.FALLEN_NORTHLAND_LOG), RarityFilter.onAverageOnceEvery(10), CountPlacement.of(2), InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP_WORLD_SURFACE, BiomeFilter.biome());
        register(context, FALLEN_STARLIGHT_MANGROVE_LOG, configuredFeatures.getOrThrow(ConfiguredFeatureInit.FALLEN_STARLIGHT_MANGROVE_LOG), RarityFilter.onAverageOnceEvery(10), CountPlacement.of(2), InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP_WORLD_SURFACE, BiomeFilter.biome());
        register(context, FALLEN_SCARLET_LOG, configuredFeatures.getOrThrow(ConfiguredFeatureInit.FALLEN_SCARLET_LOG), RarityFilter.onAverageOnceEvery(10), CountPlacement.of(2), InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP_WORLD_SURFACE, BiomeFilter.biome());
        register(context, SCARLET_LEAVES_PILE, configuredFeatures.getOrThrow(ConfiguredFeatureInit.SCARLET_LEAVES_PILE), InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP_WORLD_SURFACE, BiomeFilter.biome());
        register(context, STARLIGHT_CRYSTAL, configuredFeatures.getOrThrow(ConfiguredFeatureInit.STARLIGHT_CRYSTAL), CountPlacement.of(5), InSquarePlacement.spread(), HeightRangePlacement.uniform(VerticalAnchor.bottom(), VerticalAnchor.absolute(45)), EnvironmentScanPlacement.scanningFor(Direction.DOWN, BlockPredicate.hasSturdyFace(Direction.UP), BlockPredicate.ONLY_IN_AIR_PREDICATE, 12), RandomOffsetPlacement.vertical(ConstantInt.of(-1)), BiomeFilter.biome());
        register(context, STARLIGHT_CRYSTAL_SURFACE, configuredFeatures.getOrThrow(ConfiguredFeatureInit.STARLIGHT_CRYSTAL), RarityFilter.onAverageOnceEvery(25), InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP_WORLD_SURFACE, BiomeFilter.biome());
        register(context, CAVE_VINE, configuredFeatures.getOrThrow(ConfiguredFeatureInit.CAVE_VINE), CountPlacement.of(188), InSquarePlacement.spread(), PlacementUtils.RANGE_BOTTOM_TO_MAX_TERRAIN_HEIGHT, EnvironmentScanPlacement.scanningFor(Direction.UP, BlockPredicate.hasSturdyFace(Direction.DOWN), BlockPredicate.ONLY_IN_AIR_PREDICATE, 32), RandomOffsetPlacement.vertical(ConstantInt.of(-1)), BiomeFilter.biome());
        register(context, CAVE_MOSS, configuredFeatures.getOrThrow(ConfiguredFeatureInit.CAVE_MOSS), CountPlacement.of(200), InSquarePlacement.spread(), PlacementUtils.RANGE_BOTTOM_TO_MAX_TERRAIN_HEIGHT, EnvironmentScanPlacement.scanningFor(Direction.UP, BlockPredicate.hasSturdyFace(Direction.DOWN), BlockPredicate.ONLY_IN_AIR_PREDICATE, 32), RandomOffsetPlacement.vertical(ConstantInt.of(-1)), BiomeFilter.biome());
        register(context, ABYSSAL_KELP, configuredFeatures.getOrThrow(ConfiguredFeatureInit.ABYSSAL_KELP), NoiseBasedCountPlacement.of(120, 80.0, 0.0), InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP_TOP_SOLID, BiomeFilter.biome());
        register(context, OCEAN_VEGETATION, configuredFeatures.getOrThrow(ConfiguredFeatureInit.OCEAN_VEGETATION), NoiseBasedCountPlacement.of(20, 400.0, 0.0), InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP_TOP_SOLID, BiomeFilter.biome());
        register(context, ABYSSLATE_PATCH, configuredFeatures.getOrThrow(ConfiguredFeatureInit.ABYSSLATE_PATCH), RarityFilter.onAverageOnceEvery(3), InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP_TOP_SOLID, BiomeFilter.biome());
        register(context, THERMABYSSLATE_PATCH, configuredFeatures.getOrThrow(ConfiguredFeatureInit.THERMABYSSLATE_PATCH), RarityFilter.onAverageOnceEvery(3), InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP_TOP_SOLID, BiomeFilter.biome());
        register(context, CRYOBYSSLATE_PATCH, configuredFeatures.getOrThrow(ConfiguredFeatureInit.CRYOBYSSLATE_PATCH), RarityFilter.onAverageOnceEvery(3), InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP_TOP_SOLID, BiomeFilter.biome());
        register(context, LUNAR_TREE_CHECKED, configuredFeatures.getOrThrow(ConfiguredFeatureInit.LUNAR), PlacementUtils.filteredByBlockSurvival(BlockInit.LUNAR_SAPLING.get()));
        register(context, LUNAR_HUGE_TREE_CHECKED, configuredFeatures.getOrThrow(ConfiguredFeatureInit.LUNAR_HUGE), PlacementUtils.filteredByBlockSurvival(BlockInit.LUNAR_SAPLING.get()));
        register(context, NORTHLAND_TREE_CHECKED, configuredFeatures.getOrThrow(ConfiguredFeatureInit.NORTHLAND), PlacementUtils.filteredByBlockSurvival(BlockInit.NORTHLAND_SAPLING.get()));
        register(context, STARLIGHT_MANGROVE_TREE_CHECKED, configuredFeatures.getOrThrow(ConfiguredFeatureInit.STARLIGHT_MANGROVE), PlacementUtils.filteredByBlockSurvival(BlockInit.STARLIGHT_MANGROVE_SAPLING.get()));
        register(context, SCARLET_TREE_CHECKED, configuredFeatures.getOrThrow(ConfiguredFeatureInit.SCARLET), PlacementUtils.filteredByBlockSurvival(BlockInit.SCARLET_SAPLING.get()));
        register(context, HUGE_GLOWING_MUSHROOM_CHECKED, configuredFeatures.getOrThrow(ConfiguredFeatureInit.HUGE_GLOWING_MUSHROOM), PlacementUtils.filteredByBlockSurvival(BlockInit.GLOWING_MUSHROOM.get()));
        register(context, NORTHLAND_ON_SNOW, configuredFeatures.getOrThrow(ConfiguredFeatureInit.NORTHLAND), onSnow);
        register(context, STARLIGHT_FOREST, configuredFeatures.getOrThrow(ConfiguredFeatureInit.STARLIGHT_FOREST), VegetationPlacements.treePlacement(PlacementUtils.countExtra(12, 0.1F, 1)));
        register(context, DENSE_FOREST, configuredFeatures.getOrThrow(ConfiguredFeatureInit.DENSE_FOREST), VegetationPlacements.treePlacement(PlacementUtils.countExtra(15, 0.1F, 2)));
        register(context, PERMAFROST_FOREST, configuredFeatures.getOrThrow(ConfiguredFeatureInit.PERMAFROST_FOREST), VegetationPlacements.treePlacement(PlacementUtils.countExtra(6, 0.1F, 2)));
        register(context, SWAMP_FOREST, configuredFeatures.getOrThrow(ConfiguredFeatureInit.SWAMP_FOREST), VegetationPlacements.treePlacement(PlacementUtils.countExtra(4, 0.1F, 1)));
        register(context, SCARLET_FOREST, configuredFeatures.getOrThrow(ConfiguredFeatureInit.SCARLET_FOREST), VegetationPlacements.treePlacement(PlacementUtils.countExtra(6, 0.1F, 2)));
        register(context, DEAD_LUNAR_TREE, configuredFeatures.getOrThrow(ConfiguredFeatureInit.DEAD_LUNAR_TREE), RarityFilter.onAverageOnceEvery(15), InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP_WORLD_SURFACE, BiomeFilter.biome());
        register(context, FOREST_GRASS, configuredFeatures.getOrThrow(ConfiguredFeatureInit.FOREST_GRASS), InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP_WORLD_SURFACE, BiomeFilter.biome());
        register(context, SWAMP_GRASS, configuredFeatures.getOrThrow(ConfiguredFeatureInit.SWAMP_GRASS), InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP_WORLD_SURFACE, BiomeFilter.biome());
        register(context, PERMAFROST_FOREST_GRASS, configuredFeatures.getOrThrow(ConfiguredFeatureInit.PERMAFROST_FOREST_GRASS), InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP_WORLD_SURFACE, BiomeFilter.biome());
        register(context, SCARLET_FOREST_GRASS, configuredFeatures.getOrThrow(ConfiguredFeatureInit.SCARLET_FOREST_GRASS), InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP_WORLD_SURFACE, BiomeFilter.biome());
        register(context, DESERT_GRASS, configuredFeatures.getOrThrow(ConfiguredFeatureInit.DESERT_GRASS), InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP_WORLD_SURFACE, BiomeFilter.biome());
        register(context, NEAR_WATER_GRASS, configuredFeatures.getOrThrow(ConfiguredFeatureInit.NEAR_WATER_GRASS), InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP_WORLD_SURFACE, BlockPredicateFilter.forPredicate(BlockPredicate.anyOf(nearWater)), BiomeFilter.biome());
        register(context, SWAMP_WATER, configuredFeatures.getOrThrow(ConfiguredFeatureInit.SWAMP_WATER), PlacementUtils.HEIGHTMAP_WORLD_SURFACE, BiomeFilter.biome());
        register(context, HOT_SPRING, configuredFeatures.getOrThrow(ConfiguredFeatureInit.HOT_SPRING), RarityFilter.onAverageOnceEvery(50), InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP_WORLD_SURFACE, BiomeFilter.biome());

        // structure features
        register(context, CURSED_GARDEN_EXTRA_HEIGHT, configuredFeatures.getOrThrow(ConfiguredFeatureInit.CURSED_GARDEN_EXTRA_HEIGHT)); // no placement modifier
    }


    private static ResourceKey<PlacedFeature> create(String name) {
        return ResourceKey.create(Registries.PLACED_FEATURE, new ResourceLocation(EternalStarlight.MOD_ID, name));
    }

    private static void register(BootstapContext<PlacedFeature> context, ResourceKey<PlacedFeature> key, Holder<ConfiguredFeature<?, ?>> configuration,
                                 List<PlacementModifier> modifiers) {
        context.register(key, new PlacedFeature(configuration, List.copyOf(modifiers)));
    }

    private static void register(BootstapContext<PlacedFeature> context, ResourceKey<PlacedFeature> key, Holder<ConfiguredFeature<?, ?>> configuration,
                                 PlacementModifier... modifiers) {
        register(context, key, configuration, List.of(modifiers));
    }

    public static List<PlacementModifier> orePlacement(PlacementModifier modifier, PlacementModifier modifier1) {
        return List.of(modifier, InSquarePlacement.spread(), modifier1, BiomeFilter.biome());
    }
    public static List<PlacementModifier> commonOrePlacement(int count, PlacementModifier modifier) {
        return orePlacement(CountPlacement.of(count), modifier);
    }
}

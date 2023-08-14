package cn.leolezury.eternalstarlight.datagen.generator;

import cn.leolezury.eternalstarlight.EternalStarlight;
import cn.leolezury.eternalstarlight.init.BlockInit;
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
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.levelgen.VerticalAnchor;
import net.minecraft.world.level.levelgen.blockpredicates.BlockPredicate;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.heightproviders.UniformHeight;
import net.minecraft.world.level.levelgen.placement.*;

import java.util.List;

public class PlacedFeatureGenerator {
    public static final ResourceKey<PlacedFeature> STONE_SPIKE_KEY = createKey("stone_spike");
    public static final ResourceKey<PlacedFeature> STONE_ORE_KEY = createKey("stone_ore");
    public static final ResourceKey<PlacedFeature> DEEPSLATE_ORE_KEY = createKey("deepslate_ore");
    public static final ResourceKey<PlacedFeature> CHISELED_VOIDSTONE_ORE_KEY = createKey("chiseled_voidstone_ore");
    public static final ResourceKey<PlacedFeature> GLOWING_NIGHTSHADE_MUD_ORE_KEY = createKey("glowing_nightshade_mud_ore");
    public static final ResourceKey<PlacedFeature> NIGHTSHADE_DIRT_ORE_KEY = createKey("nightshade_dirt_ore");
    public static final ResourceKey<PlacedFeature> FALLEN_LUNAR_LOG_KEY = createKey("fallen_lunar_log");
    public static final ResourceKey<PlacedFeature> FALLEN_NORTHLAND_LOG_KEY = createKey("fallen_northland_log");
    public static final ResourceKey<PlacedFeature> FALLEN_STARLIGHT_MANGROVE_LOG_KEY = createKey("fallen_starlight_mangrove_log");
    public static final ResourceKey<PlacedFeature> STARLIGHT_CRYSTAL_KEY = createKey("starlight_crystal");
    public static final ResourceKey<PlacedFeature> CAVE_VINE_KEY = createKey("cave_vine");
    public static final ResourceKey<PlacedFeature> LUNAR_TREE_CHECKED_KEY = createKey("lunar_tree_checked");
    public static final ResourceKey<PlacedFeature> NORTHLAND_TREE_CHECKED_KEY = createKey("northland_tree_checked");
    public static final ResourceKey<PlacedFeature> STARLIGHT_MANGROVE_TREE_CHECKED_KEY = createKey("starlight_mangrove_tree_checked");
    public static final ResourceKey<PlacedFeature> HUGE_GLOWING_MUSHROOM_CHECKED_KEY = createKey("huge_glowing_mushroom_checked");
    public static final ResourceKey<PlacedFeature> NORTHLAND_ON_SNOW_KEY = createKey("northland_on_snow");
    public static final ResourceKey<PlacedFeature> SL_FOREST_KEY = createKey("sl_forest");
    public static final ResourceKey<PlacedFeature> SL_DENSE_FOREST_KEY = createKey("sl_dense_forest");
    public static final ResourceKey<PlacedFeature> SL_SWAMP_FOREST_KEY = createKey("sl_swamp_forest");
    public static final ResourceKey<PlacedFeature> SL_PERMAFROST_FOREST_KEY = createKey("sl_permafrost_forest");
    public static final ResourceKey<PlacedFeature> STARLIGHT_FLOWER_KEY = createKey("starlight_flower");
    public static final ResourceKey<PlacedFeature> SL_GRASS_KEY = createKey("sl_grass");
    public static final ResourceKey<PlacedFeature> SWAMP_GRASS_KEY = createKey("swamp_grass");
    public static final ResourceKey<PlacedFeature> SWAMP_LAKE_KEY = createKey("swamp_lake");
    public static final ResourceKey<PlacedFeature> SWAMP_WATER_KEY = createKey("swamp_water");
    public static final ResourceKey<PlacedFeature> CAVE_SPRING_KEY = createKey("cave_spring");
    public static final ResourceKey<PlacedFeature> SWAMP_SILVER_ORE_KEY = createKey("swamp_silver_ore");

    //structure features
    public static final ResourceKey<PlacedFeature> CURSED_GARDEN_EXTRA_HEIGHT_KEY = createKey("cursed_garden_extra_height");

    public static void bootstrap(BootstapContext<PlacedFeature> context) {
        HolderGetter<ConfiguredFeature<?, ?>> configuredFeatures = context.lookup(Registries.CONFIGURED_FEATURE);
        BlockPredicate snowPredicate = BlockPredicate.matchesBlocks(Direction.DOWN.getNormal(), Blocks.SNOW_BLOCK, Blocks.POWDER_SNOW);
        List<PlacementModifier> onSnow = List.of(EnvironmentScanPlacement.scanningFor(Direction.UP, BlockPredicate.not(BlockPredicate.matchesBlocks(Blocks.POWDER_SNOW)), 8), BlockPredicateFilter.forPredicate(snowPredicate));

        register(context, STONE_SPIKE_KEY, configuredFeatures.getOrThrow(ConfiguredFeatureGenerator.STONE_SPIKE_KEY), RarityFilter.onAverageOnceEvery(10), CountPlacement.of(3), InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP);
        register(context, STONE_ORE_KEY, configuredFeatures.getOrThrow(ConfiguredFeatureGenerator.STONE_ORE_KEY), commonOrePlacement(2, HeightRangePlacement.uniform(VerticalAnchor.absolute(0), VerticalAnchor.absolute(128))));
        register(context, DEEPSLATE_ORE_KEY, configuredFeatures.getOrThrow(ConfiguredFeatureGenerator.DEEPSLATE_ORE_KEY), commonOrePlacement(2, HeightRangePlacement.uniform(VerticalAnchor.absolute(-64), VerticalAnchor.absolute(0))));
        register(context, CHISELED_VOIDSTONE_ORE_KEY, configuredFeatures.getOrThrow(ConfiguredFeatureGenerator.CHISELED_VOIDSTONE_ORE_KEY), commonOrePlacement(2, HeightRangePlacement.uniform(VerticalAnchor.absolute(0), VerticalAnchor.absolute(-64))));
        register(context, GLOWING_NIGHTSHADE_MUD_ORE_KEY, configuredFeatures.getOrThrow(ConfiguredFeatureGenerator.GLOWING_NIGHTSHADE_MUD_ORE_KEY), commonOrePlacement(7, HeightRangePlacement.uniform(VerticalAnchor.absolute(0), VerticalAnchor.absolute(160))));
        register(context, NIGHTSHADE_DIRT_ORE_KEY, configuredFeatures.getOrThrow(ConfiguredFeatureGenerator.NIGHTSHADE_DIRT_ORE_KEY), commonOrePlacement(7, HeightRangePlacement.uniform(VerticalAnchor.absolute(0), VerticalAnchor.absolute(160))));
        register(context, FALLEN_LUNAR_LOG_KEY, configuredFeatures.getOrThrow(ConfiguredFeatureGenerator.FALLEN_LUNAR_LOG_KEY), RarityFilter.onAverageOnceEvery(10), CountPlacement.of(2), InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP);
        register(context, FALLEN_NORTHLAND_LOG_KEY, configuredFeatures.getOrThrow(ConfiguredFeatureGenerator.FALLEN_NORTHLAND_LOG_KEY), RarityFilter.onAverageOnceEvery(10), CountPlacement.of(2), InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP);
        register(context, FALLEN_STARLIGHT_MANGROVE_LOG_KEY, configuredFeatures.getOrThrow(ConfiguredFeatureGenerator.FALLEN_STARLIGHT_MANGROVE_LOG_KEY), RarityFilter.onAverageOnceEvery(10), CountPlacement.of(2), InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP);
        register(context, STARLIGHT_CRYSTAL_KEY, configuredFeatures.getOrThrow(ConfiguredFeatureGenerator.STARLIGHT_CRYSTAL_KEY), RarityFilter.onAverageOnceEvery(10), CountPlacement.of(188), InSquarePlacement.spread(), PlacementUtils.RANGE_BOTTOM_TO_MAX_TERRAIN_HEIGHT, EnvironmentScanPlacement.scanningFor(Direction.UP, BlockPredicate.hasSturdyFace(Direction.DOWN), BlockPredicate.ONLY_IN_AIR_PREDICATE, 12), RandomOffsetPlacement.vertical(ConstantInt.of(-1)));
        register(context, CAVE_VINE_KEY, configuredFeatures.getOrThrow(ConfiguredFeatureGenerator.CAVE_VINE_KEY), CountPlacement.of(188), InSquarePlacement.spread(), PlacementUtils.RANGE_BOTTOM_TO_MAX_TERRAIN_HEIGHT, EnvironmentScanPlacement.scanningFor(Direction.UP, BlockPredicate.hasSturdyFace(Direction.DOWN), BlockPredicate.ONLY_IN_AIR_PREDICATE, 12), RandomOffsetPlacement.vertical(ConstantInt.of(-1)));
        register(context, LUNAR_TREE_CHECKED_KEY, configuredFeatures.getOrThrow(ConfiguredFeatureGenerator.LUNAR_KEY), PlacementUtils.filteredByBlockSurvival(BlockInit.LUNAR_SAPLING.get()));
        register(context, NORTHLAND_TREE_CHECKED_KEY, configuredFeatures.getOrThrow(ConfiguredFeatureGenerator.NORTHLAND_KEY), PlacementUtils.filteredByBlockSurvival(BlockInit.NORTHLAND_SAPLING.get()));
        register(context, STARLIGHT_MANGROVE_TREE_CHECKED_KEY, configuredFeatures.getOrThrow(ConfiguredFeatureGenerator.STARLIGHT_MANGROVE_KEY), PlacementUtils.filteredByBlockSurvival(BlockInit.STARLIGHT_MANGROVE_SAPLING.get()));
        register(context, HUGE_GLOWING_MUSHROOM_CHECKED_KEY, configuredFeatures.getOrThrow(ConfiguredFeatureGenerator.HUGE_GLOWING_MUSHROOM_KEY), PlacementUtils.filteredByBlockSurvival(BlockInit.GLOWING_MUSHROOM.get()));
        register(context, NORTHLAND_ON_SNOW_KEY, configuredFeatures.getOrThrow(ConfiguredFeatureGenerator.NORTHLAND_KEY), onSnow);
        register(context, SL_FOREST_KEY, configuredFeatures.getOrThrow(ConfiguredFeatureGenerator.SL_FOREST_KEY), VegetationPlacements.treePlacement(PlacementUtils.countExtra(6, 0.1F, 2)));
        register(context, SL_DENSE_FOREST_KEY, configuredFeatures.getOrThrow(ConfiguredFeatureGenerator.SL_DENSE_FOREST_KEY), VegetationPlacements.treePlacement(PlacementUtils.countExtra(15, 0.1F, 2)));
        register(context, SL_PERMAFROST_FOREST_KEY, configuredFeatures.getOrThrow(ConfiguredFeatureGenerator.SL_PERMAFROST_FOREST_KEY), VegetationPlacements.treePlacement(PlacementUtils.countExtra(6, 0.1F, 2)));
        register(context, SL_SWAMP_FOREST_KEY, configuredFeatures.getOrThrow(ConfiguredFeatureGenerator.SL_SWAMP_FOREST_KEY), VegetationPlacements.treePlacement(PlacementUtils.countExtra(4, 0.1F, 1)));
        register(context, STARLIGHT_FLOWER_KEY, configuredFeatures.getOrThrow(ConfiguredFeatureGenerator.STARLIGHT_FLOWER_KEY), RarityFilter.onAverageOnceEvery(8), InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP);
        register(context, SL_GRASS_KEY, configuredFeatures.getOrThrow(ConfiguredFeatureGenerator.SL_GRASS_KEY), InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP_WORLD_SURFACE);
        register(context, SWAMP_GRASS_KEY, configuredFeatures.getOrThrow(ConfiguredFeatureGenerator.SWAMP_GRASS_KEY), InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP_WORLD_SURFACE);
        register(context, SWAMP_LAKE_KEY, configuredFeatures.getOrThrow(ConfiguredFeatureGenerator.SWAMP_LAKE_KEY), RarityFilter.onAverageOnceEvery(4), InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP_WORLD_SURFACE);
        register(context, SWAMP_WATER_KEY, configuredFeatures.getOrThrow(ConfiguredFeatureGenerator.SWAMP_WATER_KEY), PlacementUtils.HEIGHTMAP_WORLD_SURFACE);
        register(context, CAVE_SPRING_KEY, configuredFeatures.getOrThrow(ConfiguredFeatureGenerator.CAVE_SPRING_KEY), RarityFilter.onAverageOnceEvery(8), InSquarePlacement.spread(), HeightRangePlacement.of(UniformHeight.of(VerticalAnchor.absolute(0), VerticalAnchor.top())), EnvironmentScanPlacement.scanningFor(Direction.DOWN, BlockPredicate.allOf(BlockPredicate.not(BlockPredicate.ONLY_IN_AIR_PREDICATE), BlockPredicate.insideWorld(new BlockPos(0, -5, 0))), 32), SurfaceRelativeThresholdFilter.of(Heightmap.Types.OCEAN_FLOOR_WG, Integer.MIN_VALUE, -5));
        register(context, SWAMP_SILVER_ORE_KEY, configuredFeatures.getOrThrow(ConfiguredFeatureGenerator.SWAMP_SILVER_ORE_KEY), commonOrePlacement(30, HeightRangePlacement.uniform(VerticalAnchor.absolute(0), VerticalAnchor.top())));

        // structure features
        register(context, CURSED_GARDEN_EXTRA_HEIGHT_KEY, configuredFeatures.getOrThrow(ConfiguredFeatureGenerator.CURSED_GARDEN_EXTRA_HEIGHT_KEY)); // no placement modifier
    }


    private static ResourceKey<PlacedFeature> createKey(String name) {
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

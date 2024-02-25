package cn.leolezury.eternalstarlight.common.data;

import cn.leolezury.eternalstarlight.common.EternalStarlight;
import cn.leolezury.eternalstarlight.common.block.CaveMossBlock;
import cn.leolezury.eternalstarlight.common.registry.ESBlocks;
import cn.leolezury.eternalstarlight.common.registry.ESFeatures;
import cn.leolezury.eternalstarlight.common.util.ESTags;
import cn.leolezury.eternalstarlight.common.world.gen.feature.AbysslatePatchFeature;
import cn.leolezury.eternalstarlight.common.world.gen.feature.ESLakeFeature;
import cn.leolezury.eternalstarlight.common.world.gen.feature.FallenLogFeature;
import cn.leolezury.eternalstarlight.common.world.gen.feature.LeavesPileFeature;
import cn.leolezury.eternalstarlight.common.world.gen.feature.tree.decorator.TorreyaVinesDecorator;
import cn.leolezury.eternalstarlight.common.world.gen.feature.tree.decorator.TrunkBerriesDecorator;
import cn.leolezury.eternalstarlight.common.world.gen.feature.tree.decorator.TrunkCobwebDecorator;
import cn.leolezury.eternalstarlight.common.world.gen.feature.tree.foliage.ScarletFoliagePlacer;
import cn.leolezury.eternalstarlight.common.world.gen.feature.tree.foliage.SpheroidFoliagePlacer;
import cn.leolezury.eternalstarlight.common.world.gen.feature.tree.foliage.TorreyaFoliagePlacer;
import cn.leolezury.eternalstarlight.common.world.gen.feature.tree.trunk.BranchingTrunkPlacer;
import net.minecraft.core.Direction;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.HolderSet;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.data.worldgen.features.FeatureUtils;
import net.minecraft.data.worldgen.placement.PlacementUtils;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.random.SimpleWeightedRandomList;
import net.minecraft.util.valueproviders.ConstantInt;
import net.minecraft.util.valueproviders.IntProvider;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.util.valueproviders.WeightedListInt;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.blockpredicates.BlockPredicate;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.WeightedPlacedFeature;
import net.minecraft.world.level.levelgen.feature.configurations.*;
import net.minecraft.world.level.levelgen.feature.featuresize.TwoLayersFeatureSize;
import net.minecraft.world.level.levelgen.feature.foliageplacers.MegaPineFoliagePlacer;
import net.minecraft.world.level.levelgen.feature.foliageplacers.RandomSpreadFoliagePlacer;
import net.minecraft.world.level.levelgen.feature.rootplacers.AboveRootPlacement;
import net.minecraft.world.level.levelgen.feature.rootplacers.MangroveRootPlacement;
import net.minecraft.world.level.levelgen.feature.rootplacers.MangroveRootPlacer;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;
import net.minecraft.world.level.levelgen.feature.stateproviders.RandomizedIntStateProvider;
import net.minecraft.world.level.levelgen.feature.stateproviders.WeightedStateProvider;
import net.minecraft.world.level.levelgen.feature.treedecorators.AttachedToLeavesDecorator;
import net.minecraft.world.level.levelgen.feature.treedecorators.LeaveVineDecorator;
import net.minecraft.world.level.levelgen.feature.trunkplacers.GiantTrunkPlacer;
import net.minecraft.world.level.levelgen.feature.trunkplacers.StraightTrunkPlacer;
import net.minecraft.world.level.levelgen.feature.trunkplacers.UpwardsBranchingTrunkPlacer;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import net.minecraft.world.level.levelgen.structure.templatesystem.BlockMatchTest;
import net.minecraft.world.level.levelgen.structure.templatesystem.RuleTest;
import net.minecraft.world.level.levelgen.structure.templatesystem.TagMatchTest;

import java.util.List;
import java.util.Optional;

public class ESConfiguredFeatures {
    public static final ResourceKey<ConfiguredFeature<?, ?>> ETHER_FLUID_BORDER = create("ether_fluid_border");
    public static final ResourceKey<ConfiguredFeature<?, ?>> STONE_SPIKE = create("stone_spike");
    public static final ResourceKey<ConfiguredFeature<?, ?>> STONE_ORE = create("stone_ore");
    public static final ResourceKey<ConfiguredFeature<?, ?>> DEEPSLATE_ORE = create("deepslate_ore");
    public static final ResourceKey<ConfiguredFeature<?, ?>> GLOWING_GRIMSTONE_ORE = create("glowing_grimstone_ore");
    public static final ResourceKey<ConfiguredFeature<?, ?>> GLOWING_VOIDSTONE_ORE = create("glowing_voidstone_ore");
    public static final ResourceKey<ConfiguredFeature<?, ?>> GLOWING_NIGHTSHADE_MUD_ORE = create("glowing_nightshade_mud_ore");
    public static final ResourceKey<ConfiguredFeature<?, ?>> NIGHTSHADE_DIRT_ORE = create("nightshade_dirt_ore");
    public static final ResourceKey<ConfiguredFeature<?, ?>> SWAMP_SILVER_ORE = create("swamp_silver_ore");
    public static final ResourceKey<ConfiguredFeature<?, ?>> GRIMSTONE_REDSTONE_ORE = create("grimstone_redstone_ore");
    public static final ResourceKey<ConfiguredFeature<?, ?>> VOIDSTONE_REDSTONE_ORE = create("voidstone_redstone_ore");
    public static final ResourceKey<ConfiguredFeature<?, ?>> GRIMSTONE_SALTPETER_ORE = create("grimstone_saltpeter_ore");
    public static final ResourceKey<ConfiguredFeature<?, ?>> VOIDSTONE_SALTPETER_ORE = create("voidstone_saltpeter_ore");
    public static final ResourceKey<ConfiguredFeature<?, ?>> FALLEN_LUNAR_LOG = create("fallen_lunar_log");
    public static final ResourceKey<ConfiguredFeature<?, ?>> FALLEN_NORTHLAND_LOG = create("fallen_northland_log");
    public static final ResourceKey<ConfiguredFeature<?, ?>> FALLEN_STARLIGHT_MANGROVE_LOG = create("fallen_starlight_mangrove_log");
    public static final ResourceKey<ConfiguredFeature<?, ?>> FALLEN_SCARLET_LOG = create("fallen_scarlet_log");
    public static final ResourceKey<ConfiguredFeature<?, ?>> SCARLET_LEAVES_PILE = create("scarlet_leaves_pile");
    public static final ResourceKey<ConfiguredFeature<?, ?>> STARLIGHT_CRYSTAL = create("starlight_crystal");
    public static final ResourceKey<ConfiguredFeature<?, ?>> CAVE_VINE = create("cave_vine");
    public static final ResourceKey<ConfiguredFeature<?, ?>> CAVE_MOSS = create("cave_moss");
    public static final ResourceKey<ConfiguredFeature<?, ?>> CAVE_MOSS_VEIN = create("cave_moss_vein");
    public static final ResourceKey<ConfiguredFeature<?, ?>> ABYSSAL_KELP = create("abyssal_kelp");
    public static final ResourceKey<ConfiguredFeature<?, ?>> OCEAN_VEGETATION = create("ocean_vegetation");
    public static final ResourceKey<ConfiguredFeature<?, ?>> ABYSSLATE_PATCH = create("abysslate_patch");
    public static final ResourceKey<ConfiguredFeature<?, ?>> THERMABYSSLATE_PATCH = create("thermabysslate_patch");
    public static final ResourceKey<ConfiguredFeature<?, ?>> CRYOBYSSLATE_PATCH = create("cryobysslate_patch");
    public static final ResourceKey<ConfiguredFeature<?, ?>> LUNAR = create("lunar");
    public static final ResourceKey<ConfiguredFeature<?, ?>> LUNAR_HUGE = create("lunar_huge");
    public static final ResourceKey<ConfiguredFeature<?, ?>> NORTHLAND = create("northland");
    public static final ResourceKey<ConfiguredFeature<?, ?>> STARLIGHT_MANGROVE = create("starlight_mangrove");
    public static final ResourceKey<ConfiguredFeature<?, ?>> SCARLET = create("scarlet");
    public static final ResourceKey<ConfiguredFeature<?, ?>> TORREYA = create("torreya");
    public static final ResourceKey<ConfiguredFeature<?, ?>> HUGE_GLOWING_MUSHROOM = create("huge_glowing_mushroom");
    public static final ResourceKey<ConfiguredFeature<?, ?>> STARLIGHT_FOREST = create("starlight_forest");
    public static final ResourceKey<ConfiguredFeature<?, ?>> DENSE_FOREST = create("dense_forest");
    public static final ResourceKey<ConfiguredFeature<?, ?>> SWAMP_FOREST = create("swamp_forest");
    public static final ResourceKey<ConfiguredFeature<?, ?>> PERMAFROST_FOREST = create("permafrost_forest");
    public static final ResourceKey<ConfiguredFeature<?, ?>> SCARLET_FOREST = create("scarlet_forest");
    public static final ResourceKey<ConfiguredFeature<?, ?>> TORREYA_FOREST = create("torreya_forest");
    public static final ResourceKey<ConfiguredFeature<?, ?>> DEAD_LUNAR_TREE = create("dead_lunar_tree");
    public static final ResourceKey<ConfiguredFeature<?, ?>> FOREST_GRASS = create("forest_grass");
    public static final ResourceKey<ConfiguredFeature<?, ?>> SWAMP_GRASS = create("swamp_grass");
    public static final ResourceKey<ConfiguredFeature<?, ?>> PERMAFROST_FOREST_GRASS = create("permafrost_forest_grass");
    public static final ResourceKey<ConfiguredFeature<?, ?>> SCARLET_FOREST_GRASS = create("scarlet_forest_grass");
    public static final ResourceKey<ConfiguredFeature<?, ?>> TORREYA_FOREST_GRASS = create("torreya_forest_grass");
    public static final ResourceKey<ConfiguredFeature<?, ?>> DESERT_GRASS = create("desert_grass");
    public static final ResourceKey<ConfiguredFeature<?, ?>> NEAR_WATER_GRASS = create("near_water_grass");
    public static final ResourceKey<ConfiguredFeature<?, ?>> ON_WATER_PLANT = create("on_water_plant");
    public static final ResourceKey<ConfiguredFeature<?, ?>> CRYSTAL_CAVES_GRASS = create("crystal_caves_grass");
    public static final ResourceKey<ConfiguredFeature<?, ?>> SWAMP_WATER = create("swamp_water");
    public static final ResourceKey<ConfiguredFeature<?, ?>> HOT_SPRING = create("hot_spring");

    // structure features
    public static final ResourceKey<ConfiguredFeature<?, ?>> GOLEM_FORGE_CHIMNEY = create("golem_forge_chimney");
    public static final ResourceKey<ConfiguredFeature<?, ?>> CURSED_GARDEN_EXTRA_HEIGHT = create("cursed_garden_extra_height");

    public static void bootstrap(BootstapContext<ConfiguredFeature<?, ?>> context) {
        HolderGetter<Block> blockHolderGetter = context.lookup(Registries.BLOCK);
        HolderGetter<PlacedFeature> placedFeatureHolderGetter = context.lookup(Registries.PLACED_FEATURE);

        RuleTest slRule = new TagMatchTest(ESTags.Blocks.BASE_STONE_STARLIGHT);
        RuleTest grimstoneRule = new BlockMatchTest(ESBlocks.GRIMSTONE.get());
        RuleTest voidstoneRule = new BlockMatchTest(ESBlocks.VOIDSTONE.get());
        RuleTest mudRule = new BlockMatchTest(ESBlocks.NIGHTSHADE_MUD.get());

        FeatureUtils.register(context, ETHER_FLUID_BORDER, ESFeatures.ETHER_FLUID_BORDER.get(), new NoneFeatureConfiguration());
        FeatureUtils.register(context, STONE_SPIKE, ESFeatures.STONE_SPIKE.get(), new NoneFeatureConfiguration());
        FeatureUtils.register(context, STONE_ORE, Feature.ORE, new OreConfiguration(slRule, Blocks.STONE.defaultBlockState(), 64));
        FeatureUtils.register(context, DEEPSLATE_ORE, Feature.ORE, new OreConfiguration(slRule, Blocks.DEEPSLATE.defaultBlockState(), 64));
        FeatureUtils.register(context, GLOWING_GRIMSTONE_ORE, Feature.ORE, new OreConfiguration(grimstoneRule, ESBlocks.GLOWING_GRIMSTONE.get().defaultBlockState(), 20));
        FeatureUtils.register(context, GLOWING_VOIDSTONE_ORE, Feature.ORE, new OreConfiguration(voidstoneRule, ESBlocks.GLOWING_VOIDSTONE.get().defaultBlockState(), 20));
        FeatureUtils.register(context, GLOWING_NIGHTSHADE_MUD_ORE, Feature.ORE, new OreConfiguration(mudRule, ESBlocks.GLOWING_NIGHTSHADE_MUD.get().defaultBlockState(), 20));
        FeatureUtils.register(context, NIGHTSHADE_DIRT_ORE, Feature.ORE, new OreConfiguration(slRule, ESBlocks.NIGHTSHADE_DIRT.get().defaultBlockState(), 33));
        FeatureUtils.register(context, SWAMP_SILVER_ORE, Feature.ORE, new OreConfiguration(mudRule, ESBlocks.SWAMP_SILVER_ORE.get().defaultBlockState(), 7));
        FeatureUtils.register(context, GRIMSTONE_REDSTONE_ORE, Feature.ORE, new OreConfiguration(grimstoneRule, ESBlocks.GRIMSTONE_REDSTONE_ORE.get().defaultBlockState(), 7));
        FeatureUtils.register(context, VOIDSTONE_REDSTONE_ORE, Feature.ORE, new OreConfiguration(voidstoneRule, ESBlocks.VOIDSTONE_REDSTONE_ORE.get().defaultBlockState(), 7));
        FeatureUtils.register(context, GRIMSTONE_SALTPETER_ORE, Feature.ORE, new OreConfiguration(grimstoneRule, ESBlocks.GRIMSTONE_SALTPETER_ORE.get().defaultBlockState(), 20));
        FeatureUtils.register(context, VOIDSTONE_SALTPETER_ORE, Feature.ORE, new OreConfiguration(voidstoneRule, ESBlocks.VOIDSTONE_SALTPETER_ORE.get().defaultBlockState(), 20));
        FeatureUtils.register(context, FALLEN_LUNAR_LOG, ESFeatures.FALLEN_LOG.get(), new FallenLogFeature.Configuration(BlockStateProvider.simple(ESBlocks.LUNAR_LOG.get())));
        FeatureUtils.register(context, FALLEN_NORTHLAND_LOG, ESFeatures.FALLEN_LOG.get(), new FallenLogFeature.Configuration(BlockStateProvider.simple(ESBlocks.NORTHLAND_LOG.get())));
        FeatureUtils.register(context, FALLEN_STARLIGHT_MANGROVE_LOG, ESFeatures.FALLEN_LOG.get(), new FallenLogFeature.Configuration(BlockStateProvider.simple(ESBlocks.STARLIGHT_MANGROVE_LOG.get())));
        FeatureUtils.register(context, FALLEN_SCARLET_LOG, ESFeatures.FALLEN_LOG.get(), new FallenLogFeature.Configuration(BlockStateProvider.simple(ESBlocks.SCARLET_LOG.get())));
        FeatureUtils.register(context, SCARLET_LEAVES_PILE, ESFeatures.LEAVES_PILE.get(), new LeavesPileFeature.Configuration(BlockStateProvider.simple(ESBlocks.SCARLET_LEAVES_PILE.get())));
        FeatureUtils.register(context, STARLIGHT_CRYSTAL, ESFeatures.STARLIGHT_CRYSTAL.get(), new NoneFeatureConfiguration());
        FeatureUtils.register(context, CAVE_VINE, Feature.BLOCK_COLUMN, new BlockColumnConfiguration(List.of(BlockColumnConfiguration.layer(new WeightedListInt(SimpleWeightedRandomList.<IntProvider>builder().add(UniformInt.of(0, 19), 2).add(UniformInt.of(0, 2), 3).add(UniformInt.of(0, 6), 10).build()),
                new WeightedStateProvider(SimpleWeightedRandomList.<BlockState>builder().add(ESBlocks.BERRIES_VINES_PLANT.get().defaultBlockState(), 4).add(ESBlocks.BERRIES_VINES_PLANT.get().defaultBlockState().setValue(CaveVines.BERRIES, Boolean.valueOf(true)), 1))), BlockColumnConfiguration.layer(ConstantInt.of(1),
                new RandomizedIntStateProvider(new WeightedStateProvider(SimpleWeightedRandomList.<BlockState>builder().add(ESBlocks.BERRIES_VINES.get().defaultBlockState(), 4).add(ESBlocks.BERRIES_VINES.get().defaultBlockState().setValue(CaveVines.BERRIES, Boolean.valueOf(true)), 1)), CaveVinesBlock.AGE, UniformInt.of(23, 25)))), Direction.DOWN, BlockPredicate.ONLY_IN_AIR_PREDICATE, true));
        FeatureUtils.register(context, CAVE_MOSS, Feature.BLOCK_COLUMN, new BlockColumnConfiguration(List.of(BlockColumnConfiguration.layer(new WeightedListInt(SimpleWeightedRandomList.<IntProvider>builder().add(UniformInt.of(10, 14), 1).add(UniformInt.of(6, 10), 2).add(UniformInt.of(0, 6), 6).build()),
                BlockStateProvider.simple(ESBlocks.CAVE_MOSS_PLANT.get())), BlockColumnConfiguration.layer(ConstantInt.of(1),
                new RandomizedIntStateProvider(BlockStateProvider.simple(ESBlocks.CAVE_MOSS.get()), CaveMossBlock.AGE, UniformInt.of(23, 25)))), Direction.DOWN, BlockPredicate.ONLY_IN_AIR_PREDICATE, true));
        FeatureUtils.register(context, CAVE_MOSS_VEIN, Feature.MULTIFACE_GROWTH, new MultifaceGrowthConfiguration((MultifaceBlock) ESBlocks.CAVE_MOSS_VEIN.get(), 20, false, true, true, 0.5F, HolderSet.direct(ESBlocks.GRIMSTONE.asHolder(), ESBlocks.VOIDSTONE.asHolder())));
        FeatureUtils.register(context, ABYSSAL_KELP, ESFeatures.KELP.get(), new NoneFeatureConfiguration());
        FeatureUtils.register(context, OCEAN_VEGETATION, Feature.SIMPLE_RANDOM_SELECTOR, new SimpleRandomFeatureConfiguration(HolderSet.direct(PlacementUtils.inlinePlaced(ESFeatures.CORAL_TREE.get(), FeatureConfiguration.NONE), PlacementUtils.inlinePlaced(ESFeatures.CORAL_CLAW.get(), FeatureConfiguration.NONE), PlacementUtils.inlinePlaced(ESFeatures.CORAL_MUSHROOM.get(), FeatureConfiguration.NONE))));
        FeatureUtils.register(context, ABYSSLATE_PATCH, ESFeatures.ABYSSLATE_PATCH.get(), new AbysslatePatchFeature.Configuration(ESBlocks.ABYSSLATE.get(), BlockStateProvider.simple(ESBlocks.ABYSSAL_MAGMA_BLOCK.get()), BlockStateProvider.simple(ESBlocks.ABYSSAL_GEYSER.get())));
        FeatureUtils.register(context, THERMABYSSLATE_PATCH, ESFeatures.ABYSSLATE_PATCH.get(), new AbysslatePatchFeature.Configuration(ESBlocks.THERMABYSSLATE.get(), BlockStateProvider.simple(ESBlocks.THERMABYSSAL_MAGMA_BLOCK.get()), BlockStateProvider.simple(ESBlocks.THERMABYSSAL_GEYSER.get())));
        FeatureUtils.register(context, CRYOBYSSLATE_PATCH, ESFeatures.ABYSSLATE_PATCH.get(), new AbysslatePatchFeature.Configuration(ESBlocks.CRYOBYSSLATE.get(), BlockStateProvider.simple(ESBlocks.CRYOBYSSAL_MAGMA_BLOCK.get()), BlockStateProvider.simple(ESBlocks.CRYOBYSSAL_GEYSER.get())));
        FeatureUtils.register(context, LUNAR, Feature.TREE, new TreeConfiguration.TreeConfigurationBuilder(BlockStateProvider.simple(ESBlocks.LUNAR_LOG.get()), new BranchingTrunkPlacer(8, 2, 10, ConstantInt.of(0), UniformInt.of(4, 5), ConstantInt.of(1), UniformInt.of(3, 4)), BlockStateProvider.simple(ESBlocks.LUNAR_LEAVES.get()), new SpheroidFoliagePlacer(ConstantInt.of(2), ConstantInt.of(0)), new TwoLayersFeatureSize(4, 1, 1)).decorators(List.of(new TrunkBerriesDecorator())).build());
        FeatureUtils.register(context, LUNAR_HUGE, Feature.TREE, new TreeConfiguration.TreeConfigurationBuilder(BlockStateProvider.simple(ESBlocks.LUNAR_LOG.get()), new BranchingTrunkPlacer(32, 2, 10, UniformInt.of(3, 4), UniformInt.of(2, 4)), BlockStateProvider.simple(ESBlocks.LUNAR_LEAVES.get()), new SpheroidFoliagePlacer(ConstantInt.of(3), ConstantInt.of(0)), new TwoLayersFeatureSize(4, 1, 1)).decorators(List.of(new TrunkBerriesDecorator())).build());
        FeatureUtils.register(context, NORTHLAND, Feature.TREE, new TreeConfiguration.TreeConfigurationBuilder(BlockStateProvider.simple(ESBlocks.NORTHLAND_LOG.get()), new GiantTrunkPlacer(10, 2, 10), BlockStateProvider.simple(ESBlocks.NORTHLAND_LEAVES.get()), new MegaPineFoliagePlacer(ConstantInt.of(0), ConstantInt.of(0), UniformInt.of(13, 17)), new TwoLayersFeatureSize(1, 1, 2)).build());
        FeatureUtils.register(context, STARLIGHT_MANGROVE, Feature.TREE, (new TreeConfiguration.TreeConfigurationBuilder(BlockStateProvider.simple(ESBlocks.STARLIGHT_MANGROVE_LOG.get()), new UpwardsBranchingTrunkPlacer(4, 1, 9, UniformInt.of(1, 6), 0.5F, UniformInt.of(0, 1), blockHolderGetter.getOrThrow(BlockTags.MANGROVE_LOGS_CAN_GROW_THROUGH)), BlockStateProvider.simple(ESBlocks.STARLIGHT_MANGROVE_LEAVES.get()), new RandomSpreadFoliagePlacer(ConstantInt.of(3), ConstantInt.of(0), ConstantInt.of(2), 70), Optional.of(new MangroveRootPlacer(UniformInt.of(3, 7), BlockStateProvider.simple(ESBlocks.STARLIGHT_MANGROVE_ROOTS.get()), Optional.of(new AboveRootPlacement(BlockStateProvider.simple(Blocks.MOSS_CARPET), 0.5F)), new MangroveRootPlacement(blockHolderGetter.getOrThrow(BlockTags.MANGROVE_ROOTS_CAN_GROW_THROUGH), HolderSet.direct(Block::builtInRegistryHolder, ESBlocks.NIGHTSHADE_MUD.get(), ESBlocks.MUDDY_STARLIGHT_MANGROVE_ROOTS.get()), BlockStateProvider.simple(ESBlocks.MUDDY_STARLIGHT_MANGROVE_ROOTS.get()), 8, 15, 0.2F))), new TwoLayersFeatureSize(3, 0, 2))).decorators(List.of(new LeaveVineDecorator(0.125F), new AttachedToLeavesDecorator(0.14F, 1, 0, new RandomizedIntStateProvider(BlockStateProvider.simple(Blocks.MANGROVE_PROPAGULE.defaultBlockState().setValue(MangrovePropaguleBlock.HANGING, Boolean.valueOf(true))), MangrovePropaguleBlock.AGE, UniformInt.of(0, 4)), 2, List.of(Direction.DOWN)))).ignoreVines().build());
        FeatureUtils.register(context, SCARLET, Feature.TREE, new TreeConfiguration.TreeConfigurationBuilder(BlockStateProvider.simple(ESBlocks.SCARLET_LOG.get()), new StraightTrunkPlacer(10, 2, 10), BlockStateProvider.simple(ESBlocks.SCARLET_LEAVES.get()), new ScarletFoliagePlacer(UniformInt.of(2, 3), ConstantInt.of(0)), new TwoLayersFeatureSize(4, 1, 1)).decorators(List.of(new TrunkCobwebDecorator())).build());
        FeatureUtils.register(context, TORREYA, Feature.TREE, new TreeConfiguration.TreeConfigurationBuilder(BlockStateProvider.simple(ESBlocks.TORREYA_LOG.get()), new BranchingTrunkPlacer(32, 2, 10, UniformInt.of(3, 4), UniformInt.of(2, 4)), BlockStateProvider.simple(ESBlocks.TORREYA_LEAVES.get().defaultBlockState().setValue(LeavesBlock.PERSISTENT, true)), new TorreyaFoliagePlacer(ConstantInt.of(5), ConstantInt.of(0)), new TwoLayersFeatureSize(4, 1, 1)).decorators(List.of(new TorreyaVinesDecorator())).build());
        FeatureUtils.register(context, HUGE_GLOWING_MUSHROOM, ESFeatures.HUGE_GLOWING_MUSHROOM.get(), new HugeMushroomFeatureConfiguration(BlockStateProvider.simple(ESBlocks.GLOWING_MUSHROOM_BLOCK.get().defaultBlockState()), BlockStateProvider.simple(ESBlocks.GLOWING_MUSHROOM_STEM.get().defaultBlockState().setValue(HugeMushroomBlock.UP, false).setValue(HugeMushroomBlock.DOWN, false)), 3));
        FeatureUtils.register(context, STARLIGHT_FOREST, Feature.RANDOM_SELECTOR, new RandomFeatureConfiguration(List.of(new WeightedPlacedFeature(placedFeatureHolderGetter.getOrThrow(ESPlacedFeatures.HUGE_GLOWING_MUSHROOM_CHECKED), 0.01F), new WeightedPlacedFeature(placedFeatureHolderGetter.getOrThrow(ESPlacedFeatures.LUNAR_TREE_CHECKED), 0.04F), new WeightedPlacedFeature(placedFeatureHolderGetter.getOrThrow(ESPlacedFeatures.LUNAR_HUGE_TREE_CHECKED), 0.95F)), placedFeatureHolderGetter.getOrThrow(ESPlacedFeatures.LUNAR_TREE_CHECKED)));
        FeatureUtils.register(context, DENSE_FOREST, Feature.RANDOM_SELECTOR, new RandomFeatureConfiguration(List.of(new WeightedPlacedFeature(placedFeatureHolderGetter.getOrThrow(ESPlacedFeatures.LUNAR_TREE_CHECKED), 0.9F), new WeightedPlacedFeature(placedFeatureHolderGetter.getOrThrow(ESPlacedFeatures.HUGE_GLOWING_MUSHROOM_CHECKED), 0.1F)), placedFeatureHolderGetter.getOrThrow(ESPlacedFeatures.LUNAR_TREE_CHECKED)));
        FeatureUtils.register(context, SWAMP_FOREST, Feature.RANDOM_SELECTOR, new RandomFeatureConfiguration(List.of(new WeightedPlacedFeature(placedFeatureHolderGetter.getOrThrow(ESPlacedFeatures.STARLIGHT_MANGROVE_TREE_CHECKED), 0.9F), new WeightedPlacedFeature(placedFeatureHolderGetter.getOrThrow(ESPlacedFeatures.HUGE_GLOWING_MUSHROOM_CHECKED), 0.1F)), placedFeatureHolderGetter.getOrThrow(ESPlacedFeatures.STARLIGHT_MANGROVE_TREE_CHECKED)));
        FeatureUtils.register(context, PERMAFROST_FOREST, Feature.RANDOM_SELECTOR, new RandomFeatureConfiguration(List.of(new WeightedPlacedFeature(placedFeatureHolderGetter.getOrThrow(ESPlacedFeatures.NORTHLAND_TREE_CHECKED), 0.25F), new WeightedPlacedFeature(placedFeatureHolderGetter.getOrThrow(ESPlacedFeatures.NORTHLAND_ON_SNOW), 0.25F)), placedFeatureHolderGetter.getOrThrow(ESPlacedFeatures.NORTHLAND_TREE_CHECKED)));
        FeatureUtils.register(context, SCARLET_FOREST, Feature.RANDOM_SELECTOR, new RandomFeatureConfiguration(List.of(new WeightedPlacedFeature(placedFeatureHolderGetter.getOrThrow(ESPlacedFeatures.SCARLET_TREE_CHECKED), 0.75F), new WeightedPlacedFeature(placedFeatureHolderGetter.getOrThrow(ESPlacedFeatures.HUGE_GLOWING_MUSHROOM_CHECKED), 0.1F)), placedFeatureHolderGetter.getOrThrow(ESPlacedFeatures.SCARLET_TREE_CHECKED)));
        FeatureUtils.register(context, TORREYA_FOREST, Feature.RANDOM_SELECTOR, new RandomFeatureConfiguration(List.of(new WeightedPlacedFeature(placedFeatureHolderGetter.getOrThrow(ESPlacedFeatures.TORREYA_TREE_CHECKED), 0.25F)), placedFeatureHolderGetter.getOrThrow(ESPlacedFeatures.TORREYA_TREE_CHECKED)));
        FeatureUtils.register(context, DEAD_LUNAR_TREE, ESFeatures.DEAD_LUNAR_TREE.get(), new NoneFeatureConfiguration());
        FeatureUtils.register(context, FOREST_GRASS, Feature.RANDOM_PATCH, grassPatch(new WeightedStateProvider(SimpleWeightedRandomList.<BlockState>builder().add(ESBlocks.NIGHT_SPROUTS.get().defaultBlockState(), 2).add(ESBlocks.SMALL_NIGHT_SPROUTS.get().defaultBlockState(), 2).add(ESBlocks.GLOWING_NIGHT_SPROUTS.get().defaultBlockState(), 2).add(ESBlocks.SMALL_GLOWING_NIGHT_SPROUTS.get().defaultBlockState(), 2).add(ESBlocks.LUNAR_GRASS.get().defaultBlockState(), 2).add(ESBlocks.GLOWING_LUNAR_GRASS.get().defaultBlockState(), 2).add(ESBlocks.CRESCENT_GRASS.get().defaultBlockState(), 2).add(ESBlocks.GLOWING_CRESCENT_GRASS.get().defaultBlockState(), 2).add(ESBlocks.PARASOL_GRASS.get().defaultBlockState(), 2).add(ESBlocks.GLOWING_PARASOL_GRASS.get().defaultBlockState(), 2).add(ESBlocks.LUNAR_BUSH.get().defaultBlockState(), 2).add(ESBlocks.GLOWING_LUNAR_BUSH.get().defaultBlockState(), 2).add(ESBlocks.TALL_CRESCENT_GRASS.get().defaultBlockState(), 1).add(ESBlocks.TALL_GLOWING_CRESCENT_GRASS.get().defaultBlockState(), 1).add(ESBlocks.GLADESPIKE.get().defaultBlockState(), 2).add(ESBlocks.TALL_GLADESPIKE.get().defaultBlockState(), 1).add(ESBlocks.VIVIDSTALK.get().defaultBlockState(), 1).add(ESBlocks.PINK_ROSE.get().defaultBlockState(), 2).add(ESBlocks.PINK_ROSE_BUSH.get().defaultBlockState(), 1).add(ESBlocks.STARLIGHT_TORCHFLOWER.get().defaultBlockState(), 2).add(ESBlocks.WHISPERBLOOM.get().defaultBlockState(), 2).add(ESBlocks.STARLIGHT_FLOWER.get().defaultBlockState(), 2)), 128));
        FeatureUtils.register(context, SWAMP_GRASS, Feature.RANDOM_PATCH, grassPatch(new WeightedStateProvider(SimpleWeightedRandomList.<BlockState>builder().add(ESBlocks.FANTABUD.get().defaultBlockState(), 1).add(ESBlocks.GREEN_FANTABUD.get().defaultBlockState(), 1).add(ESBlocks.FANTAFERN.get().defaultBlockState(), 1).add(ESBlocks.GREEN_FANTAFERN.get().defaultBlockState(), 1).add(ESBlocks.FANTAGRASS.get().defaultBlockState(), 1).add(ESBlocks.GREEN_FANTAGRASS.get().defaultBlockState(), 1).add(ESBlocks.VIVIDSTALK.get().defaultBlockState(), 1).add(ESBlocks.SWAMP_ROSE.get().defaultBlockState(), 1).add(ESBlocks.NIGHTFAN.get().defaultBlockState(), 1).add(ESBlocks.WHISPERBLOOM.get().defaultBlockState(), 1).add(ESBlocks.STARLIGHT_FLOWER.get().defaultBlockState(), 1)), 128));
        FeatureUtils.register(context, PERMAFROST_FOREST_GRASS, Feature.RANDOM_PATCH, grassPatch(new WeightedStateProvider(SimpleWeightedRandomList.<BlockState>builder().add(ESBlocks.NIGHT_SPROUTS.get().defaultBlockState(), 2).add(ESBlocks.SMALL_NIGHT_SPROUTS.get().defaultBlockState(), 2).add(ESBlocks.GLOWING_NIGHT_SPROUTS.get().defaultBlockState(), 2).add(ESBlocks.SMALL_GLOWING_NIGHT_SPROUTS.get().defaultBlockState(), 2).add(ESBlocks.LUNAR_GRASS.get().defaultBlockState(), 2).add(ESBlocks.GLOWING_LUNAR_GRASS.get().defaultBlockState(), 2).add(ESBlocks.CRESCENT_GRASS.get().defaultBlockState(), 2).add(ESBlocks.GLOWING_CRESCENT_GRASS.get().defaultBlockState(), 2).add(ESBlocks.PARASOL_GRASS.get().defaultBlockState(), 2).add(ESBlocks.GLOWING_PARASOL_GRASS.get().defaultBlockState(), 2).add(ESBlocks.LUNAR_BUSH.get().defaultBlockState(), 2).add(ESBlocks.GLOWING_LUNAR_BUSH.get().defaultBlockState(), 2).add(ESBlocks.TALL_CRESCENT_GRASS.get().defaultBlockState(), 1).add(ESBlocks.TALL_GLOWING_CRESCENT_GRASS.get().defaultBlockState(), 1).add(ESBlocks.CONEBLOOM.get().defaultBlockState(), 2).add(ESBlocks.VIVIDSTALK.get().defaultBlockState(), 1).add(ESBlocks.WHISPERBLOOM.get().defaultBlockState(), 2).add(ESBlocks.STARLIGHT_FLOWER.get().defaultBlockState(), 2)), 128));
        FeatureUtils.register(context, SCARLET_FOREST_GRASS, Feature.RANDOM_PATCH, grassPatch(new WeightedStateProvider(SimpleWeightedRandomList.<BlockState>builder().add(ESBlocks.ORANGE_SCARLET_BUD.get().defaultBlockState(), 1).add(ESBlocks.PURPLE_SCARLET_BUD.get().defaultBlockState(), 1).add(ESBlocks.RED_SCARLET_BUD.get().defaultBlockState(), 1).add(ESBlocks.SCARLET_GRASS.get().defaultBlockState(), 1).add(ESBlocks.WHISPERBLOOM.get().defaultBlockState(), 1).add(ESBlocks.STARLIGHT_FLOWER.get().defaultBlockState(), 1)), 128));
        FeatureUtils.register(context, TORREYA_FOREST_GRASS, Feature.RANDOM_PATCH, grassPatch(new WeightedStateProvider(SimpleWeightedRandomList.<BlockState>builder().add(ESBlocks.WITHERED_STARLIGHT_FLOWER.get().defaultBlockState(), 1)), 128));
        FeatureUtils.register(context, DESERT_GRASS, Feature.RANDOM_PATCH, grassPatch(new WeightedStateProvider(SimpleWeightedRandomList.<BlockState>builder().add(ESBlocks.DEAD_LUNAR_BUSH.get().defaultBlockState(), 1)), 5));
        FeatureUtils.register(context, NEAR_WATER_GRASS, Feature.RANDOM_PATCH, grassPatch(new WeightedStateProvider(SimpleWeightedRandomList.<BlockState>builder().add(ESBlocks.LUNAR_REED.get().defaultBlockState(), 1)), 128));
        FeatureUtils.register(context, ON_WATER_PLANT, Feature.RANDOM_PATCH, grassPatch(new WeightedStateProvider(SimpleWeightedRandomList.<BlockState>builder().add(ESBlocks.MOONLIGHT_DUCKWEED.get().defaultBlockState(), 18).add(ESBlocks.MOONLIGHT_LILY_PAD.get().defaultBlockState(), 2).add(ESBlocks.STARLIT_LILY_PAD.get().defaultBlockState(), 1)), 128));
        FeatureUtils.register(context, CRYSTAL_CAVES_GRASS, Feature.RANDOM_PATCH, grassPatch(new WeightedStateProvider(SimpleWeightedRandomList.<BlockState>builder().add(ESBlocks.CRYSTALLIZED_LUNAR_GRASS.get().defaultBlockState(), 6).add(ESBlocks.RED_CRYSTAL_ROOTS.get().defaultBlockState(), 6).add(ESBlocks.BLUE_CRYSTAL_ROOTS.get().defaultBlockState(), 6).add(ESBlocks.TWILVEWRYM_HERB.get().defaultBlockState(), 1).add(ESBlocks.STELLAFLY_BUSH.get().defaultBlockState(), 1).add(ESBlocks.GLIMMERFLY_BUSH.get().defaultBlockState(), 1)), 128));
        FeatureUtils.register(context, SWAMP_WATER, ESFeatures.SWAMP_WATER.get(), new NoneFeatureConfiguration());
        FeatureUtils.register(context, HOT_SPRING, ESFeatures.LAKE.get(), new ESLakeFeature.Configuration(BlockStateProvider.simple(Blocks.WATER.defaultBlockState()), new WeightedStateProvider(SimpleWeightedRandomList.<BlockState>builder().add(ESBlocks.SPRINGSTONE.get().defaultBlockState(), 10).add(ESBlocks.THERMAL_SPRINGSTONE.get().defaultBlockState(), 1).build())));

        // structure features
        FeatureUtils.register(context, GOLEM_FORGE_CHIMNEY, ESFeatures.GOLEM_FORGE_CHIMNEY.get(), new NoneFeatureConfiguration());
        FeatureUtils.register(context, CURSED_GARDEN_EXTRA_HEIGHT, ESFeatures.CURSED_GARDEN_EXTRA_HEIGHT.get(), new NoneFeatureConfiguration());
    }


    public static ResourceKey<ConfiguredFeature<?, ?>> create(String name) {
        return ResourceKey.create(Registries.CONFIGURED_FEATURE, new ResourceLocation(EternalStarlight.MOD_ID, name));
    }

    private static RandomPatchConfiguration grassPatch(BlockStateProvider stateProvider, int tries) {
        return FeatureUtils.simpleRandomPatchConfiguration(tries, PlacementUtils.onlyWhenEmpty(Feature.SIMPLE_BLOCK, new SimpleBlockConfiguration(stateProvider)));
    }
}

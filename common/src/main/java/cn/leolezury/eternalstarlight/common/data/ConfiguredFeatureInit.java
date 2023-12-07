package cn.leolezury.eternalstarlight.common.data;

import cn.leolezury.eternalstarlight.common.EternalStarlight;
import cn.leolezury.eternalstarlight.common.init.BlockInit;
import cn.leolezury.eternalstarlight.common.init.FeatureInit;
import cn.leolezury.eternalstarlight.common.util.ESTags;
import cn.leolezury.eternalstarlight.common.world.gen.feature.ESLakeFeature;
import cn.leolezury.eternalstarlight.common.world.gen.feature.FallenLogFeature;
import cn.leolezury.eternalstarlight.common.world.gen.feature.tree.decorator.TrunkBerriesDecorator;
import cn.leolezury.eternalstarlight.common.world.gen.feature.tree.foliage.SpheroidFoliagePlacer;
import cn.leolezury.eternalstarlight.common.world.gen.feature.tree.trunk.BranchingTrunkPlacer;
import com.google.common.base.Suppliers;
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
import net.minecraft.world.level.levelgen.feature.trunkplacers.UpwardsBranchingTrunkPlacer;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import net.minecraft.world.level.levelgen.structure.templatesystem.BlockMatchTest;
import net.minecraft.world.level.levelgen.structure.templatesystem.RuleTest;
import net.minecraft.world.level.levelgen.structure.templatesystem.TagMatchTest;

import java.util.List;
import java.util.Optional;
import java.util.function.Supplier;

public class ConfiguredFeatureInit {
    public static final ResourceKey<ConfiguredFeature<?, ?>> STONE_SPIKE = create("stone_spike");
    public static final ResourceKey<ConfiguredFeature<?, ?>> STONE_ORE = create("stone_ore");
    public static final ResourceKey<ConfiguredFeature<?, ?>> DEEPSLATE_ORE = create("deepslate_ore");
    public static final ResourceKey<ConfiguredFeature<?, ?>> GLOWING_VOIDSTONE_ORE = create("glowing_voidstone_ore");
    public static final ResourceKey<ConfiguredFeature<?, ?>> GLOWING_NIGHTSHADE_MUD_ORE = create("glowing_nightshade_mud_ore");
    public static final ResourceKey<ConfiguredFeature<?, ?>> NIGHTSHADE_DIRT_ORE = create("nightshade_dirt_ore");
    public static final ResourceKey<ConfiguredFeature<?, ?>> FALLEN_LUNAR_LOG = create("fallen_lunar_log");
    public static final ResourceKey<ConfiguredFeature<?, ?>> FALLEN_NORTHLAND_LOG = create("fallen_northland_log");
    public static final ResourceKey<ConfiguredFeature<?, ?>> FALLEN_STARLIGHT_MANGROVE_LOG = create("fallen_starlight_mangrove_log");
    public static final ResourceKey<ConfiguredFeature<?, ?>> STARLIGHT_CRYSTAL = create("starlight_crystal");
    public static final ResourceKey<ConfiguredFeature<?, ?>> CAVE_VINE = create("cave_vine");
    public static final ResourceKey<ConfiguredFeature<?, ?>> ABYSSAL_KELP = create("abyssal_kelp");
    public static final ResourceKey<ConfiguredFeature<?, ?>> LUNAR = create("lunar");
    public static final ResourceKey<ConfiguredFeature<?, ?>> LUNAR_HUGE = create("lunar_huge");
    public static final ResourceKey<ConfiguredFeature<?, ?>> NORTHLAND = create("northland");
    public static final ResourceKey<ConfiguredFeature<?, ?>> STARLIGHT_MANGROVE = create("starlight_mangrove");
    public static final ResourceKey<ConfiguredFeature<?, ?>> HUGE_GLOWING_MUSHROOM = create("huge_glowing_mushroom");
    public static final ResourceKey<ConfiguredFeature<?, ?>> STARLIGHT_FOREST = create("starlight_forest");
    public static final ResourceKey<ConfiguredFeature<?, ?>> DENSE_FOREST = create("dense_forest");
    public static final ResourceKey<ConfiguredFeature<?, ?>> SWAMP_FOREST = create("swamp_forest");
    public static final ResourceKey<ConfiguredFeature<?, ?>> PERMAFROST_FOREST = create("permafrost_forest");
    public static final ResourceKey<ConfiguredFeature<?, ?>> COMMON_FLOWER = create("common_flower");
    public static final ResourceKey<ConfiguredFeature<?, ?>> COMMON_GRASS = create("common_grass");
    public static final ResourceKey<ConfiguredFeature<?, ?>> SWAMP_GRASS = create("swamp_grass");
    public static final ResourceKey<ConfiguredFeature<?, ?>> SWAMP_LAKE = create("swamp_lake");
    public static final ResourceKey<ConfiguredFeature<?, ?>> SWAMP_WATER = create("swamp_water");
    public static final ResourceKey<ConfiguredFeature<?, ?>> CAVE_SPRING = create("cave_spring");
    public static final ResourceKey<ConfiguredFeature<?, ?>> SWAMP_SILVER_ORE = create("swamp_silver_ore");

    //structure features
    public static final ResourceKey<ConfiguredFeature<?, ?>> CURSED_GARDEN_EXTRA_HEIGHT = create("cursed_garden_extra_height");

    public static void bootstrap(BootstapContext<ConfiguredFeature<?, ?>> context) {
        HolderGetter<Block> blockHolderGetter = context.lookup(Registries.BLOCK);
        HolderGetter<PlacedFeature> placedFeatureHolderGetter = context.lookup(Registries.PLACED_FEATURE);

        Supplier<List<OreConfiguration.TargetBlockState>> MUD_SWAMP_SILVER_ORES = Suppliers.memoize(() -> List.of(
                OreConfiguration.target(new BlockMatchTest(BlockInit.NIGHTSHADE_MUD.get()), BlockInit.SWAMP_SILVER_ORE.get().defaultBlockState())));

        RuleTest slRule = new TagMatchTest(ESTags.Blocks.BASE_STONE_STARLIGHT);
        RuleTest voidstoneRule = new BlockMatchTest(BlockInit.VOIDSTONE.get());
        RuleTest mudRule = new BlockMatchTest(BlockInit.NIGHTSHADE_MUD.get());

        register(context, STONE_SPIKE, FeatureInit.STONE_SPIKE.get(), new NoneFeatureConfiguration());
        register(context, STONE_ORE, Feature.ORE, new OreConfiguration(slRule, Blocks.STONE.defaultBlockState(), 64));
        register(context, DEEPSLATE_ORE, Feature.ORE, new OreConfiguration(slRule, Blocks.DEEPSLATE.defaultBlockState(), 64));
        register(context, GLOWING_VOIDSTONE_ORE, Feature.ORE, new OreConfiguration(voidstoneRule, BlockInit.GLOWING_VOIDSTONE.get().defaultBlockState(), 20));
        register(context, GLOWING_NIGHTSHADE_MUD_ORE, Feature.ORE, new OreConfiguration(mudRule, BlockInit.GLOWING_NIGHTSHADE_MUD.get().defaultBlockState(), 20));
        register(context, NIGHTSHADE_DIRT_ORE, Feature.ORE, new OreConfiguration(slRule, BlockInit.NIGHTSHADE_DIRT.get().defaultBlockState(), 33));
        register(context, FALLEN_LUNAR_LOG, FeatureInit.FALLEN_LOG.get(), new FallenLogFeature.Configuration(BlockStateProvider.simple(BlockInit.LUNAR_LOG.get())));
        register(context, FALLEN_NORTHLAND_LOG, FeatureInit.FALLEN_LOG.get(), new FallenLogFeature.Configuration(BlockStateProvider.simple(BlockInit.NORTHLAND_LOG.get())));
        register(context, FALLEN_STARLIGHT_MANGROVE_LOG, FeatureInit.FALLEN_LOG.get(), new FallenLogFeature.Configuration(BlockStateProvider.simple(BlockInit.STARLIGHT_MANGROVE_LOG.get())));
        register(context, STARLIGHT_CRYSTAL, FeatureInit.STARLIGHT_CRYSTAL.get(), new NoneFeatureConfiguration());
        register(context, CAVE_VINE, Feature.BLOCK_COLUMN, new BlockColumnConfiguration(List.of(BlockColumnConfiguration.layer(new WeightedListInt(SimpleWeightedRandomList.<IntProvider>builder().add(UniformInt.of(0, 19), 2).add(UniformInt.of(0, 2), 3).add(UniformInt.of(0, 6), 10).build()),
                new WeightedStateProvider(SimpleWeightedRandomList.<BlockState>builder().add(BlockInit.BERRIES_VINES_PLANT.get().defaultBlockState(), 4).add(BlockInit.BERRIES_VINES_PLANT.get().defaultBlockState().setValue(CaveVines.BERRIES, Boolean.valueOf(true)), 1))), BlockColumnConfiguration.layer(ConstantInt.of(1),
                new RandomizedIntStateProvider(new WeightedStateProvider(SimpleWeightedRandomList.<BlockState>builder().add(BlockInit.BERRIES_VINES.get().defaultBlockState(), 4).add(BlockInit.BERRIES_VINES.get().defaultBlockState().setValue(CaveVines.BERRIES, Boolean.valueOf(true)), 1)), CaveVinesBlock.AGE, UniformInt.of(23, 25)))), Direction.DOWN, BlockPredicate.ONLY_IN_AIR_PREDICATE, true));
        register(context, ABYSSAL_KELP, FeatureInit.KELP.get(), new NoneFeatureConfiguration());
        register(context, LUNAR, Feature.TREE, new TreeConfiguration.TreeConfigurationBuilder(BlockStateProvider.simple(BlockInit.LUNAR_LOG.get()), new BranchingTrunkPlacer(8, 2, 10, ConstantInt.of(0), UniformInt.of(6, 7), ConstantInt.of(1), UniformInt.of(3, 4)), BlockStateProvider.simple(BlockInit.LUNAR_LEAVES.get()), new SpheroidFoliagePlacer(UniformInt.of(3, 4), ConstantInt.of(0)), new TwoLayersFeatureSize(4, 1, 1)).decorators(List.of(new TrunkBerriesDecorator())).build());
        register(context, LUNAR_HUGE, Feature.TREE, new TreeConfiguration.TreeConfigurationBuilder(BlockStateProvider.simple(BlockInit.LUNAR_LOG.get()), new BranchingTrunkPlacer(32, 2, 10, UniformInt.of(3, 4), UniformInt.of(2, 4)), BlockStateProvider.simple(BlockInit.LUNAR_LEAVES.get()), new SpheroidFoliagePlacer(UniformInt.of(3, 4), ConstantInt.of(0)), new TwoLayersFeatureSize(4, 1, 1)).decorators(List.of(new TrunkBerriesDecorator())).build());
        register(context, NORTHLAND, Feature.TREE, new TreeConfiguration.TreeConfigurationBuilder(BlockStateProvider.simple(BlockInit.NORTHLAND_LOG.get()), new GiantTrunkPlacer(10, 2, 10), BlockStateProvider.simple(BlockInit.NORTHLAND_LEAVES.get()), new MegaPineFoliagePlacer(ConstantInt.of(0), ConstantInt.of(0), UniformInt.of(13, 17)), new TwoLayersFeatureSize(1, 1, 2)).build());
        register(context, STARLIGHT_MANGROVE, Feature.TREE, (new TreeConfiguration.TreeConfigurationBuilder(BlockStateProvider.simple(BlockInit.STARLIGHT_MANGROVE_LOG.get()), new UpwardsBranchingTrunkPlacer(4, 1, 9, UniformInt.of(1, 6), 0.5F, UniformInt.of(0, 1), blockHolderGetter.getOrThrow(BlockTags.MANGROVE_LOGS_CAN_GROW_THROUGH)), BlockStateProvider.simple(BlockInit.STARLIGHT_MANGROVE_LEAVES.get()), new RandomSpreadFoliagePlacer(ConstantInt.of(3), ConstantInt.of(0), ConstantInt.of(2), 70), Optional.of(new MangroveRootPlacer(UniformInt.of(3, 7), BlockStateProvider.simple(BlockInit.STARLIGHT_MANGROVE_ROOTS.get()), Optional.of(new AboveRootPlacement(BlockStateProvider.simple(Blocks.MOSS_CARPET), 0.5F)), new MangroveRootPlacement(blockHolderGetter.getOrThrow(BlockTags.MANGROVE_ROOTS_CAN_GROW_THROUGH), HolderSet.direct(Block::builtInRegistryHolder, BlockInit.NIGHTSHADE_MUD.get(), BlockInit.MUDDY_STARLIGHT_MANGROVE_ROOTS.get()), BlockStateProvider.simple(BlockInit.MUDDY_STARLIGHT_MANGROVE_ROOTS.get()), 8, 15, 0.2F))), new TwoLayersFeatureSize(3, 0, 2))).decorators(List.of(new LeaveVineDecorator(0.125F), new AttachedToLeavesDecorator(0.14F, 1, 0, new RandomizedIntStateProvider(BlockStateProvider.simple(Blocks.MANGROVE_PROPAGULE.defaultBlockState().setValue(MangrovePropaguleBlock.HANGING, Boolean.valueOf(true))), MangrovePropaguleBlock.AGE, UniformInt.of(0, 4)), 2, List.of(Direction.DOWN)))).ignoreVines().build());
        register(context, HUGE_GLOWING_MUSHROOM, Feature.HUGE_RED_MUSHROOM, new HugeMushroomFeatureConfiguration(BlockStateProvider.simple(BlockInit.GLOWING_MUSHROOM_BLOCK.get().defaultBlockState().setValue(HugeMushroomBlock.DOWN, Boolean.valueOf(false))), BlockStateProvider.simple(Blocks.MUSHROOM_STEM.defaultBlockState().setValue(HugeMushroomBlock.UP, Boolean.valueOf(false)).setValue(HugeMushroomBlock.DOWN, Boolean.valueOf(false))), 2));
        register(context, STARLIGHT_FOREST, Feature.RANDOM_SELECTOR, new RandomFeatureConfiguration(List.of(new WeightedPlacedFeature(placedFeatureHolderGetter.getOrThrow(PlacedFeatureInit.HUGE_GLOWING_MUSHROOM_CHECKED), 0.01F), new WeightedPlacedFeature(placedFeatureHolderGetter.getOrThrow(PlacedFeatureInit.LUNAR_TREE_CHECKED), 0.04F), new WeightedPlacedFeature(placedFeatureHolderGetter.getOrThrow(PlacedFeatureInit.LUNAR_HUGE_TREE_CHECKED), 0.95F)), placedFeatureHolderGetter.getOrThrow(PlacedFeatureInit.LUNAR_TREE_CHECKED)));
        register(context, DENSE_FOREST, Feature.RANDOM_SELECTOR, new RandomFeatureConfiguration(List.of(new WeightedPlacedFeature(placedFeatureHolderGetter.getOrThrow(PlacedFeatureInit.LUNAR_TREE_CHECKED), 0.9F), new WeightedPlacedFeature(placedFeatureHolderGetter.getOrThrow(PlacedFeatureInit.HUGE_GLOWING_MUSHROOM_CHECKED), 0.1F)), placedFeatureHolderGetter.getOrThrow(PlacedFeatureInit.LUNAR_TREE_CHECKED)));
        register(context, SWAMP_FOREST, Feature.RANDOM_SELECTOR, new RandomFeatureConfiguration(List.of(new WeightedPlacedFeature(placedFeatureHolderGetter.getOrThrow(PlacedFeatureInit.STARLIGHT_MANGROVE_TREE_CHECKED), 0.9F), new WeightedPlacedFeature(placedFeatureHolderGetter.getOrThrow(PlacedFeatureInit.HUGE_GLOWING_MUSHROOM_CHECKED), 0.1F)), placedFeatureHolderGetter.getOrThrow(PlacedFeatureInit.STARLIGHT_MANGROVE_TREE_CHECKED)));
        register(context, PERMAFROST_FOREST, Feature.RANDOM_SELECTOR, new RandomFeatureConfiguration(List.of(new WeightedPlacedFeature(placedFeatureHolderGetter.getOrThrow(PlacedFeatureInit.NORTHLAND_TREE_CHECKED), 0.25F), new WeightedPlacedFeature(placedFeatureHolderGetter.getOrThrow(PlacedFeatureInit.NORTHLAND_ON_SNOW), 0.25F)), placedFeatureHolderGetter.getOrThrow(PlacedFeatureInit.NORTHLAND_TREE_CHECKED)));
        register(context, COMMON_FLOWER, Feature.FLOWER, grassPatch(new WeightedStateProvider(SimpleWeightedRandomList.<BlockState>builder().add(BlockInit.STARLIGHT_FLOWER.get().defaultBlockState(), 2).add(BlockInit.CONEBLOOM.get().defaultBlockState(), 1).add(BlockInit.NIGHTFAN.get().defaultBlockState(), 1).add(BlockInit.PINK_ROSE.get().defaultBlockState(), 1).add(BlockInit.STARLIGHT_TORCHFLOWER.get().defaultBlockState(), 1)), 128));
        register(context, COMMON_GRASS, Feature.FLOWER, grassPatch(new WeightedStateProvider(SimpleWeightedRandomList.<BlockState>builder().add(BlockInit.NIGHT_SPROUTS.get().defaultBlockState(), 2).add(BlockInit.SMALL_NIGHT_SPROUTS.get().defaultBlockState(), 2).add(BlockInit.GLOWING_NIGHT_SPROUTS.get().defaultBlockState(), 2).add(BlockInit.SMALL_GLOWING_NIGHT_SPROUTS.get().defaultBlockState(), 2).add(BlockInit.LUNAR_GRASS.get().defaultBlockState(), 2).add(BlockInit.GLOWING_LUNAR_GRASS.get().defaultBlockState(), 2).add(BlockInit.CRESCENT_GRASS.get().defaultBlockState(), 2).add(BlockInit.GLOWING_CRESCENT_GRASS.get().defaultBlockState(), 2).add(BlockInit.PARASOL_GRASS.get().defaultBlockState(), 2).add(BlockInit.GLOWING_PARASOL_GRASS.get().defaultBlockState(), 2).add(BlockInit.LUNAR_REED.get().defaultBlockState(), 1).add(BlockInit.GLADESPIKE.get().defaultBlockState(), 1).add(BlockInit.VIVIDSTALK.get().defaultBlockState(), 1)), 128));
        register(context, SWAMP_GRASS, Feature.FLOWER, grassPatch(new WeightedStateProvider(SimpleWeightedRandomList.<BlockState>builder().add(BlockInit.FANTABUD.get().defaultBlockState(), 1).add(BlockInit.GREEN_FANTABUD.get().defaultBlockState(), 1).add(BlockInit.FANTAFERN.get().defaultBlockState(), 1).add(BlockInit.GREEN_FANTAFERN.get().defaultBlockState(), 1).add(BlockInit.FANTAGRASS.get().defaultBlockState(), 1).add(BlockInit.GREEN_FANTAGRASS.get().defaultBlockState(), 1)), 128));
        WeightedStateProvider swampLakeStateProvider = new WeightedStateProvider(SimpleWeightedRandomList.<BlockState>builder().add(BlockInit.NIGHTSHADE_MUD.get().defaultBlockState(), 10).add(BlockInit.GLOWING_NIGHTSHADE_MUD.get().defaultBlockState(), 1).build());
        register(context, SWAMP_LAKE, FeatureInit.LAKE.get(), new ESLakeFeature.Configuration(BlockStateProvider.simple(Blocks.WATER.defaultBlockState()), swampLakeStateProvider, UniformInt.of(8, 10), UniformInt.of(4, 6), UniformInt.of(8, 10)));
        register(context, SWAMP_WATER, FeatureInit.SWAMP_WATER.get(), new NoneFeatureConfiguration());
        WeightedStateProvider caveSpringStateProvider = new WeightedStateProvider(SimpleWeightedRandomList.<BlockState>builder().add(BlockInit.SPRINGSTONE.get().defaultBlockState(), 10).add(BlockInit.THERMAL_SPRINGSTONE.get().defaultBlockState(), 1).build());
        register(context, CAVE_SPRING, FeatureInit.LAKE.get(), new ESLakeFeature.Configuration(BlockStateProvider.simple(Blocks.WATER.defaultBlockState()), caveSpringStateProvider, UniformInt.of(8, 10), UniformInt.of(6, 8), UniformInt.of(8, 10)));
        register(context, SWAMP_SILVER_ORE, Feature.ORE, new OreConfiguration(MUD_SWAMP_SILVER_ORES.get(), 7));

        // structure features
        register(context, CURSED_GARDEN_EXTRA_HEIGHT, FeatureInit.CURSED_GARDEN_EXTRA_HEIGHT.get(), new NoneFeatureConfiguration());
    }


    public static ResourceKey<ConfiguredFeature<?, ?>> create(String name) {
        return ResourceKey.create(Registries.CONFIGURED_FEATURE, new ResourceLocation(EternalStarlight.MOD_ID, name));
    }

    private static <FC extends FeatureConfiguration, F extends Feature<FC>> void register(BootstapContext<ConfiguredFeature<?, ?>> context,
                                                                                          ResourceKey<ConfiguredFeature<?, ?>> key, F feature, FC configuration) {
        context.register(key, new ConfiguredFeature<>(feature, configuration));
    }

    private static RandomPatchConfiguration grassPatch(BlockStateProvider stateProvider, int tries) {
        return FeatureUtils.simpleRandomPatchConfiguration(tries, PlacementUtils.onlyWhenEmpty(Feature.SIMPLE_BLOCK, new SimpleBlockConfiguration(stateProvider)));
    }
}

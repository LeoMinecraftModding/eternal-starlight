package cn.leolezury.eternalstarlight.common.data;

import cn.leolezury.eternalstarlight.common.EternalStarlight;
import cn.leolezury.eternalstarlight.common.init.BlockInit;
import cn.leolezury.eternalstarlight.common.world.gen.biomesource.ESBiomeSource;
import cn.leolezury.eternalstarlight.common.world.gen.chunkgenerator.ESChunkGenerator;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.HolderSet;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.dimension.DimensionType;
import net.minecraft.world.level.dimension.LevelStem;
import net.minecraft.world.level.levelgen.*;
import net.minecraft.world.level.levelgen.placement.CaveSurface;

import java.util.List;
import java.util.OptionalLong;

public class DimensionInit {
    public static final ResourceKey<Level> STARLIGHT_KEY = ResourceKey.create(Registries.DIMENSION, new ResourceLocation(EternalStarlight.MOD_ID, "starlight"));
    public static final ResourceKey<LevelStem> STARLIGHT_LEVEL_STEM = ResourceKey.create(Registries.LEVEL_STEM, new ResourceLocation(EternalStarlight.MOD_ID, "starlight"));
    public static final ResourceKey<DimensionType> STARLIGHT_TYPE = ResourceKey.create(Registries.DIMENSION_TYPE, new ResourceLocation(EternalStarlight.MOD_ID, "starlight"));
    public static final ResourceKey<NoiseGeneratorSettings> STARLIGHT_NOISE_SETTINGS = ResourceKey.create(Registries.NOISE_SETTINGS, new ResourceLocation(EternalStarlight.MOD_ID, "starlight"));

    private static SurfaceRules.RuleSource makeSurface(BlockState grassBlock, BlockState dirt) {
        return SurfaceRules.sequence(
                SurfaceRules.ifTrue(
                        SurfaceRules.stoneDepthCheck(0, false, CaveSurface.FLOOR),
                        SurfaceRules.state(grassBlock)
                ),
                SurfaceRules.ifTrue(
                        SurfaceRules.stoneDepthCheck(0, true, CaveSurface.FLOOR),
                        SurfaceRules.state(dirt)
                )
        );
    }

    private static SurfaceRules.RuleSource makeSimpleSurface(BlockState simple) {
        return SurfaceRules.ifTrue(
                SurfaceRules.stoneDepthCheck(0, true, CaveSurface.FLOOR),
                SurfaceRules.state(simple)
        );
    }

    private static SurfaceRules.RuleSource makeSurfaceRule() {
        SurfaceRules.RuleSource bedrock = SurfaceRules.state(Blocks.BEDROCK.defaultBlockState());
        SurfaceRules.RuleSource voidstone = SurfaceRules.state(BlockInit.VOIDSTONE.get().defaultBlockState());

        return SurfaceRules.sequence(
                SurfaceRules.ifTrue(SurfaceRules.verticalGradient("bedrock_floor", VerticalAnchor.bottom(), VerticalAnchor.aboveBottom(5)), bedrock),
                SurfaceRules.ifTrue(SurfaceRules.verticalGradient("stone", VerticalAnchor.absolute(0), VerticalAnchor.absolute(8)), voidstone),
                SurfaceRules.ifTrue(SurfaceRules.isBiome(BiomeInit.SHIMMER_RIVER, BiomeInit.STARLIT_SEA, BiomeInit.WARM_SHORE), makeSimpleSurface(BlockInit.TWILIGHT_SAND.get().defaultBlockState())),
                SurfaceRules.ifTrue(SurfaceRules.isBiome(BiomeInit.DARK_SWAMP), makeSurface(BlockInit.FANTASY_GRASS_BLOCK.get().defaultBlockState(), BlockInit.NIGHTSHADE_MUD.get().defaultBlockState())),
                makeSurface(BlockInit.NIGHTSHADE_GRASS_BLOCK.get().defaultBlockState(), BlockInit.NIGHTSHADE_DIRT.get().defaultBlockState())
        );
    }

    public static void bootstrapNoiseSettings(BootstapContext<NoiseGeneratorSettings> context) {
        NoiseGeneratorSettings settings = new NoiseGeneratorSettings(
                NoiseSettings.create(
                        -64,
                        384,
                        1,
                        2
                ),
                BlockInit.GRIMSTONE.get().defaultBlockState(),
                Blocks.WATER.defaultBlockState(),
                new NoiseRouter(
                        DensityFunctions.zero(),
                        DensityFunctions.zero(),
                        DensityFunctions.zero(),
                        DensityFunctions.zero(),
                        DensityFunctions.zero(),
                        DensityFunctions.zero(),
                        DensityFunctions.zero(),
                        DensityFunctions.zero(),
                        DensityFunctions.zero(),
                        DensityFunctions.zero(),
                        DensityFunctions.zero(),
                        DensityFunctions.zero(),
                        DensityFunctions.zero(),
                        DensityFunctions.zero(),
                        DensityFunctions.zero() // yeah, we're using our own worldgen system
                ),
                makeSurfaceRule(),
                List.of(),
                50,
                false,
                false,
                false,
                false
        );
        context.register(STARLIGHT_NOISE_SETTINGS, settings);
    }

    public static void bootstrapLevelStem(BootstapContext<LevelStem> context) {
        HolderGetter<Biome> biomeHolderGetter = context.lookup(Registries.BIOME);
        HolderGetter<DimensionType> dimensionTypeHolderGetter = context.lookup(Registries.DIMENSION_TYPE);
        HolderGetter<NoiseGeneratorSettings> noiseSettingsHolderGetter = context.lookup(Registries.NOISE_SETTINGS);
        LevelStem levelStem = new LevelStem(dimensionTypeHolderGetter.getOrThrow(STARLIGHT_TYPE), new ESChunkGenerator(new ESBiomeSource(HolderSet.direct(
                // all biomes in our dimension
                biomeHolderGetter.getOrThrow(BiomeInit.STARLIGHT_FOREST),
                biomeHolderGetter.getOrThrow(BiomeInit.STARLIGHT_DENSE_FOREST),
                biomeHolderGetter.getOrThrow(BiomeInit.STARLIGHT_PERMAFROST_FOREST),
                biomeHolderGetter.getOrThrow(BiomeInit.DARK_SWAMP),
                biomeHolderGetter.getOrThrow(BiomeInit.SHIMMER_RIVER),
                biomeHolderGetter.getOrThrow(BiomeInit.STARLIT_SEA),
                biomeHolderGetter.getOrThrow(BiomeInit.WARM_SHORE)
        )), noiseSettingsHolderGetter.getOrThrow(STARLIGHT_NOISE_SETTINGS)));
        context.register(STARLIGHT_LEVEL_STEM, levelStem);
    }

    public static void bootstrapDimType(BootstapContext<DimensionType> context) {
        DimensionType type = new DimensionType(
                OptionalLong.of(12000L), // fixed time
                true, // has skylight
                false, // has ceiling
                false, // ultrawarm
                true, // natural
                1, // coordinate scale
                true, // bed works
                true, // respawn anchor works
                -64, // min y
                384, // max y
                384, // height
                BlockTags.INFINIBURN_OVERWORLD, // infiniburn
                new ResourceLocation(EternalStarlight.MOD_ID, "special_effect"), // special effect
                0f, // ambient light
                new DimensionType.MonsterSettings(false, false, UniformInt.of(0, 7), 7) // monster spawn things
        );
        context.register(STARLIGHT_TYPE, type);
    }
}

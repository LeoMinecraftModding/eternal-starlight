package cn.leolezury.eternalstarlight.common.data;

import cn.leolezury.eternalstarlight.common.EternalStarlight;
import cn.leolezury.eternalstarlight.common.registry.ESBlocks;
import cn.leolezury.eternalstarlight.common.world.gen.biomesource.ESBiomeSource;
import cn.leolezury.eternalstarlight.common.world.gen.chunkgenerator.ESChunkGenerator;
import cn.leolezury.eternalstarlight.common.world.gen.system.WorldGenProvider;
import cn.leolezury.eternalstarlight.common.world.gen.system.transformer.DataTransformer;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.HolderSet;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.ResourceKey;
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

import java.util.ArrayList;
import java.util.List;
import java.util.OptionalLong;

public class ESDimensions {
	public static final ResourceKey<Level> STARLIGHT_KEY = ResourceKey.create(Registries.DIMENSION, EternalStarlight.id("starlight"));
	public static final ResourceKey<LevelStem> STARLIGHT_LEVEL_STEM = ResourceKey.create(Registries.LEVEL_STEM, EternalStarlight.id("starlight"));
	public static final ResourceKey<DimensionType> STARLIGHT_TYPE = ResourceKey.create(Registries.DIMENSION_TYPE, EternalStarlight.id("starlight"));
	public static final ResourceKey<NoiseGeneratorSettings> STARLIGHT_NOISE_SETTINGS = ResourceKey.create(Registries.NOISE_SETTINGS, EternalStarlight.id("starlight"));

	public static final int SEA_LEVEL = 50;

	private static SurfaceRules.RuleSource makeSurface(BlockState grassBlock, BlockState dirt) {
		return SurfaceRules.sequence(
			SurfaceRules.ifTrue(
				SurfaceRules.stoneDepthCheck(0, false, CaveSurface.FLOOR),
				SurfaceRules.sequence(SurfaceRules.ifTrue(SurfaceRules.waterBlockCheck(0, 0), SurfaceRules.state(grassBlock)), SurfaceRules.state(dirt))
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

	private static SurfaceRules.RuleSource makeAbyss() {
		return SurfaceRules.sequence(
			SurfaceRules.ifTrue(SurfaceRules.not(SurfaceRules.yBlockCheck(VerticalAnchor.absolute(-25), 1)), SurfaceRules.state(ESBlocks.CRYOBYSSLATE.get().defaultBlockState())),
			SurfaceRules.ifTrue(SurfaceRules.not(SurfaceRules.yBlockCheck(VerticalAnchor.absolute(-10), 1)), SurfaceRules.state(ESBlocks.ABYSSLATE.get().defaultBlockState())),
			SurfaceRules.ifTrue(SurfaceRules.not(SurfaceRules.yBlockCheck(VerticalAnchor.absolute(50), 1)), SurfaceRules.state(ESBlocks.THERMABYSSLATE.get().defaultBlockState()))
		);
	}

	private static SurfaceRules.RuleSource makeSurfaceRule() {
		SurfaceRules.RuleSource bedrock = SurfaceRules.state(Blocks.BEDROCK.defaultBlockState());
		SurfaceRules.RuleSource voidstone = SurfaceRules.state(ESBlocks.VOIDSTONE.get().defaultBlockState());
		SurfaceRules.RuleSource ice = SurfaceRules.state(ESBlocks.ETERNAL_ICE.get().defaultBlockState());
		SurfaceRules.RuleSource sand = SurfaceRules.state(ESBlocks.TWILIGHT_SAND.get().defaultBlockState());
		SurfaceRules.RuleSource sandstone = SurfaceRules.state(ESBlocks.TWILIGHT_SANDSTONE.get().defaultBlockState());
		SurfaceRules.RuleSource desertRule = SurfaceRules.sequence(
			SurfaceRules.ifTrue(SurfaceRules.stoneDepthCheck(0, true, CaveSurface.FLOOR), sand),
			SurfaceRules.ifTrue(SurfaceRules.stoneDepthCheck(0, true, 6, CaveSurface.FLOOR), sandstone)
		);

		return SurfaceRules.sequence(
			SurfaceRules.ifTrue(SurfaceRules.verticalGradient("bedrock_floor", VerticalAnchor.bottom(), VerticalAnchor.aboveBottom(5)), bedrock),
			SurfaceRules.ifTrue(SurfaceRules.not(SurfaceRules.isBiome(ESBiomes.THE_ABYSS)), SurfaceRules.ifTrue(SurfaceRules.verticalGradient("stone", VerticalAnchor.absolute(0), VerticalAnchor.absolute(8)), voidstone)),
			SurfaceRules.ifTrue(SurfaceRules.isBiome(ESBiomes.SHIMMER_RIVER, ESBiomes.ETHER_RIVER, ESBiomes.WARM_SHORE), makeSimpleSurface(ESBlocks.TWILIGHT_SAND.get().defaultBlockState())),
			SurfaceRules.ifTrue(SurfaceRules.isBiome(ESBiomes.STARLIT_SEA), makeSimpleSurface(ESBlocks.DUSTED_GRAVEL.get().defaultBlockState())),
			SurfaceRules.ifTrue(SurfaceRules.isBiome(ESBiomes.DARK_SWAMP), makeSurface(ESBlocks.FANTASY_GRASS_BLOCK.get().defaultBlockState(), ESBlocks.NIGHTFALL_MUD.get().defaultBlockState())),
			SurfaceRules.ifTrue(SurfaceRules.isBiome(ESBiomes.CRYSTALLIZED_DESERT), desertRule),
			SurfaceRules.ifTrue(SurfaceRules.isBiome(ESBiomes.THE_ABYSS), makeAbyss()),
			makeSurface(ESBlocks.NIGHTFALL_GRASS_BLOCK.get().defaultBlockState(), ESBlocks.NIGHTFALL_DIRT.get().defaultBlockState()),
			SurfaceRules.ifTrue(SurfaceRules.isBiome(ESBiomes.STARLIGHT_PERMAFROST_FOREST), ice)
		);
	}

	public static void bootstrapNoiseSettings(BootstrapContext<NoiseGeneratorSettings> context) {
		NoiseGeneratorSettings settings = new NoiseGeneratorSettings(
			NoiseSettings.create(
				-64,
				384,
				1,
				2
			),
			ESBlocks.GRIMSTONE.get().defaultBlockState(),
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
			SEA_LEVEL,
			false,
			false,
			false,
			false
		);
		context.register(STARLIGHT_NOISE_SETTINGS, settings);
	}

	public static void bootstrapLevelStem(BootstrapContext<LevelStem> context) {
		HolderGetter<Biome> biomeHolderGetter = context.lookup(Registries.BIOME);
		HolderGetter<DimensionType> dimensionTypeHolderGetter = context.lookup(Registries.DIMENSION_TYPE);
		HolderGetter<NoiseGeneratorSettings> noiseSettingsHolderGetter = context.lookup(Registries.NOISE_SETTINGS);
		HolderGetter<DataTransformer> transformers = context.lookup(ESRegistries.DATA_TRANSFORMER);
		List<WorldGenProvider.TransformerWithSeed> biomeTransformers = new ArrayList<>();
		biomeTransformers.add(new WorldGenProvider.TransformerWithSeed(transformers.getOrThrow(ESDataTransformers.ADD_OCEAN), 0));
		biomeTransformers.add(new WorldGenProvider.TransformerWithSeed(transformers.getOrThrow(ESDataTransformers.APPLY_BIOMES), 0));
		biomeTransformers.add(new WorldGenProvider.TransformerWithSeed(transformers.getOrThrow(ESDataTransformers.DUPLICATE), 0));
		for (int i = 0; i < 6; i++) {
			biomeTransformers.add(new WorldGenProvider.TransformerWithSeed(transformers.getOrThrow(ESDataTransformers.DUPLICATE), i));
			biomeTransformers.add(new WorldGenProvider.TransformerWithSeed(transformers.getOrThrow(ESDataTransformers.RANDOMIZE_BIOMES), i));
			biomeTransformers.add(new WorldGenProvider.TransformerWithSeed(transformers.getOrThrow(ESDataTransformers.ASSIMILATE_BIOMES), i));
			biomeTransformers.add(new WorldGenProvider.TransformerWithSeed(transformers.getOrThrow(ESDataTransformers.ASSIMILATE_LONELY_BIOMES), i));
		}
		biomeTransformers.add(new WorldGenProvider.TransformerWithSeed(transformers.getOrThrow(ESDataTransformers.ASSIMILATE_LONELY_BIOMES), 0));
		for (int i = 0; i < 4; i++) {
			biomeTransformers.add(new WorldGenProvider.TransformerWithSeed(transformers.getOrThrow(ESDataTransformers.ADD_BEACHES), 0));
		}
		biomeTransformers.add(new WorldGenProvider.TransformerWithSeed(transformers.getOrThrow(ESDataTransformers.ADD_RIVERS_AND_ABYSS), 0));
		for (int i = 0; i < 3; i++) {
			biomeTransformers.add(new WorldGenProvider.TransformerWithSeed(transformers.getOrThrow(ESDataTransformers.ADD_TRANSITIONS), 0));
		}
		biomeTransformers.add(new WorldGenProvider.TransformerWithSeed(transformers.getOrThrow(ESDataTransformers.DUPLICATE), 0));
		biomeTransformers.add(new WorldGenProvider.TransformerWithSeed(transformers.getOrThrow(ESDataTransformers.DUPLICATE), 0));
		List<WorldGenProvider.TransformerWithSeed> heightTransformers = new ArrayList<>();
		heightTransformers.add(new WorldGenProvider.TransformerWithSeed(transformers.getOrThrow(ESDataTransformers.SMOOTH_HEIGHTS_LARGE), 0));
		heightTransformers.add(new WorldGenProvider.TransformerWithSeed(transformers.getOrThrow(ESDataTransformers.NOISE_HEIGHT), 0));
		heightTransformers.add(new WorldGenProvider.TransformerWithSeed(transformers.getOrThrow(ESDataTransformers.SMOOTH_HEIGHTS_SMALL), 0));
		WorldGenProvider provider = new WorldGenProvider(biomeTransformers, heightTransformers, 320, -64);
		LevelStem levelStem = new LevelStem(dimensionTypeHolderGetter.getOrThrow(STARLIGHT_TYPE), new ESChunkGenerator(new ESBiomeSource(provider, HolderSet.direct(
			// all biomes in our dimension
			biomeHolderGetter.getOrThrow(ESBiomes.STARLIGHT_FOREST),
			biomeHolderGetter.getOrThrow(ESBiomes.STARLIGHT_DENSE_FOREST),
			biomeHolderGetter.getOrThrow(ESBiomes.STARLIGHT_PERMAFROST_FOREST),
			biomeHolderGetter.getOrThrow(ESBiomes.DARK_SWAMP),
			biomeHolderGetter.getOrThrow(ESBiomes.SCARLET_FOREST),
			biomeHolderGetter.getOrThrow(ESBiomes.TORREYA_FOREST),
			biomeHolderGetter.getOrThrow(ESBiomes.CRYSTALLIZED_DESERT),
			biomeHolderGetter.getOrThrow(ESBiomes.SHIMMER_RIVER),
			biomeHolderGetter.getOrThrow(ESBiomes.ETHER_RIVER),
			biomeHolderGetter.getOrThrow(ESBiomes.STARLIT_SEA),
			biomeHolderGetter.getOrThrow(ESBiomes.THE_ABYSS),
			biomeHolderGetter.getOrThrow(ESBiomes.WARM_SHORE)
		)), noiseSettingsHolderGetter.getOrThrow(STARLIGHT_NOISE_SETTINGS)));
		context.register(STARLIGHT_LEVEL_STEM, levelStem);
	}

	public static void bootstrapDimType(BootstrapContext<DimensionType> context) {
		DimensionType type = new DimensionType(
			OptionalLong.of(12900L), // fixed time
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
			EternalStarlight.id("special_effect"), // special effects
			0f, // ambient light
			new DimensionType.MonsterSettings(false, false, UniformInt.of(0, 7), 7) // monster spawn things
		);
		context.register(STARLIGHT_TYPE, type);
	}
}

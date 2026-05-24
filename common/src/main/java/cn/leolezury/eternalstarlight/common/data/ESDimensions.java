package cn.leolezury.eternalstarlight.common.data;

import cn.leolezury.eternalstarlight.common.EternalStarlight;
import cn.leolezury.eternalstarlight.common.registry.ESBlocks;
import cn.leolezury.eternalstarlight.common.world.gen.biome.BiomeData;
import cn.leolezury.eternalstarlight.common.world.gen.biomesource.ESBiomeSource;
import cn.leolezury.eternalstarlight.common.world.gen.chunkgenerator.ESChunkGenerator;
import cn.leolezury.eternalstarlight.common.world.gen.surface.OnSurfaceCondition;
import com.mojang.datafixers.util.Pair;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.biome.Climate;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.dimension.DimensionType;
import net.minecraft.world.level.dimension.LevelStem;
import net.minecraft.world.level.levelgen.*;
import net.minecraft.world.level.levelgen.synth.NormalNoise;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.OptionalLong;

public class ESDimensions {
	public static final ResourceKey<Level> STARLIGHT_KEY = ResourceKey.create(Registries.DIMENSION, EternalStarlight.id("starlight"));
	public static final ResourceKey<LevelStem> STARLIGHT_LEVEL_STEM = ResourceKey.create(Registries.LEVEL_STEM, EternalStarlight.id("starlight"));
	public static final ResourceKey<DimensionType> STARLIGHT_TYPE = ResourceKey.create(Registries.DIMENSION_TYPE, EternalStarlight.id("starlight"));
	public static final ResourceKey<NoiseGeneratorSettings> STARLIGHT_NOISE_SETTINGS = ResourceKey.create(Registries.NOISE_SETTINGS, EternalStarlight.id("starlight"));

	public static final int SEA_LEVEL = 48;

	private static SurfaceRules.RuleSource makeAbyss() {
		return SurfaceRules.sequence(
			SurfaceRules.ifTrue(SurfaceRules.not(SurfaceRules.yBlockCheck(VerticalAnchor.absolute(-25), 1)), SurfaceRules.state(ESBlocks.CRYOBYSSLATE.get().defaultBlockState())),
			SurfaceRules.ifTrue(SurfaceRules.not(SurfaceRules.yBlockCheck(VerticalAnchor.absolute(-10), 1)), SurfaceRules.state(ESBlocks.ABYSSLATE.get().defaultBlockState())),
			SurfaceRules.ifTrue(SurfaceRules.not(SurfaceRules.yBlockCheck(VerticalAnchor.absolute(50), 1)), SurfaceRules.state(ESBlocks.THERMABYSSLATE.get().defaultBlockState()))
		);
	}

	private static SurfaceRules.RuleSource makeSurfaceRule() {
		SurfaceRules.RuleSource surface = SurfaceRules.sequence(
			SurfaceRules.ifTrue(
				SurfaceRules.ON_FLOOR,
				SurfaceRules.sequence(
					SurfaceRules.ifTrue(SurfaceRules.isBiome(ESBiomes.STARLIT_SEA, ESBiomes.SPIRAL_KELP_FOREST), SurfaceRules.state(ESBlocks.DUSTED_GRAVEL.get().defaultBlockState())),
					SurfaceRules.ifTrue(SurfaceRules.isBiome(ESBiomes.LUSH_SHALLOW_SEA), SurfaceRules.sequence(SurfaceRules.ifTrue(SurfaceRules.noiseCondition(Noises.ICE, -0.0625, 0.025), SurfaceRules.state(ESBlocks.GLOWING_MOSSY_DUSTED_GRAVEL.get().defaultBlockState())), SurfaceRules.state(ESBlocks.MOSSY_DUSTED_GRAVEL.get().defaultBlockState()))),
					SurfaceRules.ifTrue(
						SurfaceRules.waterBlockCheck(-1, 0),
						SurfaceRules.sequence(
							SurfaceRules.ifTrue(SurfaceRules.isBiome(ESBiomes.CRYSTALLIZED_DESERT, ESBiomes.SHIMMER_RIVER, ESBiomes.ETHER_RIVER, ESBiomes.WARM_SHORE), SurfaceRules.state(ESBlocks.TWILIGHT_SAND.get().defaultBlockState())),
							SurfaceRules.ifTrue(SurfaceRules.isBiome(ESBiomes.DARK_SWAMP), SurfaceRules.sequence(SurfaceRules.ifTrue(SurfaceRules.waterBlockCheck(0, 0), SurfaceRules.state(ESBlocks.FANTASY_GRASS_BLOCK.get().defaultBlockState())), SurfaceRules.state(ESBlocks.NIGHTFALL_MUD.get().defaultBlockState()))),
							SurfaceRules.sequence(SurfaceRules.ifTrue(SurfaceRules.waterBlockCheck(0, 0), SurfaceRules.state(ESBlocks.NIGHTFALL_GRASS_BLOCK.get().defaultBlockState())), SurfaceRules.state(ESBlocks.NIGHTFALL_DIRT.get().defaultBlockState()))
						)
					)
				)
			),
			SurfaceRules.ifTrue(
				SurfaceRules.UNDER_FLOOR,
				SurfaceRules.sequence(
					SurfaceRules.ifTrue(SurfaceRules.isBiome(ESBiomes.CRYSTALLIZED_DESERT, ESBiomes.SHIMMER_RIVER, ESBiomes.ETHER_RIVER, ESBiomes.WARM_SHORE), SurfaceRules.state(ESBlocks.TWILIGHT_SAND.get().defaultBlockState())),
					SurfaceRules.ifTrue(SurfaceRules.isBiome(ESBiomes.STARLIT_SEA, ESBiomes.SPIRAL_KELP_FOREST, ESBiomes.LUSH_SHALLOW_SEA), SurfaceRules.state(ESBlocks.DUSTED_GRAVEL.get().defaultBlockState())),
					SurfaceRules.ifTrue(SurfaceRules.isBiome(ESBiomes.DARK_SWAMP), SurfaceRules.state(ESBlocks.NIGHTFALL_MUD.get().defaultBlockState())),
					SurfaceRules.state(ESBlocks.NIGHTFALL_DIRT.get().defaultBlockState())
				)
			),
			SurfaceRules.ifTrue(
				SurfaceRules.VERY_DEEP_UNDER_FLOOR,
				SurfaceRules.ifTrue(SurfaceRules.isBiome(ESBiomes.CRYSTALLIZED_DESERT), SurfaceRules.state(ESBlocks.TWILIGHT_SANDSTONE.get().defaultBlockState()))
			)
		);

		return SurfaceRules.sequence(
			SurfaceRules.ifTrue(SurfaceRules.verticalGradient("bedrock_floor", VerticalAnchor.bottom(), VerticalAnchor.aboveBottom(5)), SurfaceRules.state(Blocks.BEDROCK.defaultBlockState())),
			SurfaceRules.ifTrue(SurfaceRules.isBiome(ESBiomes.THE_ABYSS), makeAbyss()),
			SurfaceRules.ifTrue(SurfaceRules.not(SurfaceRules.isBiome(ESBiomes.GRIM_SHORE, ESBiomes.SOLARIS_ISLES)), SurfaceRules.ifTrue(OnSurfaceCondition.INSTANCE, surface)),
			SurfaceRules.ifTrue(SurfaceRules.isBiome(ESBiomes.STARLIGHT_PERMAFROST_FOREST, ESBiomes.PERMAFROST_PEAKS), SurfaceRules.sequence(SurfaceRules.ifTrue(SurfaceRules.verticalGradient("stone", VerticalAnchor.absolute(0), VerticalAnchor.absolute(8)), SurfaceRules.state(ESBlocks.HAZE_ICE.get().defaultBlockState())), SurfaceRules.state(ESBlocks.ETERNAL_ICE.get().defaultBlockState()))),
			SurfaceRules.ifTrue(SurfaceRules.isBiome(ESBiomes.DARK_SWAMP), SurfaceRules.sequence(
				SurfaceRules.ifTrue(SurfaceRules.not(SurfaceRules.verticalGradient("stone", VerticalAnchor.absolute(32), VerticalAnchor.absolute(40))), SurfaceRules.state(ESBlocks.NIGHTFALL_MUD.get().defaultBlockState())),
				SurfaceRules.ifTrue(SurfaceRules.not(SurfaceRules.verticalGradient("stone", VerticalAnchor.absolute(22), VerticalAnchor.absolute(30))), SurfaceRules.state(ESBlocks.PACKED_NIGHTFALL_MUD.get().defaultBlockState()))
			)),
			SurfaceRules.ifTrue(SurfaceRules.verticalGradient("stone", VerticalAnchor.absolute(0), VerticalAnchor.absolute(8)), SurfaceRules.state(ESBlocks.VOIDSTONE.get().defaultBlockState()))
		);
	}

	private static final ResourceKey<DensityFunction> SHIFT_X = createVanillaDensityFunctionKey("shift_x");
	private static final ResourceKey<DensityFunction> SHIFT_Z = createVanillaDensityFunctionKey("shift_z");

	public static final ResourceKey<DensityFunction> DEPTH = createDensityFunctionKey("depth");

	public static void bootstrapNoiseSettings(BootstrapContext<NoiseGeneratorSettings> context) {
		HolderGetter<DensityFunction> densityFunctions = context.lookup(Registries.DENSITY_FUNCTION);
		HolderGetter<NormalNoise.NoiseParameters> noiseParameters = context.lookup(Registries.NOISE);

		DensityFunction shiftX = getFunction(densityFunctions, SHIFT_X);
		DensityFunction shiftZ = getFunction(densityFunctions, SHIFT_Z);
		DensityFunction temperature = DensityFunctions.shiftedNoise2d(
			shiftX, shiftZ, 0.25, noiseParameters.getOrThrow(Noises.TEMPERATURE)
		);
		DensityFunction vegetation = DensityFunctions.shiftedNoise2d(
			shiftX, shiftZ, 0.25, noiseParameters.getOrThrow(Noises.VEGETATION)
		);
		DensityFunction depth = getFunction(densityFunctions, DEPTH);
		NoiseRouter router = new NoiseRouter(
			DensityFunctions.zero(),
			DensityFunctions.zero(),
			DensityFunctions.zero(),
			DensityFunctions.zero(),
			temperature,
			vegetation,
			getFunction(densityFunctions, NoiseRouterData.CONTINENTS),
			getFunction(densityFunctions, NoiseRouterData.EROSION),
			depth,
			getFunction(densityFunctions, NoiseRouterData.RIDGES),
			DensityFunctions.zero(),
			DensityFunctions.zero(),
			DensityFunctions.zero(),
			DensityFunctions.zero(),
			DensityFunctions.zero()
		);

		NoiseGeneratorSettings settings = new NoiseGeneratorSettings(
			NoiseSettings.create(
				-64,
				384,
				1,
				4
			),
			ESBlocks.GRIMSTONE.get().defaultBlockState(),
			Blocks.WATER.defaultBlockState(),
			router,
			makeSurfaceRule(),
			new ESBiomeBuilder().spawnTarget(),
			SEA_LEVEL,
			false,
			false,
			false,
			false
		);
		context.register(STARLIGHT_NOISE_SETTINGS, settings);
	}

	public static void bootstrapDensityFunctions(BootstrapContext<DensityFunction> context) {
		HolderGetter<NormalNoise.NoiseParameters> noiseParameters = context.lookup(Registries.NOISE);
		context.register(DEPTH, DensityFunctions.add(
			DensityFunctions.yClampedGradient(-64, 320, 1.5, -1.5),
			DensityFunctions.flatCache(
				DensityFunctions.cache2d(
					DensityFunctions.mul(DensityFunctions.constant(0.03), DensityFunctions.noise(noiseParameters.getOrThrow(ESNoises.DEPTH_OFFSET), 0.3, 0.0))
				)
			)
		));
	}

	private static ResourceKey<DensityFunction> createVanillaDensityFunctionKey(String location) {
		return ResourceKey.create(Registries.DENSITY_FUNCTION, ResourceLocation.withDefaultNamespace(location));
	}

	private static ResourceKey<DensityFunction> createDensityFunctionKey(String location) {
		return ResourceKey.create(Registries.DENSITY_FUNCTION, EternalStarlight.id(location));
	}

	private static DensityFunction getFunction(HolderGetter<DensityFunction> densityFunctions, ResourceKey<DensityFunction> key) {
		return new DensityFunctions.HolderHolder(densityFunctions.getOrThrow(key));
	}

	public static void bootstrapLevelStem(BootstrapContext<LevelStem> context) {
		HolderGetter<BiomeData> biomeData = context.lookup(ESRegistries.BIOME_DATA);
		HolderGetter<DimensionType> dimensionTypes = context.lookup(Registries.DIMENSION_TYPE);
		HolderGetter<NoiseGeneratorSettings> noiseSettings = context.lookup(Registries.NOISE_SETTINGS);

		List<ESBiomeSource.RiverEntry> rivers = List.of(
			new ESBiomeSource.RiverEntry(
				biomeData.getOrThrow(ESBiomeData.SHIMMER_RIVER), 4.0f,
				Optional.of(biomeData.getOrThrow(ESBiomeData.SHIMMER_RIVER_TRANSITION)), 6.0f,
				0, false, false),
			new ESBiomeSource.RiverEntry(
				biomeData.getOrThrow(ESBiomeData.ETHER_RIVER), 6.0f,
				Optional.of(biomeData.getOrThrow(ESBiomeData.TORREYA_FOREST)), 16.0f,
				2009, false, false),
			new ESBiomeSource.RiverEntry(
				biomeData.getOrThrow(ESBiomeData.THE_ABYSS), 16.0f,
				Optional.of(biomeData.getOrThrow(ESBiomeData.THE_ABYSS_TRANSITION)), 24.0f,
				707, true, true)
		);

		List<Pair<Climate.ParameterPoint, Holder<BiomeData>>> parameterList = new ArrayList<>();
		new ESBiomeBuilder().addBiomes(p -> parameterList.add(p.mapSecond(biomeData::getOrThrow)));
		Climate.ParameterList<Holder<BiomeData>> climateList = new Climate.ParameterList<>(parameterList);

		LevelStem levelStem = new LevelStem(dimensionTypes.getOrThrow(STARLIGHT_TYPE), new ESChunkGenerator(new ESBiomeSource(climateList, rivers), noiseSettings.getOrThrow(STARLIGHT_NOISE_SETTINGS)));

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
package cn.leolezury.eternalstarlight.common.data;

import cn.leolezury.eternalstarlight.common.EternalStarlight;
import cn.leolezury.eternalstarlight.common.registry.ESBlocks;
import cn.leolezury.eternalstarlight.common.world.gen.biome.BiomeData;
import cn.leolezury.eternalstarlight.common.world.gen.biome.RiverEntry;
import cn.leolezury.eternalstarlight.common.world.gen.biomesource.ESBiomeSource;
import cn.leolezury.eternalstarlight.common.world.gen.chunkgenerator.ESChunkGenerator;
import cn.leolezury.eternalstarlight.common.world.gen.density.RiverValueFunction;
import cn.leolezury.eternalstarlight.common.world.gen.density.SmoothedFunction;
import cn.leolezury.eternalstarlight.common.world.gen.density.SurfaceBiomeHeightFunction;
import cn.leolezury.eternalstarlight.common.world.gen.surface.AboveSurfaceCondition;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.Mth;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.biome.Climate;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.dimension.DimensionType;
import net.minecraft.world.level.dimension.LevelStem;
import net.minecraft.world.level.levelgen.*;
import net.minecraft.world.level.levelgen.synth.BlendedNoise;
import net.minecraft.world.level.levelgen.synth.NormalNoise;

import java.util.List;
import java.util.Optional;
import java.util.OptionalLong;

public class ESDimensions {
	public static final ResourceKey<Level> STARLIGHT_KEY = ResourceKey.create(Registries.DIMENSION, EternalStarlight.id("starlight"));
	public static final ResourceKey<LevelStem> STARLIGHT_LEVEL_STEM = ResourceKey.create(Registries.LEVEL_STEM, EternalStarlight.id("starlight"));
	public static final ResourceKey<DimensionType> STARLIGHT_TYPE = ResourceKey.create(Registries.DIMENSION_TYPE, EternalStarlight.id("starlight"));
	public static final ResourceKey<NoiseGeneratorSettings> STARLIGHT_NOISE_SETTINGS = ResourceKey.create(Registries.NOISE_SETTINGS, EternalStarlight.id("starlight"));

	public static final int SEA_LEVEL = 48;

	private static final int MIN_Y = -64;
	private static final int HEIGHT = 384;
	private static final int MAX_Y = MIN_Y + HEIGHT;

	// 1.0 is 128 blocks below the column surface, -1.0 is 128 blocks above it
	private static final double DEPTH_SCALE = 1.0 / 128.0;
	// one vertical noise cell, so the surface ramp crosses the surface inside a single cell
	private static final double SURFACE_DENSITY_SCALE = 1.0 / 8.0;
	// the cavern noise fades in over this many blocks below the surface, which is what keeps the terrain height exact
	private static final double CAVERN_FADE_START = 6.0;
	private static final double CAVERN_FADE_LENGTH = 18.0;
	private static final double CAVERN_AMPLITUDE = 3.0;
	// and fades out this far above the bottom of the world, so the floor stays sealed
	private static final int CAVERN_BOTTOM_FADE = 16;
	private static final int HEIGHT_SMOOTHING_RADIUS = 12;
	// how much of the amount by which the smoothing lifts a column above its own height is kept. At 1 the banks are one
	// continuous slope, at 0 river beds hold their own height but the channel edge becomes a wall, and the ether thins
	// out as this rises, so it sits in the middle.
	private static final double SMOOTHING_UPWARD_FILL = 0.5;
	// how far below the column floor the surface material rules may still apply
	private static final int SURFACE_RULE_DEPTH = 8;

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
			// without this the floor rules also fire on cave floors, covering the underground in grass and dirt
			SurfaceRules.ifTrue(
				SurfaceRules.not(SurfaceRules.isBiome(ESBiomes.GRIM_SHORE, ESBiomes.SOLARIS_ISLES)),
				SurfaceRules.ifTrue(new AboveSurfaceCondition(SURFACE_RULE_DEPTH), surface)
			),
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

	public static final ResourceKey<DensityFunction> TEMPERATURE = createDensityFunctionKey("temperature");
	public static final ResourceKey<DensityFunction> HUMIDITY = createDensityFunctionKey("humidity");
	public static final ResourceKey<DensityFunction> HEIGHT_NOISE = createDensityFunctionKey("height_noise");
	public static final ResourceKey<DensityFunction> CAVERN_NOISE = createDensityFunctionKey("cavern_noise");
	public static final ResourceKey<DensityFunction> RIVER_SHIFT_X = createDensityFunctionKey("river_shift_x");
	public static final ResourceKey<DensityFunction> RIVER_SHIFT_Z = createDensityFunctionKey("river_shift_z");
	public static final ResourceKey<DensityFunction> RIVER_NOISE = createDensityFunctionKey("river_noise");
	public static final ResourceKey<DensityFunction> RIVER_VALUE = createDensityFunctionKey("river_value");
	public static final ResourceKey<DensityFunction> BIOME_HEIGHT_RAW = createDensityFunctionKey("biome_height_raw");
	public static final ResourceKey<DensityFunction> BIOME_HEIGHT = createDensityFunctionKey("biome_height");
	public static final ResourceKey<DensityFunction> DEPTH = createDensityFunctionKey("depth");
	public static final ResourceKey<DensityFunction> INITIAL_DENSITY = createDensityFunctionKey("initial_density");
	public static final ResourceKey<DensityFunction> FINAL_DENSITY = createDensityFunctionKey("final_density");

	private static List<RiverEntry> createRivers(HolderGetter<BiomeData> biomeData) {
		return List.of(
			new RiverEntry(
				biomeData.getOrThrow(ESBiomeData.SHIMMER_RIVER), 6.0F,
				Optional.of(biomeData.getOrThrow(ESBiomeData.SHIMMER_RIVER_TRANSITION)), 12.0F,
				0, false),
			new RiverEntry(
				biomeData.getOrThrow(ESBiomeData.ETHER_RIVER), 8.0F,
				Optional.of(biomeData.getOrThrow(ESBiomeData.TORREYA_FOREST)), 16.0F,
				2009, false),
			new RiverEntry(
				biomeData.getOrThrow(ESBiomeData.THE_ABYSS), 16.0F,
				Optional.of(biomeData.getOrThrow(ESBiomeData.THE_ABYSS_TRANSITION)), 24.0F,
				707, true)
		);
	}

	public static void bootstrapNoiseSettings(BootstrapContext<NoiseGeneratorSettings> context) {
		HolderGetter<DensityFunction> densityFunctions = context.lookup(Registries.DENSITY_FUNCTION);
		HolderGetter<NormalNoise.NoiseParameters> noiseParameters = context.lookup(Registries.NOISE);
		NoiseRouter router = new NoiseRouter(
			DensityFunctions.noise(noiseParameters.getOrThrow(Noises.AQUIFER_BARRIER), 0.5),
			DensityFunctions.noise(noiseParameters.getOrThrow(Noises.AQUIFER_FLUID_LEVEL_FLOODEDNESS), 0.67),
			DensityFunctions.noise(noiseParameters.getOrThrow(Noises.AQUIFER_FLUID_LEVEL_SPREAD), 0.7142857142857143),
			DensityFunctions.noise(noiseParameters.getOrThrow(Noises.AQUIFER_LAVA)),
			getFunction(densityFunctions, TEMPERATURE),
			getFunction(densityFunctions, HUMIDITY),
			getFunction(densityFunctions, NoiseRouterData.CONTINENTS),
			getFunction(densityFunctions, NoiseRouterData.EROSION),
			getFunction(densityFunctions, DEPTH),
			getFunction(densityFunctions, NoiseRouterData.RIDGES),
			getFunction(densityFunctions, INITIAL_DENSITY),
			getFunction(densityFunctions, FINAL_DENSITY),
			// all three vein slots: ore veins are disabled
			DensityFunctions.zero(),
			DensityFunctions.zero(),
			DensityFunctions.zero()
		);

		NoiseGeneratorSettings settings = new NoiseGeneratorSettings(
			NoiseSettings.create(MIN_Y, HEIGHT, 1, 2),
			ESBlocks.GRIMSTONE.get().defaultBlockState(),
			Blocks.WATER.defaultBlockState(),
			router,
			makeSurfaceRule(),
			new ESBiomeBuilder().spawnTarget(),
			SEA_LEVEL,
			false,
			true,
			false,
			false
		);
		context.register(STARLIGHT_NOISE_SETTINGS, settings);
	}

	public static void bootstrapDensityFunctions(BootstrapContext<DensityFunction> context) {
		HolderGetter<DensityFunction> densityFunctions = context.lookup(Registries.DENSITY_FUNCTION);
		HolderGetter<NormalNoise.NoiseParameters> noiseParameters = context.lookup(Registries.NOISE);
		HolderGetter<BiomeData> biomeData = context.lookup(ESRegistries.BIOME_DATA);
		Holder<Climate.ParameterList<Holder<BiomeData>>> surfaceClimate = context.lookup(ESRegistries.SURFACE_CLIMATE).getOrThrow(ESSurfaceClimate.STARLIGHT);
		List<RiverEntry> rivers = createRivers(biomeData);

		DensityFunction shiftX = getFunction(densityFunctions, SHIFT_X);
		DensityFunction shiftZ = getFunction(densityFunctions, SHIFT_Z);

		context.register(TEMPERATURE, DensityFunctions.shiftedNoise2d(shiftX, shiftZ, 0.25, noiseParameters.getOrThrow(Noises.TEMPERATURE)));
		context.register(HUMIDITY, DensityFunctions.shiftedNoise2d(shiftX, shiftZ, 0.25, noiseParameters.getOrThrow(Noises.VEGETATION)));
		// the same noise at three coordinate scales
		Holder<NormalNoise.NoiseParameters> heightNoise = noiseParameters.getOrThrow(ESNoises.BIOME_HEIGHT);
		context.register(HEIGHT_NOISE, DensityFunctions.cache2d(DensityFunctions.add(
			DensityFunctions.mul(DensityFunctions.constant(0.7), DensityFunctions.noise(heightNoise, 1.0, 0.0)),
			DensityFunctions.add(
				DensityFunctions.mul(DensityFunctions.constant(0.2), DensityFunctions.noise(heightNoise, 0.4, 0.0)),
				DensityFunctions.mul(DensityFunctions.constant(0.1), DensityFunctions.noise(heightNoise, 0.15, 0.0))
			)
		)));
		// the noise the overworld carves its cheese caves with: features 80 blocks wide and 160 blocks tall
		context.register(CAVERN_NOISE, BlendedNoise.createUnseeded(0.25, 0.125, 80.0, 160.0, 8.0));
		// no cache markers on the shifts: quantised shifts would shatter the river banks, which the river field
		// differentiates over one block steps
		context.register(RIVER_SHIFT_X, DensityFunctions.shiftA(noiseParameters.getOrThrow(ESNoises.RIVER_SHIFT)));
		context.register(RIVER_SHIFT_Z, DensityFunctions.shiftB(noiseParameters.getOrThrow(ESNoises.RIVER_SHIFT)));
		context.register(RIVER_NOISE, DensityFunctions.shiftedNoise2d(
			getFunction(densityFunctions, RIVER_SHIFT_X), getFunction(densityFunctions, RIVER_SHIFT_Z), 1.0,
			noiseParameters.getOrThrow(ESNoises.RIVER)));
		context.register(RIVER_VALUE, new RiverValueFunction(getFunction(densityFunctions, RIVER_NOISE)));

		context.register(BIOME_HEIGHT_RAW, DensityFunctions.flatCache(new SurfaceBiomeHeightFunction(
			surfaceClimate,
			getFunction(densityFunctions, TEMPERATURE),
			getFunction(densityFunctions, HUMIDITY),
			getFunction(densityFunctions, NoiseRouterData.CONTINENTS),
			getFunction(densityFunctions, NoiseRouterData.EROSION),
			getFunction(densityFunctions, NoiseRouterData.RIDGES),
			getFunction(densityFunctions, HEIGHT_NOISE),
			getFunction(densityFunctions, RIVER_VALUE),
			rivers
		)));
		context.register(BIOME_HEIGHT, DensityFunctions.flatCache(new SmoothedFunction(
			getFunction(densityFunctions, BIOME_HEIGHT_RAW), HEIGHT_SMOOTHING_RADIUS, SMOOTHING_UPWARD_FILL
		)));

		DensityFunction y = DensityFunctions.yClampedGradient(MIN_Y, MAX_Y, MIN_Y, MAX_Y);
		// The depth axis the biome table reads and the terrain ramp read the same smoothed height, which is what keeps
		// biome bands aligned with the real surface. They are split into two nodes so the biome lookup stays cacheable.
		DensityFunction biomeHeight = getFunction(densityFunctions, BIOME_HEIGHT);
		DensityFunction toSurface = DensityFunctions.add(biomeHeight, DensityFunctions.mul(DensityFunctions.constant(-1.0), y));
		context.register(DEPTH, DensityFunctions.mul(toSurface, DensityFunctions.constant(DEPTH_SCALE)));

		DensityFunction ramp = DensityFunctions.mul(toSurface, DensityFunctions.constant(SURFACE_DENSITY_SCALE)).clamp(-1.0, 1.0);
		context.register(INITIAL_DENSITY, ramp);

		// Overworld style caverns, faded out near the surface and near the bottom of the world so the terrain height
		// stays exact and the floor stays sealed. The noise may only ever take density away.
		DensityFunction cavernFade = DensityFunctions.mul(
			DensityFunctions.add(toSurface, DensityFunctions.constant(-CAVERN_FADE_START)).clamp(0.0, CAVERN_FADE_LENGTH),
			DensityFunctions.constant(1.0 / CAVERN_FADE_LENGTH)
		);
		DensityFunction caverns = DensityFunctions.mul(
			DensityFunctions.mul(cavernFade, DensityFunctions.yClampedGradient(MIN_Y, MIN_Y + CAVERN_BOTTOM_FADE, 0.0, 1.0)),
			DensityFunctions.mul(DensityFunctions.constant(CAVERN_AMPLITUDE), getFunction(densityFunctions, CAVERN_NOISE))
		);
		// the cavern term is zero above the fade, so the range check keeps the noise out of the sky entirely
		DensityFunction fadedCaverns = DensityFunctions.rangeChoice(
			toSurface, -1000000.0, CAVERN_FADE_START, DensityFunctions.zero(), caverns
		);
		context.register(FINAL_DENSITY, DensityFunctions.interpolated(DensityFunctions.min(
			ramp,
			DensityFunctions.add(ramp, fadedCaverns)
		)));
	}

	/**
	 * The height the terrain actually starts at in this column, for callers that walk blocks and have to tell terrain
	 * from what the aquifer sealed into it. The block filler decides solid or air from the trilinear interpolation of the
	 * four cell corner heights, so this solves that very interpolation for zero instead of sampling one corner: terrain
	 * only exists strictly below the result, which is what makes it safe to run a block pass off this number. The corner
	 * heights come from the existing per column caches, so this costs arithmetic only.
	 */
	public static double surfaceHeight(DensityFunction depth, int x, int z) {
		int cornerX = x & ~3;
		int cornerZ = z & ~3;
		double height00 = heightAt(depth, cornerX, cornerZ);
		double height10 = heightAt(depth, cornerX + 4, cornerZ);
		double height01 = heightAt(depth, cornerX, cornerZ + 4);
		double height11 = heightAt(depth, cornerX + 4, cornerZ + 4);
		double tx = (x - cornerX) * 0.25;
		double tz = (z - cornerZ) * 0.25;
		double low = Math.min(Math.min(height00, height10), Math.min(height01, height11)) - 8.0;
		double high = Math.max(Math.max(height00, height10), Math.max(height01, height11)) + 8.0;
		for (int i = 0; i < 16; i++) {
			double mid = (low + high) * 0.5;
			if (interpolatedDensity(mid, tx, tz, height00, height10, height01, height11) > 0.0) {
				low = mid;
			} else {
				high = mid;
			}
		}
		return high;
	}

	private static double heightAt(DensityFunction depth, int x, int z) {
		return depth.compute(new DensityFunction.SinglePointContext(x, 0, z)) / DEPTH_SCALE;
	}

	/**
	 * The filler's trilinear interpolation: the ramp is linear in y inside a cell, so this is the y lerp of the two
	 * bilinear blends of the clamped corner heights at the cell's y levels.
	 */
	private static double interpolatedDensity(double y, double tx, double tz, double height00, double height10, double height01, double height11) {
		int level = Mth.floorDiv(Mth.floor(y) - MIN_Y, 8) * 8 + MIN_Y;
		return Mth.lerp((y - level) * SURFACE_DENSITY_SCALE,
			blendedDensity(level, tx, tz, height00, height10, height01, height11),
			blendedDensity(level + 8, tx, tz, height00, height10, height01, height11)
		);
	}

	private static double blendedDensity(double y, double tx, double tz, double height00, double height10, double height01, double height11) {
		return Mth.lerp2(tx, tz,
			Mth.clamp((height00 - y) * SURFACE_DENSITY_SCALE, -1.0, 1.0),
			Mth.clamp((height10 - y) * SURFACE_DENSITY_SCALE, -1.0, 1.0),
			Mth.clamp((height01 - y) * SURFACE_DENSITY_SCALE, -1.0, 1.0),
			Mth.clamp((height11 - y) * SURFACE_DENSITY_SCALE, -1.0, 1.0)
		);
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
		HolderGetter<DimensionType> dimensionTypes = context.lookup(Registries.DIMENSION_TYPE);
		HolderGetter<NoiseGeneratorSettings> noiseSettings = context.lookup(Registries.NOISE_SETTINGS);
		HolderGetter<DensityFunction> densityFunctions = context.lookup(Registries.DENSITY_FUNCTION);
		HolderGetter<BiomeData> biomeData = context.lookup(ESRegistries.BIOME_DATA);
		Holder<Climate.ParameterList<Holder<BiomeData>>> surfaceClimate = context.lookup(ESRegistries.SURFACE_CLIMATE).getOrThrow(ESSurfaceClimate.STARLIGHT);

		ESBiomeSource biomeSource = new ESBiomeSource(surfaceClimate, densityFunctions.getOrThrow(RIVER_VALUE), createRivers(biomeData));
		LevelStem levelStem = new LevelStem(dimensionTypes.getOrThrow(STARLIGHT_TYPE), new ESChunkGenerator(biomeSource, noiseSettings.getOrThrow(STARLIGHT_NOISE_SETTINGS)));

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

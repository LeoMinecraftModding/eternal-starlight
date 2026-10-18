package cn.leolezury.eternalstarlight.common.world.gen.density;

import cn.leolezury.eternalstarlight.common.data.ESBiomeClimate;
import cn.leolezury.eternalstarlight.common.world.gen.biome.BiomeData;
import cn.leolezury.eternalstarlight.common.world.gen.biome.RiverEntry;
import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.Holder;
import net.minecraft.core.QuartPos;
import net.minecraft.util.KeyDispatchDataCodec;
import net.minecraft.util.Mth;
import net.minecraft.world.level.biome.Climate;
import net.minecraft.world.level.levelgen.DensityFunction;

import java.util.List;
import java.util.Objects;

public final class SurfaceBiomeHeightFunction implements DensityFunction {
	private static final int COLUMN_MASK = ~3;
	private static final int CACHE_SIZE = 1024;
	public static final double DEFAULT_BANK_FADE = 16.0;

	private final Holder<Climate.ParameterList<Holder<BiomeData>>> biomes;
	private final DensityFunction temperature;
	private final DensityFunction humidity;
	private final DensityFunction continentalness;
	private final DensityFunction erosion;
	private final DensityFunction weirdness;
	private final DensityFunction noise;
	private final DensityFunction riverValue;
	private final List<RiverEntry> rivers;
	private final int minHeight;
	private final int maxHeight;
	private final double bankFade;
	private final ThreadLocal<HeightCache> heightCache = ThreadLocal.withInitial(HeightCache::new);

	public SurfaceBiomeHeightFunction(
		Holder<Climate.ParameterList<Holder<BiomeData>>> biomes,
		DensityFunction temperature,
		DensityFunction humidity,
		DensityFunction continentalness,
		DensityFunction erosion,
		DensityFunction weirdness,
		DensityFunction noise,
		DensityFunction riverValue,
		List<RiverEntry> rivers,
		int minHeight,
		int maxHeight,
		double bankFade
	) {
		this.biomes = biomes;
		this.temperature = temperature;
		this.humidity = humidity;
		this.continentalness = continentalness;
		this.erosion = erosion;
		this.weirdness = weirdness;
		this.noise = noise;
		this.riverValue = riverValue;
		this.rivers = rivers;
		this.minHeight = minHeight;
		this.maxHeight = maxHeight;
		this.bankFade = bankFade;
	}

	public static final MapCodec<SurfaceBiomeHeightFunction> DATA_CODEC = RecordCodecBuilder.mapCodec(instance -> instance.group(
		ESBiomeClimate.HOLDER_CODEC.fieldOf("biomes").forGetter(SurfaceBiomeHeightFunction::biomes),
		DensityFunction.HOLDER_HELPER_CODEC.fieldOf("temperature").forGetter(SurfaceBiomeHeightFunction::temperature),
		DensityFunction.HOLDER_HELPER_CODEC.fieldOf("humidity").forGetter(SurfaceBiomeHeightFunction::humidity),
		DensityFunction.HOLDER_HELPER_CODEC.fieldOf("continentalness").forGetter(SurfaceBiomeHeightFunction::continentalness),
		DensityFunction.HOLDER_HELPER_CODEC.fieldOf("erosion").forGetter(SurfaceBiomeHeightFunction::erosion),
		DensityFunction.HOLDER_HELPER_CODEC.fieldOf("weirdness").forGetter(SurfaceBiomeHeightFunction::weirdness),
		DensityFunction.HOLDER_HELPER_CODEC.fieldOf("noise").forGetter(SurfaceBiomeHeightFunction::noise),
		DensityFunction.HOLDER_HELPER_CODEC.fieldOf("river_value").forGetter(SurfaceBiomeHeightFunction::riverValue),
		RiverEntry.CODEC.listOf().fieldOf("rivers").forGetter(SurfaceBiomeHeightFunction::rivers),
		Codec.INT.optionalFieldOf("min_height", -64).forGetter(SurfaceBiomeHeightFunction::minHeight),
		Codec.INT.optionalFieldOf("max_height", 320).forGetter(SurfaceBiomeHeightFunction::maxHeight),
		Codec.DOUBLE.optionalFieldOf("bank_fade", DEFAULT_BANK_FADE).forGetter(SurfaceBiomeHeightFunction::bankFade)
	).apply(instance, SurfaceBiomeHeightFunction::new));
	public static final KeyDispatchDataCodec<SurfaceBiomeHeightFunction> CODEC = KeyDispatchDataCodec.of(DATA_CODEC);

	public Holder<Climate.ParameterList<Holder<BiomeData>>> biomes() {
		return this.biomes;
	}

	public DensityFunction temperature() {
		return this.temperature;
	}

	public DensityFunction humidity() {
		return this.humidity;
	}

	public DensityFunction continentalness() {
		return this.continentalness;
	}

	public DensityFunction erosion() {
		return this.erosion;
	}

	public DensityFunction weirdness() {
		return this.weirdness;
	}

	public DensityFunction noise() {
		return this.noise;
	}

	public DensityFunction riverValue() {
		return this.riverValue;
	}

	public List<RiverEntry> rivers() {
		return this.rivers;
	}

	public int minHeight() {
		return this.minHeight;
	}

	public int maxHeight() {
		return this.maxHeight;
	}

	public double bankFade() {
		return this.bankFade;
	}

	@Override
	public double compute(DensityFunction.FunctionContext context) {
		return columnHeight(context.blockX(), context.blockZ());
	}

	private double columnHeight(int x, int z) {
		int columnX = x & COLUMN_MASK;
		int columnZ = z & COLUMN_MASK;
		HeightCache cache = this.heightCache.get();
		int index = cacheIndex(columnX, columnZ);
		if (cache.valid[index] && cache.x[index] == columnX && cache.z[index] == columnZ) {
			return cache.height[index];
		}
		double height = rawHeight(columnX, columnZ);
		cache.x[index] = columnX;
		cache.z[index] = columnZ;
		cache.height[index] = height;
		cache.valid[index] = true;
		return height;
	}

	private double rawHeight(int x, int z) {
		DensityFunction.SinglePointContext climateContext = new DensityFunction.SinglePointContext(QuartPos.fromBlock(x), 0, QuartPos.fromBlock(z));
		Climate.TargetPoint target = Climate.target(
			(float) this.temperature.compute(climateContext),
			(float) this.humidity.compute(climateContext),
			(float) this.continentalness.compute(climateContext),
			(float) this.erosion.compute(climateContext),
			0.0F,
			(float) this.weirdness.compute(climateContext)
		);
		BiomeData base = this.biomes.value().findValue(target).value();
		double noise = this.noise.compute(new DensityFunction.SinglePointContext(x, 0, z));
		double baseHeight = base.height() + base.variance() * noise;
		if (this.rivers.isEmpty() || !base.hasRivers()) {
			return baseHeight;
		}
		boolean ocean = base.isOcean();
		double height = baseHeight;
		for (RiverEntry entry : this.rivers) {
			if (ocean != entry.oceanOnly()) {
				continue;
			}
			double band = entry.transition().isPresent() ? entry.transitionSize() : entry.size();
			double halfFade = this.bankFade * 0.5;
			double distance = this.riverValue.compute(new DensityFunction.SinglePointContext(x + entry.offset(), 0, z + entry.offset()));
			if (this.bankFade <= 0.0 ? distance >= band : distance >= band + halfFade) {
				continue;
			}
			BiomeData river = entry.river().value();
			BiomeData transition = entry.transition().map(Holder::value).orElse(river);
			double riverHeight = river.height() + river.variance() * noise;
			double transitionHeight = transition.height() + transition.variance() * noise;
			double profile;
			if (this.bankFade <= 0.0) {
				profile = distance <= entry.size() ? riverHeight : (distance <= band ? transitionHeight : baseHeight);
			} else {
				double bed = Mth.lerp(saturate((distance - (entry.size() - halfFade)) / this.bankFade), riverHeight, transitionHeight);
				profile = Mth.lerp(saturate((distance - (band - halfFade)) / this.bankFade), bed, baseHeight);
			}
			height = Math.min(height, profile);
		}
		return height;
	}

	private static double saturate(double value) {
		return Math.clamp(value, 0.0, 1.0);
	}

	private static int cacheIndex(int columnX, int columnZ) {
		return ((columnX >> 2) & 31) << 5 | ((columnZ >> 2) & 31);
	}

	@Override
	public void fillArray(double[] array, DensityFunction.ContextProvider contextProvider) {
		contextProvider.fillAllDirectly(array, this);
	}

	@Override
	public DensityFunction mapAll(DensityFunction.Visitor visitor) {
		return visitor.apply(new SurfaceBiomeHeightFunction(
			this.biomes,
			this.temperature.mapAll(visitor),
			this.humidity.mapAll(visitor),
			this.continentalness.mapAll(visitor),
			this.erosion.mapAll(visitor),
			this.weirdness.mapAll(visitor),
			this.noise.mapAll(visitor),
			this.riverValue.mapAll(visitor),
			this.rivers,
			this.minHeight,
			this.maxHeight,
			this.bankFade
		));
	}

	@Override
	public double minValue() {
		return this.minHeight;
	}

	@Override
	public double maxValue() {
		return this.maxHeight;
	}

	@Override
	public KeyDispatchDataCodec<? extends DensityFunction> codec() {
		return CODEC;
	}

	@Override
	public boolean equals(Object other) {
		return other instanceof SurfaceBiomeHeightFunction function
			&& this.biomes.equals(function.biomes)
			&& this.temperature.equals(function.temperature)
			&& this.humidity.equals(function.humidity)
			&& this.continentalness.equals(function.continentalness)
			&& this.erosion.equals(function.erosion)
			&& this.weirdness.equals(function.weirdness)
			&& this.noise.equals(function.noise)
			&& this.riverValue.equals(function.riverValue)
			&& this.rivers.equals(function.rivers)
			&& this.minHeight == function.minHeight
			&& this.maxHeight == function.maxHeight
			&& this.bankFade == function.bankFade;
	}

	@Override
	public int hashCode() {
		return Objects.hash(this.biomes, this.temperature, this.humidity, this.continentalness, this.erosion, this.weirdness, this.noise, this.riverValue, this.rivers, this.minHeight, this.maxHeight, this.bankFade);
	}

	private static final class HeightCache {
		private final int[] x = new int[CACHE_SIZE];
		private final int[] z = new int[CACHE_SIZE];
		private final double[] height = new double[CACHE_SIZE];
		private final boolean[] valid = new boolean[CACHE_SIZE];
	}
}

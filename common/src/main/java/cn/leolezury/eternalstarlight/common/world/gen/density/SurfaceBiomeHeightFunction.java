package cn.leolezury.eternalstarlight.common.world.gen.density;

import cn.leolezury.eternalstarlight.common.data.ESSurfaceClimate;
import cn.leolezury.eternalstarlight.common.world.gen.biome.BiomeData;
import cn.leolezury.eternalstarlight.common.world.gen.biome.RiverEntry;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.Holder;
import net.minecraft.util.KeyDispatchDataCodec;
import net.minecraft.world.level.biome.Climate;
import net.minecraft.world.level.levelgen.DensityFunction;

import java.util.List;
import java.util.Objects;

/**
 * Surface height of a column's biome, the anchor both the terrain and the relative depth axis are measured from.
 */
public final class SurfaceBiomeHeightFunction implements DensityFunction {
	private static final int MIN_HEIGHT = -64;
	private static final int MAX_HEIGHT = 320;
	/**
	 * Heights are per column, so quantising to the noise chunk cell grid makes them cacheable.
	 */
	private static final int COLUMN_MASK = ~3;
	private static final int CACHE_SIZE = 1024;
	private static final int CACHE_MASK = CACHE_SIZE - 1;

	private final Holder<Climate.ParameterList<Holder<BiomeData>>> biomes;
	private final DensityFunction temperature;
	private final DensityFunction humidity;
	private final DensityFunction continentalness;
	private final DensityFunction erosion;
	private final DensityFunction weirdness;
	private final DensityFunction noise;
	private final DensityFunction riverValue;
	private final List<RiverEntry> rivers;
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
		List<RiverEntry> rivers
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
	}

	public static final MapCodec<SurfaceBiomeHeightFunction> DATA_CODEC = RecordCodecBuilder.mapCodec(instance -> instance.group(
		ESSurfaceClimate.HOLDER_CODEC.fieldOf("biomes").forGetter(SurfaceBiomeHeightFunction::biomes),
		DensityFunction.HOLDER_HELPER_CODEC.fieldOf("temperature").forGetter(SurfaceBiomeHeightFunction::temperature),
		DensityFunction.HOLDER_HELPER_CODEC.fieldOf("humidity").forGetter(SurfaceBiomeHeightFunction::humidity),
		DensityFunction.HOLDER_HELPER_CODEC.fieldOf("continentalness").forGetter(SurfaceBiomeHeightFunction::continentalness),
		DensityFunction.HOLDER_HELPER_CODEC.fieldOf("erosion").forGetter(SurfaceBiomeHeightFunction::erosion),
		DensityFunction.HOLDER_HELPER_CODEC.fieldOf("weirdness").forGetter(SurfaceBiomeHeightFunction::weirdness),
		DensityFunction.HOLDER_HELPER_CODEC.fieldOf("noise").forGetter(SurfaceBiomeHeightFunction::noise),
		DensityFunction.HOLDER_HELPER_CODEC.fieldOf("river_value").forGetter(SurfaceBiomeHeightFunction::riverValue),
		RiverEntry.CODEC.listOf().fieldOf("rivers").forGetter(SurfaceBiomeHeightFunction::rivers)
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

	@Override
	public double compute(DensityFunction.FunctionContext context) {
		return columnHeight(context.blockX(), context.blockY(), context.blockZ());
	}

	/**
	 * The smoothing node samples many neighbouring columns, so the cache saves recomputing the same one.
	 */
	private double columnHeight(int x, int y, int z) {
		int columnX = x & COLUMN_MASK;
		int columnZ = z & COLUMN_MASK;
		HeightCache cache = this.heightCache.get();
		int index = ((columnX >> 2) * 961 + (columnZ >> 2)) & CACHE_MASK;
		if (cache.valid[index] && cache.x[index] == columnX && cache.z[index] == columnZ) {
			return cache.height[index];
		}
		double height = rawHeight(columnX, y, columnZ);
		cache.x[index] = columnX;
		cache.z[index] = columnZ;
		cache.height[index] = height;
		cache.valid[index] = true;
		return height;
	}

	private double rawHeight(int x, int y, int z) {
		Climate.TargetPoint target = Climate.target(
			(float) this.temperature.compute(new DensityFunction.SinglePointContext(x, y, z)),
			(float) this.humidity.compute(new DensityFunction.SinglePointContext(x, y, z)),
			(float) this.continentalness.compute(new DensityFunction.SinglePointContext(x, y, z)),
			(float) this.erosion.compute(new DensityFunction.SinglePointContext(x, y, z)),
			0.0F,
			(float) this.weirdness.compute(new DensityFunction.SinglePointContext(x, y, z))
		);
		Holder<BiomeData> base = this.biomes.value().findValue(target);
		BiomeData data = RiverEntry.resolve(base, this.rivers, this.riverValue, x, y, z).value();
		return data.height() + data.variance() * this.noise.compute(new DensityFunction.SinglePointContext(x, y, z));
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
			this.rivers
		));
	}

	@Override
	public double minValue() {
		return MIN_HEIGHT;
	}

	@Override
	public double maxValue() {
		return MAX_HEIGHT;
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
			&& this.rivers.equals(function.rivers);
	}

	@Override
	public int hashCode() {
		return Objects.hash(this.biomes, this.temperature, this.humidity, this.continentalness, this.erosion, this.weirdness, this.noise, this.riverValue, this.rivers);
	}

	private static final class HeightCache {
		private final int[] x = new int[CACHE_SIZE];
		private final int[] z = new int[CACHE_SIZE];
		private final double[] height = new double[CACHE_SIZE];
		private final boolean[] valid = new boolean[CACHE_SIZE];
	}
}

package cn.leolezury.eternalstarlight.common.world.gen.density;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.util.KeyDispatchDataCodec;
import net.minecraft.world.level.levelgen.DensityFunction;

import java.util.Objects;

/**
 * Disc shaped weighted average of the wrapped function, used to blur the surface height across biome borders.
 * {@code upwardFill} is how much of the amount by which the average sits above the column's own height is kept: at 1
 * every dip is filled, at 0 river beds keep their own height while their banks are still blended down towards them.
 */
public final class SmoothedFunction implements DensityFunction {
	private static final int TAP_STEP = 4;
	private static final int COLUMN_MASK = ~3;
	private static final int CACHE_SIZE = 1024;
	private static final int CACHE_MASK = CACHE_SIZE - 1;

	private final DensityFunction input;
	private final int radius;
	private final double upwardFill;
	/**
	 * One weight per tap, precomputed, see the constructor.
	 */
	private final double[] weights;
	private final double totalWeight;
	private final int taps;
	private final ThreadLocal<ColumnCache> columnCache = ThreadLocal.withInitial(ColumnCache::new);

	public SmoothedFunction(DensityFunction input, int radius, double upwardFill) {
		this.input = input;
		this.radius = radius;
		this.upwardFill = upwardFill;
		// A tap stands for a whole four block column, so its weight is the cone integrated over that column, sampled once
		// per block. Weighing the tap by the cone's value at its corner instead leaves the kernel with a square support,
		// and that is what makes smoothed features read as rounded squares rather than circles.
		this.taps = 2 * radius / TAP_STEP + 1;
		this.weights = new double[this.taps * this.taps];
		double total = 0.0;
		for (int i = 0; i < this.taps; i++) {
			for (int j = 0; j < this.taps; j++) {
				double weight = 0.0;
				for (int x = 0; x < TAP_STEP; x++) {
					for (int z = 0; z < TAP_STEP; z++) {
						double dx = offset(i) + x - (TAP_STEP - 1) * 0.5;
						double dz = offset(j) + z - (TAP_STEP - 1) * 0.5;
						weight += Math.max(0.0, radius - Math.sqrt(dx * dx + dz * dz));
					}
				}
				this.weights[i * this.taps + j] = weight;
				total += weight;
			}
		}
		this.totalWeight = total;
	}

	private int offset(int tap) {
		return -this.radius + tap * TAP_STEP;
	}

	public static final MapCodec<SmoothedFunction> DATA_CODEC = RecordCodecBuilder.mapCodec(instance -> instance.group(
		DensityFunction.HOLDER_HELPER_CODEC.fieldOf("input").forGetter(SmoothedFunction::input),
		Codec.INT.fieldOf("radius").forGetter(SmoothedFunction::radius),
		Codec.DOUBLE.fieldOf("upward_fill").forGetter(SmoothedFunction::upwardFill)
	).apply(instance, SmoothedFunction::new));
	public static final KeyDispatchDataCodec<SmoothedFunction> CODEC = KeyDispatchDataCodec.of(DATA_CODEC);

	public DensityFunction input() {
		return this.input;
	}

	public int radius() {
		return this.radius;
	}

	public double upwardFill() {
		return this.upwardFill;
	}

	@Override
	public double compute(DensityFunction.FunctionContext context) {
		return columnHeight(context.blockX(), context.blockY(), context.blockZ());
	}

	/**
	 * Callers sample whole columns, so a per thread per column cache saves dozens of tap sets.
	 */
	private double columnHeight(int x, int y, int z) {
		int columnX = x & COLUMN_MASK;
		int columnZ = z & COLUMN_MASK;
		ColumnCache cache = this.columnCache.get();
		int index = ((columnX >> 2) * 961 + (columnZ >> 2)) & CACHE_MASK;
		if (cache.valid[index] && cache.x[index] == columnX && cache.z[index] == columnZ) {
			return cache.height[index];
		}
		double own = this.input.compute(new DensityFunction.SinglePointContext(columnX, y, columnZ));
		double total = 0;
		for (int i = 0; i < this.taps; i++) {
			for (int j = 0; j < this.taps; j++) {
				double tapWeight = this.weights[i * this.taps + j];
				if (tapWeight <= 0.0) {
					continue;
				}
				total += tapWeight * this.input.compute(new DensityFunction.SinglePointContext(columnX + offset(i), y, columnZ + offset(j)));
			}
		}
		double smoothed = this.totalWeight <= 0.0 ? own : total / this.totalWeight;
		double raised = smoothed - own;
		double result = raised > 0.0 ? smoothed - (1.0 - this.upwardFill) * raised : smoothed;
		cache.x[index] = columnX;
		cache.z[index] = columnZ;
		cache.height[index] = result;
		cache.valid[index] = true;
		return result;
	}

	@Override
	public void fillArray(double[] array, DensityFunction.ContextProvider contextProvider) {
		contextProvider.fillAllDirectly(array, this);
	}

	@Override
	public DensityFunction mapAll(DensityFunction.Visitor visitor) {
		return visitor.apply(new SmoothedFunction(this.input.mapAll(visitor), this.radius, this.upwardFill));
	}

	@Override
	public double minValue() {
		return this.input.minValue();
	}

	@Override
	public double maxValue() {
		return this.input.maxValue();
	}

	@Override
	public KeyDispatchDataCodec<? extends DensityFunction> codec() {
		return CODEC;
	}

	@Override
	public boolean equals(Object other) {
		return other instanceof SmoothedFunction function && this.radius == function.radius && this.upwardFill == function.upwardFill && this.input.equals(function.input);
	}

	@Override
	public int hashCode() {
		return Objects.hash(this.input, this.radius, this.upwardFill);
	}

	private static final class ColumnCache {
		private final int[] x = new int[CACHE_SIZE];
		private final int[] z = new int[CACHE_SIZE];
		private final double[] height = new double[CACHE_SIZE];
		private final boolean[] valid = new boolean[CACHE_SIZE];
	}
}

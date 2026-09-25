package cn.leolezury.eternalstarlight.common.world.gen.density;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.util.KeyDispatchDataCodec;
import net.minecraft.world.level.levelgen.DensityFunction;

/**
 * Approximate distance in blocks from a column to the centreline of the river described by the wrapped noise, obtained
 * by dividing the noise value by the length of its horizontal gradient. Normalising by the gradient is what makes the
 * rivers equally wide: the noise itself only decides where the rivers run.
 */
public record RiverValueFunction(DensityFunction noise) implements DensityFunction {
	private static final double MIN_GRADIENT = 1.0E-4;
	private static final double MAX_VALUE = 64.0;

	public static final MapCodec<RiverValueFunction> DATA_CODEC = RecordCodecBuilder.mapCodec(instance -> instance.group(
		DensityFunction.HOLDER_HELPER_CODEC.fieldOf("noise").forGetter(RiverValueFunction::noise)
	).apply(instance, RiverValueFunction::new));
	public static final KeyDispatchDataCodec<RiverValueFunction> CODEC = KeyDispatchDataCodec.of(DATA_CODEC);

	@Override
	public double compute(DensityFunction.FunctionContext context) {
		int x = context.blockX();
		int y = context.blockY();
		int z = context.blockZ();
		double value = this.noise.compute(new DensityFunction.SinglePointContext(x, y, z));
		double gradientX = (this.noise.compute(new DensityFunction.SinglePointContext(x + 1, y, z))
			- this.noise.compute(new DensityFunction.SinglePointContext(x - 1, y, z))) * 0.5;
		double gradientZ = (this.noise.compute(new DensityFunction.SinglePointContext(x, y, z + 1))
			- this.noise.compute(new DensityFunction.SinglePointContext(x, y, z - 1))) * 0.5;
		double gradient = Math.sqrt(gradientX * gradientX + gradientZ * gradientZ);
		return Math.min(Math.abs(value) / Math.max(gradient, MIN_GRADIENT), MAX_VALUE);
	}

	@Override
	public void fillArray(double[] array, DensityFunction.ContextProvider contextProvider) {
		contextProvider.fillAllDirectly(array, this);
	}

	@Override
	public DensityFunction mapAll(DensityFunction.Visitor visitor) {
		return visitor.apply(new RiverValueFunction(this.noise.mapAll(visitor)));
	}

	@Override
	public double minValue() {
		return 0.0;
	}

	@Override
	public double maxValue() {
		return MAX_VALUE;
	}

	@Override
	public KeyDispatchDataCodec<? extends DensityFunction> codec() {
		return CODEC;
	}
}

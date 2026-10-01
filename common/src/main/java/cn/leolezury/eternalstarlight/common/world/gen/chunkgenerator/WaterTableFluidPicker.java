package cn.leolezury.eternalstarlight.common.world.gen.chunkgenerator;

import cn.leolezury.eternalstarlight.common.data.ESDimensions;
import net.minecraft.util.Mth;
import net.minecraft.world.level.levelgen.Aquifer;
import net.minecraft.world.level.levelgen.DensityFunction;

import java.util.Arrays;

public class WaterTableFluidPicker implements Aquifer.FluidPicker {
	private static final int CELL_MASK = ~3;
	private static final int CACHE_SIZE = 1024;
	private static final int CACHE_MASK = CACHE_SIZE - 1;
	private static final double HEIGHT_SCALE = 1.0 / ESDimensions.DEPTH_SCALE;
	private static final ThreadLocal<CellCache> CACHE = ThreadLocal.withInitial(CellCache::new);

	private final Aquifer.FluidPicker vanilla;
	private final DensityFunction depth;

	public WaterTableFluidPicker(Aquifer.FluidPicker vanilla, DensityFunction depth) {
		this.vanilla = vanilla;
		this.depth = depth;
	}

	@Override
	public Aquifer.FluidStatus computeFluid(int x, int y, int z) {
		return this.vanilla.computeFluid(x, y, z);
	}

	public int ground(int x, int z) {
		int cellX = x & CELL_MASK;
		int cellZ = z & CELL_MASK;
		CellCache cache = CACHE.get();
		if (cache.depth != this.depth) {
			cache.depth = this.depth;
			Arrays.fill(cache.valid, false);
		}
		int index = (cellX * 31 + cellZ) & CACHE_MASK;
		if (!cache.valid[index] || cache.cellX[index] != cellX || cache.cellZ[index] != cellZ) {
			double lowest = Double.MAX_VALUE;
			for (int dx = 0; dx <= 4; dx += 4) {
				for (int dz = 0; dz <= 4; dz += 4) {
					lowest = Math.min(lowest, this.depth.compute(new DensityFunction.SinglePointContext(cellX + dx, 0, cellZ + dz)));
				}
			}
			cache.cellX[index] = cellX;
			cache.cellZ[index] = cellZ;
			cache.ground[index] = Mth.ceil(lowest * HEIGHT_SCALE);
			cache.valid[index] = true;
		}
		return cache.ground[index];
	}

	private static final class CellCache {
		private DensityFunction depth;
		private final int[] cellX = new int[CACHE_SIZE];
		private final int[] cellZ = new int[CACHE_SIZE];
		private final int[] ground = new int[CACHE_SIZE];
		private final boolean[] valid = new boolean[CACHE_SIZE];
	}
}

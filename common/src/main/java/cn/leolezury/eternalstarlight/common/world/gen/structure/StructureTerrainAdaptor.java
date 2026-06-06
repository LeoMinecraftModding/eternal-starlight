package cn.leolezury.eternalstarlight.common.world.gen.structure;

import cn.leolezury.eternalstarlight.common.util.FastNoise;
import net.minecraft.world.level.levelgen.structure.BoundingBox;

import java.util.List;

public class StructureTerrainAdaptor {
	public static final int EDGE_MARGIN = 16;
	public static final int MAX_FILL_DEPTH = 30;

	private static final double EDGE_NOISE_AMPLITUDE = 4.0;
	private static final float EDGE_NOISE_SCALE = 0.07f;
	private static final double FILL_NOISE_WEIGHT = 0.22;
	private static final double FILL_THRESHOLD = 0.30;

	private final FastNoise noise;
	private final List<BoundingBox> boxes;

	public StructureTerrainAdaptor(long seed, List<BoundingBox> boxes) {
		this.boxes = boxes;
		this.noise = new FastNoise((int) (seed ^ 0xA7D3F21CL));
		noise.setNoiseType(FastNoise.NoiseType.OPEN_SIMPLEX_2);
		noise.setFrequency(0.06f);
		noise.setFractalType(FastNoise.FractalType.FBM);
		noise.setFractalOctaves(2);
		noise.setFractalLacunarity(2.0f);
		noise.setFractalGain(0.5f);
	}

	public boolean shouldFill(int x, int y, int z, int surfaceHeight) {
		if (boxes.isEmpty()) return false;

		for (BoundingBox box : boxes) {
			int floorY = box.minY();
			if (y >= floorY) continue;

			int depthBelow = floorY - y;
			if (depthBelow > MAX_FILL_DEPTH) continue;

			// horizontal distance with noise-perturbed boundary
			int dx = Math.max(0, Math.max(box.minX() - x, x - box.maxX()));
			int dz = Math.max(0, Math.max(box.minZ() - z, z - box.maxZ()));
			double distToEdge = Math.sqrt((double) dx * dx + (double) dz * dz);

			double edgeNoise = noise.getNoise(x * EDGE_NOISE_SCALE, z * EDGE_NOISE_SCALE);
			double perturbedDist = distToEdge + edgeNoise * EDGE_NOISE_AMPLITUDE;
			if (perturbedDist > EDGE_MARGIN) continue;

			double edgeBlend = perturbedDist <= 0.0 ? 1.0 : 1.0 - smoothstep(perturbedDist / EDGE_MARGIN);

			// blend floor into natural terrain surface
			double targetY = floorY * edgeBlend + surfaceHeight * (1.0 - edgeBlend);
			if (y >= targetY) continue;

			// noise-driven fill below the blended surface
			double distToTarget = targetY - y;
			double surfaceCloseness = Math.min(1.0, distToTarget / 3.0);

			double fillNoise = noise.getNoise(x * 0.06f, z * 0.06f);
			int hash = x * 73856093 ^ y * 19349663 ^ z * 83492791;
			double vertHash = ((hash & 0xFFFF) / 65536.0 - 0.5) * 0.45;
			double noiseVal = fillNoise + vertHash;

			double fillChance = surfaceCloseness + noiseVal * FILL_NOISE_WEIGHT;
			if (fillChance > FILL_THRESHOLD) return true;
		}
		return false;
	}

	private static double smoothstep(double t) {
		t = Math.max(0.0, Math.min(1.0, t));
		return t * t * (3.0 - 2.0 * t);
	}
}

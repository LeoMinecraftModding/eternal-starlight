package cn.leolezury.eternalstarlight.common.world.gen.structure;

import cn.leolezury.eternalstarlight.common.util.ESTags;
import cn.leolezury.eternalstarlight.common.world.gen.chunkgenerator.WaterTableFluidPicker;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.Registries;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.StructureManager;
import net.minecraft.world.level.levelgen.Beardifier;
import net.minecraft.world.level.levelgen.DensityFunction;
import net.minecraft.world.level.levelgen.structure.BoundingBox;
import net.minecraft.world.level.levelgen.structure.Structure;
import net.minecraft.world.level.levelgen.structure.StructurePiece;
import net.minecraft.world.level.levelgen.structure.StructureStart;
import net.minecraft.world.level.levelgen.structure.pools.JigsawJunction;
import net.minecraft.world.level.levelgen.synth.NormalNoise;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

public class StructureBeardFunction extends Beardifier {
	private static final int MARGIN = 24;
	private static final float EDGE_NOISE_SCALE = 0.07F;
	private static final double EDGE_NOISE_AMPLITUDE = 4.0;
	private static final double RAMP_NOISE_SCALE = 0.03;
	private static final double RAMP_NOISE_AMPLITUDE = 2.5;
	private static final double STEP = 2.0;
	private static final double REACH = 40.0;
	private static final int CACHE_SIZE = 1024;
	private static final int CACHE_MASK = CACHE_SIZE - 1;
	private static final NormalNoise NOISE = NormalNoise.create(RandomSource.create(0L), new NormalNoise.NoiseParameters(-4, 1.0, 0.5));
	private static final ThreadLocal<ColumnCache> CACHE = ThreadLocal.withInitial(ColumnCache::new);

	private final Beardifier inside;
	private final List<BoundingBox> boxes;
	private final WaterTableFluidPicker picker;

	public StructureBeardFunction(Beardifier inside, List<BoundingBox> boxes, WaterTableFluidPicker picker) {
		super(new ObjectArrayList<Rigid>().iterator(), new ObjectArrayList<JigsawJunction>().iterator());
		this.inside = inside;
		this.boxes = boxes;
		this.picker = picker;
	}

	public static StructureBeardFunction create(StructureManager structureManager, ChunkPos chunkPos, WaterTableFluidPicker picker) {
		Beardifier inside = Beardifier.forStructuresInChunk(structureManager, chunkPos);
		List<BoundingBox> boxes = new ArrayList<>();
		Set<Structure> adaptive = structureManager.registryAccess()
			.registryOrThrow(Registries.STRUCTURE)
			.getTag(ESTags.Structures.TERRAIN_ADAPTIVE)
			.map(tag -> tag.stream().map(Holder::value).collect(java.util.stream.Collectors.toSet()))
			.orElse(Set.of());
		if (!adaptive.isEmpty()) {
			for (StructureStart start : structureManager.startsForStructure(chunkPos, adaptive::contains)) {
				for (StructurePiece piece : start.getPieces()) {
					if (piece.isCloseToChunk(chunkPos, MARGIN + (int) EDGE_NOISE_AMPLITUDE + 4)) {
						boxes.add(piece.getBoundingBox());
					}
				}
			}
		}
		return new StructureBeardFunction(inside, boxes, picker);
	}

	@Override
	public double compute(DensityFunction.FunctionContext context) {
		double inside = this.inside.compute(context);
		if (this.boxes.isEmpty()) {
			return inside;
		}
		int x = context.blockX();
		int z = context.blockZ();
		ColumnCache cache = CACHE.get();
		if (cache.owner != this) {
			cache.owner = this;
			Arrays.fill(cache.valid, false);
		}
		int index = (x * 31 + z) & CACHE_MASK;
		if (!cache.valid[index] || cache.x[index] != x || cache.z[index] != z) {
			fillColumn(cache, index, x, z);
		}
		double target = cache.target[index];
		if (context.blockY() >= target || target - context.blockY() > REACH) {
			return inside;
		}
		return inside + STEP;
	}

	private void fillColumn(ColumnCache cache, int index, int x, int z) {
		double edge = NOISE.getValue(x * EDGE_NOISE_SCALE, 0.0, z * EDGE_NOISE_SCALE) * EDGE_NOISE_AMPLITUDE;
		double fuzz = NOISE.getValue(x * RAMP_NOISE_SCALE, 0.0, z * RAMP_NOISE_SCALE) * RAMP_NOISE_AMPLITUDE;
		double ground = this.picker.ground(x, z);
		double bestBlend = 0.0;
		double target = ground;
		for (BoundingBox box : this.boxes) {
			double dx = Math.max(0, Math.max(box.minX() - x, x - box.maxX()));
			double dz = Math.max(0, Math.max(box.minZ() - z, z - box.maxZ()));
			double distance = Math.max(0.0, Math.sqrt(dx * dx + dz * dz) + edge);
			if (distance >= MARGIN) {
				continue;
			}
			double t = distance / MARGIN;
			double blend = 1.0 - t * t * (3.0 - 2.0 * t);
			if (blend > bestBlend) {
				bestBlend = blend;
				target = (box.minY() + fuzz) * blend + ground * (1.0 - blend);
			}
		}
		cache.x[index] = x;
		cache.z[index] = z;
		cache.target[index] = target;
		cache.valid[index] = true;
	}

	private static final class ColumnCache {
		private Object owner;
		private final int[] x = new int[CACHE_SIZE];
		private final int[] z = new int[CACHE_SIZE];
		private final double[] target = new double[CACHE_SIZE];
		private final boolean[] valid = new boolean[CACHE_SIZE];
	}
}

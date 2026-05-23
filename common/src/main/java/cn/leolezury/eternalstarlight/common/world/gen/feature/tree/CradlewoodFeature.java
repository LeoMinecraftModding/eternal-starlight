package cn.leolezury.eternalstarlight.common.world.gen.feature.tree;

import cn.leolezury.eternalstarlight.common.registry.ESBlocks;
import cn.leolezury.eternalstarlight.common.util.ESMathUtil;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.util.valueproviders.IntProvider;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.RotatedPillarBlock;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class CradlewoodFeature extends Feature<CradlewoodFeature.Configuration> {
	public CradlewoodFeature(Codec<Configuration> codec) {
		super(codec);
	}

	@Override
	public boolean place(FeaturePlaceContext<Configuration> context) {
		WorldGenLevel level = context.level();
		BlockPos pos = context.origin();
		RandomSource random = context.random();
		List<BlockPos> trunkPositions = new ArrayList<>();
		List<BlockPos> woodPositions = new ArrayList<>();
		List<BlockPos> leavesPositions = new ArrayList<>();
		Configuration config = context.config();
		int height = config.height().sample(random);
		int horizontalOffset = config.horizontalOffset().sample(random);
		int leavesRadius = config.leavesRadius().sample(random);
		int trunkRadius = config.trunkRadius().sample(random);
		float angle = random.nextInt(360) * Mth.DEG_TO_RAD;
		// make a trunk
		BlockPos targetPos = pos.offset((random.nextBoolean() ? 1 : -1) * (int) Math.round(horizontalOffset * Math.sin(angle)), 0, (random.nextBoolean() ? 1 : -1) * (int) Math.round(horizontalOffset * Math.cos(angle)));
		List<int[]> points = ESMathUtil.getBresenham3DPoints(pos.getX(), pos.getY() - height, pos.getZ(), targetPos.getX(), pos.getY() - height, targetPos.getZ());
		points.sort(Comparator.comparingInt(o -> (o[0] - pos.getX()) * (o[0] - pos.getX()) + (o[2] - pos.getZ()) * (o[2] - pos.getZ())));
		int horizontalLength = points.size();
		List<int[]> ellipsePoints = ESMathUtil.getBresenhamEllipsePoints(horizontalLength / 2, pos.getY(), horizontalLength / 2 + 1, height);
		ellipsePoints.removeIf(o -> o[1] > pos.getY());
		ellipsePoints.sort(Comparator.comparingInt(o -> o[0]));
		for (int i = 0; i < points.size(); i++) {
			int[] point = points.get(i);
			int lineX = i;
			ellipsePoints.stream().filter(o -> o[0] == lineX).forEach(o -> {
				BlockPos trunkPos = new BlockPos(point[0], o[1], point[2]);
				trunkPositions.add(trunkPos);
			});
		}
		List<BlockPos> nearTop = trunkPositions.stream()
			.filter(o -> (o.getX() - pos.getX()) * (o.getX() - pos.getX()) + (o.getZ() - pos.getZ()) * (o.getZ() - pos.getZ()) < horizontalOffset / 2 * horizontalOffset / 2)
			.sorted(Comparator.comparingInt(BlockPos::getY))
			.toList();
		if (!nearTop.isEmpty() && !points.isEmpty()) {
			int topY = nearTop.getLast().getY();
			for (int i = topY; i <= pos.getY(); i++) {
				BlockPos extra = new BlockPos(points.getFirst()[0], i, points.getFirst()[2]);
				if (!trunkPositions.contains(extra)) {
					trunkPositions.add(extra);
				}
			}
		}
		List<BlockPos> farTop = trunkPositions.stream()
			.filter(o -> (o.getX() - pos.getX()) * (o.getX() - pos.getX()) + (o.getZ() - pos.getZ()) * (o.getZ() - pos.getZ()) > horizontalOffset / 2 * horizontalOffset / 2)
			.sorted(Comparator.comparingInt(BlockPos::getY))
			.toList();
		if (!farTop.isEmpty()) {
			BlockPos endPos = farTop.getLast();
			trunkPositions.add(endPos.offset(1, 0, 1));
			trunkPositions.add(endPos.offset(1, 0, -1));
			trunkPositions.add(endPos.offset(-1, 0, 1));
			trunkPositions.add(endPos.offset(-1, 0, -1));
			for (int x = 0; x <= leavesRadius; x++) {
				for (int z = 0; z <= leavesRadius; z++) {
					for (int y = 0; y <= leavesRadius / 1.5f; y++) {
						if (ESMathUtil.isPointInOrOnEllipsoid(x, y, z, leavesRadius, leavesRadius / 1.5f, leavesRadius)) {
							for (int i = -1; i <= 1; i += 2) {
								for (int j = -1; j <= 1; j += 2) {
									for (int k = -1; k <= 1; k += 2) {
										BlockPos leavesPos = endPos.offset(i * x, j * y, k * z);
										leavesPositions.add(leavesPos);
										for (Direction direction : Direction.values()) {
											if (random.nextInt(5) == 0) {
												leavesPositions.add(leavesPos.relative(direction));
											}
										}
									}
								}
							}
						}
					}
				}
			}
		}

		int dxLine = targetPos.getX() - pos.getX();
		int dzLine = targetPos.getZ() - pos.getZ();
		Direction.Axis horizontalAxis = Math.abs(dxLine) > Math.abs(dzLine) ? Direction.Axis.X : Direction.Axis.Z;
		int ellipseCenterX = horizontalLength / 2;
		int ellipseRadiusA = horizontalLength / 2 + 1;

		if (trunkRadius > 0) {
			for (BlockPos trunkPos : trunkPositions) {
				Direction.Axis axis = computeAxisForPos(trunkPos, points, ellipseCenterX, ellipseRadiusA, horizontalAxis);
				for (int dr = 1; dr <= trunkRadius; dr++) {
					switch (axis) {
						case X -> {
							for (int dy = -dr; dy <= dr; dy++) {
								for (int dz = -dr; dz <= dr; dz++) {
									if (dy * dy + dz * dz <= dr * dr) {
										woodPositions.add(trunkPos.offset(0, dy, dz));
									}
								}
							}
						}
						case Z -> {
							for (int dx = -dr; dx <= dr; dx++) {
								for (int dy = -dr; dy <= dr; dy++) {
									if (dx * dx + dy * dy <= dr * dr) {
										woodPositions.add(trunkPos.offset(dx, dy, 0));
									}
								}
							}
						}
						case Y -> {
							for (int dx = -dr; dx <= dr; dx++) {
								for (int dz = -dr; dz <= dr; dz++) {
									if (dx * dx + dz * dz <= dr * dr) {
										woodPositions.add(trunkPos.offset(dx, 0, dz));
									}
								}
							}
						}
					}
				}
			}
			woodPositions.removeAll(trunkPositions);
		}

		for (BlockPos trunkPos : trunkPositions) {
			if (!level.isEmptyBlock(trunkPos) && !level.getBlockState(trunkPos).is(BlockTags.REPLACEABLE_BY_TREES)) {
				return false;
			}
		}
		for (BlockPos woodPos : woodPositions) {
			if (!level.isEmptyBlock(woodPos) && !level.getBlockState(woodPos).is(BlockTags.REPLACEABLE_BY_TREES)) {
				return false;
			}
		}
		for (BlockPos leavesPos : leavesPositions) {
			if (!level.isEmptyBlock(leavesPos) && !level.getBlockState(leavesPos).is(BlockTags.REPLACEABLE_BY_TREES)) {
				return false;
			}
		}
		for (BlockPos leavesPos : leavesPositions) {
			setBlock(level, leavesPos, ESBlocks.CRADLEWOOD_LEAVES.get().defaultBlockState());
		}
		for (BlockPos trunkPos : trunkPositions) {
			Direction.Axis axis = computeAxisForPos(trunkPos, points, ellipseCenterX, ellipseRadiusA, horizontalAxis);
			setBlock(level, trunkPos, ESBlocks.CRADLEWOOD_LOG.get().defaultBlockState().setValue(RotatedPillarBlock.AXIS, axis));
		}
		for (BlockPos trunkPos : woodPositions) {
			Direction.Axis axis = computeAxisForPos(trunkPos, points, ellipseCenterX, ellipseRadiusA, horizontalAxis);
			setBlock(level, trunkPos, ESBlocks.CRADLEWOOD_WOOD.get().defaultBlockState().setValue(RotatedPillarBlock.AXIS, axis));
		}
		return true;
	}

	private static Direction.Axis computeAxisForPos(BlockPos pos, List<int[]> bresenhamPoints, int ellipseCenterX, int ellipseRadiusA, Direction.Axis horizontalAxis) {
		int index = findBresenhamIndex(pos, bresenhamPoints);
		if (index < 0) {
			return Direction.Axis.Y;
		}
		double hRel = Math.abs(index - ellipseCenterX);
		if (hRel > ellipseRadiusA * 0.6) {
			return Direction.Axis.Y;
		}
		return horizontalAxis;
	}

	private static int findBresenhamIndex(BlockPos pos, List<int[]> bresenhamPoints) {
		for (int i = 0; i < bresenhamPoints.size(); i++) {
			int[] p = bresenhamPoints.get(i);
			if (p[0] == pos.getX() && p[2] == pos.getZ()) {
				return i;
			}
		}
		int bestIndex = 0;
		int bestDist = Integer.MAX_VALUE;
		for (int i = 0; i < bresenhamPoints.size(); i++) {
			int[] p = bresenhamPoints.get(i);
			int dx = p[0] - pos.getX();
			int dz = p[2] - pos.getZ();
			int dist = dx * dx + dz * dz;
			if (dist < bestDist) {
				bestDist = dist;
				bestIndex = i;
			}
		}
		return bestIndex;
	}

	public record Configuration(IntProvider height, IntProvider horizontalOffset, IntProvider leavesRadius, IntProvider trunkRadius) implements FeatureConfiguration {
		public static final Codec<Configuration> CODEC = RecordCodecBuilder.create((instance) -> instance.group(
			IntProvider.CODEC.fieldOf("height").forGetter(Configuration::height),
			IntProvider.CODEC.fieldOf("horizontal_offset").forGetter(Configuration::horizontalOffset),
			IntProvider.CODEC.fieldOf("leaves_radius").forGetter(Configuration::leavesRadius),
			IntProvider.CODEC.fieldOf("trunk_radius").forGetter(Configuration::trunkRadius)
		).apply(instance, Configuration::new));
	}
}

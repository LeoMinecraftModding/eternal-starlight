package cn.leolezury.eternalstarlight.common.world.gen.feature.tree.foliage;

import cn.leolezury.eternalstarlight.common.registry.ESTreePlacers;
import cn.leolezury.eternalstarlight.common.util.ESMathUtil;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.util.valueproviders.IntProvider;
import net.minecraft.world.level.LevelSimulatedReader;
import net.minecraft.world.level.levelgen.feature.configurations.TreeConfiguration;
import net.minecraft.world.level.levelgen.feature.foliageplacers.FoliagePlacer;
import net.minecraft.world.level.levelgen.feature.foliageplacers.FoliagePlacerType;

import java.util.List;

public class TorreyaFoliagePlacer extends FoliagePlacer {
	public static final MapCodec<TorreyaFoliagePlacer> CODEC = RecordCodecBuilder.mapCodec((instance) -> foliagePlacerParts(instance).apply(instance, TorreyaFoliagePlacer::new));

	public TorreyaFoliagePlacer(IntProvider horizontalRadius, IntProvider yOffset) {
		super(horizontalRadius, yOffset);
	}

	@Override
	protected FoliagePlacerType<TorreyaFoliagePlacer> type() {
		return ESTreePlacers.FOLIAGE_TORREYA.get();
	}

	public static void placeLineFoliage(LevelSimulatedReader level, FoliageSetter setter, TreeConfiguration configuration, RandomSource random, BlockPos fromPos, BlockPos toPos) {
		List<int[]> leavesPositions = ESMathUtil.getBresenham3DPoints(fromPos.getX(), fromPos.getY(), fromPos.getZ(), toPos.getX(), toPos.getY(), toPos.getZ());
		if (!leavesPositions.isEmpty()) {
			for (int i = 0; i < leavesPositions.size(); i++) {
				int[] pos = leavesPositions.get(i);
				int[] lastPos = leavesPositions.get(Math.max(i - 1, 0));
				checkDistanceAndPlaceLeaf(level, setter, random, configuration, fromPos, new BlockPos(pos[0], pos[1], pos[2]));
				checkDistanceAndPlaceLeaf(level, setter, random, configuration, fromPos, new BlockPos(lastPos[0], pos[1], pos[2]));
				checkDistanceAndPlaceLeaf(level, setter, random, configuration, fromPos, new BlockPos(pos[0], pos[1], lastPos[2]));
				checkDistanceAndPlaceLeaf(level, setter, random, configuration, fromPos, new BlockPos(lastPos[0], pos[1], lastPos[2]));
			}
		}
	}

	protected static boolean checkDistanceAndPlaceLeaf(LevelSimulatedReader level, FoliageSetter setter, RandomSource random, TreeConfiguration configuration, BlockPos center, BlockPos pos) {
		if (center.distManhattan(pos) < 7) {
			return tryPlaceLeaf(level, setter, random, configuration, pos);
		}
		return false;
	}

	public static void placeTorreyaFoliage(LevelSimulatedReader level, FoliageSetter setter, TreeConfiguration configuration, RandomSource random, BlockPos centerPos, float xzRadius) {
		placeLineFoliage(level, setter, configuration, random, centerPos, centerPos.offset((int) (xzRadius), 2, 0));
		placeLineFoliage(level, setter, configuration, random, centerPos, centerPos.offset((int) -(xzRadius), 2, 0));
		placeLineFoliage(level, setter, configuration, random, centerPos, centerPos.offset(0, 2, (int) -(xzRadius)));
		placeLineFoliage(level, setter, configuration, random, centerPos, centerPos.offset(0, 2, (int) (xzRadius)));
		double xzOffset = xzRadius / 2f * Math.sqrt(2);
		placeLineFoliage(level, setter, configuration, random, centerPos, centerPos.offset((int) xzOffset, 2, (int) xzOffset));
		placeLineFoliage(level, setter, configuration, random, centerPos, centerPos.offset((int) xzOffset, 2, (int) -xzOffset));
		placeLineFoliage(level, setter, configuration, random, centerPos, centerPos.offset((int) -xzOffset, 2, (int) xzOffset));
		placeLineFoliage(level, setter, configuration, random, centerPos, centerPos.offset((int) -xzOffset, 2, (int) -xzOffset));
	}

	@Override
	protected void createFoliage(LevelSimulatedReader levelReader, FoliageSetter setter, RandomSource random, TreeConfiguration configuration, int trunkHeight, FoliageAttachment foliage, int foliageHeight, int radius, int offset) {
		BlockPos center = foliage.pos().above(offset);
		// no radius offset because of decay distance
		placeTorreyaFoliage(levelReader, setter, configuration, random, center, this.radius.sample(random));
	}

	@Override
	public int foliageHeight(RandomSource random, int i, TreeConfiguration treeConfiguration) {
		return 0;
	}

	@Override
	protected boolean shouldSkipLocation(RandomSource random, int i0, int i1, int i2, int i3, boolean bool) {
		return false;
	}
}


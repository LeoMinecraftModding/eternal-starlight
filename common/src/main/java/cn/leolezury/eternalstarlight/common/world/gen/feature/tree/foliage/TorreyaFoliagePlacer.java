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
		for (int[] pos : leavesPositions) {
			tryPlaceLeaf(level, setter, random, configuration, new BlockPos(pos[0], pos[1], pos[2]));
		}
	}

	public static void placeTorreyaFoliage(LevelSimulatedReader level, FoliageSetter setter, TreeConfiguration configuration, RandomSource random, BlockPos centerPos, float xzRadius, float yRadius) {
		placeLineFoliage(level, setter, configuration, random, centerPos.offset(0, (int) -yRadius, 0), centerPos.offset((int) (xzRadius), (int) yRadius, 0));
		placeLineFoliage(level, setter, configuration, random, centerPos.offset(0, (int) -yRadius, 0), centerPos.offset((int) -(xzRadius), (int) yRadius, 0));
		placeLineFoliage(level, setter, configuration, random, centerPos.offset(0, (int) -yRadius, 0), centerPos.offset(0, (int) yRadius, (int) -(xzRadius)));
		placeLineFoliage(level, setter, configuration, random, centerPos.offset(0, (int) -yRadius, 0), centerPos.offset(0, (int) yRadius, (int) (xzRadius)));
		double xzOffset = xzRadius / 2f * Math.sqrt(2);
		placeLineFoliage(level, setter, configuration, random, centerPos.offset(0, (int) -yRadius, 0), centerPos.offset((int) xzOffset, (int) yRadius, (int) xzOffset));
		placeLineFoliage(level, setter, configuration, random, centerPos.offset(0, (int) -yRadius, 0), centerPos.offset((int) xzOffset, (int) yRadius, (int) -xzOffset));
		placeLineFoliage(level, setter, configuration, random, centerPos.offset(0, (int) -yRadius, 0), centerPos.offset((int) -xzOffset, (int) yRadius, (int) xzOffset));
		placeLineFoliage(level, setter, configuration, random, centerPos.offset(0, (int) -yRadius, 0), centerPos.offset((int) -xzOffset, (int) yRadius, (int) -xzOffset));
	}

	@Override
	protected void createFoliage(LevelSimulatedReader levelReader, FoliageSetter setter, RandomSource random, TreeConfiguration configuration, int trunkHeight, FoliageAttachment foliage, int foliageHeight, int radius, int offset) {
		BlockPos center = foliage.pos().above(offset);
		placeTorreyaFoliage(levelReader, setter, configuration, random, center, foliage.radiusOffset() + this.radius.sample(random), foliage.radiusOffset() + 1.5F + random.nextInt(2));
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



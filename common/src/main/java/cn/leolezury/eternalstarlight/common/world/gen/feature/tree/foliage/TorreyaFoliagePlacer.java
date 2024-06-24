package cn.leolezury.eternalstarlight.common.world.gen.feature.tree.foliage;

import cn.leolezury.eternalstarlight.common.registry.ESPlacers;
import cn.leolezury.eternalstarlight.common.util.ESMathUtil;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.util.valueproviders.IntProvider;
import net.minecraft.world.level.LevelSimulatedReader;
import net.minecraft.world.level.levelgen.feature.TreeFeature;
import net.minecraft.world.level.levelgen.feature.configurations.TreeConfiguration;
import net.minecraft.world.level.levelgen.feature.foliageplacers.FoliagePlacer;
import net.minecraft.world.level.levelgen.feature.foliageplacers.FoliagePlacerType;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;

import java.util.List;
import java.util.function.BiFunction;

public class TorreyaFoliagePlacer extends FoliagePlacer {
	public static final MapCodec<TorreyaFoliagePlacer> CODEC = RecordCodecBuilder.mapCodec((instance) -> foliagePlacerParts(instance).apply(instance, TorreyaFoliagePlacer::new));

	public static final BiFunction<LevelSimulatedReader, BlockPos, Boolean> VALID_TREE_POS = TreeFeature::validTreePos;

	public TorreyaFoliagePlacer(IntProvider horizontalRadius, IntProvider yOffset) {
		super(horizontalRadius, yOffset);
	}

	@Override
	protected FoliagePlacerType<TorreyaFoliagePlacer> type() {
		return ESPlacers.FOLIAGE_TORREYA.get();
	}

	public static void placeFoliage(LevelSimulatedReader level, FoliageSetter setter, BiFunction<LevelSimulatedReader, BlockPos, Boolean> predicate, BlockPos pos, BlockStateProvider config, RandomSource random) {
		if (predicate.apply(level, pos)) {
			setter.set(pos, config.getState(random, pos));
		}
	}

	public static void placeLineFoliage(LevelSimulatedReader level, FoliageSetter setter, BiFunction<LevelSimulatedReader, BlockPos, Boolean> predicate, RandomSource random, BlockPos fromPos, BlockPos toPos, BlockStateProvider provider) {
		List<int[]> leavesPositions = ESMathUtil.getBresenham3DPoints(fromPos.getX(), fromPos.getY(), fromPos.getZ(), toPos.getX(), toPos.getY(), toPos.getZ());
		for (int[] pos : leavesPositions) {
			placeFoliage(level, setter, predicate, new BlockPos(pos[0], pos[1], pos[2]), provider, random);
		}
	}

	public static void placeTorreyaFoliage(LevelSimulatedReader level, FoliageSetter setter, BiFunction<LevelSimulatedReader, BlockPos, Boolean> predicate, RandomSource random, BlockPos centerPos, float xzRadius, float yRadius, BlockStateProvider provider) {
		placeLineFoliage(level, setter, predicate, random, centerPos.offset(0, (int) -yRadius, 0), centerPos.offset((int) (xzRadius), (int) yRadius, 0), provider);
		placeLineFoliage(level, setter, predicate, random, centerPos.offset(0, (int) -yRadius, 0), centerPos.offset((int) -(xzRadius), (int) yRadius, 0), provider);
		placeLineFoliage(level, setter, predicate, random, centerPos.offset(0, (int) -yRadius, 0), centerPos.offset(0, (int) yRadius, (int) -(xzRadius)), provider);
		placeLineFoliage(level, setter, predicate, random, centerPos.offset(0, (int) -yRadius, 0), centerPos.offset(0, (int) yRadius, (int) (xzRadius)), provider);
		double xzOffset = xzRadius / 2f * Math.sqrt(2);
		placeLineFoliage(level, setter, predicate, random, centerPos.offset(0, (int) -yRadius, 0), centerPos.offset((int) xzOffset, (int) yRadius, (int) xzOffset), provider);
		placeLineFoliage(level, setter, predicate, random, centerPos.offset(0, (int) -yRadius, 0), centerPos.offset((int) xzOffset, (int) yRadius, (int) -xzOffset), provider);
		placeLineFoliage(level, setter, predicate, random, centerPos.offset(0, (int) -yRadius, 0), centerPos.offset((int) -xzOffset, (int) yRadius, (int) xzOffset), provider);
		placeLineFoliage(level, setter, predicate, random, centerPos.offset(0, (int) -yRadius, 0), centerPos.offset((int) -xzOffset, (int) yRadius, (int) -xzOffset), provider);
	}

	@Override
	protected void createFoliage(LevelSimulatedReader levelReader, FoliageSetter setter, RandomSource random, TreeConfiguration baseTreeFeatureConfig, int trunkHeight, FoliageAttachment foliage, int foliageHeight, int radius, int offset) {
		BlockPos center = foliage.pos().above(offset);
		placeTorreyaFoliage(levelReader, setter, VALID_TREE_POS, random, center, foliage.radiusOffset() + this.radius.sample(random), foliage.radiusOffset() + 1.5F + random.nextInt(2), baseTreeFeatureConfig.foliageProvider);
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



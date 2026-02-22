package cn.leolezury.eternalstarlight.common.world.gen.feature.tree.foliage;

import cn.leolezury.eternalstarlight.common.registry.ESTreePlacers;
import cn.leolezury.eternalstarlight.common.util.ESMathUtil;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.RandomSource;
import net.minecraft.util.valueproviders.IntProvider;
import net.minecraft.world.level.LevelSimulatedReader;
import net.minecraft.world.level.levelgen.feature.configurations.TreeConfiguration;
import net.minecraft.world.level.levelgen.feature.foliageplacers.FoliagePlacer;
import net.minecraft.world.level.levelgen.feature.foliageplacers.FoliagePlacerType;

public class SpheroidFoliagePlacer extends FoliagePlacer {
	public static final MapCodec<SpheroidFoliagePlacer> CODEC = RecordCodecBuilder.mapCodec((instance) -> foliagePlacerParts(instance).apply(instance, SpheroidFoliagePlacer::new));

	public SpheroidFoliagePlacer(IntProvider horizontalRadius, IntProvider yOffset) {
		super(horizontalRadius, yOffset);
	}

	@Override
	protected FoliagePlacerType<SpheroidFoliagePlacer> type() {
		return ESTreePlacers.FOLIAGE_SPHEROID.get();
	}

	protected static void placeFoliage(LevelSimulatedReader level, FoliageSetter setter, RandomSource random, TreeConfiguration configuration, BlockPos pos) {
		if (tryPlaceLeaf(level, setter, random, configuration, pos)) {
			for (Direction direction : Direction.values()) {
				if (random.nextInt(5) == 0) {
					tryPlaceLeaf(level, setter, random, configuration, pos.relative(direction));
				}
			}
		}
	}

	public static void placeSpheroidFoliage(LevelSimulatedReader level, FoliageSetter setter, TreeConfiguration configuration, RandomSource random, BlockPos centerPos, float xzRadius, float yRadius) {
		for (int x = 0; x <= xzRadius; x++) {
			for (int z = 0; z <= xzRadius; z++) {
				for (int y = 0; y <= yRadius; y++) {
					if (ESMathUtil.isPointInOrOnEllipsoid(x, y, z, xzRadius, yRadius, xzRadius)) {
						placeFoliage(level, setter, random, configuration, centerPos.offset(x, y, z));
						placeFoliage(level, setter, random, configuration, centerPos.offset(x, -y, z));
						placeFoliage(level, setter, random, configuration, centerPos.offset(-x, y, z));
						placeFoliage(level, setter, random, configuration, centerPos.offset(-x, -y, z));
						placeFoliage(level, setter, random, configuration, centerPos.offset(x, y, -z));
						placeFoliage(level, setter, random, configuration, centerPos.offset(x, -y, -z));
						placeFoliage(level, setter, random, configuration, centerPos.offset(-x, y, -z));
						placeFoliage(level, setter, random, configuration, centerPos.offset(-x, -y, -z));
					}
				}
			}
		}
	}

	@Override
	protected void createFoliage(LevelSimulatedReader levelReader, FoliageSetter setter, RandomSource random, TreeConfiguration configuration, int trunkHeight, FoliageAttachment foliage, int foliageHeight, int radius, int offset) {
		BlockPos center = foliage.pos().above(offset);
		placeSpheroidFoliage(levelReader, setter, configuration, random, center, foliage.radiusOffset() + this.radius.sample(random), foliage.radiusOffset() + 1.5F + random.nextInt(2));
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


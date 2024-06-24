package cn.leolezury.eternalstarlight.common.world.gen.feature.tree.foliage;

import cn.leolezury.eternalstarlight.common.registry.ESPlacers;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.util.valueproviders.IntProvider;
import net.minecraft.world.level.LevelSimulatedReader;
import net.minecraft.world.level.levelgen.feature.TreeFeature;
import net.minecraft.world.level.levelgen.feature.configurations.TreeConfiguration;
import net.minecraft.world.level.levelgen.feature.foliageplacers.FoliagePlacer;
import net.minecraft.world.level.levelgen.feature.foliageplacers.FoliagePlacerType;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;

import java.util.function.BiFunction;

public class ScarletFoliagePlacer extends FoliagePlacer {
	public static final MapCodec<ScarletFoliagePlacer> CODEC = RecordCodecBuilder.mapCodec((instance) -> foliagePlacerParts(instance).apply(instance, ScarletFoliagePlacer::new));

	public static final BiFunction<LevelSimulatedReader, BlockPos, Boolean> VALID_TREE_POS = TreeFeature::validTreePos;

	public ScarletFoliagePlacer(IntProvider horizontalRadius, IntProvider yOffset) {
		super(horizontalRadius, yOffset);
	}

	@Override
	protected FoliagePlacerType<?> type() {
		return ESPlacers.FOLIAGE_SCARLET.get();
	}

	public static void placeFoliage(LevelSimulatedReader level, FoliageSetter setter, BiFunction<LevelSimulatedReader, BlockPos, Boolean> predicate, BlockPos pos, BlockStateProvider config, RandomSource random) {
		if (predicate.apply(level, pos)) {
			setter.set(pos, config.getState(random, pos));
		}
	}

	public static void placeSpikeFoliage(LevelSimulatedReader level, FoliageSetter setter, BiFunction<LevelSimulatedReader, BlockPos, Boolean> predicate, RandomSource random, BlockPos centerPos, int xzRadius, int height, BlockStateProvider provider) {
		for (int y = 0; y >= -height; y--) {
			int radius = Mth.lerpInt((float) y / height, xzRadius, 0);
			int radiusNext = Mth.lerpInt((float) (y - 1) / height, xzRadius, 0);
			if (radius < radiusNext) {
				radius = random.nextInt(radius, radiusNext);
			}
			for (int x = -radius; x <= radius; x++) {
				for (int z = -radius; z <= radius; z++) {
					if (x * x + z * z <= radius * radius) {
						placeFoliage(level, setter, VALID_TREE_POS, centerPos.offset(x, y, z), provider, random);
						for (Direction direction : Direction.values()) {
							if (random.nextInt(5) == 0) {
								placeFoliage(level, setter, VALID_TREE_POS, centerPos.offset(x, y, z).relative(direction), provider, random);
							}
						}
					}
				}
			}
		}
	}

	@Override
	protected void createFoliage(LevelSimulatedReader levelReader, FoliageSetter setter, RandomSource random, TreeConfiguration baseTreeFeatureConfig, int trunkHeight, FoliageAttachment foliage, int foliageHeight, int radius, int offset) {
		BlockPos center = foliage.pos().above(offset);
		int xzRadius = foliage.radiusOffset() + this.radius.sample(random);
		int height = (int) (xzRadius * 2.4);
		placeSpikeFoliage(levelReader, setter, VALID_TREE_POS, random, center, xzRadius, height, baseTreeFeatureConfig.foliageProvider);
	}

	@Override
	public int foliageHeight(RandomSource randomSource, int i, TreeConfiguration treeConfiguration) {
		return 0;
	}

	@Override
	protected boolean shouldSkipLocation(RandomSource randomSource, int i, int j, int k, int l, boolean bl) {
		return false;
	}
}

package cn.leolezury.eternalstarlight.common.world.gen.feature.tree.foliage;

import cn.leolezury.eternalstarlight.common.registry.ESTreePlacers;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.util.valueproviders.IntProvider;
import net.minecraft.world.level.LevelSimulatedReader;
import net.minecraft.world.level.levelgen.feature.configurations.TreeConfiguration;
import net.minecraft.world.level.levelgen.feature.foliageplacers.FoliagePlacer;
import net.minecraft.world.level.levelgen.feature.foliageplacers.FoliagePlacerType;

public class ScarletFoliagePlacer extends FoliagePlacer {
	public static final MapCodec<ScarletFoliagePlacer> CODEC = RecordCodecBuilder.mapCodec((instance) -> foliagePlacerParts(instance).apply(instance, ScarletFoliagePlacer::new));

	public ScarletFoliagePlacer(IntProvider horizontalRadius, IntProvider yOffset) {
		super(horizontalRadius, yOffset);
	}

	@Override
	protected FoliagePlacerType<?> type() {
		return ESTreePlacers.FOLIAGE_SCARLET.get();
	}

	public static void placeSpikeFoliage(LevelSimulatedReader level, FoliageSetter setter, TreeConfiguration configuration, RandomSource random, BlockPos centerPos, int xzRadius, int height) {
		for (int y = 0; y >= -height; y--) {
			int radius = Mth.lerpInt((float) y / height, xzRadius, 0);
			int radiusNext = Mth.lerpInt((float) (y - 1) / height, xzRadius, 0);
			if (radius < radiusNext) {
				radius = random.nextInt(radius, radiusNext);
			}
			for (int x = -radius; x <= radius; x++) {
				for (int z = -radius; z <= radius; z++) {
					if (x * x + z * z <= radius * radius) {
						tryPlaceLeaf(level, setter, random, configuration, centerPos.offset(x, y, z));
						for (Direction direction : Direction.values()) {
							if (random.nextInt(5) == 0) {
								tryPlaceLeaf(level, setter, random, configuration, centerPos.offset(x, y, z).relative(direction));
							}
						}
					}
				}
			}
		}
	}

	@Override
	protected void createFoliage(LevelSimulatedReader levelReader, FoliageSetter setter, RandomSource random, TreeConfiguration configuration, int trunkHeight, FoliageAttachment foliage, int foliageHeight, int radius, int offset) {
		BlockPos center = foliage.pos().above(offset);
		int xzRadius = foliage.radiusOffset() + this.radius.sample(random);
		int height = (int) (xzRadius * 2.4);
		placeSpikeFoliage(levelReader, setter, configuration, random, center, xzRadius, height);
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

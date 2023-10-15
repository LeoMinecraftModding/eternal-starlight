package cn.leolezury.eternalstarlight.common.world.gen.feature.tree;

import cn.leolezury.eternalstarlight.common.init.PlacerInit;
import com.mojang.serialization.Codec;
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

import java.util.function.BiFunction;

public class SpheroidFoliagePlacer extends FoliagePlacer {
    public static final Codec<SpheroidFoliagePlacer> CODEC = RecordCodecBuilder.create((instance) -> foliagePlacerParts(instance).apply(instance, SpheroidFoliagePlacer::new));

    private final float horizontalRadius;
    public static final BiFunction<LevelSimulatedReader, BlockPos, Boolean> VALID_TREE_POS = TreeFeature::validTreePos;

    public SpheroidFoliagePlacer(IntProvider horizontalRadius, IntProvider yOffset) {
        super(horizontalRadius, yOffset);
        this.horizontalRadius = (horizontalRadius.getMaxValue() + horizontalRadius.getMinValue()) / 2f;
    }


    protected FoliagePlacerType<SpheroidFoliagePlacer> type() {
        return PlacerInit.FOLIAGE_SPHEROID.get();
    }

    public static void placeFoliage(LevelSimulatedReader level, FoliageSetter setter, BiFunction<LevelSimulatedReader, BlockPos, Boolean> predicate, BlockPos pos, BlockStateProvider config, RandomSource random) {
        if (predicate.apply(level, pos)) {
            setter.set(pos, config.getState(random, pos));
        }
    }

    public static void placeSpheroid(LevelSimulatedReader level, FoliageSetter setter, BiFunction<LevelSimulatedReader, BlockPos, Boolean> predicate, RandomSource random, BlockPos centerPos, float xzRadius, float yRadius, float yOffset, BlockStateProvider provider) {
        float xzRadiusSquared = xzRadius * xzRadius;
        float yRadiusSquared = yRadius * yRadius;
        float xyzRadiusSquared = xzRadiusSquared * yRadiusSquared;

        placeFoliage(level, setter, predicate, centerPos, provider, random);

        for (int y = 0; y <= yRadius; y++) {
            placeFoliage(level, setter, predicate, centerPos.offset(0, y, 0), provider, random);
            placeFoliage(level, setter, predicate, centerPos.offset(0, -y, 0), provider, random);
        }

        for (int x = 0; x <= xzRadius; x++) {
            for (int z = 0; z <= xzRadius; z++) {
                double xzSqr = Math.pow(x, 2) + Math.pow(z, 2);
                if (xzSqr <= xzRadiusSquared) {
                    placeFoliage(level, setter, predicate, centerPos.offset(x, 0, z), provider, random);
                    placeFoliage(level, setter, predicate, centerPos.offset(-x, 0, -z), provider, random);
                    placeFoliage(level, setter, predicate, centerPos.offset(-z, 0, x), provider, random);
                    placeFoliage(level, setter, predicate, centerPos.offset(z, 0, -x), provider, random);

                    for (int i = 1; i <= yRadius; i++) {
                        float xzSquare = (float) (xzSqr * yRadiusSquared);

                        if (xzSquare + Math.pow(i - yOffset, 2) * xzRadiusSquared <= xyzRadiusSquared) {
                            placeFoliage(level, setter, predicate, centerPos.offset(x, i, z), provider, random);
                            placeFoliage(level, setter, predicate, centerPos.offset(-x, i, -z), provider, random);
                            placeFoliage(level, setter, predicate, centerPos.offset(-z, i, x), provider, random);
                            placeFoliage(level, setter, predicate, centerPos.offset(z, i, -x), provider, random);
                        }

                        if (xzSquare + Math.pow(i + yOffset, 2) * xzRadiusSquared <= xyzRadiusSquared) {
                            placeFoliage(level, setter, predicate, centerPos.offset(x, -i, z), provider, random);
                            placeFoliage(level, setter, predicate, centerPos.offset(-x, -i, -z), provider, random);
                            placeFoliage(level, setter, predicate, centerPos.offset(-z, -i, x), provider, random);
                            placeFoliage(level, setter, predicate, centerPos.offset(z, -i, -x), provider, random);
                        }
                    }
                }
            }
        }
    }

    protected void createFoliage(LevelSimulatedReader levelReader, FoliageSetter setter, RandomSource random, TreeConfiguration baseTreeFeatureConfig, int trunkHeight, FoliageAttachment foliage, int foliageHeight, int radius, int offset) {
        BlockPos center = foliage.pos().above(offset);
        placeSpheroid(levelReader, setter, VALID_TREE_POS, random, center, foliage.radiusOffset() + this.horizontalRadius + random.nextInt(4), foliage.radiusOffset() + 1.5F + random.nextInt(2), -0.25F, baseTreeFeatureConfig.foliageProvider);
    }

    public int foliageHeight(RandomSource random, int i, TreeConfiguration treeConfiguration) {
        return 0;
    }


    protected boolean shouldSkipLocation(RandomSource random, int i0, int i1, int i2, int i3, boolean bool) {
        return false;
    }
}



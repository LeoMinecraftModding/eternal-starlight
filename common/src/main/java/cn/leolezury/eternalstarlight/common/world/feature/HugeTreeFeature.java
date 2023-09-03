package cn.leolezury.eternalstarlight.common.world.feature;

import cn.leolezury.eternalstarlight.common.util.ESTags;
import cn.leolezury.eternalstarlight.common.util.FastNoiseLite;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.BlockPos;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.util.valueproviders.IntProvider;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;

import java.util.ArrayList;
import java.util.List;

public class HugeTreeFeature extends ESFeature<HugeTreeFeature.Configuration> {
    public HugeTreeFeature(Codec<Configuration> codec) {
        super(codec);
        this.fastNoiseLite = new FastNoiseLite();
    }

    private final FastNoiseLite fastNoiseLite;

    @Override
    public boolean place(FeaturePlaceContext<Configuration> context) {
        long seed = context.level().getSeed();
        fastNoiseLite.SetSeed((int) seed);

        WorldGenLevel level = context.level();
        BlockPos origin = context.origin();
        RandomSource random = context.random();
        HugeTreeFeature.Configuration configuration = context.config();

        int xzRadius = configuration.xzLeavesRadius.sample(random);
        int yRadius = configuration.yLeavesRadius.sample(random);
        int height = configuration.height.sample(random);
        BlockStateProvider logProvider = configuration.log;
        BlockStateProvider leavesProvider = configuration.leaves;

        genBranchAt(level, origin, random, logProvider, leavesProvider, true, height, xzRadius, yRadius);

        return true;
    }

    private void genBranchAt(WorldGenLevel level, BlockPos origin, RandomSource random, BlockStateProvider logProvider, BlockStateProvider leavesProvider, boolean mainTrunk, int len, int xzRadius, int yRadius) {
        int pitch = mainTrunk ? random.nextInt(-10, 10) : (random.nextBoolean() ? random.nextInt(30, 50) : random.nextInt(-50, -30));
        int roll = mainTrunk ? random.nextInt(-10, 10) : (random.nextBoolean() ? random.nextInt(30, 50) : random.nextInt(-50, -30));
        int i = len / (mainTrunk ? 5 : 2);
        int yStart = mainTrunk ? -5 : 0;
        int r = mainTrunk ? 1 : 0;

        // generate the top leaves
        BlockPos topLeavesPos = rotateBlockPos(origin, origin.offset(0, len, 0), pitch, roll);
        genLeavesAt(level, topLeavesPos, random, logProvider, leavesProvider, xzRadius, yRadius);

        for (int y = yStart; y <= len; y++) {
            if (mainTrunk) {
                for (int x = -r; x <= r; x++) {
                    for (int z = -r; z <= r; z++) {
                        if (x == r && (z == r || z == -r) || x == -r && (z == r || z == -r)) {
                            continue;
                        }
                        if (y > len / 5 && y < len - yRadius * 2 && y % i == 0 && (x == -r || x == r || z == -r || z == r)) {
                            BlockPos rotated = rotateBlockPos(origin, origin.offset(x, y, z), pitch, roll);
                            genLeavesAt(level, rotated, random, logProvider, leavesProvider, (int) (xzRadius / 1.5f), (int) (yRadius / 1.5f));
                            genBranchAt(level, rotated, random, logProvider, leavesProvider, false, random.nextInt(5, 10), (int) (xzRadius / 1.5f), (int) (yRadius / 1.5f));
                        }
                        setBlockWithRotationIfEmpty(level, origin, origin.offset(x, y, z), pitch, roll, logProvider.getState(random, origin.offset(x, y, z)), List.of(ESTags.Blocks.BASE_STONE_STARLIGHT, BlockTags.DIRT, BlockTags.LEAVES));
                    }
                }
            } else {
                setBlockWithRotationIfEmpty(level, origin, origin.offset(0, y, 0), pitch, roll, logProvider.getState(random, origin.offset(0, y, 0)), List.of(ESTags.Blocks.BASE_STONE_STARLIGHT, BlockTags.DIRT, BlockTags.LEAVES));
            }
        }
    }

    private void genLeavesAt(WorldGenLevel level, BlockPos center, RandomSource random, BlockStateProvider logProvider, BlockStateProvider leavesProvider, int xzRadius, int yRadius) {
        List<BlockPos> logs = new ArrayList<>(List.of(center));
        float xzRadiusSquared = xzRadius * xzRadius;
        float yRadiusSquared = yRadius * yRadius;
        float xyzRadiusSquared = xzRadiusSquared * yRadiusSquared;

        for (int y = 0; y <= yRadius; y++) {
            placeFoliage(level, center, center.offset(0, y, 0), logs, logProvider, leavesProvider, random);
            placeFoliage(level, center, center.offset(0, -y, 0), logs, logProvider, leavesProvider, random);
        }

        for (int x = 0; x <= xzRadius; x++) {
            for (int z = 0; z <= xzRadius; z++) {
                double xzSqr = Math.pow(x, 2) + Math.pow(z, 2);
                if (xzSqr <= xzRadiusSquared) {
                    placeFoliage(level, center, center.offset(x, 0, z), logs, logProvider, leavesProvider, random);
                    placeFoliage(level, center, center.offset(-x, 0, -z), logs, logProvider, leavesProvider, random);
                    placeFoliage(level, center, center.offset(-z, 0, x), logs, logProvider, leavesProvider, random);
                    placeFoliage(level, center, center.offset(z, 0, -x), logs, logProvider, leavesProvider, random);

                    for (int i = 1; i <= yRadius; i++) {
                        float xzSquare = (float) (xzSqr * yRadiusSquared);

                        if (xzSquare + Math.pow(i, 2) * xzRadiusSquared <= xyzRadiusSquared) {
                            placeFoliage(level, center, center.offset(x, i, z), logs, logProvider, leavesProvider, random);
                            placeFoliage(level, center, center.offset(-x, i, -z), logs, logProvider, leavesProvider, random);
                            placeFoliage(level, center, center.offset(-z, i, x), logs, logProvider, leavesProvider, random);
                            placeFoliage(level, center, center.offset(z, i, -x), logs, logProvider, leavesProvider, random);
                            placeFoliage(level, center, center.offset(x, -i, z), logs, logProvider, leavesProvider, random);
                            placeFoliage(level, center, center.offset(-x, -i, -z), logs, logProvider, leavesProvider, random);
                            placeFoliage(level, center, center.offset(-z, -i, x), logs, logProvider, leavesProvider, random);
                            placeFoliage(level, center, center.offset(z, -i, -x), logs, logProvider, leavesProvider, random);
                        }
                    }
                }
            }
        }
    }

    public void placeFoliage(WorldGenLevel level, BlockPos center, BlockPos pos, List<BlockPos> logs, BlockStateProvider logProvider, BlockStateProvider leavesProvider, RandomSource random) {
        if (!level.getBlockState(pos).is(BlockTags.FEATURES_CANNOT_REPLACE) && !level.getBlockState(pos).is(BlockTags.LOGS)) {
            setBlock(level, pos, leavesProvider.getState(random, pos));
            boolean decay = true;
            for (BlockPos log : logs) {
                if (log.getCenter().distanceTo(pos.getCenter()) <= 4) {
                    decay = false;
                    break;
                }
            }
            if (decay) {
                float percentage = (float) (3f / center.getCenter().distanceTo(pos.getCenter()));
                BlockPos newLogPos = new BlockPos(Mth.lerpInt(percentage, pos.getX(), center.getX()), Mth.lerpInt(percentage, pos.getY(), center.getY()), Mth.lerpInt(percentage, pos.getZ(), center.getZ()));
                if (!level.getBlockState(newLogPos).is(BlockTags.FEATURES_CANNOT_REPLACE) && !level.getBlockState(newLogPos).is(BlockTags.LOGS)) {
                    setBlock(level, newLogPos, logProvider.getState(random, newLogPos));
                    logs.add(newLogPos);
                }
            }
        }
    }

    public record Configuration(BlockStateProvider log, BlockStateProvider leaves, IntProvider height, IntProvider xzLeavesRadius, IntProvider yLeavesRadius) implements FeatureConfiguration {
        public static final Codec<HugeTreeFeature.Configuration> CODEC = RecordCodecBuilder.create((instance) -> instance.group(BlockStateProvider.CODEC.fieldOf("log").forGetter(HugeTreeFeature.Configuration::log), BlockStateProvider.CODEC.fieldOf("leaves").forGetter(HugeTreeFeature.Configuration::leaves), IntProvider.CODEC.fieldOf("height").forGetter(HugeTreeFeature.Configuration::height), IntProvider.CODEC.fieldOf("xzLeavesRadius").forGetter(HugeTreeFeature.Configuration::xzLeavesRadius), IntProvider.CODEC.fieldOf("yLeavesRadius").forGetter(HugeTreeFeature.Configuration::yLeavesRadius)).apply(instance, HugeTreeFeature.Configuration::new));
    }
}

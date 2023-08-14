package cn.leolezury.eternalstarlight.world.feature;

import cn.leolezury.eternalstarlight.util.FastNoiseLite;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.BlockPos;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.RandomSource;
import net.minecraft.util.valueproviders.IntProvider;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;

import java.util.ArrayList;
import java.util.List;

public class BetterLakeFeature extends SLFeature<BetterLakeFeature.Configuration> {
    public BetterLakeFeature(Codec<Configuration> codec) {
        super(codec);
        this.fastNoiseLite = new FastNoiseLite();
    }

    private final FastNoiseLite fastNoiseLite;

    @Override
    public boolean place(FeaturePlaceContext<BetterLakeFeature.Configuration> context) {
        long seed = context.level().getSeed();
        fastNoiseLite.SetSeed((int) seed);

        WorldGenLevel level = context.level();
        BlockPos origin = context.origin();
        RandomSource random = context.random();
        Configuration configuration = context.config();

        int xRadius = configuration.xRadius.sample(random);
        int yRadius = configuration.yRadius.sample(random);
        int zRadius = configuration.zRadius.sample(random);
        BlockStateProvider fluidProvider = configuration.fluid;
        BlockStateProvider barrierProvider = configuration.barrier;

        List<BlockPos> blockPositions = new ArrayList<>();
        List<BlockPos> fluidPositions = new ArrayList<>();
        List<BlockPos> barrierPositions = new ArrayList<>();

        for (int x = -xRadius; x <= xRadius; x++) {
            for (int z = -zRadius; z <= zRadius; z++) {
                for (int y = -yRadius; y <= yRadius; y++) {
                    BlockPos pos = origin.offset(x, y, z);
                    double param = Math.pow(x, 2) / Math.pow(xRadius, 2) + Math.pow(y, 2) / Math.pow(yRadius, 2) + Math.pow(z, 2) / Math.pow(zRadius, 2);
                    double threshold = 1 + 1.4 * fastNoiseLite.GetNoise(pos.getX(), pos.getY() * 2, pos.getZ());
                    if (param < threshold && !level.isEmptyBlock(pos)) {
                        double distSqr = Math.pow(x, 2) + Math.pow(y, 2) + Math.pow(z, 2);
                        if (distSqr <= (xRadius + 16) * (zRadius + 16)) {
                            blockPositions.add(pos);
                            if (distSqr <= xRadius * zRadius) {
                                if (y <= 0) {
                                    fluidPositions.add(pos);
                                }
                            } else {
                                barrierPositions.add(pos);
                            }
                        }
                    }
                }
            }
        }

        int waterLevel = level.getMaxBuildHeight();

        for (BlockPos fluidPos : fluidPositions) {
            if (!fluidPositions.contains(fluidPos.above()) && fluidPos.getY() < waterLevel) {
                waterLevel = fluidPos.getY();
            }
        }

        for (BlockPos blockPos : blockPositions) {
            if (!level.getBlockState(blockPos).is(BlockTags.FEATURES_CANNOT_REPLACE)) {
                level.setBlock(blockPos, Blocks.AIR.defaultBlockState(), 2);
            }
        }

        for (BlockPos fluidPos : fluidPositions) {
            if (fluidPos.getY() <= waterLevel && !level.getBlockState(fluidPos).is(BlockTags.FEATURES_CANNOT_REPLACE)) {
                BlockState state = fluidProvider.getState(random, fluidPos);
                level.setBlock(fluidPos, state, 2);
                level.scheduleTick(fluidPos, state.getFluidState().getType(), 20);
            }
        }
        for (BlockPos barrierPos : barrierPositions) {
            if (!level.getBlockState(barrierPos).is(BlockTags.FEATURES_CANNOT_REPLACE)) {
                level.setBlock(barrierPos, barrierProvider.getState(random, barrierPos), 2);
                level.scheduleTick(barrierPos, level.getBlockState(barrierPos).getBlock(), 20);
            }
        }

        return true;
    }

    public record Configuration(BlockStateProvider fluid, BlockStateProvider barrier, IntProvider xRadius, IntProvider yRadius, IntProvider zRadius) implements FeatureConfiguration {
        public static final Codec<BetterLakeFeature.Configuration> CODEC = RecordCodecBuilder.create((instance) -> instance.group(BlockStateProvider.CODEC.fieldOf("fluid").forGetter(Configuration::fluid), BlockStateProvider.CODEC.fieldOf("barrier").forGetter(Configuration::barrier), IntProvider.CODEC.fieldOf("xRadius").forGetter(Configuration::xRadius), IntProvider.CODEC.fieldOf("yRadius").forGetter(Configuration::yRadius), IntProvider.CODEC.fieldOf("zRadius").forGetter(Configuration::zRadius)).apply(instance, Configuration::new));
    }
}
package cn.leolezury.eternalstarlight.world.feature;

import cn.leolezury.eternalstarlight.init.BlockInit;
import cn.leolezury.eternalstarlight.util.SLTags;
import com.mojang.serialization.Codec;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;

public class StarlightCrystalFeature extends SLFeature<NoneFeatureConfiguration> {
    public StarlightCrystalFeature(Codec<NoneFeatureConfiguration> codec) {
        super(codec);
    }

    public boolean place(FeaturePlaceContext<NoneFeatureConfiguration> context) {
        RandomSource randomsource = context.random();
        boolean isRed = randomsource.nextBoolean();
        for (int x = -5; x <= 5; x++) {
            for (int z = -5; z <= 5; z++) {
                if (randomsource.nextBoolean()) {
                    if (Mth.abs(x) <= 2 && Mth.abs(z) <= 2) {
                        BlockPos blockpos = context.origin().offset(x, 0, z);

                        WorldGenLevel worldgenlevel;
                        boolean reachedAir = false;
                        for(worldgenlevel = context.level(); (worldgenlevel.isEmptyBlock(blockpos) || !reachedAir) && blockpos.getY() > worldgenlevel.getMinBuildHeight() + 2; blockpos = blockpos.below()) {
                            if (worldgenlevel.isEmptyBlock(blockpos)) {
                                reachedAir = true;
                            }
                        }

                        blockpos = blockpos.above();

                        if (worldgenlevel.getBlockState(blockpos.below()).is(SLTags.Blocks.BASE_STONE_STARLIGHT)) {
                            if (setBlockIfEmpty(worldgenlevel, blockpos, isRed ? BlockInit.RED_STARLIGHT_CRYSTAL_BLOCK.get().defaultBlockState() : BlockInit.BLUE_STARLIGHT_CRYSTAL_BLOCK.get().defaultBlockState())) {
                                setBlockIfEmpty(worldgenlevel, blockpos.above(), isRed ? BlockInit.RED_STARLIGHT_CRYSTAL_CLUSTER.get().defaultBlockState() : BlockInit.BLUE_STARLIGHT_CRYSTAL_CLUSTER.get().defaultBlockState());
                                setBlockIfEmpty(worldgenlevel, blockpos.offset(1, 0, 0), isRed ? BlockInit.RED_STARLIGHT_CRYSTAL_CLUSTER.get().defaultBlockState().setValue(BlockStateProperties.FACING, Direction.EAST) : BlockInit.BLUE_STARLIGHT_CRYSTAL_CLUSTER.get().defaultBlockState().setValue(BlockStateProperties.FACING, Direction.EAST));
                                setBlockIfEmpty(worldgenlevel, blockpos.offset(-1, 0, 0), isRed ? BlockInit.RED_STARLIGHT_CRYSTAL_CLUSTER.get().defaultBlockState().setValue(BlockStateProperties.FACING, Direction.WEST) : BlockInit.BLUE_STARLIGHT_CRYSTAL_CLUSTER.get().defaultBlockState().setValue(BlockStateProperties.FACING, Direction.WEST));
                                setBlockIfEmpty(worldgenlevel, blockpos.offset(0, 0, 1), isRed ? BlockInit.RED_STARLIGHT_CRYSTAL_CLUSTER.get().defaultBlockState().setValue(BlockStateProperties.FACING, Direction.SOUTH) : BlockInit.BLUE_STARLIGHT_CRYSTAL_CLUSTER.get().defaultBlockState().setValue(BlockStateProperties.FACING, Direction.SOUTH));
                                setBlockIfEmpty(worldgenlevel, blockpos.offset(0, 0, -1), isRed ? BlockInit.RED_STARLIGHT_CRYSTAL_CLUSTER.get().defaultBlockState().setValue(BlockStateProperties.FACING, Direction.NORTH) : BlockInit.BLUE_STARLIGHT_CRYSTAL_CLUSTER.get().defaultBlockState().setValue(BlockStateProperties.FACING, Direction.NORTH));
                            }
                        }
                    } else {
                        BlockPos blockpos = context.origin().offset(x, 0, z);

                        WorldGenLevel worldgenlevel;
                        boolean reachedAir = false;
                        for(worldgenlevel = context.level(); (worldgenlevel.isEmptyBlock(blockpos) || !reachedAir) && blockpos.getY() > worldgenlevel.getMinBuildHeight() + 2; blockpos = blockpos.below()) {
                            if (worldgenlevel.isEmptyBlock(blockpos)) {
                                reachedAir = true;
                            }
                        }

                        blockpos = blockpos.above();

                        if (worldgenlevel.getBlockState(blockpos.below()).is(SLTags.Blocks.BASE_STONE_STARLIGHT)) {
                            setBlockIfEmpty(worldgenlevel, blockpos, isRed ? BlockInit.RED_CRYSTAL_MOSS_CARPET.get().defaultBlockState() : BlockInit.BLUE_CRYSTAL_MOSS_CARPET.get().defaultBlockState());
                        }
                    }
                }
            }
        }
        return true;
    }
}


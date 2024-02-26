package cn.leolezury.eternalstarlight.common.world.gen.feature.coral;

import cn.leolezury.eternalstarlight.common.util.ESTags;
import com.mojang.serialization.Codec;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Direction.Plane;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.BaseCoralWallFanBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.SeaPickleBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;

import java.util.Iterator;
import java.util.Optional;

public abstract class ESCoralFeature extends Feature<NoneFeatureConfiguration> {
    public ESCoralFeature(Codec<NoneFeatureConfiguration> codec) {
        super(codec);
    }

    public boolean place(FeaturePlaceContext<NoneFeatureConfiguration> featurePlaceContext) {
        RandomSource randomSource = featurePlaceContext.random();
        WorldGenLevel worldGenLevel = featurePlaceContext.level();
        BlockPos blockPos = featurePlaceContext.origin();
        Optional<Block> optional = BuiltInRegistries.BLOCK.getTag(ESTags.Blocks.CORAL_BLOCKS).flatMap((named) -> named.getRandomElement(randomSource)).map(Holder::value);
        return optional.filter(block -> this.placeFeature(worldGenLevel, randomSource, blockPos, block.defaultBlockState())).isPresent();
    }

    protected abstract boolean placeFeature(LevelAccessor levelAccessor, RandomSource randomSource, BlockPos blockPos, BlockState blockState);

    protected boolean placeCoralBlock(LevelAccessor levelAccessor, RandomSource randomSource, BlockPos blockPos, BlockState state) {
        BlockPos blockPos2 = blockPos.above();
        BlockState blockState2 = levelAccessor.getBlockState(blockPos);
        if ((blockState2.is(Blocks.WATER) || blockState2.is(ESTags.Blocks.CORALS)) && levelAccessor.getBlockState(blockPos2).is(Blocks.WATER)) {
            levelAccessor.setBlock(blockPos, state, 3);
            if (randomSource.nextFloat() < 0.25F) {
                BuiltInRegistries.BLOCK.getTag(ESTags.Blocks.CORALS).flatMap((named) -> named.getRandomElement(randomSource)).map(Holder::value).ifPresent((block) -> {
                    levelAccessor.setBlock(blockPos2, block.defaultBlockState(), 2);
                });
            } else if (randomSource.nextFloat() < 0.05F) {
                levelAccessor.setBlock(blockPos2, Blocks.SEA_PICKLE.defaultBlockState().setValue(SeaPickleBlock.PICKLES, randomSource.nextInt(4) + 1), 2);
            }

            Iterator var7 = Plane.HORIZONTAL.iterator();

            while(var7.hasNext()) {
                Direction direction = (Direction)var7.next();
                if (randomSource.nextFloat() < 0.2F) {
                    BlockPos blockPos3 = blockPos.relative(direction);
                    if (levelAccessor.getBlockState(blockPos3).is(Blocks.WATER)) {
                        BuiltInRegistries.BLOCK.getTag(ESTags.Blocks.WALL_CORALS).flatMap((named) -> {
                            return named.getRandomElement(randomSource);
                        }).map(Holder::value).ifPresent((block) -> {
                            BlockState blockState = block.defaultBlockState();
                            if (blockState.hasProperty(BaseCoralWallFanBlock.FACING)) {
                                blockState = blockState.setValue(BaseCoralWallFanBlock.FACING, direction);
                            }
                            levelAccessor.setBlock(blockPos3, blockState, 2);
                        });
                    }
                }
            }

            return true;
        } else {
            return false;
        }
    }
}

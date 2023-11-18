package cn.leolezury.eternalstarlight.common.block;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.SnowLayerBlock;
import net.minecraft.world.level.block.SnowyDirtBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.lighting.LightEngine;

import java.util.function.Supplier;

public abstract class SpreadingSnowyNightshadeDirtBlock extends SnowyDirtBlock {
    private final Supplier<? extends Block> spreadsOn;

    protected SpreadingSnowyNightshadeDirtBlock(BlockBehaviour.Properties properties, Supplier<? extends Block> spreadsOn) {
        super(properties);
        this.spreadsOn = spreadsOn;
    }

    private static boolean canBeGrass(BlockState state, LevelReader levelReader, BlockPos pos) {
        BlockPos blockpos = pos.above();
        BlockState blockstate = levelReader.getBlockState(blockpos);
        if (blockstate.is(Blocks.SNOW) && blockstate.getValue(SnowLayerBlock.LAYERS) == 1) {
            return true;
        } else if (blockstate.getFluidState().getAmount() == 8) {
            return false;
        } else {
            int i = LightEngine.getLightBlockInto(levelReader, state, pos, blockstate, blockpos, Direction.UP, blockstate.getLightBlock(levelReader, blockpos));
            return i < levelReader.getMaxLightLevel();
        }
    }

    private static boolean canPropagate(BlockState state, LevelReader levelReader, BlockPos pos) {
        BlockPos blockpos = pos.above();
        return canBeGrass(state, levelReader, pos) && !levelReader.getFluidState(blockpos).is(FluidTags.WATER);
    }

    public void randomTick(BlockState state, ServerLevel serverLevel, BlockPos pos, RandomSource randomSource) {
        if (!canBeGrass(state, serverLevel, pos)) {
            serverLevel.setBlockAndUpdate(pos, spreadsOn.get().defaultBlockState());
        } else {
            if (serverLevel.getMaxLocalRawBrightness(pos.above()) >= 9) {
                BlockState blockstate = this.defaultBlockState();

                for(int i = 0; i < 4; ++i) {
                    BlockPos blockpos = pos.offset(randomSource.nextInt(3) - 1, randomSource.nextInt(5) - 3, randomSource.nextInt(3) - 1);
                    if (serverLevel.getBlockState(blockpos).is(spreadsOn.get()) && canPropagate(blockstate, serverLevel, blockpos)) {
                        serverLevel.setBlockAndUpdate(blockpos, blockstate.setValue(SNOWY, Boolean.valueOf(serverLevel.getBlockState(blockpos.above()).is(Blocks.SNOW))));
                    }
                }
            }

        }
    }
}

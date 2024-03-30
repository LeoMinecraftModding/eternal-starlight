package cn.leolezury.eternalstarlight.common.block;

import cn.leolezury.eternalstarlight.common.registry.ESBlocks;
import cn.leolezury.eternalstarlight.common.registry.ESFluids;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.FarmBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.Fluids;
import org.jetbrains.annotations.Nullable;

import java.util.Iterator;

public class NightshadeFarmlandBlock extends FarmBlock {
    public static IntegerProperty ETHER_MOISTURE = IntegerProperty.create("ether_moisture", 0, 7);;

    public NightshadeFarmlandBlock(Properties properties) {
        super(properties);
        this.registerDefaultState(this.stateDefinition.any().setValue(MOISTURE, 0));
    }

//    public static final MapCodec<NightshadeFarmlandBlock> CODEC = simpleCodec(NightshadeFarmlandBlock::new);
//
//    public MapCodec<NightshadeFarmlandBlock> codec() {
//        return CODEC;
//    }

    public static void turnToDirt(@Nullable Entity entity, BlockState blockState, Level level, BlockPos blockPos) {
        BlockState dirt = pushEntitiesUp(blockState, ESBlocks.NIGHTSHADE_DIRT.get().defaultBlockState(), level, blockPos);
        BlockState ether_dirt = pushEntitiesUp(blockState, ESBlocks.ETHER_DIRT.get().defaultBlockState(), level, blockPos);
        if (blockState.getValue(ETHER_MOISTURE) < 3) {
            level.setBlockAndUpdate(blockPos, dirt);
            level.gameEvent(GameEvent.BLOCK_CHANGE, blockPos, GameEvent.Context.of(entity, dirt));
        } else {
            level.setBlockAndUpdate(blockPos, ether_dirt);
            level.gameEvent(GameEvent.BLOCK_CHANGE, blockPos, GameEvent.Context.of(entity, ether_dirt));
        }
    }

    private static boolean isNearFluid(Fluid fluid, LevelReader levelReader, BlockPos blockPos) {
        Iterator<BlockPos> var2 = BlockPos.betweenClosed(blockPos.offset(-4, 0, -4), blockPos.offset(4, 1, 4)).iterator();

        BlockPos blockPos2;
        do {
            if (!var2.hasNext()) {
                return false;
            }

            blockPos2 = var2.next();
        } while(!levelReader.getFluidState(blockPos2).is(fluid));

        return true;
    }

    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(MOISTURE);
        builder.add(ETHER_MOISTURE);
    }

    @Override
    public void tick(BlockState blockState, ServerLevel serverLevel, BlockPos blockPos, RandomSource randomSource) {
        if (!blockState.canSurvive(serverLevel, blockPos)) {
            turnToDirt(null, blockState, serverLevel, blockPos);
        }
    }

    @Override
    public void randomTick(BlockState blockState, ServerLevel serverLevel, BlockPos blockPos, RandomSource randomSource) {
        int moist = blockState.getValue(MOISTURE);
        int ether = blockState.getValue(ETHER_MOISTURE);
        if (ether < 3 ) {
            if (!isNearFluid(Fluids.WATER, serverLevel, blockPos) && !serverLevel.isRainingAt(blockPos.above())) {
                if (moist > 0) {
                    serverLevel.setBlock(blockPos, blockState.setValue(MOISTURE, moist - 1), 2);
                } else if (!serverLevel.getBlockState(blockPos.above()).is(BlockTags.MAINTAINS_FARMLAND)) {
                    turnToDirt(null, blockState, serverLevel, blockPos);
                }
            } else if (moist < 7) {
                serverLevel.setBlock(blockPos, blockState.setValue(MOISTURE, 7), 2);
            }
        } else if (!isNearFluid(ESFluids.ETHER_FLOWING.get(), serverLevel, blockPos) || !isNearFluid(ESFluids.ETHER_STILL.get(), serverLevel, blockPos)) {
            serverLevel.setBlock(blockPos, blockState.setValue(ETHER_MOISTURE, moist - 1), 2);
        } else if (ether < 7) {
            serverLevel.setBlock(blockPos, blockState.setValue(ETHER_MOISTURE, 7), 2);
        }
    }
}

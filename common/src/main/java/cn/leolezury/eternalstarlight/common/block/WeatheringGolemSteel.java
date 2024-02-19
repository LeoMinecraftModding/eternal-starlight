package cn.leolezury.eternalstarlight.common.block;

import cn.leolezury.eternalstarlight.common.init.ESBlocks;
import com.google.common.base.Suppliers;
import com.google.common.collect.ImmutableMap;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;

import java.util.Optional;
import java.util.function.Supplier;

public interface WeatheringGolemSteel {
    Supplier<ImmutableMap<Block, Block>> TO_OXIDIZED = Suppliers.memoize(() -> ImmutableMap.of(
            ESBlocks.GOLEM_STEEL_BLOCK.get(), ESBlocks.OXIDIZED_GOLEM_STEEL_BLOCK.get(),
            ESBlocks.GOLEM_STEEL_SLAB.get(), ESBlocks.OXIDIZED_GOLEM_STEEL_SLAB.get(),
            ESBlocks.GOLEM_STEEL_STAIRS.get(), ESBlocks.OXIDIZED_GOLEM_STEEL_STAIRS.get(),
            ESBlocks.GOLEM_STEEL_TILES.get(), ESBlocks.OXIDIZED_GOLEM_STEEL_TILES.get(),
            ESBlocks.GOLEM_STEEL_TILE_SLAB.get(), ESBlocks.OXIDIZED_GOLEM_STEEL_TILE_SLAB.get(),
            ESBlocks.GOLEM_STEEL_TILE_STAIRS.get(), ESBlocks.OXIDIZED_GOLEM_STEEL_TILE_STAIRS.get(),
            ESBlocks.CHISELED_GOLEM_STEEL_BLOCK.get(), ESBlocks.OXIDIZED_CHISELED_GOLEM_STEEL_BLOCK.get()
    ));

    default boolean isOxidized() {
        if (this instanceof Block block) {
            return TO_OXIDIZED.get().containsValue(block);
        } else {
            return false;
        }
    }

    default Optional<BlockState> getOxidizedState(BlockState blockState) {
        if (TO_OXIDIZED.get().containsKey(blockState.getBlock())) {
            return Optional.ofNullable(TO_OXIDIZED.get().get(blockState.getBlock())).map((block) -> block.withPropertiesOf(blockState));
        }
        return Optional.empty();
    }

    default void changeOverTime(BlockState blockState, ServerLevel serverLevel, BlockPos blockPos, RandomSource randomSource) {
        if (randomSource.nextFloat() < 0.05688889F) {
            this.getNextState(blockState, serverLevel, blockPos, randomSource).ifPresent((state) -> {
                serverLevel.setBlockAndUpdate(blockPos, state);
            });
        }
    }

    default Optional<BlockState> getNextState(BlockState blockState, ServerLevel serverLevel, BlockPos blockPos, RandomSource randomSource) {
        boolean oxidized = this.isOxidized();
        int lessAffected = 0;
        int moreAffected = 0;

        for (BlockPos pos : BlockPos.withinManhattan(blockPos, 4, 4, 4)) {
            int distManhattan = pos.distManhattan(blockPos);
            if (distManhattan > 4) {
                break;
            }

            if (!pos.equals(blockPos)) {
                Block block = serverLevel.getBlockState(pos).getBlock();
                if (block instanceof WeatheringGolemSteel weatheringGolemSteel) {
                    boolean otherBlockOxidized = weatheringGolemSteel.isOxidized();
                    if (!otherBlockOxidized && oxidized) {
                        return Optional.empty();
                    }
                    if (otherBlockOxidized && !oxidized) {
                        ++moreAffected;
                    } else {
                        ++lessAffected;
                    }
                }
            }
        }

        float oxidizeFactor = (float)(moreAffected + 1) / (float)(moreAffected + lessAffected + 1);
        return randomSource.nextFloat() < oxidizeFactor * oxidizeFactor * 0.75f ? this.getOxidizedState(blockState) : Optional.empty();
    }
}

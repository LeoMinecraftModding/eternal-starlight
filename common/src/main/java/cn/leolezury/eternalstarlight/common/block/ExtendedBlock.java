package cn.leolezury.eternalstarlight.common.block;

import net.minecraft.world.level.block.state.BlockState;

public interface ExtendedBlock {
	default boolean isSticky(BlockState blockState) {
		return false;
	}

	default boolean canStickToEachOther(BlockState blockState, BlockState blockState2) {
		return false;
	}
}

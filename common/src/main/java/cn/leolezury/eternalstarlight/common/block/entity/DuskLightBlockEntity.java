package cn.leolezury.eternalstarlight.common.block.entity;

import cn.leolezury.eternalstarlight.common.block.DuskLightBlock;
import cn.leolezury.eternalstarlight.common.registry.ESBlockEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.block.state.BlockState;

public class DuskLightBlockEntity extends AbstractDuskLightBlockEntity {
	public DuskLightBlockEntity(BlockPos blockPos, BlockState blockState) {
		super(ESBlockEntities.DUSK_LIGHT.get(), blockPos, blockState);
	}

	@Override
	protected boolean isFaceActivated(BlockState state, Direction direction) {
		return state.getValue(DuskLightBlock.FACING).equals(direction);
	}
}

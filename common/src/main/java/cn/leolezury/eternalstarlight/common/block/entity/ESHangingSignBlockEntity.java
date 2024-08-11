package cn.leolezury.eternalstarlight.common.block.entity;

import cn.leolezury.eternalstarlight.common.registry.ESBlockEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.entity.HangingSignBlockEntity;
import net.minecraft.world.level.block.state.BlockState;

public class ESHangingSignBlockEntity extends HangingSignBlockEntity {
	public ESHangingSignBlockEntity(BlockPos pos, BlockState state) {
		super(pos, state);
	}

	@Override
	public boolean isValidBlockState(BlockState blockState) {
		return this.getType().isValid(blockState);
	}

	@Override
	public BlockEntityType<?> getType() {
		return ESBlockEntities.HANGING_SIGN.get();
	}
}

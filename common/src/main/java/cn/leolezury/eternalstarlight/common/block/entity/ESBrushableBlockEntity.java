package cn.leolezury.eternalstarlight.common.block.entity;

import cn.leolezury.eternalstarlight.common.registry.ESBlockEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.entity.BrushableBlockEntity;
import net.minecraft.world.level.block.state.BlockState;

public class ESBrushableBlockEntity extends BrushableBlockEntity {
	public ESBrushableBlockEntity(BlockPos blockPos, BlockState blockState) {
		super(blockPos, blockState);
	}

	@Override
	public boolean isValidBlockState(BlockState blockState) {
		return this.getType().isValid(blockState);
	}

	@Override
	public BlockEntityType<?> getType() {
		return ESBlockEntities.BRUSHABLE_BLOCK.get();
	}
}

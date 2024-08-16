package cn.leolezury.eternalstarlight.common.block.entity;

import cn.leolezury.eternalstarlight.common.registry.ESBlockEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

public class HaloBlockEntity extends BlockEntity {
	public HaloBlockEntity(BlockPos blockPos, BlockState blockState) {
		super(ESBlockEntities.HALO_BLOCK.get(), blockPos, blockState);
	}

	@Override
	public boolean isValidBlockState(BlockState blockState) {
		return this.getType().isValid(blockState);
	}

	@Override
	public BlockEntityType<?> getType() {
		return ESBlockEntities.HALO_BLOCK.get();
	}
}

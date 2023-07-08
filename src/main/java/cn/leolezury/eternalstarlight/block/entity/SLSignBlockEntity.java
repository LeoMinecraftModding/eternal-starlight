package cn.leolezury.eternalstarlight.block.entity;

import cn.leolezury.eternalstarlight.init.BlockEntityInit;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.entity.SignBlockEntity;
import net.minecraft.world.level.block.state.BlockState;

public class SLSignBlockEntity extends SignBlockEntity {
    public SLSignBlockEntity(BlockPos blockPos, BlockState blockState) {
        super(blockPos, blockState);
    }

    @Override
    public BlockEntityType<?> getType() {
        return BlockEntityInit.SIGN_BLOCK_ENTITY.get();
    }
}

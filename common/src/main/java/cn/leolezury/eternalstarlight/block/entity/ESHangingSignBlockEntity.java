package cn.leolezury.eternalstarlight.block.entity;

import cn.leolezury.eternalstarlight.init.BlockEntityInit;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.entity.HangingSignBlockEntity;
import net.minecraft.world.level.block.state.BlockState;

public class ESHangingSignBlockEntity extends HangingSignBlockEntity {
    public ESHangingSignBlockEntity(BlockPos p_250603_, BlockState p_251674_) {
        super(p_250603_, p_251674_);
    }

    @Override
    public BlockEntityType<?> getType() {
        return BlockEntityInit.HANGING_SIGN_BLOCK_ENTITY.get();
    }
}

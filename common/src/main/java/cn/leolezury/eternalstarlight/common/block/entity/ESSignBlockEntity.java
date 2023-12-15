package cn.leolezury.eternalstarlight.common.block.entity;

import cn.leolezury.eternalstarlight.common.init.BlockEntityInit;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.entity.SignBlockEntity;
import net.minecraft.world.level.block.state.BlockState;

public class ESSignBlockEntity extends SignBlockEntity {
    public ESSignBlockEntity(BlockPos pos, BlockState state) {
        super(pos, state);
    }

    @Override
    public BlockEntityType<?> getType() {
        return BlockEntityInit.SIGN.get();
    }
}

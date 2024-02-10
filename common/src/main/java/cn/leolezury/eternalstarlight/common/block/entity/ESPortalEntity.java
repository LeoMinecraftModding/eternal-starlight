package cn.leolezury.eternalstarlight.common.block.entity;

import cn.leolezury.eternalstarlight.common.init.BlockEntityInit;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

public class ESPortalEntity extends BlockEntity {
    protected ESPortalEntity(BlockEntityType<?> entityType, BlockPos pos, BlockState state) {
        super(entityType, pos, state);
    }
    public ESPortalEntity(BlockPos blockPos, BlockState blockState) {
        this(BlockEntityInit.PORTAL.get(), blockPos, blockState);
    }
}

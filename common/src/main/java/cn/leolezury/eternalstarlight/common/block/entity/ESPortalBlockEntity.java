package cn.leolezury.eternalstarlight.common.block.entity;

import cn.leolezury.eternalstarlight.common.init.ESBlockEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

public class ESPortalBlockEntity extends BlockEntity {
    protected ESPortalBlockEntity(BlockEntityType<?> entityType, BlockPos pos, BlockState state) {
        super(entityType, pos, state);
    }

    public ESPortalBlockEntity(BlockPos blockPos, BlockState blockState) {
        this(ESBlockEntities.STARLIGHT_PORTAL.get(), blockPos, blockState);
    }

    private int clientSideTickCount = 0;

    public int getClientSideTickCount() {
        return clientSideTickCount;
    }

    public static void tick(Level level, BlockPos pos, BlockState state, ESPortalBlockEntity entity) {
        if (level.isClientSide && entity.getClientSideTickCount() < 60) {
            entity.clientSideTickCount++;
        }
    }
}

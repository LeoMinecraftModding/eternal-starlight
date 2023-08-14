package cn.leolezury.eternalstarlight.block;

import cn.leolezury.eternalstarlight.block.entity.SLHangingSignBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.WallHangingSignBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.WoodType;

public class SLWallHangingSignBlock extends WallHangingSignBlock {
    public SLWallHangingSignBlock(Properties p_251606_, WoodType p_252140_) {
        super(p_251606_, p_252140_);
    }

    @Override
    public BlockEntity newBlockEntity(BlockPos p_154556_, BlockState p_154557_) {
        return new SLHangingSignBlockEntity(p_154556_, p_154557_);
    }
}

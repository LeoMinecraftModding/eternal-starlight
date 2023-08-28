package cn.leolezury.eternalstarlight.common.block;

import cn.leolezury.eternalstarlight.common.block.entity.ESHangingSignBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.WallHangingSignBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.WoodType;

public class ESWallHangingSignBlock extends WallHangingSignBlock {
    public ESWallHangingSignBlock(Properties p_251606_, WoodType p_252140_) {
        super(p_251606_, p_252140_);
    }

    @Override
    public BlockEntity newBlockEntity(BlockPos p_154556_, BlockState p_154557_) {
        return new ESHangingSignBlockEntity(p_154556_, p_154557_);
    }
}

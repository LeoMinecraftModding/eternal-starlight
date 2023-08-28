package cn.leolezury.eternalstarlight.common.block;

import cn.leolezury.eternalstarlight.common.block.entity.ESHangingSignBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.CeilingHangingSignBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.WoodType;

public class ESCeilingHangingSignBlock extends CeilingHangingSignBlock {
    public ESCeilingHangingSignBlock(Properties p_250481_, WoodType p_248716_) {
        super(p_250481_, p_248716_);
    }

    @Override
    public BlockEntity newBlockEntity(BlockPos p_154556_, BlockState p_154557_) {
        return new ESHangingSignBlockEntity(p_154556_, p_154557_);
    }
}

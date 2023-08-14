package cn.leolezury.eternalstarlight.block;

import cn.leolezury.eternalstarlight.block.entity.SLHangingSignBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.CeilingHangingSignBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.WoodType;

public class SLCeilingHangingSignBlock extends CeilingHangingSignBlock {
    public SLCeilingHangingSignBlock(Properties p_250481_, WoodType p_248716_) {
        super(p_250481_, p_248716_);
    }

    @Override
    public BlockEntity newBlockEntity(BlockPos p_154556_, BlockState p_154557_) {
        return new SLHangingSignBlockEntity(p_154556_, p_154557_);
    }
}

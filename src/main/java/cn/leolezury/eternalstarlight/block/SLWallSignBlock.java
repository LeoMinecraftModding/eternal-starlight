package cn.leolezury.eternalstarlight.block;

import cn.leolezury.eternalstarlight.block.entity.SLSignBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.WallSignBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.WoodType;

public class SLWallSignBlock extends WallSignBlock {
    public SLWallSignBlock(Properties p_56990_, WoodType p_56991_) {
        super(p_56990_, p_56991_);
    }

    @Override
    public BlockEntity newBlockEntity(BlockPos p_154556_, BlockState p_154557_) {
        return new SLSignBlockEntity(p_154556_, p_154557_);
    }
}

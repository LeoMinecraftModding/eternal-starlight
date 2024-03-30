package cn.leolezury.eternalstarlight.common.block;

import cn.leolezury.eternalstarlight.common.registry.ESBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.CropBlock;
import net.minecraft.world.level.block.state.BlockState;

public class ESCropBlock extends CropBlock {
    int maxAge;
    public ESCropBlock(Properties properties, int maxAge, boolean surviveOnEther) {
        super(properties);
        this.maxAge = maxAge;
    }

    @Override
    public int getMaxAge() {
        return maxAge;
    }

    @Override
    public boolean canSurvive(BlockState blockState, LevelReader levelReader, BlockPos blockPos) {
        if (levelReader.getBlockState(blockPos.below()).getBlock() instanceof NightshadeFarmlandBlock) {

        }
        return false;
    }
}

package cn.leolezury.eternalstarlight.common.block;

import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.FarmBlock;
import net.minecraft.world.level.block.state.BlockState;

public class NightshadeFarmBlock extends FarmBlock {
    public NightshadeFarmBlock(Properties properties) {
        super(properties);
    }

    public BlockState getStateForPlacement(BlockPlaceContext blockPlaceContext) {
        return !this.defaultBlockState().canSurvive(blockPlaceContext.getLevel(), blockPlaceContext.getClickedPos()) ? Blocks.DIRT.defaultBlockState() : super.getStateForPlacement(blockPlaceContext);
    }
}

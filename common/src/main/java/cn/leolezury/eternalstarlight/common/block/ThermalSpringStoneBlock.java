package cn.leolezury.eternalstarlight.common.block;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.BubbleColumnBlock;
import net.minecraft.world.level.block.state.BlockState;

public class ThermalSpringStoneBlock extends Block {
    public ThermalSpringStoneBlock(Properties properties) {
        super(properties);
    }

    public void tick(BlockState state, ServerLevel level, BlockPos pos, RandomSource p_221418_) {
        BubbleColumnBlock.updateColumn(level, pos.above(), state);
    }

    public BlockState updateShape(BlockState state, Direction direction, BlockState newState, LevelAccessor level, BlockPos pos, BlockPos pos1) {
        if (direction == Direction.UP && newState.is(Blocks.WATER)) {
            level.scheduleTick(pos, this, 20);
        }

        return super.updateShape(state, direction, newState, level, pos, pos1);
    }

    public void onPlace(BlockState state, Level level, BlockPos pos, BlockState state1, boolean b) {
        level.scheduleTick(pos, this, 20);
    }
}

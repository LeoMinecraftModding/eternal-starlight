package cn.leolezury.eternalstarlight.common.block;

import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import org.jetbrains.annotations.Nullable;

import java.util.Arrays;
import java.util.List;

public class ShadegrieveBlock extends Block {
    public static final MapCodec<ShadegrieveBlock> CODEC = simpleCodec(ShadegrieveBlock::new);
    public static final BooleanProperty TOP = BooleanProperty.create("top");

    public ShadegrieveBlock(Properties properties) {
        super(properties);
        this.registerDefaultState(this.stateDefinition.any().setValue(TOP, false));
    }

    @Override
    protected MapCodec<ShadegrieveBlock> codec() {
        return CODEC;
    }

    @Nullable
    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        BlockState state = super.getStateForPlacement(context);
        if (state == null) return null;
        boolean sturdy = true;
        Level level = context.getLevel();
        BlockPos blockPos = context.getClickedPos();
        List<Direction> xzDirections = Arrays.stream(Direction.values()).filter(dir -> dir.getAxis() != Direction.Axis.Y).toList();
        for (Direction dir : xzDirections) {
            sturdy &= level.getBlockState(blockPos.above()).isFaceSturdy(level, blockPos.above(), dir);
        }
        return state.setValue(TOP, !(level.getBlockState(blockPos.above()).getBlock() instanceof ShadegrieveBlock) && sturdy);
    }

    @Override
    public BlockState updateShape(BlockState state, Direction direction, BlockState blockState2, LevelAccessor level, BlockPos blockPos, BlockPos blockPos2) {
        boolean sturdy = true;
        List<Direction> xzDirections = Arrays.stream(Direction.values()).filter(dir -> dir.getAxis() != Direction.Axis.Y).toList();
        for (Direction dir : xzDirections) {
            sturdy &= level.getBlockState(blockPos.above()).isFaceSturdy(level, blockPos.above(), dir);
        }
        return state.setValue(TOP, !(level.getBlockState(blockPos.above()).getBlock() instanceof ShadegrieveBlock) && sturdy);
    }

    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(TOP);
    }
}

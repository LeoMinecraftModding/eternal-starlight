package cn.leolezury.eternalstarlight.common.block;

import cn.leolezury.eternalstarlight.common.util.ESTags;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.phys.BlockHitResult;
import org.jetbrains.annotations.Nullable;

public class DoomedenKeyholeBlock extends HorizontalDirectionalBlock {
    public static final BooleanProperty LIT = BlockStateProperties.LIT;

    public DoomedenKeyholeBlock(Properties properties) {
        super(properties);
        this.registerDefaultState(this.getStateDefinition().any().setValue(LIT, false).setValue(FACING, Direction.NORTH));
    }

    @Nullable
    public BlockState getStateForPlacement(BlockPlaceContext blockPlaceContext) {
        if (blockPlaceContext.getPlayer() != null) {
            Direction direction = Direction.NORTH;
            Direction[] directions = Direction.orderedByNearest(blockPlaceContext.getPlayer());
            for (int i = 0; i < 3; i++) {
                Direction dir = directions[i];
                if (dir != Direction.UP && dir != Direction.DOWN) {
                    direction = dir;
                    break;
                }
            }
            return defaultBlockState().setValue(FACING, direction);
        }

        return defaultBlockState();
    }

    @Override
    public InteractionResult use(BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult result) {
        if (player.getItemInHand(hand).is(ESTags.Items.DOOMEDEN_KEY_ITEM) && !state.getValue(LIT)) {
            level.setBlock(pos, state.setValue(LIT, true), 1);
            return InteractionResult.sidedSuccess(level.isClientSide);
        } else {
            return InteractionResult.PASS;
        }
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(LIT);
        builder.add(FACING);
        super.createBlockStateDefinition(builder);
    }
}

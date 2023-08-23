package cn.leolezury.eternalstarlight.block;

import cn.leolezury.eternalstarlight.init.BlockInit;
import cn.leolezury.eternalstarlight.init.ItemInit;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.BonemealableBlock;
import net.minecraft.world.level.block.GrowingPlantHeadBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.phys.BlockHitResult;

public class BerriesVineBlock extends GrowingPlantHeadBlock implements BonemealableBlock, BerriesVines {
    public BerriesVineBlock(BlockBehaviour.Properties properties) {
        super(properties, Direction.DOWN, SHAPE, false, 0.1D);
        this.registerDefaultState(this.stateDefinition.any().setValue(AGE, Integer.valueOf(0)).setValue(BERRIES, Boolean.valueOf(false)));
    }

    protected int getBlocksToGrowWhenBonemealed(RandomSource randomSource) {
        return 1;
    }

    protected boolean canGrowInto(BlockState state) {
        return state.isAir();
    }

    protected Block getBodyBlock() {
        return BlockInit.BERRIES_VINES_PLANT.get();
    }

    protected BlockState updateBodyAfterConvertedFromHead(BlockState state, BlockState blockState) {
        return blockState.setValue(BERRIES, state.getValue(BERRIES));
    }

    protected BlockState getGrowIntoState(BlockState state, RandomSource randomSource) {
        return super.getGrowIntoState(state, randomSource).setValue(BERRIES, Boolean.valueOf(randomSource.nextFloat() < 0.11F));
    }

    public ItemStack getCloneItemStack(BlockGetter blockGetter, BlockPos pos, BlockState state) {
        return new ItemStack(ItemInit.LUNAR_BERRIES.get());
    }

    public InteractionResult use(BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult result) {
        return BerriesVines.use(state, level, pos);
    }

    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        super.createBlockStateDefinition(builder);
        builder.add(BERRIES);
    }

    @Override
    public boolean isValidBonemealTarget(LevelReader levelReader, BlockPos pos, BlockState state, boolean b) {
        return !state.getValue(BERRIES);
    }

    public boolean isBonemealSuccess(Level level, RandomSource randomSource, BlockPos pos, BlockState state) {
        return true;
    }

    public void performBonemeal(ServerLevel serverLevel, RandomSource randomSource, BlockPos pos, BlockState state) {
        serverLevel.setBlock(pos, state.setValue(BERRIES, Boolean.valueOf(true)), 2);
    }
}

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
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.BonemealableBlock;
import net.minecraft.world.level.block.GrowingPlantBodyBlock;
import net.minecraft.world.level.block.GrowingPlantHeadBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.phys.BlockHitResult;

public class BerriesVinePlantBlock extends GrowingPlantBodyBlock implements BonemealableBlock, BerriesVines {
    public BerriesVinePlantBlock(BlockBehaviour.Properties p_153000_) {
        super(p_153000_, Direction.DOWN, SHAPE, false);
        this.registerDefaultState(this.stateDefinition.any().setValue(BERRIES, Boolean.valueOf(false)));
    }

    protected GrowingPlantHeadBlock getHeadBlock() {
        return (GrowingPlantHeadBlock) BlockInit.BERRIES_VINES.get();
    }

    protected BlockState updateHeadAfterConvertedFromBody(BlockState p_153028_, BlockState p_153029_) {
        return p_153029_.setValue(BERRIES, p_153028_.getValue(BERRIES));
    }

    public ItemStack getCloneItemStack(BlockGetter p_153007_, BlockPos p_153008_, BlockState p_153009_) {
        return new ItemStack(ItemInit.LUNAR_BERRIES.get());
    }

    public InteractionResult use(BlockState p_153021_, Level p_153022_, BlockPos p_153023_, Player p_153024_, InteractionHand p_153025_, BlockHitResult p_153026_) {
        return BerriesVines.use(p_153021_, p_153022_, p_153023_);
    }

    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> p_153031_) {
        p_153031_.add(BERRIES);
    }

    public boolean isValidBonemealTarget(BlockGetter p_153011_, BlockPos p_153012_, BlockState p_153013_, boolean p_153014_) {
        return !p_153013_.getValue(BERRIES);
    }

    public boolean isBonemealSuccess(Level p_220943_, RandomSource p_220944_, BlockPos p_220945_, BlockState p_220946_) {
        return true;
    }

    public void performBonemeal(ServerLevel p_220938_, RandomSource p_220939_, BlockPos p_220940_, BlockState p_220941_) {
        p_220938_.setBlock(p_220940_, p_220941_.setValue(BERRIES, Boolean.valueOf(true)), 2);
    }
}

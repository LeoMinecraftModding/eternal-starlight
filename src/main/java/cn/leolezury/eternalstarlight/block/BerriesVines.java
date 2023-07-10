package cn.leolezury.eternalstarlight.block;

import cn.leolezury.eternalstarlight.init.ItemInit;
import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.phys.shapes.VoxelShape;

import java.util.function.ToIntFunction;

public interface BerriesVines {
    VoxelShape SHAPE = Block.box(1.0D, 0.0D, 1.0D, 15.0D, 16.0D, 15.0D);
    BooleanProperty BERRIES = BlockStateProperties.BERRIES;

    static InteractionResult use(BlockState p_152954_, Level p_152955_, BlockPos p_152956_) {
        if (p_152954_.getValue(BERRIES)) {
            Block.popResource(p_152955_, p_152956_, new ItemStack(ItemInit.LUNAR_BERRIES.get(), 1));
            float f = Mth.randomBetween(p_152955_.random, 0.8F, 1.2F);
            p_152955_.playSound(null, p_152956_, SoundEvents.CAVE_VINES_PICK_BERRIES, SoundSource.BLOCKS, 1.0F, f);
            p_152955_.setBlock(p_152956_, p_152954_.setValue(BERRIES, Boolean.valueOf(false)), 2);
            return InteractionResult.sidedSuccess(p_152955_.isClientSide);
        } else {
            return InteractionResult.PASS;
        }
    }
}

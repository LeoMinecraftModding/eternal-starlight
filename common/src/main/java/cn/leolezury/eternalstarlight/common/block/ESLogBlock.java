package cn.leolezury.eternalstarlight.common.block;

import cn.leolezury.eternalstarlight.common.block.modifier.ModifiedBlock;
import cn.leolezury.eternalstarlight.common.block.modifier.ModifierContainer;
import cn.leolezury.eternalstarlight.common.init.BlockInit;
import cn.leolezury.eternalstarlight.common.platform.ESPlatform;
import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.RotatedPillarBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;

import java.util.function.Consumer;

public class ESLogBlock extends RotatedPillarBlock implements ModifiedBlock<RotatedPillarBlock> {
    private ModifierContainer<Block> modifierContainer;
    public ESLogBlock(Properties properties) {
        super(properties);
    }

    @Override
    public ESLogBlock modifiers(Consumer... modifiers) {
        modifierContainer = new ModifierContainer<Block>(this, modifiers);
        return this;
    }

    @Override
    public ModifierContainer<Block> getModifierContainer() {
        return modifierContainer;
    }

    @Override
    public InteractionResult use(BlockState blockState, Level level, BlockPos blockPos, Player player, InteractionHand interactionHand, BlockHitResult blockHitResult) {
        ItemStack stack = player.getItemInHand(interactionHand);
        if (ESPlatform.INSTANCE.canStrip(stack)) {
            // strip
            Block stripped;
            if (blockState.is(BlockInit.LUNAR_LOG.get())) {
                stripped = BlockInit.STRIPPED_LUNAR_LOG.get();
            } else if (blockState.is(BlockInit.NORTHLAND_LOG.get())) {
                stripped = BlockInit.STRIPPED_NORTHLAND_LOG.get();
            } else if (blockState.is(BlockInit.STARLIGHT_MANGROVE_LOG.get())) {
                stripped = BlockInit.STRIPPED_STARLIGHT_MANGROVE_LOG.get();
            } else {
                return super.use(blockState, level, blockPos, player, interactionHand, blockHitResult);
            }
            level.setBlockAndUpdate(blockPos, stripped.defaultBlockState().setValue(RotatedPillarBlock.AXIS, blockState.getValue(RotatedPillarBlock.AXIS)));
            return InteractionResult.sidedSuccess(level.isClientSide);
        }
        return super.use(blockState, level, blockPos, player, interactionHand, blockHitResult);
    }
}

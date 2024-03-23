package cn.leolezury.eternalstarlight.common.block;

import cn.leolezury.eternalstarlight.common.platform.ESPlatform;
import cn.leolezury.eternalstarlight.common.registry.ESBlocks;
import cn.leolezury.eternalstarlight.common.registry.ESItems;
import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.RotatedPillarBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;

public class ESLogBlock extends RotatedPillarBlock {
    public static final MapCodec<ESLogBlock> CODEC = simpleCodec(ESLogBlock::new);

    public ESLogBlock(Properties properties) {
        super(properties);
    }

    @Override
    public MapCodec<? extends RotatedPillarBlock> codec() {
        return CODEC;
    }

    @Override
    public InteractionResult use(BlockState blockState, Level level, BlockPos blockPos, Player player, InteractionHand interactionHand, BlockHitResult blockHitResult) {
        ItemStack stack = player.getItemInHand(interactionHand);
        if (ESPlatform.INSTANCE.canStrip(stack)) {
            // strip
            Block stripped;
            if (blockState.is(ESBlocks.LUNAR_LOG.get())) {
                stripped = ESBlocks.STRIPPED_LUNAR_LOG.get();
                player.playSound(SoundEvents.AXE_STRIP);
            } else if (blockState.is(ESBlocks.NORTHLAND_LOG.get())) {
                stripped = ESBlocks.STRIPPED_NORTHLAND_LOG.get();
                player.playSound(SoundEvents.AXE_STRIP);
            } else if (blockState.is(ESBlocks.STARLIGHT_MANGROVE_LOG.get())) {
                stripped = ESBlocks.STRIPPED_STARLIGHT_MANGROVE_LOG.get();
                player.playSound(SoundEvents.AXE_STRIP);
            } else if (blockState.is(ESBlocks.SCARLET_LOG.get())) {
                stripped = ESBlocks.STRIPPED_SCARLET_LOG.get();
                player.playSound(SoundEvents.AXE_STRIP);
            } else if (blockState.is(ESBlocks.TORREYA_LOG.get())) {
                stripped = ESBlocks.STRIPPED_TORREYA_LOG.get();
                player.playSound(SoundEvents.AXE_STRIP);
                if (player.getRandom().nextInt(20) == 0) {
                    player.spawnAtLocation(ESItems.RAW_AMARAMBER.get());
                }
            } else {
                return super.use(blockState, level, blockPos, player, interactionHand, blockHitResult);
            }
            level.setBlockAndUpdate(blockPos, stripped.defaultBlockState().setValue(RotatedPillarBlock.AXIS, blockState.getValue(RotatedPillarBlock.AXIS)));
            return InteractionResult.sidedSuccess(level.isClientSide);
        }
        return super.use(blockState, level, blockPos, player, interactionHand, blockHitResult);
    }
}

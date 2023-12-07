package cn.leolezury.eternalstarlight.common.mixins;

import cn.leolezury.eternalstarlight.common.init.BlockInit;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.BubbleColumnBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(BubbleColumnBlock.class)
public abstract class BubbleColumnBlockMixin {
    @Inject(method = "getColumnState", at = @At(value = "RETURN"), cancellable = true)
    private static void es_getColumnState(BlockState state, CallbackInfoReturnable<BlockState> cir) {
        if (state.is(BlockInit.THERMAL_SPRINGSTONE.get())) {
            cir.setReturnValue(Blocks.BUBBLE_COLUMN.defaultBlockState().setValue(BlockStateProperties.DRAG, Boolean.valueOf(false)));
        }
    }

    @Inject(method = "canSurvive", at = @At(value = "RETURN"), cancellable = true)
    private void es_canSurvive(BlockState state, LevelReader levelReader, BlockPos pos, CallbackInfoReturnable<Boolean> cir) {
        if (levelReader.getBlockState(pos.below()).is(BlockInit.THERMAL_SPRINGSTONE.get())) {
            cir.setReturnValue(true);
        }
    }
}

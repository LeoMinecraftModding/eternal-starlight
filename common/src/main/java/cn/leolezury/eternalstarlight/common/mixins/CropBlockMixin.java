package cn.leolezury.eternalstarlight.common.mixins;

import cn.leolezury.eternalstarlight.common.util.ESTags;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.CropBlock;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(CropBlock.class)
public abstract class CropBlockMixin {
    @Inject(method = "canSurvive", at = @At(value = "HEAD"), cancellable = true)
    private void es_canSurvive(BlockState blockState, LevelReader levelReader, BlockPos blockPos, CallbackInfoReturnable<Boolean> cir) {
        if (blockState.is(ESTags.Blocks.NIGHTSHADE_TILLABLE_BLOCK)) {
            cir.setReturnValue(true);
        }
    }
}

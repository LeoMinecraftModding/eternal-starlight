package cn.leolezury.eternalstarlight.common.mixins;

import cn.leolezury.eternalstarlight.common.block.AbyssFireBlock;
import cn.leolezury.eternalstarlight.common.registry.ESBlocks;
import cn.leolezury.eternalstarlight.common.util.ESTags;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseFireBlock;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(BaseFireBlock.class)
public abstract class BaseFireBlockMixin {
    @Inject(method = "getState", at = @At(value = "HEAD"), cancellable = true)
    private static void injectGetState(BlockGetter blockGetter, BlockPos blockPos, CallbackInfoReturnable<BlockState> cir) {
        if (AbyssFireBlock.canSurviveOnBlock(blockGetter.getBlockState(blockPos.below()))) {
            cir.setReturnValue(ESBlocks.ABYSS_FIRE.get().defaultBlockState());
        }
    }

    @Inject(method = "canBePlacedAt", at = @At(value = "HEAD"), cancellable = true)
    private static void injectCanBePlaceAt(Level level, BlockPos blockPos, Direction direction, CallbackInfoReturnable<Boolean> cir) {
        if (level.getBlockState(blockPos.below()).is(ESTags.Blocks.ABYSS_BURNER)) {
            cir.setReturnValue(true);
        }
    }
}

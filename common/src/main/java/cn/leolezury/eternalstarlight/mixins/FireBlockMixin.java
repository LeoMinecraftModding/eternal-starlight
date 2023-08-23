package cn.leolezury.eternalstarlight.mixins;

import cn.leolezury.eternalstarlight.block.modifier.ESFlammabilityModifier;
import net.minecraft.world.level.block.FireBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(FireBlock.class)
public class FireBlockMixin {
    @Inject(at = @At("HEAD"), method = "getBurnOdds", cancellable = true)
    private void getBurnOdds(BlockState blockState, CallbackInfoReturnable<Integer> callbackInfoReturnable) {
        ESFlammabilityModifier.Entry entry;
        if ((entry = ESFlammabilityModifier.getEntry(blockState.getBlock())) != null) {
            callbackInfoReturnable.setReturnValue(blockState.hasProperty(BlockStateProperties.WATERLOGGED) && blockState.getValue(BlockStateProperties.WATERLOGGED) ? 0 : entry.burnOdds());
        }
    }

    @Inject(at = @At("HEAD"), method = "getIgniteOdds*", cancellable = true)
    private void getIgniteOdds(BlockState blockState, CallbackInfoReturnable<Integer> callbackInfoReturnable) {
        ESFlammabilityModifier.Entry entry;
        if ((entry = ESFlammabilityModifier.getEntry(blockState.getBlock())) != null) {
            callbackInfoReturnable.setReturnValue(blockState.hasProperty(BlockStateProperties.WATERLOGGED) && blockState.getValue(BlockStateProperties.WATERLOGGED) ? 0 : entry.catchOdds());
        }
    }
}
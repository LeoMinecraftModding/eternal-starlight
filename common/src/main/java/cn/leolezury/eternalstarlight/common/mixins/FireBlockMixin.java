package cn.leolezury.eternalstarlight.common.mixins;

import cn.leolezury.eternalstarlight.common.block.flammable.ESFlammabilityRegistry;
import net.minecraft.world.level.block.FireBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Optional;

@Mixin(FireBlock.class)
public abstract class FireBlockMixin {
    @Inject(at = @At("HEAD"), method = "getBurnOdds", cancellable = true)
    private void es_getBurnOdds(BlockState blockState, CallbackInfoReturnable<Integer> callbackInfoReturnable) {
        Optional<ESFlammabilityRegistry.Flammability> flammability = ESFlammabilityRegistry.getBlockFlammability(blockState.getBlock());
        flammability.ifPresent(value -> callbackInfoReturnable.setReturnValue(blockState.hasProperty(BlockStateProperties.WATERLOGGED) && blockState.getValue(BlockStateProperties.WATERLOGGED) ? 0 : value.burnOdds()));
    }

    @Inject(at = @At("HEAD"), method = "getIgniteOdds*", cancellable = true)
    private void es_getIgniteOdds(BlockState blockState, CallbackInfoReturnable<Integer> callbackInfoReturnable) {
        Optional<ESFlammabilityRegistry.Flammability> flammability = ESFlammabilityRegistry.getBlockFlammability(blockState.getBlock());
        flammability.ifPresent(value -> callbackInfoReturnable.setReturnValue(blockState.hasProperty(BlockStateProperties.WATERLOGGED) && blockState.getValue(BlockStateProperties.WATERLOGGED) ? 0 : value.catchOdds()));
    }
}
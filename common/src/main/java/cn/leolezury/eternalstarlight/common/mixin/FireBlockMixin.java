package cn.leolezury.eternalstarlight.common.mixin;

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
	@Inject(method = "getBurnOdds", at = @At("HEAD"), cancellable = true)
	private void getBurnOdds(BlockState blockState, CallbackInfoReturnable<Integer> callbackInfoReturnable) {
		Optional<ESFlammabilityRegistry.Flammability> flammability = ESFlammabilityRegistry.getBlockFlammability(blockState.getBlock());
		flammability.ifPresent(value -> callbackInfoReturnable.setReturnValue(blockState.hasProperty(BlockStateProperties.WATERLOGGED) && blockState.getValue(BlockStateProperties.WATERLOGGED) ? 0 : value.burnOdds()));
	}

	@Inject(method = "getIgniteOdds*", at = @At("HEAD"), cancellable = true)
	private void getIgniteOdds(BlockState blockState, CallbackInfoReturnable<Integer> callbackInfoReturnable) {
		Optional<ESFlammabilityRegistry.Flammability> flammability = ESFlammabilityRegistry.getBlockFlammability(blockState.getBlock());
		flammability.ifPresent(value -> callbackInfoReturnable.setReturnValue(blockState.hasProperty(BlockStateProperties.WATERLOGGED) && blockState.getValue(BlockStateProperties.WATERLOGGED) ? 0 : value.catchOdds()));
	}
}
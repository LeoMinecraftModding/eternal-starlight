package cn.leolezury.eternalstarlight.common.mixin;

import cn.leolezury.eternalstarlight.common.registry.ESBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.CoralBlock;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(CoralBlock.class)
public abstract class CoralBlockMixin {
	@Inject(method = "scanForWater", at = @At("HEAD"), cancellable = true)
	private void scanForWater(BlockGetter blockGetter, BlockPos blockPos, CallbackInfoReturnable<Boolean> cir) {
		for (Direction direction : Direction.values()) {
			BlockState state = blockGetter.getBlockState(blockPos.relative(direction));
			if (state.is(ESBlocks.VELVETUMOSS.get()) || state.is(ESBlocks.RED_VELVETUMOSS.get())) {
				cir.setReturnValue(true);
			}
		}
	}
}

package cn.leolezury.eternalstarlight.fabric.mixin;

import cn.leolezury.eternalstarlight.common.block.ExtendedBlock;
import net.minecraft.world.level.block.piston.PistonStructureResolver;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(PistonStructureResolver.class)
public abstract class PistonStructureResolverMixin {
	@Inject(method = "isSticky", at = @At(value = "RETURN"), cancellable = true)
	private static void isSticky(BlockState blockState, CallbackInfoReturnable<Boolean> cir) {
		if (blockState.getBlock() instanceof ExtendedBlock extendedBlock) {
			cir.setReturnValue(extendedBlock.isSticky(blockState));
		}
	}

	@Inject(method = "canStickToEachOther", at = @At(value = "RETURN"), cancellable = true)
	private static void canStickToEachOther(BlockState blockState, BlockState blockState2, CallbackInfoReturnable<Boolean> cir) {
		if (blockState.getBlock() instanceof ExtendedBlock extendedBlock) {
			cir.setReturnValue(extendedBlock.canStickToEachOther(blockState, blockState2));
		}
	}
}

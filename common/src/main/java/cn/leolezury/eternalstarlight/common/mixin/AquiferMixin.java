package cn.leolezury.eternalstarlight.common.mixin;

import cn.leolezury.eternalstarlight.common.world.gen.chunkgenerator.WaterTableFluidPicker;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.Aquifer;
import net.minecraft.world.level.levelgen.DensityFunction;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Aquifer.NoiseBasedAquifer.class)
public abstract class AquiferMixin {
	@Shadow
	@Final
	private Aquifer.FluidPicker globalFluidPicker;

	@Inject(
		method = "computeSubstance(Lnet/minecraft/world/level/levelgen/DensityFunction$FunctionContext;D)Lnet/minecraft/world/level/block/state/BlockState;",
		at = @At("RETURN"),
		cancellable = true
	)
	private void keepAboveGroundAir(DensityFunction.FunctionContext context, double density, CallbackInfoReturnable<BlockState> cir) {
		if (density > 0.0 || !(this.globalFluidPicker instanceof WaterTableFluidPicker picker)) {
			return;
		}
		if (context.blockY() >= picker.ground(context.blockX(), context.blockZ())) {
			cir.setReturnValue(Blocks.AIR.defaultBlockState());
		}
	}
}

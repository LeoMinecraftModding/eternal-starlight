package cn.leolezury.eternalstarlight.fabric.mixin;

import cn.leolezury.eternalstarlight.common.registry.ESBlocks;
import cn.leolezury.eternalstarlight.common.util.ESTags;
import com.google.common.collect.ImmutableList;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.LiquidBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.FlowingFluid;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(LiquidBlock.class)
public abstract class LiquidBlockMixin {
	@Shadow
	@Final
	protected FlowingFluid fluid;

	@Shadow
	@Final
	public static ImmutableList<Direction> POSSIBLE_FLOW_DIRECTIONS;

	@Shadow
	protected abstract void fizz(LevelAccessor levelAccessor, BlockPos blockPos);

	@Inject(method = "shouldSpreadLiquid", at = @At(value = "HEAD"), cancellable = true)
	public void shouldSpreadLiquid(Level level, BlockPos blockPos, BlockState blockState, CallbackInfoReturnable<Boolean> cir) {
		if (!this.fluid.is(ESTags.Fluids.ETHER)) {
			for (Direction direction : POSSIBLE_FLOW_DIRECTIONS) {
				BlockPos relativePos = blockPos.relative(direction.getOpposite());
				if (level.getFluidState(relativePos).is(ESTags.Fluids.ETHER)) {
					level.setBlockAndUpdate(blockPos, ESBlocks.THIOQUARTZ_BLOCK.get().defaultBlockState());
					this.fizz(level, blockPos);
					cir.setReturnValue(false);
				}
			}
		}
	}
}

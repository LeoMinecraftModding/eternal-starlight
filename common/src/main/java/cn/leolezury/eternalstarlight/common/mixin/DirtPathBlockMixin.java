package cn.leolezury.eternalstarlight.common.mixin;

import cn.leolezury.eternalstarlight.common.registry.ESBlocks;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.DirtPathBlock;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(DirtPathBlock.class)
public abstract class DirtPathBlockMixin {
	@Inject(method = "getStateForPlacement", at = @At(value = "RETURN"), cancellable = true)
	private void getStateForPlacement(BlockPlaceContext blockPlaceContext, CallbackInfoReturnable<BlockState> cir) {
		if (((DirtPathBlock) (Object) this).defaultBlockState().is(ESBlocks.NIGHTFALL_DIRT_PATH.get()) && !((DirtPathBlock) (Object) this).defaultBlockState().canSurvive(blockPlaceContext.getLevel(), blockPlaceContext.getClickedPos())) {
			cir.setReturnValue(Block.pushEntitiesUp(((DirtPathBlock) (Object) this).defaultBlockState(), ESBlocks.NIGHTFALL_DIRT.get().defaultBlockState(), blockPlaceContext.getLevel(), blockPlaceContext.getClickedPos()));
		}
	}
}

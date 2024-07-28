package cn.leolezury.eternalstarlight.common.mixin;

import cn.leolezury.eternalstarlight.common.registry.ESBlocks;
import cn.leolezury.eternalstarlight.common.registry.ESItems;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.item.AxeItem;
import net.minecraft.world.item.context.UseOnContext;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(AxeItem.class)
public abstract class AxeItemMixin {
	@Inject(method = "useOn", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/Level;setBlock(Lnet/minecraft/core/BlockPos;Lnet/minecraft/world/level/block/state/BlockState;I)Z"))
	private void useOn(UseOnContext useOnContext, CallbackInfoReturnable<InteractionResult> cir) {
		if (useOnContext.getLevel().getBlockState(useOnContext.getClickedPos()).is(ESBlocks.TORREYA_LOG.get()) && useOnContext.getPlayer() != null) {
			if (useOnContext.getPlayer().getRandom().nextInt(20) == 0) {
				useOnContext.getPlayer().spawnAtLocation(ESItems.RAW_AMARAMBER.get());
			}
		}
	}
}

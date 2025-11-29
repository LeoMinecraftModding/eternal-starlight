package cn.leolezury.eternalstarlight.common.mixin;

import cn.leolezury.eternalstarlight.common.registry.ESBlocks;
import cn.leolezury.eternalstarlight.common.registry.ESItems;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.AxeItem;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.phys.Vec3;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(AxeItem.class)
public abstract class AxeItemMixin {
	@Inject(method = "useOn", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/Level;setBlock(Lnet/minecraft/core/BlockPos;Lnet/minecraft/world/level/block/state/BlockState;I)Z"))
	private void useOn(UseOnContext useOnContext, CallbackInfoReturnable<InteractionResult> cir) {
		if (!useOnContext.getLevel().isClientSide && useOnContext.getLevel().getBlockState(useOnContext.getClickedPos()).is(ESBlocks.TORREYA_LOG.get()) && useOnContext.getPlayer() != null) {
			if (useOnContext.getPlayer().getRandom().nextInt(5) == 0) {
				Vec3 itemPos = useOnContext.getClickedPos().getCenter().add(new Vec3(useOnContext.getClickedFace().step()).scale(0.75));
				ItemEntity itemEntity = new ItemEntity(useOnContext.getLevel(), itemPos.x, itemPos.y, itemPos.z, ESItems.RAW_AMARAMBER.get().getDefaultInstance());
				itemEntity.setDefaultPickUpDelay();
				useOnContext.getLevel().addFreshEntity(itemEntity);
			}
		}
	}
}

package cn.leolezury.eternalstarlight.common.mixin;

import cn.leolezury.eternalstarlight.common.registry.ESItems;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(AbstractArrow.class)
public abstract class AbstractArrowMixin {
	@Shadow
	@Nullable
	private ItemStack firedFromWeapon;

	@Inject(method = "shotFromCrossbow", at = @At(value = "RETURN"), cancellable = true)
	public void shotFromCrossbow(CallbackInfoReturnable<Boolean> cir) {
		if (this.firedFromWeapon != null && (this.firedFromWeapon.is(ESItems.CRYSTAL_CROSSBOW.get()) || this.firedFromWeapon.is(ESItems.MECHANICAL_CROSSBOW.get()))) {
			cir.setReturnValue(true);
		}
	}
}

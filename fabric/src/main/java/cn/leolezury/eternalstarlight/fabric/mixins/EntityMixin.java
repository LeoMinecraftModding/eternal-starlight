package cn.leolezury.eternalstarlight.fabric.mixins;

import cn.leolezury.eternalstarlight.common.util.ESTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.material.Fluid;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Entity.class)
public abstract class EntityMixin {
	@Shadow
	public abstract void resetFallDistance();

	@Shadow
	public abstract void clearFire();

	@Shadow
	public abstract boolean updateFluidHeightAndDoFluidPushing(TagKey<Fluid> tagKey, double d);

	@Shadow
	protected boolean wasTouchingWater;

	@Inject(method = "updateInWaterStateAndDoFluidPushing", at = @At("RETURN"), cancellable = true)
	private void updateInWaterStateAndDoFluidPushing(CallbackInfoReturnable<Boolean> cir) {
		if (this.updateFluidHeightAndDoFluidPushing(ESTags.Fluids.ETHER, 0.014)) {
			this.resetFallDistance();
			this.clearFire();
			this.wasTouchingWater = true;
			cir.setReturnValue(true);
		}
	}
}

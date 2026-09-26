package cn.leolezury.eternalstarlight.common.mixin;

import cn.leolezury.eternalstarlight.common.item.tab.ESCreativeModeTab;
import net.minecraft.core.HolderLookup;
import net.minecraft.world.flag.FeatureFlagSet;
import net.minecraft.world.item.CreativeModeTab;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

// ModernFix "compat"
@Mixin(CreativeModeTab.ItemDisplayParameters.class)
public class ItemDisplayParametersMixin {
	@Inject(method = "needsUpdate", at = @At("HEAD"), cancellable = true)
	private void forceUpdate(FeatureFlagSet enabledFeatures, boolean hasPermissions, HolderLookup.Provider holders, CallbackInfoReturnable<Boolean> cir) {
		if (ESCreativeModeTab.isForcedRebuild()) {
			cir.setReturnValue(true);
		}
	}
}
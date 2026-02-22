package cn.leolezury.eternalstarlight.fabric.mixin;

import cn.leolezury.eternalstarlight.common.EternalStarlight;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.Util;
import org.slf4j.Logger;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(Util.class)
public abstract class UtilMixin {
	// don't log the annoying data fixer error
	@WrapOperation(method = "doFetchChoiceType", at = @At(value = "INVOKE", target = "Lorg/slf4j/Logger;error(Ljava/lang/String;Ljava/lang/Object;)V", remap = false))
	private static void logError(Logger instance, String string, Object o, Operation<Void> original, @Local(argsOnly = true) String id) {
		if (!id.startsWith(EternalStarlight.ID + ":")) {
			original.call(instance, string, o);
		}
	}
}

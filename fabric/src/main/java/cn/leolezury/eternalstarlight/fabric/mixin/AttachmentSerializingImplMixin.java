package cn.leolezury.eternalstarlight.fabric.mixin;

import cn.leolezury.eternalstarlight.common.config.ESConfig;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.fabricmc.fabric.impl.attachment.AttachmentSerializingImpl;
import org.slf4j.Logger;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(AttachmentSerializingImpl.class)
public abstract class AttachmentSerializingImplMixin {
	@WrapOperation(method = "deserializeAttachmentData", at = @At(value = "INVOKE", target = "Lorg/slf4j/Logger;warn(Ljava/lang/String;Ljava/lang/Object;)V", remap = false), remap = false)
	private static void logWarning(Logger instance, String string, Object o, Operation<Void> original) {
		if (!ESConfig.INSTANCE.blockUnknownAttachmentWarning) {
			original.call(instance, string, o);
		}
	}
}

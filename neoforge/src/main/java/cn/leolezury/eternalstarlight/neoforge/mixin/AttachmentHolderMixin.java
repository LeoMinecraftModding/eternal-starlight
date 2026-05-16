package cn.leolezury.eternalstarlight.neoforge.mixin;

import cn.leolezury.eternalstarlight.common.EternalStarlight;
import cn.leolezury.eternalstarlight.common.config.ESConfig;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.neoforge.attachment.AttachmentHolder;
import org.slf4j.Logger;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(AttachmentHolder.class)
public abstract class AttachmentHolderMixin {
	@WrapOperation(method = "deserializeAttachments", at = @At(value = "INVOKE", target = "Lorg/slf4j/Logger;error(Ljava/lang/String;Ljava/lang/Object;)V", ordinal = 1, remap = false), remap = false)
	private void logError(Logger instance, String string, Object o, Operation<Void> original) {
		if (!(ESConfig.INSTANCE.blockUnknownAttachmentWarning && o instanceof String s && ResourceLocation.parse(s).getNamespace().equals(EternalStarlight.ID))) {
			original.call(instance, string, o);
		}
	}
}

package cn.leolezury.eternalstarlight.fabric.mixin;

import cn.leolezury.eternalstarlight.common.config.ESConfig;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.minecraft.SharedConstants;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtUtils;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(NbtUtils.class)
public abstract class NbtUtilsMixin {
	@WrapOperation(method = "getDataVersion", at = @At(value = "INVOKE", target = "Lnet/minecraft/nbt/CompoundTag;getInt(Ljava/lang/String;)I"))
	private static int getInt(CompoundTag instance, String string, Operation<Integer> original) {
		int version = original.call(instance, string);
		if (ESConfig.INSTANCE.enableDataFixer && version == SharedConstants.getCurrentVersion().getDataVersion().getVersion()) {
			return version - 1;
		}
		return version;
	}
}

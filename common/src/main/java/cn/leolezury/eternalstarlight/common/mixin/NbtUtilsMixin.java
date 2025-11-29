package cn.leolezury.eternalstarlight.common.mixin;

import cn.leolezury.eternalstarlight.common.EternalStarlight;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.minecraft.SharedConstants;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtUtils;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(NbtUtils.class)
public abstract class NbtUtilsMixin {
	// report a lower version if eternal starlight version is low
	// yes, this is ugly, but I can't think of a better way
	@WrapOperation(method = "getDataVersion", at = @At(value = "INVOKE", target = "Lnet/minecraft/nbt/CompoundTag;getInt(Ljava/lang/String;)I"))
	private static int getInt(CompoundTag instance, String string, Operation<Integer> original) {
		int version = original.call(instance, string);
		if (instance.getInt("eternal_starlight_version") < EternalStarlight.VERSION && version == SharedConstants.getCurrentVersion().getDataVersion().getVersion()) {
			return version - 1;
		}
		return version;
	}
}

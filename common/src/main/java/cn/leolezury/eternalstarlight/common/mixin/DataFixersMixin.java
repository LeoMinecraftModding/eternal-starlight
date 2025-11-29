package cn.leolezury.eternalstarlight.common.mixin;

import cn.leolezury.eternalstarlight.common.datafix.ESDataFixers;
import com.mojang.datafixers.DataFixerBuilder;
import net.minecraft.util.datafix.DataFixers;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(DataFixers.class)
public abstract class DataFixersMixin {
	@Inject(method = "addFixers", at = @At(value = "RETURN"))
	private static void addFixers(DataFixerBuilder builder, CallbackInfo ci) {
		ESDataFixers.addFixers(builder);
	}
}

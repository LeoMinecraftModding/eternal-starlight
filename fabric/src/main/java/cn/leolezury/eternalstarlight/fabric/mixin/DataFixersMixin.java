package cn.leolezury.eternalstarlight.fabric.mixin;

import cn.leolezury.eternalstarlight.common.config.ESConfig;
import cn.leolezury.eternalstarlight.fabric.datafix.ESDataFixers;
import com.mojang.datafixers.DataFixerBuilder;
import net.minecraft.util.datafix.DataFixers;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(DataFixers.class)
public abstract class DataFixersMixin {
	@Inject(method = "addFixers", at = @At("RETURN"))
	private static void addFixers(DataFixerBuilder builder, CallbackInfo ci) {
		if (ESConfig.INSTANCE.enableDataFixer) {
			ESDataFixers.addFixers(builder);
		}
	}
}

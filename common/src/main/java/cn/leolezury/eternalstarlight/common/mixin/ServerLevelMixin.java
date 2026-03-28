package cn.leolezury.eternalstarlight.common.mixin;

import cn.leolezury.eternalstarlight.common.data.ESDimensions;
import cn.leolezury.eternalstarlight.common.util.ESWeatherUtil;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ServerLevel.class)
public abstract class ServerLevelMixin {
	@Inject(method = "tickPrecipitation", at = @At("RETURN"))
	private void tickPrecipitation(BlockPos blockPos, CallbackInfo ci) {
		if (((ServerLevel) (Object) this).dimension() == ESDimensions.STARLIGHT_KEY) {
			ESWeatherUtil.getOrCreateWeathers(((ServerLevel) (Object) this)).getActiveWeather().ifPresent((instance) -> {
				instance.getWeather().tickBlock(((ServerLevel) (Object) this), instance.ticksSinceStarted, blockPos);
			});
		}
	}
}

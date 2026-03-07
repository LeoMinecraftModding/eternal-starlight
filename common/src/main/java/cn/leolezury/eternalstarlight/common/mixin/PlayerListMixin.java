package cn.leolezury.eternalstarlight.common.mixin;

import cn.leolezury.eternalstarlight.common.data.ESDimensions;
import cn.leolezury.eternalstarlight.common.handler.ESCommonHandler;
import cn.leolezury.eternalstarlight.common.network.SimpleActionPacket;
import cn.leolezury.eternalstarlight.common.network.UpdateWeatherPacket;
import cn.leolezury.eternalstarlight.common.platform.ESPlatform;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.players.PlayerList;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(PlayerList.class)
public abstract class PlayerListMixin {
	@Inject(method = "sendLevelInfo", at = @At("RETURN"))
	private void sendLevelInfo(ServerPlayer serverPlayer, ServerLevel serverLevel, CallbackInfo ci) {
		if (serverLevel.dimension() == ESDimensions.STARLIGHT_KEY) {
			ESCommonHandler.getActiveWeather().ifPresentOrElse((weatherInstance -> ESPlatform.INSTANCE.sendToClient(serverPlayer, new UpdateWeatherPacket(weatherInstance.getWeather()))), () -> ESPlatform.INSTANCE.sendToClient(serverPlayer, new SimpleActionPacket(SimpleActionPacket.S2C_CLEAR_WEATHER)));
		}
	}
}

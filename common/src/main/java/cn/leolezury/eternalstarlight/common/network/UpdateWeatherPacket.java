package cn.leolezury.eternalstarlight.common.network;

import cn.leolezury.eternalstarlight.common.EternalStarlight;
import cn.leolezury.eternalstarlight.common.client.weather.ClientWeatherState;
import cn.leolezury.eternalstarlight.common.registry.ESWeathers;
import cn.leolezury.eternalstarlight.common.weather.AbstractWeather;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.world.entity.player.Player;

public record UpdateWeatherPacket(AbstractWeather weather) implements CustomPacketPayload {
	public static final CustomPacketPayload.Type<UpdateWeatherPacket> TYPE = new CustomPacketPayload.Type<>(EternalStarlight.id("update_weather"));
	public static final StreamCodec<RegistryFriendlyByteBuf, UpdateWeatherPacket> STREAM_CODEC = StreamCodec.composite(
		ByteBufCodecs.idMapper(ESWeathers.WEATHERS.registry()), UpdateWeatherPacket::weather,
		UpdateWeatherPacket::new
	);

	public static void handle(UpdateWeatherPacket packet, Player player) {
		ClientWeatherState.weather = packet.weather();
	}

	@Override
	public Type<? extends CustomPacketPayload> type() {
		return TYPE;
	}
}

package cn.leolezury.eternalstarlight.common.network;

import cn.leolezury.eternalstarlight.common.EternalStarlight;
import cn.leolezury.eternalstarlight.common.client.ClientWeatherState;
import cn.leolezury.eternalstarlight.common.registry.ESWeathers;
import cn.leolezury.eternalstarlight.common.weather.AbstractWeather;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.world.entity.player.Player;

public record UpdateWeatherPacket(AbstractWeather weather) implements CustomPacketPayload {
	public static final CustomPacketPayload.Type<UpdateWeatherPacket> TYPE = new CustomPacketPayload.Type<>(EternalStarlight.id("update_weather"));
	public static final StreamCodec<RegistryFriendlyByteBuf, UpdateWeatherPacket> STREAM_CODEC = StreamCodec.ofMember(UpdateWeatherPacket::write, UpdateWeatherPacket::read);

	public static UpdateWeatherPacket read(FriendlyByteBuf buf) {
		AbstractWeather abstractWeather = ESWeathers.WEATHERS.registry().byId(buf.readInt());
		return new UpdateWeatherPacket(abstractWeather);
	}

	public static void write(UpdateWeatherPacket packet, FriendlyByteBuf buf) {
		buf.writeInt(ESWeathers.WEATHERS.registry().getId(packet.weather()));
	}

	public static void handle(UpdateWeatherPacket packet, Player player) {
		ClientWeatherState.weather = packet.weather();
	}

	@Override
	public Type<? extends CustomPacketPayload> type() {
		return TYPE;
	}
}

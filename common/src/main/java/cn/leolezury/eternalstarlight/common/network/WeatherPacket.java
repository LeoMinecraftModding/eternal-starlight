package cn.leolezury.eternalstarlight.common.network;

import cn.leolezury.eternalstarlight.common.EternalStarlight;
import cn.leolezury.eternalstarlight.common.client.ClientWeatherInfo;
import cn.leolezury.eternalstarlight.common.registry.ESWeathers;
import cn.leolezury.eternalstarlight.common.weather.AbstractWeather;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;

public record WeatherPacket(AbstractWeather weather,
                            int duration, int ticks) implements CustomPacketPayload {
    public static final CustomPacketPayload.Type<WeatherPacket> TYPE = new CustomPacketPayload.Type<>(new ResourceLocation(EternalStarlight.MOD_ID, "weather"));
    public static final StreamCodec<RegistryFriendlyByteBuf, WeatherPacket> STREAM_CODEC = StreamCodec.ofMember(WeatherPacket::write, WeatherPacket::read);

    public static WeatherPacket read(FriendlyByteBuf buf) {
        AbstractWeather abstractWeather = ESWeathers.WEATHERS.registry().byId(buf.readInt());
        int d = buf.readInt();
        int t = buf.readInt();
        return new WeatherPacket(abstractWeather, d, t);
    }

    public static void write(WeatherPacket message, FriendlyByteBuf buf) {
        buf.writeInt(ESWeathers.WEATHERS.registry().getId(message.weather()));
        buf.writeInt(message.duration);
        buf.writeInt(message.ticks);
    }

    public static void handle(WeatherPacket message, Player player) {
        ClientWeatherInfo.WEATHER = message.weather();
        ClientWeatherInfo.DURATION = message.duration();
        ClientWeatherInfo.TICKS = message.ticks();
    }

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }
}

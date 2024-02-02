package cn.leolezury.eternalstarlight.common.network;

import cn.leolezury.eternalstarlight.common.client.ClientWeatherInfo;
import cn.leolezury.eternalstarlight.common.init.WeatherInit;
import cn.leolezury.eternalstarlight.common.weather.AbstractWeather;
import net.minecraft.network.FriendlyByteBuf;

public record ESWeatherPacket(AbstractWeather weather,
                              int duration, int ticks) {

    public static ESWeatherPacket read(FriendlyByteBuf buf) {
        AbstractWeather abstractWeather = WeatherInit.WEATHERS.registry().byId(buf.readInt());
        int d = buf.readInt();
        int t = buf.readInt();
        return new ESWeatherPacket(abstractWeather, d, t);
    }

    public static void write(ESWeatherPacket message, FriendlyByteBuf buf) {
        buf.writeInt(WeatherInit.WEATHERS.registry().getId(message.weather()));
        buf.writeInt(message.duration);
        buf.writeInt(message.ticks);
    }

    public static class Handler {
        public static void handle(ESWeatherPacket message) {
            ClientWeatherInfo.weather = message.weather();
            ClientWeatherInfo.duration = message.duration();
            ClientWeatherInfo.ticks = message.ticks();
        }
    }
}

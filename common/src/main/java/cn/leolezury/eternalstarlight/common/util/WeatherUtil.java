package cn.leolezury.eternalstarlight.common.util;

import cn.leolezury.eternalstarlight.common.weather.Weathers;
import net.minecraft.server.level.ServerLevel;

public class WeatherUtil {
    private static Weathers weathers;

    public static Weathers getOrCreateWeathers(ServerLevel serverLevel) {
        if (weathers == null) {
            weathers = serverLevel.getDataStorage().computeIfAbsent(Weathers.factory(serverLevel), "weathers");
        }
        return weathers;
    }
}

package cn.leolezury.eternalstarlight.common.util;

import cn.leolezury.eternalstarlight.common.weather.Weathers;
import net.minecraft.server.level.ServerLevel;

public class ESWeatherUtil {
    public static Weathers getOrCreateWeathers(ServerLevel serverLevel) {
        return serverLevel.getDataStorage().computeIfAbsent(Weathers.factory(serverLevel), "weathers");
    }
}

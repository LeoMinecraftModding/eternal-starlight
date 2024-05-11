package cn.leolezury.eternalstarlight.common.client;

import cn.leolezury.eternalstarlight.common.weather.AbstractWeather;
import net.minecraft.util.Mth;

public class ClientWeatherInfo {
    public static AbstractWeather WEATHER;
    public static int DURATION;
    public static int TICKS;
    public static float OLD_LEVEL;
    public static float LEVEL;
    public static float LEVEL_TARGET;

    public static void tickRainLevel() {
        OLD_LEVEL = LEVEL;
        LEVEL = Mth.lerp(0.2f, LEVEL, LEVEL_TARGET);
        if (Math.abs(LEVEL_TARGET - LEVEL) < 0.01) {
            LEVEL = LEVEL_TARGET;
        }
    }

    public static float getRainLevel(float partialTick) {
        return Mth.lerp(partialTick, OLD_LEVEL, LEVEL);
    }
}

package cn.leolezury.eternalstarlight.common.client;

import cn.leolezury.eternalstarlight.common.weather.AbstractWeather;
import net.minecraft.util.Mth;

public class ClientWeatherState {
	public static AbstractWeather weather;
	public static float oldLevel;
	public static float level;
	public static float levelTarget;

	public static void tickRainLevel() {
		oldLevel = level;
		level = Mth.lerp(0.12f, level, levelTarget);
		if (Math.abs(levelTarget - level) < 0.01) {
			level = levelTarget;
		}
	}

	public static float getRainLevel(float partialTick) {
		return Mth.lerp(partialTick, oldLevel, level);
	}
}

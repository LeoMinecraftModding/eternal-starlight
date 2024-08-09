package cn.leolezury.eternalstarlight.common.weather;

import cn.leolezury.eternalstarlight.common.registry.ESWeathers;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.Util;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.valueproviders.IntProvider;

public abstract class AbstractWeather {
	private final Properties properties;

	public Properties weatherProperties() {
		return properties;
	}

	public AbstractWeather(Properties properties) {
		this.properties = properties;
	}

	public abstract boolean canStart(ServerLevel level);

	public abstract boolean canContinue(ServerLevel level, int ticks);

	public abstract void serverTick(ServerLevel level, int ticks);

	public abstract void tickBlock(ServerLevel level, int ticks, BlockPos pos);

	public abstract void onStart(ServerLevel level);

	public abstract void onStop(ServerLevel level, int ticks);

	@Environment(EnvType.CLIENT)
	public abstract void clientTick();

	@Environment(EnvType.CLIENT)
	public abstract float modifyRainLevel(float original);

	public String getDescriptionId() {
		return Util.makeDescriptionId("weather", ESWeathers.WEATHERS.registry().getKey(this));
	}

	public Component getDescription() {
		return Component.translatable(getDescriptionId());
	}

	public record Properties(IntProvider duration, IntProvider interval) {

	}
}

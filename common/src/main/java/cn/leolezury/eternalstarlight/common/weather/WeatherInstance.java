package cn.leolezury.eternalstarlight.common.weather;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;

public class WeatherInstance {
	private static final String TAG_ACTIVE = "active";
	private static final String TAG_WEATHER_TICKS = "weather_ticks";
	private static final String TAG_DURATION = "duration";
	private static final String TAG_INTERVAL_TICKS = "interval_ticks";
	private static final String TAG_INTERVAL = "interval";

	private final ServerLevel serverLevel;
	private final AbstractWeather weather;

	public AbstractWeather getWeather() {
		return weather;
	}

	public boolean active;
	public int ticksSinceStarted;
	public int currentDuration;
	public int ticksUntilNext;
	public int currentInterval;

	public WeatherInstance(ServerLevel serverLevel, AbstractWeather weather) {
		this.serverLevel = serverLevel;
		this.weather = weather;
		this.active = false;
		this.ticksUntilNext = this.currentInterval = weather.weatherProperties().interval().sample(serverLevel.getRandom());
	}

	public void load(CompoundTag compoundTag) {
		this.active = compoundTag.getBoolean(TAG_ACTIVE);
		this.ticksSinceStarted = compoundTag.getInt(TAG_WEATHER_TICKS);
		this.currentDuration = compoundTag.getInt(TAG_DURATION);
		this.ticksUntilNext = compoundTag.getInt(TAG_INTERVAL_TICKS);
		this.currentInterval = compoundTag.getInt(TAG_INTERVAL);
	}

	public void save(CompoundTag compoundTag) {
		compoundTag.putBoolean(TAG_ACTIVE, this.active);
		compoundTag.putInt(TAG_WEATHER_TICKS, this.ticksSinceStarted);
		compoundTag.putInt(TAG_DURATION, this.currentDuration);
		compoundTag.putInt(TAG_INTERVAL_TICKS, this.ticksUntilNext);
		compoundTag.putInt(TAG_INTERVAL, this.currentInterval);
	}

	public void start() {
		this.weather.onStart(serverLevel);
		active = true;
		ticksSinceStarted = 0;
		ticksUntilNext = 0;
		currentDuration = weather.weatherProperties().duration().sample(serverLevel.getRandom());
	}

	public void stop() {
		this.weather.onStop(serverLevel, ticksSinceStarted);
		active = false;
		ticksSinceStarted = 0;
		ticksUntilNext = currentInterval = weather.weatherProperties().interval().sample(serverLevel.getRandom());
	}

	// returns whether the instance wants to be active
	public boolean tick() {
		if (active) {
			ticksSinceStarted++;
			ticksUntilNext = 0;
			if (ticksSinceStarted <= currentDuration && this.weather.canContinue(serverLevel, ticksSinceStarted)) {
				this.weather.serverWeatherTick(serverLevel, ticksSinceStarted);
			} else {
				stop();
			}
		} else {
			ticksUntilNext--;
			if (ticksUntilNext <= 0) {
				ticksSinceStarted = 0;
				ticksUntilNext = 0;
				return this.weather.canStart(serverLevel);
			}
		}
		return false;
	}
}

package cn.leolezury.eternalstarlight.common.weather;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;

public class WeatherInstance {
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
        this.active = compoundTag.getBoolean("Active");
        this.ticksSinceStarted = compoundTag.getInt("WeatherTick");
        this.currentDuration = compoundTag.getInt("Duration");
        this.ticksUntilNext = compoundTag.getInt("IntervalTick");
        this.currentInterval = compoundTag.getInt("Interval");
    }

    public void save(CompoundTag compoundTag) {
        compoundTag.putBoolean("Active", this.active);
        compoundTag.putInt("WeatherTick", this.ticksSinceStarted);
        compoundTag.putInt("Duration", this.currentDuration);
        compoundTag.putInt("IntervalTick", this.ticksUntilNext);
        compoundTag.putInt("Interval", this.currentInterval);
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

package cn.leolezury.eternalstarlight.common.weather;

import cn.leolezury.eternalstarlight.common.init.WeatherInit;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.saveddata.SavedData;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

public class Weathers extends SavedData {
    private final ServerLevel serverLevel;
    private final List<WeatherInstance> weathers = new ArrayList<>();

    public Weathers(ServerLevel serverLevel) {
        this.serverLevel = serverLevel;
        WeatherInit.WEATHERS.registry().forEach(weather -> {
            WeatherInstance instance = new WeatherInstance(serverLevel, weather);
            weathers.add(instance);
        });
    }

    public List<WeatherInstance> getWeathers() {
        return weathers;
    }

    public Optional<WeatherInstance> getActiveWeather() {
        for (WeatherInstance instance : getWeathers()) {
            if (instance.active) {
                return Optional.of(instance);
            }
        }
        return Optional.empty();
    }

    public void setActiveWeather(AbstractWeather weather, int duration) {
        for (WeatherInstance instance : getWeathers()) {
            if (!instance.active && instance.getWeather() == weather) {
                instance.start();
                instance.currentDuration = duration;
            }
            if (instance.active && !(instance.getWeather() == weather)) {
                instance.stop();
            }
        }
        setDirty();
    }

    public void clearAllWeathers(int duration) {
        for (WeatherInstance instance : getWeathers()) {
            instance.stop();
            instance.ticksUntilNext = duration;
        }
        setDirty();
    }

    public static SavedData.Factory<Weathers> factory(ServerLevel serverLevel) {
        return new SavedData.Factory<>(() -> new Weathers(serverLevel), (compoundTag) -> load(serverLevel, compoundTag), null);
    }

    public void tick() {
        List<WeatherInstance> canStartWeathers = new ArrayList<>();
        boolean hasActive = false;
        for (WeatherInstance instance : weathers) {
            if (instance.tick()) {
                canStartWeathers.add(instance);
            }
            hasActive = hasActive || instance.active;
        }
        if (!hasActive && !canStartWeathers.isEmpty()) {
            canStartWeathers.get(serverLevel.getRandom().nextInt(canStartWeathers.size())).start();
        }
        setDirty();
    }

    public static Weathers load(ServerLevel serverLevel, CompoundTag compoundTag) {
        Weathers weathersData = new Weathers(serverLevel);
        if (compoundTag.contains("Weathers", CompoundTag.TAG_COMPOUND)) {
            CompoundTag weathersTag = compoundTag.getCompound("Weathers");
            WeatherInit.WEATHERS.registry().forEach(weather -> {
                WeatherInstance instance = new WeatherInstance(serverLevel, weather);
                String id = Objects.requireNonNull(WeatherInit.WEATHERS.registry().getKey(weather)).toString();
                if (weathersTag.contains(id, CompoundTag.TAG_COMPOUND)) {
                    instance.load(weathersTag.getCompound(id));
                }
                weathersData.weathers.add(instance);
            });
        }
        return weathersData;
    }

    @Override
    public CompoundTag save(CompoundTag compoundTag) {
        CompoundTag weathersTag = new CompoundTag();
        for (WeatherInstance instance : weathers) {
            String id = Objects.requireNonNull(WeatherInit.WEATHERS.registry().getKey(instance.getWeather())).toString();
            CompoundTag weatherTag = new CompoundTag();
            instance.save(weatherTag);
            weathersTag.put(id, weatherTag);
        }
        compoundTag.put("Weathers", weathersTag);
        return compoundTag;
    }
}

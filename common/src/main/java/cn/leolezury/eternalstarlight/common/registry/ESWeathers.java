package cn.leolezury.eternalstarlight.common.registry;

import cn.leolezury.eternalstarlight.common.EternalStarlight;
import cn.leolezury.eternalstarlight.common.platform.registry.RegistrationProvider;
import cn.leolezury.eternalstarlight.common.platform.registry.RegistryObject;
import cn.leolezury.eternalstarlight.common.weather.AbstractWeather;
import cn.leolezury.eternalstarlight.common.weather.EmptyWeather;
import cn.leolezury.eternalstarlight.common.weather.MeteorShowerWeather;
import com.mojang.serialization.Codec;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.util.valueproviders.UniformInt;

public class ESWeathers {
	public static final ResourceKey<Registry<AbstractWeather>> REGISTRY_KEY = ResourceKey.createRegistryKey(EternalStarlight.id("weather"));
	public static final RegistrationProvider<AbstractWeather> WEATHERS = RegistrationProvider.newRegistry(REGISTRY_KEY, EternalStarlight.ID);
	public static final Codec<AbstractWeather> CODEC = WEATHERS.registry().byNameCodec();
	public static final RegistryObject<AbstractWeather, MeteorShowerWeather> METEOR_SHOWER = WEATHERS.register("meteor_shower", () -> new MeteorShowerWeather(new AbstractWeather.Properties(UniformInt.of(1200, 2400), UniformInt.of(36000, 180000))));
	public static final RegistryObject<AbstractWeather, EmptyWeather> AURORA = WEATHERS.register("aurora", () -> new EmptyWeather(new AbstractWeather.Properties(UniformInt.of(6000, 12000), UniformInt.of(36000, 180000))));

	public static void loadClass() {
	}
}

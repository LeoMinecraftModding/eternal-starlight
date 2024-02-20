package cn.leolezury.eternalstarlight.common.registry;

import cn.leolezury.eternalstarlight.common.EternalStarlight;
import cn.leolezury.eternalstarlight.common.platform.registry.RegistrationProvider;
import cn.leolezury.eternalstarlight.common.platform.registry.RegistryObject;
import cn.leolezury.eternalstarlight.common.weather.AbstractWeather;
import cn.leolezury.eternalstarlight.common.weather.MeteorRainWeather;
import com.mojang.serialization.Codec;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.ExtraCodecs;
import net.minecraft.util.valueproviders.UniformInt;

public class ESWeathers {
    public static final ResourceKey<Registry<AbstractWeather>> REGISTRY_KEY = ResourceKey.createRegistryKey(new ResourceLocation(EternalStarlight.MOD_ID, "weather"));
    public static final RegistrationProvider<AbstractWeather> WEATHERS = RegistrationProvider.newRegistry(REGISTRY_KEY, EternalStarlight.MOD_ID);
    public static final Codec<AbstractWeather> CODEC = ExtraCodecs.lazyInitializedCodec(WEATHERS.registry()::byNameCodec);
    public static final RegistryObject<AbstractWeather, MeteorRainWeather> METEOR_RAIN = WEATHERS.register("meteor_rain", () -> new MeteorRainWeather(new AbstractWeather.Properties(UniformInt.of(600, 800), UniformInt.of(24000, 180000))));
    public static void loadClass() {}
}

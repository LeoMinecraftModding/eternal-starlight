package cn.leolezury.eternalstarlight.common.platform.registry;

import cn.leolezury.eternalstarlight.common.platform.ESPlatform;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;

import java.util.function.Supplier;

public interface RegistrationProvider<T> {
    static <T> RegistrationProvider<T> get(ResourceKey<? extends Registry<T>> key, String namespace) {
        return ESPlatform.INSTANCE.createRegistrationProvider(key, namespace);
    }

    <I extends T> RegistryObject<T, I> register(String id, Supplier<? extends I> supplier);
}

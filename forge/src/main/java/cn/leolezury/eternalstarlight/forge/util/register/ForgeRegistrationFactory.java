/*
 * This file and all files in subdirectories of the file's parent are provided by the
 * RegistrationUtils Gradle plugin, and are licensed under the MIT license.
 * More info at https://github.com/Matyrobbrt/RegistrationUtils.
 *
 * MIT License
 *
 * Copyright (c) 2022 Matyrobbrt
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package cn.leolezury.eternalstarlight.forge.util.register;

import cn.leolezury.eternalstarlight.common.util.register.RegistrationProvider;
import cn.leolezury.eternalstarlight.common.util.register.RegistryObject;
import cn.leolezury.eternalstarlight.common.util.register.registries.RegistryBuilder;
import cn.leolezury.eternalstarlight.common.util.register.registries.RegistryFeatureType;
import com.google.auto.service.AutoService;
import com.google.common.base.Suppliers;
import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.NewRegistryEvent;
import org.jetbrains.annotations.ApiStatus;

import javax.annotation.Nonnull;
import java.util.*;
import java.util.function.Supplier;

@AutoService(RegistrationProvider.Factory.class)
public class ForgeRegistrationFactory implements RegistrationProvider.Factory {

    @Override
    public <T> RegistrationProvider<T> create(ResourceKey<? extends Registry<T>> resourceKey, String modId) {
        final var register = DeferredRegister.create(resourceKey, modId);
        final Provider<T> provider = new Provider<>(modId, register);

        final IEventBus bus = getBus(modId);
        register.register(bus);
        bus.addListener(provider::onNewRegistry);

        return provider;
    }

    @Nonnull
    @ApiStatus.Internal
    static IEventBus getBus(String modId) {
        if (modId.equals("minecraft"))
            modId = "forge"; // Defer minecraft namespace to forge bus
        final var containerOpt = ModList.get().getModContainerById(modId);
        if (containerOpt.isEmpty())
            throw new NullPointerException("Cannot find mod container for id " + modId);
        final var modBus = ForgeBusGetter.getBus(containerOpt.get());
        if (modBus == null) {
            throw new NullPointerException("Cannot get the mod event bus for the mod container with the mod id of " + modId);
        }
        return modBus;
    }

    private static class Provider<T> implements RegistrationProvider<T> {
        private final String modId;
        private final DeferredRegister<T> registry;
        private net.minecraftforge.registries.RegistryBuilder<T> regBuilder;

        private final Set<RegistryObject<T>> entries = new HashSet<>();
        private final Set<RegistryObject<T>> entriesView = Collections.unmodifiableSet(entries);

        private Provider(String modId, DeferredRegister<T> registry) {
            this.modId = modId;
            this.registry = registry;
        }

        private void onNewRegistry(NewRegistryEvent event) {
            if (regBuilder != null) {
                event.create(regBuilder);
            }
        }

        @Override
        public String getModId() {
            return modId;
        }

        @Override
        public ResourceKey<? extends Registry<T>> getRegistryKey() {
            return registry.getRegistryKey();
        }

        @SuppressWarnings("unchecked")
        private final Supplier<Registry<T>> registryInstance = Suppliers.memoize(() -> (Registry<T>) get(BuiltInRegistries.REGISTRY, getRegistryKey()));
        @Override
        public Registry<T> getRegistry() {
            return registryInstance.get();
        }

        @SuppressWarnings("unchecked")
        private static <T> T get(Registry<T> registry, ResourceKey<?> key) {
            return registry.get((ResourceKey<T>) key);
        }

        @Override
        @SuppressWarnings("unchecked")
        public <I extends T> RegistryObject<I> register(String name, Supplier<? extends I> supplier) {
            final var obj = registry.<I>register(name, supplier);
            final var ro = new RegistryObject<I>() {

                @Override
                public ResourceKey<I> getResourceKey() {
                    return obj.getKey();
                }

                @Override
                public ResourceLocation getId() {
                    return obj.getId();
                }

                @Override
                public I get() {
                    return obj.get();
                }

                @Override
                public Holder<I> asHolder() {
                    return obj.getHolder().orElseThrow();
                }
            };
            entries.add((RegistryObject<T>) ro);
            return ro;
        }

        @Override
        public Set<RegistryObject<T>> getEntries() {
            return entriesView;
        }

        @Override
        public RegistryBuilder<T> registryBuilder() {
            return new Builder();
        }

        private final class Builder implements RegistryBuilder<T> {
            private final net.minecraftforge.registries.RegistryBuilder<T> builder = new net.minecraftforge.registries.RegistryBuilder<>();
            private final Map<RegistryFeatureType<?>, Object> features = new HashMap<>();

            public Builder() {
                builder.hasTags(); // We need a wrapper
            }

            @Override
            public <X> RegistryBuilder<T> withFeature(RegistryFeatureType<X> type, X value) {
                features.put(type, value);
                return this;
            }

            @Override
            public RegistryBuilder<T> withFeature(RegistryFeatureType<Void> type) {
                return this.withFeature(type, null);
            }

            @Override
            public RegistryBuilder<T> withDefaultValue(String id, Supplier<T> defaultValueSupplier) {
                register(id, defaultValueSupplier);
                return withFeature(RegistryFeatureType.DEFAULTED, new ResourceLocation(modId, id));
            }

            @Override
            public Supplier<Registry<T>> build() {
                configureBuilder();
                Provider.this.regBuilder = builder.setName(getRegistryKey().location());
                return Provider.this.registryInstance;
            }

            private void configureBuilder() {
                if (!features.containsKey(RegistryFeatureType.SYNCED)) {
                    builder.disableSync();
                }
                if (!features.containsKey(RegistryFeatureType.SAVED_TO_DISK)) {
                    builder.disableSaving();
                }
                if (!features.containsKey(RegistryFeatureType.SUPPORTS_OVERRIDES)) {
                    builder.disableOverrides();
                }

                if (features.containsKey(RegistryFeatureType.DEFAULTED)) {
                    builder.setDefaultKey((ResourceLocation) features.get(RegistryFeatureType.DEFAULTED));
                }
            }
        }
    }
}

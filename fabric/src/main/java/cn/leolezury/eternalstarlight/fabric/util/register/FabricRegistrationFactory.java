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

package cn.leolezury.eternalstarlight.fabric.util.register;

import cn.leolezury.eternalstarlight.common.util.register.RegistrationProvider;
import cn.leolezury.eternalstarlight.common.util.register.RegistryObject;
import cn.leolezury.eternalstarlight.common.util.register.registries.RegistryBuilder;
import cn.leolezury.eternalstarlight.common.util.register.registries.RegistryFeatureType;
import com.google.auto.service.AutoService;
import com.google.common.base.Suppliers;
import com.mojang.serialization.Lifecycle;
import net.fabricmc.fabric.api.event.registry.FabricRegistryBuilder;
import net.fabricmc.fabric.api.event.registry.RegistryAttribute;
import net.minecraft.core.DefaultedMappedRegistry;
import net.minecraft.core.Holder;
import net.minecraft.core.MappedRegistry;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.ApiStatus;

import java.util.*;
import java.util.function.Supplier;

@AutoService(RegistrationProvider.Factory.class)
public class FabricRegistrationFactory implements RegistrationProvider.Factory {

    @Override
    public <T> RegistrationProvider<T> create(ResourceKey<? extends Registry<T>> resourceKey, String modId) {
        return new Provider<>(modId, resourceKey);
    }

    @Override
    public <T> RegistrationProvider<T> create(Registry<T> registry, String modId) {
        return new Provider<>(modId, registry);
    }

    private static class Provider<T> implements RegistrationProvider<T>, InternalFabricHelper<T> {
        private final String modId;
        private final Supplier<Registry<T>> registry;
        private final ResourceKey<? extends Registry<T>> registryKey;

        private final Set<RegistryObject<T>> entries = new HashSet<>();
        private final Set<RegistryObject<T>> entriesView = Collections.unmodifiableSet(entries);

        @SuppressWarnings({"unchecked"})
        private Provider(String modId, ResourceKey<? extends Registry<T>> key) {
            this.modId = modId;

            this.registry = Suppliers.memoize(() -> {
                final var reg = BuiltInRegistries.REGISTRY.get(key.location());
                if (reg == null) {
                    throw new RuntimeException("Registry with name " + key.location() + " was not found!");
                }
                return (Registry<T>) reg;
            });
            this.registryKey = key;
        }

        private Provider(String modId, Registry<T> registry) {
            this.modId = modId;
            this.registry = Suppliers.ofInstance(registry);
            this.registryKey = registry.key();
        }

        @Override
        public Registry<T> getRegistry() {
            return registry.get();
        }

        @Override
        public ResourceKey<? extends Registry<T>> getRegistryKey() {
            return registryKey;
        }

        @Override
        public <I extends T> RegistryObject<I> register(String name, Supplier<? extends I> supplier) {
            final var rl = new ResourceLocation(modId, name);
            return create(rl, Registry.register(registry.get(), rl, supplier.get()));
        }

        @Override
        @SuppressWarnings("unchecked")
        public <I extends T> RegistryObject<I> create(ResourceLocation rl, I obj) {
            final var ro = new RegistryObject<I>() {
                final ResourceKey<I> key = ResourceKey.create((ResourceKey<? extends Registry<I>>) getRegistryKey(), rl);

                @Override
                public ResourceKey<I> getResourceKey() {
                    return key;
                }

                @Override
                public ResourceLocation getId() {
                    return rl;
                }

                @Override
                public I get() {
                    return obj;
                }

                @Override
                public Holder<I> asHolder() {
                    return (Holder<I>) registry.get().getHolderOrThrow((ResourceKey<T>) this.key);
                }
            };
            entries.add((RegistryObject<T>) ro);
            return ro;
        }

        @Override
        public Collection<RegistryObject<T>> getEntries() {
            return entriesView;
        }

        @Override
        public String getModId() {
            return modId;
        }

        @Override
        public RegistryBuilder<T> registryBuilder() {
            return new Builder();
        }

        private final class Builder implements RegistryBuilder<T> {
            private final Map<RegistryFeatureType<?>, Object> features = new HashMap<>();
            private Supplier<T> defaultValueSupplier;

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
                this.defaultValueSupplier = defaultValueSupplier;
                return this.withFeature(RegistryFeatureType.DEFAULTED, new ResourceLocation(modId, id));
            }

            @Override
            public Supplier<Registry<T>> build() {
                final FabricRegistryBuilder<T, MappedRegistry<T>> builder = FabricRegistryBuilder.from(makeRegistry());

                if (features.containsKey(RegistryFeatureType.SYNCED)) {
                    builder.attribute(RegistryAttribute.SYNCED);
                }
                /*if (features.containsKey(RegistryFeatureType.SAVED_TO_DISK)) {
                    builder.attribute(RegistryAttribute.PERSISTED);
                }*/

                final Supplier<Registry<T>> sup = Suppliers.ofInstance(builder.buildAndRegister());
                if (defaultValueSupplier != null) {
                    Registry.register(sup.get(), (ResourceLocation) features.get(RegistryFeatureType.DEFAULTED), defaultValueSupplier.get());
                }
                return sup;
            }

            public MappedRegistry<T> makeRegistry() {
                if (features.containsKey(RegistryFeatureType.DEFAULTED)) {
                    return new DefaultedMappedRegistry<>(
                            ((ResourceLocation) features.get(RegistryFeatureType.DEFAULTED)).toString(),
                            registryKey,
                            Lifecycle.stable(),
                            false
                    );
                }
                return new MappedRegistry<>(
                        registryKey, Lifecycle.stable(), false
                );
            }
        }
    }

    @ApiStatus.Internal
    interface InternalFabricHelper<T> {
        <I extends T> RegistryObject<I> create(ResourceLocation name, I object);
    }
}
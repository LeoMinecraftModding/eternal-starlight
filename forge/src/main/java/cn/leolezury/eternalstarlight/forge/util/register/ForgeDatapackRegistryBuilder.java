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

import cn.leolezury.eternalstarlight.common.util.register.registries.DatapackRegistry;
import cn.leolezury.eternalstarlight.common.util.register.registries.DatapackRegistryBuilder;
import cn.leolezury.eternalstarlight.common.util.register.util.DatapackRegistryGenerator;
import com.google.auto.service.AutoService;
import com.mojang.serialization.Codec;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.Registry;
import net.minecraft.core.RegistryAccess;
import net.minecraft.core.RegistrySetBuilder;
import net.minecraft.data.DataProvider;
import net.minecraft.resources.ResourceKey;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DataPackRegistryEvent;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.annotation.ParametersAreNonnullByDefault;
import java.util.Objects;
import java.util.concurrent.CompletableFuture;

@ParametersAreNonnullByDefault
public class ForgeDatapackRegistryBuilder<T> implements DatapackRegistryBuilder<T> {
    private final ResourceKey<Registry<T>> key;
    private Codec<T> elementCodec;
    private @Nullable Codec<T> networkCodec;
    private @Nullable RegistrySetBuilder.RegistryBootstrap<T> bootstrap;

    private ForgeDatapackRegistryBuilder(ResourceKey<Registry<T>> key) {
        this.key = Objects.requireNonNull(key, "registry key must not be null");
    }

    @Override
    public DatapackRegistryBuilder<T> withElementCodec(@NotNull Codec<T> codec) {
        this.elementCodec = Objects.requireNonNull(codec, "element codec must not be null");
        return this;
    }

    @Override
    public DatapackRegistryBuilder<T> withNetworkCodec(@Nullable Codec<T> codec) {
        this.networkCodec = codec;
        return this;
    }

    @Override
    public DatapackRegistryBuilder<T> withBootstrap(@Nullable RegistrySetBuilder.RegistryBootstrap<T> bootstrap) {
        this.bootstrap = bootstrap;
        return this;
    }

    @Override
    public DatapackRegistry<T> build() {
        final IEventBus bus = ForgeRegistrationFactory.getBus(key.location().getNamespace());
        bus.addListener((final DataPackRegistryEvent.NewRegistry event) -> event.dataPackRegistry(key, Objects.requireNonNull(elementCodec, "element codec must not be null"), networkCodec));

        return new DatapackRegistry<>() {
            @Override
            public ResourceKey<Registry<T>> key() {
                return key;
            }

            @Override
            public DataProvider.Factory<DataProvider> bootstrapDataGenerator(CompletableFuture<HolderLookup.Provider> lookupProvider) {
                return out -> new DatapackRegistryGenerator(out, lookupProvider, registryData -> registryData.key() == key());
            }

            @Override
            public void addToSet(RegistrySetBuilder builder) {
                builder.add(key, bootstrap == null ? ctx -> {} : bootstrap);
            }

            @Override
            public Registry<T> get(RegistryAccess registryAccess) {
                return registryAccess.registryOrThrow(key);
            }
        };
    }

    @AutoService(DatapackRegistryBuilder.$Factory.class)
    public static final class Factory implements $Factory {

        @Override
        public <T> DatapackRegistryBuilder<T> newBuilder(ResourceKey<Registry<T>> key) {
            return new ForgeDatapackRegistryBuilder<>(key);
        }
    }
}

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

package cn.leolezury.eternalstarlight.util.register.registries;

import net.minecraft.Util;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.Registry;
import net.minecraft.core.RegistryAccess;
import net.minecraft.core.RegistrySetBuilder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.DataProvider;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;

import java.util.concurrent.CompletableFuture;

/**
 * A utility class that wraps a datapack registry.
 *
 * @param <T> the type of the registry
 */
public interface DatapackRegistry<T> {
    /**
     * Creates a {@link DatapackRegistryBuilder} with the given {@code key}.
     *
     * @param key the key of the registry. This will be used for determining the path of the registry folder,
     *            which will be {@code data/<datapack_namespace>/key_namespace/key_path/}
     * @param <T> the type of the registry
     * @return a builder
     */
    static <T> DatapackRegistryBuilder<T> builder(ResourceKey<Registry<T>> key) {
        return DatapackRegistryBuilder.$Factory.INSTANCE.newBuilder(key);
    }

    /**
     * Creates a {@link DatapackRegistryBuilder} with the given {@code key}.
     *
     * @param key the key of the registry. This will be used for determining the path of the registry folder,
     *            which will be {@code data/<datapack_namespace>/key_namespace/key_path/}
     * @param <T> the type of the registry
     * @return a builder
     */
    static <T> DatapackRegistryBuilder<T> builder(ResourceLocation key) {
        return builder(ResourceKey.createRegistryKey(key));
    }

    /**
     * {@return the key of the registry}
     */
    ResourceKey<Registry<T>> key();

    /**
     * {@return a DataProvider factory that generates the bootstrap entries of this datapack registry}
     *
     * @param lookupProvider the provider to generate entries from
     */
    DataProvider.Factory<DataProvider> bootstrapDataGenerator(CompletableFuture<HolderLookup.Provider> lookupProvider);

    /**
     * {@return a DataProvider factory that generates the bootstrap entries of this datapack registry from a set to which entries have been added via {@link #addToSet(RegistrySetBuilder)}}
     */
    default DataProvider.Factory<DataProvider> bootstrapDataGenerator() {
        return bootstrapDataGenerator(CompletableFuture.supplyAsync(() -> {
            final RegistryAccess.Frozen access = RegistryAccess.fromRegistryOfRegistries(BuiltInRegistries.REGISTRY);
            final RegistrySetBuilder builder = new RegistrySetBuilder();
            addToSet(builder);
            return builder.build(access);
        }, Util.backgroundExecutor()));
    }

    /**
     * Adds this registry and its bootstrap to the {@code builder}.
     */
    void addToSet(RegistrySetBuilder builder);

    /**
     * Gets the registry from the given {@code registryAccess}.
     *
     * @param registryAccess the access to get the registry from
     * @return the registry
     * @see RegistryAccess#registryOrThrow(ResourceKey)
     * @throws IllegalStateException if the registry does not exist in the access
     */
    Registry<T> get(RegistryAccess registryAccess);
}

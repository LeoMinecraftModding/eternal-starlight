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

package cn.leolezury.eternalstarlight.common.util.register.util;

import com.google.gson.JsonElement;
import com.mojang.logging.LogUtils;
import com.mojang.serialization.DynamicOps;
import com.mojang.serialization.Encoder;
import com.mojang.serialization.JsonOps;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.Registry;
import net.minecraft.data.CachedOutput;
import net.minecraft.data.DataProvider;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.RegistryDataLoader;
import net.minecraft.resources.RegistryOps;
import net.minecraft.resources.ResourceKey;
import org.slf4j.Logger;

import javax.annotation.ParametersAreNonnullByDefault;
import java.nio.file.Path;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.function.Predicate;

/**
 * A {@link DataProvider} used to generate bootstrap entries of all registries that match the given {@code predicate}.
 */
@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public class DatapackRegistryGenerator implements DataProvider {
    private static final Logger LOGGER = LogUtils.getLogger();
    private final PackOutput output;
    private final CompletableFuture<HolderLookup.Provider> registries;
    private final Predicate<RegistryDataLoader.RegistryData<?>> predicate;

    public DatapackRegistryGenerator(PackOutput output, CompletableFuture<HolderLookup.Provider> lookup, Predicate<RegistryDataLoader.RegistryData<?>> predicate) {
        this.registries = lookup;
        this.output = output;
        this.predicate = predicate;
    }

    @Override
    public CompletableFuture<?> run(CachedOutput output) {
        return this.registries.thenCompose((lookup) -> {
            final DynamicOps<JsonElement> ops = RegistryOps.create(JsonOps.INSTANCE, lookup);
            return CompletableFuture.allOf(RegistryDataLoader.WORLDGEN_REGISTRIES.stream()
                    .filter(predicate)
                    .flatMap(data -> this.dumpRegistryCap(output, lookup, ops, data).stream())
                    .toArray(CompletableFuture[]::new));
        });
    }

    private <T> Optional<CompletableFuture<?>> dumpRegistryCap(CachedOutput output, HolderLookup.Provider lookup, DynamicOps<JsonElement> ops, RegistryDataLoader.RegistryData<T> data) {
        final ResourceKey<? extends Registry<T>> registryKey = data.key();
        return lookup.lookup(registryKey).map((registry) -> {
            final PackOutput.PathProvider pathProvider = this.output.createPathProvider(PackOutput.Target.DATA_PACK, registryKey.location().getPath());
            return CompletableFuture.allOf(registry.listElements().map((value) -> dumpValue(pathProvider.json(value.key().location()), output, ops, data.elementCodec(), value.value()))
                    .toArray(CompletableFuture[]::new));
        });
    }

    private static <E> CompletableFuture<?> dumpValue(Path path, CachedOutput output, DynamicOps<JsonElement> ops, Encoder<E> codec, E value) {
        final Optional<JsonElement> encoded = codec.encodeStart(ops, value)
                .resultOrPartial((error) -> LOGGER.error("Couldn't serialize element {}: {}", path, error));
        return encoded.isPresent() ? DataProvider.saveStable(output, encoded.get(), path) : CompletableFuture.completedFuture(null);
    }

    @Override
    public String getName() {
        return predicate + " registry";
    }
}

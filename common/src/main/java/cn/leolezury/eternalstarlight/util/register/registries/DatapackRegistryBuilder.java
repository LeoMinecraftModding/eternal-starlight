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

import cn.leolezury.eternalstarlight.util.register.util.InternalRegUtils;
import com.mojang.serialization.Codec;
import net.minecraft.core.Registry;
import net.minecraft.core.RegistrySetBuilder;
import net.minecraft.resources.ResourceKey;
import org.jetbrains.annotations.ApiStatus;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;
import java.util.concurrent.CompletableFuture;

/**
 * A builder used to create custom datapack registries.
 *
 * @see DatapackRegistry#builder(ResourceKey)
 */
@ParametersAreNonnullByDefault
public interface DatapackRegistryBuilder<T> {

    /**
     * Sets the codec used to decode registry elements from json.
     *
     * @param codec the codec used to decode registry elements from json
     * @return the builder instance
     */
    DatapackRegistryBuilder<T> withElementCodec(@Nonnull Codec<T> codec);

    /**
     * Sets the codec used to encode registry elements to network for client syncing. <br>
     * Setting it to {@code null} or not giving it a value means the registry will not be synced to clients,
     * and as such not accessible on the logical client.
     *
     * @param codec the network codec
     * @return the builder instance
     */
    DatapackRegistryBuilder<T> withNetworkCodec(@Nullable Codec<T> codec);

    /**
     * Sets the datagen {@link net.minecraft.core.RegistrySetBuilder.RegistryBootstrap} for this registry. <br>
     *
     * @param bootstrap the datagen registry bootstrap
     * @see DatapackRegistry#bootstrapDataGenerator(CompletableFuture)
     * @see DatapackRegistry#addToSet(RegistrySetBuilder)
     * @return the builder instance
     */
    DatapackRegistryBuilder<T> withBootstrap(@Nullable RegistrySetBuilder.RegistryBootstrap<T> bootstrap);

    /**
     * Builds and registers this registry.
     *
     * @return a {@link DatapackRegistry} instance which may be ignored
     */
    DatapackRegistry<T> build();

    @ApiStatus.Internal
    interface $Factory {
        $Factory INSTANCE = InternalRegUtils.getOneAndOnlyService($Factory.class);

        <T> DatapackRegistryBuilder<T> newBuilder(ResourceKey<Registry<T>> key);
    }
}

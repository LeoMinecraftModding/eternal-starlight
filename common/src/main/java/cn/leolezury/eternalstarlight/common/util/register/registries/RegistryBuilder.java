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

package cn.leolezury.eternalstarlight.common.util.register.registries;

import cn.leolezury.eternalstarlight.common.util.register.RegistrationProvider;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;

import java.util.function.Supplier;

/**
 * A builder for creating registries.
 *
 * @param <T> the type of the registry
 * @see RegistrationProvider#registryBuilder()
 */
public interface RegistryBuilder<T> {
    /**
     * Adds a feature to the registry.
     *
     * @param type  the type of the feature
     * @param value the value of the feature
     * @param <X>   the type of the feature
     * @return the builder instance value
     */
    <X> RegistryBuilder<T> withFeature(RegistryFeatureType<X> type, X value);

    /**
     * Adds a feature with no arguments to the registry.
     *
     * @param type the type of the feature
     * @return the builder instance value
     */
    RegistryBuilder<T> withFeature(RegistryFeatureType<Void> type);

    /**
     * Sets the default value of the registry. <br>
     * This is equivalent to {@link #withFeature(RegistryFeatureType, Object) withFetaure(RegistryFeatureType.DEFAULTED, new ResourceLocation(modId, id))} and {@link RegistrationProvider#register(String, Supplier)}.
     *
     * @param id                   the ID of the default value. This is just the {@linkplain ResourceLocation#getNamespace() namespace}
     * @param defaultValueSupplier a supplier of the default value
     * @return the builder instance
     */
    RegistryBuilder<T> withDefaultValue(String id, Supplier<T> defaultValueSupplier);

    /**
     * Builds and registers this registry. <br>
     * <strong>Calling {@link Supplier#get() get} too early might result in exceptions.</strong>
     * Prefer using registry keys for referencing the registry.
     *
     * @return a supplier of the registry
     */
    Supplier<Registry<T>> build();
}

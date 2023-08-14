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

package cn.leolezury.eternalstarlight.util.register;

import cn.leolezury.eternalstarlight.util.register.registries.RegistryBuilder;
import cn.leolezury.eternalstarlight.util.register.util.InternalRegUtils;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;

import java.util.Collection;
import java.util.function.Supplier;

/**
 * Utility class for multiloader registration.
 * <p>
 * Example usage:
 * <pre>{@code
 * public static final RegistrationProvider<Test> PROVIDER = RegistrationProvider.get(Test.REGISTRY, "modid");
 * public static final RegistryObject<Test> OBJECT = PROVIDER.register("object", () -> new Test());
 *
 * // The purpose of this method is to be called in the mod's constructor, in order to assure that the class is loaded, and that objects can be registered.
 * public static void loadClass(){}
 * }</pre>
 *
 * @param <T> the type of the objects that this class registers
 */
public interface RegistrationProvider<T> {

    /**
     * Gets a provider for specified {@code modId} and {@code registryKey}. <br>
     * It is <i>recommended</i> to store the resulted provider in a {@code static final} field,
     * so that multiple providers for the same mod ID and registry keys are avoided.
     *
     * @param registryKey the {@link ResourceKey} of the registry of the provider
     * @param modId       the mod id that the provider will register objects for
     * @param <T>         the type of the provider
     * @return the provider
     */
    static <T> RegistrationProvider<T> get(ResourceKey<? extends Registry<T>> registryKey, String modId) {
        return Factory.INSTANCE.create(registryKey, modId);
    }

    /**
     * Gets a provider for specified {@code modId} and {@code registryKey}. <br>
     * It is <i>recommended</i> to store the resulted provider in a {@code static final} field,
     * so that multiple providers for the same mod ID and registry keys are avoided. <br>
     *
     * @param registryId the ID of the registry to create this provider for.
     * @param modId      the mod id that the provider will register objects for
     * @param <T>        the type of the provider
     * @return the provider
     */
    static <T> RegistrationProvider<T> get(ResourceLocation registryId, String modId) {
        return Factory.INSTANCE.create(ResourceKey.createRegistryKey(registryId), modId);
    }

    /**
     * Gets a provider for specified {@code modId} and {@code registry}. <br>
     * It is <i>recommended</i> to store the resulted provider in a {@code static final} field,
     * so that multiple providers for the same mod ID and registry keys are avoided.
     *
     * @param registry the {@link Registry} of the provider
     * @param modId    the mod id that the provider will register objects for
     * @param <T>      the type of the provider
     * @return the provider
     */
    static <T> RegistrationProvider<T> get(Registry<T> registry, String modId) {
        return Factory.INSTANCE.create(registry, modId);
    }

    /**
     * Registers an object.
     *
     * @param name     the name of the object
     * @param supplier a supplier of the object to register
     * @param <I>      the type of the object
     * @return a wrapper containing the lazy registered object. <strong>Calling {@link RegistryObject#get() get} too early
     * on the wrapper might result in crashes!</strong>
     */
    <I extends T> RegistryObject<I> register(String name, Supplier<? extends I> supplier);

    /**
     * Gets all the objects currently registered.
     *
     * @return an <strong>immutable</strong> view of all the objects currently registered
     */
    Collection<RegistryObject<T>> getEntries();

    /**
     * Gets the registry key stored in this provider.
     *
     * @return the registry key stored in this provider
     */
    ResourceKey<? extends Registry<T>> getRegistryKey();

    /**
     * Gets the registry stored in this provider.
     *
     * @return the registry stored in this provider
     */
    Registry<T> getRegistry();

    /**
     * Gets the mod id that this provider registers objects for.
     *
     * @return the mod id
     */
    String getModId();

    /**
     * Creates a builder for creating a registry with this {@link #getRegistryKey() key}. <br>
     * Note: on Fabric this feature requires the Fabric API.
     *
     * @return a builder for the registry
     */
    RegistryBuilder<T> registryBuilder();

    /**
     * Factory class for {@link RegistrationProvider registration providers}. <br>
     * This class is loaded using {@link java.util.ServiceLoader Service Loaders}, and only one
     * should exist per mod loader.
     */
    interface Factory {

        /**
         * The singleton instance of the {@link Factory}. This is different on each loader.
         */
        Factory INSTANCE = InternalRegUtils.getOneAndOnlyService(Factory.class);

        /**
         * Creates a {@link RegistrationProvider}.
         *
         * @param resourceKey the {@link ResourceKey} of the registry to create this provider for
         * @param modId       the mod id for which the provider will register objects
         * @param <T>         the type of the provider
         * @return the provider
         */
        <T> RegistrationProvider<T> create(ResourceKey<? extends Registry<T>> resourceKey, String modId);

        /**
         * Creates a {@link RegistrationProvider}.
         *
         * @param registry the {@link Registry} to create this provider for
         * @param modId    the mod id for which the provider will register objects
         * @param <T>      the type of the provider
         * @return the provider
         */
        default <T> RegistrationProvider<T> create(Registry<T> registry, String modId) {
            return create(registry.key(), modId);
        }
    }
}
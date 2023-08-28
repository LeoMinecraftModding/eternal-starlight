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

import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.ModContainer;
import net.minecraftforge.fml.javafmlmod.FMLModContainer;

import javax.annotation.Nullable;
import java.util.Objects;
import java.util.ServiceLoader;

/**
 * An interface loaded through Service Loaders, for providing the mod event bus for a
 * mod container, which is used by the {@link ForgeRegistrationFactory} for getting the bus
 * that a {@link net.minecraftforge.registries.DeferredRegister} should be registered to.
 */
public interface ForgeBusGetter {

    /**
     * Gets the mod event bus for a container.
     *
     * @param container the container of the mod
     * @return the bus. Can be null
     */
    @Nullable
    IEventBus getModEventBus(ModContainer container);

    @Nullable
    static IEventBus getBus(ModContainer container) {
        return ServiceLoader.load(ForgeBusGetter.class)
                .stream()
                .map(p -> p.get().getModEventBus(container))
                .filter(Objects::nonNull)
                .findFirst()
                .orElseGet(() -> {
                    if (container instanceof FMLModContainer fmlModContainer) {
                        return fmlModContainer.getEventBus();
                    }
                    return null;
                });
    }
}

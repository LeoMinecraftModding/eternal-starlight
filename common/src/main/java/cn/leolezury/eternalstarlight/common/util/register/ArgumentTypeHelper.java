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

package cn.leolezury.eternalstarlight.common.util.register;

import cn.leolezury.eternalstarlight.common.util.register.util.InternalRegUtils;
import com.mojang.brigadier.arguments.ArgumentType;
import net.minecraft.commands.synchronization.ArgumentTypeInfo;

import java.util.function.Supplier;

/**
 * A helper used to register custom {@link ArgumentTypeInfo Argument Types}.
 * <p>
 * Example usage:
 * <pre>{@code
 * public static final RegistrationProvider<ArgumentTypeInfo<?, ?>> ARGUMENT_TYPES = RegistrationProvider.get(BuiltInRegistries.COMMAND_ARGUMENT_TYPE, "modid");
 * public static final RegistryObject<SingletonArgumentInfo<CustomArgument>> CUSTOM_ARGUMENT = ArgumentTypeHelper.INSTANCE.register(ARGUMENT_TYPES, "custom", CustomArgument.class, () -> SingletonArgumentInfo.contextFree(CustomArgument::new));
 *
 * // The purpose of this method is to be called in the mod's constructor, in order to assure that the class is loaded, and that objects can be registered.
 * public static void loadClass(){}
 * }</pre>
 */
public interface ArgumentTypeHelper {

    /**
     * The singleton helper instance. Will be different on each platform.
     */
    ArgumentTypeHelper INSTANCE = InternalRegUtils.getOneAndOnlyService(ArgumentTypeHelper.class);

    /**
     * Registers an {@link ArgumentTypeInfo}.
     *
     * @param provider   the provider to register to
     * @param name       the name of the argument
     * @param clazz      the class of the argument
     * @param serializer the argument serializer
     * @param <A>        the type of the argument
     * @param <T>        the argument template type
     * @param <I>        the argument serializer type
     * @return a wrapper containing the lazy registered object. <strong>Calling get too early on the wrapper might result in crashes!</strong>
     */
    <A extends ArgumentType<?>, T extends ArgumentTypeInfo.Template<A>, I extends ArgumentTypeInfo<A, T>> RegistryObject<I> register(
            RegistrationProvider<ArgumentTypeInfo<?, ?>> provider,
            String name, Class<A> clazz,
            Supplier<I> serializer
    );
}

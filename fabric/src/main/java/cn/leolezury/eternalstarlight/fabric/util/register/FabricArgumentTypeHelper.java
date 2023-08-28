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

import cn.leolezury.eternalstarlight.common.util.register.ArgumentTypeHelper;
import cn.leolezury.eternalstarlight.common.util.register.RegistrationProvider;
import cn.leolezury.eternalstarlight.common.util.register.RegistryObject;
import com.google.auto.service.AutoService;
import com.mojang.brigadier.arguments.ArgumentType;
import net.fabricmc.fabric.api.command.v2.ArgumentTypeRegistry;
import net.minecraft.commands.synchronization.ArgumentTypeInfo;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.ApiStatus;

import java.util.function.Supplier;

@ApiStatus.Internal
@AutoService(ArgumentTypeHelper.class)
public class FabricArgumentTypeHelper implements ArgumentTypeHelper {
    @Override
    @SuppressWarnings({"unchecked", "rawtypes"})
    public <A extends ArgumentType<?>, T extends ArgumentTypeInfo.Template<A>, I extends ArgumentTypeInfo<A, T>> RegistryObject<I> register(RegistrationProvider<ArgumentTypeInfo<?, ?>> provider, String name, Class<A> clazz, Supplier<I> serializer) {
        final ResourceLocation loc = new ResourceLocation(provider.getModId(), name);
        final I ser = serializer.get();
        ArgumentTypeRegistry.registerArgumentType(loc, clazz, ser);

        if (provider instanceof FabricRegistrationFactory.InternalFabricHelper helper) {
            return helper.create(loc, ser);
        }
        return new RegistryObject<>() {
            final ResourceKey<I> key = (ResourceKey<I>) ResourceKey.create(Registries.COMMAND_ARGUMENT_TYPE, loc);
            final Holder<I> holder = (Holder<I>) BuiltInRegistries.COMMAND_ARGUMENT_TYPE.getHolder(ResourceKey.create(Registries.COMMAND_ARGUMENT_TYPE, loc))
                    .orElseThrow();

            @Override
            public ResourceKey<I> getResourceKey() {
                return key;
            }

            @Override
            public ResourceLocation getId() {
                return loc;
            }

            @Override
            public I get() {
                return ser;
            }

            @Override
            public Holder<I> asHolder() {
                return holder;
            }
        };
    }
}

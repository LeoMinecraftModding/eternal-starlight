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

import cn.leolezury.eternalstarlight.util.register.util.InternalRegUtils;
import net.minecraft.network.syncher.EntityDataSerializer;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.resources.ResourceLocation;

import javax.annotation.ParametersAreNonnullByDefault;

/**
 * A helper used to register custom {@link EntityDataSerializer EntityDataSerializers}.
 * <p>
 * Example usage:
 * <pre>{@code
 * public static final EntityDataSerializer<MyType> MY_TYPE_SERIALIZER = EntityDataSerializerHelper.INSTANCE.register(new ResourceLocation("mymod:mytype"), EntityDataSerializer.simple(MyType::write, MyType::read));
 *
 * // The purpose of this method is to be called in the mod's constructor, in order to assure that the class is loaded, and that objects can be registered.
 * public static void loadClass(){}
 * }</pre>
 */
@ParametersAreNonnullByDefault
public interface EntityDataSerializerHelper {

    /**
     * The singleton helper instance. Will be different on each platform.
     */
    EntityDataSerializerHelper INSTANCE = InternalRegUtils.getOneAndOnlyService(EntityDataSerializerHelper.class);

    /**
     * Register a {@link EntityDataSerializer} using {@link net.minecraft.network.syncher.EntityDataSerializers#registerSerializer(EntityDataSerializer)} on Fabric
     * and the Forge registry on Forge.
     *
     * @param key the key of the serializer. This is only used on Forge.
     * @param serializer the serializer to register
     * @param <T> the type of the serializer
     * @return the serializer, to be used in {@link SynchedEntityData#defineId(Class, EntityDataSerializer)}
     */
    <T> EntityDataSerializer<T> register(ResourceLocation key, EntityDataSerializer<T> serializer);
}

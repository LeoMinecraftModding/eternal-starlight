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

import net.minecraft.core.Holder;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;

/**
 * A specialized registry object for {@link Block Blocks}.
 *
 * @param <B> the type of the block
 */
public interface BlockRegistryObject<B extends Block> extends RegistryObject<B>, ItemLike {

    /**
     * Gets the default state of this block.
     *
     * @return the default state of this block
     */
    default BlockState defaultBlockState() {
        return get().defaultBlockState();
    }

    /**
     * Gets the item representation of this block.
     *
     * @return the item representation of this block
     */
    @Override
    default @NotNull Item asItem() {
        return get().asItem();
    }

    /**
     * Wraps a normal registry object into a specialized block one.
     *
     * @param object the object to wrap
     * @param <B>    the type of the block
     * @return the wrapper
     */
    static <B extends Block> BlockRegistryObject<B> wrap(RegistryObject<B> object) {
        return new BlockRegistryObject<>() {
            @Override
            public ResourceKey<B> getResourceKey() {
                return object.getResourceKey();
            }

            @Override
            public ResourceLocation getId() {
                return object.getId();
            }

            @Override
            public B get() {
                return object.get();
            }

            @Override
            public Holder<B> asHolder() {
                return object.asHolder();
            }
        };
    }
}

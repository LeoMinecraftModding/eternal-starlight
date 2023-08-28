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

import java.util.ServiceLoader;

/**
 * This interface will be added by the holder loading transformer to classes that have at least one
 * static {@link RegistrationProvider} field.
 * <br>
 * Loaded using {@link ServiceLoader Service Loaders}, any class implementing this will be instantiated (meaning
 * it <b>needs</b> a public constructor with no parameters that shouldn't throw an exception) at mod init, static
 * loading it, in order to make sure that all objects will get a chance to being registered.
 */
@SuppressWarnings("unused")
public interface RegistryHolder {

    @SuppressWarnings("ALL") // the `getName` call just loads the class
    static void loadAll() {
        ServiceLoader.load(RegistryHolder.class).forEach(clz -> clz.getClass().getName());
    }
}

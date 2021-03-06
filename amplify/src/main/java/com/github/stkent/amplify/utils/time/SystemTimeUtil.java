/*
 * Copyright 2015 Stuart Kent
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License.
 *
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations
 * under the License.
 */

package com.github.stkent.amplify.utils.time;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * SystemTimeUtil class.
 */
public final class SystemTimeUtil {

    @Nullable
    private static ISystemTimeProvider sharedInstance;

    /**
     * get currentTimeMillis .
     */
    public static long currentTimeMillis() {
        synchronized (SystemTimeUtil.class) {
            if (sharedInstance == null) {
                sharedInstance = new RealSystemTimeProvider();
            }
        }

        return sharedInstance.currentTimeMillis();
    }

    public static void setSharedInstance(@NotNull final ISystemTimeProvider systemTimeProvider) {
        sharedInstance = systemTimeProvider;
    }

    private SystemTimeUtil() {
    }

}

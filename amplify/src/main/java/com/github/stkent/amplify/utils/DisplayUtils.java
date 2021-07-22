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

package com.github.stkent.amplify.utils;


import ohos.app.Context;
import ohos.global.configuration.DeviceCapability;
import ohos.global.resource.ResourceManager;
import org.jetbrains.annotations.NotNull;
import static java.lang.Math.max;
import static java.lang.Math.round;

/**
 * DisplayUtils class.
 */
public final class DisplayUtils {

    /**
     * convert dp to px.
     *
     * @param context context.
     *
     * @param dp int dp value.
     */
    public static int dpToPx(@NotNull final Context context, final int dp) {
        final ResourceManager resourceManager = context.getResourceManager();
        final DeviceCapability deviceCapability = resourceManager.getDeviceCapability();
        final float floatResult = dp * ((float) deviceCapability.screenDensity / DeviceCapability.SCREEN_DEFAULT);

        return max(1, round(floatResult));
    }

    private DisplayUtils() {
        // This constructor intentionally left blank.
    }

}

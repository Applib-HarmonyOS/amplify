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

package com.github.stkent.amplify;

import ohos.app.Context;
import ohos.global.configuration.DeviceCapability;
import ohos.system.DeviceInfo;
import org.jetbrains.annotations.NotNull;

/**
 * Device implements IDevice.
 */
public final class Device implements IDevice {

    @NotNull
    private static String getDensityBucketString(@NotNull final DeviceCapability deviceCapability) {
        switch (deviceCapability.screenDensity) {
            case DeviceCapability.SCREEN_SDPI:
                return "sdpi";
            case DeviceCapability.SCREEN_MDPI:
                return "mdpi";
            case DeviceCapability.SCREEN_LDPI:
                return "ldpi";
            case DeviceCapability.SCREEN_XLDPI:
                return "xldpi";
            case DeviceCapability.SCREEN_XXLDPI:
                return "xxldpi";
            case DeviceCapability.SCREEN_XXXLDPI:
                return "xxxldpi";
            default:
                return "Unknown";
        }
    }

    @NotNull
    private final String resolution;

    @NotNull
    private final String actualDensity;

    @NotNull
    private final String densityBucket;

    /**
     * Device constructor.
     *
     * @param context context.
     */
    public Device(@NotNull final Context context) {
        final DeviceCapability deviceCapability = context.getResourceManager().getDeviceCapability();
        resolution = deviceCapability.height + "x" + deviceCapability.width;
        actualDensity = deviceCapability.screenDensity + "dpi";
        densityBucket = getDensityBucketString(deviceCapability);
    }

    @NotNull
    @Override
    public String getManufacturer() {
        return DeviceInfo.getDeviceType();
    }

    @NotNull
    @Override
    public String getModel() {
        return DeviceInfo.getModel();
    }

    @NotNull
    @Override
    public String getResolution() {
        return resolution;
    }

    @NotNull
    @Override
    public String getActualDensity() {
        return actualDensity;
    }

    @NotNull
    public String getDensityBucket() {
        return densityBucket;
    }

}

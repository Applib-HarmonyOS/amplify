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

import ohos.aafwk.content.Intent;
import ohos.rpc.RemoteException;
import org.jetbrains.annotations.NotNull;

/**
 * Interface Environment.
 */
public interface IEnvironment {

    /**
     * Call to check whether an application with a given package name is installed on this device.
     *
     * @param packageName the package name to look for
     * @return true if an application with the given package name is installed on the current device; false otherwise
     */
    boolean isAppInstalled(@NotNull String packageName);

    boolean canHandleIntent(@NotNull Intent intent) throws RemoteException;

    /**
     * Call to check whether the Huawei App Gallery is installed on this device.
     *
     * @return true if the Huawei App Gallery is installed on the current device; false otherwise
     */
    boolean isHuaweiAppGalleryInstalled();
}

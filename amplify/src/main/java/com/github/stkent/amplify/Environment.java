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
import ohos.app.Context;
import ohos.bundle.AbilityInfo;
import ohos.bundle.IBundleManager;
import ohos.rpc.RemoteException;
import org.jetbrains.annotations.NotNull;
import java.util.List;
import static com.github.stkent.amplify.utils.Constants.HUAWEI_APP_GALLERY_PACKAGE_NAME;
import static ohos.bundle.IBundleManager.GET_BUNDLE_WITH_ABILITIES;

/**
 * Environment implements IEnvironment.
 */
public final class Environment implements IEnvironment {
    @NotNull
     private final Context appContext;

    public Environment(@NotNull final Context context) {
        this.appContext = context;
    }


    @SuppressWarnings("PMD.AvoidCatchingGenericException")
    @Override
    public boolean isAppInstalled(@NotNull final String packageName) {
        final IBundleManager iBundleManager = appContext.getBundleManager();

        try {
            return iBundleManager.getBundleInfo(packageName, GET_BUNDLE_WITH_ABILITIES) != null;
        } catch (final Exception ignored) {
            return false;
        }
    }

    @Override
    public boolean isHuaweiAppGalleryInstalled() {

        return isAppInstalled(HUAWEI_APP_GALLERY_PACKAGE_NAME);
    }

    @Override
    public boolean canHandleIntent(@NotNull final Intent intent) throws RemoteException {
        final List<AbilityInfo> abilityInfoList = appContext.getBundleManager().queryAbilityByIntent(intent,
                IBundleManager.GET_BUNDLE_DEFAULT, 0);
        return (abilityInfoList != null && !abilityInfoList.isEmpty());
    }

}

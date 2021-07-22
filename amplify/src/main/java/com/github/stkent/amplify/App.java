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
import ohos.bundle.ApplicationInfo;
import ohos.bundle.BundleInfo;
import ohos.bundle.IBundleManager;
import ohos.rpc.RemoteException;
import org.jetbrains.annotations.NotNull;
import static ohos.bundle.IBundleManager.GET_BUNDLE_DEFAULT;

/**
 * App implements IApp.
 */

public final class App implements IApp {

    @NotNull
    private final String name;

    @NotNull
    private final String versionName;

    private final int versionCode;

    private final long firstInstallTime;

    private final long lastUpdateTime;

    @NotNull
    private final InstallSource installSource;

    /**
     * App constructor.
     *
     * @param context context.
     */
    public App(@NotNull final Context context) {
        final ApplicationInfo applicationInfo = context.getApplicationInfo();
        final BundleInfo bundleInfo = getBundleInfo(context);
        final String installerPackageName;
        installerPackageName  = bundleInfo.name;
        name = applicationInfo.getLabel();
        versionName = bundleInfo.getVersionName();
        versionCode = bundleInfo.getVersionCode();
        firstInstallTime = bundleInfo.getInstallTime();
        lastUpdateTime =  bundleInfo.getUpdateTime();
        installSource = InstallSource.fromInstallerPackageName(installerPackageName);
    }

    @NotNull
    @Override
    public String getName() {
        return name;
    }

    @NotNull
    public String getVersionName() {
        return versionName;
    }

    public int getVersionCode() {
        return versionCode;
    }

    @Override
    public long getInstallTime() {
        return firstInstallTime;
    }

    @Override
    public long getLastUpdateTime() {
        return lastUpdateTime;
    }

    @NotNull
    @Override
    public InstallSource getInstallSource() {
        return installSource;
    }

    @NotNull
    private BundleInfo getBundleInfo(@NotNull final Context context) {
        final IBundleManager iBundleManager = context.getBundleManager();

        try {
            return iBundleManager.getBundleInfo(context.getBundleName(), GET_BUNDLE_DEFAULT);
        } catch (final RemoteException ignored) {
            return new BundleInfo();
        }
    }

}

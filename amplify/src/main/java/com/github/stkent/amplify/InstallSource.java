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


import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import static com.github.stkent.amplify.utils.Constants.HUAWEI_APP_GALLERY_PACKAGE_NAME;

/**
 * interface Install Source.
 */

public interface InstallSource {

    /**
     * static InstallSource fromInstallerPackageName.
     *
     * @param installerPackageName installer package name.
     */
    @NotNull
    static InstallSource fromInstallerPackageName(@Nullable final String installerPackageName) {
        if (HUAWEI_APP_GALLERY_PACKAGE_NAME.equalsIgnoreCase(installerPackageName)) {
            return new HuaweiAppGalleryInstallSource();
        } else if (installerPackageName != null) {
            return new UnrecognizedInstallSource(installerPackageName);
        } else {
            return new UnknownInstallSource();
        }
    }

    /**
     * HuaweiAppGalleryInstallSource implements InstallSource.
     */
    final class HuaweiAppGalleryInstallSource implements InstallSource {
        @Override
        public String toString() {
            return "Huawei App Gallery";
        }
    }

    /**
     * UnknownInstallSource implements InstallSource.
     */
    final class UnknownInstallSource implements InstallSource {
        @Override
        public String toString() {
            return "Unknown";
        }
    }

    /**
     * UnrecognizedInstallSource implements InstallSource.
     */
    final class UnrecognizedInstallSource implements InstallSource {

        @NotNull
        private final String installerPackageName;

        @SuppressWarnings("PMD.CallSuperInConstructor") // Super does nothing.
        private UnrecognizedInstallSource(@NotNull final String installerPackageName) {
            this.installerPackageName = installerPackageName;
        }

        @Override
        public String toString() {
            return installerPackageName;
        }

    }

}

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

import com.github.stkent.amplify.helpers.BaseTest;
import org.junit.Test;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * InstallSourceTest extends BaseTest
 */
public class InstallSourceTest extends BaseTest {


    @Test
    public void testHuaweiAppGalleryInstallSourceIsParsedCorrectly() {
        // Act
        final InstallSource installSource = InstallSource.fromInstallerPackageName("com.huawei.appmarket");

        // Assert
        assertTrue(
                "Package name is correctly identified as belonging to the Huawei App Gallery",
                installSource instanceof InstallSource.HuaweiAppGalleryInstallSource);

        assertEquals(
                "Huawei App Gallery install source has correct description",
                "Huawei App Gallery",
                installSource.toString());
    }

    @Test
    public void testUnrecognizedInstallSourceIsParsedCorrectly() {
        // Arrange
        final String unrecognizedInstallerPackageName = "com.unrecognized";

        // Act
        final InstallSource installSource = InstallSource.fromInstallerPackageName(unrecognizedInstallerPackageName);

        // Assert
        assertTrue(
                "Non-null package name is correctly identified as an unrecognized install source",
                installSource instanceof InstallSource.UnrecognizedInstallSource);

        assertEquals(
                "Unrecognized install source returns raw package name as description",
                unrecognizedInstallerPackageName,
                installSource.toString());
    }


    @Test
    public void testUnknownInstallSourceIsParsedCorrectly() {
        // Act
        final InstallSource installSource = InstallSource.fromInstallerPackageName(null);

        // Assert
        assertTrue(
                "null package name is correctly identified as an unknown install source",
                installSource instanceof InstallSource.UnknownInstallSource);

        assertEquals(
                "Unknown install source has correct description",
                "Unknown",
                installSource.toString());
    }

}

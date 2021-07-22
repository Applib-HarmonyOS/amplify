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

package com.github.stkent.amplify.feedback;

import com.github.stkent.amplify.IApp;
import com.github.stkent.amplify.IDevice;
import com.github.stkent.amplify.IEnvironment;
import org.jetbrains.annotations.NotNull;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

/**
 * DefaultEmailFeedbackCollector extends BaseEmailFeedbackCollector.
 */
public class DefaultEmailFeedbackCollector extends BaseEmailFeedbackCollector {
    public DefaultEmailFeedbackCollector(@NotNull final String... recipients) {
        super(recipients);
    }

    @NotNull
    @Override
    protected String getSubjectLine(
            @NotNull final IApp app,
            @NotNull final IEnvironment environment,
            @NotNull final IDevice device) {

        return app.getName() + " Android App Feedback";
    }

    @NotNull
    @Override
    protected String getBody(
            @NotNull final IApp app,
            @NotNull final IEnvironment environment,
            @NotNull final IDevice device) {

        final String appVersionString = String.format("%s (%s)", app.getVersionName(), app.getVersionCode());

        // @formatter:off
        return    "Time Stamp: " + getCurrentUtcTimeStringForDate(new Date()) + "\n"
                + "App Version: " + appVersionString + "\n"
                + "Install Source: " + app.getInstallSource() + "\n"
                + "Device Manufacturer: " + device.getManufacturer() + "\n"
                + "Device Model: " + device.getModel() + "\n"
                + "Display Resolution: " + device.getResolution() + "\n"
                + "Display Density (Actual): " + device.getActualDensity() + "\n"
                + "Display Density (Bucket) " + device.getDensityBucket() + "\n"
                + "---------------------\n\n";
        // @formatter:on
    }

    @NotNull
    private String getCurrentUtcTimeStringForDate(final Date date) {
        final SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss z", Locale.getDefault());
        simpleDateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
        return simpleDateFormat.format(date);
    }

}

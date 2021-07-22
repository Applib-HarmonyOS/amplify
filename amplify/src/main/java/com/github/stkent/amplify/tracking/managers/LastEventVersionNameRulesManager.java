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

package com.github.stkent.amplify.tracking.managers;

import com.github.stkent.amplify.IApp;
import com.github.stkent.amplify.tracking.interfaces.ISettings;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * LastEventVersionNameRulesManager extends BaseEventsManager.
 */
public final class LastEventVersionNameRulesManager extends BaseEventsManager<String> {

    @NotNull
    private final IApp app;

    public LastEventVersionNameRulesManager(@NotNull final ISettings<String> settings,
                                            @NotNull final IApp app) {
        super(settings);
        this.app = app;
    }

    @NotNull
    @Override
    protected String getTrackedEventDimensionDescription() {
        return "Last version name";
    }

    @NotNull
    @Override
    protected String getEventTrackingStatusStringSuffix(@NotNull final String cachedEventValue) {
        return "last occurred for app version name " + cachedEventValue;
    }

    @NotNull
    @Override
    public String getUpdatedTrackingValue(@Nullable final String cachedTrackingValue) {
        return app.getVersionName();
    }

}

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

package com.github.stkent.amplify.tracking;

import com.github.stkent.amplify.tracking.interfaces.IAppLevelEventRulesManager;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import static java.lang.Thread.UncaughtExceptionHandler;




/**
 * An exception handler used to observe application crashes. Received
 * exceptions are forwarded to the provided default exception handler.
 */
public final class AmplifyExceptionHandler implements Thread.UncaughtExceptionHandler {

    @NotNull
    private final IAppLevelEventRulesManager appLevelEventRulesManager;

    @Nullable
    private final UncaughtExceptionHandler defaultExceptionHandler;

    /**
     * AmplifyExceptionHandler constructor.
     *
     * @param appLevelEventRulesManager appLevelEventRulesManager.
     *
     * @param defaultExceptionHandler defaultExceptionHandler.
     */
    public AmplifyExceptionHandler(
            @NotNull final IAppLevelEventRulesManager appLevelEventRulesManager,
            @Nullable final UncaughtExceptionHandler defaultExceptionHandler) {

        this.appLevelEventRulesManager = appLevelEventRulesManager;
        this.defaultExceptionHandler = defaultExceptionHandler;
    }

    @Override
    public void uncaughtException(final Thread thread, final Throwable throwable) {
        appLevelEventRulesManager.notifyOfCrash();

        // Call the original handler if provided.

        if (defaultExceptionHandler != null) {
            defaultExceptionHandler.uncaughtException(thread, throwable);
        }
    }

}

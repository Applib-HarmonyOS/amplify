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

import ohos.aafwk.ability.Ability;
import ohos.aafwk.content.Intent;
import ohos.rpc.RemoteException;
import ohos.utils.net.Uri;
import com.github.stkent.amplify.IApp;
import com.github.stkent.amplify.IDevice;
import com.github.stkent.amplify.IEnvironment;
import com.github.stkent.amplify.tracking.Amplify;
import org.jetbrains.annotations.NotNull;
import java.util.Arrays;

/**
 * BaseEmailFeedbackCollector implements IFeedbackCollector.
 */
public abstract class BaseEmailFeedbackCollector implements IFeedbackCollector {

    @NotNull
    protected abstract String getSubjectLine(
            @NotNull IApp app,
            @NotNull IEnvironment environment,
            @NotNull IDevice device);

    @NotNull
    protected abstract String getBody(
            @NotNull IApp app,
            @NotNull IEnvironment environment,
            @NotNull IDevice device);

    @NotNull
    private final String[] recipients;

    protected BaseEmailFeedbackCollector(@NotNull final String... recipients) {
        this.recipients = Arrays.copyOf(recipients, recipients.length);
    }

    @Override
    public boolean tryCollectingFeedback(
            @NotNull final Ability currentAbility,
            @NotNull final IApp app,
            @NotNull final IEnvironment environment,
            @NotNull final IDevice device) throws RemoteException {
        final Intent emailIntent = getEmailIntent(app, environment, device);
        if (!environment.canHandleIntent(emailIntent)) {
            Amplify.getLogger().error("Unable to present email client chooser.");
            return false;
        }

        showFeedbackEmailChooser(currentAbility, emailIntent);
        return true;
    }

    @NotNull
    private Intent getEmailIntent(
            @NotNull final IApp app,
            @NotNull final IEnvironment environment,
            @NotNull final IDevice device) {
        Intent result = new Intent();
        Intent.parseUri(String.valueOf(Uri.parse("mailto:")));
        result.setParam("Email", recipients);
        result.setParam("Subject", getSubjectLine(app, environment, device));
        result.setParam("Text", getBody(app, environment, device));
        return result;
    }

    private void showFeedbackEmailChooser(@NotNull final Ability currentActivity, @NotNull final Intent emailIntent) {
        currentActivity.startAbility(emailIntent);
    }

}

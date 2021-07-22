/*
 * Copyright (C) 2020-21 Application Library Engineering Group
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.github.stkent.amplify.feedback;

import ohos.aafwk.ability.Ability;
import ohos.aafwk.content.Intent;
import ohos.aafwk.content.Operation;
import com.github.stkent.amplify.IApp;
import com.github.stkent.amplify.IDevice;
import com.github.stkent.amplify.IEnvironment;
import org.jetbrains.annotations.NotNull;

// Source: https://developer.Huawei.com/public/apis/earn/in-app-purchasing/docs-v2/deep-linking-to-the-Huawei-client
/**
 * HuaweiEmailFeedbackCollector implements IFeedbackCollector.
 */
public final class HuaweiEmailFeedbackCollector implements IFeedbackCollector {
    public static final String EMAIL_BUNDLE_NAME = "com.huawei.email";

    @Override
    public boolean tryCollectingFeedback(
            @NotNull final Ability currentAbility,
            @NotNull final IApp app,
            @NotNull final IEnvironment environment,
            @NotNull final IDevice device) {
        try {
            startEmail(currentAbility);
            return true;
        } catch ( final IllegalArgumentException | IllegalStateException illegalException) {
            return false;
        }
    }

    private void startEmail(Ability ability) {
        Intent intent = new Intent();
        Intent.OperationBuilder operationBuilder = new Intent.OperationBuilder();
        operationBuilder.withBundleName(EMAIL_BUNDLE_NAME);
        operationBuilder.withFlags(Intent.FLAG_ABILITY_NEW_MISSION);
        Operation operation = operationBuilder.build();
        intent.setOperation(operation);
        ability.startAbility(intent);
        ability.setTransitionAnimation(0x010a0000, 0x010a0001);
    }

}

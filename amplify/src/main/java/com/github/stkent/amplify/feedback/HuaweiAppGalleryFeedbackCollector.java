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
import ohos.utils.IntentConstants;
import ohos.utils.net.Uri;
import com.github.stkent.amplify.IApp;
import com.github.stkent.amplify.IDevice;
import com.github.stkent.amplify.IEnvironment;
import com.github.stkent.amplify.utils.StringUtils;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

// Source: https://developer.Huawei.com/public/apis/earn/in-app-purchasing/docs-v2/deep-linking-to-the-Huawei-client
/**
 * HuaweiAppGalleryFeedbackCollector implements IFeedbackCollector.
 */
public final class HuaweiAppGalleryFeedbackCollector implements IFeedbackCollector {
    private static final String HUAWEI_APP_GALLERY_URI_PREFIX = "com.huawei.appmarket";
    private static final String HUAWEI_APP_GALLERY_ACTION = "com.huawei.appmarket.intent.action.AppDetail";
    private static final String HUAWEI_MARKET_URI_PREFIX = "https://appgallery.huawei.com";

    @Nullable
    private final String overridePackageName;

    public HuaweiAppGalleryFeedbackCollector() {
        this.overridePackageName = null;
    }

    public HuaweiAppGalleryFeedbackCollector(@NotNull final String overridePackageName) {
        this.overridePackageName = overridePackageName;
    }

    @Override
    public boolean tryCollectingFeedback(
            @NotNull final Ability currentAbility,
            @NotNull final IApp app,
            @NotNull final IEnvironment environment,
            @NotNull final IDevice device) {

        final String packageName = StringUtils.defaultIfBlank(overridePackageName, currentAbility.getAbilityName());
        try {
            startHuaweiAppGallery(currentAbility, packageName);
            return true;
        } catch (final IllegalArgumentException | IllegalStateException illegalException) {
            try {
                launchMarketUrl(currentAbility);
                return true;
            } catch (final IllegalArgumentException | IllegalStateException illegalException2) {
                return false;
            }
        }
    }

    private void launchMarketUrl(Ability ability) {
        Intent intent = new Intent();
        Intent.OperationBuilder operationBuilder = new Intent.OperationBuilder();
        operationBuilder.withUri(Uri.parse(HUAWEI_MARKET_URI_PREFIX));
        operationBuilder.withAction(IntentConstants.ACTION_SEARCH);
        Operation operation = operationBuilder.build();
        intent.setOperation(operation);
        ability.startAbility(intent);
    }

    private void startHuaweiAppGallery(Ability ability, String packageName) {
        Intent.OperationBuilder operationBuilder = new Intent.OperationBuilder();
        operationBuilder.withBundleName(HUAWEI_APP_GALLERY_URI_PREFIX);
        operationBuilder.withFlags(Intent.FLAG_ABILITY_NEW_MISSION);
        operationBuilder.withAction(HUAWEI_APP_GALLERY_ACTION);
        Operation operation = operationBuilder.build();
        Intent intent = new Intent();
        intent.setParam("APP_PACKAGENAME", packageName);
        intent.setOperation(operation);
        ability.startAbility(intent);
    }
}

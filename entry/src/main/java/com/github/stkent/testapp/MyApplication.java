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

package com.github.stkent.testapp;

import ohos.aafwk.ability.AbilityPackage;
import com.github.stkent.amplify.feedback.HuaweiAppGalleryFeedbackCollector;
import com.github.stkent.amplify.logging.HarmonyLogger;
import com.github.stkent.amplify.tracking.Amplify;

/**
 * MyApplication extends AbilityPackage.
 */
public class MyApplication extends AbilityPackage {
    @Override
    public void onInitialize() {
        super.onInitialize();

        final String releasePackageName = "com.github.stkent.testapp";
        Amplify.setLogger(new HarmonyLogger());

        Amplify.initSharedInstance(this)
                .setPositiveFeedbackCollectors(new HuaweiAppGalleryFeedbackCollector(releasePackageName))
                .setCriticalFeedbackCollectors(new HuaweiAppGalleryFeedbackCollector(releasePackageName))
                .setAlwaysShow(true);
    }
}













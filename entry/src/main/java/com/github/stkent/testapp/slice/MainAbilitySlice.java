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

package com.github.stkent.testapp.slice;

import com.github.stkent.amplify.prompt.BasePromptBuilder;
import com.github.stkent.amplify.prompt.BasePromptViewConfig;
import com.github.stkent.amplify.prompt.CustomLayoutPromptView;
import com.github.stkent.amplify.prompt.CustomLayoutPromptViewConfig;
import com.github.stkent.amplify.prompt.DefaultLayoutPromptView;
import com.github.stkent.amplify.prompt.DefaultLayoutPromptViewConfig;
import ohos.aafwk.ability.AbilitySlice;
import ohos.aafwk.content.Intent;
import ohos.agp.utils.Color;
import ohos.rpc.RemoteException;
import com.github.stkent.amplify.tracking.Amplify;
import com.github.stkent.ResourceTable;

/**
 * MainAbilitySlice extends AbilitySlice.
 */
public class MainAbilitySlice extends AbilitySlice {
    @Override
    public void onStart(Intent intent) {
        super.onStart(intent);
        super.setUIContent(ResourceTable.Layout_ability_main);

        try {
            Amplify.getSharedInstance().promptIfReady(
                   (DefaultLayoutPromptView)
                       findComponentById(ResourceTable.Id_default_layout_prompt_view_no_customization));
        } catch (RemoteException e) {
            e.printStackTrace();
        }


        final DefaultLayoutPromptView defaultLayoutPromptView = (DefaultLayoutPromptView)
                findComponentById(ResourceTable.Id_default_layout_prompt_view_code_config);
        if (defaultLayoutPromptView == null) {
            throw new IllegalStateException("default layout is null");
        }
        final BasePromptViewConfig baseDefaultLayoutPromptViewConfig
                = new BasePromptBuilder()
                .setUserOpinionQuestionTitle("i. User Opinion Title")
                .setUserOpinionQuestionSubtitle("ii. User Opinion Subtitle")
                .setUserOpinionQuestionPositiveButtonLabel("iii. Yes")
                .setUserOpinionQuestionNegativeButtonLabel("iv. No")
                .setPositiveFeedbackQuestionTitle("v. Positive Feedback Title")
                .setPositiveFeedbackQuestionSubtitle("vi. Positive Feedback Subtitle")
                .setPositiveFeedbackQuestionPositiveButtonLabel("vii. Yes")
                .setPositiveFeedbackQuestionNegativeButtonLabel("viii. No")
                .setCriticalFeedbackQuestionTitle("ix. Critical Feedback Title")
                .setCriticalFeedbackQuestionSubtitle("x. Critical Feedback Subtitle")
                .setCriticalFeedbackQuestionPositiveButtonLabel("xi. Yes")
                .setCriticalFeedbackQuestionNegativeButtonLabel("xii. No")
                .setThanksTitle("xiii. Thanks Title")
                .setThanksSubtitle("xiv. Thanks Subtitle")
                .setThanksDisplayTimeMs(2000)
                .build();
        final DefaultLayoutPromptViewConfig defaultLayoutPromptViewConfig
                = new DefaultLayoutPromptViewConfig.Builder()
                .setForegroundColor(Color.getIntColor("#FF0000"))
                .setBackgroundColor(Color.getIntColor("#FF9900"))
                .setTitleTextColor(Color.getIntColor("#000000"))
                .setSubtitleTextColor(Color.getIntColor("#3300FF"))
                .setPositiveButtonTextColor(Color.getIntColor("#FFFFFF"))
                .setPositiveButtonBackgroundColor(Color.getIntColor("#3300FF"))
                .setNegativeButtonTextColor(Color.getIntColor("#000000"))
                .setNegativeButtonBackgroundColor(Color.getIntColor("#FF0000"))
                .setCustomTextSizePx(50)
                .setButtonCornerRadiusPx(90)
                .build();
        defaultLayoutPromptView.applyBaseConfig(baseDefaultLayoutPromptViewConfig);
        defaultLayoutPromptView.applyConfig(defaultLayoutPromptViewConfig);
        try {
            Amplify.getSharedInstance().promptIfReady(defaultLayoutPromptView);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        try {
            Amplify.getSharedInstance().promptIfReady(
                    (DefaultLayoutPromptView) findComponentById(
                            ResourceTable.Id_default_layout_prompt_view_xml_config));
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        final CustomLayoutPromptView customLayoutPromptView
                = (CustomLayoutPromptView) findComponentById(
                ResourceTable.Id_custom_layout_prompt_view_code_config);
        final BasePromptViewConfig baseCustomLayoutPromptViewConfig
                = new BasePromptBuilder()
                .setUserOpinionQuestionTitle("A. User Opinion Title")
                .setUserOpinionQuestionSubtitle("B. User Opinion Subtitle")
                .setUserOpinionQuestionPositiveButtonLabel("C. Yes")
                .setUserOpinionQuestionNegativeButtonLabel("D. No")
                .setPositiveFeedbackQuestionTitle("E. Positive Feedback Title")
                .setPositiveFeedbackQuestionSubtitle("F. Positive Feedback Subtitle")
                .setPositiveFeedbackQuestionPositiveButtonLabel("G. Yes")
                .setPositiveFeedbackQuestionNegativeButtonLabel("H. No")
                .setCriticalFeedbackQuestionTitle("I. Critical Feedback Title")
                .setCriticalFeedbackQuestionSubtitle("J. Critical Feedback Subtitle")
                .setCriticalFeedbackQuestionPositiveButtonLabel("K. Yes")
                .setCriticalFeedbackQuestionNegativeButtonLabel("L. No")
                .setThanksTitle("M. Thanks Title")
                .setThanksSubtitle("N. Thanks Subtitle")
                .setThanksDisplayTimeMs(2000)
                .build();
        final CustomLayoutPromptViewConfig customLayoutPromptViewConfig
                = new CustomLayoutPromptViewConfig(
                ResourceTable.Layout_custom_question_view, ResourceTable.Layout_default_thanks_view);
        customLayoutPromptView.applyBaseConfig(baseCustomLayoutPromptViewConfig);
        customLayoutPromptView.applyConfig(customLayoutPromptViewConfig);
        try {
            Amplify.getSharedInstance().promptIfReady(customLayoutPromptView);
        } catch (RemoteException e) {
            e.printStackTrace();
        }


    }

    @Override
    public void onActive() {
        super.onActive();
    }

    @Override
    public void onForeground(Intent intent) {
        super.onForeground(intent);
    }
}

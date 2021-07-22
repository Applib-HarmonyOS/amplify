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

package com.github.stkent.amplify.prompt;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * public class BasePromptBuilder.
 */
public class BasePromptBuilder {
    @Nullable
    private String userOpinionQuestionTitle;
    @Nullable private String userOpinionQuestionSubtitle;
    @Nullable private String userOpinionQuestionPositiveButtonLabel;
    @Nullable private String userOpinionQuestionNegativeButtonLabel;
    @Nullable private String positiveFeedbackQuestionTitle;
    @Nullable private String positiveFeedbackQuestionSubtitle;
    @Nullable private String positiveFeedbackQuestionPositiveButtonLabel;
    @Nullable private String positiveFeedbackQuestionNegativeButtonLabel;
    @Nullable private String criticalFeedbackQuestionTitle;
    @Nullable private String criticalFeedbackQuestionSubtitle;
    @Nullable private String criticalFeedbackQuestionPositiveButtonLabel;
    @Nullable private String criticalFeedbackQuestionNegativeButtonLabel;
    @Nullable private String thanksTitle;
    @Nullable private String thanksSubtitle;
    @Nullable private Long thanksDisplayTimeMs;

    /**
     * setUserOpinionQuestionTitle BasePromptBuilder class.
     *
     * @param userOpinionQuestionTitle set userOpinionQuestionTitle
     */
    public BasePromptBuilder setUserOpinionQuestionTitle(
            @NotNull final String userOpinionQuestionTitle) {

        this.userOpinionQuestionTitle = userOpinionQuestionTitle;
        return this;
    }

    /**
     * setUserOpinionQuestionsubitle BasePromptBuilder class.
     *
     * @param userOpinionQuestionSubtitle set userOpinionQuestionSubtitle
     */
    public BasePromptBuilder setUserOpinionQuestionSubtitle(
            @NotNull final String userOpinionQuestionSubtitle) {

        this.userOpinionQuestionSubtitle = userOpinionQuestionSubtitle;
        return this;
    }

    /**
     * setUserOpinionQuestionPositiveButtonLabel BasePromptBuilder class.
     *
     * @param userOpinionQuestionPositiveButtonLabel set userOpinionQuestionPositiveButtonLabel
     */
    public BasePromptBuilder setUserOpinionQuestionPositiveButtonLabel(
            @NotNull final String userOpinionQuestionPositiveButtonLabel) {

        this.userOpinionQuestionPositiveButtonLabel = userOpinionQuestionPositiveButtonLabel;
        return this;
    }

    /**
     * setUserOpinionQuestionNegativeButtonLabel BasePromptBuilder class.
     *
     * @param userOpinionQuestionNegativeButtonLabel set userOpinionQuestionNegativeButtonLabel
     */
    public BasePromptBuilder setUserOpinionQuestionNegativeButtonLabel(
            @NotNull final String userOpinionQuestionNegativeButtonLabel) {

        this.userOpinionQuestionNegativeButtonLabel = userOpinionQuestionNegativeButtonLabel;
        return this;
    }

    /**
     * setPositiveFeedbackQuestionTitle BasePromptBuilder class.
     *
     * @param positiveFeedbackQuestionTitle set positiveFeedbackQuestionTitle
     */
    public BasePromptBuilder setPositiveFeedbackQuestionTitle(
            @NotNull final String positiveFeedbackQuestionTitle) {

        this.positiveFeedbackQuestionTitle = positiveFeedbackQuestionTitle;
        return this;
    }

    /**
     * setPositiveFeedbackQuestionSubtitle BasePromptBuilder class.
     *
     * @param positiveFeedbackQuestionSubtitle set positiveFeedbackQuestionSubtitle
     */
    public BasePromptBuilder setPositiveFeedbackQuestionSubtitle(
            @NotNull final String positiveFeedbackQuestionSubtitle) {

        this.positiveFeedbackQuestionSubtitle = positiveFeedbackQuestionSubtitle;
        return this;
    }

    /**
     * setPositiveFeedbackQuestionPositiveButtonLabel BasePromptBuilder class.
     *
     * @param positiveFeedbackQuestionPositiveButtonLabel set positiveFeedbackQuestionPositiveButtonLabel
     */
    public BasePromptBuilder setPositiveFeedbackQuestionPositiveButtonLabel(
            @NotNull final String positiveFeedbackQuestionPositiveButtonLabel) {

        this.positiveFeedbackQuestionPositiveButtonLabel
                = positiveFeedbackQuestionPositiveButtonLabel;

        return this;
    }

    /**
     * setPositiveFeedbackQuestionNegativeButtonLabel BasePromptBuilder class.
     *
     * @param positiveFeedbackQuestionNegativeButtonLabel set positiveFeedbackQuestionNegativeButtonLabel
     */
    public BasePromptBuilder setPositiveFeedbackQuestionNegativeButtonLabel(
            @NotNull final String positiveFeedbackQuestionNegativeButtonLabel) {

        this.positiveFeedbackQuestionNegativeButtonLabel
                = positiveFeedbackQuestionNegativeButtonLabel;

        return this;
    }

    /**
     * setCriticalFeedbackQuestionTitle BasePromptBuilder class.
     *
     * @param criticalFeedbackQuestionTitle set criticalFeedbackQuestionTitle
     */
    public BasePromptBuilder setCriticalFeedbackQuestionTitle(
            @NotNull final String criticalFeedbackQuestionTitle) {

        this.criticalFeedbackQuestionTitle = criticalFeedbackQuestionTitle;
        return this;
    }

    /**
     * setCriticalFeedbackQuestionSubtitle BasePromptBuilder class.
     *
     * @param criticalFeedbackQuestionSubtitle set criticalFeedbackQuestionSubtitle
     */
    public BasePromptBuilder setCriticalFeedbackQuestionSubtitle(
            @NotNull final String criticalFeedbackQuestionSubtitle) {

        this.criticalFeedbackQuestionSubtitle = criticalFeedbackQuestionSubtitle;
        return this;
    }

    /**
     * setCriticalFeedbackQuestionPositiveButtonLabel BasePromptBuilder class.
     *
     * @param criticalFeedbackQuestionPositiveButtonLabel set criticalFeedbackQuestionPositiveButtonLabel
     */
    public BasePromptBuilder setCriticalFeedbackQuestionPositiveButtonLabel(
            @NotNull final String criticalFeedbackQuestionPositiveButtonLabel) {

        this.criticalFeedbackQuestionPositiveButtonLabel
                = criticalFeedbackQuestionPositiveButtonLabel;

        return this;
    }

    /**
     * setCriticalFeedbackQuestionNegativeButtonLabel BasePromptBuilder class.
     *
     * @param criticalFeedbackQuestionNegativeButtonLabel set criticalFeedbackQuestionNegativeButtonLabel
     */
    public BasePromptBuilder setCriticalFeedbackQuestionNegativeButtonLabel(
            @NotNull final String criticalFeedbackQuestionNegativeButtonLabel) {

        this.criticalFeedbackQuestionNegativeButtonLabel
                = criticalFeedbackQuestionNegativeButtonLabel;

        return this;
    }

    /**
     * setThanksTitle BasePromptBuilder class.
     *
     * @param thanksTitle set thanksTitle
     */
    public BasePromptBuilder setThanksTitle(@NotNull final String thanksTitle) {
        this.thanksTitle = thanksTitle;
        return this;
    }

    /**
     * setThanksSubtitle BasePromptBuilder class.
     *
     * @param thanksSubtitle set thanksSubtitle
     */
    public BasePromptBuilder setThanksSubtitle(@NotNull final String thanksSubtitle) {
        this.thanksSubtitle = thanksSubtitle;
        return this;
    }

    /**
     * setThanksDisplayTimeMs BasePromptBuilder class.
     *
     * @param thanksDisplayTimeMs set thanksDisplayTimeMs
     */
    public BasePromptBuilder setThanksDisplayTimeMs(final int thanksDisplayTimeMs) {
        this.thanksDisplayTimeMs = (long) thanksDisplayTimeMs;
        return this;
    }

    /**
     * BasePromptViewConfig build function.
     */
    public BasePromptViewConfig build() {
        BasePromptViewConfig basePromptViewConfig = new BasePromptViewConfig();
        basePromptViewConfig.setUserOpinion(
                userOpinionQuestionTitle,
                userOpinionQuestionSubtitle,
                userOpinionQuestionPositiveButtonLabel,
                userOpinionQuestionNegativeButtonLabel);
        basePromptViewConfig.setPositiveFeedback(
                positiveFeedbackQuestionTitle,
                positiveFeedbackQuestionSubtitle,
                positiveFeedbackQuestionPositiveButtonLabel,
                positiveFeedbackQuestionNegativeButtonLabel);
        basePromptViewConfig.setCriticalFeedback(
                criticalFeedbackQuestionTitle,
                criticalFeedbackQuestionSubtitle,
                criticalFeedbackQuestionPositiveButtonLabel,
                criticalFeedbackQuestionNegativeButtonLabel);
        basePromptViewConfig.setThanksPrompt(
                thanksTitle,
                thanksSubtitle,
                thanksDisplayTimeMs);

        return basePromptViewConfig;
    }
}
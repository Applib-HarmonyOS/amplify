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

package com.github.stkent.amplify.prompt;


import ohos.agp.components.Attr;
import ohos.agp.components.AttrSet;
import ohos.utils.Parcel;
import ohos.utils.Sequenceable;
import com.github.stkent.amplify.prompt.interfaces.IQuestion;
import com.github.stkent.amplify.prompt.interfaces.IThanks;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import java.util.Optional;
import static com.github.stkent.amplify.utils.StringUtils.defaultIfBlank;

/**
 * BasePromptViewConfig implements Sequenceable.
 */
@SuppressWarnings({"PMD.ExcessiveParameterList", "checkstyle:parameternumber"})
public final class BasePromptViewConfig implements Sequenceable {

    private static final String DEFAULT_USER_OPINION_QUESTION_TITLE
            = "Enjoying the app?";
    private static final String DEFAULT_POSITIVE_FEEDBACK_QUESTION_TITLE
            = "Awesome! We'd love an AppGallery review";
    private static final String DEFAULT_CRITICAL_FEEDBACK_QUESTION_TITLE
            = "Oh no! Would you give us some feedback?";
    private static final String DEFAULT_USER_OPINION_QUESTION_POSITIVE_BUTTON_LABEL
            = "Yes!";
    private static final String DEFAULT_USER_OPINION_QUESTION_NEGATIVE_BUTTON_LABEL
            = "No";
    private static final String DEFAULT_FEEDBACK_QUESTION_POSITIVE_BUTTON_LABEL
            = "Sure thing!";
    private static final String DEFAULT_FEEDBACK_QUESTION_NEGATIVE_BUTTON_LABEL
            = "Not right now";
    private static final String DEFAULT_THANKS_TITLE
            = "Thanks for your feedback!";

    /**
     * BasePromptViewConfig constructor.
     */
    protected BasePromptViewConfig() {
    }

    @Nullable
    private static Long suppliedLongOrNull(@Nullable  AttrSet attrs, final String index) {

        if (attrs != null) {
            Optional<Attr> optionalAttr = attrs.getAttr(index);
            if (optionalAttr.isPresent()) {
                return optionalAttr.get().getLongValue();
            }
        }
        return null;
    }

    @Nullable private String userOpinionQuestionTitle;
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
    @Nullable private Long   thanksDisplayTimeMs;

    /**
     * BasePromptViewConfig constructor.
     *
     * @param attrSet attributes set.
     */
    public BasePromptViewConfig(@NotNull AttrSet attrSet) {
        setUserOpinionAttr(attrSet);
        setPositiveFeedbackAttr(attrSet);
        setCriticalFeedbackAttr(attrSet);
        setThanksPromptAttr(attrSet);
    }

    protected void setUserOpinionAttr(@NotNull AttrSet attrSet) {
        Optional<Attr> optionalAttr;
        optionalAttr = attrSet.getAttr("prompt_view_user_opinion_question_title");
        if (optionalAttr.isPresent()) {
            userOpinionQuestionTitle = optionalAttr.get().getStringValue();
        } else {
            userOpinionQuestionTitle = DEFAULT_USER_OPINION_QUESTION_TITLE;
        }

        optionalAttr = attrSet.getAttr("prompt_view_user_opinion_question_subtitle");
        if (optionalAttr.isPresent()) {
            userOpinionQuestionSubtitle = optionalAttr.get().getStringValue();
        } else {
            userOpinionQuestionSubtitle = "";
        }

        optionalAttr = attrSet.getAttr("prompt_view_user_opinion_question_positive_button_label");
        if (optionalAttr.isPresent()) {
            userOpinionQuestionPositiveButtonLabel = optionalAttr.get().getStringValue();
        } else {
            userOpinionQuestionPositiveButtonLabel = DEFAULT_USER_OPINION_QUESTION_POSITIVE_BUTTON_LABEL;
        }

        optionalAttr = attrSet.getAttr("prompt_view_user_opinion_question_negative_button_label");
        if (optionalAttr.isPresent()) {
            userOpinionQuestionNegativeButtonLabel = optionalAttr.get().getStringValue();
        } else {
            userOpinionQuestionNegativeButtonLabel = DEFAULT_USER_OPINION_QUESTION_NEGATIVE_BUTTON_LABEL;
        }
    }

    protected void setPositiveFeedbackAttr(@NotNull AttrSet attrSet) {
        Optional<Attr> optionalAttr;

        optionalAttr = attrSet.getAttr("prompt_view_positive_feedback_question_title");
        if (optionalAttr.isPresent()) {
            positiveFeedbackQuestionTitle = optionalAttr.get().getStringValue();
        } else {
            positiveFeedbackQuestionTitle = DEFAULT_POSITIVE_FEEDBACK_QUESTION_TITLE;
        }

        optionalAttr = attrSet.getAttr("prompt_view_positive_feedback_question_subtitle");
        if (optionalAttr.isPresent()) {
            positiveFeedbackQuestionSubtitle = optionalAttr.get().getStringValue();
        } else {
            positiveFeedbackQuestionSubtitle = "";
        }
        optionalAttr = attrSet.getAttr("prompt_view_positive_feedback_question_positive_button_label");
        if (optionalAttr.isPresent()) {
            positiveFeedbackQuestionPositiveButtonLabel = optionalAttr.get().getStringValue();
        } else {
            positiveFeedbackQuestionPositiveButtonLabel = DEFAULT_FEEDBACK_QUESTION_POSITIVE_BUTTON_LABEL;
        }

        optionalAttr = attrSet.getAttr("prompt_view_positive_feedback_question_negative_button_label");
        if (optionalAttr.isPresent()) {
            positiveFeedbackQuestionNegativeButtonLabel = optionalAttr.get().getStringValue();
        } else {
            positiveFeedbackQuestionNegativeButtonLabel = DEFAULT_FEEDBACK_QUESTION_NEGATIVE_BUTTON_LABEL;
        }
    }

    protected void setCriticalFeedbackAttr(@NotNull AttrSet attrSet) {
        Optional<Attr> optionalAttr;

        optionalAttr = attrSet.getAttr("prompt_view_critical_feedback_question_title");
        if (optionalAttr.isPresent()) {
            criticalFeedbackQuestionTitle = optionalAttr.get().getStringValue();
        } else {
            criticalFeedbackQuestionTitle = DEFAULT_CRITICAL_FEEDBACK_QUESTION_TITLE;
        }

        optionalAttr = attrSet.getAttr("prompt_view_critical_feedback_question_subtitle");
        if (optionalAttr.isPresent()) {
            criticalFeedbackQuestionSubtitle = optionalAttr.get().getStringValue();
        } else {
            criticalFeedbackQuestionSubtitle = "";
        }

        optionalAttr = attrSet.getAttr("prompt_view_critical_feedback_question_positive_button_label");
        if (optionalAttr.isPresent()) {
            criticalFeedbackQuestionPositiveButtonLabel = optionalAttr.get().getStringValue();
        } else {
            criticalFeedbackQuestionPositiveButtonLabel = DEFAULT_FEEDBACK_QUESTION_POSITIVE_BUTTON_LABEL;
        }

        optionalAttr = attrSet.getAttr("prompt_view_critical_feedback_question_negative_button_label");
        if (optionalAttr.isPresent()) {
            criticalFeedbackQuestionNegativeButtonLabel = optionalAttr.get().getStringValue();
        } else {
            criticalFeedbackQuestionNegativeButtonLabel = DEFAULT_FEEDBACK_QUESTION_NEGATIVE_BUTTON_LABEL;
        }
    }

    protected void setThanksPromptAttr(@NotNull AttrSet attrSet) {
        Optional<Attr> optionalAttr;

        optionalAttr = attrSet.getAttr("prompt_view_thanks_title");
        if (optionalAttr.isPresent()) {
            thanksTitle = optionalAttr.get().getStringValue();
        } else {
            thanksTitle = DEFAULT_THANKS_TITLE;
        }

        optionalAttr = attrSet.getAttr("prompt_view_thanks_subtitle");
        if (optionalAttr.isPresent()) {
            thanksSubtitle = optionalAttr.get().getStringValue();
        } else {
            thanksSubtitle = "";
        }
        thanksDisplayTimeMs = suppliedLongOrNull(attrSet, "prompt_view_thanks_display_time_ms");
    }

    protected void setPositiveFeedback(
            @Nullable final String positiveFeedbackQuestionTitle,
            @Nullable final String positiveFeedbackQuestionSubtitle,
            @Nullable final String positiveFeedbackQuestionPositiveButtonLabel,
            @Nullable final String positiveFeedbackQuestionNegativeButtonLabel) {
        this.positiveFeedbackQuestionTitle = positiveFeedbackQuestionTitle;
        this.positiveFeedbackQuestionSubtitle = positiveFeedbackQuestionSubtitle;
        this.positiveFeedbackQuestionPositiveButtonLabel = positiveFeedbackQuestionPositiveButtonLabel;
        this.positiveFeedbackQuestionNegativeButtonLabel = positiveFeedbackQuestionNegativeButtonLabel;
    }


    protected void setCriticalFeedback(
            @Nullable final String criticalFeedbackQuestionTitle,
            @Nullable final String criticalFeedbackQuestionSubtitle,
            @Nullable final String criticalFeedbackQuestionPositiveButtonLabel,
            @Nullable final String criticalFeedbackQuestionNegativeButtonLabel) {
        this.criticalFeedbackQuestionTitle = criticalFeedbackQuestionTitle;
        this.criticalFeedbackQuestionSubtitle = criticalFeedbackQuestionSubtitle;
        this.criticalFeedbackQuestionPositiveButtonLabel = criticalFeedbackQuestionPositiveButtonLabel;
        this.criticalFeedbackQuestionNegativeButtonLabel = criticalFeedbackQuestionNegativeButtonLabel;
    }

    protected void setUserOpinion(
            @Nullable final String userOpinionQuestionTitle,
            @Nullable final String userOpinionQuestionSubtitle,
            @Nullable final String userOpinionQuestionPositiveButtonLabel,
            @Nullable final String userOpinionQuestionNegativeButtonLabel) {
        this.userOpinionQuestionTitle                    = userOpinionQuestionTitle;
        this.userOpinionQuestionSubtitle                 = userOpinionQuestionSubtitle;
        this.userOpinionQuestionPositiveButtonLabel      = userOpinionQuestionPositiveButtonLabel;
        this.userOpinionQuestionNegativeButtonLabel      = userOpinionQuestionNegativeButtonLabel;
    }

    protected void setThanksPrompt(
            @Nullable final String thanksTitle,
            @Nullable final String thanksSubtitle,
            @Nullable final Long thanksDisplayTimeMs) {
        this.thanksTitle                                 = thanksTitle;
        this.thanksSubtitle                              = thanksSubtitle;
        this.thanksDisplayTimeMs                         = thanksDisplayTimeMs;
    }

    /**
     * getUserOpinionQuestion returns Question object.
     */
    @NotNull
    public IQuestion getUserOpinionQuestion() {
        return new Question(
                getUserOpinionQuestionTitle(),
                getUserOpinionQuestionSubtitle(),
                getUserOpinionQuestionPositiveButtonLabel(),
                getUserOpinionQuestionNegativeButtonLabel());
    }

    /**
     * getPositiveFeedbackQuestion returns Question object.
     */

    @NotNull
    public IQuestion getPositiveFeedbackQuestion() {
        return new Question(
                getPositiveFeedbackQuestionTitle(),
                positiveFeedbackQuestionSubtitle,
                getPositiveFeedbackQuestionPositiveButtonLabel(),
                getPositiveFeedbackQuestionNegativeButtonLabel());
    }

    /**
     * getCriticalFeedbackQuestion returns Question object.
     */
    @NotNull
    public IQuestion getCriticalFeedbackQuestion() {
        return new Question(
                getCriticalFeedbackQuestionTitle(),
                criticalFeedbackQuestionSubtitle,
                getCriticalFeedbackQuestionPositiveButtonLabel(),
                getCriticalFeedbackQuestionNegativeButtonLabel());
    }

    @NotNull
    public IThanks getThanks() {
        return new Thanks(getThanksTitle(), thanksSubtitle);
    }

    @Nullable
    public Long getThanksDisplayTimeMs() {
        return thanksDisplayTimeMs;
    }

    @NotNull
    private String getUserOpinionQuestionTitle() {
        return defaultIfBlank(
                userOpinionQuestionTitle, DEFAULT_USER_OPINION_QUESTION_TITLE);
    }

    @NotNull
    private String getUserOpinionQuestionSubtitle() {
        return defaultIfBlank(
                userOpinionQuestionSubtitle, "");
    }

    @NotNull
    private String getUserOpinionQuestionPositiveButtonLabel() {
        return defaultIfBlank(
                userOpinionQuestionPositiveButtonLabel,
                DEFAULT_USER_OPINION_QUESTION_POSITIVE_BUTTON_LABEL);
    }

    @NotNull
    private String getUserOpinionQuestionNegativeButtonLabel() {
        return defaultIfBlank(
                userOpinionQuestionNegativeButtonLabel,
                DEFAULT_USER_OPINION_QUESTION_NEGATIVE_BUTTON_LABEL);
    }

    @NotNull
    private String getPositiveFeedbackQuestionTitle() {
        return defaultIfBlank(
                positiveFeedbackQuestionTitle, DEFAULT_POSITIVE_FEEDBACK_QUESTION_TITLE);
    }

    @NotNull
    private String getPositiveFeedbackQuestionPositiveButtonLabel() {
        return defaultIfBlank(
                positiveFeedbackQuestionPositiveButtonLabel,
                DEFAULT_FEEDBACK_QUESTION_POSITIVE_BUTTON_LABEL);
    }

    @NotNull
    private String getPositiveFeedbackQuestionNegativeButtonLabel() {
        return defaultIfBlank(
                positiveFeedbackQuestionNegativeButtonLabel,
                DEFAULT_FEEDBACK_QUESTION_NEGATIVE_BUTTON_LABEL);
    }

    @NotNull
    private String getCriticalFeedbackQuestionTitle() {
        return defaultIfBlank(
                criticalFeedbackQuestionTitle, DEFAULT_CRITICAL_FEEDBACK_QUESTION_TITLE);
    }

    @NotNull
    private String getCriticalFeedbackQuestionPositiveButtonLabel() {
        return defaultIfBlank(
                criticalFeedbackQuestionPositiveButtonLabel,
                DEFAULT_FEEDBACK_QUESTION_POSITIVE_BUTTON_LABEL);
    }

    @NotNull
    private String getCriticalFeedbackQuestionNegativeButtonLabel() {
        return defaultIfBlank(
                criticalFeedbackQuestionNegativeButtonLabel,
                DEFAULT_FEEDBACK_QUESTION_NEGATIVE_BUTTON_LABEL);
    }

    @NotNull
    private String getThanksTitle() {
        return defaultIfBlank(thanksTitle, DEFAULT_THANKS_TITLE);
    }

    /**
     * marshalling return true.
     */
    @Override
    public boolean marshalling(Parcel dest) {
        return true;
    }

    /**
     * unmarshalling return false.
     */
    @Override
    public boolean unmarshalling(Parcel parcel) {
        return false;
    }



}

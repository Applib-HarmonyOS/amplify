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

import ohos.agp.components.Component;
import ohos.agp.components.StackLayout;
import ohos.agp.components.Text;
import ohos.agp.render.layoutboost.LayoutBoost;
import ohos.app.Context;
import ohos.rpc.RemoteException;
import com.github.stkent.ResourceTable;
import com.github.stkent.amplify.prompt.interfaces.IQuestion;
import com.github.stkent.amplify.prompt.interfaces.IQuestionPresenter;
import com.github.stkent.amplify.prompt.interfaces.IQuestionView;
import com.github.stkent.amplify.utils.Constants;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@SuppressWarnings("ViewConstructor")
class CustomLayoutQuestionView extends StackLayout implements IQuestionView {
    private static final String QUESTION_PRESENTER_MUST_BE_SET_EXCEPTION_MESSAGE
            = "Question presenter must be set before buttons can be clicked.";

    @NotNull
    private final Text titleTextView;

    @Nullable
    private final Text subtitleTextView;

    @NotNull
    private final Component positiveButton;

    @NotNull
    private final Component negativeButton;

    private IQuestionPresenter questionPresenter;

    CustomLayoutQuestionView(final Context context, final int layoutRes) {

        super(context);
        LayoutBoost.inflate(context, layoutRes, this, true);
        final Text titleTextViewComp = (Text) findComponentById(ResourceTable.Id_amplify_title_text_view);
        final Component positiveButtonComp =  findComponentById(ResourceTable.Id_amplify_positive_button);
        final Component negativeButtonComp =  findComponentById(ResourceTable.Id_amplify_negative_button);
        if (positiveButtonComp == null) {
            throw new IllegalStateException(Constants.MISSING_LAYOUT_IDS_EXCEPTION_MESSAGE + " positive ");
        }
        if (titleTextViewComp == null) {
            throw new IllegalStateException(Constants.MISSING_LAYOUT_IDS_EXCEPTION_MESSAGE + " titleText ");
        }
        if (negativeButtonComp == null) {
            throw new IllegalStateException(Constants.MISSING_LAYOUT_IDS_EXCEPTION_MESSAGE + " negative ");
        }

        this.titleTextView = titleTextViewComp;
        this.subtitleTextView = (Text) findComponentById(ResourceTable.Id_amplify_subtitle_text_view);
        this.positiveButton = positiveButtonComp;
        this.negativeButton = negativeButtonComp;

        positiveButton.setClickedListener(component -> {
            if (questionPresenter == null) {
                throw new IllegalStateException(
                        QUESTION_PRESENTER_MUST_BE_SET_EXCEPTION_MESSAGE);
            }

            try {
                questionPresenter.userRespondedPositively();
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        });
        negativeButton.setClickedListener(component -> {
            if (questionPresenter == null) {
                throw new IllegalStateException(
                        QUESTION_PRESENTER_MUST_BE_SET_EXCEPTION_MESSAGE);
            }

            try {
                questionPresenter.userRespondedNegatively();
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        });

    }

    @Override
    public void setPresenter(@NotNull final IQuestionPresenter questionPresenter) {
        this.questionPresenter = questionPresenter;
    }

    @Override
    public void bind(@NotNull final IQuestion question) {
        titleTextView.setText(question.getTitle());

        setQuoteButtonUnquoteText(positiveButton, question.getPositiveButtonLabel());
        setQuoteButtonUnquoteText(negativeButton, question.getNegativeButtonLabel());

        final String subtitle = question.getSubTitle();

        if (subtitleTextView != null) {
            if (subtitle != null) {
                subtitleTextView.setText(subtitle);
                subtitleTextView.setVisibility(VISIBLE);
            } else {
                subtitleTextView.setVisibility(HIDE);
            }
        }
    }

    @NotNull
    protected Text getTitleTextView() {
        return titleTextView;
    }

    @Nullable
    protected Text getSubtitleTextView() {
        return subtitleTextView;
    }

    @NotNull
    protected Component getPositiveButton() {
        return positiveButton;
    }

    @NotNull
    protected Component getNegativeButton() {
        return negativeButton;
    }

    /**
     * We are defensive here, because it's not uncommon to make "buttons" out of UI components like
     * FrameLayouts, say. If we can't cast to a TextView to obtain a setText method, the button text
     * will be left unchanged.
     *
     * @param quoteButtonUnquote the "button" whose text we wish to set
     *
     * @param text the text we wish to apply
     */

    private void setQuoteButtonUnquoteText(
            final @NotNull Component quoteButtonUnquote,
            @NotNull final String text) {

        if (quoteButtonUnquote instanceof Text) {
            ((Text) quoteButtonUnquote).setText(text);
        }
    }

}

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

import ohos.agp.components.AttrSet;
import ohos.agp.components.Component;
import ohos.agp.components.ComponentContainer;
import ohos.agp.components.StackLayout;
import ohos.app.Context;
import ohos.rpc.RemoteException;
import com.github.stkent.amplify.prompt.interfaces.IPromptPresenter;
import com.github.stkent.amplify.prompt.interfaces.IPromptView;
import com.github.stkent.amplify.prompt.interfaces.IQuestionPresenter;
import com.github.stkent.amplify.prompt.interfaces.IQuestionView;
import com.github.stkent.amplify.prompt.interfaces.IThanksView;
import com.github.stkent.amplify.tracking.Amplify;
import com.github.stkent.amplify.tracking.PromptViewEvent;
import com.github.stkent.amplify.tracking.interfaces.IEventListener;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import static com.github.stkent.amplify.prompt.interfaces.IPromptPresenter.UserFeedbackAction.AGREED;
import static com.github.stkent.amplify.prompt.interfaces.IPromptPresenter.UserFeedbackAction.DECLINED;
import static com.github.stkent.amplify.prompt.interfaces.IPromptPresenter.UserOpinion.CRITICAL;
import static com.github.stkent.amplify.prompt.interfaces.IPromptPresenter.UserOpinion.POSITIVE;

@SuppressWarnings({"PMD.TooManyMethods"})
abstract class BasePromptView<T extends Component & IQuestionView, U extends Component & IThanksView>
        extends StackLayout implements IPromptView {

    protected abstract boolean isConfigured();

    @NotNull
    protected abstract T getQuestionView();

    @Nullable
    protected abstract U getThanksView();

    private final IQuestionPresenter userOpinionQuestionPresenter =
            new IQuestionPresenter() {
                @Override
                public void userRespondedPositively() throws RemoteException {
                    promptPresenter.reportUserOpinion(POSITIVE);
                }

                @Override
                public void userRespondedNegatively() throws RemoteException {
                    promptPresenter.reportUserOpinion(CRITICAL);
                }
            };

    private final IQuestionPresenter feedbackQuestionPresenter =
            new IQuestionPresenter() {
                @Override
                public void userRespondedPositively() throws RemoteException {
                    promptPresenter.reportUserFeedbackAction(AGREED);
                }

                @Override
                public void userRespondedNegatively() throws RemoteException {
                    promptPresenter.reportUserFeedbackAction(DECLINED);
                }
            };
    private final IPromptPresenter promptPresenter;
    private BasePromptViewConfig basePromptViewConfig;
    private T displayedQuestionView;
    private boolean thanksDisplayTimeExpired;

    BasePromptView(final Context context) {
        this(context, null);
    }

    BasePromptView(final Context context, @Nullable final AttrSet attributeSet) {
        this(context, attributeSet, 0);
    }

    BasePromptView(final Context context, @Nullable final AttrSet attrSet, final int defStyleAttr) {
        super(context, attrSet, String.valueOf(defStyleAttr));
        setLayoutConfig(new ComponentContainer.LayoutConfig(
                ComponentContainer.LayoutConfig.MATCH_PARENT, ComponentContainer.LayoutConfig.MATCH_CONTENT));
        initializeBaseConfig(attrSet);
        promptPresenter = new PromptPresenter(Amplify.getSharedInstance(), this);
    }

    @NotNull
    @Override
    public final IPromptPresenter getPresenter() {
        return promptPresenter;
    }

    @Override
    public final void queryUserOpinion(final boolean triggeredByConfigChange) throws RemoteException {
        if (!isConfigured()) {
            throw new IllegalStateException("PromptView is not fully configured.");
        }

        if (!triggeredByConfigChange) {
            promptPresenter.notifyEventTriggered(PromptViewEvent.PROMPT_SHOWN);
        }

        displayQuestionViewIfNeeded();
        displayedQuestionView.setPresenter(userOpinionQuestionPresenter);
        displayedQuestionView.bind(basePromptViewConfig.getUserOpinionQuestion());

    }

    @Override
    public final void requestPositiveFeedback() {
        displayQuestionViewIfNeeded();
        displayedQuestionView.setPresenter(feedbackQuestionPresenter);
        displayedQuestionView.bind(basePromptViewConfig.getPositiveFeedbackQuestion());
    }

    @Override
    public final void requestCriticalFeedback() {
        displayQuestionViewIfNeeded();
        displayedQuestionView.setPresenter(feedbackQuestionPresenter);
        displayedQuestionView.bind(basePromptViewConfig.getCriticalFeedbackQuestion());
    }

    @SuppressWarnings("ConstantConditions")
    @Override
    public final void thankUser(final boolean triggeredByConfigChange) throws RemoteException {
        if (!triggeredByConfigChange) {
            promptPresenter.notifyEventTriggered(PromptViewEvent.THANKS_SHOWN);
        }
        clearDisplayedQuestionViewReference();
        if (!thanksDisplayTimeExpired) {
            final U thanksView = getThanksView();
            thanksView.bind(basePromptViewConfig.getThanks());
            setDisplayedView(thanksView);
            final Long thanksDisplayTimeMs = basePromptViewConfig.getThanksDisplayTimeMs();
            if (thanksDisplayTimeMs == null) {
                hide();
            }
        }

    }

    @Override
    public final void dismiss(final boolean triggeredByConfigChange) throws RemoteException {
        if (!triggeredByConfigChange) {
            promptPresenter.notifyEventTriggered(PromptViewEvent.PROMPT_DISMISSED);
        }

        clearDisplayedQuestionViewReference();
        hide();
    }

    @Override
    public final boolean providesThanksView() {
        return getThanksView() != null;
    }

    public final void applyBaseConfig(@NotNull final BasePromptViewConfig basePromptViewConfig) {
        if (isDisplayed()) {
            throw new IllegalStateException("Configuration cannot be changed after the prompt is first displayed.");
        }

        this.basePromptViewConfig = basePromptViewConfig;
    }

    public final void addPromptEventListener(@NotNull final IEventListener promptEventListener) {
        promptPresenter.addPromptEventListener(promptEventListener);
    }


    protected final boolean isDisplayed() {
        return getChildCount() > 0;
    }

    private void initializeBaseConfig(@Nullable final AttrSet attrSet) {
        if (attrSet != null) {
            basePromptViewConfig = new BasePromptViewConfig(attrSet);
        }
    }

    private void setDisplayedView(@NotNull final Component component) {
        removeAllComponents();
        addComponent(component, new LayoutConfig(ComponentContainer.LayoutConfig.MATCH_PARENT,
                ComponentContainer.LayoutConfig.MATCH_CONTENT));


        setVisibility(VISIBLE);
    }

    private void displayQuestionViewIfNeeded() {
        if (displayedQuestionView == null) {
            final T questionViewToDisplay = getQuestionView();
            displayedQuestionView = questionViewToDisplay;
            setDisplayedView(questionViewToDisplay);
        }
    }

    private void clearDisplayedQuestionViewReference() {
        displayedQuestionView = null;
    }

    private void hide() {
        removeAllComponents();
        setVisibility(HIDE);
    }

}

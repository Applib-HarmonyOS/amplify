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

import ohos.rpc.RemoteException;
import ohos.utils.PacMap;
import com.github.stkent.amplify.prompt.interfaces.IPromptPresenter;
import com.github.stkent.amplify.prompt.interfaces.IPromptView;
import com.github.stkent.amplify.tracking.PromptInteractionEvent;
import com.github.stkent.amplify.tracking.interfaces.IEvent;
import com.github.stkent.amplify.tracking.interfaces.IEventListener;
import org.jetbrains.annotations.NotNull;
import java.util.ArrayList;
import java.util.List;

/**
 * public final class PromptPresenter implements IPromptPresenter.
 */
public final class PromptPresenter implements IPromptPresenter {

    private static final String PROMPT_FLOW_STATE_KEY = "PromptFlowStateKey";
    private static final PromptFlowState DEFAULT_PROMPT_FLOW_STATE = PromptFlowState.INITIALIZED;

    @NotNull
    private final IEventListener eventListener;

    @NotNull
    private final IPromptView promptView;

    @NotNull
    private PromptFlowState promptFlowState = DEFAULT_PROMPT_FLOW_STATE;

    @NotNull
    private final List<IEventListener> extraEventListeners = new ArrayList<>();

    /**
     * PromptPresenter constructor.
     *
     * @param eventListener event listener.
     * @param promptView prompt view.
     */
    public PromptPresenter(
            @NotNull final IEventListener eventListener,
            @NotNull final IPromptView promptView) {

        this.eventListener = eventListener;
        this.promptView = promptView;
    }

    @Override
    public void notifyEventTriggered(@NotNull final IEvent event) throws RemoteException {
        eventListener.notifyEventTriggered(event);
        for (final IEventListener extraEventListener : extraEventListeners) {
            extraEventListener.notifyEventTriggered(event);
        }
    }

    @Override
    public void addPromptEventListener(@NotNull final IEventListener promptEventListener) {
        extraEventListeners.add(promptEventListener);
    }

    @Override
    public void start() throws RemoteException {
        setToState(PromptFlowState.QUERYING_USER_OPINION);
    }

    @Override
    public void reportUserOpinion(@NotNull final UserOpinion userOpinion) throws RemoteException {
        if (userOpinion == UserOpinion.POSITIVE) {
            notifyEventTriggered(PromptInteractionEvent.USER_INDICATED_POSITIVE_OPINION);
            setToState(PromptFlowState.REQUESTING_POSITIVE_FEEDBACK);
        } else if (userOpinion == UserOpinion.CRITICAL) {
            notifyEventTriggered(PromptInteractionEvent.USER_INDICATED_CRITICAL_OPINION);
            setToState(PromptFlowState.REQUESTING_CRITICAL_FEEDBACK);
        }
    }

    @Override
    public void reportUserFeedbackAction(@NotNull final UserFeedbackAction userFeedbackAction) throws RemoteException {
        if (promptFlowState != PromptFlowState.REQUESTING_POSITIVE_FEEDBACK
                && promptFlowState != PromptFlowState.REQUESTING_CRITICAL_FEEDBACK) {

            throw new IllegalStateException(
                    "User opinion must be set before this method is called.");
        }

        if (userFeedbackAction == UserFeedbackAction.AGREED) {
            handleUserAgreedToGiveFeedback();
        } else if (userFeedbackAction == UserFeedbackAction.DECLINED) {
            handleUserDeclinedToGiveFeedback();
        }
    }

    @Override
    public void restoreStateFromBundle(final PacMap pacMap) throws RemoteException {
        final PromptFlowState promptFlowStatePacMap =
                PromptFlowState.values()[
                        pacMap.getIntValue(PROMPT_FLOW_STATE_KEY,
                                DEFAULT_PROMPT_FLOW_STATE.ordinal())];

        setToState(promptFlowStatePacMap, true);
    }

    @Override
    public @NotNull PacMap generateStateBundle() {
        final PacMap result = new PacMap();
        result.putIntValue(PROMPT_FLOW_STATE_KEY, promptFlowState.ordinal());
        return result;
    }

    private void handleUserAgreedToGiveFeedback() throws RemoteException {
        notifyEventTriggered(PromptInteractionEvent.USER_GAVE_FEEDBACK);

        if (promptFlowState == PromptFlowState.REQUESTING_POSITIVE_FEEDBACK) {
            notifyEventTriggered(PromptInteractionEvent.USER_GAVE_POSITIVE_FEEDBACK);
        } else if (promptFlowState == PromptFlowState.REQUESTING_CRITICAL_FEEDBACK) {
            notifyEventTriggered(PromptInteractionEvent.USER_GAVE_CRITICAL_FEEDBACK);
        }

        if (promptView.providesThanksView()) {
            setToState(PromptFlowState.THANKING_USER);
        } else {
            setToState(PromptFlowState.DISMISSED);
        }
    }

    private void handleUserDeclinedToGiveFeedback() throws RemoteException {
        notifyEventTriggered(PromptInteractionEvent.USER_DECLINED_FEEDBACK);

        if (promptFlowState == PromptFlowState.REQUESTING_POSITIVE_FEEDBACK) {
            notifyEventTriggered(PromptInteractionEvent.USER_DECLINED_POSITIVE_FEEDBACK);
        } else if (promptFlowState == PromptFlowState.REQUESTING_CRITICAL_FEEDBACK) {
            notifyEventTriggered(PromptInteractionEvent.USER_DECLINED_CRITICAL_FEEDBACK);
        }

        setToState(PromptFlowState.DISMISSED);
    }

    private void setToState(@NotNull final PromptFlowState promptFlowState) throws RemoteException {
        setToState(promptFlowState, false);
    }

    private void setToState(
            @NotNull final PromptFlowState promptFlowState,
            final boolean triggeredByConfigChange) throws RemoteException {

        this.promptFlowState = promptFlowState;

        switch (promptFlowState) {
            case QUERYING_USER_OPINION:
                promptView.queryUserOpinion(triggeredByConfigChange);
                break;
            case REQUESTING_POSITIVE_FEEDBACK:
                promptView.requestPositiveFeedback();
                break;
            case REQUESTING_CRITICAL_FEEDBACK:
                promptView.requestCriticalFeedback();
                break;
            case THANKING_USER:
                promptView.thankUser(triggeredByConfigChange);
                break;
            case DISMISSED:
                promptView.dismiss(triggeredByConfigChange);
                break;
            default:
                break;
        }
    }

}

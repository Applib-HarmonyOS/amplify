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
import ohos.app.Context;
import com.github.stkent.amplify.prompt.interfaces.IPromptView;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * DefaultLayoutPromptView extends BasePromptView DefaultLayoutQuestionView, DefaultLayoutThanksView
 *         implements IPromptView.
 */

public final class DefaultLayoutPromptView
        extends BasePromptView<DefaultLayoutQuestionView, DefaultLayoutThanksView>
        implements IPromptView {

    private static final String DEFAULT_LAYOUT_PROMPT_VIEW_CONFIG_KEY = "DEFAULT_LAYOUT_PROMPT_VIEW_CONFIG_KEY";

    private DefaultLayoutPromptViewConfig config;

    public DefaultLayoutPromptView(final Context context) {
        this(context, null);
    }

    public DefaultLayoutPromptView(final Context context, @Nullable final AttrSet attributeSet) {
        this(context, attributeSet, 0);
    }

    /**
     * DefaultLayoutPromptView constructor.
     *
     * @param context context.
     *
     * @param attributeSet attribute set.
     *
     * @param defStyleAttr def style Attr.
     */
    public DefaultLayoutPromptView(
            final Context context,
            @Nullable final AttrSet attributeSet,
            final int defStyleAttr) {

        super(context, attributeSet, defStyleAttr);
        init(attributeSet);
    }

    /**
     ** apply config.
     *
     * @param config DefaultLayoutPromptViewConfig.
     */
    public void applyConfig(@NotNull final DefaultLayoutPromptViewConfig config) {
        if (isDisplayed()) {
            throw new IllegalStateException("Configuration cannot be changed after the prompt is first displayed.");
        }

        this.config = config;
    }


    @Override
    protected boolean isConfigured() {
        return true;
    }

    @NotNull
    @Override
    protected DefaultLayoutQuestionView getQuestionView() {
        return new DefaultLayoutQuestionView(getContext(), config);
    }

    @NotNull
    @Override
    protected DefaultLayoutThanksView getThanksView() {
        return new DefaultLayoutThanksView(getContext(), config);
    }

    /**
     * Note: <code>Theme.obtainStyledAttributes</code> accepts a null <code>AttributeSet</code>; see documentation of
     * that method for confirmation.
     */
    private void init(@Nullable final AttrSet attributeSet) {
        if (attributeSet != null) {
            config = new DefaultLayoutPromptViewConfig(attributeSet);
        }
    }

}

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
 * CustomLayoutPromptView extends BasePromptView CustomLayoutQuestionView, CustomLayoutThanksView
 *         implements IPromptView.
 */
public final class CustomLayoutPromptView
        extends BasePromptView<CustomLayoutQuestionView, CustomLayoutThanksView>
        implements IPromptView {

    private CustomLayoutPromptViewConfig config;

    /**
     * CustomLayoutPromptView constructor.
     *
     * @param context context.
     */
    public CustomLayoutPromptView(final Context context) {
        this(context, null);
    }

    /**
     * CustomLayoutPromptView constructor.
     *
     * @param context context.
     * @param attrSet Attr set.
     */
    public CustomLayoutPromptView(final Context context, @Nullable final AttrSet attrSet) {
        this(context, attrSet, 0);
    }

    /**
     * CustomLayoutPromptView constructor.
     *
     * @param context context.
     * @param attributeSet Attr set.
     * @param defStyleAttr define style attribute.
     *
     */
    public CustomLayoutPromptView(
            final Context context,
            @Nullable final AttrSet attributeSet,
            final int defStyleAttr) {

        super(context, attributeSet, defStyleAttr);
        init(attributeSet);
    }

    /**
     * apply config.
     *
     * @param config CustomLayoutPromptViewConfig config.
     */
    public void applyConfig(@NotNull final CustomLayoutPromptViewConfig config) {
        if (isDisplayed()) {
            throw new IllegalStateException("Configuration cannot be changed after the prompt is first displayed.");
        }

        this.config = config;
    }


    @Override
    protected boolean isConfigured() {
        return config.isValid();
    }

    @NotNull

    @Override
    protected CustomLayoutQuestionView getQuestionView() {
        return new CustomLayoutQuestionView(getContext(), config.getQuestionLayout());
    }

    @Nullable
    @Override
    protected CustomLayoutThanksView getThanksView() {
        if (config.getThanksLayout() != null) {
            return new CustomLayoutThanksView(getContext(), config.getThanksLayout());
        }

        return null;
    }

    /**
     * Note: <code>Theme.obtainStyledAttributes</code> accepts a null <code>AttributeSet</code>; see documentation of
     * that method for confirmation.
     */
    private void init(@Nullable final AttrSet attrSet) {
        config = new CustomLayoutPromptViewConfig(attrSet);

    }

}

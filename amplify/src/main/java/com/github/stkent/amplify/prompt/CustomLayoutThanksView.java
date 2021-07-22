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

import ohos.agp.components.StackLayout;
import ohos.agp.components.Text;
import ohos.agp.render.layoutboost.LayoutBoost;
import ohos.app.Context;
import com.github.stkent.ResourceTable;
import com.github.stkent.amplify.prompt.interfaces.IThanks;
import com.github.stkent.amplify.prompt.interfaces.IThanksView;
import com.github.stkent.amplify.utils.Constants;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@SuppressWarnings("ViewConstructor")
class CustomLayoutThanksView extends StackLayout implements IThanksView {

    @NotNull
    private final Text titleText;

    @Nullable
    private final Text subtitleText;

    CustomLayoutThanksView(final Context context, final int layoutRes) {
        super(context);
        LayoutBoost.inflate(context, layoutRes, this, true);
        final Text titleTextcomponent = (Text) findComponentById(ResourceTable.Id_amplify_title_text_view);

        if (titleTextcomponent == null) {
            throw new IllegalStateException(Constants.MISSING_LAYOUT_IDS_EXCEPTION_MESSAGE);
        }

        this.titleText = titleTextcomponent;
        subtitleText = (Text) findComponentById(ResourceTable.Id_amplify_subtitle_text_view);
    }

    @Override
    public void bind(@NotNull final IThanks thanks) {
        titleText.setText(thanks.getTitle());

        final String subtitle = thanks.getSubTitle();

        if (subtitleText != null) {
            if (subtitle != null) {
                subtitleText.setText(subtitle);
                subtitleText.setVisibility(VISIBLE);
            } else {
                subtitleText.setVisibility(HIDE);
            }
        }
    }

    @NotNull
    protected Text getTitleText() {
        return titleText;
    }

    @Nullable
    protected Text getSubtitleText() {
        return subtitleText;
    }

}

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

import ohos.agp.colors.RgbColor;
import ohos.agp.components.element.ShapeElement;
import ohos.app.Context;
import com.github.stkent.ResourceTable;
import org.jetbrains.annotations.NotNull;

/**
 * DefaultLayoutThanksView extends CustomLayoutThanksView.
 */

@SuppressWarnings("ViewConstructor")
public final class DefaultLayoutThanksView extends CustomLayoutThanksView {

    /**
     * DefaultLayoutThanksView constructor.
     * Pending : Set background not working
     * setBackgroundColor(config.getFillColor());
     *
     * @param context context.
     *
     * @param config config.
     */
    public DefaultLayoutThanksView(
            final Context context,
            @NotNull final DefaultLayoutPromptViewConfig config) {

        super(context, ResourceTable.Layout_default_thanks_view);
        ShapeElement shapeElement = new ShapeElement();
        shapeElement.setShape(ShapeElement.RECTANGLE);
        shapeElement.setRgbColor(new RgbColor(RgbColor.fromArgbInt(config.getFillColor().getValue())));
        setBackground(shapeElement);
        getTitleText().setTextColor(config.getTitleTextColor());

        if (getSubtitleText() != null) {
            getSubtitleText().setTextColor(config.getSubtitleTextColor());
        }
    }



}

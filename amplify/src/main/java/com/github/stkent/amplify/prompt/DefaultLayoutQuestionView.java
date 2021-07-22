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
import ohos.agp.components.Component;
import ohos.agp.components.Text;
import ohos.agp.components.element.ShapeElement;
import ohos.agp.utils.Color;
import ohos.app.Context;
import com.github.stkent.ResourceTable;
import com.github.stkent.amplify.utils.Constants;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@SuppressWarnings("ViewConstructor")
final class DefaultLayoutQuestionView extends CustomLayoutQuestionView {

    private static final int DEFAULT_BUTTON_CORNER_RADIUS_PX = 0;

    /**
     * DefaultLayoutQuestionView.
     * Pending : Set background not working.
     * setBackgroundColor(config.getFillColor()).
     *
     * @param context .context
     *
     * @param config config
     */
    DefaultLayoutQuestionView(final Context context,
            @NotNull final DefaultLayoutPromptViewConfig config) {
        super(context, ResourceTable.Layout_default_question_view);
        final Text subtitleTextView = getSubtitleTextView();
        ShapeElement shapeElement = new ShapeElement();
        shapeElement.setShape(ShapeElement.RECTANGLE);
        shapeElement.setRgbColor(new RgbColor(RgbColor.fromArgbInt(config.getFillColor().getValue())));
        setBackground(shapeElement);

        if (subtitleTextView == null) {
            throw new IllegalStateException(Constants.MISSING_LAYOUT_IDS_EXCEPTION_MESSAGE);
        }
        getTitleTextView().setTextColor(config.getTitleTextColor());
        subtitleTextView.setTextColor(config.getSubtitleTextColor());

        setQuoteButtonUnquoteTextColor(getPositiveButton(), config.getPositiveButtonTextColor());
        setQuoteButtonUnquoteTextColor(getNegativeButton(), config.getNegativeButtonTextColor());

        configureTextSizes(config);
        configureButtonBackgrounds(config);
    }

    /**
     * We are defensive here, because it's not uncommon to make "buttons" out of UI components like
     * FrameLayouts, say. If we can't cast to a TextView to obtain a setTextColor method, the button
     * text color will be left unchanged.
     *
     * @param quoteButtonUnquote the "button" whose text color we wish to set
     * @param color the color we wish to apply
     */

    private void setQuoteButtonUnquoteTextColor(
            @NotNull final Component quoteButtonUnquote, final int color) {
        if (quoteButtonUnquote instanceof Text) {
            ((Text) quoteButtonUnquote).setTextColor(new Color(color));
            ((Text) quoteButtonUnquote).setTextAlignment(72);
        }
        quoteButtonUnquote.setMarginLeft(15);
        quoteButtonUnquote.setMarginRight(15);
    }

    private void configureTextSizes(@NotNull final DefaultLayoutPromptViewConfig config) {
        final Integer customTextSizePx = config.getCustomTextSizePx();

        if (customTextSizePx != null) {
            getTitleTextView().setTextSize(customTextSizePx);
            setQuoteButtonUnquoteTextSize(getPositiveButton(), customTextSizePx);
            setQuoteButtonUnquoteTextSize(getNegativeButton(), customTextSizePx);

            final Text subtitleTextView = getSubtitleTextView();

            if (subtitleTextView != null) {
                subtitleTextView.setTextSize(customTextSizePx);
            }
        }
    }

    /**
     * We are defensive here, because it's not uncommon to make "buttons" out of UI components like
     * FrameLayouts, say. If we can't cast to a TextView to obtain a setText method, the button text
     * size will be left unchanged.
     *
     * @param quoteButtonUnquote the "button" whose text size we wish to set
     * @param textSize the text size we wish to apply, in pixels
     */
    private void setQuoteButtonUnquoteTextSize(
            @NotNull final Component quoteButtonUnquote, final int textSize) {

        if (quoteButtonUnquote instanceof Text) {
            ((Text) quoteButtonUnquote).setTextSize(textSize);
        }
    }

    private void configureButtonBackgrounds(
            @NotNull final DefaultLayoutPromptViewConfig config) {

        final Integer customButtonCornerRadiusPx = config.getCustomButtonCornerRadiusPx();


        final Integer actualButtonCornerRadiusPx
                = customDimensionOrDefault(
                        customButtonCornerRadiusPx,
                        DEFAULT_BUTTON_CORNER_RADIUS_PX);

        setButtonViewBackground(
                getPositiveButton(),
                config.getPositiveButtonBackgroundColor(),
                actualButtonCornerRadiusPx);

        setButtonViewBackground(
                getNegativeButton(),
                config.getNegativeButtonBackgroundColor(),
                actualButtonCornerRadiusPx);
    }

    private int customDimensionOrDefault(
            @Nullable
            final Integer customDimension,
            final int defaultDimension) {

        return customDimension != null ? customDimension : defaultDimension;
    }


    private void setButtonViewBackground(
            @NotNull final Component button,
            final int fillColor,
            final int cornerRadiusPx) {
        ShapeElement shapeElement = new ShapeElement();
        shapeElement.setShape(ShapeElement.RECTANGLE);
        shapeElement.setRgbColor(new RgbColor(RgbColor.fromArgbInt(fillColor)));
        shapeElement.setCornerRadius(cornerRadiusPx);
        button.setBackground(shapeElement);
    }

}

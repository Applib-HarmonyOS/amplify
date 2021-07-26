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
import ohos.agp.utils.Color;
import ohos.utils.Parcel;
import ohos.utils.Sequenceable;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import java.util.Optional;

/**
 * DefaultLayoutPromptViewConfig implements Sequenceable.
 */

@SuppressWarnings({"PMD.ExcessiveParameterList", "checkstyle:parameternumber"})
public final class DefaultLayoutPromptViewConfig implements Sequenceable {

    private static final Color DEFAULT_FOREGROUND_COLOR = Color.WHITE;
    private static final Color DEFAULT_BACKGROUND_COLOR = Color.BLUE;
    

    private static int defaultIfNull(@Nullable final Integer primaryColor, final int defaultColor) {
        return primaryColor != null ? primaryColor : defaultColor;
    }



    @Nullable
    private static Integer suppliedAttrOrNull(@NotNull AttrSet attrSet, final String index) {
        Optional<Attr> optionalAttr = attrSet.getAttr(index);
        if (optionalAttr.isPresent()) {
            return optionalAttr.get().getIntegerValue();
        } else {
            return null;
        }
    }

    @Nullable
    private static Integer suppliedAttrOrNullColor(@NotNull AttrSet attrSet, final String index) {
        Optional<Attr> optionalAttr = attrSet.getAttr(index);
        if (optionalAttr.isPresent()) {
            Color c = optionalAttr.get().getColorValue();
            c.getValue();
            return c.getValue();
        } else {
            return null;
        }
    }


    @Nullable private Integer positiveButtonTextColor;
    @Nullable private Integer positiveButtonBackgroundColor;
    @Nullable private Integer positiveButtonBorderColor;

    @Nullable private Integer negativeButtonTextColor;
    @Nullable private Integer negativeButtonBackgroundColor;
    @Nullable private Integer negativeButtonBorderColor;

    @Nullable private final Integer foregroundColor;
    @Nullable private final Integer backgroundColor;

    @Nullable private Integer customTextSizePx;
    @Nullable private Integer customButtonBorderWidthPx;
    @Nullable private Integer customButtonCornerRadiusPx;
    
    @Nullable private final Integer titleTextColor;
    @Nullable private final Integer subtitleTextColor;


    /**
     * DefaultLayoutPromptViewConfig constructor.
     *
     * @param attrSet attr set.
     */
    public DefaultLayoutPromptViewConfig(@NotNull final AttrSet attrSet) {
        foregroundColor = suppliedAttrOrNullColor(
                attrSet, "prompt_view_foreground_color");

        backgroundColor = suppliedAttrOrNullColor(
                attrSet, "prompt_view_background_color");

        titleTextColor = suppliedAttrOrNullColor(
                attrSet, "prompt_view_title_text_color");

        subtitleTextColor = suppliedAttrOrNullColor(
                attrSet, "prompt_view_subtitle_text_color");

        positiveButtonTextColor = suppliedAttrOrNullColor(
                attrSet, "prompt_view_positive_button_text_color");

        positiveButtonBackgroundColor = suppliedAttrOrNullColor(
                attrSet, "prompt_view_positive_button_background_color");

        positiveButtonBorderColor = suppliedAttrOrNullColor(
                attrSet, "prompt_view_positive_button_border_color");

        negativeButtonTextColor = suppliedAttrOrNullColor(
                attrSet, "prompt_view_negative_button_text_color");

        negativeButtonBackgroundColor = suppliedAttrOrNullColor(
                attrSet, "prompt_view_negative_button_background_color");

        negativeButtonBorderColor = suppliedAttrOrNullColor(
                attrSet, "prompt_view_negative_button_border_color");

        customTextSizePx = suppliedAttrOrNull(
                attrSet, "prompt_view_text_size");

        customButtonBorderWidthPx = suppliedAttrOrNull(
                attrSet, "prompt_view_button_border_width");

        customButtonCornerRadiusPx = suppliedAttrOrNull(
                attrSet, "prompt_view_button_corner_radius");
    }

    protected DefaultLayoutPromptViewConfig(
            @Nullable final Integer foregroundColor,
            @Nullable final Integer backgroundColor,
            @Nullable final Integer titleTextColor,
            @Nullable final Integer subtitleTextColor) {

        this.foregroundColor     = foregroundColor;
        this.backgroundColor     = backgroundColor;
        this.titleTextColor      = titleTextColor;
        this.subtitleTextColor   = subtitleTextColor;
    }

    protected DefaultLayoutPromptViewConfig(
            @Nullable final String foregroundColor,
            @Nullable final String backgroundColor,
            @Nullable final String titleTextColor,
            @Nullable final String subtitleTextColor) {

        this.foregroundColor     = Color.getIntColor(foregroundColor);
        this.backgroundColor     = Color.getIntColor(backgroundColor);
        this.titleTextColor      = Color.getIntColor(titleTextColor);
        this.subtitleTextColor   = Color.getIntColor(subtitleTextColor);
    }

    protected void setCustomSize(@Nullable final Integer customTextSizePx,
                                 @Nullable final Integer customButtonBorderWidthPx,
                                 @Nullable final Integer customButtonCornerRadiusPx) {
        this.customTextSizePx = customTextSizePx;
        this.customButtonBorderWidthPx = customButtonBorderWidthPx;
        this.customButtonCornerRadiusPx = customButtonCornerRadiusPx;
    }

    protected void setPositiveButtonColor(@Nullable final Integer positiveButtonTextColor,
                                          @Nullable final Integer positiveButtonBackgroundColor,
                                          @Nullable final Integer positiveButtonBorderColor) {
        this.positiveButtonTextColor       = positiveButtonTextColor;
        this.positiveButtonBackgroundColor = positiveButtonBackgroundColor;
        this.positiveButtonBorderColor     = positiveButtonBorderColor;
    }

    protected void setNegativeButtonColor(@Nullable final Integer negativeButtonTextColor,
                                          @Nullable final Integer negativeButtonBackgroundColor,
                                          @Nullable final Integer negativeButtonBorderColor) {
        this.negativeButtonTextColor       = negativeButtonTextColor;
        this.negativeButtonBackgroundColor = negativeButtonBackgroundColor;
        this.negativeButtonBorderColor     = negativeButtonBorderColor;
    }




    public Color getFillColor() {
        return  new Color(getBackgroundColor());
    }

    public Color getTitleTextColor() {
        return new Color(defaultIfNull(titleTextColor, getForegroundColor()));
    }

    public Color getSubtitleTextColor() {
        return new Color(defaultIfNull(subtitleTextColor, getForegroundColor()));
    }

    public int getPositiveButtonTextColor() {
        return defaultIfNull(positiveButtonTextColor, getBackgroundColor());
    }



    public int getNegativeButtonBorderColor() {
        return defaultIfNull(negativeButtonBorderColor, getForegroundColor());
    }

    @Nullable
    public Integer getCustomTextSizePx() {
        return customTextSizePx;
    }

    @Nullable
    public Integer getCustomButtonBorderWidthPx() {
        return customButtonBorderWidthPx;
    }

    @Nullable
    public Integer getCustomButtonCornerRadiusPx() {
        return customButtonCornerRadiusPx;
    }

    public int getNegativeButtonTextColor() {
        return defaultIfNull(negativeButtonTextColor, getBackgroundColor());
    }

    public int getNegativeButtonBackgroundColor() {
        return defaultIfNull(negativeButtonBackgroundColor, getForegroundColor());
    }

    private int getForegroundColor() {
        return defaultIfNull(foregroundColor, DEFAULT_FOREGROUND_COLOR.getValue());
    }

    private int getBackgroundColor() {
        return defaultIfNull(backgroundColor, DEFAULT_BACKGROUND_COLOR.getValue());
    }

    public int getPositiveButtonBackgroundColor() {
        return defaultIfNull(positiveButtonBackgroundColor, getForegroundColor());
    }

    public int getPositiveButtonBorderColor() {
        return defaultIfNull(positiveButtonBorderColor, getForegroundColor());
    }


    @Override
    public boolean marshalling(Parcel parcel) {
        return false;
    }

    @Override
    public boolean unmarshalling(Parcel parcel) {
        return false;
    }

    /**
     * Builder class.
     */
    public static final class Builder {
        @Nullable private Integer foregroundColor;
        @Nullable private Integer backgroundColor;
        @Nullable private Integer titleTextColor;
        @Nullable private Integer positiveButtonTextColor;
        @Nullable private Integer subtitleTextColor;
        @Nullable private Integer negativeButtonTextColor;
        @Nullable private Integer positiveButtonBackgroundColor;
        @Nullable private Integer negativeButtonBorderColor;
        @Nullable private Integer positiveButtonBorderColor;
        @Nullable private Integer negativeButtonBackgroundColor;
        @Nullable private Integer customTextSizePx;
        @Nullable private Integer customButtonBorderWidthPx;
        @Nullable private Integer customButtonCornerRadiusPx;

        public Builder setForegroundColor(final int foregroundColor) {
            this.foregroundColor = foregroundColor;
            return this;
        }

        public Builder setBackgroundColor(final int backgroundColor) {
            this.backgroundColor = backgroundColor;
            return this;
        }

        public Builder setPositiveButtonTextColor(final int positiveButtonTextColor) {
            this.positiveButtonTextColor = positiveButtonTextColor;
            return this;
        }

        public Builder setPositiveButtonBackgroundColor(final int positiveButtonBackgroundColor) {
            this.positiveButtonBackgroundColor = positiveButtonBackgroundColor;
            return this;
        }

        public Builder setPositiveButtonBorderColor(final int positiveButtonBorderColor) {
            this.positiveButtonBorderColor = positiveButtonBorderColor;
            return this;
        }

        public Builder setNegativeButtonTextColor(final int negativeButtonTextColor) {
            this.negativeButtonTextColor = negativeButtonTextColor;
            return this;
        }

        public Builder setButtonCornerRadiusPx(final int customButtonCornerRadiusPx) {
            this.customButtonCornerRadiusPx = customButtonCornerRadiusPx;
            return this;
        }

        public Builder setNegativeButtonBackgroundColor(final int negativeButtonBackgroundColor) {
            this.negativeButtonBackgroundColor = negativeButtonBackgroundColor;
            return this;
        }

        public Builder setNegativeButtonBorderColor(final int negativeButtonBorderColor) {
            this.negativeButtonBorderColor = negativeButtonBorderColor;
            return this;
        }

        public Builder setCustomTextSizePx(final int customTextSizePx) {
            this.customTextSizePx = customTextSizePx;
            return this;
        }

        public Builder setButtonBorderWidthPx(final int customButtonBorderWidthPx) {
            this.customButtonBorderWidthPx = customButtonBorderWidthPx;
            return this;
        }

        public Builder setTitleTextColor(final int titleTextColor) {
            this.titleTextColor = titleTextColor;
            return this;
        }

        public Builder setSubtitleTextColor(final int subtitleTextColor) {
            this.subtitleTextColor = subtitleTextColor;
            return this;
        }



        /**
         * DefaultLayoutPromptViewConfig build.
         */

        public DefaultLayoutPromptViewConfig build() {
            DefaultLayoutPromptViewConfig defaultLayoutPromptViewConfig = new DefaultLayoutPromptViewConfig(
                    foregroundColor,
                    backgroundColor,
                    titleTextColor,
                    subtitleTextColor);

            defaultLayoutPromptViewConfig.setCustomSize(
                    customTextSizePx,
                    customButtonBorderWidthPx,
                    customButtonCornerRadiusPx);

            defaultLayoutPromptViewConfig.setPositiveButtonColor(
                    positiveButtonTextColor,
                    positiveButtonBackgroundColor,
                    positiveButtonBorderColor);

            defaultLayoutPromptViewConfig.setNegativeButtonColor(
                    negativeButtonTextColor,
                    negativeButtonBackgroundColor,
                    negativeButtonBorderColor);


            return defaultLayoutPromptViewConfig;
        }

    }
}


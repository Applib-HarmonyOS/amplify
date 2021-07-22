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
import com.github.stkent.amplify.utils.Constants;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import java.util.Optional;

/**
 * CustomLayoutPromptViewConfig implements Sequenceable.
 */
public final class CustomLayoutPromptViewConfig implements Sequenceable {
    @Nullable
    private static Integer suppliedLayoutOrNull(@NotNull AttrSet attrSet, final String index) {
        Optional<Attr> optionalAttr = attrSet.getAttr(index);
        if (optionalAttr.isPresent()) {
            return optionalAttr.get().getIntegerValue();
        } else {
            return null;
        }
    }

    @Nullable
    private final Integer questionLayout;

    @Nullable
    private final Integer thanksLayout;

    /**
     * CustomLayoutPromptViewConfig constructor.
     *
     * @param attrSet attribute set.
     */
    public CustomLayoutPromptViewConfig(@NotNull final AttrSet attrSet) {
        System.out.println("CHIRAG : CustomLayoutPromptViewConfig");
        System.out.println("CHIRAG : CustomLayoutPromptView attr length " + attrSet.getLength());
        this.questionLayout =
                suppliedLayoutOrNull(attrSet, "prompt_view_question_layout");
        this.thanksLayout =
                suppliedLayoutOrNull(attrSet, "prompt_view_thanks_layout");
        if(this.questionLayout == null){
            System.out.println("CHIRAG : CustomLayoutPromptViewConfig question null");
        }
        if(this.thanksLayout == null){
            System.out.println("CHIRAG : CustomLayoutPromptViewConfig thanks null");
        }
    }

    /**
     * CustomLayoutPromptViewConfig constructor.
     *
     * @param questionLayout int question layout value.
     * @param thanksLayout int thanks layout value.
     */
    public CustomLayoutPromptViewConfig(final int questionLayout, @Nullable final Integer thanksLayout) {
        this.questionLayout = questionLayout;
        this.thanksLayout = thanksLayout;
    }

    /**
     * check if it is valid to give prompt.
     *
     */
    public boolean isValid() {
        return questionLayout != null;
    }

    /**
     * get Question Layout.
     *
     */
    public int getQuestionLayout() {
        if (questionLayout == null) {
            throw new IllegalStateException(Constants.MISSING_LAYOUT_IDS_EXCEPTION_MESSAGE);
        }

        return questionLayout;
    }

    @Nullable
    public Integer getThanksLayout() {
        return thanksLayout;
    }

    @Override
    public boolean marshalling(Parcel parcel) {
        return false;
    }

    @Override
    public boolean unmarshalling(Parcel parcel) {
        return false;
    }
}

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

import com.github.stkent.amplify.prompt.interfaces.IThanks;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

final class Thanks implements IThanks {

    @NotNull
    private final String title;

    @Nullable
    private final String subTitle;

    protected Thanks(
            @NotNull  final String title,
            @Nullable final String subTitle) {

        this.title = title;
        this.subTitle = subTitle;
    }

    @NotNull
    @Override
    public String getTitle() {
        return title;
    }

    @Nullable
    @Override
    public String getSubTitle() {
        return subTitle;
    }

}

/*
 * Copyright (C) 2020-21 Application Library Engineering Group
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.github.stkent.amplify.tracking.rules;

import com.github.stkent.amplify.IEnvironment;
import com.github.stkent.amplify.tracking.interfaces.IEnvironmentBasedRule;
import org.jetbrains.annotations.NotNull;

/**
 * HuaweiAppGalleryRule implements InterfaceEnvironmentBasedRule.
 */
public final class HuaweiAppGalleryRule implements IEnvironmentBasedRule {

    @Override
    public boolean shouldAllowFeedbackPrompt(@NotNull final IEnvironment environment) {
        return environment.isHuaweiAppGalleryInstalled();
    }

    @NotNull
    @Override
    public String getDescription() {
        return "Huawei App Gallery Rule";
    }

}

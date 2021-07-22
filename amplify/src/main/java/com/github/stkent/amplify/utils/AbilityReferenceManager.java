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

package com.github.stkent.amplify.utils;

import ohos.aafwk.ability.Ability;
import ohos.aafwk.ability.AbilityLifecycleCallbacks;
import ohos.utils.PacMap;
import org.jetbrains.annotations.Nullable;
import java.lang.ref.WeakReference;

/**
 * AbilityReferenceManager implements AbilityLifecycleCallbacks.
 */
public final class AbilityReferenceManager implements AbilityLifecycleCallbacks {

    @Nullable
    private WeakReference<Ability> wability;

    /**
     * Get validated ability.
     */
    @Nullable
    public Ability getValidatedAbility() {
        if (wability == null) {
            return null;
        }

        final Ability ability = wability.get();
        if (!isAbilityValid(ability)) {
            return null;
        }

        return ability;
    }

    @Override
    public void onAbilityActive(final Ability ability) {
        wability = new WeakReference<>(ability);
        // This method intentionally left blank
    }

    @Override
    public void onAbilityInactive(final Ability ability) {
        // This method intentionally left blank
    }

    @Override
    public void onAbilityBackground(final Ability ability) {
        // This method intentionally left blank
    }

    @Override
    public void onAbilityForeground(final Ability ability) {
        // This method intentionally left blank
    }

    @Override
    public void onAbilityStart(final Ability ability) {
        // This method intentionally left blank
    }

    @Override
    public void onAbilityStop(final Ability ability) {
        // This method intentionally left blank
    }

    @Override
    public void onAbilitySaveState(final PacMap outState) {
        // This method intentionally left blank
    }


    private boolean isAbilityValid(@Nullable final Ability ability) {
        if (ability == null) {
            return false;
        } else {
            return !ability.isTerminating();
        }
    }

}

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

package com.github.stkent.amplify.tracking;

import ohos.data.preferences.Preferences;
import com.github.stkent.amplify.tracking.interfaces.ISettings;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import java.util.Map;

/**
 * Settings implements InterfaceSettings.
 */
public final class Settings<T> implements ISettings<T> {

    private final Preferences pref;

    public Settings(@NotNull final Preferences pref) {
        this.pref = pref;
    }

    /**
     * write tracking value.
     *
     * @param trackingKey tracking key
     * @param value T value
     */
    public void writeTrackingValue(@NotNull final String trackingKey, final T value) {
        if (value.getClass().equals(String.class)) {
            pref.putString(trackingKey, (String) value);
        } else if (value.getClass().equals(Boolean.class)) {
            pref.putBoolean(trackingKey, (Boolean) value);
        } else if (value.getClass().equals(Long.class)) {
            pref.putLong(trackingKey, (Long) value);
        } else if (value.getClass().equals(Integer.class)) {
            pref.putInt(trackingKey, (Integer) value);
        } else if (value.getClass().equals(Float.class)) {
            pref.putFloat(trackingKey, (Float) value);
        } else {
            throw new IllegalArgumentException(
                    "Event value must be one of String, Boolean, Long, Integer or Float");
        }
        pref.flush();
    }

    /**
     * tracking key.
     *
     * @param trackingKey tracking key
     */
    @Nullable
    public T readTrackingValue(@NotNull final String trackingKey) {
        final Map<String, ?> map = pref.getAll();

        for (final Map.Entry<String, ?> entry : map.entrySet()) {
            if (entry.getKey().equals(trackingKey)) {
                return (T) entry.getValue();
            }
        }
        return null;
    }

}

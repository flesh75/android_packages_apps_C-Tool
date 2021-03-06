/*
 * Copyright (C) 2012-2013 The CyanogenMod Project
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

package com.crom.settings.device.htc;

import android.content.Context;
import android.content.res.Resources;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.CheckBoxPreference;
import android.preference.TwoStatePreference;
import android.preference.ListPreference;
import android.preference.Preference;
import android.preference.PreferenceActivity;
import android.preference.PreferenceFragment;
import android.preference.PreferenceManager;
import android.preference.PreferenceScreen;
import android.util.Log;

import com.crom.settings.R;

public class ButtonLightFragmentActivity extends PreferenceFragment {

    private static final String TAG = "DeviceSettings_ButtonLight";
    public static final String KEY_BUTTONLIGHTNOTIFICATION_SWITCH = "buttonlightnotification_switch";

    private static boolean sButtonLight;
    private TwoStatePreference mButtonLightNotification;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Resources res = getResources();
        sButtonLight = res.getBoolean(R.bool.has_buttonlight);

        addPreferencesFromResource(R.xml.htc_buttonlight_preferences);

        if (sButtonLight) {
            mButtonLightNotification = (TwoStatePreference) findPreference(KEY_BUTTONLIGHTNOTIFICATION_SWITCH);
            mButtonLightNotification.setEnabled(ButtonLightNotificationSwitch.isSupported());
            mButtonLightNotification.setOnPreferenceChangeListener(new ButtonLightNotificationSwitch());
        }
    }

    @Override
    public boolean onPreferenceTreeClick(PreferenceScreen preferenceScreen, Preference preference) {
        String boxValue;
        String key = preference.getKey();
        Log.w(TAG, "key: " + key);
        return true;
    }

    public static void restore(Context context) {
        SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(context);
    }
}

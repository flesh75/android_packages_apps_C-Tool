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

package com.crom.settings.device;

import android.app.ActionBar;
import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.res.Resources;
import android.content.Context;
import android.os.Bundle;
import android.preference.PreferenceDrawerActivityAlt;
import android.support.v13.app.FragmentPagerAdapter;
import android.support.v4.view.PagerTabStrip;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.crom.settings.R;
import com.crom.settings.SettingsPreferenceFragment;
import com.crom.settings.Utils;

// import htc one stuffs
import com.crom.settings.device.htc.*;
// import the other stuffs

import java.util.ArrayList;

public class DeviceTools extends SettingsPreferenceFragment {

    private static final String TAG = "DeviceTools";

    PagerTabStrip mPagerTabStrip;
    ViewPager mViewPager;

    boolean isHtcOne;
    String titleString[];

    ViewGroup mContainer;

    static Bundle mSavedState;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        mContainer = container;

        isHtcOne = getResources().getBoolean(R.bool.is_htc_one);

        View view = inflater.inflate(R.layout.pager_tab, container, false);
        mViewPager = (ViewPager) view.findViewById(R.id.viewPager);
        mPagerTabStrip = (PagerTabStrip) view.findViewById(R.id.pagerTabStrip);

        DeviceToolAdapter DeviceToolAdapter = new DeviceToolAdapter(getFragmentManager());
        mViewPager.setAdapter(DeviceToolAdapter);

        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        // We don't call super.onActivityCreated() here, since it assumes we already set up
        // Preference (probably in onCreate()), while ProfilesSettings exceptionally set it up in
        // this method.
        // On/off switch
        Activity activity = getActivity();
        //Switch

        if (activity instanceof PreferenceDrawerActivityAlt) {
            PreferenceDrawerActivityAlt preferenceActivity = (PreferenceDrawerActivityAlt) activity;
        }

        // After confirming PreferenceScreen is available, we call super.
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onResume() {
        super.onResume();

        if (!Utils.isTablet(getActivity())) {
            mContainer.setPadding(0, 0, 0, 0);
        }
    }

    class DeviceToolAdapter extends FragmentPagerAdapter {
        String titles[] = getTitles();
        private Fragment frags[] = new Fragment[titles.length];

        public DeviceToolAdapter(FragmentManager fm) {
            super(fm);
            // Display for certain devices only
            if (isHtcOne) {
                frags[0] = new ButtonLightFragmentActivity();
                frags[1] = new SensorsFragmentActivity();
                frags[2] = new TouchscreenFragmentActivity();
            }
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return titles[position];
        }

        @Override
        public Fragment getItem(int position) {
            return frags[position];
        }

        @Override
        public int getCount() {
            return frags.length;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            if (position >= getCount()) {
                FragmentManager manager = ((Fragment) object).getFragmentManager();
                FragmentTransaction trans = manager.beginTransaction();
                trans.remove((Fragment) object);
                trans.commit();
            }
        }
    }

    private String[] getTitles() {
        if (isHtcOne) {
            titleString = new String[]{
                    getResources().getString(R.string.category_buttonlight_title),
                    getResources().getString(R.string.category_sensors_title),
                    getResources().getString(R.string.category_touchscreen_title)};
        }
        return titleString;
    }
}

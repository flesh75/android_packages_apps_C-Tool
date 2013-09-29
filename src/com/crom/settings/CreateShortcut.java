/*
 * Copyright (C) 2010 The Android Open Source Project
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

package com.crom.settings;

import android.app.LauncherActivity;
import android.content.Intent;
import android.view.View;
import android.widget.ListView;

public class CreateShortcut extends LauncherActivity {

    @Override
    protected Intent getTargetIntent() {
        Intent targetIntent = new Intent(Intent.ACTION_MAIN, null);
        targetIntent.addCategory("com.crom.settings.SHORTCUT");
        targetIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        return targetIntent;
    }

    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
        Intent shortcutIntent = intentForPosition(position);

        String intentClass = shortcutIntent.getComponent().getClassName();

        shortcutIntent = new Intent();
        shortcutIntent.setClass(getApplicationContext(), CrSettingsActivity.class);
        shortcutIntent.setAction("com.crom.settings.START_NEW_FRAGMENT");
        shortcutIntent.putExtra("crom_fragment_name", intentClass);
        shortcutIntent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        shortcutIntent.setFlags(Intent.FLAG_ACTIVITY_RESET_TASK_IF_NEEDED);

        Intent intent = new Intent();
        intent.putExtra(Intent.EXTRA_SHORTCUT_ICON_RESOURCE,
                Intent.ShortcutIconResource.fromContext(this, getProperShortcutIcon(intentClass)));
        intent.putExtra(Intent.EXTRA_SHORTCUT_INTENT, shortcutIntent);
        intent.putExtra(Intent.EXTRA_SHORTCUT_NAME, itemForPosition(position).label);
        setResult(RESULT_OK, intent);
        finish();
    }

    private int getProperShortcutIcon(String className) {
        String c = className.substring(className.lastIndexOf(".") + 1);

        if (c.equals("GeneralUI"))
            return R.drawable.ic_crom_interface;
        else if (c.equals("NavigationBar"))
            return R.drawable.ic_crom_navbar;
        else if (c.equals("PowerWidget"))
            return R.drawable.ic_crom_powerwidget;
        else if (c.equals("StatusBar"))
            return R.drawable.ic_crom_statusbar;
        else if (c.equals("QuickSettings"))
            return R.drawable.ic_crom_quicksettings;
        else if (c.equals("PowerMenu"))
            return R.drawable.ic_crom_powermenu;
        else if (c.equals("PieHeader"))
            return R.drawable.ic_crom_pie;
        else if (c.equals("Lockscreen"))
            return R.drawable.ic_crom_lockscreen;
        else if (c.equals("ScreenStateToggles"))
            return R.drawable.ic_crom_screen_state;
        else if (c.equals("WakeLockBlocker")){
            return R.drawable.ic_crom_wakelock_blocker;
        else
            return R.mipmap.ic_launcher;
    }

    @Override
    protected boolean onEvaluateShowIcons() {
        return false;
    }
}

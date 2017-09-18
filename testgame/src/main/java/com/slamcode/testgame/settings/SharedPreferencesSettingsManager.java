package com.slamcode.testgame.settings;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public final class SharedPreferencesSettingsManager implements AppSettingsManager {

    private final SharedPreferences sharedPreferences;

    private static final String WAS_INFO_SHOWN_NAME = "WasInfoShown";
    private final static boolean WAS_INFO_SHOWN_DEFAULT_VALUE = false;

    public SharedPreferencesSettingsManager(Context context)
    {
        this.sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
    }

    @Override
    public boolean wasInfoDialogShown() {
        return this.sharedPreferences.getBoolean(WAS_INFO_SHOWN_NAME, WAS_INFO_SHOWN_DEFAULT_VALUE);
    }

    @Override
    public void setWasInfoDialogShown(boolean newValue) {
        SharedPreferences.Editor editor = this.sharedPreferences.edit();

        editor.putBoolean(WAS_INFO_SHOWN_NAME, newValue);
        editor.apply();
    }
}

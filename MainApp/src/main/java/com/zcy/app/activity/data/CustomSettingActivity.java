package com.zcy.app.activity.data;

import android.os.Bundle;
import android.preference.PreferenceActivity;

import com.zcy.app.R;

public class CustomSettingActivity extends PreferenceActivity
{

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.settings);
    }
}

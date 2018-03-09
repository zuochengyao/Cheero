package com.zcy.app.activity.data;

import android.app.Activity;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.preference.PreferenceFragment;
import android.support.annotation.Nullable;

import com.zcy.app.R;

public class CustomSettingActivity extends Activity
{

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        FragmentTransaction ft = getFragmentManager().beginTransaction();
        ft.add(android.R.id.content, new SettingFragment());
        ft.commit();
    }

    public static class SettingFragment extends PreferenceFragment
    {
        @Override
        public void onCreate(@Nullable Bundle savedInstanceState)
        {
            super.onCreate(savedInstanceState);
            addPreferencesFromResource(R.xml.settings);
        }
    }
}

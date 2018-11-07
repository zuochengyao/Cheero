package com.icheero.plugins.activity;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

import com.icheero.plugins.R;

public class PluginActivity extends Activity
{
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        TextView view = new TextView(this);
        view.setText("This is a Plugin Activity");
        setContentView(R.layout.activity_plugin);
    }
}

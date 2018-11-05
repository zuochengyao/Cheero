package com.icheero.plugins.activity;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.icheero.common.base.BaseActivity;
import com.icheero.plugins.R;

public class MainActivity extends BaseActivity
{
    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViewById(R.id.ClickMe).setOnClickListener(v -> {
            Toast.makeText(MainActivity.this, "Hello plugin!", Toast.LENGTH_SHORT).show();
            initClassLoader();
        });
    }

    private void initClassLoader()
    {
        ClassLoader classLoader = getClassLoader();
        if (classLoader != null)
        {
            Log.i(TAG, "ClassLoader: " + classLoader.toString());
            while (classLoader.getParent() != null)
            {
                classLoader = classLoader.getParent();
                Log.i(TAG, "ParentClassLoader: " + classLoader.toString());
            }
        }
    }

}

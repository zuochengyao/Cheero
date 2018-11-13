package com.icheero.plugins.activity;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.icheero.sdk.base.BaseActivity;
import com.icheero.sdk.core.manager.AndFixPatchManager;
import com.icheero.plugins.R;

public class MainActivity extends BaseActivity
{
    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViewById(R.id.click_me).setOnClickListener(v -> {
            Toast.makeText(MainActivity.this, "Hello plugin!", Toast.LENGTH_SHORT).show();
            initClassLoader();
        });

        findViewById(R.id.create_bug).setOnClickListener(v -> createBug());

        findViewById(R.id.fix_bug).setOnClickListener(v -> fixBug());
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

    private void createBug()
    {
        com.icheero.sdk.util.Log.print();
    }

    private void fixBug()
    {
        String path = AndFixPatchManager.getInstance().getPatchDir().concat("cheero").concat(AndFixPatchManager.PATCH_EXTENSION);
        AndFixPatchManager.getInstance().addPatch(path);
    }
}

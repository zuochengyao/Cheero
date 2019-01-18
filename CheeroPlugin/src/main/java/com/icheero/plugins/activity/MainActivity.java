package com.icheero.plugins.activity;

import android.os.Bundle;
import android.widget.Toast;

import com.icheero.plugins.R;
import com.icheero.sdk.base.BaseActivity;
import com.icheero.sdk.core.manager.AndFixPatchManager;
import com.icheero.sdk.core.manager.IOManager;
import com.icheero.sdk.util.Log;

public class MainActivity extends BaseActivity
{
    private static final Class TAG = MainActivity.class;

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
        Log.print();
    }

    private void fixBug()
    {
        String path = IOManager.DIR_PATH_CHEERO_PATCHES.concat("cheero").concat(AndFixPatchManager.PATCH_EXTENSION);
        AndFixPatchManager.getInstance().addPatch(path);
    }
}

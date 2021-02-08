package com.icheero.app.activity.memory;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.widget.ImageView;

import com.icheero.app.R;
import com.icheero.sdk.base.BaseActivity;
import com.icheero.sdk.util.Log;

import java.lang.ref.WeakReference;

import androidx.annotation.NonNull;

public class WeakHandlerActivity extends BaseActivity
{
    private WeakHandler mWeakHandler;
    private ImageView mImageView;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        mImageView = new ImageView(this);
        mImageView.setImageResource(R.drawable.nini);
        setContentView(mImageView);
        mWeakHandler = new WeakHandler(this);
        mWeakHandler.sendEmptyMessageDelayed(0, 2 * 1000);
        mWeakHandler.sendEmptyMessageDelayed(0, 10 * 1000);
        mWeakHandler.sendEmptyMessageDelayed(1, 3 * 1000);
        mWeakHandler.sendEmptyMessageDelayed(1, 13 * 1000);
    }

    @Override
    protected void onDestroy()
    {
        super.onDestroy();
        gc();
    }

    private void gc()
    {
        Log.i(TAG, "gc");
        System.gc();
    }

    private static class WeakHandler extends Handler
    {
        private final WeakReference<WeakHandlerActivity> mWeakActivity;

        WeakHandler(WeakHandlerActivity activity)
        {
            this.mWeakActivity = new WeakReference<>(activity);
        }

        @Override
        public void handleMessage(@NonNull Message msg)
        {
            super.handleMessage(msg);
            Log.i(TAG, "MyHandler - handleMessage");
            switch (msg.what)
            {
                case 0:
                {
                    Log.i(TAG, mWeakActivity.get().toString());
                    mWeakActivity.get().gc();
                    Log.i(TAG, mWeakActivity.get().toString());
                    break;
                }
                case 1:
                {
                    mWeakActivity.get().finish();
                    // mWeakActivity.clear();
                    break;
                }
            }
        }
    }
}
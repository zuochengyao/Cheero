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
        mWeakHandler.sendEmptyMessageDelayed(0, 0);
        mWeakHandler.sendEmptyMessageDelayed(0, 30);
    }

    private void test()
    {
        Log.i(TAG, "test");
    }

    private static class WeakHandler extends Handler
    {
        private WeakReference<WeakHandlerActivity> mWeakActivity;

        WeakHandler(WeakHandlerActivity activity)
        {
            this.mWeakActivity = new WeakReference<>(activity);
        }
        @Override
        public void handleMessage(@NonNull Message msg)
        {
            super.handleMessage(msg);
            WeakHandlerActivity activity = mWeakActivity.get();
            Log.i(TAG, "MyHandler - handleMessage ------ 消息到达了  activity is NULL:" + (activity == null));
            assert activity != null;
            Log.i(TAG, "MyHandler - handleMessage ------ 消息到达了  activity is NULL:" + (activity == null));
            activity.test();
        }
    }
}
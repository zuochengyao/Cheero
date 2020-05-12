package com.icheero.app.activity.service;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.view.View;
import android.widget.Button;

import com.icheero.app.R;
import com.icheero.app.service.CheeroIntentService;
import com.icheero.app.service.CheeroService;
import com.icheero.sdk.base.BaseActivity;
import com.icheero.sdk.util.Log;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class AboutServiceActivity extends BaseActivity
{
    @BindView(R.id.start_cheero_service)
    Button mStartCheeroService;
    @BindView(R.id.stop_cheero_service)
    Button mStopCheeroService;
    @BindView(R.id.bind_cheero_service)
    Button mBindCheeroService;
    @BindView(R.id.unbind_cheero_service)
    Button mUnbindCheeroService;
    @BindView(R.id.call_cheero_service_method)
    Button mCallCheeroServiceMethod;
    @BindView(R.id.start_cheero_intent_service)
    Button mStartCheeroIntentService;

    private Intent mIntent;
    private BindServiceConnection mConnection;
    private CheeroService mCheeroService;
    private CheeroService.CheeroBinder mCheeroBinder;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_service);
        doInitView();
    }

    @Override
    protected void onDestroy()
    {
        super.onDestroy();
        mConnection = null;
    }

    private void doInitView()
    {
        ButterKnife.bind(this);
        mIntent = new Intent();
        mIntent.setAction(CheeroService.ACTION_START);
        mIntent.setPackage(getPackageName());
        mConnection = new BindServiceConnection();
    }

    @OnClick({R.id.start_cheero_service, R.id.stop_cheero_service, R.id.bind_cheero_service, R.id.unbind_cheero_service, R.id.call_cheero_service_method, R.id.start_cheero_intent_service})
    protected void OnServiceClickEvent(View v)
    {
        switch (v.getId())
        {
            case R.id.start_cheero_service:
                startService(mIntent);
                break;
            case R.id.stop_cheero_service:
                stopService(mIntent);
                break;
            case R.id.bind_cheero_service:
                bindService(mIntent, mConnection, Context.BIND_AUTO_CREATE);
                break;
            case R.id.unbind_cheero_service:
                if (mCheeroBinder != null)
                    unbindService(mConnection);
                break;
            case R.id.call_cheero_service_method:
                if (mCheeroService != null)
                    mCheeroService.call();
                break;
            case R.id.start_cheero_intent_service:
                Intent intent = new Intent(this, CheeroIntentService.class);
                intent.putExtra("task","播放音乐");
                startService(intent);
                intent.putExtra("task","播放视频");
                startService(intent);
                intent.putExtra("task","播放图片");
                startService(intent);
                break;
        }
    }

    private class BindServiceConnection implements ServiceConnection
    {
        @Override
        public void onServiceConnected(ComponentName name, IBinder binder)
        {
            Log.i(TAG, name.getShortClassName() + " onServiceConnected");
            mCheeroBinder = (CheeroService.CheeroBinder) binder;
            mCheeroService = mCheeroBinder.getService();
        }

        @Override
        public void onServiceDisconnected(ComponentName name)
        {
            Log.i(TAG, name.getShortClassName() + "onServiceDisconnected");
            mCheeroBinder = null;
            mCheeroService = null;
        }
    }
}

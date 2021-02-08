package com.icheero.app.activity.framework.xposed;

import android.os.Bundle;
import android.widget.TextView;

import com.icheero.app.R;
import com.icheero.sdk.base.BaseActivity;
import com.icheero.sdk.core.push.SocketClient;

import butterknife.BindView;
import butterknife.ButterKnife;

public class XposedActivity extends BaseActivity
{
    @BindView(R.id.xposedText)
    TextView xposedText;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_xposed);
        ButterKnife.bind(this);
//        xposedText.setText(getDeviceID());
        SocketClient client = new SocketClient();
        new Thread(() -> client.start()).start();
    }

//    public String getDeviceID()
//    {
//        TelephonyManager tm = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
//        if (mPermissionManager.checkPermission(Manifest.permission.READ_PHONE_STATE))
//            return tm.getDeviceId();
//        else
//            return null;
//    }



}

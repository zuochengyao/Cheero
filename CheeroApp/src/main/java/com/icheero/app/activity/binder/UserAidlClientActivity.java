package com.icheero.app.activity.binder;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.widget.Button;

import com.icheero.app.IUserAidl;
import com.icheero.app.R;
import com.icheero.app.model.User;
import com.icheero.sdk.base.BaseActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class UserAidlClientActivity extends BaseActivity
{
    private IUserAidl mUserAidl;

    @BindView(R.id.aidl_button)
    Button mAidlBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_aidl_client);
        doInitView();
        bindService(new Intent(getApplicationContext(), IUserAidl.class), mConnection, BIND_AUTO_CREATE);
    }

    private void doInitView()
    {
        ButterKnife.bind(this);
    }

    private ServiceConnection mConnection = new ServiceConnection()
    {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service)
        {
            mUserAidl = IUserAidl.Stub.asInterface(service);
        }

        @Override
        public void onServiceDisconnected(ComponentName name)
        {
            mUserAidl = null;
        }
    };

    @OnClick(R.id.aidl_button)
    public void onClickEvent()
    {
        try
        {
            mUserAidl.addUser(new User());
        }
        catch (RemoteException e)
        {
            e.printStackTrace();
        }
    }
}

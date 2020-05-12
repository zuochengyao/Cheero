package com.icheero.app.activity.service;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Build;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.view.View;
import android.widget.Button;

import com.icheero.app.IUserAidl;
import com.icheero.app.R;
import com.icheero.app.model.User;
import com.icheero.app.service.UserAidlServerService;
import com.icheero.sdk.base.BaseActivity;
import com.icheero.sdk.util.Log;

import java.util.List;

import androidx.annotation.RequiresApi;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class UserAidlClientActivity extends BaseActivity
{
    private IUserAidl mUserAidl;

    @BindView(R.id.aidl_add_user)
    Button mAddUser;
    @BindView(R.id.aidl_get_list)
    Button mGetList;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_aidl_client);
        doInitView();
    }

    private void doInitView()
    {
        ButterKnife.bind(this);
        Intent intent = new Intent(getApplicationContext(), UserAidlServerService.class);
        intent.setPackage(getPackageName());
        bindService(intent, mConnection, BIND_AUTO_CREATE);
    }

    private ServiceConnection mConnection = new ServiceConnection()
    {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service)
        {
            Log.i(TAG, "onServiceConnected");
            mUserAidl = IUserAidl.Stub.asInterface(service);
        }

        @Override
        public void onServiceDisconnected(ComponentName name)
        {
            Log.i(TAG, "onServiceDisconnected");
            mUserAidl = null;
        }
    };

    @RequiresApi(api = Build.VERSION_CODES.N)
    @OnClick({R.id.aidl_add_user, R.id.aidl_get_list})
    public void onAidlClickEvent(View v)
    {
        try
        {
            switch (v.getId())
            {
                case R.id.aidl_add_user:
                {
                    mUserAidl.addUser(new User());
                    break;
                }
                case R.id.aidl_get_list:
                {
                    List<User> users = mUserAidl.getUserList();
                    if (users != null && users.size() > 0)
                        users.forEach(user -> Log.i(TAG, "user id: " + user.toString()));
                    break;
                }
            }
        }
        catch (RemoteException e)
        {
            e.printStackTrace();
        }
    }
}

package com.icheero.app.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteException;

import com.icheero.app.IUserAidl;
import com.icheero.app.model.User;
import com.icheero.sdk.util.Log;

import java.util.ArrayList;
import java.util.List;

/**
 * AIDL 服务端
 */
public class UserAidlServerService extends Service
{
    private static final Class TAG = UserAidlServerService.class;

    private List<User> mUsers;

    @Override
    public IBinder onBind(Intent intent)
    {
        mUsers = new ArrayList<>();
        Log.i(TAG, "onBind");
        return mIBinder;
    }

    private IBinder mIBinder = new IUserAidl.Stub()
    {
        @Override
        public void addUser(User user) throws RemoteException
        {
            mUsers.add(user);
        }

        @Override
        public List<User> getUserList() throws RemoteException
        {
            return mUsers;
        }
    };

}

package com.icheero.app.service;

import android.os.Binder;
import android.os.RemoteException;

import com.icheero.app.IUserAidl;
import com.icheero.app.model.User;
import com.icheero.sdk.base.BaseService;

import java.util.ArrayList;
import java.util.List;

/**
 * AIDL 服务端
 */
public class UserAidlServerService extends BaseService
{
    private List<User> mUsers;

    @Override
    protected Binder getBinder()
    {
        mUsers = new ArrayList<>();
        return mIBinder;
    }

    private Binder mIBinder = new IUserAidl.Stub()
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

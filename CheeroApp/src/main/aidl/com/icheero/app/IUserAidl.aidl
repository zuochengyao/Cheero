// IUserAidl.aidl
package com.icheero.app;

import com.icheero.app.model.User;

// Declare any non-default types here with import statements

interface IUserAidl {
    void addUser(in User user);

    List<User> getUserList();
}
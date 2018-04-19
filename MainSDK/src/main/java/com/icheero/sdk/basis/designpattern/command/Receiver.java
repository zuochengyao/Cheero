package com.icheero.sdk.basis.designpattern.command;

import com.icheero.sdk.util.Log;

/**
 * 知道如何实施并执行一个与请求相关的操作，任何类都可能成为一个接收者
 * Created by zuochengyao on 2018/3/21.
 */

public class Receiver
{
    public void action()
    {
        Log.i(Receiver.class, "执行请求");
    }
}

package com.icheero.sdk.basis.designpattern.command;

/**
 * 用来声明执行操作的接口
 * Created by zuochengyao on 2018/3/21.
 */

public abstract class Command
{
    protected Receiver mReceiver;

    public Command(Receiver receiver)
    {
        this.mReceiver = receiver;
    }

    public abstract void execute();
}

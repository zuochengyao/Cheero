package com.zcy.sdk.basis.designpattern.command;

/**
 * 将一个接收者对象绑定于一个动作，调用接收者相应的操作
 * Created by zuochengyao on 2018/3/21.
 */

public class ConcreteCommand extends Command
{
    public ConcreteCommand(Receiver receiver)
    {
        super(receiver);
    }

    @Override
    public void execute()
    {
        mReceiver.action();
    }
}

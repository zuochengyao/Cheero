package com.icheero.sdk.knowledge.designpattern.command;

/**
 * 要求该命令执行这个请求
 * Created by zuochengyao on 2018/3/21.
 */

public class Invoker
{
    private Command command;

    public void setCommand(Command command)
    {
        this.command = command;
    }

    public void execute()
    {
        command.execute();
    }
}

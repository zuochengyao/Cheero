package com.icheero.sdk.knowledge.designpattern.responsibility;

/**
 * Created by zuochengyao on 2018/3/21.
 */

public abstract class Manager
{
    protected String name;
    protected Manager leader;

    public Manager(String name)
    {
        this.name = name;
    }

    public void setLeader(Manager leader)
    {
        this.leader = leader;
    }

    public abstract void doRequest(WorkRequest request);

}

package com.icheero.common.knowledge.designpattern.responsibility;

import com.icheero.common.util.Log;

import java.util.Locale;

/**
 * Created by zuochengyao on 2018/3/21.
 */

public class SuperiorLeader extends Manager
{

    public SuperiorLeader(String name)
    {
        super(name);
    }

    @Override
    public void doRequest(WorkRequest request)
    {
        if (WorkRequest.TYPE_HOLIDAY.equals(request.getType()) && request.getNumber() <= 2)
            Log.i(SuperiorLeader.class, String.format(Locale.getDefault(), "%s:%s 数量 %d 批准", name, request.getContent(), request.getNumber()));
        else
            if (leader != null)
                leader.doRequest(request);
    }
}

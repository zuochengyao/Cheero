package com.icheero.sdk.knowledge.designpattern.responsibility;

import com.icheero.util.Log;

import java.util.Locale;

/**
 * Created by zuochengyao on 2018/3/21.
 */

public class GroupLeader extends Manager
{
    public GroupLeader(String name)
    {
        super(name);
    }

    @Override
    public void doRequest(WorkRequest request)
    {
        if (WorkRequest.TYPE_HOLIDAY.equals(request.getType()))
            Log.i(GroupLeader.class, String.format(Locale.CHINA, "%s:%s 数量 %d 被批准", name, request.getContent(), request.getNumber()));
        else if (WorkRequest.TYPE_MONEY.equals(request.getType()))
        {
            if (request.getNumber() <= 500)
                Log.i(GroupLeader.class, String.format(Locale.CHINA, "%s:%s 数量 %d 被批准", name, request.getContent(), request.getNumber()));
            else
                Log.i(GroupLeader.class, String.format(Locale.CHINA, "%s:%s 数量 %d 再说吧", name, request.getContent(), request.getNumber()));
        }
    }
}

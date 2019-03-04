package com.icheero.sdk.knowledge.designpattern.mediator;

import com.icheero.sdk.util.Log;

/**
 * Created by zuochengyao on 2018/3/21.
 */

public class USA extends Country
{
    public USA(UnitedNations mediator)
    {
        super(mediator);
    }

    @Override
    public void declare(String message)
    {
        mediator.declare(message, this);
    }

    @Override
    public void getMessage(String message)
    {
        Log.i(USA.class, "美国获得对方信息：" + message);
    }
}

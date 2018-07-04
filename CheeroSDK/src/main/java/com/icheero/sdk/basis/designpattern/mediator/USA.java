package com.icheero.sdk.basis.designpattern.mediator;

import com.icheero.common.util.Log;

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

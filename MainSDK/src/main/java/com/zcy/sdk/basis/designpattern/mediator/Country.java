package com.zcy.sdk.basis.designpattern.mediator;

/**
 * Created by zuochengyao on 2018/3/21.
 */

public abstract class Country
{
    protected UnitedNations mediator;

    public Country(UnitedNations mediator)
    {
        this.mediator = mediator;
    }

    public abstract void declare(String message);

    public abstract void getMessage(String message);
}

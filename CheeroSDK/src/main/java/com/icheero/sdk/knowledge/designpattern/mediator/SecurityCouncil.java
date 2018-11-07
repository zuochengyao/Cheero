package com.icheero.sdk.knowledge.designpattern.mediator;

/**
 * Created by zuochengyao on 2018/3/21.
 */

public class SecurityCouncil extends UnitedNations
{
    private USA america;
    private Iraq iraq;

    public void setAmerica(USA america)
    {
        this.america = america;
    }

    public void setIraq(Iraq iraq)
    {
        this.iraq = iraq;
    }

    @Override
    public void declare(String message, Country colleague)
    {
        if (colleague == america)
            iraq.getMessage(message);
        else if (colleague == iraq)
            america.getMessage(message);
    }
}

package com.icheero.sdk.knowledge.designpattern.behavioral.visitor.demo;

import com.icheero.sdk.util.Log;

public class Success extends Action
{
    @Override
    public void getConclusionMan(Man man)
    {
        Log.i(Success.class, "Man Success");
    }

    @Override
    public void getConclusionWoman(Woman women)
    {
        Log.i(Success.class, "Woman Success");
    }
}

package com.zcy.sdk.basis.designpattern.visitor.demo;

import com.zcy.sdk.util.Log;

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

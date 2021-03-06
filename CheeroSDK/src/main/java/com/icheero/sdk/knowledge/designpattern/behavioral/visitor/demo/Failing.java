package com.icheero.sdk.knowledge.designpattern.behavioral.visitor.demo;

import com.icheero.sdk.util.Log;

public class Failing extends Action
{
    @Override
    public void getConclusionMan(Man man)
    {
        Log.i(Failing.class, "Man Failing");
    }

    @Override
    public void getConclusionWoman(Woman women)
    {
        Log.i(Failing.class, "Woman Failing");
    }
}

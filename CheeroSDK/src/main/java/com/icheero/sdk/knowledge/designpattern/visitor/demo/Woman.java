package com.icheero.sdk.knowledge.designpattern.visitor.demo;

public class Woman extends Person
{
    @Override
    public void accept(Action visitor)
    {
        visitor.getConclusionWoman(this);
    }
}

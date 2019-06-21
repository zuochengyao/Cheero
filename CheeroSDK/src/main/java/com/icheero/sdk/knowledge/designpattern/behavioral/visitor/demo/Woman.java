package com.icheero.sdk.knowledge.designpattern.behavioral.visitor.demo;

public class Woman extends Person
{
    @Override
    public void accept(Action visitor)
    {
        visitor.getConclusionWoman(this);
    }
}

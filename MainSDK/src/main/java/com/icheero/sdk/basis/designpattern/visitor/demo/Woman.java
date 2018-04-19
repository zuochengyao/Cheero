package com.icheero.sdk.basis.designpattern.visitor.demo;

public class Woman extends Person
{
    @Override
    public void accept(Action visitor)
    {
        visitor.getConclusionWoman(this);
    }
}

package com.icheero.sdk.basis.designpattern.visitor.demo;

public class Man extends Person
{
    @Override
    public void accept(Action visitor)
    {
        visitor.getConclusionMan(this);
    }
}

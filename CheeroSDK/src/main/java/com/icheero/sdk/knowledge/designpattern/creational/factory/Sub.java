package com.icheero.sdk.knowledge.designpattern.creational.factory;

public class Sub extends Operation
{
	public double getResult()
	{
		return getNumberA() - getNumberB();
	}
}

package com.icheero.sdk.knowledge.designpattern.factory;

public class Sub extends Operation
{
	public double getResult()
	{
		return getNumberA() - getNumberB();
	}
}

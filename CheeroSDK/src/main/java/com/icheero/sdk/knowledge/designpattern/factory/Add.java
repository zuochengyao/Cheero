package com.icheero.sdk.knowledge.designpattern.factory;

public class Add extends Operation
{
	public double getResult()
	{
		return getNumberA() + getNumberB();
	}
}

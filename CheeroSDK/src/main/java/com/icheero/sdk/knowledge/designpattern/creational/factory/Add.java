package com.icheero.sdk.knowledge.designpattern.creational.factory;

public class Add extends Operation
{
	public double getResult()
	{
		return getNumberA() + getNumberB();
	}
}

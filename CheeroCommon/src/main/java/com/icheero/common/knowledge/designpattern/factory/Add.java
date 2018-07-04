package com.icheero.common.knowledge.designpattern.factory;

public class Add extends Operation
{
	public double getResult()
	{
		return getNumberA() + getNumberB();
	}
}

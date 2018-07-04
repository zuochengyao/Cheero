package com.icheero.common.knowledge.designpattern.factory;

public class Sub extends Operation
{
	public double getResult()
	{
		return getNumberA() - getNumberB();
	}
}

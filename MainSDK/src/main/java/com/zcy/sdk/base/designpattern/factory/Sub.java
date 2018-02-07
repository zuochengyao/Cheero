package com.zcy.sdk.base.designpattern.factory;

public class Sub extends Operation
{
	public double getResult()
	{
		return getNumberA() - getNumberB();
	}
}

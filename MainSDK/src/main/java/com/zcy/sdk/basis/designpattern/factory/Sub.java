package com.zcy.sdk.basis.designpattern.factory;

public class Sub extends Operation
{
	public double getResult()
	{
		return getNumberA() - getNumberB();
	}
}

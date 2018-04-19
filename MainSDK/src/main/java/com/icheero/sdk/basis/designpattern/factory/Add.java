package com.icheero.sdk.basis.designpattern.factory;

public class Add extends Operation
{
	public double getResult()
	{
		return getNumberA() + getNumberB();
	}
}

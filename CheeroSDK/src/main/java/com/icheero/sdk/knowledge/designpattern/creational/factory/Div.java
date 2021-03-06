package com.icheero.sdk.knowledge.designpattern.creational.factory;

public class Div extends Operation
{
	public double getResult()
	{
		if (getNumberB() == 0)
			throw new ArithmeticException("除数不能为0");
		else
			return getNumberA() / getNumberB();
	}

}

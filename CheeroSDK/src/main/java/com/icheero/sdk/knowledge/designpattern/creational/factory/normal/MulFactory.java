package com.icheero.sdk.knowledge.designpattern.creational.factory.normal;


import com.icheero.sdk.knowledge.designpattern.creational.factory.Mul;
import com.icheero.sdk.knowledge.designpattern.creational.factory.Operation;

public class MulFactory implements IFactory
{

	@Override
	public Operation createOperation()
	{
		return new Mul();
	}

}

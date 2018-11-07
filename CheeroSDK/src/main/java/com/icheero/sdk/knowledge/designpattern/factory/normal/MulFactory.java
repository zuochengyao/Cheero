package com.icheero.sdk.knowledge.designpattern.factory.normal;


import com.icheero.sdk.knowledge.designpattern.factory.Mul;
import com.icheero.sdk.knowledge.designpattern.factory.Operation;

public class MulFactory implements IFactory
{

	@Override
	public Operation createOperation()
	{
		return new Mul();
	}

}

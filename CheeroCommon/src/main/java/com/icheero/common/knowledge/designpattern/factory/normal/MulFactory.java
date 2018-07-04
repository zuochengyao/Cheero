package com.icheero.common.knowledge.designpattern.factory.normal;


import com.icheero.common.knowledge.designpattern.factory.Mul;
import com.icheero.common.knowledge.designpattern.factory.Operation;

public class MulFactory implements IFactory
{

	@Override
	public Operation createOperation()
	{
		return new Mul();
	}

}

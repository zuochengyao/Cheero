package com.icheero.common.knowledge.designpattern.factory.normal;


import com.icheero.common.knowledge.designpattern.factory.Operation;
import com.icheero.common.knowledge.designpattern.factory.Sub;

public class SubFactory implements IFactory
{

	@Override
	public Operation createOperation()
	{
		return new Sub();
	}

}

package com.icheero.sdk.knowledge.designpattern.factory.normal;


import com.icheero.sdk.knowledge.designpattern.factory.Operation;
import com.icheero.sdk.knowledge.designpattern.factory.Sub;

public class SubFactory implements IFactory
{

	@Override
	public Operation createOperation()
	{
		return new Sub();
	}

}

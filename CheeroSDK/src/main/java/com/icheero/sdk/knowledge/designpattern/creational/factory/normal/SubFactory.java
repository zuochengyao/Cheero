package com.icheero.sdk.knowledge.designpattern.creational.factory.normal;


import com.icheero.sdk.knowledge.designpattern.creational.factory.Operation;
import com.icheero.sdk.knowledge.designpattern.creational.factory.Sub;

public class SubFactory implements IFactory
{

	@Override
	public Operation createOperation()
	{
		return new Sub();
	}

}

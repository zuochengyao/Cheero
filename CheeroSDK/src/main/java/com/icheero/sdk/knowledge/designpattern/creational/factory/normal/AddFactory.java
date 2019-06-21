package com.icheero.sdk.knowledge.designpattern.creational.factory.normal;


import com.icheero.sdk.knowledge.designpattern.creational.factory.Add;
import com.icheero.sdk.knowledge.designpattern.creational.factory.Operation;

public class AddFactory implements IFactory
{
	@Override
	public Operation createOperation()
	{
		return new Add();
	}

}

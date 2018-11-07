package com.icheero.sdk.knowledge.designpattern.factory.normal;


import com.icheero.sdk.knowledge.designpattern.factory.Add;
import com.icheero.sdk.knowledge.designpattern.factory.Operation;

public class AddFactory implements IFactory
{
	@Override
	public Operation createOperation()
	{
		return new Add();
	}

}

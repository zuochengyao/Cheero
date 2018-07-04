package com.icheero.common.knowledge.designpattern.factory.normal;


import com.icheero.common.knowledge.designpattern.factory.Add;
import com.icheero.common.knowledge.designpattern.factory.Operation;

public class AddFactory implements IFactory
{
	@Override
	public Operation createOperation()
	{
		return new Add();
	}

}

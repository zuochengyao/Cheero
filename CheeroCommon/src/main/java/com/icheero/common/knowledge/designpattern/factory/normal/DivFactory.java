package com.icheero.common.knowledge.designpattern.factory.normal;


import com.icheero.common.knowledge.designpattern.factory.Div;
import com.icheero.common.knowledge.designpattern.factory.Operation;

public class DivFactory implements IFactory
{

	@Override
	public Operation createOperation()
	{
		return new Div();
	}

}

package com.icheero.sdk.knowledge.designpattern.factory.normal;


import com.icheero.sdk.knowledge.designpattern.factory.Div;
import com.icheero.sdk.knowledge.designpattern.factory.Operation;

public class DivFactory implements IFactory
{
	@Override
	public Operation createOperation()
	{
		return new Div();
	}

}

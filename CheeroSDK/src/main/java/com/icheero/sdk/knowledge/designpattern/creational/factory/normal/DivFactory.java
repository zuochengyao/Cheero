package com.icheero.sdk.knowledge.designpattern.creational.factory.normal;


import com.icheero.sdk.knowledge.designpattern.creational.factory.Div;
import com.icheero.sdk.knowledge.designpattern.creational.factory.Operation;

public class DivFactory implements IFactory
{
	@Override
	public Operation createOperation()
	{
		return new Div();
	}

}

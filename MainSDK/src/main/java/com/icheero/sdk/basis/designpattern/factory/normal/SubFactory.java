package com.icheero.sdk.basis.designpattern.factory.normal;


import com.icheero.sdk.basis.designpattern.factory.Sub;
import com.icheero.sdk.basis.designpattern.factory.Operation;

public class SubFactory implements IFactory
{

	@Override
	public Operation createOperation()
	{
		return new Sub();
	}

}

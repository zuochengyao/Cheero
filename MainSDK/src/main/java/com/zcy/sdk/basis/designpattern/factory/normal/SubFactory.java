package com.zcy.sdk.basis.designpattern.factory.normal;


import com.zcy.sdk.basis.designpattern.factory.Sub;
import com.zcy.sdk.basis.designpattern.factory.Operation;

public class SubFactory implements IFactory
{

	@Override
	public Operation createOperation()
	{
		return new Sub();
	}

}

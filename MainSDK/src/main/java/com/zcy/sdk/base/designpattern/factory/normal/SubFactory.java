package com.zcy.sdk.base.designpattern.factory.normal;

import com.zcy.designpattern.factory.Operation;
import com.zcy.designpattern.factory.Sub;

public class SubFactory implements IFactory
{

	@Override
	public Operation createOperation()
	{
		return new Sub();
	}

}

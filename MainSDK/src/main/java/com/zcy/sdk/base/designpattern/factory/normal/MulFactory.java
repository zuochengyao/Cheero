package com.zcy.sdk.base.designpattern.factory.normal;

import com.zcy.designpattern.factory.Mul;
import com.zcy.designpattern.factory.Operation;

public class MulFactory implements IFactory
{

	@Override
	public Operation createOperation()
	{
		return new Mul();
	}

}

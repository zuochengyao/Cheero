package com.zcy.sdk.basis.designpattern.factory.normal;


import com.zcy.sdk.basis.designpattern.factory.Mul;
import com.zcy.sdk.basis.designpattern.factory.Operation;

public class MulFactory implements IFactory
{

	@Override
	public Operation createOperation()
	{
		return new Mul();
	}

}

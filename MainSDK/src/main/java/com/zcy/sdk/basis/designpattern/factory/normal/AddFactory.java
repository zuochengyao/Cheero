package com.zcy.sdk.basis.designpattern.factory.normal;

import com.zcy.sdk.basis.designpattern.factory.Add;
import com.zcy.sdk.basis.designpattern.factory.Operation;

public class AddFactory implements IFactory
{
	@Override
	public Operation createOperation()
	{
		return new Add();
	}

}

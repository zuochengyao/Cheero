package com.zcy.sdk.base.designpattern.factory.normal;

import com.zcy.designpattern.factory.Add;
import com.zcy.designpattern.factory.Operation;

public class AddFactory implements IFactory
{

	@Override
	public Operation createOperation()
	{
		return new Add();
	}

}

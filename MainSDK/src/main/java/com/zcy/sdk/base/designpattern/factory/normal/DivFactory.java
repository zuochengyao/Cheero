package com.zcy.sdk.base.designpattern.factory.normal;

import com.zcy.designpattern.factory.Div;
import com.zcy.designpattern.factory.Operation;

public class DivFactory implements IFactory
{

	@Override
	public Operation createOperation()
	{
		return new Div();
	}

}

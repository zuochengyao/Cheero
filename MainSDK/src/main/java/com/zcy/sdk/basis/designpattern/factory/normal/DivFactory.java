package com.zcy.sdk.basis.designpattern.factory.normal;

import com.zcy.sdk.basis.designpattern.factory.Div;
import com.zcy.sdk.basis.designpattern.factory.Operation;

public class DivFactory implements IFactory
{

	@Override
	public Operation createOperation()
	{
		return new Div();
	}

}

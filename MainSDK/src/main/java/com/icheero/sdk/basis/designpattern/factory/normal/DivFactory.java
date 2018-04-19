package com.icheero.sdk.basis.designpattern.factory.normal;

import com.icheero.sdk.basis.designpattern.factory.Div;
import com.icheero.sdk.basis.designpattern.factory.Operation;

public class DivFactory implements IFactory
{

	@Override
	public Operation createOperation()
	{
		return new Div();
	}

}

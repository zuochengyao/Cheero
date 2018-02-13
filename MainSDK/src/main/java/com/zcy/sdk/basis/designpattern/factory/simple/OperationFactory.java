package com.zcy.sdk.basis.designpattern.factory.simple;

import com.zcy.designpattern.factory.Add;
import com.zcy.designpattern.factory.Div;
import com.zcy.designpattern.factory.Mul;
import com.zcy.designpattern.factory.Operation;
import com.zcy.designpattern.factory.Sub;

public class OperationFactory
{
	public enum Operate 
	{
		ADD , SUB, MUL, DIV
	}
	
	public static Operation createOperation(String operate)
	{
		Operation operation;
		switch (operate)
		{
			case "+":
			default:
				operation = new Add();
				break;
			case "-":
				operation = new Sub();
				break;
			case "*":
				operation = new Mul();
				break;
			case "/":
				operation = new Div();
				break;
		}
		return operation;
	}
}

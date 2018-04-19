package com.icheero.sdk.basis.designpattern.factory.simple;


import com.icheero.sdk.basis.designpattern.factory.Add;
import com.icheero.sdk.basis.designpattern.factory.Div;
import com.icheero.sdk.basis.designpattern.factory.Mul;
import com.icheero.sdk.basis.designpattern.factory.Sub;
import com.icheero.sdk.basis.designpattern.factory.Operation;

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

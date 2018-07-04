package com.icheero.common.knowledge.designpattern.factory.simple;


import com.icheero.common.knowledge.designpattern.factory.Add;
import com.icheero.common.knowledge.designpattern.factory.Div;
import com.icheero.common.knowledge.designpattern.factory.Mul;
import com.icheero.common.knowledge.designpattern.factory.Operation;
import com.icheero.common.knowledge.designpattern.factory.Sub;

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

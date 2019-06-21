package com.icheero.sdk.knowledge.designpattern.creational.factory.simple;


import com.icheero.sdk.knowledge.designpattern.creational.factory.Add;
import com.icheero.sdk.knowledge.designpattern.creational.factory.Div;
import com.icheero.sdk.knowledge.designpattern.creational.factory.Mul;
import com.icheero.sdk.knowledge.designpattern.creational.factory.Operation;
import com.icheero.sdk.knowledge.designpattern.creational.factory.Sub;


public class OperationFactory
{
	public enum Operate 
	{
		ADD , SUB, MUL, DIV
	}
	
	public static Operation createOperation(Operate operate)
	{
		Operation operation;
		switch (operate)
		{
			case ADD:
			default:
				operation = new Add();
				break;
			case SUB:
				operation = new Sub();
				break;
			case MUL:
				operation = new Mul();
				break;
			case DIV:
				operation = new Div();
				break;
		}
		return operation;
	}
}

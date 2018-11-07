package com.icheero.sdk.knowledge.datastructure.list;

import java.util.Stack;

public class Stack2Queue
{
	Stack<Integer> stack1 = new Stack<>();
	Stack<Integer> stack2 = new Stack<>();
	
	public void inQueue(int x)
	{
		stack1.push(x);
	}
	
	public int deQueue()
	{
		if ((stack1.size() + stack2.size()) != 0)
		{
			if (stack2.isEmpty())
				stack1ToStack2();
			return stack2.pop();
		}
		else
		{
			System.out.println("队列已经为空，不能执行从队头出队");  
            return -1;  
		}
	}
	
	private void stack1ToStack2()
	{
		while(!stack1.isEmpty())
			stack2.push(stack1.pop());
	}
	
	
}

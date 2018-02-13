package com.zcy.sdk.basis.datastructure.list;

import java.util.LinkedList;

public class Queue2Stack
{
	LinkedList<Integer> queue1 = new LinkedList<Integer>();
	LinkedList<Integer> queue2 = new LinkedList<Integer>();

	public void push(int x)
	{
		queue1.addLast(x);
	}

	public int pop()
	{
		if (count() == 0)
		{
			System.out.println("栈已经为空，不能执行弹栈操作");
			return -1;
		}
		else
		{
			if (!queue1.isEmpty())
			{
				toAnotherQueue();
				return queue1.removeFirst();
			}
			else
			// q2 空
			{
				toAnotherQueue();
				return queue2.removeFirst();
			}
		}
	}

	private void toAnotherQueue()
	{
		if (!queue1.isEmpty())
		{
			while (queue1.size() > 1)
				queue2.addLast(queue1.removeFirst());
		}
		else if (!queue2.isEmpty())
		{
			while (queue2.size() > 1)
				queue1.addLast(queue2.removeFirst());
		}
	}

	private int count()
	{
		return queue1.size() + queue2.size();
	}
}

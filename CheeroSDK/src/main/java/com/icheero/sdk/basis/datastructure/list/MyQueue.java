package com.icheero.sdk.basis.datastructure.list;

public class MyQueue<E>
{
	private MyArrayList<E> arrayList = new MyArrayList<E>();
	private int size = 0;

	public E deQueue()
	{
		if (size > 0)
		{
			E e = arrayList.get(0); 
			arrayList.remove(0);
			size--;
			return e;
		}
		else
			throw new RuntimeException("已经到达队列顶部");  
	}

	public void inQueue(E e)
	{
		arrayList.add(e);
		size++;
	}
	
}

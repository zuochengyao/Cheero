package com.icheero.sdk.basis.datastructure.list;

public class MyStack<E>
{
	private MyArrayList<E> arrayList = new MyArrayList<E>();
	private int size = 0;
	
	public E pop()
	{
		E e = arrayList.remove(size - 1);
		size--;
		return e;
	}
	
	public void push(E e)
	{
		arrayList.add(e);
		size++;
	}
	
}

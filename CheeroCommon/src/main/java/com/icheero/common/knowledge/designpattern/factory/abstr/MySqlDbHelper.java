package com.icheero.common.knowledge.designpattern.factory.abstr;

public class MySqlDbHelper<E> implements IDbHelper<E>
{

	@Override
	public void insert(E entity)
	{
		System.out.println("MySql insert username");
	}

	@Override
	public void query(int id)
	{
		System.out.println("MySql query userid = " + id);
	}

}

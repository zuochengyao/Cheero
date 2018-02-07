package com.zcy.sdk.base.designpattern.factory.abstr;

public class MSSqlDbHelper<E> implements IDbHelper<E>
{

	@Override
	public void insert(E entity)
	{
		System.out.println("SQLSERVER insert entity");
	}

	@Override
	public void query(int id)
	{
		System.out.println("SQLSERVER query userid = " + id);
	}
}

package com.icheero.sdk.knowledge.designpattern.factory.abstr;

public interface IDbHelper<E>
{
	void insert(E entity);

	void query(int id);
}

package com.icheero.common.knowledge.datastructure.list;

/**
 * 线性表特征： 
            第一，一个特定的线性表，应该是用来存放特定的某一个类型的元素的（元素的“同一性”）
            第二， 除第一个元素外，其他每一个元素有且仅有一个直接前驱；除最后一个元素外，其他每一个元素有且仅有一个直接后继（元素的“序偶性”）
            第三， 元素在线性表中的“下标”唯一地确定该元素在表中的相对位置（元素的“索引性”）
   自己实现线性表之顺序表
			思路：
                1.顺序表因为采用顺序存储形式，所以内部使用数组来存储数据
                2.因为存储的具体对象类型不一定，所以采用泛型操作
                3.数组操作优点：1.通过指针快速定位到下表，查询快速
                  数组操作缺点：1.数组声明时即需要确定数组大小。当操作中超过容量时，则需要重新声明数组，并且复制当前所有数据
                              2.当需要在中间进行插入或者删除时，则需要移动大量元素（size-index个）
 * @author Yao
 * @param <E>
 */
@SuppressWarnings("unchecked")
public class MyArrayList<E> extends MyList
{
	private static final int LIST_DEFAULT_SIZE = 10;
	// 用来保存此队列中内容的数组
	private Object[] mData;
	// 表示数组大小的指标 
	private int mCapacity;
	
	public MyArrayList()
	{
		this(LIST_DEFAULT_SIZE);
	}
	
	public MyArrayList(int size)
	{
		if (size > 0)
		{
			this.mData = new Object[size];
			this.mSize = 0;
			this.mCapacity = size;
		}
		else
			throw new RuntimeException("数组大小错误:" + size);
	}
	
	public boolean add(E entity)
	{
		ensureCapacity();
		mData[mSize++] = entity;
		return true;
	}
	
	private void ensureCapacity()
	{
		ensureCapacity(mSize);
	}
	
	private void ensureCapacity(int current)
	{
		// 如果已经达到容量，需要重新扩容
		if (this.mCapacity == current)
		{
			this.mCapacity += LIST_DEFAULT_SIZE;
			Object[] newData = new Object[this.mCapacity];
			for (int i = 0; i < current; i++)
				newData[i] = this.mData[i];
			mData = newData;
		}
	}
	
	public boolean add(int index, E entity)
	{
		validateIndex(index);
		ensureCapacity();
		// 临时数组备份index（含）后的数据
		/*
		Object[] tmpData = new Object[this.mCapacity];
		for (int i = 0; i < size(); i++)
		{
			if (i < index)
				tmpData[i] = this.mData[i];
			else if (i == index)
				tmpData[i] = entity;
			else
				tmpData[i] = this.mData[i - 1];
		}
		this.mData = tmpData;*/
		// 改进上面方法，不用循环
		System.arraycopy(this.mData, index, this.mData, index + 1, this.mSize - index);
		mData[index] = entity;
		mSize++;
		return true;
	}
	
	public E get(int index)
	{
		validateIndex(index);
		return (E) mData[index];
	}
	
	public E set(int index, E entity)
	{
		validateIndex(index);
		E oldObject = (E) this.mData[index]; 
		this.mData[index] = entity;
		return oldObject;
	}
	
	public E remove(int index)
	{
		validateIndex(index);
		E e = (E) this.mData[index];
		System.arraycopy(this.mData, index + 1, this.mData, index - 1, this.mSize - 1 - index);
		mData[--mSize] = null;
		return e;
	}
	
	public int size()
	{
		return this.mSize;
	}
}

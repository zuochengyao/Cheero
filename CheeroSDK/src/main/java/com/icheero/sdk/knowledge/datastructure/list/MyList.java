package com.icheero.sdk.knowledge.datastructure.list;

public class MyList
{
	// 保存当前为第几个元素的指标/元素个数
	protected int mSize;
	
	/**
	 * 检测索引是否正确
	 * @param index
	 */
	protected void validateIndex(int index)
	{
		if (index < 0 || index > this.mSize)
			throw new RuntimeException("数组index错误：" + index);
	}
	
	
}

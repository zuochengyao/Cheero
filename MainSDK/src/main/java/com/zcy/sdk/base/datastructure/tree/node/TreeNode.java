package com.zcy.sdk.base.datastructure.tree.node;

public class TreeNode
{
	private Object mData;
	private int mParent;

	public TreeNode(Object data, int parent)
	{
		this.mData = data;
		this.mParent = parent;
	}

	public Object getData()
	{
		return mData;
	}

	public void setData(Object data)
	{
		mData = data;
	}

	public int getParent()
	{
		return mParent;
	}

	public void setParent(int parent)
	{
		mParent = parent;
	}
}

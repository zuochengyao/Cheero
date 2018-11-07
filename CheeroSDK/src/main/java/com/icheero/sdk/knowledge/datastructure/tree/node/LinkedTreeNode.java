package com.icheero.sdk.knowledge.datastructure.tree.node;

public class LinkedTreeNode
{
	private Object mData; // 存储的数据
	private LinkedTreeNode mParent; // 父亲节点的下标
	private LinkedTreeNode mChild; // 孩子节点的引用

	public LinkedTreeNode(Object data, LinkedTreeNode parent)
	{
		mData = data;
		mParent = parent;
	}

	public Object getData()
	{
		return mData;
	}

	public void setData(Object data)
	{
		mData = data;
	}

	public Object getParent()
	{
		return mParent;
	}

	public void setParent(LinkedTreeNode parent)
	{
		mParent = parent;
	}

	public LinkedTreeNode getChild()
	{
		return mChild;
	}

	public void setChild(LinkedTreeNode child)
	{
		mChild = child;
	}
}

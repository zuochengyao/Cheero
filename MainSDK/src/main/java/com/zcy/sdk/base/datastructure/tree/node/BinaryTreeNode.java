package com.zcy.sdk.base.datastructure.tree.node;

public class BinaryTreeNode
{
	private Object mData;
	private BinaryTreeNode mLeftChild;
	private BinaryTreeNode mRightChild;

	public BinaryTreeNode(Object data, BinaryTreeNode left, BinaryTreeNode right)
	{
		this.mData = data;
		this.mLeftChild = left;
		this.mRightChild = right;
	}

	public Object getData()
	{
		return mData;
	}

	public void setData(Object data)
	{
		mData = data;
	}

	public BinaryTreeNode getLeftChild()
	{
		return mLeftChild;
	}

	public void setLeftChild(BinaryTreeNode leftChild)
	{
		mLeftChild = leftChild;
	}

	public BinaryTreeNode getRightChild()
	{
		return mRightChild;
	}

	public void setRightChild(BinaryTreeNode rightChild)
	{
		mRightChild = rightChild;
	}
}

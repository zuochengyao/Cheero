package com.icheero.sdk.knowledge.datastructure.tree;

import com.icheero.sdk.knowledge.datastructure.tree.node.BinaryTreeNode;

import java.util.LinkedList;
import java.util.Stack;

public class BinaryTree
{
	// 根节点
	private BinaryTreeNode mRoot;

	public BinaryTree()
	{
	}

	public BinaryTree(BinaryTreeNode root)
	{
		mRoot = root;
	}

	public BinaryTreeNode getRoot()
	{
		return mRoot;
	}

	public void setRoot(BinaryTreeNode root)
	{
		mRoot = root;
	}

	private void checkTreeEmpty()
	{
		if (mRoot == null)
			throw new IllegalStateException("tree is null");
	}

	// 插入左子树
	public void insertLeftChild(BinaryTreeNode leftChild)
	{
		checkTreeEmpty();
		mRoot.setLeftChild(leftChild);
	}

	// 插入右子树
	public void insertRightChild(BinaryTreeNode rightChild)
	{
		checkTreeEmpty();
		mRoot.setRightChild(rightChild);
	}

	// 删除元素
	public void deleteNode(BinaryTreeNode node)
	{
		checkTreeEmpty();
		if (node == null)
			return;
		deleteNode(node.getLeftChild());
		deleteNode(node.getRightChild());
		node = null;
	}

	public void clear()
	{
		if (mRoot != null)
			deleteNode(mRoot);
	}

	/**
	 * 获取树的高度 ，特殊的获得节点高度
	 */
	public int getTreeHeight()
	{
		return getHeight(mRoot);
	}

	/**
	 * 获取指定节点的度
	 */
	public int getHeight(BinaryTreeNode node)
	{
		if (node == null)
			return 0;
		int leftChildHeight = getHeight(node.getLeftChild());
		int rightChildHeight = getHeight(node.getRightChild());
		return leftChildHeight > rightChildHeight ? leftChildHeight + 1 : rightChildHeight + 1; // 加上自己本身
	}

	public int getSize()
	{
		return getChildSize(mRoot);
	}

	public int getChildSize(BinaryTreeNode node)
	{
		if (node == null)
			return 0;
		int leftChildSize = getChildSize(node.getLeftChild());
		int rightChildSize = getChildSize(node.getRightChild());
		return leftChildSize + rightChildSize + 1;
	}

	// 获取父亲节点
	public BinaryTreeNode getParent(BinaryTreeNode node)
	{
		if (node == null || node == mRoot)
			return null;
		else
			return getParent(mRoot, node);
	}

	public BinaryTreeNode getParent(BinaryTreeNode subTree, BinaryTreeNode node)
	{
		if (subTree == null)
			return null;
		if (subTree.getLeftChild() == node || subTree.getRightChild() == node)
			return subTree;

		BinaryTreeNode parent;
		if ((parent = getParent(subTree.getLeftChild(), node)) != null)
			return parent;
		else
			return getParent(subTree.getRightChild(), node);
	}

	// 先序遍历：先访问根节点、再先序遍历左子树、再先序遍历右子树、退出
	public void recursionFirstOrder(BinaryTreeNode node)
	{
		if (node == null)
			return;
		operate(node);
		recursionFirstOrder(node.getLeftChild());
		recursionFirstOrder(node.getRightChild());
	}
	
	public void iterateFirstOrder(BinaryTreeNode node)
	{
		if (node == null)
			return;
		Stack<BinaryTreeNode> stack = new Stack<>();
		stack.push(mRoot);
		while (!stack.isEmpty())
		{
			BinaryTreeNode cur = stack.pop();
			operate(cur);
			if (cur.getRightChild() != null)
				stack.push(cur.getRightChild());
			if (cur.getLeftChild() != null)
				stack.push(cur.getLeftChild());
		}
	}
	
	// 中序遍历：先中序遍历左子树、再访问根节点、再中序遍历右子树、退出
	public void recursionMediumOrder(BinaryTreeNode node)
	{
		if (node == null)
			return;
		recursionMediumOrder(node.getLeftChild());
		operate(node);
		recursionMediumOrder(node.getRightChild());
	}
	
	public void iterateMediumOrder(BinaryTreeNode node)
	{
		if (node == null)
			return;
		Stack<BinaryTreeNode> stack = new Stack<>();
		BinaryTreeNode cur = node;
		while (!stack.isEmpty() || cur != null)
		{	
			if (cur != null)
			{
				stack.push(cur);
				cur = cur.getLeftChild();
			}
			else 
			{
				cur = stack.pop();
				operate(cur);
				cur = cur.getRightChild();
			}
		}
	}
	
	// 后序遍历：先后序遍历左子树、再后序遍历右子树、最后访问根节点、退出
	public void recursionLastOrder(BinaryTreeNode node)
	{
		if (node == null)
			return;
		recursionLastOrder(node.getLeftChild());
		recursionLastOrder(node.getRightChild());
		operate(node);
	}
	
	public void iterateLastOrder(BinaryTreeNode node)
	{
		if (node == null)
			return;
		Stack<BinaryTreeNode> s = new Stack<>();
		Stack<BinaryTreeNode> output = new Stack<>();
		s.push(node);
		while (!s.isEmpty())
		{
			BinaryTreeNode out = s.pop();
			output.push(out);
			
			if (out.getLeftChild() != null)
				s.push(out.getLeftChild());
			if (out.getRightChild() != null)
				s.push(out.getRightChild());
		}
		
		while (!output.isEmpty())
			operate(output.pop());
	}
	
	public void levelTranversal(BinaryTreeNode node)
	{
		if (node == null)
			return;
		LinkedList<BinaryTreeNode> queue = new LinkedList<>();
		queue.add(node);
		
		while (!queue.isEmpty())
		{
			BinaryTreeNode cur = queue.removeFirst();
			operate(cur);
			if (cur.getLeftChild() != null)
				queue.add(cur.getLeftChild());
			if (cur.getRightChild() != null)
				queue.add(cur.getRightChild());
		}
	}
	
	/**
	 * 模拟操作
	 * 
	 * @param node
	 */
	private void operate(BinaryTreeNode node)
	{
		if (node == null)
			return;
		System.out.println(node.getData());
	}
}

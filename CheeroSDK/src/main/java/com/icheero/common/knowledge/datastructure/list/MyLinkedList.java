package com.icheero.common.knowledge.datastructure.list;



/**
 * 思路：
 * 		1.链表采用链式存储结构，在内部只需要将一个一个结点链接起来。（每个结点中有关于此结点下一个结点的引用）
 * 链表操作优点：
 * 		1.因为每个结点记录下个结点的引用，则在进行插入和删除操作时，只需要改变对应下标下结点的引用即可
 * 缺点：
 * 		1.要得到某个下标的数据，不能通过下标直接得到，需要遍历整个链表。
 * @author Yao
 * @param <E>
 */
public class MyLinkedList<E> extends MyList
{
	// 头结点
	private MyNode<E> mHeader = null;
	private MyNode<E> mLast = null;
	
	public MyLinkedList()
	{
		mHeader = new MyNode<E>(null, null, null);
	}
	
	/**
	 * 在最后插入一个元素
	 * @param e
	 * @return
	 */
	public boolean add(E e)
	{
		if (mSize == 0)
			mHeader.e = e;
		else
		{
			// 创建新的节点，以插入链表
			MyNode<E> newNode = new MyNode<E>(null, e, null);
			// 若尾节点为null，则说明当前链表只有一个头结点
			if (mLast == null)
			{
				mHeader.next = newNode;
				newNode.prev = mHeader;
				mLast = newNode;
			}
			else
			{
				mLast.next = newNode;
				newNode.prev = mLast;
				mLast = newNode;
			}
		}
		mSize++;
		return true;
	}
	
	public boolean add(int index, E e)
	{
		validateIndex(index);
		if (index == mSize)
			add(e);
		else 
		{
			MyNode<E> newNode = new MyNode<E>(null, e, null);
			MyNode<E> preNode = getNode(index - 1);
			newNode.prev = preNode;
			newNode.next = preNode.next;
			preNode.next = newNode;
			mSize++;
		}
		return true;
	}
	
	public E get(int index)
	{
		validateIndex(index);
		if (index == 0)
			return mHeader.e;
		else if (index == mSize)
			return mLast.e;
		else
			return getNode(index).e;
	}
	
	public boolean set(int index, E e)
	{
		validateIndex(index);
		MyNode<E> tmp = getNode(index);
        // E old = tmp.e;
        tmp.e = e;
		return true;
	}
	
	public E remove(int index)
	{
		validateIndex(index);
		MyNode<E> node = getNode(index);
		(node.prev).next = node.next;
		(node.next).prev = node.prev;
		mSize--;
		return node.e;
	}
	
	// 待优化
	private MyNode<E> getNode(int index)
	{
		validateIndex(index);
		MyNode<E> tmpNode = mHeader;
		int count = 0;
		while (count != index)
		{
			tmpNode = tmpNode.next;
			count++;
		}
		return tmpNode;
	}
	
	public int size()
	{
		return this.mSize;
	}
	
	@SuppressWarnings("hiding")
	class MyNode<E>
	{
		E e;
		MyNode<E> next;
		MyNode<E> prev;

		MyNode(MyNode<E> prev, E e, MyNode<E> next)
		{
			this.e = e;
			this.next = next;
			this.prev = prev;
		}
	}
}

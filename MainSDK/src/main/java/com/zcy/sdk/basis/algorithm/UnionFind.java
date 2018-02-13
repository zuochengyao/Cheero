package com.zcy.sdk.basis.algorithm;

/**
 * 我们需要维护2个变量，其中一个变量count表示实时的连通分量数，另一个变量可以用来存储具体每一个点所属的连通分量。
 * 因为不需要存储复杂的信息。这里我们选常用的数组 id[N] 存储即可。 然后，我们需要2个函数find(int x)和union(int p,int
 * q)。 前者返回点“x”所属于的连通分量，后者将p,q两点进行连接。
 * 注意，所谓的连接，其实可以简单的将p的连通分量值赋予q或者将q的连通分量值赋予p，即： id[p]=q 或者id[q]=p。
 */
public class UnionFind
{
	private int count; // 联通分量数
	private int[] id; // 每个数所属的连通分量
	private int[] w; // 每个根节点对应分量的大小
	
	public UnionFind(int N)
	{
		this.count = N;
		this.id = new int[N];
		w = new int[N];
		for (int i = 0; i < N; i++)
		{
			id[i] = i;
			w[i] = 1;
		}
	}

	// 返回连通分量数
	public int getCount()
	{
		return count;
	}

	// 查找x所属的连通分量
	public int find(int x)
	{
		while (x != id[x])
		{
			x = id[x];
		}
		return x;
	}

	// 连接p,q(将q的分量改为p所在的分量)
	public void union(int p, int q)
	{
		int pid = find(p);
		int qid = find(q);
		if (pid == qid)
		{
			return;
		}

		if (w[pid] < w[qid])
		{
			id[pid] = qid;
			w[qid] += w[pid];
		}
		else
		{
			id[qid] = pid;
			w[pid] += w[qid];
		}
		count--;
	}

	// 判断p,q是否连接，即是否属于同一个分量
	public boolean isConnected(int p, int q)
	{
		return find(p) == find(q);
	}
}

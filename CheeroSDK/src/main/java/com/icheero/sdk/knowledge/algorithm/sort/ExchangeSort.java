package com.icheero.sdk.knowledge.algorithm.sort;

public class ExchangeSort
{
	public static int[] BubbleSort(int[] target)
	{
		if (target != null && target.length > 0)
		{
			int n = target.length;
			for (int i = 0; i < n - 1; i++)
			{
				for (int j = n - 1; j > i; j--)
				{
					if(target[j] < target[j-1])
					{
						int temp = target[j];
	                    target[j] = target[j-1];
	                    target[j-1] = temp;
					}
				}
			}
		}
		return target;
	}
	
	public static int[] QuickSort(int[] target)
	{
		if (target != null && target.length > 0)
		{
			quick(target, 0, target.length -1);
		}
		return target;
	}
	
	private static void quick(int[] a, int low, int high)
	{
		if (low < high)
		{
			int mid = getMiddle(a, low, high);
			quick(a, 0, mid - 1);
			quick(a, mid + 1, high);
		}
	}
	
	// 3 28 1 45 6
	private static int getMiddle(int[] a, int low, int high)
	{
		// low = 0 high = 4
		int base = a[low]; // base = 3
		while (low < high)
		{
			while (low < high && a[high] >= base)
				high--;
			a[low] = a[high]; // high = 2 a[high] = 1 = a[low]
			while (low < high && a[low] <= base)
				low++; 
			a[high] = a[low]; // low = 1 a[high] = 28 = a[1]
		}
		a[low] = base;
        return low;
	}
}

package com.icheero.sdk.basis.algorithm.sort;

public class SelectSort
{
	enum SortTypeSelect
    {
        // 直接选择排序
        STRAIGHT,
        // 堆排序
        HEAP
    }
	
	/**
     * Title: 选择排序中的直接选择排序，依赖于初始序列
     * Description: 每一趟 (例如第i趟，i = 0,1,...)在后面第n-i个待排序元素中选出最小元素作为有序序列的第i个元素
     * 时间复杂度：最好情形O(n^2)，平均情形O(n^2)，最差情形O(n^2)
     * 空间复杂度：O(1)
     * 稳定性：不稳定
     * 内部排序(在排序过程中数据元素完全在内存)
     */
    static int[] StraightSelectSort(int[] target)
    {
        if (target != null && target.length > 1)
        {
            for (int i = 0; i < target.length; i++)
            {
                int min = i;
                for (int j = i + 1; j < target.length; j++)
                {
                    if (target[min] > target[j])
                        min = j;
                }
                if (target[min] != target[i])
                {
                    int temp = target[min];
                    target[min] = target[i];
                    target[i] = temp;
                }
            }
        }
        return target;
    }
    
    static int[] HeapSort(int[] target)
    {
    	return target;
    }
}

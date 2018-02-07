package com.zcy.sdk.base.algorithm.sort;


public class InsertSort
{
    enum SortTypeInsertion
    {
        // 直接插入排序
        STRAIGHT,
        // 希尔排序
        SHELL,
        // 折半插入排序
        BINARY
    }

    private static void Swap(int[] target, int indexM, int indexN)
    {
        int temp = target[indexM];
        target[indexM] = target[indexN];
        target[indexN] = temp;
    }

    // 初始顺序 23,1,34,32,5,100,100,2,46

    /**
     * Title: 插入排序中的直接插入排序，依赖于初始序列
     * Description: 在有序序列中不断插入新的记录以达到扩大有序区到整个数组的目的
     * 当插入第i(i>=1)个元素时，前面的V[0],…,V[i-1]等i-1个 元素已经有序。
     * 这时，将第i个元素与前i-1个元素V[i-1]，…，V[0]依次比较，找到插入位置即将V[i]插入，同时原来位置上的元素向后顺移。
     * 在这里，插入位置的查找是顺序查找
     * 时间复杂度：最好情形O(n)，平均情形O(n^2)，最差情形O(n^2)
     * 空间复杂度：O(1)
     * 稳定性：稳定
     * 内部排序(在排序过程中数据元素完全在内存)
     */
    public static int[] StraightInsertionSort(int[] target)
    {
        if (target != null && target.length > 1)
        {
            for (int i = 1; i < target.length; i++)
            {
                for (int j = i; j > 0; j--)
                {
                    if (target[j] < target[j - 1])
                        Swap(target, j, j - 1);
                }
            }
        }
        return target;
    }

    /**
     * Title: 插入排序中的希尔排序，依赖于初始序列
     * Description: 分别对间隔为gap的gap个子序列进行【直接插入排序】，不断缩小gap,直至为 1
     * 刚开始时，gap较大，每个子序列元素较少，排序速度较快；
     * 待到排序后期，gap变小，每个子序列元素较多，但大部分元素基本有序，所以排序速度仍较快。
     * 时间复杂度：O(n) ~ O(n^2)
     * 空间复杂度：O(1)
     * 稳定性：不稳定
     * 内部排序(在排序过程中数据元素完全在内存)
     */
    public static int[] ShellInsertionSort(int[] target)
    {
        if (target != null && target.length > 1)
        {
            int gap = target.length;
            while (gap > 1)
            {
                gap = gap / 2;
                for (int i = gap; i < target.length; i++)
                {
                    int j = i - gap; // 同组的前一个index
                    if (target[i] < target[j]) // 小于同组的前一个index
                    {
                        int temp = target[i];         // 待插入值
                        do
                        {
                            target[j + gap] = target[j];         // 后移元素
                            j = j - gap;          // 再比较前一个元素
                        }
                        while (j >= 0 && target[j] > temp);  // 向前比较的终止条件
                        	target[j + gap] = temp;         // 将待插入值插入合适的位置
                    }
                }
            }
        }
        return target;
    }

    /**
     * Title: 插入排序中的折半插入排序，依赖于初始序列
     * Description: 折半搜索出插入位置，并直接插入;与直接插入搜索的区别是，后者的搜索要快于顺序搜索
     * 时间复杂度：折半插入排序比直接插入排序明显减少了关键字之间的比较次数，但是移动次数是没有改变。所以，
     * 折半插入排序和插入排序的时间复杂度相同都是O（N^2），在减少了比较次数方面它确实相当优秀，所以该算法仍然比直接插入排序好。
     * 空间复杂度：O(1)
     * 稳定性：稳定
     * 内部排序(在排序过程中数据元素完全在内存)
     */
    public static int[] BinaryInsertionSort(int[] target)
    {
        if (target != null && target.length > 1)
        {
            for (int i = 1; i < target.length; i++)
            {
                int left = 0;
                int right = i - 1;
                int mid;
                int temp = target[i];
                if (temp < target[right])
                {
                    while (left <= right)
                    {
                        mid = (left + right) / 2;
                        if (temp < target[mid])
                            right = mid - 1;
                        else if (temp > target[mid])
                            left = mid + 1;
                        else
                            left += 1;
                    }
                    // left及其后面的数据顺序向后移动，并在left位置插入
                    for (int j = i; j > left; j--)
                    {
                        target[j] = target[j - 1];
                    }
                    target[left] = temp;
                }
            }
        }
        return target;
    }
}

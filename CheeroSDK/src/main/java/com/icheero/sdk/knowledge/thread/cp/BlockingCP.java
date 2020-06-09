package com.icheero.sdk.knowledge.thread.cp;

import java.util.PriorityQueue;

/**
 * 第一种方式：
 * 使用Object.wait()、Object.notify() 和 非阻塞队列 实现
 */
public class BlockingCP
{
    private int mQueueSize = 10;
    private final PriorityQueue<Integer> mQueue = new PriorityQueue<>(mQueueSize);

    public class Consumer extends Thread
    {
        @Override
        public void run()
        {
            while (true)
            {
                synchronized (mQueue)
                {
                    while (mQueue.size() == 0)
                    {
                        try
                        {
                            System.out.println("队列空，等待数据");
                            mQueue.wait();
                        }
                        catch (InterruptedException e)
                        {
                            mQueue.notify();
                        }
                    }
                    mQueue.poll();
                    mQueue.notify();
                }
            }
        }
    }

    public class Producer extends Thread
    {
        @Override
        public void run()
        {
            while (true)
            {
                synchronized (mQueue)
                {
                    while (mQueue.size() == mQueueSize)
                    {
                        try
                        {
                            System.out.println("队列满，等待空间");
                            mQueue.wait();
                        }
                        catch (InterruptedException e)
                        {
                            mQueue.notify();
                        }
                    }
                    mQueue.offer(1);
                    mQueue.notify();
                }
            }
        }
    }
}

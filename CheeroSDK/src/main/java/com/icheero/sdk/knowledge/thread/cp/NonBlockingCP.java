package com.icheero.sdk.knowledge.thread.cp;

import java.util.concurrent.ArrayBlockingQueue;

public class NonBlockingCP
{
    private int mQueueSize = 10;
    private ArrayBlockingQueue<Integer> mQueue = new ArrayBlockingQueue<>(mQueueSize);

    public class Consumer extends Thread
    {
        @Override
        public void run()
        {
            while (true)
            {
                try
                {
                    mQueue.take();
                }
                catch (InterruptedException e)
                {
                    Thread.currentThread().interrupt();
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
                try
                {
                    mQueue.put(1);
                }
                catch (InterruptedException e)
                {
                    e.printStackTrace();
                }
            }
        }
    }
}

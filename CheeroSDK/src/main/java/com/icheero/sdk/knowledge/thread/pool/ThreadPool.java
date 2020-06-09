package com.icheero.sdk.knowledge.thread.pool;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class ThreadPool
{
    private static final int CPU_COUNT = Runtime.getRuntime().availableProcessors();
    private static final int SIZE_CORE = 2;
    private static final int SIZE_MAX = 4;
    private static final int TIME_OUT = 60;

    public static void main(String[] args)
    {
//        executorsTest();
//        stopThreadByFlag();
//        stopThread();
    }

    private static void executorsTest()
    {
        LinkedBlockingDeque<Runnable> linkedQueue = new LinkedBlockingDeque<>();
        ArrayBlockingQueue<Runnable> arrayQueue = new ArrayBlockingQueue<Runnable>(10);
        ThreadPoolExecutor executor = new ThreadPoolExecutor(SIZE_CORE, SIZE_MAX, TIME_OUT, TimeUnit.MILLISECONDS, arrayQueue);
        for (int i = 0; i < 10; i++)
        {
            final int index = i;
            executor.execute(() -> {
                try
                {
                    Thread.sleep(100);
                }
                catch (InterruptedException e)
                {
                    e.printStackTrace();
                }
                System.out.println("index " + index + " queue size = " + arrayQueue.size());
            });
        }
    }

    private static void stopThread()
    {
        MyRunnable runnable = new MyRunnable();
        Thread thread = new Thread(runnable);
        thread.start();
        try
        {
            Thread.sleep(1000);
        }
        catch (InterruptedException e)
        {
            e.printStackTrace();
        }

        runnable.flag = false;
        thread.interrupt();
    }

    static class MyRunnable implements Runnable
    {
        private boolean flag = true;
        @Override
        public void run()
        {
            // 终止线程时，一般通过两个标记判断
            while (flag && !Thread.interrupted())
            {
                try
                {
                    Thread.sleep(200);
                }
                catch (InterruptedException e)
                {
                    e.printStackTrace();
                }
                System.out.println("running");
            }
        }
    }
}

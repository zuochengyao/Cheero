package com.icheero.sdk.knowledge.thread;

import com.icheero.sdk.knowledge.thread.cp.BlockingCP;
import com.icheero.sdk.knowledge.thread.cp.NonBlockingCP;

import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class ThreadMain
{
    public static void main(String[] args) throws Exception
    {
        // doInterruptThread();
        // blockingCP();
        //        nonBlockingCP();
        //        doJoinThread();
        //        doShareData();
        nonBlockingCP();
        doDeadLock();
    }

    // region 测试线程函数

    private static void doInterruptThread() throws InterruptedException
    {
        class MoonRunner implements Runnable
        {
            private long i;

            @Override
            public void run()
            {
                Lock lock = new ReentrantLock();
                lock.lock();
                try
                {
                    while (!Thread.currentThread().isInterrupted())
                    {
                        System.out.println(Thread.currentThread().getName() + ":" + (++i));
                    }
                }
                catch (Exception e)
                {
                    Thread.currentThread().interrupt();
                    return;
                }
                finally
                {
                    lock.unlock();
                }
                System.out.println(Thread.currentThread().getName() + ": Stop");
            }
        }
        MoonRunner runner1 = new MoonRunner();
        Thread thread1 = new Thread(runner1, "MoonThread1");
        Thread thread2 = new Thread(runner1, "MoonThread2");
        thread1.start();
        thread2.start();
        TimeUnit.MILLISECONDS.sleep(10);
        thread1.interrupt();
        thread2.interrupt();
    }

    private static void doJoinThread()
    {
        System.out.println(Thread.currentThread().getName() + "主线程运行开始!");
        class ThreadJoin extends Thread
        {
            private String mName;

            private ThreadJoin(String name)
            {
                this.mName = name;
            }

            @Override
            public void run()
            {
                for (int i = 0; i < 5; i++)
                {
                    System.out.println("子线程" + mName + "运行 : " + i);
                    try
                    {
                        sleep((int) (Math.random() * 100));
                    }
                    catch (InterruptedException e)
                    {
                        e.printStackTrace();
                    }
                }
                System.out.println(Thread.currentThread().getName() + " 线程运行结束!");
            }
        }
        ThreadJoin t1 = new ThreadJoin("A");
        ThreadJoin t2 = new ThreadJoin("B");
        t1.start();
        t2.start();
        try
        {
            t1.join();
            t2.join();
        }
        catch (InterruptedException e)
        {
            e.printStackTrace();
        }
        System.out.println(Thread.currentThread().getName() + "主线程运行结束!");
    }

    private static void doSyncThread()
    {
        class MyRunnable implements Runnable
        {
            private String name;
            private final Object obj;

            MyRunnable(String name, Object obj)
            {
                this.name = name;
                this.obj = obj;
            }

            @Override
            public void run()
            {
                int count = 5;
                while (count > 0)
                {
                    synchronized (obj)
                    {
                        System.out.println(name);
                        count--;
                        notify();
                    }
                    try
                    {
                        obj.wait();
                    }
                    catch (InterruptedException e)
                    {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    private static void doShareData()
    {
        class MyRunnable implements Runnable
        {
            private int count = 100;
            private final Object lock = new Object();
            private boolean flag = true;

            @Override
            public void run()
            {
                while (flag)
                {
                    synchronized (lock)
                    {
                        if (count > 0)
                        {
                            try
                            {
                                Thread.sleep(10);
                            }
                            catch (InterruptedException e)
                            {
                                e.printStackTrace();
                            }
                            System.out.println(Thread.currentThread().getName() + " count: " + count--);
                        }
                        else
                        {
                            flag = false;
                        }
                    }
                }
            }
        }

        MyRunnable runnable = new MyRunnable();
        Thread t1 = new Thread(runnable);
        Thread t2 = new Thread(runnable);
        Thread t3 = new Thread(runnable);
        Thread t4 = new Thread(runnable);
        t1.start();
        t2.start();
        t3.start();
        t4.start();
    }

    private static void doDeadLock()
    {
        Thread t1 = new Thread(new MyRunnable(true));
        Thread t2 = new Thread(new MyRunnable(false));
        t1.start();
        t2.start();
    }

    // endregion

    // region 生产者-消费者

    private static void blockingCP()
    {
        BlockingCP blockingCP = new BlockingCP();
        BlockingCP.Consumer consumer = blockingCP.new Consumer();
        BlockingCP.Producer producer = blockingCP.new Producer();
        consumer.start();
        producer.start();
    }

    private static void nonBlockingCP()
    {
        NonBlockingCP nonBlockingCP = new NonBlockingCP();
        NonBlockingCP.Consumer consumer = nonBlockingCP.new Consumer();
        NonBlockingCP.Producer producer = nonBlockingCP.new Producer();
        consumer.start();
        producer.start();
    }

    // endregion

    // region 线程池

    private static void doThreadPool()
    {
        Executors.newFixedThreadPool(10);
    }

    // endregion
}

class LockObj
{
    static final Object lockA = new Object();
    static final Object lockB = new Object();
}

class MyRunnable implements Runnable
{
    private boolean flag;

    MyRunnable(boolean flag)
    {
        this.flag = flag;
    }

    @Override
    public void run()
    {
        if (flag)
        {
            while (true)
            {
                synchronized (LockObj.lockA)
                {
                    System.out.println("A");
                    synchronized (LockObj.lockB)
                    {
                        System.out.println("B");
                    }
                }
            }
        }
        else
        {
            while (true)
            {
                synchronized (LockObj.lockB)
                {
                    System.out.println("C");
                    synchronized (LockObj.lockA)
                    {
                        System.out.println("D");
                    }
                }
            }
        }
    }
}

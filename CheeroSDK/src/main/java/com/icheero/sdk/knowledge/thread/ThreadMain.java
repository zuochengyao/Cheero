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
        // doStopThread();
        // blockingCP();
        nonBlockingCP();
    }

    // region 测试线程终止

    private static void doStopThread() throws InterruptedException
    {
        MoonRunner runner1 = new MoonRunner();
        Thread thread1 = new Thread(runner1, "MoonThread1");
        Thread thread2 = new Thread(runner1, "MoonThread2");
        thread1.start();
        thread2.start();
        TimeUnit.MILLISECONDS.sleep(10);
        thread1.interrupt();
        thread2.interrupt();
    }

    private static class MoonRunner implements Runnable
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
            }
            finally
            {
                lock.unlock();
            }
            System.out.println(Thread.currentThread().getName() + ": Stop");
        }
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

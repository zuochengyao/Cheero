package com.icheero.sdk.knowledge.thread;

public class Deadlock {
    public static Object objA = new Object();
    public static Object objB = new Object();

    public static void doDeadlock() {
        LockA lockA = new LockA();
        new Thread(lockA).start();
        LockB lockB = new LockB();
        new Thread(lockB).start();
    }
}

class LockA implements Runnable {
    @Override
    public void run() {
        try {
            while (true) {
                synchronized (Deadlock.objA) {
                    Thread.sleep(3000);
                    synchronized (Deadlock.objB) {
                        Thread.sleep(3000);
                    }
                }
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}

class LockB implements Runnable {
    @Override
    public void run() {
        try {
            while (true) {
                synchronized (Deadlock.objB) {
                    Thread.sleep(3000);
                    synchronized (Deadlock.objA) {
                        Thread.sleep(3000);
                    }
                }
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
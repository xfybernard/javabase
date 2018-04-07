package com.xfy.bernard.thread;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;


public class TestMyLock {

	static class SleepUtils {
    public static final void second(long seconds) {
        try {
            TimeUnit.SECONDS.sleep(seconds);
        } catch (InterruptedException e) {
        	}
    	}
	}

	
    public void test() {
        final Lock lock = new MyLock();  //自定义独占锁
    //	  final Lock lock = new MyShareLock();	//自定义共享锁
    	
        class Worker extends Thread {
            public void run() {
                while (true) {
                    lock.lock();
                    try {
                        SleepUtils.second(1);
                        System.out.println(Thread.currentThread().getName());
                        SleepUtils.second(1);
                    } finally {
                        lock.unlock();
                    }
                    SleepUtils.second(2);
                }
            }
        }
        
        for (int i = 0; i < 10; i++) {
            Worker w = new Worker();
            //w.setDaemon(true);
            w.start();
        }
        
        for (int i = 0; i < 10; i++) {
            SleepUtils.second(3);
            System.out.println();
        }
    }

    public static void main(String[] args) {
        TestMyLock testMyLock = new TestMyLock();
        testMyLock.test();
    }
}

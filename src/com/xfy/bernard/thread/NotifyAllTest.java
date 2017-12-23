package com.xfy.bernard.thread;

public class NotifyAllTest {
	private static Object obj = new Object();

	public static void main(String[] args) {
		NotifyAllTest na = new NotifyAllTest();
		new Thread(na.new ThreadA()).start();
		new Thread(na.new ThreadA()).start();
		new Thread(na.new ThreadA()).start();
		try {
			System.out.println(Thread.currentThread().getName() + " sleep(2000)");
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		synchronized (obj) {
			// System.out.println(Thread.currentThread().getName() + " notify
			// one wait thread on obj");
			// obj.notify(); 只能随机唤醒等待obj对象锁的其中一个线程
			System.out.println(Thread.currentThread().getName() + " notify all wait thread on obj");
			obj.notifyAll(); // 唤醒等待obj对象锁的所有线程
		}
	}

	class ThreadA implements Runnable {

		@Override
		public void run() {
			synchronized (obj) {
				System.out.println(Thread.currentThread().getName() + " wait");
				try {
					obj.wait();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				System.out.println(Thread.currentThread().getName() + " continue");
			}
		}
	}
}

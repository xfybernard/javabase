package com.xfy.bernard.thread;

/**
 * ���߳���Ҫ��ThreadA����
 * 
 * @author Administrator
 *
 */
public class WaitTest {
	class ThreadA extends Thread {

		@Override
		public void run() {
			synchronized (this) {
				try {
					System.out.println("ThreadA:" + this);
					Thread.sleep(5000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				System.out.println(Thread.currentThread().getName() + " call main thread notify()");
				notify(); // ���ѵȴ��ö��������߳�
			}
		}
	}

	public static void main(String[] args) {
		ThreadA t1 = new WaitTest().new ThreadA();
		synchronized (t1) {
			System.out.println("main:" + t1);
			System.out.println(Thread.currentThread().getName() + " start thread");
			t1.start();
			try {
				System.out.println(Thread.currentThread().getName() + " wait ....");
				t1.wait();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			System.out.println(Thread.currentThread().getName() + " continue ");
		}
	}
}

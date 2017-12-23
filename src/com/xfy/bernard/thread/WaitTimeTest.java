package com.xfy.bernard.thread;

/**
 * 主线程wait3秒后,自动进入就绪状态等待cpu执行
 * 
 * @author Administrator
 *
 */
public class WaitTimeTest {
	class ThreadA extends Thread {

		@Override
		public void run() {
			System.out.println(Thread.currentThread().getName() + " is running...");
			while (true) {
			}
		}

	}

	public static void main(String[] args) {
		ThreadA t1 = new WaitTimeTest().new ThreadA();
		synchronized (t1) {
			System.out.println(Thread.currentThread().getName() + " start thread");
			t1.start();
			System.out.println(Thread.currentThread().getName() + " wait 3s...");
			try {
				t1.wait(); // 等待其它线程唤醒,自己不会醒来
				// t1.wait(3000); // 3秒后，自己唤醒
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			System.out.println(Thread.currentThread().getName() + " continue ");
		}
	}
}

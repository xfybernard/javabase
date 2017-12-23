package com.xfy.bernard.thread;

/**
 * ���߳�wait3���,�Զ��������״̬�ȴ�cpuִ��
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
				t1.wait(); // �ȴ������̻߳���,�Լ���������
				// t1.wait(3000); // 3����Լ�����
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			System.out.println(Thread.currentThread().getName() + " continue ");
		}
	}
}

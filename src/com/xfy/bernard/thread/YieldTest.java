package com.xfy.bernard.thread;

public class YieldTest {
	private Object obj = new Object();

	public static void main(String[] args) {
		YieldTest yt = new YieldTest();
		yt.new ThreadA("t1").start();
		yt.new ThreadA("t2").start();
	}

	class ThreadA extends Thread {
		public ThreadA(String threadName) {
			super(threadName);
		}

		@Override
		public void run() {
			System.out.println(Thread.currentThread().getName() + " ������obj������....");
			synchronized (obj) {
				System.out.println(Thread.currentThread().getName() + " ���obj������....");
				for (int i = 0; i < 10; i++) {
					System.out.println(String.format("%s:%d", Thread.currentThread().getName(), i));
					if (i % 4 == 0) {
						Thread.yield(); // ��Ȼ��ǰ�߳��ó���cpu,������δ�ͷ�obj������
					}
				}
			}
		}
	}

}

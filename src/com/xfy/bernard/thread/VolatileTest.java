package com.xfy.bernard.thread;

public class VolatileTest {
	
	static class VolatileThread extends Thread{
		private volatile int a;

		@Override
		public void run() {
			a = a +1 ;
			System.out.println(Thread.currentThread().getName()+":" + a);
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			a = a +1;
			System.out.println(Thread.currentThread().getName()+":" + a);
		}
		
		
		public static void main(String[] args) {
			VolatileThread vt = new VolatileThread();
			Thread t1 = new Thread(vt);
			Thread t2 = new Thread(vt);
			Thread t3 = new Thread(vt);
			Thread t4 = new Thread(vt);
			t1.start();
			t2.start();
			t3.start();
			t4.start();
		}
	}
}

package com.xfy.bernard.thread;

import java.util.Random;

public class ThreadInterrupt {

	public static void main(String[] args) throws InterruptedException {
		CThread thread = new CThread();
		thread.start();
		Thread.sleep(new Random().nextInt(5) * 1000);
		thread.cancel();
	}

	static class CThread extends Thread {

		private volatile boolean on = true;

		@Override
		public synchronized void run() {
			try {
				while (on && !isInterrupted()) {
					System.out.println(getId() + "正在睡觉中。。。");
					// Thread.sleep(1000);
					wait();
				}
			} catch (InterruptedException e) {
				e.printStackTrace();
				System.out.println(getId() + " ,sleep线程被中断");
				interrupt();
			}
			System.out.println(getId() + " tmd谁把我吵醒了....");
		}

		public synchronized void cancel() {
			on = false;
			System.out.println(getId() + " 中止sleep线程");
			interrupt();
		}
	}
}

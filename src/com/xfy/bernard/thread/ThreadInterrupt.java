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
					System.out.println(getId() + "����˯���С�����");
					// Thread.sleep(1000);
					wait();
				}
			} catch (InterruptedException e) {
				e.printStackTrace();
				System.out.println(getId() + " ,sleep�̱߳��ж�");
				interrupt();
			}
			System.out.println(getId() + " tmd˭���ҳ�����....");
		}

		public synchronized void cancel() {
			on = false;
			System.out.println(getId() + " ��ֹsleep�߳�");
			interrupt();
		}
	}
}

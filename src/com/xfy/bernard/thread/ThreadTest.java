package com.xfy.bernard.thread;

import java.text.SimpleDateFormat;
import java.util.Date;

public class ThreadTest {
	private Boolean finished = false;
	private Integer count = 0;
	private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

	public static void main(String[] args) {
		ThreadTest t = new ThreadTest();
		t.new AThread().start();
		t.new BThread().start();
	}

	class AThread extends Thread {
		@Override
		public void run() {
			while (!finished) {
				if (count >= 100) {
					try {
						System.out.println(
								Thread.currentThread().getName() + " ִ��ʱ�� " + sdf.format(new Date()) + " ��ʼ�ȴ���");
						long t = System.currentTimeMillis();
						synchronized (finished) {
							finished.wait();
						}
						System.out.println(Thread.currentThread().getName() + " ִ��ʱ�� " + sdf.format(new Date()) + " �ȴ���"
								+ (System.currentTimeMillis() - t));
					} catch (Exception e) {
						e.printStackTrace();
					}

				} else {
					System.out.println(
							Thread.currentThread().getName() + " ִ��ʱ�� " + sdf.format(new Date()) + "\t" + (count++));
				}
			}
		}
	}

	class BThread extends Thread {

		@Override
		public void run() {
			try {
				Thread.sleep(3000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			System.out.println(Thread.currentThread().getName() + " ִ��ʱ�� " + sdf.format(new Date()) + " ��Ҫ����A�߳�");
			synchronized (finished) {
				finished.notifyAll();
				finished = true;
			}
		}
	}
}

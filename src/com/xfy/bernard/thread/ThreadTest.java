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
								Thread.currentThread().getName() + " 执行时间 " + sdf.format(new Date()) + " 开始等待了");
						long t = System.currentTimeMillis();
						synchronized (finished) {
							finished.wait();
						}
						System.out.println(Thread.currentThread().getName() + " 执行时间 " + sdf.format(new Date()) + " 等待了"
								+ (System.currentTimeMillis() - t));
					} catch (Exception e) {
						e.printStackTrace();
					}

				} else {
					System.out.println(
							Thread.currentThread().getName() + " 执行时间 " + sdf.format(new Date()) + "\t" + (count++));
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
			System.out.println(Thread.currentThread().getName() + " 执行时间 " + sdf.format(new Date()) + " 我要唤醒A线程");
			synchronized (finished) {
				finished.notifyAll();
				finished = true;
			}
		}
	}
}

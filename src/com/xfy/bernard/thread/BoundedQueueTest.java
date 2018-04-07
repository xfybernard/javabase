package com.xfy.bernard.thread;

import java.util.Random;
import java.util.UUID;

public class BoundedQueueTest {

//	private static BoundedQueue<String> queue = new BoundedQueue<>(100);
	private static BoundedQueueLC<String> queue = new BoundedQueueLC<>(5);

	private static Random rdm = new Random();
	
	static class PushThread extends Thread {

		@Override
		public void run() {
			try {
				while (true) {
					Thread.sleep(rdm.nextInt(50));
					String uuid = UUID.randomUUID().toString(); 
					queue.push(uuid);
					System.out.println("线程" + getName() + ",put数据:" + uuid);
				}
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	static class PollThread extends Thread {

		@Override
		public void run() {
			try {
				while (true) {
					System.out.println("线程" + getName() + ",poll数据:" + queue.poll());
					Thread.sleep(rdm.nextInt(5000));
				}
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	public static void main(String[] args) {
		new PushThread().start();
		for (int i = 0; i < 1; i++) {
			new PollThread().start();
		}
	}
}

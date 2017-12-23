package com.xfy.bernard.thread;

public class BoundedQueueTest {

	private static BoundedQueue<String> queue = new BoundedQueue<>(100);

	static class PushThread extends Thread {

		@Override
		public void run() {
			try {
				while (true) {
					Thread.sleep(300);
					for (int i = 0; i < 100; i++) {
						String value = String.valueOf(System.currentTimeMillis() + i);
						queue.push(value);
					}
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
					System.out.println(getName() + "\t,pollÊý¾Ý:" + queue.poll());
					Thread.sleep(2000);
				}
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	public static void main(String[] args) {
		new PushThread().start();
		for (int i = 0; i < 2; i++) {
			new PollThread().start();
		}
	}
}

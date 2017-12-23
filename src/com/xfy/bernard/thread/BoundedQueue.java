package com.xfy.bernard.thread;

import java.util.LinkedList;

public class BoundedQueue<T> {
	private int queueSize = 16;

	public BoundedQueue(int queueSize) {
		this.queueSize = queueSize;
	}

	private LinkedList<T> dataList = new LinkedList<T>();

	public synchronized void push(T data) throws InterruptedException {
		// 如果当前队列已满,则等待入队
		while (dataList.size() == queueSize) {
			System.out.println(Thread.currentThread().getName() + ": 队列已满,等待数据出队...");
			wait();
		}
		// 如果dataList为空时,需要唤醒在其等待的出队线程
		if (dataList.size() == 0) {
			notifyAll();
		}
		dataList.add(data);
	}

	public synchronized T poll() throws InterruptedException {
		// 若队列为空时,则等待出队
		while (dataList.size() == 0) {
			System.out.println(Thread.currentThread().getName() + ": 队列已空,等待数据入队...");
			wait();
		}
		// 当队列满时,需要唤醒在其等待的入队线程
		if (dataList.size() == queueSize) {
			notifyAll();
		}
		return dataList.remove();
	}
}

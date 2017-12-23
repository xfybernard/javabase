package com.xfy.bernard.thread;

import java.util.LinkedList;

public class BoundedQueue<T> {
	private int queueSize = 16;

	public BoundedQueue(int queueSize) {
		this.queueSize = queueSize;
	}

	private LinkedList<T> dataList = new LinkedList<T>();

	public synchronized void push(T data) throws InterruptedException {
		// �����ǰ��������,��ȴ����
		while (dataList.size() == queueSize) {
			System.out.println(Thread.currentThread().getName() + ": ��������,�ȴ����ݳ���...");
			wait();
		}
		// ���dataListΪ��ʱ,��Ҫ��������ȴ��ĳ����߳�
		if (dataList.size() == 0) {
			notifyAll();
		}
		dataList.add(data);
	}

	public synchronized T poll() throws InterruptedException {
		// ������Ϊ��ʱ,��ȴ�����
		while (dataList.size() == 0) {
			System.out.println(Thread.currentThread().getName() + ": �����ѿ�,�ȴ��������...");
			wait();
		}
		// ��������ʱ,��Ҫ��������ȴ�������߳�
		if (dataList.size() == queueSize) {
			notifyAll();
		}
		return dataList.remove();
	}
}

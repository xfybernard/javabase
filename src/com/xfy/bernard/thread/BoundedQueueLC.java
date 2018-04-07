package com.xfy.bernard.thread;

import java.util.LinkedList;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class BoundedQueueLC<T> {
	private int queueSize = 16;
	
	private Lock lock = new ReentrantLock();
	
	private Condition full = lock.newCondition();
	
	private Condition empty = lock.newCondition();
	

	public BoundedQueueLC(int queueSize) {
		this.queueSize = queueSize;
	}

	private LinkedList<T> dataList = new LinkedList<T>();
	
	public  void push(T data) throws InterruptedException{
		lock.lock();
		try{
			while (dataList.size()==queueSize){
				full.await();
			}
			dataList.add(data);
			empty.signal();
		}finally{
			lock.unlock();
		}
	}
	
	public  T poll() throws InterruptedException{
		lock.lock();
		try{
			while (dataList.size()==0){
				empty.await();
			}
			full.signal();
			return dataList.removeFirst();
		}finally{
			lock.unlock();
		}
	}
}

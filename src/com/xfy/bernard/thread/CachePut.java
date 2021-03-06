package com.xfy.bernard.thread;

import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.DelayQueue;
import java.util.concurrent.Delayed;

public class CachePut<T extends Delayed> implements Runnable {
	private DelayQueue<T> cache;
	private CopyOnWriteArrayList<T> waitAddElement = new CopyOnWriteArrayList<>();

	public CachePut(DelayQueue<T> cache) {
		this.cache = cache;
	}
	
	public void addData(T data){
		waitAddElement.add(data);
	}

	@Override
	public void run() {
		for (T obj : waitAddElement) {
			System.out.println(obj);
			cache.put(obj);
		}
	}
}

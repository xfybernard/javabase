package com.xfy.bernard.thread;

import java.util.concurrent.DelayQueue;
import java.util.concurrent.Delayed;

public class CachePut<T extends Delayed> implements Runnable {
	private DelayQueue<T> cache;
	private T[] waitAddElement;

	public CachePut(DelayQueue<T> cache, T[] waitAddElement) {
		this.cache = cache;
		this.waitAddElement = waitAddElement;
	}

	@Override
	public void run() {
		for (T obj : waitAddElement) {
			System.out.println(obj);
			cache.offer(obj);
		}
	}
}

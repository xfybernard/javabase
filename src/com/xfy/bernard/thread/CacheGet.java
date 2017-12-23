package com.xfy.bernard.thread;

import java.util.concurrent.DelayQueue;
import java.util.concurrent.Delayed;

public class CacheGet<T extends Delayed> implements Runnable {

	private DelayQueue<T> cache;

	public CacheGet(DelayQueue<T> cache) {
		this.cache = cache;
	}

	@Override
	public void run() {
		while (true) {
			try {
				T element = this.cache.take();
				if (element instanceof Cache) {
					Cache cache = (Cache) element;
					System.out.println(Thread.currentThread().getId() + " GetFromCache " + cache);
				}
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}

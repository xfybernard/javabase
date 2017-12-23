package com.xfy.bernard.string;

import java.util.concurrent.Callable;
import java.util.concurrent.CancellationException;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.FutureTask;

public class Memoizer<K, V> implements Computable<K, V> {

	private final ConcurrentHashMap<K, Future<V>> cache = new ConcurrentHashMap<>();

	private final Computable<K, V> c;

	public Memoizer(Computable<K, V> c) {
		this.c = c;
	}

	@Override
	public V compute(K arg) throws InterruptedException {
		Future<V> result = cache.get(arg);
		if (result == null) {
			Callable<V> callable = new Callable<V>() {
				@Override
				public V call() throws Exception {
					return c.compute(arg);
				}
			};
			FutureTask<V> task = new FutureTask<>(callable);
			result = cache.putIfAbsent(arg, task);
			if (result == null) {
				result = task;
				task.run();
			}
		}
		try {
			return result.get();
		} catch (CancellationException e1) {
			cache.remove(arg, result);
		} catch (ExecutionException e) {
			e.printStackTrace();
		}
		return null;
	}

}

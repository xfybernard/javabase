package com.xfy.bernard.thread;

import java.util.concurrent.Delayed;
import java.util.concurrent.TimeUnit;

/**
 * Delayed 队列的case实现
 * 
 * @author Administrator
 *
 * @param <T>
 */
public class Cache<T> implements Delayed {
	private String id;
	private String name;
	private Long activeTime; // cache有效时间
	private T data;

	public Cache(String id, String name, T data, Long activeTime) {
		this.id = id;
		this.name = name;
		this.data = data;
		this.activeTime = TimeUnit.NANOSECONDS.convert(activeTime, TimeUnit.MILLISECONDS);
		this.activeTime += System.nanoTime();
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public T getData() {
		return data;
	}

	public void setData(T data) {
		this.data = data;
	}

	@Override
	public String toString() {
		return "Cache [id=" + id + ", name=" + name + ", activeTime=" + activeTime + ", data=" + data + "]";
	}

	@Override
	public long getDelay(TimeUnit unit) {
		return unit.convert(this.activeTime - System.nanoTime(), TimeUnit.NANOSECONDS);
	}

	@Override
	public int compareTo(Delayed o) {
		Long r = getDelay(TimeUnit.NANOSECONDS) - o.getDelay(TimeUnit.NANOSECONDS);
		return (r == 0) ? 0 : (r > 0) ? 1 : -1;
	}

}

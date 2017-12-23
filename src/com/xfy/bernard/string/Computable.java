package com.xfy.bernard.string;

public interface Computable<K, V> {
	public V compute(K arg) throws InterruptedException;
}

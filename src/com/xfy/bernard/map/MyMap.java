package com.xfy.bernard.map;

public interface MyMap<K, V> {
	public V put(K key, V value);

	public V get(K key);

	public Integer size();

	interface Entry<K, V> {
		K getKey();

		V getValue();
	}
}

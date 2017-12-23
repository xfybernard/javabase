package com.xfy.bernard.map;

public class MyHashMap<K, V> implements MyMap<K, V> {

	private static int defaultLength = 1 << 4;

	private int size = 0;

	private static float loadFactor = 0.75f;

	private Entry<K, V> entryAry[] = null;

	public MyHashMap(int _size, float _loadFactor) {
		this.defaultLength = _size;
		this.loadFactor = _loadFactor;
		this.entryAry = new Entry[_size];
	}

	public MyHashMap() {
		this(defaultLength, loadFactor);
	}

	private int hash(K key) {
		int m = defaultLength - 1;
		return key.hashCode() % m;
	}

	@Override
	public V put(K key, V value) {
		// map扩容
		if (size > defaultLength * loadFactor) {
			Entry<K, V> tempAry[] = new Entry[defaultLength * 2];
			System.arraycopy(entryAry, 0, tempAry, 0, entryAry.length);
			entryAry = tempAry;
			System.out.println("MyHashMap长度已扩容至:" + entryAry.length);
			defaultLength = entryAry.length;
		}
		int idx = hash(key);
		Entry<K, V> entry = entryAry[idx];
		if (entry == null) {
			entryAry[idx] = new Entry<K, V>(key, value, null, idx);
			size++;
		} else {
			entryAry[idx] = new Entry<K, V>(key, value, entry, idx);
			size++;
		}
		return entryAry[idx].getValue();
	}

	@Override
	public V get(K key) {
		int idx = hash(key);
		Entry<K, V> entry = entryAry[idx];
		return entry != null ? entry.getValue() : null;
	}

	@Override
	public Integer size() {
		return size;
	}

	class Entry<K, V> implements MyMap.Entry<K, V> {
		K key;
		V value;
		Entry<K, V> next;
		int index;

		Entry(K key, V value, Entry<K, V> next, int index) {
			this.key = key;
			this.value = value;
			this.next = next;
			this.index = index;
		}

		@Override
		public K getKey() {
			return key;
		}

		@Override
		public V getValue() {
			return value;
		}
	}
}

package com.xfy.bernard.map;

public class MyMapTest {
	public static void main(String[] args) {
		MyMap<Integer, String> map = new MyHashMap<>();
		for (int i = 0; i < 100; i++) {
			map.put(i, String.valueOf(i));
			System.out.println(map.get(i));
		}
		System.out.println("map当前元素个数:" + map.size());
	}
}

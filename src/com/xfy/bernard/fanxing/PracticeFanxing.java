package com.xfy.bernard.fanxing;

import java.util.Arrays;
import java.util.List;
import java.util.ListIterator;

public class PracticeFanxing {

	public static <T> T transfer(Object obj) {
		return (T) obj;
	}

	public static <T> T[] copy(List<T> list, T[] dest) {
		ListIterator<T> itr = list.listIterator();
		int idx = 0;
		while (itr.hasNext()) {
			dest[idx++] = itr.next();
		}
		for (T ele : dest) {
			System.out.println(ele);
		}
		return dest;
	}

	public static void main(String[] args) {
		Object obj = "abc";
		String x = transfer(obj);
		System.out.println(x);

		List<Integer> list = Arrays.asList(12, 11, 3);
		Integer ary[] = new Integer[list.size()];
		copy(list, ary);

		List<String> list2 = Arrays.asList("hello", "bernard");
		String ary2[] = new String[list2.size()];
		copy(list2, ary2);
	}
}

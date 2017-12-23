package com.xfy.bernard.fanxing;

import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;

/**
 * 集合泛型
 * 
 * @author Administrator
 *
 */
public class ListTest {
	public static void main(String[] args) throws IllegalAccessException, IllegalArgumentException,
			InvocationTargetException, NoSuchMethodException, SecurityException {
		List<Integer> mylist = new ArrayList<>();
		mylist.add(1);
		mylist.add(2);
		for (int i = 3; i < 10; i++) {
			mylist.add(i);
		}
		System.out.println(mylist.size() + "\t" + mylist);
		// 通过反射向mylist增加数字型值
		List.class.getMethod("add", Object.class).invoke(mylist, "abc");
		System.out.println(mylist.get(mylist.size() - 1));
		printCollection(Arrays.asList("abc", 1, 2, new HashMap<String, String>()));
		printCollection2(Arrays.asList("edf", 2, 2.3f));
		printExtendsCollection(Arrays.asList(1.1f, 3, BigDecimal.valueOf(20)));
		printSupperCollection(Arrays.asList("abc", 123, Boolean.TRUE));
		copy(Arrays.asList(1, 2, 3), new ArrayList<Number>(10));

		// 增加任意类型到集合中 可以用extends限定类型
		// addToList("abc");
		addToList(1323);
		addToList((byte) (12));
		// addToList(true);

		// 交换数据中元素位置
		String[] strAry = swap(new String[] { "hello ,", "bernard ,", " today is new day!!!" }, 0, 1);
		System.out.println(strAry[0] + strAry[1] + strAry[2]);

		System.out.println(divid(10, 2));
		// System.out.println(divid(10, 0)); // 抛出异常 除数为0
		System.out.println(divid(null, 10)); // 抛出异常 除数或被除数为空
	}

	public static void printCollection(Collection<?> collections) {
		for (Object o : collections) {
			System.out.println("printCollection:" + o + "\t classType:" + o.getClass().getName());
		}
	}

	public static <T> void printCollection2(Collection<T> collections) {
		for (T o : collections) {
			System.out.println("printCollection2:" + o + "\t classType:" + o.getClass().getName());
		}
	}

	// 集合里面只能放Number及Number的子类
	public static void printExtendsCollection(Collection<? extends Number> collections) {
		for (Number number : collections) {
			System.out.println("printExtendsCollection : " + number + "\t classType:" + number.getClass().getName());
		}
		// 重新定义集合
		collections = new LinkedList<Float>();
	}

	// 集合里面只能放Integer或Integer的父类
	public static void printSupperCollection(Collection<? super Integer> collections) {
		for (Object o : collections) {
			System.out.println("printSupperCollection:" + o + "\t classType:" + o.getClass().getName());
		}
		// 重新定义集合
		collections = new ArrayList<Number>();
		collections = new ArrayList<Object>();
		collections.add(3);
	}

	/**
	 * 从源集合复到元素到目标集合
	 * 
	 * @param src
	 * @param dest
	 * @return
	 */
	public static List<? super Integer> copy(List<? extends Integer> src, List<? super Integer> dest) {
		ListIterator<? extends Integer> itr = src.listIterator();
		while (itr.hasNext()) {
			dest.add(itr.next());
		}
		System.out.println(dest);
		return dest;
	}

	/**
	 * 增加元素到集合
	 * 
	 * @param element
	 * @return
	 */
	public static <T extends Number & Comparable<T>> List<T> addToList(T element) {
		List<T> list = new ArrayList<T>();
		list.add(element);
		System.out.println("addToList :" + list + " classType:" + element.getClass().getName());
		return list;
	}

	/**
	 * 交换任意集合的元素位置
	 * 
	 * @param ary
	 * @param i
	 * @param j
	 */
	public static <T> T[] swap(T[] ary, int i, int j) {
		T tmp = ary[i];
		ary[i] = ary[j];
		ary[j] = tmp;
		return ary;
	}

	@SuppressWarnings("unchecked")
	public static <E extends Exception, T extends Number> T divid(T t1, T t2) throws E {
		if (t1 == null || t2 == null) {
			throw new NullPointerException("除数和被除数均不能为空");
		}
		if (t2.intValue() == 0) {
			throw new RuntimeException("除数不能为0");
		}
		return (T) BigDecimal.valueOf(t1.doubleValue() / t2.doubleValue());
	}

}

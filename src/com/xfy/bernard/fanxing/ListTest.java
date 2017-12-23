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
 * ���Ϸ���
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
		// ͨ��������mylist����������ֵ
		List.class.getMethod("add", Object.class).invoke(mylist, "abc");
		System.out.println(mylist.get(mylist.size() - 1));
		printCollection(Arrays.asList("abc", 1, 2, new HashMap<String, String>()));
		printCollection2(Arrays.asList("edf", 2, 2.3f));
		printExtendsCollection(Arrays.asList(1.1f, 3, BigDecimal.valueOf(20)));
		printSupperCollection(Arrays.asList("abc", 123, Boolean.TRUE));
		copy(Arrays.asList(1, 2, 3), new ArrayList<Number>(10));

		// �����������͵������� ������extends�޶�����
		// addToList("abc");
		addToList(1323);
		addToList((byte) (12));
		// addToList(true);

		// ����������Ԫ��λ��
		String[] strAry = swap(new String[] { "hello ,", "bernard ,", " today is new day!!!" }, 0, 1);
		System.out.println(strAry[0] + strAry[1] + strAry[2]);

		System.out.println(divid(10, 2));
		// System.out.println(divid(10, 0)); // �׳��쳣 ����Ϊ0
		System.out.println(divid(null, 10)); // �׳��쳣 �����򱻳���Ϊ��
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

	// ��������ֻ�ܷ�Number��Number������
	public static void printExtendsCollection(Collection<? extends Number> collections) {
		for (Number number : collections) {
			System.out.println("printExtendsCollection : " + number + "\t classType:" + number.getClass().getName());
		}
		// ���¶��弯��
		collections = new LinkedList<Float>();
	}

	// ��������ֻ�ܷ�Integer��Integer�ĸ���
	public static void printSupperCollection(Collection<? super Integer> collections) {
		for (Object o : collections) {
			System.out.println("printSupperCollection:" + o + "\t classType:" + o.getClass().getName());
		}
		// ���¶��弯��
		collections = new ArrayList<Number>();
		collections = new ArrayList<Object>();
		collections.add(3);
	}

	/**
	 * ��Դ���ϸ���Ԫ�ص�Ŀ�꼯��
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
	 * ����Ԫ�ص�����
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
	 * �������⼯�ϵ�Ԫ��λ��
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
			throw new NullPointerException("�����ͱ�����������Ϊ��");
		}
		if (t2.intValue() == 0) {
			throw new RuntimeException("��������Ϊ0");
		}
		return (T) BigDecimal.valueOf(t1.doubleValue() / t2.doubleValue());
	}

}

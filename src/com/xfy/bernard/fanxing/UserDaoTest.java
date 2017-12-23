package com.xfy.bernard.fanxing;

import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;
import java.util.Map;

public class UserDaoTest {
	private List<String> list;
	private Map<String, Object> map;

	public static void main(String[] args) throws NoSuchFieldException, SecurityException, NoSuchMethodException {
		UserDao userDao = new UserDao();
		User u = new User("张三", 30);
		userDao.save(u);
		userDao.get(u.getId());
		userDao.delete(u.getId());
		testList();
		testMap();

		// 获得方法的泛型参数
		Method method = UserDaoTest.class.getMethod("printList", List.class);
		Type[] types = method.getGenericParameterTypes();
		ParameterizedType ptype = (ParameterizedType) types[0];
		System.out.println(ptype.getRawType());
		for (Type type : ptype.getActualTypeArguments()) {
			System.out.println(type);
		}

	}

	public static void printList(List<?> list) {
		for (Object o : list) {
			System.out.println(o);
		}
	}

	/***
	 * 获取List中的泛型
	 */
	public static void testList() throws NoSuchFieldException, SecurityException {
		Type t = UserDaoTest.class.getDeclaredField("list").getGenericType();
		System.out.println("testList typeName:" + t.getTypeName());
		if (ParameterizedType.class.isAssignableFrom(t.getClass())) {
			for (Type t1 : ((ParameterizedType) t).getActualTypeArguments()) {
				System.out.print(t1 + ",");
			}
			System.out.println();
		}
	}

	/***
	 * 获取Map中的泛型
	 */
	public static void testMap() throws NoSuchFieldException, SecurityException {
		Type t = UserDaoTest.class.getDeclaredField("map").getGenericType();
		System.out.println("testMap typeName:" + t.getTypeName());
		if (ParameterizedType.class.isAssignableFrom(t.getClass())) {
			for (Type t1 : ((ParameterizedType) t).getActualTypeArguments()) {
				System.out.print(t1 + ",");
			}
			System.out.println();
		}
	}

}

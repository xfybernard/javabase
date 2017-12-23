package com.xfy.bernard.classloader;

import java.util.ArrayList;

public class MyTest {
	public static void main(String[] args)
			throws ClassNotFoundException, InstantiationException, IllegalAccessException {
		ClassLoader clr = MyTest.class.getClassLoader();
		while (clr != null) {
			System.out.println(clr.getClass().getName());
			clr = clr.getParent();
		}
		System.out.println(System.class.getClassLoader());

		System.out.println(MyTest.class.getResource("").getPath());

		// System.out.println(new MyList<String>().toString());
		// 通过AppClassLoader加载失败,字节码文件已经加密

		String classDir = MyTest.class.getResource("").getPath();
		MyClassloader myloader = new MyClassloader(classDir);
		Class clazz = myloader.loadClass("MyList");
		ArrayList myList = (ArrayList) clazz.newInstance();
		System.out.println(myList);

	}
}

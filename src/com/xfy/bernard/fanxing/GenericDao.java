package com.xfy.bernard.fanxing;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;

/**
 * ���ڷ������ͨ��dao
 * 
 * @author Administrator
 *
 * @param <T>
 * @param <PK>
 */
public abstract class GenericDao<T extends Entity<PK>, PK extends Serializable> {

	private Class<T> clazz; // ����ķ�������

	public GenericDao() {
		clazz = (Class<T>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
	}

	public int save(T entity) {
		System.out.println("save " + clazz.getName());
		return 1;
	}

	public T get(PK id) {
		System.out.println("get " + clazz.getName());
		try {
			return clazz.newInstance();
		} catch (InstantiationException | IllegalAccessException e) {
			e.printStackTrace();
		}
		return null;
	}

	public void delete(PK id) {
		System.out.println("delete " + clazz.getName());
	}
}

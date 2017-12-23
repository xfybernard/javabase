package com.xfy.bernard.thread;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.DelayQueue;
import java.util.concurrent.Delayed;

/**
 * 延时队列的demo 测试
 * 
 * @author Administrator
 *
 */
public class CacheTest {

	public static void main(String[] args) throws InterruptedException {
		DelayQueue<Cache<User>> queue = new DelayQueue<>();
		Cache<User> u1 = new Cache<>("1", "Bernard", new User("Bernard"), 3000l);
		Cache<User> u2 = new Cache<>("2", "Yjwxfpl", new User("Yjwxfpl"), 10000l);
		List<Cache<User>> list = new ArrayList<>();
		list.add(u1);
		list.add(u2);
		CachePut<Delayed> putThread = new CachePut(queue, list.toArray(new Cache[0]));
		CacheGet<Delayed> getThread = new CacheGet(queue);
		new Thread(putThread).start();
		new Thread(getThread).start();
	}
}

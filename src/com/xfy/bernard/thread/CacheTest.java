package com.xfy.bernard.thread;

import java.util.Random;
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
		
		CachePut<Delayed> putThread = new CachePut(queue);
		
		Thread t = new Thread(){
			@Override
			public void run() {
				Random rdm = new Random();
				for(int i=0;i<20;i++){
					String idx = String.valueOf(i);
					String userName = "User"+idx;
					Cache<User> data = new Cache<>(idx, userName, new User(userName), new Long(rdm.nextInt(5000000)));
					putThread.addData(data);
				}
			}
		};
		t.start();
		t.join();
		
		CacheGet<Delayed> getThread = new CacheGet(queue);
		new Thread(putThread).start();
		new Thread(getThread).start();
	}
}

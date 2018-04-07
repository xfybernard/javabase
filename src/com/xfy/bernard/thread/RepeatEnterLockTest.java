package com.xfy.bernard.thread;

import java.util.concurrent.locks.Lock;

public class RepeatEnterLockTest {
	private Lock lock = new MyLock();

	private int count = 15;
	
	private int needRelase = 0;
	
	//测试可重入锁
	public void testLock(){
		if (count==0){
			System.out.println("准备释放锁....");
			//释放重入锁
			for(int i=0;i<needRelase;i++){
				lock.unlock();
			}
			return;
		}
		lock.lock();
		count --;
		needRelase ++;
		testLock();
	}
	
	public static void main(String[] args) {
		new RepeatEnterLockTest().testLock();
	}
}


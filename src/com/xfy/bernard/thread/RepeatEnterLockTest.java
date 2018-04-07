package com.xfy.bernard.thread;

import java.util.concurrent.locks.Lock;

public class RepeatEnterLockTest {
	private Lock lock = new MyLock();

	private int count = 15;
	
	private int needRelase = 0;
	
	//���Կ�������
	public void testLock(){
		if (count==0){
			System.out.println("׼���ͷ���....");
			//�ͷ�������
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


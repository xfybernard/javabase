package com.xfy.bernard.thread.practice.aim;

/**
 * 模拟实际业务耗时
 */
public class BusiMock {

	public static void buisness(int sleepTime) {
		try {
			Thread.sleep(sleepTime);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

}

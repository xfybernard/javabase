package com.xfy.bernard.thread;

import java.util.Random;
import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;

public class ComputTask implements Callable<Integer> {
	private String taskName;
	public  ComputTask(String taskName){
		this.taskName = taskName;
		System.out.println(taskName+" 任务创建成功");
	}
	
	@Override
	public Integer call() throws Exception {
		System.out.println(taskName+" 任务开始执行");
		Random rdm = new Random();
		int sum = 0;
		int sleepTime = rdm.nextInt(10);
		for(int i=0;i<100;i++){
			sum += i;
		}
		try {
			TimeUnit.SECONDS.sleep(sleepTime);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		System.out.println(taskName+" 任务执行完毕,sleepTime:" + sleepTime);
		return sum;
	}
	
	
	static class ComputRun implements Runnable{
		private String taskName;
		public  ComputRun(String taskName){
			this.taskName = taskName;
			System.out.println(taskName+" 任务创建成功");
		}
		@Override
		public void run() {
			System.out.println(taskName+" 任务开始执行");
			Random rdm = new Random();
			int sum = 0;
			int sleepTime = rdm.nextInt(10);
			for(int i=0;i<100;i++){
				sum += i;
			}
			try {
				TimeUnit.SECONDS.sleep(sleepTime);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			System.out.println(taskName+" 任务执行完毕,sleepTime:" + sleepTime);
		}
		
	}
}

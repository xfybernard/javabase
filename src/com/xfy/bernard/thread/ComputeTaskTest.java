package com.xfy.bernard.thread;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletionService;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorCompletionService;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.FutureTask;
import java.util.concurrent.TimeoutException;

public class ComputeTaskTest {
	
	public void run1() throws InterruptedException, ExecutionException{
		ExecutorService executor = Executors.newFixedThreadPool(1);
		List<FutureTask<Integer>> taskList = new ArrayList<>();
		for(int i=0;i<10;i++){
			FutureTask<Integer> task = new FutureTask<>(new ComputTask("task"+i));
			//Integer xx= 100;
			//FutureTask<Integer> task = new FutureTask<>(new ComputRun("task"+i),xx);
			taskList.add(task);
			executor.submit(task);
		}
		
		System.out.println(Thread.currentThread().getName()+"已提交任务,做自己的事情");
		int total = 0;
		for(FutureTask<Integer> task:taskList){
			total  = total+task.get();
		}
		System.out.println(Thread.currentThread().getName()+"结果:" +total);
		executor.shutdown();
	}
	
	
	public void run2() throws InterruptedException, ExecutionException {
		ExecutorService executor = Executors.newFixedThreadPool(5);
		CompletionService<Integer> service = new ExecutorCompletionService<>(executor);
		for(int i=0;i<10;i++){
			service.submit(new ComputTask("task"+i));
		}
		
		int total = 0;
		for(int i=0;i<10;i++){
			Future<Integer> future = service.take();
			System.out.println(future.get()+"\t" + future.isDone());
			total = total + future.get();
		}
		System.out.println(Thread.currentThread().getName()+"结果:" +total);
		executor.shutdown();
	}
	
	public static void main(String[] args) throws InterruptedException, ExecutionException, TimeoutException {
	//	new ComputeTaskTest().run2();
		new ComputeTaskTest().run1();
	}
}

package com.xfy.bernard.thread;

public class CutThread extends Thread{
	
	private Thread cut;
	
	public CutThread(Thread t,String name){
		super(name);
		cut = t;
	}
	
	@Override
	public void run() {
		try {
			cut.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		System.out.println(Thread.currentThread().getName()+" will work");
	}
	
	public static void main(String[] args) {
		Thread cutt = Thread.currentThread();
		for(int i=1;i<=5;i++){
			Thread td = new CutThread(cutt,String.valueOf(i));
			System.out.println(cutt.getName()+" insert before " + td.getName());
			td.start();
			cutt = td;
		}
		System.out.println("main finished");
	}
}

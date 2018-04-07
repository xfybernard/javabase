package com.xfy.bernard.thread;

import java.util.concurrent.atomic.AtomicStampedReference;

public class AtomicReference {
	
	private static final AtomicStampedReference<User> userRef = new AtomicStampedReference<User>(new User("u0"), 0);
	
	static class ChangeName1 extends Thread{
		private  int stamp ;
		private User u ;
		public ChangeName1(int stamp,User u){
			this.stamp = stamp;
			this.u = u;
		}
		@Override
		public void run() {
			System.out.println(Thread.currentThread().getName()+"---" +stamp+"----"+u);
			User u1 = new User("u1 change by ChangeName1");
			boolean result = userRef.compareAndSet(u, u1, stamp, stamp+1);
			System.out.println("----------"+ result);
		}
	}
	
	static class ChangeName2 extends Thread{
		private  int stamp ;
		private User u;
		public ChangeName2(int stamp,User u){
			this.stamp = stamp;
			this.u = u;
		}
		@Override
		public void run() {
			System.out.println(Thread.currentThread().getName()+"---" +stamp+"----"+u);
			User u2 = new User("u2 change by ChangeName2");
			boolean result = userRef.compareAndSet(u, u2, stamp, stamp+1);
			System.out.println("----------"+ result);
		}
	}
	
	public static void main(String[] args) throws InterruptedException{
		int stamp = userRef.getStamp();
		User user = userRef.getReference();
		System.out.println("init stamp:" + stamp+",init value :" +user);
		Thread t1 =new ChangeName1(stamp,user);
		Thread t2 = new ChangeName2(stamp,user);
		t1.start();
		t1.join();
		t2.start();
		t2.join();
		System.out.println("after  stamp:" + userRef.getStamp()+",after value :" +userRef.getReference());
	}
	
}

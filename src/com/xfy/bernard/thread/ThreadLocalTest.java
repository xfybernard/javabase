package com.xfy.bernard.thread;

import java.io.IOException;

public class ThreadLocalTest {
	
	static class Resources{
		public static ThreadLocal<String> resource1 = new ThreadLocal<String>();
		public static ThreadLocal<String> resource2 = new ThreadLocal<String>();
		
		public static void remove(){
			resource1.remove();
			resource2.remove();
		}
	}
	
	static class SetResource{
		public void setOne(String value){
			Resources.resource1.set(value);
		}
		
		public void setTwo(String value){
			Resources.resource2.set(value);
		}
	}
	
	static class GetResource{
		public void display(){
			System.out.println(Resources.resource1.get()+":"+Resources.resource2.get());
		}
	}
	
	
	public static void main(String[] args) throws IOException {
		final SetResource a = new SetResource();
		final GetResource b = new GetResource();
		for(int i=0;i<10;i++){
			final String value1 = "Ïß³Ì-" + i;
			final String value2  ="thread-" + i;
			Thread t=  new Thread(){
				@Override
				public void run() {
					try{
						a.setOne(value1);
						a.setTwo(value2);
						b.display();
					}finally{
						Resources.remove();
					}
				}
			};
			t.start();
		}
	}
}

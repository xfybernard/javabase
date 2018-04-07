package com.xfy.bernard.thread;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicInteger;

public class ConnectionPoolTest {
	
	static final int threadCount = 50;
	
	static final int count = 10;
	
	static final ConnectionPool pool = new ConnectionPool(20);
	
	static final CountDownLatch begin = new CountDownLatch(1);
	
	static final CountDownLatch end = new CountDownLatch(threadCount);
	static AtomicInteger success = new AtomicInteger();
	static AtomicInteger failure = new AtomicInteger();
	static final long timeOut = 500;
	
	static class Client extends Thread{
		@Override
		public void run() {
			try {
				begin.await();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			for(int i = 0;i<count;i++){
				try {
					Connection connection = pool.fetchConnection(timeOut);
					if (connection!=null){
						try{
							connection.createStatement();
							connection.commit();
						}finally{
							pool.relaseConnection(connection);
							success.incrementAndGet();
						}
					}else{
						failure.incrementAndGet();
						System.err.println(Thread.currentThread().getName()+"在指定时间" + timeOut+ "内未获取到连接!!!");
					}
				} catch (InterruptedException | SQLException e) {
					e.printStackTrace();
				}
			}
			end.countDown();
		}
	}
	
	public static void main(String[] args) throws InterruptedException {
		for(int i=0;i<threadCount;i++){
			new Client().start();
		}
		begin.countDown();
		end.await();
		System.out.println("total request :" + threadCount*count);
		System.out.println("get connection success  :" + success);
		System.err.println(" get connection  failure:" + failure);
	}
}	

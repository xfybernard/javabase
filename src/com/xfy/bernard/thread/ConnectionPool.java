package com.xfy.bernard.thread;

import java.sql.Connection;
import java.util.LinkedList;

public class ConnectionPool {
	private int defaultSize = 10;
	private LinkedList<Connection> connectionList = new LinkedList<>();

	public ConnectionPool(int initionSize) {
		if (initionSize < 0) {
			initionSize = defaultSize;
		}
		for (int i = 0; i < initionSize; i++) {
			connectionList.add(ConnectionDriver.getConnection());
		}
	}
	
	/**
	 * 释放连接
	 * @param connection
	 */
	public void relaseConnection(Connection connection){
		if (connection!=null){
			synchronized (connectionList) {
				connectionList.addLast(connection);
				connectionList.notifyAll();
			}
		}
	}
	
	public Connection fetchConnection(long millis) throws InterruptedException{
		synchronized (connectionList) {
			if (millis<=0){
				return connectionList.isEmpty()?null:connectionList.removeFirst();
			}else{
				long future = System.currentTimeMillis()+millis;	// 具体超时时间
				long remain = millis;	//还剩多久超时
				while(connectionList.isEmpty() && remain>0){
					connectionList.wait(remain);
					remain = future - System.currentTimeMillis();
				}
				return connectionList.isEmpty()?null:connectionList.removeFirst();
			}
		}
	}

}

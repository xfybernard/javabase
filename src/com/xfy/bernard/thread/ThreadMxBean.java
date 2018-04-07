package com.xfy.bernard.thread;

import java.lang.management.ManagementFactory;
import java.lang.management.ThreadInfo;
import java.lang.management.ThreadMXBean;

public class ThreadMxBean {
	public static void main(String[] args) {
		ThreadMXBean mx = ManagementFactory.getThreadMXBean();
		ThreadInfo threads [] = mx.dumpAllThreads(false, false);
		for(ThreadInfo info:threads){
			System.out.println(info.getThreadId()+"\t" +info.getThreadName()+"\t"+info.getThreadState().name());
		}
	}
}

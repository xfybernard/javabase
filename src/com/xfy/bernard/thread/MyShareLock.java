package com.xfy.bernard.thread;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.AbstractQueuedSynchronizer;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;

public class MyShareLock implements Lock{
	
	class Sync extends AbstractQueuedSynchronizer{

		Sync(int count){
			setState(count);
		}
		
		@Override
		protected int tryAcquireShared(int arg) {
			 for(;;){
	                int current = getState();
	                int newCount = current - arg;
	                if(newCount<0|| compareAndSetState(current,newCount)){
	                    return newCount;
	                }
	         }
		}

		@Override
		protected boolean tryReleaseShared(int arg) {
			 for(;;){
				int state = getState();
				if (compareAndSetState(state, state+arg)){
					return true;
				}
			 }
		}
		
		Condition newCondition(){
			return new ConditionObject();
		}
		
	}
	
	private final Sync sync = new Sync(3);
	
	@Override
	public void lock() {
		sync.acquireShared(1);
	}

	@Override
	public void lockInterruptibly() throws InterruptedException {
		sync.acquireSharedInterruptibly(1);
	}

	@Override
	public boolean tryLock() {
		return sync.tryAcquireShared(1)>=0;
	}

	@Override
	public boolean tryLock(long time, TimeUnit unit) throws InterruptedException {
		return sync.tryAcquireSharedNanos(1, unit.toNanos(time));
	}

	@Override
	public void unlock() {
		sync.releaseShared(1);
	}

	@Override
	public Condition newCondition() {
		return sync.newCondition();
	}
	
}

package com.xfy.bernard.thread;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.AbstractQueuedSynchronizer;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;

public class MyLock implements Lock{

	static class Sync extends AbstractQueuedSynchronizer{
		
		@Override
		protected boolean tryAcquire(int arg) {
			int state = getState();
			if (state==0){
				if (compareAndSetState(0, 1)){
					System.out.println(Thread.currentThread().getName()+"已获得独占锁...");
					setExclusiveOwnerThread(Thread.currentThread());
					return true;
				}
			}else {
				if (getExclusiveOwnerThread()==Thread.currentThread()){
					System.out.println(Thread.currentThread().getName()+"进入重入锁");
					int newState = getState()+arg;
					if (newState<0){
						throw new IllegalMonitorStateException("newState is less zero");
					}
					setState(newState);
					return true;
				}
			}
			return false;
		}

		@Override
		protected boolean tryRelease(int arg) {
			int newState = getState() - arg;
			if (Thread.currentThread()!=getExclusiveOwnerThread()){
				throw new IllegalMonitorStateException();
			}
			boolean free = false;
			if (newState ==0){
				free = true;
				setExclusiveOwnerThread(null);
				System.out.println(Thread.currentThread().getName()+"独占锁已释放!!");
			}
			setState(newState);
			return free;
		}

		@Override
		protected boolean isHeldExclusively() {
			return getState()==1;
		}
		
		Condition newCondition(){
			return new ConditionObject();
		}
		
	}
	
	private final Sync sync = new Sync();
	
	@Override
	public void lock() {
		sync.acquire(1);
	}

	@Override
	public void lockInterruptibly() throws InterruptedException {
		sync.acquireInterruptibly(1);
	}

	@Override
	public boolean tryLock() {
		return sync.tryAcquire(1);
	}

	@Override
	public boolean tryLock(long time, TimeUnit unit) throws InterruptedException {
		return sync.tryAcquireNanos(1, unit.toNanos(time));
	}

	@Override
	public void unlock() {
		sync.release(1);
	}

	@Override
	public Condition newCondition() {
		return sync.newCondition();
	}
}

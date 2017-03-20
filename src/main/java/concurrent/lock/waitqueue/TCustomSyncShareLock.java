package concurrent.lock.waitqueue;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.AbstractQueuedSynchronizer;
import java.util.concurrent.locks.AbstractQueuedSynchronizer.ConditionObject;
import java.util.concurrent.locks.Lock;

/** 
 *
 * @Author chenxun
 * @date 2016年12月22日 下午3:54:32 
 * @Theme:
 * @Reference:
 * @Descript:使线程节点进入阻塞的队列的不适合AQS实现的共享锁操作，
 * 因为newCondition.await()其中会调用tryRelease(long arg),而共享锁不会实现这个
 */
public class TCustomSyncShareLock implements Lock {
	
	private Sync sync;
	/**
	 * 
	 * @param limit 当前共享锁最大支持几个线程访问
	 */
	public TCustomSyncShareLock(int limit) {
		if(limit<1)
			throw new IllegalArgumentException(" limit must over zero");
		this.sync =  new Sync(limit);
	}
	
	@SuppressWarnings("serial")
	private  final class  Sync extends AbstractQueuedSynchronizer{
		public Sync(int limit){
			super.setState(limit);
		};
		
		public int tryAcquireShared(int reduceCount) {

			for (;;) {
				int current = getState();
				int newCount = current - reduceCount;
				if (newCount < 0 || compareAndSetState(current, newCount)) {
					return newCount;
				}
			}
		}

		public boolean tryReleaseShared(int returnCount) {

			for (;;) {
				int current = getState();
				int newCount = current + returnCount;
				if (compareAndSetState(current, newCount)) {
					return true;
				}
			}
		}
		
		@Override
		protected boolean isHeldExclusively() {
			return true;
		}

		public ConditionObject newCondition(){
			return new ConditionObject();
		}
	}
	
	@Override
	public void lock() {
		sync.acquireShared(1);
	}
	
	@Override
	public void unlock() {
		sync.releaseShared(1);
	}
	
	@Override
	public void lockInterruptibly() throws InterruptedException {
		sync.acquireSharedInterruptibly(1);
	}
	@Override
	public boolean tryLock() {
		return sync.tryAcquireShared(1) >= 0;
	}
	@Override
	public boolean tryLock(long time, TimeUnit unit)
			throws InterruptedException {
		return sync.tryAcquireSharedNanos(1, unit.toNanos(time));
	}
	
	@Override
	public ConditionObject newCondition() {
		return sync.newCondition();
	}
	public int getSameConditionObjectNodeSize(ConditionObject conditionObject) {
		return sync.getWaitingThreads(conditionObject).size();
	}
   
	public static void main(String[] args) throws InterruptedException {
		final TCustomSyncShareLock lock = new TCustomSyncShareLock(3);
		final ConditionObject newCondition = lock.newCondition();
		final  int[] ints = {0};
		for (int i = 0; i < 10; i++) {
			new Thread(new Runnable() {
				@Override
				public void run() {
						lock.lock();
						System.out.format("等待队列的大写:%d%n",lock.getSameConditionObjectNodeSize(newCondition));
						System.out.format("%s====%n",Thread.currentThread().getName());
						try {
							ints[0] = ints[0]+1;
							/**
							 * await 操作涉及的动作：
							 * 1.使线程节点进入 阻塞队列
							 * 2.使当前节点释放锁，其中会调用tryRelease(long arg) 
							 * 由于共享锁不会重写这个方法 ，所以使线程节点进入阻塞的队列的不适合AQS实现的共享锁操作
							 * 所以本类不适合
							 */
							newCondition.await();
						} catch (InterruptedException e) {
						}finally{
						    lock.unlock();
						}
				}
	    	}).start();
		};
		
		{
			while(ints[0]!=100)
				Thread.yield();
		}
		
		lock.lock();
		System.out.format("等待队列的大写:%d%n",lock.getSameConditionObjectNodeSize(newCondition));
		newCondition.signalAll();
		lock.unlock();
	}
   
}

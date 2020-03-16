package concurrent.lock;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.AbstractQueuedSynchronizer;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;

/** 
 *
 * @Author chenxun
 * @date 2016年12月22日 下午3:54:32 
 * @Theme:
 * 			自定义同步共享锁
 * @Reference:
 * @Descript:
 */
public class TCustomSyncShareLock implements Lock {
	
	private SyncShare sync;
	/**
	 * 
	 * @param limit 当前共享锁最大支持几个线程访问
	 */
	public TCustomSyncShareLock(int limit) {
		if(limit<1){
			throw new IllegalArgumentException(" limit must over zero");
		}
		this.sync =  new SyncShare(limit);
	}
	
	@SuppressWarnings("serial")
	private  final class  SyncShare extends AbstractQueuedSynchronizer{
		public SyncShare(int limit){
			super.setState(limit);
		};

		@Override
		public int tryAcquireShared(int reduceCount) {
			for (;;) {
				int current = getState();
				int newCount = current - reduceCount;
				if (newCount < 0 || compareAndSetState(current, newCount)) {
					return newCount;
				}
			}
		}

		@Override
		public boolean tryReleaseShared(int returnCount) {
			for (;;) {
				int current = getState();
				int newCount = current + returnCount;
				if (compareAndSetState(current, newCount)) {
					return true;
				}
			}
		}

		Condition newCondition(){
			return new ConditionObject();
		}


		public int getTState(){
			return super.getState();
		};
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
	public Condition newCondition() {
		return sync.newCondition();
	}
   
	public static void main(String[] args) throws InterruptedException {
		final TCustomSyncShareLock lock = new TCustomSyncShareLock(3);
		ExecutorService newFixedThreadPool = Executors.newFixedThreadPool(11);
		for (int i = 0; i < 10; i++) {
			int finalI = i;
			Runnable runnable = () -> {
				try {
					lock.lock();
					System.out.format("%s====%d%n", Thread.currentThread().getName(),lock.sync.getTState());
					Thread.sleep(1000* finalI);
				} catch (InterruptedException e) {
				} finally {
					lock.unlock();
				}
			};
			newFixedThreadPool.submit(runnable);
		}

		Thread.sleep(12000);
		System.exit(0);
	}
   
}

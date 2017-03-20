package concurrent.lock;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.AbstractQueuedSynchronizer;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;

/** 
 * @Author:chenxun
 * @2016年12月24日 下午12:25:29 
 * @Theme:
 * 			自定义同步互斥锁（独享锁）
 * @Reference:
 * @Descript:
 * 			锁是面向用户的，同步器是面向锁的（就是锁的内部实现）
 * 			锁有独享锁，共享锁。
 */
public class TCustomSyncExclusiveLock implements Lock {
	
	private final class SyncLock  extends AbstractQueuedSynchronizer{

		private static final long serialVersionUID = 7780381425422372965L;
		
		@Override
		protected boolean tryAcquire(int arg) {
			if(compareAndSetState(0, 1)){
				setExclusiveOwnerThread(Thread.currentThread());
				return true;
			}
			return false;
		}

		@Override
		protected boolean tryRelease(int arg) {
			if(getState()==0){
				throw new IllegalMonitorStateException("没有指定监视器的线程");
			}
			setExclusiveOwnerThread(null);
			setState(0);
			return true;
		}
        /**
         * 是否是独享锁
         */
		@Override
		protected boolean isHeldExclusively() {
			return getState()==1;
		}
		
		Condition newCondition(){
			return new ConditionObject();
		}
	}
    private SyncLock syncLock = new SyncLock();
    
	@Override
	public void lock() {
		syncLock.acquire(1);
	}

	@Override
	public void lockInterruptibly() throws InterruptedException {
		syncLock.acquireInterruptibly(1);
	}

	@Override
	public boolean tryLock() {
		return syncLock.tryAcquire(1);
	}

	@Override
	public boolean tryLock(long time, TimeUnit unit)
			throws InterruptedException {
		//此处需要把时间换算成为毫秒值 ，省略
		return syncLock.tryAcquireNanos(1, time);
	}

	@Override
	public void unlock() {
		syncLock.release(1);
	}

	@Override
	public Condition newCondition() {
		return syncLock.newCondition();
	}
	
	public int getWaitQueuedSzie() {
		return syncLock.getQueuedThreads().size();
	}
	
	/**
	 * 测试可以通过打开锁和注释锁进行测试
	 * @param args
	 */
	public static void main(String[] args) {
		final TCustomSyncExclusiveLock lock = new TCustomSyncExclusiveLock();
		final int[] ints = {0};
		for (int i = 0; i < 10000; i++) {
			new Thread(new Runnable() {
				@Override
				public void run() {
					try {
						lock.lock();
						System.out.format("等待队列的大写:%d%n",lock.getWaitQueuedSzie());
						ints[0] = ints[0]+1;
					}finally{
						lock.unlock();
					}
				}
			}).start();
		}
		
		while (Thread.activeCount()>1) {
			Thread.yield();
		}
		System.out.println(ints[0]);
	}

}

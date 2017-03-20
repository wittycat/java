package concurrent.lock.waitqueue;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.AbstractQueuedSynchronizer;
import java.util.concurrent.locks.AbstractQueuedSynchronizer.ConditionObject;
import java.util.concurrent.locks.Lock;

/** 
 * @Author:chenxun
 * @2016年12月30日 下午12:25:29 
 * @Theme:
 * 			在“自定义同步互斥锁（独享锁）” 基础之上对：同步器的阻塞队列和同步队列进行测试
 * @Reference:
 * @Descript:
 *          使线程节点进入阻塞的队列的不适合AQS实现的共享锁操作,只适合互斥锁
 *          注意: newCondition.signalAll() 调用的前提是必须获取锁
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
		
		protected ConditionObject newCondition(){
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
	public ConditionObject newCondition() {
		return syncLock.newCondition();
	}
	
	public int getWaitQueuedSzie() {
		return syncLock.getQueuedThreads().size();
	}
	/**
	 * 某个监听器上的阻塞队列（或条件队列）大小
	 * @param conditionObject
	 * @return
	 */
	public int getSameConditionObjectNodeSize(ConditionObject conditionObject) {
		return syncLock.getWaitingThreads(conditionObject).size();
	}
	
	/**
	 * 测试可以通过打开锁和注释锁进行测试
	 * @param args
	 * @throws InterruptedException 
	 */
	public static void main(String[] args) throws InterruptedException {
		final TCustomSyncExclusiveLock lock = new TCustomSyncExclusiveLock();
		final ConditionObject newCondition = lock.newCondition();
		final  int[] ints = {0};
		for (int i = 0; i < 100; i++) {
			new Thread(new Runnable() {
				@Override
				public void run() {
					try {
						lock.lock();
						System.out.printf("sleep - %s",Thread.currentThread().getName());
						System.out.format("******等待队列的大写:%d%n",lock.getSameConditionObjectNodeSize(newCondition));
						ints[0] = ints[0]+1;
						/**
						 * await 操作涉及的动作：
						 * 1.使线程节点进入 阻塞队列
						 * 2.使当前节点释放锁，其中会调用tryRelease(long arg) 
						 * 由于共享锁不会重写这个方法 ，所以使线程节点进入阻塞的队列的不适合AQS实现的共享锁操作
						 */
						newCondition.await();
						System.out.printf("wake up - %s%n",Thread.currentThread().getName());
					} catch (InterruptedException e) {
						e.printStackTrace();
					}finally{
						lock.unlock();
					}
				}
			}).start();
		}
		/**
		 * 保证所有线程都处于等待状态
		 */
		{
			while(ints[0]!=100)
				Thread.yield();
		}
		/**
		 * newCondition.signalAll() 调用的前提是必须获取锁
		 */
		{
			lock.lock();
			System.out.format("等待队列的大写:%d%n",lock.getSameConditionObjectNodeSize(newCondition));
			newCondition.signalAll();//使线程节点从 阻塞队列恢复到同步队列
			lock.unlock();
		}
		/**
		 * 保证所有子线程都结束
		 */
		while(Thread.activeCount()>1){
			Thread.yield();
		}
	}

}

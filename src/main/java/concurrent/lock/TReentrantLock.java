package concurrent.lock;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/** 
 * @Author:chenxun
 * @2016年12月31日 下午9:38:15 
 * @Theme:ReentrantLock基本使用
 * @Reference:
 * @Descript:
 * <ul>
 * <li>ReentrantLock,属于互斥锁，重入锁
 * <li>ReentrantLock的方法在调用时 如果抛出 IllegalMonitorStateException - 则该方法必须在锁的区域内调用
 */
public class TReentrantLock {
	public static void main(String[] args) {
		/**
		 * 1.公平锁（相比较非公平锁）可以保证 块1 少获得执行权限;
		 */
		final ReentrantLock lock  = new ReentrantLock(true);
		final Condition condition = lock.newCondition();
		for (int i = 0; i < 10; i++) {
			Thread thread = new Thread(new Runnable() {
				@Override
				public void run() {
					lock.lock();
					System.out.format("%s 获取锁,处于等待 %n", Thread.currentThread().getName());
					try {
						condition.await();
						System.out.format("%s 获取锁, 被唤醒  %n", Thread.currentThread().getName());
					} catch (InterruptedException e) {
					}finally{
						lock.unlock();
					}
				}
			});
			thread.setName("thread-"+i);
			thread.start();
		}
		//保证所有线程都启动,但不能保证所有线程已经获取过锁
		while(Thread.activeCount()<11)
			Thread.yield();
		
		{
			System.out.format("返回正等待获取此锁的线程估计数:%d %n", lock.getQueueLength());
			System.out.format("查询给定线程是否正在等待获取此锁:%b %n", lock.hasQueuedThread(Thread.currentThread()));
			System.out.format("查询是否有些线程正在等待获取此锁:%b %n", lock.hasQueuedThreads());
			System.out.format("此锁是否为公平锁:%b %n", lock.isFair());
			System.out.format("查询当前线程是否保持此锁:%b %n", lock.isHeldByCurrentThread());
			System.out.format("查询此锁是否由任意线程保持:%b %n", lock.isLocked());
		}
	    /**
	     * 块1:
	     * 保证其他线程全部被唤醒
	     */
		{
			int i = 0 ;
			while(Thread.activeCount()>1){
				lock.lock();
				System.out.format("查询是否有些线程正在等待与此锁有关的给定条件:%s %n", lock.hasWaiters(condition));
				condition.signalAll();
				i++;
				lock.unlock();
			}
			System.out.println("再次获取锁次数"+i);
		}
		
		System.out.println("=====================测试重入性质============================");
		{
			lock.lock();
			lock.lock();
			lock.lock();
			System.out.format("查询当前线程保持此锁的次数:%d %n", lock.getHoldCount());
			lock.unlock();
			System.out.format("查询当前线程保持此锁的次数:%d %n", lock.getHoldCount());
			System.out.format("查询当前线程是否保持此锁:%b %n", lock.isHeldByCurrentThread());
			lock.unlock();
			lock.unlock();
			System.out.format("查询当前线程是否保持此锁:%b %n", lock.isHeldByCurrentThread());
		}

	}
}

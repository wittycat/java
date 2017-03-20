package concurrent.lock;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/** 
 * @author：chenxun
 * createDate：2016年11月13日 下午11:14:09 
 * Theme:
 * reference:
 * descript:
 * Condition:为每个lock提供多个监听对象
 */
public class TCondition {
	
  static volatile int  semaphore;
  
  public static void main(String[] args) {
  	final ReentrantLock reentrantLock = new ReentrantLock();
	final Condition newCondition1 = reentrantLock.newCondition();
	final Condition newCondition2 = reentrantLock.newCondition();
	new Thread(new Runnable() {
		@Override
		public void run() {
			try {
				reentrantLock.lock();
				semaphore ++;
				System.out.println(Thread.currentThread().getName()+"-ing");
				newCondition1.await();
				newCondition2.signal();
				System.out.println(Thread.currentThread().getName()+"-over");
			} catch (InterruptedException e) {
			}finally{
				reentrantLock.unlock();
			}
		}
	}).start();
	
	new Thread(new Runnable() {
		@Override
		public void run() {
			try {
				/**
				 * 1.保证线程0先获取lock
				 * 2.如果线程1先获取锁，则2个线程就会处于阻塞状态
				 */
				while(semaphore!=1)
					Thread.yield();
				
				reentrantLock.lock();
				System.out.println(Thread.currentThread().getName()+"-ing");
				newCondition1.signal();
				newCondition2.await();
				System.out.println(Thread.currentThread().getName()+"-over");
			} catch (InterruptedException e) {
			}finally{
				reentrantLock.unlock();;
			}
		}
	}).start();
	
	while (Thread.activeCount()>1) {
		Thread.yield();
	}
	System.out.println("mian-over");
  }
}

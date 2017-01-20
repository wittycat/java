package concurrent.lock.comporeblock;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author:chenxun
 * @2016年12月27日 上午12:19:53
 * @Theme:
 * @Reference:Condition和synchronized依赖锁
 * @Descript:针对锁上的监听器（可以获取多个）去控制线程阻塞和非阻塞 和synchronized 更相近
 */
public class TCondition {
	
	static final ReentrantLock reentrantLock = new ReentrantLock();
	
	static final Condition  condition= reentrantLock.newCondition();

	public static void main(String[] args) throws InterruptedException {
		
		reentrantLock.lock();
		try {
			ThreadA ta = new ThreadA("ta");
			System.out.println(Thread.currentThread().getName() + " start ta");
			ta.start();
			System.out.println(Thread.currentThread().getName() + " block");
			// 主线程阻塞
			condition.await();
			System.out.println(Thread.currentThread().getName() + " continue");
		} catch (Exception e) {
		}finally{
			reentrantLock.unlock();
		}
	}

	static class ThreadA extends Thread {

		public ThreadA(String name) {
			super(name);
		}

		public void run() {
			try {
				reentrantLock.lock();
				System.out.println(Thread.currentThread().getName()+ " wakup others");
				condition.signal();
			}finally{
				reentrantLock.unlock();
			}
		}
	}
}

package concurrent.lock;

import java.util.ArrayList;
import java.util.Collection;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/** 
 * @Author:chenxun
 * @2016年12月31日 下午9:38:15 
 * @Theme:主要测试公平锁和非公平锁的性能和同步队列中线程的打印
 * @Reference:
 * @Descript
 * <ul>
 * <li>不管是公平锁或者非公平锁都使用的AQS的同步队列
 * <li>主要差异就在于
 * <li>公平锁当前一个线程释放锁锁后，下一次锁一定让同步队列中的头节点获取；
 * <li>非公平锁再上一个线程释放锁后，下一次获取锁权限又当前并发的线程去竞争，如果没有竞争锁的并发线程，
 * 	       其实和公平锁一样，把锁的权限交给同步队列的头节点
 * 
 */
public class TReentrantLock2 {
	
	static class InnerReentrantLock  extends ReentrantLock{
	
		private static final long serialVersionUID = 1L;

		public InnerReentrantLock (boolean fair){
			super(fair);
		}

		public Collection<Thread> getQueuedThreads() {
			ArrayList<Thread> list = new ArrayList<Thread>(super.getQueuedThreads());
			java.util.Collections.reverse(list);
			return list;
		}
	}
	
	static class Job extends Thread{
		Lock lock;
		public Job (Lock lock){
			this.lock = lock;
		}
		
		@Override
		public void run() {
			try {
				for (int i = 0; i <2; i++) {
					lock.lock();
					System.out.format("%s 获取锁,等待的线程:%s %n",getName(),((InnerReentrantLock)lock).getQueuedThreads());
					lock.unlock();
				}
			}finally{
			}
		}
		/**
		 * 改变线程默认的toString打印
		 */
		@Override
		public String toString() {
			return getName();
		}
	}
	
	public static void main(String[] args) {
		//前提:把不同线程获取锁的一次定义为一次上下文切换
		
		//获取同等锁次数情况下，非公平锁相对用时更少，原因是减少了cpu的上下文切换
//		isFairWithTime();
		
		//公平锁执行较多上下文切换次数，
		//而非公平锁执行上下文切换次数较少（原因是当一个线程获取释放锁后，下一次如果它再需要锁，相对比其他线程获取锁的概率更大）
		boolean fair = false;
		isFairWithCount(fair);
	}

	public static void isFairWithTime() {
		boolean fair;
		for (int j = 0; j < 10; j++) {
			long nanoTime = System.nanoTime();
			fair = j%2==0?true:false;
			InnerReentrantLock lock  = new InnerReentrantLock(fair);
			for (int i = 0; i < 10; i++) {
				Job thread = new Job(lock);
				thread.setName(""+i);
				thread.start();
			}
			//all over
			while(Thread.activeCount()>1)
				Thread.yield();
			System.out.format("是否是公平锁%B,用时%d[微妙]%n",fair,System.nanoTime()-nanoTime);
		}
	}
	
	public static void isFairWithCount(boolean fair) {
			InnerReentrantLock lock  = new InnerReentrantLock(fair);
			for (int i = 0; i < 10; i++) {
				Job thread = new Job(lock);
				thread.setName(""+i);
				thread.start();
			}
			//all over
			while(Thread.activeCount()>1)
				Thread.yield();
	}
}
